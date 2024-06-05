package net.nevowolfman.mipo.UI.OrgEditor;

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

import java.util.List;

public class AddTeamDialog extends DialogFragment {
    EditText etName;

    OrgEditorFragment fragment;

    public AddTeamDialog(OrgEditorFragment fragment) {
        this.fragment = fragment;
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
                    Toast.makeText(requireContext(), "Please fill out", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Team> all = fragment.getAllTeams();
                for (int i = 0; i < all.size(); i++) {
                    if(all.get(i).getName().equals(name))
                    {
                        Toast.makeText(requireContext(), "Team already exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                fragment.addTeam(new Team(name));
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
