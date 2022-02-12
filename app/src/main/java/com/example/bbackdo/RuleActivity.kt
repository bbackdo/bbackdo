package com.example.bbackdo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bbackdo.databinding.ActivityRuleBinding

class RuleActivity : AppCompatActivity() {

    private val bind by lazy {
        ActivityRuleBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(bind){
            setContentView(root)

            backbutton.setOnClickListener {
                finish()
            }
        }


    }
}