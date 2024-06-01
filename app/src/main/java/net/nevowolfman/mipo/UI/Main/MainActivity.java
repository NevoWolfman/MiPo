package net.nevowolfman.mipo.UI.Main;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import net.nevowolfman.mipo.Model.Organization;
import net.nevowolfman.mipo.R;
import net.nevowolfman.mipo.Repository.Repository;
import net.nevowolfman.mipo.Repository.UserModel;
import net.nevowolfman.mipo.UI.Login.LoginActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView navbar;

    private OrgFragment orgFragment;
    private ProfileFragment profileFragment;
    private AddOrganizationDialog addOrganizationDialog;

    private Repository repository;

    private List<Organization> allOrgs;
    private FirebaseUser currentUser;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    if(!onSignInResult(result)){
                        Toast.makeText(MainActivity.this, "Login Failed" + result.getResultCode(), Toast.LENGTH_SHORT).show();
                        startSignIn();
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new ScreenSlidePagerAdapter(this));
        navbar = findViewById(R.id.navbar);

        orgFragment = new OrgFragment();
        profileFragment = new ProfileFragment();

        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.itemProfile)
                {
                    viewPager.setCurrentItem(0);
                    return true;
                }
                if(id == R.id.itemOrgs)
                {
                    viewPager.setCurrentItem(1);
                    return true;
                }
                return false;
            }
        });

        repository = new Repository(this);
        allOrgs = new ArrayList<>();

        startSignIn();
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

                return profileFragment;
            }
            if(position == 1)
            {

                return orgFragment;
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

    public boolean onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (response != null && result.getResultCode() == RESULT_OK) {
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            return true;
        }
        return false;
    }

    public void startSignIn(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());
                //new AuthUI.IdpConfig.GoogleBuilder().build()); does not work

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
    }
}