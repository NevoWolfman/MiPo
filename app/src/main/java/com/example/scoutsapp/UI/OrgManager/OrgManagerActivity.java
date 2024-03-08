package com.example.scoutsapp.UI.OrgManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.scoutsapp.Model.Member;
import com.example.scoutsapp.Model.Organization;
import com.example.scoutsapp.Model.Team;
import com.example.scoutsapp.R;
import com.example.scoutsapp.UI.Main.MainActivity;
import com.example.scoutsapp.UI.NewOrg.NewOrgActivity;

public class OrgManagerActivity extends AppCompatActivity {

    private Organization org;
    private Team current_team;
    private MemberFragment list;
    private ButtonsFragment buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        org = new Organization(getIntent().getStringExtra("name"), getIntent().getStringExtra("password"));
        current_team = org.getAdmins();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_manager);

        list = (MemberFragment) getSupportFragmentManager().findFragmentById(R.id.Org_Create_list);
        buttons = (ButtonsFragment) getSupportFragmentManager().findFragmentById(R.id.Org_Create_Buttons);
    }

    public Organization getOrg() {
        return org;
    }

    public Team getCurrent_team() {
        return current_team;
    }

    public void setCurrent_team(Team current_team) {
        this.current_team = current_team;
    }

    public void addMember(Member member)
    {
        current_team.getMembers().add(member);
        list.notifyItemInserted(current_team.getMembers().size()-1);
    }
}