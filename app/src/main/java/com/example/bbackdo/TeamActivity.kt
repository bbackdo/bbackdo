package com.example.bbackdo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bbackdo.databinding.*
import com.example.bbackdo.dto.Room
import com.example.bbackdo.dto.Team
import com.example.bbackdo.dto.User
import com.example.bbackdo.lib.Authentication.uid
import com.example.bbackdo.lib.Database
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import splitties.bundle.BundleSpec
import splitties.bundle.bundle
import splitties.bundle.withExtras
import java.util.*
import kotlin.collections.HashMap

class TeamActivity : AppCompatActivity() {
    object Extras : BundleSpec() {
        var room: Room by bundle()
    }

    private val room by lazy {
        withExtras(Extras) {
            room
        }
    }

    private val bind by lazy {
        ActivityTeamBinding.inflate(layoutInflater)
    }

    private var teamList = HashMap<String, Any>()
    private var userList = HashMap<String, Any>()
    private var dataList = arrayListOf<Team>()
    private val adapter = TeamAdapter(this@TeamActivity, dataList)
    private val memberList = arrayListOf<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(bind) {
            setContentView(root)

            Database.getReference("rooms/${room.rid}/teams").get().addOnSuccessListener {
                teamList = it.getValue<HashMap<String, Any>>()!!
            }

            //Log.d("horang datalist", dataList.toString())
            teamRecycler.adapter = adapter

            // 새로고침
            swipeRefreshLayout.setOnRefreshListener {
                refreshRoomList(true)
            }

            Database.getReference("teams").addChildEventListener(teamEventListener)

            readyButton.setOnClickListener {
                Database.getReference("rooms/${room.rid}/manager").get().addOnSuccessListener {
                    when (it.value?.equals(uid)) {
                        true -> gameStart()
                        false -> gameReady()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        Database.getReference("rooms/${room.rid}/manager").get().addOnSuccessListener {
            var readyButton = findViewById<Button>(R.id.readyButton)
            when(it.value?.equals(uid)) {
                true -> {
                    readyButton.text = "시작"
                }
                false -> {
                    readyButton.text = "준비"
                }
             }
        }

        refreshRoomList(true)
    }

    private fun gameStart() {
        Database.getReference("users/$uid/readyState").setValue(true)

        val users = Database.getReference("rooms/${room.rid}/users").get()
        for (userID in listOf(users)) {
            if(Database.getReference("users/${userID}/readyState").equals(false)) {
                Toast.makeText(this, "모든 플레이어가 준비 상태여야만 게임을 시작할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val intent = Intent(this, GameActivity::class.java)
        //intent.putExtra("teamNum", count)
        startActivity(intent)

    }

    private fun gameReady() {
        var readyButton = findViewById<Button>(R.id.readyButton)
        Database.getReference("users/$uid/readyState").get().addOnSuccessListener {
            when (it.value) {
                true -> {
                    Database.getReference("users/$uid/readyState").setValue(false)
                    readyButton.text = "준비"

                }
                false -> {
                    Database.getReference("users/$uid/readyState").setValue(true)
                    readyButton.text = "준비 완료"
                }
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun refreshRoomList(refreshing: Boolean){
        bind.swipeRefreshLayout.setRefreshing(refreshing)

        Database.getReference("teams").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {

                    if (it.getValue<Team>()?.tid in teamList && it.getValue<Team>() !in dataList) {
                        dataList.add(it.getValue<Team>()!!)
//                        Log.d("horang list", teamList.toString())
//                        if (dataList[dataList.lastIndex].members != null){ //멤버가 있을 경우
//                            userList =
//                                dataList[dataList.lastIndex].members as HashMap<String, Any> //userlist는 member야
//                            // 이 유저 리스트를 memberlist에 넣어야해
//                            Database.getReference("users").get().addOnSuccessListener { users->
//                                users.children.forEach { user->
//                                    if (user.key in userList){
//                                        memberList.add(user.getValue<User>()!!)
//                                        Log.d("horang user", it.key.toString())
//                                    }
//                                }
//                            }
//                            Log.d("horang children", userList.toString())
//                        }
//                        memberAdapter.notifyItemChanged(memberList.lastIndex)
                        adapter.notifyItemInserted(dataList.lastIndex)
                    }
                }
                bind.swipeRefreshLayout.setRefreshing(false)

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }


    private val teamEventListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            adapter.notifyDataSetChanged()
            adapter.notifyItemInserted(dataList.lastIndex)

        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onChildRemoved(snapshot: DataSnapshot) {

        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onCancelled(error: DatabaseError) {

        }

    }
}

