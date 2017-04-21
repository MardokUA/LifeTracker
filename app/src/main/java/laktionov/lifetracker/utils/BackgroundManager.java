package laktionov.lifetracker.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class BackgroundManager extends JobService {

    private String text;

    @Override
    public boolean onStartJob(JobParameters job) {
        text = job.getExtras().getString("test1", "wow!");

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .setContentText(text)
                .setContentTitle("I AM ALIVE!");

        notificationManager.notify(5, builder.build());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
