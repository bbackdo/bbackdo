package com.example.bbackdo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bbackdo.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility

import splitties.activities.start

class MainActivity : AppCompatActivity() {

    private val main by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(main.root)

        main.testButton.setOnClickListener {
            start<TeamNumActivity>()
        }

        main.kakaoLoginBtn.setOnClickListener {
            Toast.makeText(this@MainActivity, "준비중인 서비스 입니다", Toast.LENGTH_SHORT).show()
            //start<LoginActivity>()
        }

    }
}