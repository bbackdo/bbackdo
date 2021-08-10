package com.example.bbackdo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bbackdo.databinding.ActivityCreateRoomBinding

class CreateRoomActivity : AppCompatActivity() {

    private val bind by lazy{
        ActivityCreateRoomBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(bind){
            setContentView(root)
            cancelButton.setOnClickListener {  }
            createRoomButton.setOnClickListener { }
        }


    }
}