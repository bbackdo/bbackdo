package com.example.bbackdo

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
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
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
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
                bind.logoImage.isEnabled = true
                bind.googleLoginButton.isEnabled = false
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
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)


        with(bind){

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this@LoginActivity, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this@LoginActivity, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this@LoginActivity, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this@LoginActivity, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this@LoginActivity, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this@LoginActivity, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this@LoginActivity, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this@LoginActivity, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this@LoginActivity, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {
                Toast.makeText(this@LoginActivity, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, LoginActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))

            }
        }

            if (Authentication.isLoggedIn()) {
                entranceButton.visibility = View.VISIBLE
                logoutButton.visibility = View.VISIBLE
                kakaoLoginButton.visibility= View.INVISIBLE
                googleLoginButton.visibility= View.INVISIBLE
            }else if(!Authentication.isLoggedIn()){
                entranceButton.visibility = View.INVISIBLE
                logoutButton.visibility = View.INVISIBLE
            }
            entranceButton.setOnClickListener {
                if (!Authentication.isLoggedIn()) {
                    titleImage.isEnabled = false
                } else {
                    start<RoomListActivity>()
                }
            }
            googleLoginButton.setOnClickListener {
                login()
                entranceButton.visibility = View.VISIBLE
                logoutButton.visibility = View.VISIBLE
                kakaoLoginButton.visibility= View.INVISIBLE
                googleLoginButton.visibility= View.INVISIBLE
            }
            kakaoLoginButton.setOnClickListener {
                if(UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)){
                    UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity, callback = callback)

                }else{
                    UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = callback)
                }
                entranceButton.visibility = View.VISIBLE
                logoutButton.visibility = View.VISIBLE
                kakaoLoginButton.visibility= View.INVISIBLE
                googleLoginButton.visibility= View.INVISIBLE
            }
            logoutButton.setOnClickListener {
                kakaoLoginButton.visibility= View.VISIBLE
                googleLoginButton.visibility= View.VISIBLE
                logout()
                UserApiClient.instance.logout { error ->
                    if (error != null) {
                        Toast.makeText(this@LoginActivity, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(this@LoginActivity, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                    }
                    finish()
            }


            }
        }
    }
}