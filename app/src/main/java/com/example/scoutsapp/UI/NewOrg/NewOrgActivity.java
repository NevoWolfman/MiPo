package com.example.scoutsapp.UI.NewOrg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.scoutsapp.R;
import com.example.scoutsapp.UI.Main.MainActivity;

public class NewOrgActivity extends AppCompatActivity {

    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_org);

        back = findViewById(R.id.btnBack1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewOrgActivity.this, MainActivity.class));
            }
        });
    }
}