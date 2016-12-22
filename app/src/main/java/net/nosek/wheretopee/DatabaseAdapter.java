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
    private static final String DB_NAME = "WhereToPeeDatabase";
    private static final String DB_USER_TABLE = "User";

    public static final String KEY_ID = "id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;

    public static final String KEY_NICKNAME = "nickname";
    public static final String NICKNAME_OPITONS = "TEXT NOT NULL";
    public static final int NICKNAME_COLUMN = 1;

    public static final String KEY_PHONEINFO = "phoneinfo";
    public static final String PHONEINFO_OPITONS = "TEXT"; // can be NULL
    public static final int PHONEINFO_COLUMN = 2;

    /* CREATE TABLE User(id INTEGER PRIMARY KEY AUTOINCREMENT, nickname TEXT NOT NULL, phoneinfo TEXT); */
    private static final String DB_CREATE_USER_TABLE =
            "CREATE TABLE " + DB_USER_TABLE + "(" +
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

    /* SAVING & READING TO / FROM DATABASE */
    public long insertUser(String nickname, String phoneInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NICKNAME, nickname);
        contentValues.put(KEY_PHONEINFO, phoneInfo);
        return db.insert(DB_USER_TABLE, null, contentValues);
    }

    /* returns true if deletion was succesful, false otherwise */
    public boolean deleteUser(long id) {
        String where = KEY_ID + "=" + id;
        return db.delete(DB_USER_TABLE, where, null) > 0;
    }

    public Cursor getAllUsers() {
        String[] columns = {KEY_ID, KEY_NICKNAME, KEY_PHONEINFO};
        return db.query(DB_USER_TABLE, columns, null, null, null, null, null);
    }

    public User getUser(long id) {
        String[] columns = {KEY_ID, KEY_NICKNAME, KEY_PHONEINFO};
        String where = KEY_ID + "" + id;
        Cursor cursor = db.query(DB_USER_TABLE, columns, where, null, null, null, null);
        User user = null; // to return
        if(cursor != null && cursor.moveToFirst()) {
            String nickname = cursor.getString(NICKNAME_COLUMN);
            String phoneInfo = cursor.getString(PHONEINFO_COLUMN);
            user = new User(id, nickname, phoneInfo);
        }
        return user;
    }

}
