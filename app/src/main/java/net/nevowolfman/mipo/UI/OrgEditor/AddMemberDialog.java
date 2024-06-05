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
    EditText etID, etName;
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
                            Toast.makeText(requireContext(), "Please fill out", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            try {
                                id = Integer.parseInt(id_str);
                            }catch (NumberFormatException e){
                                Toast.makeText(requireContext(), "Invalid ID", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            fragment.addMember(new Member(id, name));
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
