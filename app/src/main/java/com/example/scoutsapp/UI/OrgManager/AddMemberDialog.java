package com.example.scoutsapp.UI.OrgManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.scoutsapp.Model.Member;
import com.example.scoutsapp.R;

public class AddMemberDialog extends DialogFragment {
    EditText etID, etName;
    OrgManagerActivity activity;

    public AddMemberDialog(OrgManagerActivity activity){
        this.activity = activity;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = getLayoutInflater();

        View mainView = inflater.inflate(R.layout.new_member_dialog,null);
        etID = mainView.findViewById(R.id.etID);
        etName = mainView.findViewById(R.id.etName);

        builder.setView(mainView)
                .setPositiveButton(R.string.Add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = etName.getText().toString();
                        String id_str = etID.getText().toString();
                        int id;

                        if(name.isEmpty() || id_str.isEmpty())
                        {
                            Toast.makeText(activity, "Please fill out", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            try {
                                id = Integer.parseInt(id_str);
                            }catch (NumberFormatException e){
                                Toast.makeText(activity, "Invalid ID", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            activity.addMember(new Member(id, name, activity.getCurrent_team()));
                        }
                    }
                }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddMemberDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
