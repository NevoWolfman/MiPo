package com.example.scoutsapp.UI.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scoutsapp.R;
import com.example.scoutsapp.Repository.Repository;
import com.example.scoutsapp.Repository.UserModel;
import com.example.scoutsapp.UI.Login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvEmail, tvPassword;
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvEmail = findViewById(R.id.tvEmail);
        tvPassword = findViewById(R.id.tvPassword);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        Repository repository = new Repository(this);
        String email = getIntent().getStringExtra("email");
        if(email != null)
        {
            UserModel user = repository.getUserByEmail(email);
            if(user != null)
            {
                tvEmail.setText(email);
                tvPassword.setText(user.getPassword());
            }
        }


    }
}