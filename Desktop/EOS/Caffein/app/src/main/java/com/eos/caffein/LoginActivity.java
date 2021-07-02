package com.eos.caffein;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eos.caffein.MainActivity;
import com.eos.caffein.R;


public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnRegister, btnKakaoLogin;
    private EditText editTextID, editTextPassword, etEmail;
    public static String token;

    private CheckBox checkBoxAutoLogin;
    private SharedPreferences settingPrefs, tokenPrefs;
    SharedPreferences.Editor settingEditor, tokenEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.button_login_login);
        btnRegister = findViewById(R.id.button_login_register);
        btnKakaoLogin = findViewById(R.id.button_login_kakao);
        editTextID = findViewById(R.id.editText_login_id);
        etEmail = findViewById(R.id.editText_login_id);
        editTextPassword = findViewById(R.id.editText_login_password);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}