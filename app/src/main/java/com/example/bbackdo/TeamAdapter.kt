package com.example.bbackdo

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.bbackdo.databinding.AlertdialogEdittextBinding
import com.example.bbackdo.databinding.DialogEnterRoomBinding
import com.example.bbackdo.databinding.ItemRecyclerRoomBinding
import com.example.bbackdo.dto.Room
import com.example.bbackdo.lib.Authentication
import com.example.bbackdo.lib.Database
import com.example.bbackdo.lib.Util
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.getValue
import splitties.activities.start
import splitties.bundle.putExtras

class TeamAdapter(
    private val context: Context,
    private var listData: ArrayList<Room>
) :
    RecyclerView.Adapter<TeamAdapter.ViewHolder>(), Filterable {
    var unfilteredList = listData //필터 전 리스트
    var filteredList = listData //필터 중인 리스트


    val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamAdapter.ViewHolder {
        val view =
            ItemRecyclerRoomBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamAdapter.ViewHolder, position: Int) {
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
                    var filteringList = ArrayList<Room>()
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
            if (room != null && room.state == Room.STATE_WAIT) {
                if (it.child("users/$uid").exists()) {
                    context.start<TeamActivity> {
                        putExtras(TeamActivity.Extras) {
                            this.room = room
                        }
                    }
                } else {
                    val updates = hashMapOf(
                        "rooms/$rid/users/$uid" to true,
                        "users/$uid/rooms/$rid" to ServerValue.TIMESTAMP
                    )
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
