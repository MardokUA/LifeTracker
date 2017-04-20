package laktionov.lifetracker.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import laktionov.lifetracker.utils.GlobalVariables;

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private long timeSet;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
    }

    public DatePickerDialogFragment() {
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        timeSet = calendar.getTimeInMillis();

        createAndStartTimePicker();
    }

    private void createAndStartTimePicker() {
        TimePickerDialogFragment timePickerDialogFragment = new TimePickerDialogFragment();
        Bundle args = new Bundle();
        args.putLong(GlobalVariables.TIME_TO_NOTIFY, timeSet);
        timePickerDialogFragment.setArguments(args);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        timePickerDialogFragment.show(fragmentManager, "TAG");
    }
}
