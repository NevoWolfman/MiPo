package com.example.scoutsapp.UI.Start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.scoutsapp.R;
import com.example.scoutsapp.UI.Login.LoginActivity;
import com.example.scoutsapp.UI.NewOrg.NewOrgActivity;
import com.example.scoutsapp.UI.SignUp.SignUpActivity;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    Button toLogin, toSignUp, toNewOrg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        toLogin = findViewById(R.id.btnToLogin);
        toSignUp = findViewById(R.id.btnToSignUp);
        toNewOrg = findViewById(R.id.btnToNewOrg);

        toLogin.setOnClickListener(this);
        toSignUp.setOnClickListener(this);
        toNewOrg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if(v == toLogin)
        {
            intent.setClass(StartActivity.this, LoginActivity.class);
        }
        else if (v == toSignUp)
        {
            intent.setClass(StartActivity.this, SignUpActivity.class);
        }
        else if (v == toNewOrg)
        {
            intent.setClass(StartActivity.this, NewOrgActivity.class);
        }
        else{
            return;
        }
        startActivity(intent);
    }
}