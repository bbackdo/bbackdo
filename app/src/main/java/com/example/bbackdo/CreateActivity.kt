package com.example.bbackdo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bbackdo.databinding.ActivityCreateRoomBinding
import com.example.bbackdo.lib.Authentication
import com.example.bbackdo.lib.Database
import com.example.bbackdo.dto.Room
import com.google.firebase.database.ServerValue

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
            }
        }
    }

}