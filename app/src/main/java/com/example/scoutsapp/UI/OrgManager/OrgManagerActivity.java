package com.example.scoutsapp.UI.OrgManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scoutsapp.Model.Member;
import com.example.scoutsapp.Model.Organization;
import com.example.scoutsapp.Model.Team;
import com.example.scoutsapp.R;
import com.example.scoutsapp.UI.Main.MainActivity;
import com.example.scoutsapp.UI.NewOrg.NewOrgActivity;

import java.util.ArrayList;
import java.util.List;

public class OrgManagerActivity extends AppCompatActivity {

    private TextView tvTeamName;
    private Organization org;
    private Team current_team;
    private MemberFragment list;
    private ButtonsFragment buttons;
    private AddMemberDialog addMemberDialog;
    private AddTeamDialog addTeamDialog;
    private final String addMemberDialog_TAG = "addMember";
    private final String addTeamDialog_TAG = "addTeam";
    private List<Team> allTeams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        org = new Organization(getIntent().getStringExtra("name"), getIntent().getStringExtra("password"));
        current_team = org.getAdmins();
        allTeams = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_manager);

        ((TextView)findViewById(R.id.tvOrgName)).setText(org.getName());
        tvTeamName = findViewById(R.id.tvTeamName);
        tvTeamName.setText(org.getAdmins().getName());

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

    public void showAddMemberDialog()
    {
        addMemberDialog = new AddMemberDialog();
        addMemberDialog.show(getSupportFragmentManager(), addMemberDialog_TAG);
    }

    public void showAddTeamDialog()
    {
        addTeamDialog = new AddTeamDialog();
        addTeamDialog.show(getSupportFragmentManager(), addTeamDialog_TAG);
    }

    public void addMember(Member member)
    {
        int pos = current_team.getMembers().size();
        current_team.getMembers().add(member);
        list.notifyMemberInserted(pos);
    }

    public void addTeam(Team team){
        allTeams.add(team);
    }

    public List<Team> getAllTeams() {
        return allTeams;
    }

    public void setAllTeams(List<Team> allTeams) {
        this.allTeams = allTeams;
    }
}