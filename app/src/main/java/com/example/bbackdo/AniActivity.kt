package com.example.bbackdo

import android.app.Activity
import java.util.Random
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.bbackdo.databinding.ActivityAniBinding

class AniActivity : AppCompatActivity() {
    private val bind by lazy{
        ActivityAniBinding.inflate(layoutInflater)
    }

    var imgYut = arrayListOf<Int>(R.drawable.yut_0,R.drawable.yut_1)
    var Yut = arrayListOf<String>("윷","걸","개","도","모")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ani)

        with(bind){
            rollAniButton.setOnClickListener {
                val random = Random()
                val num = random.nextInt(10)
                val n1 = 1-num/6
                val n2 = 1-num/6
                val n3 = 1-num/6
                val n4 = 1-num/6
                val n = n1 + n2 + n3 + n4

                imageView1.setImageResource(imgYut[n1])
                imageView2.setImageResource(imgYut[n2])
                imageView3.setImageResource(imgYut[n3])
                imageView4.setImageResource(imgYut[n4])

                yutEditText.setText(Yut[n])

            }
        }
    }
}