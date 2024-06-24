package net.nevowolfman.mipo.UI.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import net.nevowolfman.mipo.Model.EventDate;
import net.nevowolfman.mipo.Model.Organization;
import net.nevowolfman.mipo.R;
import net.nevowolfman.mipo.Repository.Repository;

import java.util.List;
import java.util.ListIterator;

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
                parent.getRepository().getOrg(new Repository.GetOrgListener() {
                    @Override
                    public void onComplete(Organization org) {
                        if(org != null) {
                            ListIterator<EventDate> iterator = org.getEventDates().listIterator();
                            while(iterator.hasNext()) {
                                parent.cancelAlarm(iterator.next());
                            }
                        }
                    }
                });
                AuthUI.getInstance()
                        .signOut(requireContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                try {
                                    OrgFragment orgFragment = (OrgFragment) getParentFragmentManager().findFragmentById(R.id.fragOrg);
                                    orgFragment.resetButtons();
                                }catch (NullPointerException ignored) {}
                                Toast.makeText(parent, "Signed Out", Toast.LENGTH_SHORT).show();
                                ((MainActivity)requireActivity()).startSignIn();
                            }
                        });
            }
        });
        return root;
    }

    public void setTVEmail(String str) {
        tvEmail.setText(str);
    }
}