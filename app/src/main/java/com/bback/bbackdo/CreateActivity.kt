package com.bback.bbackdo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bback.bbackdo.databinding.ActivityCreateRoomBinding
import com.bback.bbackdo.lib.Authentication
import com.bback.bbackdo.lib.Database

class CreateActivity : AppCompatActivity() {
    private val bind by lazy{
        ActivityCreateRoomBinding.inflate(layoutInflater)
    }

    private val penalties = arrayListOf("")
    //penalty 어댑터 만들기

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        with(bind){
            setContentView(root)
            createRoomButton.setOnClickListener{
                val roomRef = Database.getReference("rooms").push()
                val uid = Authentication.uid!!
                val rid = roomRef.key
                val iter = penalties.iterator()
/*
                val room = Room(
                    rid,
                    titleEditText.text.toString(),
                    passwordEditText.text.toString(),
                    memberNumEditText.text as Int,
                    hashMapOf(
                        uid to true
                    ),
                    penalties.distinct()
                )

                val updates = hashMapOf(
                    "rooms/$rid" to room,
                    "users/$uid/rooms/$rid" to ServerValue.TIMESTAMP
                )

 */
            }
        }
    }

}