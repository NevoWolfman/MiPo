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

    private RelativeLayout layout;
    private Button check, edit, record;


    private AddOrganizationDialog addOrganizationDialog;
    private CheckOrgRecordDialog checkOrgRecordDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_org, container, false);

        parent = (MainActivity)requireActivity();

        check = view.findViewById(R.id.btnCheck);
        edit = view.findViewById(R.id.btnEdit);
        record = view.findViewById(R.id.btnRecord);

        //NOTE: this is acting weird because of the delay with the DB. Maybe try to fix it?
        checkOrgMode();

        layout = (RelativeLayout) view;

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == check) {
            parent.getRepository().getCheckedOrg(new Repository.GetOrgListener() {
                @Override
                public void onComplete(Organization org) {
                    parent.swapFragments(R.id.fragOrg, new OrgCheckerFragment(org));
                }
            });
        }
        else if (view == edit) {
            parent.getRepository().getCheckedOrg(new Repository.GetOrgListener() {
                @Override
                public void onComplete(Organization org) {
                    parent.swapFragments(R.id.fragOrg, new OrgEditorFragment(org));
                }
            });
        }
        else if (view == record) {
            checkOrgRecordDialog = new CheckOrgRecordDialog(parent);
            checkOrgRecordDialog.show(getParentFragmentManager(), "checkRecord");
        }
    }

    public void checkOrgMode() {
        parent.getRepository().getOrg(new Repository.GetOrgListener() {
            @Override
            public void onComplete(Organization org) {
                if(org == null) {
                    layout.removeView(check);
                    layout.removeView(record);
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
                    record.setOnClickListener(OrgFragment.this);
                }
            }
        });
    }
}