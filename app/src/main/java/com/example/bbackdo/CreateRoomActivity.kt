package com.example.bbackdo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bbackdo.databinding.ActivityCreateRoomBinding
import com.example.bbackdo.lib.Authentication
import com.example.bbackdo.lib.Database
import com.example.bbackdo.dto.Room
import com.google.firebase.database.ServerValue
import splitties.activities.start

class CreateRoomActivity : AppCompatActivity() {

    private val bind by lazy{
        ActivityCreateRoomBinding.inflate(layoutInflater)
    }

    private var penalty = arrayListOf(0,0,0,0,0)

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
                        finish()
                    }
                    titleEditText.text.toString() == "" -> {
                        Toast.makeText(this@CreateRoomActivity, "제목 입력 부탁", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    teamNumEditText2.text.toString() == "" -> {
                        Toast.makeText(this@CreateRoomActivity, "제목 입력 부탁", Toast.LENGTH_SHORT).show()
                        finish()
                    }


                    else->{
                        val memberNum = Integer.parseInt(memberNumEditText.text.toString())
                        val title = titleEditText.text.toString()

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

                        var room = Room(uid, memberNum, pw, penalty, rid, Integer.parseInt(memberNumEditText.text.toString()),
                            title, hashMapOf(uid to true), hashMapOf(uid to true))

                        //Toast.makeText(this@CreateRoomActivity, "$room", Toast.LENGTH_LONG).show()


                        val updates = hashMapOf(
                            "rooms/$rid" to room,
                            "users/$uid/rooms/$rid" to ServerValue.TIMESTAMP
                        )
                        Database.getReference("").updateChildren(updates).addOnSuccessListener {
                            Toast.makeText(this@CreateRoomActivity, "방 생성 완료", Toast.LENGTH_SHORT)
                                .show()
                        }
                        finish()

                    }
                }

                start<TeamActivity>{
                    flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                }

            }

        }


    }
}