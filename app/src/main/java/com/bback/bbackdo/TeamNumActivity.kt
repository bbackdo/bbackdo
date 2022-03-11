package com.bback.bbackdo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bback.bbackdo.databinding.ActivityTeamNumBinding
import com.bback.bbackdo.databinding.DialogYutBinding
import splitties.activities.start

class TeamNumActivity : AppCompatActivity() {

    private val bind by lazy {
        ActivityTeamNumBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(bind) {
            setContentView(root)

            //editText 선택시, hint 제거
            editTeamNumSolo.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        editTeamNumSolo.hint = ""
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

                if (editTeamNumSolo.text.isEmpty() || editPenaltySolo1.text.isEmpty() || editPenaltySolo2.text.isEmpty() || editPenaltySolo3.text.isEmpty() || editPenaltySolo4.text.isEmpty() || editPenaltySolo5.text.isEmpty()) {
                    Toast.makeText(this@TeamNumActivity, "설정이 완료되지 않았습니다", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    val intent = Intent(this@TeamNumActivity, UnityPlayerActivity::class.java)
                    intent.putExtra("mode", 0)
                    intent.putExtra("TeamNum", editTeamNumSolo.text.toString())
                    intent.putExtra("Penalty1", editPenaltySolo1.text.toString())
                    intent.putExtra("Penalty2", editPenaltySolo2.text.toString())
                    intent.putExtra("Penalty3", editPenaltySolo3.text.toString())
                    intent.putExtra("Penalty4", editPenaltySolo4.text.toString())
                    intent.putExtra("Penalty5", editPenaltySolo5.text.toString())
                    
                    startActivity(intent)

                }
            }

            buttonCancel.setOnClickListener {
                finish()
            }


            checkimgSolo.setOnClickListener {
                val builder = DialogYutBinding.inflate(layoutInflater)
                with(builder){
                    AlertDialog.Builder(this@TeamNumActivity, R.style.MyDialogTheme)
                        .setView(root)
                        .setPositiveButton("확인", null)
                        .show()
                }
            }
            checkDevelop.setOnClickListener {
                start<DeveloperActivity>()
            }

            rulebutton.setOnClickListener {
                start<RuleActivity>()
            }
        }
    }
}