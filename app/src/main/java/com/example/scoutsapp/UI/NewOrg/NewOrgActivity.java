package com.example.scoutsapp.UI.NewOrg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scoutsapp.R;
import com.example.scoutsapp.UI.Main.MainActivity;
import com.example.scoutsapp.UI.OrgManager.OrgManagerActivity;

public class NewOrgActivity extends AppCompatActivity implements View.OnClickListener {

    EditText orgName, orgPassword;
    Button back, create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_org);

        orgName = findViewById(R.id.etOrgName);
        orgPassword = findViewById(R.id.etOrgPassword);
        back = findViewById(R.id.btnBack1);
        create = findViewById(R.id.btnNewOrg);

        back.setOnClickListener(this);
        create.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == back)
        {
            startActivity(new Intent(NewOrgActivity.this, MainActivity.class));
        }
        else if(view == create)
        {
            String name = orgName.getText().toString();
            String password = orgPassword.getText().toString();

            //TODO: make better checks and error messages
            if(name.isEmpty() || password.isEmpty())
            {
                Toast.makeText(this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent intent = new Intent(NewOrgActivity.this, OrgManagerActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        }
    }
}