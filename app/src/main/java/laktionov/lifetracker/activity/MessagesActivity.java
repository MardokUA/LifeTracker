package laktionov.lifetracker.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import laktionov.lifetracker.R;

public class MessagesActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.et_chat_message)
    EditText et_chat_message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        tabLayout.setVisibility(View.GONE);
        tabLayout.setEnabled(false);
        // hide keyboard while user will not touch Edit Text area
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        MenuItem action_share = menu.findItem(R.id.action_share);
        MenuItem action_notify = menu.findItem(R.id.action_notify);
        MenuItem action_pick_time = menu.findItem(R.id.action_pick_time);
        MenuItem action_chat = menu.findItem(R.id.action_chat);

        action_chat.setEnabled(false);
        action_chat.setVisible(false);

        action_share.setOnMenuItemClickListener(this);
        action_notify.setOnMenuItemClickListener(this);
        action_pick_time.setOnMenuItemClickListener(this);

        // Init Firebase database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("chat");

        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
