package com.example.scoutsapp.UI.OrgManager;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.scoutsapp.Model.Member;
import com.example.scoutsapp.R;
import com.example.scoutsapp.UI.Main.MainActivity;
import com.example.scoutsapp.UI.NewOrg.NewOrgActivity;

public class ButtonsFragment extends Fragment implements View.OnClickListener {

    private OrgManagerActivity parent;
    Button addTeam, addMember, back;
    public ButtonsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buttons, container, false);

        parent = (OrgManagerActivity) requireActivity();

        addTeam = v.findViewById(R.id.btnAddTeam);
        addMember = v.findViewById(R.id.btnAddMember);
        back = v.findViewById(R.id.btnBack2);

        addTeam.setOnClickListener(this);
        addMember.setOnClickListener(this);
        back.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        if(view == addTeam)
        {

        }
        else if(view == addMember)
        {

            parent.addMember();

        }
        else if(view == back)
        {
            startActivity(new Intent(requireContext(), NewOrgActivity.class));
        }
    }
}