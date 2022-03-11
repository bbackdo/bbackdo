package com.bback.bbackdo

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bback.bbackdo.databinding.ActivityCreateRoomBinding
import com.bback.bbackdo.databinding.DialogLoginBinding
import com.bback.bbackdo.databinding.DialogYutBinding
import com.bback.bbackdo.lib.Authentication
import com.bback.bbackdo.lib.Database
import com.bback.bbackdo.dto.Room
import com.bback.bbackdo.dto.Team
import com.bback.bbackdo.dto.Team.Companion.TEAM1
import com.bback.bbackdo.dto.Team.Companion.TEAM2
import com.bback.bbackdo.dto.Team.Companion.TEAM3
import com.bback.bbackdo.dto.Team.Companion.TEAM4
import com.google.firebase.database.ServerValue
import splitties.activities.start
import splitties.bundle.putExtras

class CreateRoomActivity : AppCompatActivity() {

    private val bind by lazy {
        ActivityCreateRoomBinding.inflate(layoutInflater)
    }

    private var penalty = arrayListOf(0, 0, 0, 0, 0)
    private var tids = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        with(bind) {
            setContentView(root)
            cancelButton.setOnClickListener {
                finish()
            }

            //editText 선택시, hint 제거
            memberNumEditText.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        memberNumEditText.hint = ""
                    }
                }
            teamNumEditText2.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        teamNumEditText2.hint = ""
                    }
                }
            mission1.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        mission1.hint = ""
                    }
                }
            mission2.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        mission2.hint = ""
                    }
                }
            mission3.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        mission3.hint = ""
                    }
                }
            mission4.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        mission4.hint = ""
                    }
                }
            mission5.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        mission5.hint = ""
                    }
                }


            createRoomButton.setOnClickListener {
                val roomRef = Database.getReference("rooms").push();
                val uid = Authentication.uid!!
                val rid = roomRef.key
                //Toast.makeText(this@CreateRoomActivity, memberNumEditText.text.toString(), Toast.LENGTH_SHORT).show()
                var pw = ""
                when {
                    memberNumEditText.text.toString() == "" -> {
                        Toast.makeText(this@CreateRoomActivity, "멤버수 입력 부탁", Toast.LENGTH_SHORT)
                            .show()
                        //finish()
                    }
                    titleEditText.text.toString() == "" -> {
                        Toast.makeText(this@CreateRoomActivity, "제목 입력 부탁", Toast.LENGTH_SHORT)
                            .show()
                        //finish()
                    }
                    teamNumEditText2.text.toString() == "" -> {
                        Toast.makeText(this@CreateRoomActivity, "팀수 입력 부탁", Toast.LENGTH_SHORT)
                            .show()
                        //finish()
                    }
                    memberNumEditText.text.toString().toInt() < teamNumEditText2.text.toString()
                        .toInt() || memberNumEditText.text.toString()
                        .toInt() % teamNumEditText2.text.toString().toInt() != 0 -> {
                        Toast.makeText(
                            this@CreateRoomActivity,
                            "인원 수는 팀 수의 배수가 되어야 합니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    teamNumEditText2.text.toString().toInt() > 4 || teamNumEditText2.text.toString().toInt() < 1
                    -> {
                        Toast.makeText(this@CreateRoomActivity, "팀 수는 1개 ~ 4개까지 가능합니다", Toast.LENGTH_SHORT).show()
                    }
                    memberNumEditText.text.toString().toInt() > 8 || memberNumEditText.text.toString().toInt() < 1
                    -> {
                        Toast.makeText(this@CreateRoomActivity, "인원 수는 1인 ~ 8인까지 가능합니다", Toast.LENGTH_SHORT).show()
                    }


                    else -> {
                        val memberNum = Integer.parseInt(memberNumEditText.text.toString())
                        val title = titleEditText.text.toString()
                        var teamNum = Integer.parseInt(teamNumEditText2.text.toString())
                        if (mission1.text.toString() != "")
                            penalty[0] = Integer.parseInt(mission1.text.toString())
                        if (mission2.text.toString() != "")
                            penalty[1] = Integer.parseInt(mission2.text.toString())
                        if (mission3.text.toString() != "")
                            penalty[2] = Integer.parseInt(mission3.text.toString())
                        if (mission4.text.toString() != "")
                            penalty[3] = Integer.parseInt(mission4.text.toString())
                        if (mission5.text.toString() != "")
                            penalty[4] = Integer.parseInt(mission5.text.toString())
                        if (passwordEditText.text.toString() != "")
                            pw = passwordEditText.text.toString()


                        var room = Room(
                            uid, memberNum, pw, penalty, rid, teamNum,
                            title, hashMapOf(uid to true)
                        )
                        var team = Team()

                        val updates = hashMapOf(
                            "rooms/$rid" to room
                        )
                        Database.getReference("").updateChildren(updates as Map<String, Any>)
                            .addOnSuccessListener {
                            }

                        //Toast.makeText(this@CreateRoomActivity, "$teamNum", Toast.LENGTH_LONG).show()
                        for (i: Int in 1..teamNum) {
                            var teamRef = Database.getReference("teams").push()
                            var tid = teamRef.key.toString()
                            tids.add(tid)
                            var teamName = TEAM1
                            when (i) {
                                2 -> {
                                    teamName = TEAM2
                                }
                                3 -> {
                                    teamName = TEAM3
                                }
                                4 -> {
                                    teamName = TEAM4
                                }
                            }
                            team = Team(tid, name = teamName, rid = rid)

                            var update = hashMapOf(
                                "teams/$tid" to team,
                                "rooms/$rid/teams/$tid" to false
                            )
                            Database.getReference("").updateChildren(update).addOnSuccessListener {

                            }


                        }
                        var updateTeam = hashMapOf(
                            "teams/${tids[0]}/members/$uid" to false,
                            "users/${uid}/teams/${tids[0]}" to false,
                            "users/$uid/readyState" to false
                        )

                        Database.getReference("").updateChildren(updateTeam as Map<String, Any>)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this@CreateRoomActivity,
                                    "방 생성 완료",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        finish()

                        start<com.bback.bbackdo.TeamActivity> {
                            putExtras(com.bback.bbackdo.TeamActivity.Extras) {
                                com.bback.bbackdo.TeamActivity.Extras.room = room

                            }
                            flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                        }


                    }
                }


            }

            checkimg.setOnClickListener {
                val builder = AlertDialog.Builder(this@CreateRoomActivity,
                    R.style.MyDialogTheme
                )
                val builderItem = DialogYutBinding.inflate(layoutInflater)
                with(builder){
                    setView(builderItem.root)
                    setPositiveButton("확인", null)
                    show()


                }

            }
        }

    }



    }
