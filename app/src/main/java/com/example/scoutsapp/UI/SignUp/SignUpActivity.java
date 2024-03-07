package com.example.scoutsapp.UI.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scoutsapp.DB.SQLiteHelper;
import com.example.scoutsapp.R;
import com.example.scoutsapp.UI.Login.LoginActivity;
import com.example.scoutsapp.UI.Login.ModuleLogin;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private ModuleSignUp moduleSignUp;
    private EditText etEmail, etPassword;
    private Button btnSignUp, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        moduleSignUp = new ModuleSignUp(this);

        etEmail = findViewById(R.id.etEmail_sign);
        etPassword = findViewById(R.id.etPassword_sign);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnBack = findViewById(R.id.btnBack);
        btnSignUp.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnSignUp)
        {
            ModuleSignUp.Error error = moduleSignUp.userSignUp(etEmail.getText().toString(), etPassword.getText().toString());
            if(error == ModuleSignUp.Error.SUCCESS)
            {
                Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            else if (error == ModuleSignUp.Error.EMAIL_ALREADY)
            {
                Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            }
        }
        else if(view == btnBack)
        {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}