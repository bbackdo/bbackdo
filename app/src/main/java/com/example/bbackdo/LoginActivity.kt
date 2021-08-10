package com.example.bbackdo

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bbackdo.databinding.ActivityLoginBinding
import com.example.bbackdo.databinding.DialogLoginBinding
import com.example.bbackdo.dto.User
import com.example.bbackdo.lib.Authentication
import com.example.bbackdo.lib.Database
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import splitties.activities.start
import splitties.alertdialog.appcompat.*
import splitties.alertdialog.material.materialAlertDialog


class LoginActivity : AppCompatActivity() {
    private val bind by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    // 로그인 방식 (구글, 페이스북 등)
    private val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    // 로그인 결과 콜백 등록
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    // 로그인 인텐트
    private val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .build()

    // 로그인 결과 콜백 함수
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {


        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {

            val userReference = Database.getReference("users/${Authentication.uid}")
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var user = snapshot.getValue<User>()
                    // 신규 로그인
                    // nickname, win, lose, readyState, rooms
                    if (user == null) {
                        Toast.makeText(this@LoginActivity, "신규 로그인", Toast.LENGTH_SHORT).show()
                        val builder = AlertDialog.Builder(this@LoginActivity)
                        val builderItem = DialogLoginBinding.inflate(layoutInflater)
                        val editText = builderItem.alertEditText
                        with(builder) {
                            setTitle("닉네임을 입력하세요")
                            setView(builderItem.root)
                            setPositiveButton("OK") { _: DialogInterface, _: Int ->
                                if (editText.text != null) {
                                    val nickname = editText.text.toString()
                                    user = User(nickname)
                                    userReference.setValue(user)
                                    successLogin(user!!)
                                }
                            }
                            setNeutralButton("취소", null)
                            show()
                        }
                    } else {
                        successLogin(user!!)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Database.getReference("users/${Authentication.uid}").removeValue().addOnSuccessListener(object:
                        OnSuccessListener<Void> {
                        override fun onSuccess(p0: Void?) {
                            Toast.makeText(this@LoginActivity, "삭제 완료", Toast.LENGTH_SHORT).show()

                        }

                    })

                }

            })

        } else {
            login()

        }
    }

    private fun successLogin(user: User) {
        Authentication.user = user
        materialAlertDialog {
            title = user.nickname
            message = "로그인 성공"
            okButton {
                bind.logintEnterRoomBtn.isEnabled = true
                bind.loginBtn.isEnabled = false
            }
        }.show()
    }

    private fun login() {
        signInLauncher.launch(signInIntent)
    }

    private fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // 로그아웃 이후 작업
                bind.loginBtn.isEnabled = true
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        with(bind) {
            if (Authentication.isLoggedIn()) {
                logintEnterRoomBtn.isEnabled = true
            }
            logintEnterRoomBtn.setOnClickListener {
                if (!Authentication.isLoggedIn()) {
                    logintEnterRoomBtn.isEnabled = false
                } else {
                    start<RoomListActivity>()
                }
            }
            loginBtn.setOnClickListener {
                login()
            }
            logoutBtn.setOnClickListener {
                logintEnterRoomBtn.isEnabled = false
                logout()
            }

        }


    }
}