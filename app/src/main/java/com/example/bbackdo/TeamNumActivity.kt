package com.example.bbackdo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class TeamNumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_num)

        val textNum = findViewById<TextView>(R.id.editNum)
       // val buttonSub = findViewById<Button>(R.id.buttonSub)
       // val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonFinish = findViewById<Button>(R.id.buttonFinish)

        var count = 2
        textNum.text = count.toString()
/*
        buttonSub.setOnClickListener {
            if(count <= 2)
                Toast.makeText(this, "최소 2팀은 있어야합니다.", Toast.LENGTH_SHORT).show()
            else textNum.text = (--count).toString()
        }

        buttonAdd.setOnClickListener {
            if(count >= 4)
                Toast.makeText(this, "최대 4팀을 초과할 수 없습니다.", Toast.LENGTH_SHORT).show()
            else textNum.text = (++count).toString()
        }

 */

        buttonFinish.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("teamNum", count)
            startActivity(intent)
        }
    }
}