package net.nevowolfman.mipo.UI.OrgChecker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.nevowolfman.mipo.Model.Organization;
import net.nevowolfman.mipo.Model.Team;
import net.nevowolfman.mipo.R;
import net.nevowolfman.mipo.Repository.Repository;
import net.nevowolfman.mipo.UI.Main.MainActivity;
import net.nevowolfman.mipo.UI.Main.OrgFragment;
import net.nevowolfman.mipo.UI.OrgEditor.OrgEditorFragment;
import net.nevowolfman.mipo.UI.OrgEditor.OrgEditorRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class OrgCheckerFragment extends Fragment implements View.OnClickListener {
    private MainActivity parent;

    //views
    private TextView tvTeamName;
    private RecyclerView recyclerView;
    private Button back, save;

    //data
    private Organization org;
    public Team current_team;
    private Stack<Team> prevTeams;

    public OrgCheckerFragment(Organization org) {
        this.org = org;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_org_checker, container, false);

        parent = (MainActivity) requireActivity();
        current_team = org.getAdmins();
        prevTeams = new Stack<>();

        tvTeamName = v.findViewById(R.id.tvTeamName);

        recyclerView = v.findViewById(R.id.list);

        back = v.findViewById(R.id.btnBack);
        save = v.findViewById(R.id.btnSave);

        back.setOnClickListener(this);
        save.setOnClickListener(this);

        ((TextView)v.findViewById(R.id.tvOrgName)).setText(org.getName());
        tvTeamName.setText(org.getAdmins().getName());

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new OrgCheckerRecyclerViewAdapter(OrgCheckerFragment.this, current_team));

        return v;
    }

    @Override
    public void onClick(View view) {
        if(view == back) {
            if(!prevTeams.isEmpty()) {
                switchCurrent_team(prevTeams.pop());
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                builder.setTitle("Are You Sure?")
                        .setMessage("Leaving without saving will delete any changes you made")
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
            parent.getRepository().setCheckedOrg(org);
            parent.swapFragments(R.id.fragOrgChecker, new OrgFragment());
        }
    }

    public void switchCurrent_team(Team team) {
        current_team = team;
        tvTeamName.setText(team.getName());
        recyclerView.setAdapter(new OrgCheckerRecyclerViewAdapter(this, current_team));
    }

    public void pushCurrentTeam() {
        prevTeams.push(current_team);
    }
}