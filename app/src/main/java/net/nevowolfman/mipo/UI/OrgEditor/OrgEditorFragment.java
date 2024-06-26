package net.nevowolfman.mipo.UI.OrgEditor;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import net.nevowolfman.mipo.UI.Main.OrgFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class OrgEditorFragment extends Fragment implements View.OnClickListener {
    private MainActivity parent;

    //views
    private TextView tvTeamName;
    private RecyclerView recyclerView;
    private Button addMember, createTeam, back, save;

    //dialogs and stuff
    private AddMemberDialog addMemberDialog;
    private AddTeamDialog addTeamDialog;

    //data
    private Organization org;
    public Team current_team;
    private List<Team> allTeams;
    private Stack<Team> prevTeams;

    public OrgEditorFragment(Organization org) {
        this.org = org;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_org_editor, container, false);

        parent = (MainActivity) requireActivity();

        //org = parent.getOrg();
        current_team = org.getAdmins();
        allTeams = new ArrayList<>();
        prevTeams = new Stack<>();

        fillAllTeams(current_team);

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
            if(!prevTeams.isEmpty()) {
                switchCurrent_team(prevTeams.pop());
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                builder.setTitle("Are You Sure?")
                        .setMessage("Leaving without saving will delete any changes you made, and all teams not attached to members will be deleted")
                        .setPositiveButton(R.string.Go_Back, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                parent.swapFragments(R.id.fragOrgChecker, new OrgFragment());
                            }
                        })
                        .setNegativeButton(R.string.Cancel, null);
                builder.create().show();
            }
        }
        else if(view == save) {
            parent.addOrg(org);
            parent.swapFragments(R.id.fragOrgChecker, new OrgFragment());
        }
    }

    public void fillAllTeams(Team team) {
        for (int i = 0; i < team.getMembers().size(); i++) {
            Team newTeam = team.getMembers().get(i).getUnderlings();
            if(newTeam != null && !allTeams.contains(newTeam)) {
                allTeams.add(newTeam);
                fillAllTeams(team);
            }
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

    public void notifyMemberInserted(int position) {
        try {
            recyclerView.getAdapter().notifyItemInserted(position);
        }catch (NullPointerException e) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        OrgEditorRecyclerViewAdapter adapter = (OrgEditorRecyclerViewAdapter) recyclerView.getAdapter();
        int id = item.getItemId();
        if (id == R.id.deleteMember) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            adapter.removeMemberSelected();
        }
        else if(id == R.id.switchTeam){
            if(adapter.getMemberSelected().hasUnderlings()) {
                prevTeams.push(current_team);
                switchCurrent_team(adapter.getMemberSelected().getUnderlings());
            }
        }
        else if (id == R.id.editTeam) {
            if(adapter.getMemberSelected().hasUnderlings()) {
                //remove team
                adapter.removeTeamFromMemberSelected();
            }
            else {
                //attach team
                PopupMenu team_menu = new PopupMenu(requireContext(), recyclerView.findViewHolderForLayoutPosition(adapter.member_selected).itemView);
                team_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        adapter.attachTeamToMemberSelected(getTeam(item.getTitle().toString()));
                        return true;
                    }
                });

                //add all of the teams to the menu
                for (Team team : allTeams) {
                    if(!current_team.equals(team)) {
                        team_menu.getMenu().add(team.getName());
                    }
                }

                team_menu.show();
            }
        }
        else {
            return super.onContextItemSelected(item);
        }
        return true;
    }

    public void switchCurrent_team(Team team) {
        current_team = team;
        tvTeamName.setText(team.getName());
        recyclerView.setAdapter(new OrgEditorRecyclerViewAdapter(this, current_team));
    }

    //getters and setters

    public Team getCurrent_team() {
        return current_team;
    }

    public List<Team> getAllTeams() {
        return allTeams;
    }
}