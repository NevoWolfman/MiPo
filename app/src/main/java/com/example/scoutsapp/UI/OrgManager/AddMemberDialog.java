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

import com.example.scoutsapp.R;

public class AddMemberDialog extends DialogFragment {

    TextView tvTitle;
    EditText etID, etName;
    DialogListener listener;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = getLayoutInflater();

        View mainView = inflater.inflate(R.layout.new_member_dialog,null);
        tvTitle = mainView.findViewById(R.id.tvTitle);
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
                        else if (listener != null)
                        {
                            try {
                                id = Integer.parseInt(id_str);
                            }catch (NumberFormatException e){
                                Toast.makeText(requireContext(), "Invalid ID", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            listener.OnPositiveClick(AddMemberDialog.this, name, id);
                        }
                    }
                }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddMemberDialog.this.getDialog().cancel();
                        listener.OnNegativeClick(AddMemberDialog.this);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "must implement DialogListener");
        }
    }

    public interface DialogListener
    {
        void OnPositiveClick(DialogFragment dialog, String name, int id);
        void OnNegativeClick(DialogFragment dialog);
    }
}
