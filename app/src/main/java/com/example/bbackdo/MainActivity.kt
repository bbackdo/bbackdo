package com.example.bbackdo

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bbackdo.databinding.ActivityMainBinding
import splitties.activities.start

class MainActivity : AppCompatActivity() {

    private val main by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(main.root)


        main.testButton.setOnClickListener {
            start<LoginActivity>()
        }

        main.loginBtn.setOnClickListener {
            start<LoginActivity>()
        }

    }
}