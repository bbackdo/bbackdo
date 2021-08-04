package com.example.bbackdo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bbackdo.databinding.ActivityAniBinding

class AniActivity : AppCompatActivity() {
    private val bind by lazy{
        ActivityAniBinding.inflate(layoutInflater)
    }

    var intYut = arrayListOf<Int>(R.drawable.,R.drawable.)
    var Yut = arrayListOf<String>("윷","걸","개","도","모")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ani)

        with(bind){
            .setImageResource()

        }

    }
}