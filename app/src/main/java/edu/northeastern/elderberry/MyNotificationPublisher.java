package edu.northeastern.elderberry;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyNotificationPublisher extends BroadcastReceiver {

    public static int NOTIFICATION_ID = 1;
    public static final String NOTIFICATION = "notification";
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    private TextToSpeech textToSpeech;
    private static List<PendingIntent> pendingIntents = new ArrayList<>();
    private static AlarmManager alarmManager;

    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Elderberry medicine reminder", importance);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(getNotificationIdInt(), notification);
        String message = intent.getStringExtra("title");
        textToSpeech = new TextToSpeech(context, i -> {
            if (i == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.UK);
                textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, "");
            } else {
                Toast.makeText(context, "Text to speech is not available", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public static String getNotificationId() {
        NOTIFICATION_ID++;
        return String.valueOf(NOTIFICATION_ID);
    }

    public static void resetNotificationId() {
        NOTIFICATION_ID = 1;
    }

    public static int getNotificationIdInt() {
        return NOTIFICATION_ID++;
    }

    public static void setAlarm(DateTimeDose date, Context context) {
        int i = 0;
        do {
            if (date.getFromTime().toInstant().toEpochMilli() > System.currentTimeMillis()) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, default_notification_channel_id);
                builder.setContentTitle("Time to take your medication!");
                String notificationTitle;
                if (date.getDose() > 1) {
                    notificationTitle = "Please take " + date.getDose() + " doses of " + date.getName();
                } else {
                    notificationTitle = "Please take " + date.getDose() + " dose of " + date.getName();
                }
                builder.setContentText(notificationTitle);
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
                notificationIntent.putExtra("title", (notificationTitle));
                int id = MyNotificationPublisher.getNotificationIdInt();
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, notificationIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
                pendingIntents.add(pendingIntent);
                alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                assert alarmManager != null;
                System.out.println(date.getName() + " " + date.getDose() + " " + date.getFromTime() + " with requestId: " + id);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getFromTime().toInstant().toEpochMilli(), pendingIntent);
                i++;
            }
            date.getFromTime().setTime(date.getFromTime().getTime() + 300000);
        } while (i <= 5 && (date.getToDate().toInstant().toEpochMilli()) > (date.getFromTime().toInstant().toEpochMilli()));

    }

    public static List<PendingIntent> getPendingIntents() {
        return pendingIntents;
    }

    public static void deletePendingIntents() {
        for (int currentIntent = 0; currentIntent < pendingIntents.size(); currentIntent++) {
            alarmManager.cancel(pendingIntents.get(currentIntent));
        }
        pendingIntents.clear();
    }
}