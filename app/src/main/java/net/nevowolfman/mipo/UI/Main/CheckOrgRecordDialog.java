package net.nevowolfman.mipo.UI.Main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import net.nevowolfman.mipo.Model.Organization;
import net.nevowolfman.mipo.R;
import net.nevowolfman.mipo.Repository.Repository;
import net.nevowolfman.mipo.UI.OrgChecker.OrgCheckerFragment;

import java.util.List;

public class CheckOrgRecordDialog extends DialogFragment {
    private MainActivity parent;

    private Spinner spinner;

    private Organization orgSelected;
    public CheckOrgRecordDialog(MainActivity activity) {
        parent = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
        View view = parent.getLayoutInflater().inflate(R.layout.check_org_record_dialog, null);
        spinner = view.findViewById(R.id.spOrg);

        parent.getRepository().getVersions(new Repository.GetVersionsListener() {
            @Override
            public void onComplete(List<Organization> org) {
                ArrayAdapter<Organization> adapter = new ArrayAdapter<>(parent, android.R.layout.simple_spinner_dropdown_item, org.toArray(new Organization[0]));
                spinner.setAdapter(adapter);
            }
        });

        builder.setView(view).setPositiveButton(R.string.Select, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                parent.swapFragments(R.id.fragOrg, new OrgCheckerFragment((Organization) spinner.getSelectedItem()));
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();
    }
}
