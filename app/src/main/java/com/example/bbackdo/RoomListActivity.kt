package com.example.bbackdo

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bbackdo.databinding.*
import com.example.bbackdo.dto.Room
import com.example.bbackdo.dto.Room.Companion.STATE_WAIT
import com.example.bbackdo.dto.Team
import com.example.bbackdo.dto.User
import com.example.bbackdo.lib.Authentication
import com.example.bbackdo.lib.Authentication.uid
import com.example.bbackdo.lib.Database
import com.example.bbackdo.lib.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.getValue
import splitties.activities.start
import splitties.bundle.putExtras
import java.util.*
import kotlin.collections.ArrayList

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

            // 방만들기 클릭
            buttonMake.setOnClickListener {
                start<CreateRoomActivity>()
            }

            //item 간격 설정
            val spaceDecoration = VerticalSpaceItemDecoration(50)
            recyclerRoom.addItemDecoration(spaceDecoration)

            recyclerRoom.adapter = adapter
            // 새로고침
            swipeRefreshLayout.setOnRefreshListener {
                refreshRoomList(false)
            }

            //마이페이지
            Database.getReference("users/$uid").get().addOnSuccessListener {
                val user = it.getValue<User>()
                if (user != null) {
                    textViewName.text = user.nickname
                    var record = "${user.win + user.lose}전${user.win}승${user.lose}패"
                    textViewInfo.text = record
                }

            }

            buttonOpen.setOnClickListener {
                val anim = TranslateAnimation(page.width.toFloat(), 0f, 0f, 0f)
                anim.duration = 400
                anim.fillAfter = true
                page.animation = anim
                page.visibility = View.VISIBLE
                pageBlack.visibility = View.VISIBLE
            }

            imageButtonClose.setOnClickListener {
                val anim = TranslateAnimation(0f, page.width.toFloat(), 0f, 0f)
                anim.duration = 400
                anim.fillAfter = true
                page.animation = anim
                page.visibility = View.GONE
                pageBlack.visibility = View.GONE
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
                //Toast.makeText(this@RoomListActivity, room.state.toString(), Toast.LENGTH_SHORT).show()
                when(room.state){
                    STATE_WAIT->dataList.add(room)
                }

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

    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = verticalSpaceHeight
        }
    }


    inner class RecyclerViewAdapter(
        private val context: Context,
        private var listData: ArrayList<Room>
    ) :
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(), Filterable {
        var unfilteredList = listData //필터 전 리스트
        var filteredList = listData //필터 중인 리스트


        val inflater:LayoutInflater by lazy { LayoutInflater.from(context) }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
            val view =
                ItemRecyclerRoomBinding.inflate(inflater, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
            val room: Room = filteredList[position]
//            Toast.makeText(parent, listData.toString(), Toast.LENGTH_SHORT).show()
            //Log.e("datalist", "data : $listData")
            holder.binding(room)
        }

        override fun getItemCount(): Int {
         //   Log.e("datalist", "data : ${filteredList}")
            return filteredList.size
        }
        //검색 시 필터링
        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    val charString = constraint.toString()
                    filteredList = if (charString.isEmpty()) { //필터된 리스트
                        unfilteredList
                    } else {
                        val filteringList = ArrayList<Room>()
                        for (item in unfilteredList) {
                            if (item.title?.contains(charString) == true) filteringList.add(item)
                        }
                        filteringList
                    }
                    val filterResults = FilterResults()
                    filterResults.values = filteredList
                    return filterResults
                }

                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                    filteredList = results?.values as ArrayList<Room>
                    notifyDataSetChanged()
                }
            }

        }

        private fun enterRoom(context: Context, rid: String?, uid: String?) {

            Database.getReference("rooms/$rid").get().addOnSuccessListener {

                val room = it.getValue<Room>()
                val tids = room?.teams?.keys
                val test = room?.memberNum
                
                Log.d("help", tids.toString())
                if (room != null && room.state == Room.STATE_WAIT) {
                    if (it.child("users/$uid").exists()) {
                        Log.d("help", tids.toString())
                        context.start<TeamActivity> {
                            putExtras(TeamActivity.Extras) {
                                this.room = room
                            }
                        }
                    } else {
                        val random = Random ()

                        val keyList = ArrayList<String>()

                        if(tids !=null) {
                            for(key in tids) keyList.add(key)
                        }
                        val myTid = keyList.get(random.nextInt(keyList.size))
                        Log.d("help",myTid)
                        val updates = hashMapOf(
                            "rooms/$rid/users/$uid" to true,
                            "users/$uid/teams/$myTid" to false,
                            "teams/$myTid/members/$uid" to false
                        )
                        Database.getReference("").updateChildren(updates as Map<String, Any>).addOnSuccessListener {

                        }

                        context.start<TeamActivity> {
                            putExtras(TeamActivity.Extras) {
                                this.room = room
                            }
                        }

                    }
                } else {
                    // 방이 게임중이거나 없을 때
                }

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

