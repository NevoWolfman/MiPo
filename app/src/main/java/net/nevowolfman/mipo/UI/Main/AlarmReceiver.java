package net.nevowolfman.mipo.UI.Main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    public AlarmReceiver() {}
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("IT WORKS!");
        Toast.makeText(context, "IT WORKS!", Toast.LENGTH_SHORT).show();
        AlarmManager alarmManager = context.getSystemService(AlarmManager.class);
        if(!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) || alarmManager.canScheduleExactAlarms()) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + AlarmManager.INTERVAL_DAY*7, PendingIntent.getBroadcast(context, intent.getIntExtra("id", -1), intent, PendingIntent.FLAG_IMMUTABLE));
        }
    }
}
