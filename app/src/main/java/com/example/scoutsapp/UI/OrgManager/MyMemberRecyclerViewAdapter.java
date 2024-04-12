package com.example.scoutsapp.UI.OrgManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.scoutsapp.Model.Member;
import com.example.scoutsapp.Model.Team;
import com.example.scoutsapp.R;

import java.util.List;

public class MyMemberRecyclerViewAdapter extends RecyclerView.Adapter<MyMemberRecyclerViewAdapter.ViewHolder> {
    MemberFragment fragment;
    Team team;
    int member_selected;

    public MyMemberRecyclerViewAdapter(MemberFragment fragment, Team team) {
        this.fragment = fragment;
        this.team = team;
        member_selected = -1;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.member = team.getMembers().get(position);
        holder.name.setText(holder.member.getName());
        holder.id.setText(String.valueOf(holder.member.getId()));
        holder.cbHasGoons.setChecked(holder.member.hasTeams());
    }

    @Override
    public int getItemCount() {
        return team.getMembers().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public final TextView name, id;
        public final CheckBox cbHasGoons;
        public Member member;
        public LinearLayout rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = (LinearLayout) view;
            view.setOnCreateContextMenuListener(this);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    member_selected = getBindingAdapterPosition();
                    return false;
                }
            });
            name = view.findViewById(R.id.MemberName);
            id = view.findViewById(R.id.MemberID);
            cbHasGoons = view.findViewById(R.id.cbHasTeam);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            fragment.requireActivity().getMenuInflater().inflate(R.menu.member_edit, menu);
        }
    }

    public void removeMember(int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.requireActivity());
        builder.setTitle("You want to delete this member?")
                .setMessage("this will delete him and all of his teams (unless someone else is in charge of the team")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        team.getMembers().remove(position);
                        notifyItemRemoved(position);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create();
        builder.show();
    }

    public Member getMember(int position)
    {
        return team.getMembers().get(position);
    }

    public void addTeamToMember(int position, Team team)
    {
        this.team.getMembers().get(position).getTeams().add(team);
    }

    public Member getMemberSelected()
    {
        return team.getMembers().get(member_selected);
    }
}