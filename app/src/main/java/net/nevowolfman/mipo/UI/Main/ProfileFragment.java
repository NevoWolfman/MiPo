package net.nevowolfman.mipo.UI.Main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.nevowolfman.mipo.R;
import net.nevowolfman.mipo.Repository.UserModel;
import net.nevowolfman.mipo.UI.Login.LoginActivity;

public class ProfileFragment extends Fragment {
    private TextView tvEmail;
    private Button btnSignOut;

    private MainActivity parent;
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

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), LoginActivity.class));
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