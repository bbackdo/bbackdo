package com.example.bbackdo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bbackdo.databinding.ActivityCreateRoomBinding
import com.example.bbackdo.lib.Authentication
import com.example.bbackdo.lib.Database
import com.example.bbackdo.dto.Room
import com.example.bbackdo.dto.Team
import com.example.bbackdo.dto.Team.Companion.TEAM1
import com.example.bbackdo.dto.Team.Companion.TEAM2
import com.example.bbackdo.dto.Team.Companion.TEAM3
import com.example.bbackdo.dto.Team.Companion.TEAM4
import com.google.firebase.database.ServerValue
import splitties.activities.start
import splitties.bundle.putExtras

class CreateRoomActivity : AppCompatActivity() {

    private val bind by lazy{
        ActivityCreateRoomBinding.inflate(layoutInflater)
    }

    private var penalty = arrayListOf(0,0,0,0,0)
    private var tids = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        with(bind){
            setContentView(root)
            cancelButton.setOnClickListener {
                finish()
            }

            createRoomButton.setOnClickListener {
                val roomRef = Database.getReference("rooms").push();
                val uid = Authentication.uid!!
                val rid = roomRef.key
                //Toast.makeText(this@CreateRoomActivity, memberNumEditText.text.toString(), Toast.LENGTH_SHORT).show()
                var pw = ""
                when {
                    memberNumEditText.text.toString() == "" -> {
                        Toast.makeText(this@CreateRoomActivity, "멤버수 입력 부탁", Toast.LENGTH_SHORT).show()
                        //finish()
                    }
                    titleEditText.text.toString() == "" -> {
                        Toast.makeText(this@CreateRoomActivity, "제목 입력 부탁", Toast.LENGTH_SHORT).show()
                        //finish()
                    }
                    teamNumEditText2.text.toString() == "" -> {
                        Toast.makeText(this@CreateRoomActivity, "제목 입력 부탁", Toast.LENGTH_SHORT).show()
                        //finish()
                    }


                    else->{
                        val memberNum = Integer.parseInt(memberNumEditText.text.toString())
                        val title = titleEditText.text.toString()
                        var teamNum = Integer.parseInt(teamNumEditText2.text.toString())
                        penalty.clear()
                        if(mission1.text.toString() != "")
                            penalty.add(0,Integer.parseInt(mission1.text.toString()))
                        if(mission2.text.toString() != "")
                            penalty.add(1,Integer.parseInt(mission2.text.toString()))
                        if(mission3.text.toString() != "")
                            penalty.add(2,Integer.parseInt(mission3.text.toString()))
                        if(mission4.text.toString() != "")
                            penalty.add(3,Integer.parseInt(mission4.text.toString()))
                        if(mission5.text.toString() != "")
                            penalty.add(4,Integer.parseInt(mission5.text.toString()))
                        if(passwordEditText.text.toString() != "")
                            pw = passwordEditText.text.toString()


                        var room = Room(uid, memberNum, pw, penalty, rid, teamNum,
                            title, hashMapOf(uid to true))
                        var team = Team()

                        val updates = hashMapOf(
                            "rooms/$rid" to room
                        )
                        Database.getReference("").updateChildren(updates as Map<String, Any>).addOnSuccessListener {
                        }

                        Toast.makeText(this@CreateRoomActivity, "$teamNum", Toast.LENGTH_LONG).show()
                        for (i:Int in 1..teamNum){
                            var teamRef = Database.getReference("teams").push()
                            var tid = teamRef.key.toString()
                            tids.add(tid)
                            var teamName = TEAM1
                            when(i){
                                2->{teamName = TEAM2}
                                3->{teamName = TEAM3}
                                4->{teamName = TEAM4}
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
                            "users/${uid}/teams/${tids[0]}" to false
                        )

                        Database.getReference("").updateChildren(updateTeam as Map<String, Any>).addOnSuccessListener {
                            Toast.makeText(this@CreateRoomActivity, "방 생성 완료 ${room.rid}", Toast.LENGTH_SHORT)
                                .show()
                        }
                        finish()

                        start<TeamActivity>{
                            putExtras(TeamActivity.Extras){
                                this.room = room

                            }
                            flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                        }



                    }
                }



            }

        }


    }
}