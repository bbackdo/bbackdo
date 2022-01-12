package com.example.bbackdo

import android.os.Bundle
import android.util.Log
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
    private var dataList = arrayListOf<Team>()
    private val adapter = TeamAdapter(this@TeamActivity, dataList)
    private val memberList = arrayListOf<User>()
    private val memberAdapter = EachTeamAdapter(this@TeamActivity, memberList)

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
                refreshRoomList(false)
            }

            Database.getReference("teams").addChildEventListener(teamEventListener)

            readyButton.setOnClickListener {
                Database.getReference("users/$uid/readyState").get().addOnSuccessListener {

                    when (it.value) {
                        true -> {
                            Database.getReference("users/$uid/readyState").setValue(false)
                            readyButton.setText("be ready")
                        }
                        false -> {
                            Database.getReference("users/$uid/readyState").setValue(true)
                            readyButton.setText("I'm ready")
                        }
                    }

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        refreshRoomList(true)

    }
    private fun refreshRoomList(refreshing: Boolean){
        bind.swipeRefreshLayout.setRefreshing(refreshing)
        Database.getReference("teams").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    Log.d("horang children", it.getValue<Team>().toString())
                    if (it.getValue<Team>()?.tid in teamList &&  !(it.getValue<Team>() in dataList)) {
                        dataList.add(it.getValue<Team>()!!)
                        Log.d("horang list", dataList.toString())
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

