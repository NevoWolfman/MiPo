package net.nevowolfman.mipo.UI.Main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import net.nevowolfman.mipo.R;
import net.nevowolfman.mipo.Repository.UserModel;
import net.nevowolfman.mipo.UI.Login.LoginActivity;

public class ProfileFragment extends Fragment {
    private TextView tvEmail;
    private Button btnSignOut;

    private MainActivity parent;

    private SharedPreferences prefs;
    public final String SAVED_CREDENTIALS = "credentials";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_profile, container, false);

        parent = (MainActivity)requireActivity();

        tvEmail = root.findViewById(R.id.tvEmail);
        btnSignOut = root.findViewById(R.id.btnSignOut);

        prefs = parent.getSharedPreferences(SAVED_CREDENTIALS, Activity.MODE_PRIVATE);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("email");
                editor.remove("pass");
                editor.apply();

                AuthUI.getInstance()
                        .signOut(requireContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(parent, "Signed Out", Toast.LENGTH_SHORT).show();
                                ((MainActivity)requireActivity()).startSignIn();
                            }
                        });
            }
        });

        String email = parent.getIntent().getStringExtra("email");
        if(email != null)
        {
            UserModel user = parent.getRepository().getUserByEmail(email);
            if(user != null)
            {
                tvEmail.setText(email);
            }
        }
        return root;
    }
}