package com.example.scoutsapp.UI.Main;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.scoutsapp.R;


public class OrgFragment extends Fragment {
    private MainActivity parent;
    private RecyclerView list;
    private Button btnNewOrg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = (MainActivity)requireActivity();
        View view = inflater.inflate(R.layout.fragment_org_list, container, false);

        btnNewOrg = view.findViewById(R.id.btnNewOrg);
        btnNewOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.showAddOrgDialog();
            }
        });

        list = view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(parent));
        list.setAdapter(new OrgRecyclerViewAdapter(parent.getAllOrgs()));

        return view;
    }

    public void notifyItemAdded()
    {
        list.getAdapter().notifyItemInserted(list.getAdapter().getItemCount() - 1);
    }
}