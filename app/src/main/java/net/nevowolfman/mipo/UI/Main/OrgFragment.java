package net.nevowolfman.mipo.UI.Main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import net.nevowolfman.mipo.Model.Organization;
import net.nevowolfman.mipo.R;
import net.nevowolfman.mipo.Repository.Repository;
import net.nevowolfman.mipo.UI.OrgChecker.OrgCheckerFragment;
import net.nevowolfman.mipo.UI.OrgEditor.OrgEditorFragment;

public class OrgFragment extends Fragment implements View.OnClickListener {

    private MainActivity parent;
    private Button check, edit;

    private AddOrganizationDialog addOrganizationDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_org, container, false);

        parent = (MainActivity)requireActivity();

        check = view.findViewById(R.id.btnCheck);
        edit = view.findViewById(R.id.btnEdit);

        parent.getRepository().getOrg(new Repository.GetOrgListener() {
            @Override
            public void onComplete(Organization org) {
                if(org == null) {
                    ((ViewGroup)view).removeView(check);
                    edit.setText("Create a New Organization");
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)edit.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    edit.setLayoutParams(params);
                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addOrganizationDialog = new AddOrganizationDialog(parent);
                            addOrganizationDialog.show(getParentFragmentManager(), "addOrg");
                        }
                    });
                }
                else {
                    check.setOnClickListener(OrgFragment.this);
                    edit.setOnClickListener(OrgFragment.this);
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == check) {
            parent.swapFragments(R.id.fragOrg, new OrgCheckerFragment());
        }
        else if (view == edit) {
            parent.swapFragments(R.id.fragOrg, new OrgEditorFragment());
        }
    }
}