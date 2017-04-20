package laktionov.lifetracker.fragment;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

import laktionov.lifetracker.receiver.TimeNotification;
import laktionov.lifetracker.utils.GlobalVariables;


public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private long timeSet;
    private long timeToNotify;
    private AlarmManager alarmManager;

    public TimePickerDialogFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            timeSet = savedInstanceState.getLong("TIME");
        }
        timeSet = getArguments().getLong(GlobalVariables.TIME_TO_NOTIFY);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minutes, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        int convertHoursToMillis = 3600000;
        int convertMinutesToMillis = 60000;
        timeToNotify = timeSet + (hourOfDay * convertHoursToMillis) + (minute * convertMinutesToMillis);
        creatingNotification();
    }

    private void creatingNotification() {
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), TimeNotification.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeToNotify, AlarmManager.INTERVAL_DAY, pendingIntent);

    }
}
