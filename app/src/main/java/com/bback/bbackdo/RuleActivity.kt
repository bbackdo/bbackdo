package com.bback.bbackdo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bback.bbackdo.databinding.ActivityRuleBinding

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