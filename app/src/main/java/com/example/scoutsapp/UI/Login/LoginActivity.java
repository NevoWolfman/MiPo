package com.example.scoutsapp.UI.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scoutsapp.DB.SQLiteHelper;
import com.example.scoutsapp.R;
import com.example.scoutsapp.Repository.Repository;
import com.example.scoutsapp.UI.Main.MainActivity;
import com.example.scoutsapp.UI.SignUp.SignUpActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ModuleLogin moduleLogin;

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvSignUp, tvForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        moduleLogin = new ModuleLogin(this);



        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvForgot = findViewById(R.id.tvForgot);

        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvForgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnLogin)
        {
            if (moduleLogin.userLogin(etEmail.getText().toString(), etPassword.getText().toString()) == -1)
            {
                Toast.makeText(this, "Email or Password are incorrect", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        else if(view == tvSignUp)
        {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
        else if(view == tvForgot)
        {
            //TODO
            Toast.makeText(this, "work in progress", Toast.LENGTH_SHORT).show();
        }
    }
}