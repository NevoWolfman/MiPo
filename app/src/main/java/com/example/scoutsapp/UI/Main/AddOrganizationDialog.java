package com.example.scoutsapp.UI.Main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.scoutsapp.Model.Organization;
import com.example.scoutsapp.Model.Team;
import com.example.scoutsapp.R;
import com.example.scoutsapp.UI.OrgManager.OrgManagerActivity;

public class AddOrganizationDialog extends DialogFragment {
    EditText etName;

    MainActivity activity;

    public AddOrganizationDialog(MainActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.new_org_dialog, null);
        etName = view.findViewById(R.id.etName);

        builder.setView(view).setPositiveButton(R.string.Add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etName.getText().toString();
                if(name.isEmpty())
                {
                    Toast.makeText(activity, "Please fill out", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < activity.getAllOrgs().size(); i++) {
                    if(activity.getAllOrgs().get(i).getName().equals(name))
                    {
                        Toast.makeText(activity, "Organization already exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                activity.addOrg(new Organization(name));
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
