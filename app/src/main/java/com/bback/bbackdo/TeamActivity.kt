package com.bback.bbackdo

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bback.bbackdo.databinding.ActivityTeamBinding
import com.bback.bbackdo.databinding.DialogExitBinding
import com.bback.bbackdo.dto.Room
import com.bback.bbackdo.dto.Team
import com.bback.bbackdo.dto.User
import com.bback.bbackdo.lib.Authentication.uid
import com.bback.bbackdo.lib.Database
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import splitties.activities.start
import splitties.bundle.BundleSpec
import splitties.bundle.bundle
import splitties.bundle.withExtras

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
            teamRecycler.adapter = adapter

            val gridLayouManager = GridLayoutManager(applicationContext, 2)
            teamRecycler.layoutManager = gridLayouManager
            //Log.d("horang datalist", dataList.toString())

            // ????????????
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
            backbutton.setOnClickListener {
                with(bind) {
                    val exitbind = DialogExitBinding.inflate(layoutInflater)
                    with(exitbind) {
                        Database.getReference("rooms/${room.rid}").get().addOnSuccessListener {
                            //Toast.makeText(this@TeamActivity, "${it.value}", Toast.LENGTH_SHORT).show()
                            if (it.getValue<Room>()?.manager == uid) {
                                alertTitleTextView.text = "????????? ????????? ?????? ???????????????."
                                AlertDialog.Builder(this@TeamActivity, R.style.MyDialogTheme)
                                    .setView(root)
                                    .setPositiveButton("?????????") { _: DialogInterface, _: Int ->
                                        Database.getReference("rooms/${room.rid}/teams").get()
                                            .addOnSuccessListener { teams ->
                                                teams.children.forEach { team ->
                                                    val tid = team.key
                                                    //Toast.makeText(this@TeamActivity, tid.toString(), Toast.LENGTH_SHORT).show()
                                                    Database.getReference("teams/$tid")
                                                        .removeValue()

                                                }
                                            }

                                        Database.getReference("rooms/${room.rid}").removeValue()
                                        finish()
                                        start<RoomListActivity> {
                                            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        }

                                    }
                                    .setNeutralButton("??????", null)
                                    .show()

                            } else {

                                alertTitleTextView.text = "??????????????????????"
                                AlertDialog.Builder(this@TeamActivity, R.style.MyDialogTheme)
                                    .setView(root)
                                    .setPositiveButton("?????????") { _: DialogInterface, _: Int ->
                                        Database.getReference("rooms/${room.rid}/users/${uid}")
                                            .removeValue()
                                        Database.getReference("users/$uid/teams").get()
                                            .addOnSuccessListener { teams ->
                                                teams.children.forEach {
                                                    //Toast.makeText(this@TeamActivity, "${it.key}", Toast.LENGTH_SHORT).show()
                                                    Database.getReference("teams/${it.key}/members/$uid")
                                                        .removeValue()
                                                }
                                            }

                                        Database.getReference("users/$uid/teams").removeValue()

                                        start<RoomListActivity> {
                                            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        }

                                    }
                                    .setNeutralButton("??????", null)
                                    .show()


                            }


                        }

                    }


                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        Database.getReference("rooms/${room.rid}/manager").get().addOnSuccessListener {
            var readyButton = findViewById<Button>(R.id.readyButton)
            when (it.value?.equals(uid)) {
                true -> {
                    readyButton.text = "??????"
                }
                false -> {
                    readyButton.text = "??????"
                }
            }
        }

        refreshRoomList(true)
    }

    private fun gameStart() {
        Database.getReference("users/$uid/readyState").setValue(true)

        val users = Database.getReference("rooms/${room.rid}/users").get()
        for (userID in listOf(users)) {
            //Toast.makeText(this, userID.toString(), Toast.LENGTH_SHORT).show()
            if (Database.getReference("users/${userID}/readyState").equals(false)) {
                Toast.makeText(this, "?????? ??????????????? ?????? ??????????????? ????????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val intent = Intent(this, com.bback.bbackdo.UnityPlayerActivity::class.java)
        intent.putExtra("mode", 1)
        startActivity(intent)

    }

    private fun gameReady() {
        var readyButton = findViewById<Button>(R.id.readyButton)
        Database.getReference("users/$uid/readyState").get().addOnSuccessListener {
            when (it.value) {
                true -> {
                    Database.getReference("users/$uid/readyState").setValue(false)
                    readyButton.text = "??????"

                }
                false -> {
                    Database.getReference("users/$uid/readyState").setValue(true)
                    readyButton.text = "?????? ??????"
                }
            }

            adapter.notifyDataSetChanged()
        }
    }

    private fun refreshRoomList(refreshing: Boolean) {
        bind.swipeRefreshLayout.setRefreshing(refreshing)
        //Toast.makeText(this@TeamActivity, "${dataList}", Toast.LENGTH_SHORT).show()
        Database.getReference("teams").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { teams ->
                    val team = teams.getValue<Team>()
                    var check = true
                    dataList.map {
                        if (it.tid == team?.tid)
                            check = false
                    }
                    if (team?.tid in teamList && check) {
                        //Toast.makeText(this@TeamActivity, "${team.getValue<Team>()}, ${dataList}", Toast.LENGTH_SHORT).show()
                        dataList.add(team!!)
                        adapter.notifyDataSetChanged()
                        //adapter.notifyItemInserted(dataList.lastIndex)
                    }
                    if (teams.getValue<Team>()?.tid in teamList && !check) {
                        //Toast.makeText(this@TeamActivity, "???????????????", Toast.LENGTH_LONG).show()
                        adapter.notifyDataSetChanged()
                    }


                }
                bind.swipeRefreshLayout.setRefreshing(false)

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }

    override fun onBackPressed() {
        with(bind) {
            val exitbind = DialogExitBinding.inflate(layoutInflater)
            with(exitbind) {
                Database.getReference("rooms/${room.rid}").get().addOnSuccessListener {
                    //Toast.makeText(this@TeamActivity, "${it.value}", Toast.LENGTH_SHORT).show()
                    if (it.getValue<Room>()?.manager == uid) {
                        alertTitleTextView.text = "????????? ????????? ?????? ???????????????."
                        AlertDialog.Builder(this@TeamActivity, R.style.MyDialogTheme)
                            .setView(root)
                            .setPositiveButton("?????????") { _: DialogInterface, _: Int ->
                                Database.getReference("rooms/${room.rid}/teams").get()
                                    .addOnSuccessListener { teams ->
                                        teams.children.forEach { team ->
                                            val tid = team.key
                                            //Toast.makeText(this@TeamActivity, tid.toString(), Toast.LENGTH_SHORT).show()
                                            Database.getReference("teams/$tid")
                                                .removeValue()

                                        }
                                    }

                                Database.getReference("rooms/${room.rid}").removeValue()
                                finish()
                                start<RoomListActivity> {
                                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                }

                            }
                            .setNeutralButton("??????", null)
                            .show()

                    } else {
                        alertTitleTextView.text = "??????????????????????"

                        AlertDialog.Builder(this@TeamActivity, R.style.MyDialogTheme)
                            .setView(root)
                            .setPositiveButton("?????????") { _: DialogInterface, _: Int ->
                                Database.getReference("rooms/${room.rid}/users/${uid}")
                                    .removeValue()
                                Database.getReference("users/$uid/teams").get()
                                    .addOnSuccessListener { teams ->
                                        teams.children.forEach {
                                            //Toast.makeText(this@TeamActivity, "${it.key}", Toast.LENGTH_SHORT).show()
                                            Database.getReference("teams/${it.key}/members/$uid")
                                                .removeValue()
                                        }
                                    }

                                Database.getReference("users/$uid/teams").removeValue()

                                start<RoomListActivity> {
                                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                }

                            }
                            .setNeutralButton("??????", null)
                            .show()


                    }


                }

            }



        }

    }


    private val teamEventListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            adapter.notifyDataSetChanged()
            //adapter.notifyItemInserted(dataList.lastIndex)

        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {


        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val tid = snapshot.key!!
            teamList.remove(tid)
            dataList.remove(snapshot.getValue<Team>())
            Database.getReference("teams/${tid}").removeValue()
            Database.getReference("users/$uid/teams/${tid}").removeValue()
            adapter.notifyDataSetChanged()


        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onCancelled(error: DatabaseError) {

        }

    }
}


