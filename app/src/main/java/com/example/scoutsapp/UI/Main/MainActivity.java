package com.example.scoutsapp.UI.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scoutsapp.Model.Organization;
import com.example.scoutsapp.R;
import com.example.scoutsapp.Repository.Repository;
import com.example.scoutsapp.Repository.UserModel;
import com.example.scoutsapp.UI.Login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;

    private OrgFragment orgFragment;
    private AddOrganizationDialog addOrganizationDialog;

    private Repository repository;

    private List<Organization> allOrgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new ScreenSlidePagerAdapter(this));

        repository = new Repository(this);
        allOrgs = new ArrayList<>();
    }

    public void showAddOrgDialog(){
        addOrganizationDialog = new AddOrganizationDialog(this);
        addOrganizationDialog.show(getSupportFragmentManager(), "addOrg");
    }

    public List<Organization> getAllOrgs() {
        return allOrgs;
    }

    public void setAllOrgs(List<Organization> allOrgs) {
        this.allOrgs = allOrgs;
    }

    public void addOrg(Organization org){
        if(!allOrgs.contains(org)){
            allOrgs.add(org);
            orgFragment.notifyItemAdded();
        }
    }

    public Repository getRepository() {
        return repository;
    }


    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity activity) {
            super(activity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if(position == 0)
            {
                return new ProfileFragment();
            }
            if(position == 1)
            {
                orgFragment = new OrgFragment();
                return orgFragment;
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}