package net.nevowolfman.mipo.UI.OrgEditor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import net.nevowolfman.mipo.Model.Member;
import net.nevowolfman.mipo.R;

public class AddMemberDialog extends DialogFragment {
    EditText etName;
    OrgEditorFragment fragment;

    public AddMemberDialog(OrgEditorFragment fragment){
        this.fragment = fragment;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = getLayoutInflater();

        View mainView = inflater.inflate(R.layout.new_member_dialog,null);
        etName = mainView.findViewById(R.id.etName);

        builder.setView(mainView)
                .setPositiveButton(R.string.Add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = etName.getText().toString();

                        if(name.isEmpty())
                        {
                            Toast.makeText(requireContext(), "Please fill out", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            fragment.addMember(new Member(name));
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
