package laktionov.lifetracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE = "entry_database";
    public static final String TABLE_WISH = "WISH";
    public static final String TABLE_ACHIEVEMENT = "ACHIEVEMENT";
    public static final String COLUMN_ENTRY = "entry";
    public static final String COLUMN_ID = "_id";

    public DBOpenHelper(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_WISH + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ENTRY + " TEXT" + ");");
        db.execSQL("CREATE TABLE " + TABLE_ACHIEVEMENT + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ENTRY + " TEXT" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
