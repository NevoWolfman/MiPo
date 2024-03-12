package com.example.scoutsapp.UI.OrgManager;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.scoutsapp.Model.Member;
import com.example.scoutsapp.R;

import java.util.List;

public class MyMemberRecyclerViewAdapter extends RecyclerView.Adapter<MyMemberRecyclerViewAdapter.ViewHolder> {

    Context context;
    private final List<Member> mValues;

    public MyMemberRecyclerViewAdapter(Context context, List<Member> items) {
        this.context = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);

        //TODO: open a menu to decide which of the teams of goons are going to be the current team, and then switch the list
        //TODO: make this open a menu to delete this member or give it a team of goons

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.member = mValues.get(position);
        holder.name.setText(holder.member.getName());
        holder.id.setText(String.valueOf(holder.member.getId()));
        holder.cbHasGoons.setChecked(holder.member.hasGoons());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public final TextView name, id;
        public final CheckBox cbHasGoons;
        public Member member;

        public ViewHolder(View view) {
            super(view);
            view.setOnCreateContextMenuListener(this);
            name = view.findViewById(R.id.MemberName);
            id = view.findViewById(R.id.MemberID);
            cbHasGoons = view.findViewById(R.id.cbHasTeam);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }
}