package com.example.scoutsapp.UI.OrgManager;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupMenu;

import com.example.scoutsapp.Model.Member;
import com.example.scoutsapp.Model.Team;
import com.example.scoutsapp.R;

import java.util.LinkedList;
import java.util.List;

/**
 * A fragment representing a list of Members.
 */
public class MemberFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OrgManagerActivity parent;
    private RecyclerView recyclerView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MemberFragment() {
    }

    @SuppressWarnings("unused")
    public static MemberFragment newInstance(int columnCount) {
        MemberFragment fragment = new MemberFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        parent = (OrgManagerActivity) requireActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyMemberRecyclerViewAdapter(this, parent.getCurrent_team()));
            parent.registerForContextMenu(recyclerView);
        }
        return view;
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
        //TODO: finish switch
        MyMemberRecyclerViewAdapter adapter = (MyMemberRecyclerViewAdapter) recyclerView.getAdapter();
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
                    String name = item.getTitle().toString();
                    Team team = parent.getTeam(item.getTitle().toString());
                    parent.setCurrent_team(team);
                    teamSwitched();
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
                    Team team = parent.getTeam(item.getTitle().toString());
                    adapter.addTeamToMember(adapter.member_selected, team);
                    return true;
                }
            });
            for (int i = 0, n = parent.getAllTeams().size(); i < n; i++) {
                Team team = parent.getAllTeams().get(i);
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
                    Team team = parent.getTeam(item.getTitle().toString());
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

    public void teamSwitched(){
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}