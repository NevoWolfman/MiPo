package com.example.scoutsapp.UI.OrgManager;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.scoutsapp.R;


public class ButtonsFragment extends Fragment implements View.OnClickListener {

    private OrgManagerActivity parent;
    Button addMember, createTeam, back;
    public ButtonsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buttons, container, false);

        parent = (OrgManagerActivity) requireActivity();

        addMember = v.findViewById(R.id.btnAddMember);
        createTeam = v.findViewById(R.id.btnCreateTeam);
        back = v.findViewById(R.id.btnBack2);

        addMember.setOnClickListener(this);
        createTeam.setOnClickListener(this);
        back.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        if(view == addMember)
        {
            parent.showAddMemberDialog();

        }
        else if(view == createTeam)
        {
            parent.showAddTeamDialog();
        }
    }
}