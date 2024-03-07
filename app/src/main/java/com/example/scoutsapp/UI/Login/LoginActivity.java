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
import com.example.scoutsapp.UI.Start.StartActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ModuleLogin moduleLogin;

    private EditText etEmail, etPassword;
    private Button btnLogin, toStart;
    private TextView tvForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        moduleLogin = new ModuleLogin(this);



        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        toStart = findViewById(R.id.btnToStart);
        tvForgot = findViewById(R.id.tvForgot);

        btnLogin.setOnClickListener(this);
        toStart.setOnClickListener(this);
        tvForgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnLogin)
        {
            long id = moduleLogin.userLogin(etEmail.getText().toString(), etPassword.getText().toString());
            if (id == -1)
            {
                Toast.makeText(this, "Email or Password are incorrect", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("email", etEmail.getText().toString());
                startActivity(intent);
            }
        }
        else if(view == toStart)
        {
            Intent intent = new Intent(LoginActivity.this, StartActivity.class);
            startActivity(intent);
        }
        else if(view == tvForgot)
        {
            //TODO
            Toast.makeText(this, "work in progress", Toast.LENGTH_SHORT).show();
        }
    }
}