package laktionov.lifetracker.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import laktionov.lifetracker.R;
import laktionov.lifetracker.adapter.TabAdapter;
import laktionov.lifetracker.controller.ItemActionController;
import laktionov.lifetracker.data.DBOpenHelper;
import laktionov.lifetracker.fragment.AchievementFragment;
import laktionov.lifetracker.fragment.DatePickerDialogFragment;
import laktionov.lifetracker.utils.BackgroundManager;
import laktionov.lifetracker.utils.GlobalVariables;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener, MenuItem.OnMenuItemClickListener {

    private TabAdapter tabAdapter;
    private ItemActionController itemActionController;
    private ArrayList<String> shareItems;
    private SharedPreferences settings;
    private FirebaseJobDispatcher jobDispatcher;
    private boolean isChecked;
    private boolean isActive;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindString(R.string.alert_message_add)
    String message_add;
    @BindString(R.string.alert_message_share)
    String message_share;
    @BindString(R.string.alert_positive_button)
    String alert_button;
    @BindString(R.string.alert_title)
    String alert_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        settings = getSharedPreferences(GlobalVariables.SETTINGS, 0);
        isChecked = settings.getBoolean(GlobalVariables.NOTIFY, false);
        itemActionController = ItemActionController.getInstance(this);

        setSupportActionBar(toolbar);

        tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        tabLayout.addTab(tabLayout.newTab().setText(tabAdapter.getPageTitle(GlobalVariables.FRAGMENT_WISHES)));
        tabLayout.addTab(tabLayout.newTab().setText(tabAdapter.getPageTitle(GlobalVariables.FRAGMENT_ACHIEVEMENT)));
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(GlobalVariables.FRAGMENT_WISHES);
        fab.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        MenuItem action_share = menu.findItem(R.id.action_share);
        MenuItem action_notify = menu.findItem(R.id.action_notify);
        MenuItem action_pick_time = menu.findItem(R.id.action_pick_time);
        MenuItem action_chat = menu.findItem(R.id.action_chat);

        action_notify.setChecked(isChecked);
        action_share.setOnMenuItemClickListener(this);
        action_notify.setOnMenuItemClickListener(this);
        action_pick_time.setOnMenuItemClickListener(this);
        action_chat.setOnMenuItemClickListener(this);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareItems = (ArrayList<String>) itemActionController.getShareItems();
                if (shareItems.size() == 0) {
                    alertMessage(GlobalVariables.ALERT_SHARE);
                } else {
                    startActivity(buildIntent());
                }
                break;
            case R.id.action_notify:
                item.setChecked(!item.isChecked());
                settings = getSharedPreferences(GlobalVariables.SETTINGS, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(GlobalVariables.NOTIFY, item.isChecked())
                        .apply();
                break;
            case R.id.action_pick_time:
                DialogFragment fragment = new DatePickerDialogFragment();
                fragment.show(getSupportFragmentManager(), "TIME");
                break;
            case R.id.action_chat:
                //TODO: Chat activity
                Intent intent = new Intent(this, MessagesActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        isActive = tab.getPosition() == GlobalVariables.FRAGMENT_WISHES;
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (isActive) {
                    Intent intent = new Intent(this, WishActivity.class);
                    startActivity(intent);
                }
                if (!isActive) {
                    EditText et_new_achievement = ButterKnife.findById(this, R.id.et_new_achievement);
                    AchievementFragment fragment = tabAdapter.getAchievementFragment();
                    if (et_new_achievement.getText().toString().isEmpty()) {
                        alertMessage(GlobalVariables.ALERT_ADD);
                    } else {
                        addAchievementToDB(et_new_achievement, fragment);
                    }
                }
                break;
        }
    }

    private void addAchievementToDB(EditText et_new_achievement, AchievementFragment fragment) {
        String new_achievement = et_new_achievement.getText().toString();
        DBOpenHelper helper = new DBOpenHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBOpenHelper.COLUMN_ENTRY, new_achievement);
        database.insert(DBOpenHelper.TABLE_ACHIEVEMENT, null, cv);
        et_new_achievement.setText("");
        fragment.onItemAdded();

        helper.close();
        database.close();
    }

    private Intent buildIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String entry = "";
        intent.setType("text/plain");
        for (int i = 0; i < shareItems.size(); i++) {
            entry += String.valueOf(i + 1) + ". " + shareItems.get(i) + "\n";
        }
        intent.putExtra(Intent.EXTRA_TEXT, entry);
        return intent;
    }

    private void alertMessage(int from) {
        String message = (from == GlobalVariables.ALERT_SHARE) ? message_share : message_add;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alert_title);
        builder.setMessage(message);
        builder.setPositiveButton(alert_button, null);
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (jobDispatcher != null) {
            jobDispatcher.cancel("testing_job");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        startJobScheduling();
    }

    private void startJobScheduling() {
        Bundle bundle = new Bundle();
        bundle.putString("test1", "testing message");

        jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job backgroundService = jobDispatcher.newJobBuilder()
                .setService(BackgroundManager.class)
                .setTag("testing_job")
                .setRecurring(false)
                .setTrigger(Trigger.executionWindow(0, 10))
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setExtras(bundle)
                .build();

        jobDispatcher.mustSchedule(backgroundService);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
