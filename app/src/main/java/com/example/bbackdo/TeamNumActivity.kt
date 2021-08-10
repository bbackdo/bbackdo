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

        val editTeamNumSolo = findViewById<EditText>(R.id.editTeamNumSolo)
        val editPenaltySolo1 = findViewById<EditText>(R.id.editPenaltySolo1)
        val editPenaltySolo2 = findViewById<EditText>(R.id.editPenaltySolo2)
        val editPenaltySolo3 = findViewById<EditText>(R.id.editPenaltySolo3)
        val editPenaltySolo4 = findViewById<EditText>(R.id.editPenaltySolo4)
        val editPenaltySolo5 = findViewById<EditText>(R.id.editPenaltySolo5)
        val buttonFinish = findViewById<Button>(R.id.buttonFinish)
        val buttonCancel = findViewById<Button>(R.id.buttonCancel)

        buttonFinish.setOnClickListener {
            if (editTeamNumSolo.text.isEmpty() || editPenaltySolo1.text.isEmpty() || editPenaltySolo2.text.isEmpty() || editPenaltySolo3.text.isEmpty() || editPenaltySolo4.text.isEmpty() || editPenaltySolo5.text.isEmpty()) {
                Toast.makeText(this, "설정이 완료되지 않았습니다", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, GameActivity::class.java)
                //intent.putExtra("teamNum", count)
                startActivity(intent)
            }
        }

        buttonCancel.setOnClickListener {

        }
    }
}