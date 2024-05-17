package net.nevowolfman.mipo.UI.OrgManager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import net.nevowolfman.mipo.Model.Member;
import net.nevowolfman.mipo.Model.Organization;
import net.nevowolfman.mipo.Model.Team;
import net.nevowolfman.mipo.R;

import java.util.ArrayList;
import java.util.List;

public class OrgManagerActivity extends AppCompatActivity {

    private TextView tvTeamName;
    private Organization org;
    public Team current_team;
    private MemberFragment list;
    private ButtonsFragment buttons;
    private AddMemberDialog addMemberDialog;
    private AddTeamDialog addTeamDialog;
    private List<Team> allTeams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        org = new Organization(getIntent().getStringExtra("name"));
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

    public void showAddMemberDialog()
    {
        addMemberDialog = new AddMemberDialog(this);
        addMemberDialog.show(getSupportFragmentManager(), "addMember");
    }

    public void showAddTeamDialog()
    {
        addTeamDialog = new AddTeamDialog(this);
        addTeamDialog.show(getSupportFragmentManager(), "addTeam");
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
    public Team getTeam(String name) {
        for (int i = 0; i < allTeams.size(); i++) {
            Team team = allTeams.get(i);
            if(team.getName().equals(name))
            {
                return team;
            }
        }
        return null;
    }

    public List<Team> getAllTeams() {
        return allTeams;
    }

    public void setAllTeams(List<Team> allTeams) {
        this.allTeams = allTeams;
    }

    public Team getCurrent_team() {
        return current_team;
    }

    public void setCurrent_team(Team current_team) {
        this.current_team = current_team;
    }
}