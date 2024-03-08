package com.example.scoutsapp.UI.OrgManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.scoutsapp.Model.Organization;
import com.example.scoutsapp.R;
import com.example.scoutsapp.UI.Main.MainActivity;

public class OrgManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private Organization org;
    Button addTeam, addMember, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_manager);

        org = new Organization(getIntent().getStringExtra("name"), getIntent().getStringExtra("password"));

        addTeam = findViewById(R.id.btnAddTeam);
        addMember = findViewById(R.id.btnAddMember);
        back = findViewById(R.id.btnBack2);

        addTeam.setOnClickListener(this);
        addMember.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == addTeam)
        {

        }
        else if(view == addMember)
        {

        }
        else if(view == back)
        {
            startActivity(new Intent(OrgManagerActivity.this, MainActivity.class));
        }
    }
}