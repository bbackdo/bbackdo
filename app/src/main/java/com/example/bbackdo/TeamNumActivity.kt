package com.example.bbackdo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class TeamNumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_num)

        val editTeamNumSolo = findViewById<EditText>(R.id.editTeamNumSolo)
        val editNumberSolo = findViewById<EditText>(R.id.editNumberSolo)
        val editPenaltySolo1 = findViewById<EditText>(R.id.editPenaltySolo1)
        val editPenaltySolo2 = findViewById<EditText>(R.id.editPenaltySolo2)
        val editPenaltySolo3 = findViewById<EditText>(R.id.editPenaltySolo3)
        val editPenaltySolo4 = findViewById<EditText>(R.id.editPenaltySolo4)
        val editPenaltySolo5 = findViewById<EditText>(R.id.editPenaltySolo5)
        val buttonFinish = findViewById<Button>(R.id.buttonFinish)
        val buttonCancel = findViewById<Button>(R.id.buttonCancel)

        //editText 선택시, hint 제거
        editTeamNumSolo.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    editTeamNumSolo.hint = ""
                }
            }
        editNumberSolo.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    editNumberSolo.hint = ""
                }
            }
        editPenaltySolo1.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    editPenaltySolo1.hint = ""
                }
            }
        editPenaltySolo2.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    editPenaltySolo2.hint = ""
                }
            }
        editPenaltySolo3.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    editPenaltySolo3.hint = ""
                }
            }
        editPenaltySolo4.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    editPenaltySolo4.hint = ""
                }
            }
        editPenaltySolo5.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    editPenaltySolo5.hint = ""
                }
            }

        buttonFinish.setOnClickListener {
            if (editTeamNumSolo.text.isEmpty() || editNumberSolo.text.isEmpty() || editPenaltySolo1.text.isEmpty() || editPenaltySolo2.text.isEmpty() || editPenaltySolo3.text.isEmpty() || editPenaltySolo4.text.isEmpty() || editPenaltySolo5.text.isEmpty()) {
                Toast.makeText(this, "설정이 완료되지 않았습니다", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, GameActivity::class.java)
                //intent.putExtra("teamNum", count)
                startActivity(intent)
            }

        }

        buttonCancel.setOnClickListener {
            finish()
        }
    }
}