package net.nevowolfman.mipo.UI.Main;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TimePicker;
import android.widget.Toast;

import net.nevowolfman.mipo.Model.EventDate;
import net.nevowolfman.mipo.Model.Organization;
import net.nevowolfman.mipo.R;
import net.nevowolfman.mipo.Repository.Repository;
import net.nevowolfman.mipo.UI.OrgEditor.OrgEditorFragment;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView navbar;

    private Fragment currentOrgFragment;
    private ProfileFragment profileFragment;

    private AlarmManager alarmManager;
    private Repository repository;

    /////////////////////////////////////////////////////// FireBaseAuthUI ///////////////////////////////////////////////////////
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    if(!onSignInResult(result)){
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        startSignIn();
                    }
                    else{
                        profileFragment.setTVEmail((FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
                        try {
                            ((OrgFragment)getSupportFragmentManager().findFragmentById(R.id.fragOrg)).checkOrgMode();
                        }catch (NullPointerException ignored) {}
                        repository.getOrg(new Repository.GetOrgListener() {
                            @Override
                            public void onComplete(Organization org) {
                                if(org != null) {
                                    ListIterator<EventDate> iterator = org.getEventDates().listIterator();
                                    while(iterator.hasNext()) {
                                        setAlarm(iterator.next());
                                    }
                                }
                            }
                        });
                    }
                }
            }
    );

    public boolean onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (response != null && result.getResultCode() == RESULT_OK) {
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
    /////////////////////////////////////////////////////// FireBaseAuthUI ///////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new Repository(this);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startSignIn();
        }

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        createNotificationChannel("events", "Events Alarms");

        viewPager = findViewById(R.id.viewpager);
        ScreenSlidePagerAdapter pager_adapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pager_adapter);
        navbar = findViewById(R.id.navbar);

        currentOrgFragment = new OrgFragment();
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

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                navbar.getMenu().getItem(position).setChecked(true);
            }
        });
    }

    /////////////////////////////////////////////////////// ViewPager2 ///////////////////////////////////////////////////////
    public void swapFragments(@IdRes int oldFragmentID, Fragment newFragment) {
        currentOrgFragment = newFragment;
        viewPager.setAdapter(new ScreenSlidePagerAdapter(this));
        viewPager.setCurrentItem(1);
        //getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(oldFragmentID, newFragment).commit();
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
                return currentOrgFragment;
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
    /////////////////////////////////////////////////////// ViewPager2 ///////////////////////////////////////////////////////

    /////////////////////////////////////////////////////// Alarms ///////////////////////////////////////////////////////
    public void setAlarm(EventDate eventDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, eventDate.getDay());
        calendar.set(Calendar.HOUR_OF_DAY, eventDate.getHour());
        calendar.set(Calendar.MINUTE, eventDate.getMinute());

        long alarmTime = calendar.getTimeInMillis();
        if(alarmTime < System.currentTimeMillis()){
            alarmTime += AlarmManager.INTERVAL_DAY*7;
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("requestCode", eventDate.hashCode());
        intent.putExtra("alarmTime", alarmTime);
        PendingIntent alarmIntent =  PendingIntent.getBroadcast(this, eventDate.hashCode(), intent, PendingIntent.FLAG_IMMUTABLE);

        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
        if(!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) || alarmManager.canScheduleExactAlarms()){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, alarmIntent);
        }
    }

    public void cancelAlarm(EventDate eventDate) {
        PendingIntent alarmIntent =  PendingIntent.getBroadcast(this, eventDate.hashCode(), new Intent(this, AlarmReceiver.class), PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(alarmIntent);
    }

    private void createNotificationChannel(String id, String name) {
        getSystemService(NotificationManager.class).createNotificationChannel(new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT));
    }
    /////////////////////////////////////////////////////// Alarms ///////////////////////////////////////////////////////

    //get & set //////////////////////////////////////////////////////////////////////////////////
    public void addOrg(Organization org){
        repository.setOrg(org);
    }
    public Repository getRepository() {
        return repository;
    }
}