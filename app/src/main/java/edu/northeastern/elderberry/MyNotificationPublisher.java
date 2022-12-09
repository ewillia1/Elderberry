package edu.northeastern.elderberry;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class MyNotificationPublisher extends BroadcastReceiver {

    public static int NOTIFICATION_ID = 1;
    public static final String NOTIFICATION = "notification";
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";

    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Elderberry medicine reminder", importance);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(getNotificationIdInt(), notification);
    }

    public static String getNotificationId() {
        NOTIFICATION_ID++;
        return String.valueOf(NOTIFICATION_ID);
    }

    public static int getNotificationIdInt() {
        return NOTIFICATION_ID++;
    }

    public static void setAlarm(DateTimeDose date, Context context) {
        //TODO: Remove the i variable used for testing
        int i = 0;
        do {
            if (date.getFromTime().toInstant().toEpochMilli() > System.currentTimeMillis()) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, default_notification_channel_id);
                // TODO: Change the Title of the notification
                builder.setContentTitle(date.getName() + " " + date.getDose() + " " + date.getFromTime());
                builder.setContentText("Take " + date.getDose() + " " + date.getName());
                builder.setSmallIcon(R.drawable.elderberryplant);
                builder.setAutoCancel(true);
                builder.setChannelId(NOTIFICATION_CHANNEL_ID);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                Notification notification = builder.build();
                Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
                notificationIntent.putExtra(MyNotificationPublisher.getNotificationId(), 1);
                notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
                notificationIntent.putExtra("toDate", date.getToDate());
                notificationIntent.putExtra("fromTime", date.getFromTime());
                notificationIntent.putExtra("name", date.getName());

                notificationIntent.putExtra("dose", date.getDose());
                int id = MyNotificationPublisher.getNotificationIdInt();
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                assert alarmManager != null;
                System.out.println(date.getName() + " " + date.getDose() + " " + date.getFromTime() + " with requestId: "+ id);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getFromTime().toInstant().toEpochMilli(), pendingIntent);
                i++;
            }
            date.getFromTime().setTime(date.getFromTime().getTime() + 300000);
        } while (i <= 5 &&(date.getToDate().toInstant().toEpochMilli()) > (date.getFromTime().toInstant().toEpochMilli()));
    }
}
