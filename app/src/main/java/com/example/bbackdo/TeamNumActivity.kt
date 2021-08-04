package com.example.bbackdo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class TeamNumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_num)

        val teamNum = findViewById<EditText>(R.id.teamNum)
        val buttonSub = findViewById<Button>(R.id.buttonSub)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonFinish = findViewById<Button>(R.id.buttonFinish)

        var count = 2
        teamNum.setText(count.toString())

        buttonSub.setOnClickListener {
            teamNum.setText((--count).toString())
        }

        buttonAdd.setOnClickListener {
            teamNum.setText((++count).toString())
        }

        buttonFinish.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("teamNum", count)
            startActivity(intent)
        }
    }
}