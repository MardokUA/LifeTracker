package laktionov.lifetracker.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import laktionov.lifetracker.R;
import laktionov.lifetracker.data.DBOpenHelper;

public class WishActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    private DBOpenHelper helper;
    private SQLiteDatabase database;

    @BindView(R.id.tb_new_wish)
    Toolbar toolbar;
    @BindView(R.id.et_new_wish)
    EditText editText;
    @BindString(R.string.alert_message_add)
    String message_add;
    @BindString(R.string.alert_positive_button)
    String alert_button;
    @BindString(R.string.alert_title)
    String alert_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        editText = (EditText) findViewById(R.id.et_new_wish);
        helper = new DBOpenHelper(this);
        database = helper.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_wish, menu);
        MenuItem item = menu.findItem(R.id.action_add);
        item.setOnMenuItemClickListener(this);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                String new_wish;
                new_wish = editText.getText().toString();
                if (new_wish.isEmpty()) {
                    alertMessage();
                } else {
                    ContentValues cv = new ContentValues();
                    cv.put(DBOpenHelper.COLUMN_ENTRY, new_wish);
                    database.insert(DBOpenHelper.TABLE_WISH, null, cv);

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                break;
        }
        return true;
    }

    @Override
    protected void onStop() {
        database.close();
        helper.close();
        super.onStop();
    }

    public void alertMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alert_title);
        builder.setMessage(message_add);
        builder.setPositiveButton(alert_button, null);
        builder.show();
    }
}
