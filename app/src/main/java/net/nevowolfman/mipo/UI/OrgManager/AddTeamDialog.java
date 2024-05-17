package net.nevowolfman.mipo.UI.OrgManager;

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

import net.nevowolfman.mipo.Model.Team;
import net.nevowolfman.mipo.R;

public class AddTeamDialog extends DialogFragment {
    EditText etName;

    OrgManagerActivity activity;

    public AddTeamDialog(OrgManagerActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.new_team_dialog, null);
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

                for (int i = 0; i < activity.getAllTeams().size(); i++) {
                    if(activity.getAllTeams().get(i).getName().equals(name))
                    {
                        Toast.makeText(activity, "Team already exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                activity.addTeam(new Team(name));
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
