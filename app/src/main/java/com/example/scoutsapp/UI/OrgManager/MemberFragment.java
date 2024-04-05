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

import com.example.scoutsapp.R;

import java.util.LinkedList;

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
            recyclerView.setAdapter(new MyMemberRecyclerViewAdapter(this, parent.getCurrent_team().getMembers()));
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
        //TODO: finish the rest of the options
        MyMemberRecyclerViewAdapter adapter = (MyMemberRecyclerViewAdapter) recyclerView.getAdapter();
        int id = item.getItemId();
        if (id == R.id.deleteMember) {
            adapter.removeMember(adapter.member_selected);
        }
        else if (id == R.id.attachTeam) {

        }
        else if (id == R.id.removeTeams) {

        }
        else {
            return super.onContextItemSelected(item);
        }
        return true;
    }
}