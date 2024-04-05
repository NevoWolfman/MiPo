package com.example.scoutsapp.UI.OrgManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
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
import com.example.scoutsapp.R;

import java.util.List;

public class MyMemberRecyclerViewAdapter extends RecyclerView.Adapter<MyMemberRecyclerViewAdapter.ViewHolder> {
    MemberFragment fragment;
    private final List<Member> members;
    int member_selected;

    public MyMemberRecyclerViewAdapter(MemberFragment fragment, List<Member> members) {
        this.fragment = fragment;
        this.members = members;
        member_selected = -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);

        //TODO: open a menu to decide which of the teams of goons are going to be the current team, and then switch the list
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.member = members.get(position);
        holder.name.setText(holder.member.getName());
        holder.id.setText(String.valueOf(holder.member.getId()));
        holder.cbHasGoons.setChecked(holder.member.hasGoons());
    }

    @Override
    public int getItemCount() {
        return members.size();
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
}