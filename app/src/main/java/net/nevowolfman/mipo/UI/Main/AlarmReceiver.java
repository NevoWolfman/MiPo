package net.nevowolfman.mipo.UI.Main;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import net.nevowolfman.mipo.R;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    public AlarmReceiver() {}
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("IT'S TIME!");
        Toast.makeText(context, "IT'S TIME!", Toast.LENGTH_SHORT).show();

        showNotification(context);

        AlarmManager alarmManager = context.getSystemService(AlarmManager.class);
        if(!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) || alarmManager.canScheduleExactAlarms()) {
            int requestCode = intent.getIntExtra("requestCode", -1);
            long alarmTime = intent.getLongExtra("alarmTime", -1)+ AlarmManager.INTERVAL_DAY*7;
            intent.putExtra("alarmTime", alarmTime);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE));
        }
    }

    public void showNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "events")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("It's Time")
                .setContentText("Check your organization")
                .setContentIntent(PendingIntent.getActivity(context, 1, new Intent(context, MainActivity.class), PendingIntent.FLAG_IMMUTABLE))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
