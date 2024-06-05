package net.nevowolfman.mipo.UI.OrgEditor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import net.nevowolfman.mipo.Model.Member;
import net.nevowolfman.mipo.Model.Organization;
import net.nevowolfman.mipo.Model.Team;
import net.nevowolfman.mipo.R;
import net.nevowolfman.mipo.UI.Main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class OrgEditorFragment extends Fragment implements View.OnClickListener {
    private MainActivity parent;

    //views
    private TextView tvTeamName;
    private RecyclerView recyclerView;
    Button addMember, createTeam, back, save;

    //dialogs and stuff
    private AddMemberDialog addMemberDialog;
    private AddTeamDialog addTeamDialog;

    //data
    private Organization org;
    public Team current_team;
    private List<Team> allTeams;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_org_editor, container, false);

        parent = (MainActivity) requireActivity();

        org = parent.getOrg();
        current_team = org.getAdmins();
        allTeams = new ArrayList<>();

        ((TextView)v.findViewById(R.id.tvOrgName)).setText(org.getName());
        tvTeamName = v.findViewById(R.id.tvTeamName);
        tvTeamName.setText(org.getAdmins().getName());

        recyclerView = v.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new OrgEditorRecyclerViewAdapter(this, current_team));

        addMember = v.findViewById(R.id.btnAddMember);
        createTeam = v.findViewById(R.id.btnCreateTeam);
        back = v.findViewById(R.id.btnBack);
        save = v.findViewById(R.id.btnSave);

        addMember.setOnClickListener(this);
        createTeam.setOnClickListener(this);
        back.setOnClickListener(this);
        save.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View view) {
        if(view == addMember) {
            addMemberDialog = new AddMemberDialog(this);
            addMemberDialog.show(getParentFragmentManager(), "addMember");
        }
        else if (view == createTeam) {
            addTeamDialog = new AddTeamDialog(this);
            addTeamDialog.show(getParentFragmentManager(), "addTeam");
        }
        else if (view == back) {

        }
        else if(view == save) {

        }
    }

    public void addMember(Member member)
    {
        int pos = current_team.getMembers().size();
        current_team.getMembers().add(member);
        notifyMemberInserted(pos);
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

    public void notifyMemberInserted(int position)
    {
        try {
            recyclerView.getAdapter().notifyItemInserted(position);
        }catch (NullPointerException e)
        {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        OrgEditorRecyclerViewAdapter adapter = (OrgEditorRecyclerViewAdapter) recyclerView.getAdapter();
        int id = item.getItemId();
        if (id == R.id.deleteMember) {
            adapter.removeMember(adapter.member_selected);
            return true;
        }
        PopupMenu team_menu = new PopupMenu(requireContext(), recyclerView.findViewHolderForLayoutPosition(adapter.member_selected).itemView);

        if(id == R.id.switchTeam){
            team_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Team team = getTeam(item.getTitle().toString());
                    current_team = team;
                    tvTeamName.setText(team.getName());
                    recyclerView.setAdapter(new OrgEditorRecyclerViewAdapter(OrgEditorFragment.this, current_team));
                    return true;
                }
            });
            Member member = adapter.getMemberSelected();
            for (int i = 0, n = member.getTeams().size(); i < n; i++) {
                Team team = member.getTeams().get(i);
                team_menu.getMenu().add(team.getName());
            }
            team_menu.show();
        }
        else if (id == R.id.attachTeam) {
            team_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Team team = getTeam(item.getTitle().toString());
                    adapter.addTeamToMember(adapter.member_selected, team);
                    return true;
                }
            });
            for (int i = 0, n = allTeams.size(); i < n; i++) {
                Team team = getAllTeams().get(i);
                if(!adapter.getMemberSelected().getTeams().contains(team)){
                    team_menu.getMenu().add(team.getName());
                }
            }
            team_menu.show();
        }
        else if (id == R.id.removeTeams) {
            team_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    String name = item.getTitle().toString();
                    Team team = getTeam(item.getTitle().toString());
                    adapter.getMemberSelected().getTeams().remove(team);
                    return true;
                }
            });
            Member member = adapter.getMemberSelected();
            for (int i = 0, n = member.getTeams().size(); i < n; i++) {
                Team team = member.getTeams().get(i);
                team_menu.getMenu().add(team.getName());
            }
            team_menu.show();
        }
        else {
            return super.onContextItemSelected(item);
        }
        return true;
    }

    //getters and setters
    public MainActivity getParent() {
        return parent;
    }

    public void setParent(MainActivity parent) {
        this.parent = parent;
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

    public List<Team> getAllTeams() {
        return allTeams;
    }

    public void setAllTeams(List<Team> allTeams) {
        this.allTeams = allTeams;
    }
}