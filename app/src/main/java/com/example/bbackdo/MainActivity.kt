package com.example.bbackdo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import com.example.bbackdo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val testButton = findViewById<Button>(R.id.testButton)

        testButton.setOnClickListener {
            val intent = Intent(this, TeamNumActivity::class.java)
            startActivity(intent)
        }
    }
}