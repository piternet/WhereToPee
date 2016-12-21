package net.nosek.wheretopee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter {
    private static final String DEBUG_TAG = "SQL Lite debug";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";
    private static final String DB_USER_TABLE = "User";

    public static final String KEY_ID = "id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY NOT NULL AUTOINCREMENT";
    public static final int ID_COLUMN = 0;

    public static final String KEY_NICKNAME = "nickname";
    public static final String NICKNAME_OPITONS = "TEXT NOT NULL";
    public static final int NICKNAME_COLUMN = 1;

    public static final String KEY_PHONEINFO = "phoneinfo";
    public static final String PHONEINFO_OPITONS = "TEXT"; // can be NULL
    public static final int PHONEINFO_COLUMN = 2;

    /* CREATE TABLE User(id INTEGER PRIMARY KEY NOT NULL AUTOINCREMENT, nickname TEXT NOT NULL, phoneinfo TEXT); */
    private static final String DB_CREATE_USER_TABLE =
            "CREATE_TABLE" + DB_USER_TABLE + "(" +
             KEY_ID + " " + ID_OPTIONS + ", " +
             KEY_NICKNAME + " " + NICKNAME_OPITONS + ", " +
             KEY_PHONEINFO + " " + PHONEINFO_OPITONS +
             ");";
    /* DROP TABLE IF EXISTS User */
    private static final String DB_DROP_USER_TABLE =
            "DROP TABLE IF EXISTS " + DB_USER_TABLE;

    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_USER_TABLE);

            Log.d(DEBUG_TAG, "Database creating...");
            Log.d(DEBUG_TAG, "Table " + DB_USER_TABLE + " ver." + DB_VERSION + " created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DB_DROP_USER_TABLE);

            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Table " + DB_USER_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost.");

            onCreate(db);
        }
    }

    public DatabaseAdapter(Context context) {
        this.context = context;
    }

    public DatabaseAdapter open() {
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch(SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        dbHelper.close();
    }
}
