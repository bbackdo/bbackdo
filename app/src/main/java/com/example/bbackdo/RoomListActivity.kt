package com.example.bbackdo

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bbackdo.databinding.*
import com.example.bbackdo.dto.Room
import com.example.bbackdo.dto.Team
import com.example.bbackdo.lib.Authentication
import com.example.bbackdo.lib.Database
import com.example.bbackdo.lib.Util
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.getValue
import splitties.activities.start
import splitties.bundle.putExtras


//알림, 액티비티
//업을 떄/잡을 떄/졌을 때/자리

class RoomListActivity : AppCompatActivity() {

    //var firestore : FirebaseFirestore? = null
    private val room by lazy {
        ActivityRoomListBinding.inflate(layoutInflater)
    }
    private val bind by lazy {
        DialogMakeRoomBinding.inflate(layoutInflater)
    }

    private val dataList = arrayListOf<Room>()
    private val adapter = RecyclerViewAdapter(this@RoomListActivity, dataList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(room) {
            setContentView(root)
           // Toast.makeText(this@RoomListActivity, "방만들기 액티비티", Toast.LENGTH_SHORT).show()
            // 방만들기 클릭 했을 때
            buttonMake.setOnClickListener {
                start<CreateRoomActivity>()
            }

            recyclerRoom.adapter = adapter
            // 새로고침
            swipeRefreshLayout.setOnRefreshListener {
                refreshRoomList(false)
            }
        }
    }

    private fun refreshRoomList(refreshing: Boolean){
        room.swipeRefreshLayout.setRefreshing(refreshing)
        Database.getReference("rooms").get().addOnSuccessListener {
            dataList.clear()
            for (roomPair in it.children) {
                //Toast.makeText(this@RoomListActivity, roomPair.getValue<Room>().toString(), Toast.LENGTH_SHORT).show()
                val room =  roomPair.getValue<Room>()!!
                dataList.add(room)
            }
           // Toast.makeText(this@RoomListActivity, dataList.toString(), Toast.LENGTH_SHORT).show()
            dataList.reverse()
            adapter.notifyDataSetChanged()
            room.swipeRefreshLayout.setRefreshing(false)
        }
    }

    override fun onStart() {
        super.onStart()
        refreshRoomList(true)
    }


    inner class RecyclerViewAdapter(
        private val context: Context,
        private var listData: ArrayList<Room>
    ) :
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
        val inflater:LayoutInflater by lazy { LayoutInflater.from(context) }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
            val view =
                ItemRecyclerRoomBinding.inflate(inflater, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
            val room: Room = listData[position]
//            Toast.makeText(parent, listData.toString(), Toast.LENGTH_SHORT).show()
            //Log.e("datalist", "data : $listData")
            holder.binding(room)
        }

        override fun getItemCount(): Int {
            Log.e("datalist", "data : ${listData.size}")
            return listData.size
        }

        private fun enterRoom(context: Context, rid: String?, uid: String?) {

            Database.getReference("rooms/$rid").get().addOnSuccessListener {
                /*
                val room = it.getValue<Room>()
                if (room != null && room.state == Room.STATE_WAIT) {
                    if (it.child("users/$uid").exists()) {
                        context.start<RoomActivity> {
                            putExtras(RoomActivity.Extras) {
                                this.room = room
                            }
                        }
                    } else {
                        val updates = hashMapOf(
                            "rooms/$rid/users/$uid" to true,
                            "users/$uid/rooms/$rid" to ServerValue.TIMESTAMP
                        )
                        Database.getReference("")
                            .updateChildren(updates)
                            .addOnSuccessListener {
                                Database.sendChat(
                                    rid!!,
                                    Chat.TYPE_ENTER,
                                    Chat.MESSAGE_ENTER
                                )
                                context.start<RoomActivity> {
                                    putExtras(RoomActivity.Extras) {
                                        this.room = room
                                    }
                                }
                            }
                    }
                } else {
                    // 방이 게임중이거나 없을 때
                }

                 */
            }
        }


        inner class ViewHolder(private val bind: ItemRecyclerRoomBinding) :
            RecyclerView.ViewHolder(bind.root) {
            fun binding(room: Room) {
                with(bind) {
                    textTitle.text = room.title.toString()
                    textLimit.text = "${room.users?.size ?:0}/${room.memberNum}"
                    if(room.password == "")
                        imageView.visibility = View.INVISIBLE
                    itemView.setOnClickListener {
                        val rid = room.rid
                        val uid = Authentication.uid

                        val enterRoomBinding =
                            DialogEnterRoomBinding.inflate(inflater)
                        with(enterRoomBinding) {
                            alertTitleTextView.text = room.title
                            @SuppressLint("SetTextI18n")
                            alertMembersTextView.text =
                                "${room.users?.size ?: 0} / ${room.memberNum}"
                            AlertDialog.Builder(context).setView(root)
                                .setPositiveButton("입장") { _: DialogInterface, _: Int ->


                                    if (!room.password.isNullOrBlank()) {

                                        val editTextBinding =
                                            AlertdialogEdittextBinding.inflate(inflater)

                                        with(editTextBinding) {
                                            AlertDialog.Builder(context)
                                                .setTitle("비밀번호를 입력하세요")
                                                .setView(root)
                                                .setPositiveButton("입장") { _: DialogInterface, _: Int ->
                                                    val enterPassword =
                                                        alertEditText.text.toString()
                                                    // 비밀 번호 맞을 때
                                                    if (room.password == enterPassword) {
                                                        enterRoom(context, rid, uid)
                                                    }
                                                    // 비밀번호 틀렸을 때
                                                    else {
                                                        Toast.makeText(
                                                            context,
                                                            "비밀번호가 틀렸습니다.",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                                .setNeutralButton("취소", null)
                                                .create().show()
                                        }

                                    } else { // 비밀번호 없을 때
                                        enterRoom(context, rid, uid)
                                    }
                                }.setNeutralButton("취소", null).also { alertDialog ->
                                    Util.uidToNickname(room.manager!!) {
                                        if (it != Util.MESSAGE_UNDEFINED) {
                                            val nickname = it as String
                                            alertManagerTextView.text = nickname
                                            alertDialog.show()
                                        }
                                    }
                                }
                        }



                    }
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        // 참가하기 클릭 했을 때

                    }
                }

            }


            fun setTitle(title: String) {

            }
        }
    }
}

