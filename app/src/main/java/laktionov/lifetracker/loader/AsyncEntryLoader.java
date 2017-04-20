package laktionov.lifetracker.loader;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import laktionov.lifetracker.data.DBOpenHelper;
import laktionov.lifetracker.model.Item;
import laktionov.lifetracker.utils.GlobalVariables;

public class AsyncEntryLoader extends AsyncTask<Void, Void, Void> {

    private DBOpenHelper helper;
    private SQLiteDatabase database;
    private List<Item> listItems;
    private Context context;
    private int from;
    private RecyclerView.Adapter adapter;

    public AsyncEntryLoader(Context context, List<Item> listItems, RecyclerView.Adapter adapter, int from) {
        this.context = context;
        this.from = from;
        this.listItems = listItems;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        listItems.clear();

        helper = new DBOpenHelper(context);
        database = helper.getWritableDatabase();
        Cursor cursor = null;

        if (from == GlobalVariables.FROM_WISH_FRAGMENT) {
            cursor = database.query(DBOpenHelper.TABLE_WISH, null, null, null, null, null, null, null);
        }
        if (from == GlobalVariables.FROM_ACHIEVEMENT_FRAGMENT) {
            cursor = database.query(DBOpenHelper.TABLE_ACHIEVEMENT, null, null, null, null, null, null, null);
        }
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_ID));
            String entry = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_ENTRY));
            Item item = new Item(id, entry);
            listItems.add(item);
        }
        cursor.close();
        database.close();
        helper.close();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        adapter.notifyDataSetChanged();
    }
}
