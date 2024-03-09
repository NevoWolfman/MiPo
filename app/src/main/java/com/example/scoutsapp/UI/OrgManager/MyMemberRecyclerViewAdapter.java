package com.example.scoutsapp.UI.OrgManager;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.name.setText(holder.mItem.getName());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public Member mItem;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.MemberName_OrgList);
            //TODO: make the clicks actually do smth and decide how to add members and teams
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            name.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {


                    return true;
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '"   + name.getText() + "'";
        }
    }
}