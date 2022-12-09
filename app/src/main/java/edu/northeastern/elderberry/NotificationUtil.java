package edu.northeastern.elderberry;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotificationUtil {

    private static final String TAG = "NotificationUtil";
    private static FirebaseAuth mAuth;


    public static List<MedicineDoseTime> getMedicationInfo(Context context, NotificationManager notificationManager) {
        DatabaseReference userDB;
        mAuth = FirebaseAuth.getInstance();
        List<MedicineDoseTime> medicineList = new ArrayList<>();

        userDB = FirebaseDatabase.getInstance().getReference();
        String userId = mAuth.getCurrentUser().getUid();

        // Todo to provide the correct username based on log-in info
        userDB.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "_____onDataChange: ");
                notificationManager.deleteNotificationChannel(MyNotificationPublisher.NOTIFICATION_CHANNEL_ID);
                medicineList.clear();

                for (DataSnapshot d : snapshot.getChildren()) {
                    MedicineDoseTime medicineDoseTime = d.getValue(MedicineDoseTime.class);
                    medicineList.add(medicineDoseTime);
                }
                scheduleMedicationNotifications(medicineList, context);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return null;
    }

    private static void scheduleMedicationNotifications(List<MedicineDoseTime> medicines, Context context) {
        List<DateTimeDose> dates = new ArrayList<>();
        for (MedicineDoseTime doseTime : medicines) {
            String fromDate = doseTime.getFromDate();
            List<String> times = new ArrayList<>(doseTime.getTime().values()).get(0);
            List<String> doses = new ArrayList<>(doseTime.getDose().values()).get(0);

            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US);
            SimpleDateFormat toDateTimeFormatter = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US);

            int i = 0;
            for (String s : times) {
                String fromDateString = fromDate + " " + s;
                String toDateString = doseTime.getToDate() + " " + "11:59 PM";
                try {
                    DateTimeDose dateTimeDose = new DateTimeDose();
                    dateTimeDose.setFromTime(dateTimeFormatter.parse(fromDateString));
                    dateTimeDose.setToDate(toDateTimeFormatter.parse(toDateString));
                    dateTimeDose.setName(doseTime.getName());
                    dateTimeDose.setDose(Integer.parseInt(doses.get(i)));
                    i++;
                    dates.add(dateTimeDose);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        MyNotificationPublisher.resetNotificationId();
        for (DateTimeDose date : dates) {
            MyNotificationPublisher.setAlarm(date, context);
        }
    }
}
