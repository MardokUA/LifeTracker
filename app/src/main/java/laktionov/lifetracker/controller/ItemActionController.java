package laktionov.lifetracker.controller;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import laktionov.lifetracker.data.DBOpenHelper;
import laktionov.lifetracker.model.Item;
import laktionov.lifetracker.utils.GlobalVariables;

public class ItemActionController {

    private List<String> shareItems;
    private Context context;

    private static ItemActionController instance;

    private ItemActionController(Context context) {
        this.context = context;
        shareItems = new ArrayList<>();
    }

    public static ItemActionController getInstance(Context context) {
        if (instance == null) {
            instance = new ItemActionController(context);
        }
        return instance;
    }

    public List<String> getShareItems() {
        return shareItems;
    }

    public void deleteItemFromDB(List<Item> items, int position, int from) {
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        if (from == GlobalVariables.FROM_WISH) {
            db.delete(DBOpenHelper.TABLE_WISH, "_id = ?", new String[]{String.valueOf(items.get(position).getId())});
        }
        if (from == GlobalVariables.FROM_ACHIEVEMENT) {
            db.delete(DBOpenHelper.TABLE_ACHIEVEMENT, "_id = ?", new String[]{String.valueOf(items.get(position).getId())});
        }
        helper.close();
        db.close();
    }

    public void itemSelect(View v, List<Item> items, int position) {
        v.setSelected(!v.isSelected());
        String share;
        if (v.isSelected()) {
            v.setAlpha(0.5f);
            share = items.get(position).getEntry();
            shareItems.add(share);
            Log.i("TAG", shareItems.toString());
        }
        if (!v.isSelected()) {
            v.setAlpha(1f);
            shareItems.remove(items.get(position).getEntry());
            Log.i("TAG", shareItems.toString());

        }
    }
}
