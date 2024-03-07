package com.example.scoutsapp.UI.NewOrg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.scoutsapp.R;
import com.example.scoutsapp.UI.Start.StartActivity;

public class NewOrgActivity extends AppCompatActivity {
    ModuleNewOrg module;

    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_org);
        module = new ModuleNewOrg(this);

        back = findViewById(R.id.btnBack1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewOrgActivity.this, StartActivity.class));
            }
        });
    }
}