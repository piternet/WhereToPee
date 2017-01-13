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

    /* Common constants */
    private static final int DB_VERSION = 5;
    private static final String DB_NAME = "WhereToPeeDatabase";
    private static final String DB_USER_TABLE = "User";
    private static final String DB_COORDINATES_TABLE = "Coordinates";
    private static final String DB_TOILET_TABLE = "Toilet";

    public static final String KEY_ID = "id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;

    /* User database constants */
    public static final String KEY_NICKNAME = "nickname";
    public static final String NICKNAME_OPITONS = "TEXT NOT NULL";
    public static final int NICKNAME_COLUMN = 1;

    public static final String KEY_PHONEINFO = "phoneinfo";
    public static final String PHONEINFO_OPITONS = "TEXT"; // can be NULL
    public static final int PHONEINFO_COLUMN = 2;

    /* Coordinates database constants */
    public static final String KEY_LATITUDE = "latitude";
    public static final String LATITUDE_OPITONS = "REAL NOT NULL";
    public static final int LATITUDE_COLUMN = 1;

    public static final String KEY_LONGITUDE = "longitude";
    public static final String LONGITUDE_OPITONS = "REAL NOT NULL";
    public static final int LONGITUDE_COLUMN = 2;

    /* Toilet database constants */
    public static final String KEY_COORDINATES = "coordinates";
    public static final String COORDINATES_OPITONS = "INTEGER REFERENCES Coordinates(id) NOT NULL UNIQUE"; // can't have two toilets in the same location
    public static final int COORDINATES_COLUMN = 1;

    public static final String KEY_USERWHOADDED = "userWhoAdded";
    public static final String USERWHOADDED_OPITONS = "INTEGER REFERENCES User(id) NOT NULL";
    public static final int USERWHOADDED_COLUMN = 2;

    public static final String KEY_DESCRIPTION = "description";
    public static final String DESCRIPTION_OPITONS = "TEXT NOT NULL";
    public static final int DESCRIPTION_COLUMN = 3;

    public static final String KEY_HASCHANGINGTABLE = "hasChangingTable";
    public static final String HASCHANGINGTABLE_OPITONS = "INTEGER NOT NULL CHECK (hasChangingTable = 0 OR hasChangingTable = 1)";
    public static final int HASCHANGINGTABLE_COLUMN = 4;

    public static final String KEY_DISABLEDACCESSIBLE = "disabledAccesible";
    public static final String DISABLEDACCESSIBLE_OPITONS = "INTEGER NOT NULL CHECK (disabledAccesible = 0 OR disabledAccesible = 1)";
    public static final int DISABLEDACCESSIBLE_COLUMN = 5;

    public static final String KEY_ISFREE = "isFree";
    public static final String ISFREE_OPITONS = "INTEGER NOT NULL CHECK (isFree = 0 OR isFree = 1)";
    public static final int ISFREE_COLUMN = 6;

    public static final String KEY_ACCEPTEDBYADMIN = "acceptedByAdmin";
    public static final String ACCEPTEDBYADMIN_OPITONS = "INTEGER NOT NULL DEFAULT 0 CHECK (acceptedByAdmin = 0 OR acceptedByAdmin = 1)";
    public static final int ACCEPTEDBYADMIN_COLUMN = 7;

    /* Creates User table */
    private static final String DB_CREATE_USER_TABLE =
            "CREATE TABLE " + DB_USER_TABLE + "(" +
                    KEY_ID + " " + ID_OPTIONS + ", " +
                    KEY_NICKNAME + " " + NICKNAME_OPITONS + ", " +
                    KEY_PHONEINFO + " " + PHONEINFO_OPITONS +
                    ");";
    /* Drops User table */
    private static final String DB_DROP_USER_TABLE =
            "DROP TABLE IF EXISTS " + DB_USER_TABLE;

    /* Creates User table */
    private static final String DB_CREATE_COORDINATES_TABLE =
            "CREATE TABLE " + DB_COORDINATES_TABLE + "(" +
                    KEY_ID + " " + ID_OPTIONS + ", "+
                    KEY_LATITUDE + " " + LATITUDE_OPITONS + ", " +
                    KEY_LONGITUDE + " " + LONGITUDE_OPITONS +
                    ");";

    /* Drops User table */
    private static final String DB_DROP_COORDINATES_TABLE =
            "DROP TABLE IF EXISTS " + DB_COORDINATES_TABLE;

    /* Creates User table */
    private static final String DB_CREATE_TOILET_TABLE =
            "CREATE TABLE " + DB_TOILET_TABLE + "(" +
                    KEY_ID + " " + ID_OPTIONS + ", " +
                    KEY_COORDINATES + " " + COORDINATES_OPITONS + ", " +
                    KEY_USERWHOADDED + " " + USERWHOADDED_OPITONS + ", " +
                    KEY_DESCRIPTION + " " + DESCRIPTION_OPITONS + ", " +
                    KEY_HASCHANGINGTABLE + " " + HASCHANGINGTABLE_OPITONS + ", " +
                    KEY_DISABLEDACCESSIBLE + " " + DISABLEDACCESSIBLE_OPITONS + ", " +
                    KEY_ISFREE + " " + ISFREE_OPITONS + ", " +
                    KEY_ACCEPTEDBYADMIN + " " + ACCEPTEDBYADMIN_OPITONS +
                    ");";

    /* Drops User table */
    private static final String DB_DROP_TOILET_TABLE =
            "DROP TABLE IF EXISTS " + DB_TOILET_TABLE;

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
            db.execSQL(DB_CREATE_COORDINATES_TABLE);
            db.execSQL(DB_CREATE_TOILET_TABLE);

            Log.d(DEBUG_TAG, "Database creating...");
            Log.d(DEBUG_TAG, "Table " + DB_USER_TABLE + " ver." + DB_VERSION + " created");
            Log.d(DEBUG_TAG, "Table " + DB_COORDINATES_TABLE + " ver." + DB_VERSION + " created");
            Log.d(DEBUG_TAG, "Table " + DB_TOILET_TABLE + " ver." + DB_VERSION + " created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DB_DROP_USER_TABLE);
            db.execSQL(DB_DROP_COORDINATES_TABLE);
            db.execSQL(DB_DROP_TOILET_TABLE);

            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Table " + DB_USER_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "Table " + DB_COORDINATES_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "Table " + DB_TOILET_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
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

    public boolean isEmpty() { // checks only TOILET table
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_TOILET_TABLE, null);
        if (cursor.moveToFirst())
            return false;
        return true;
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
        String where = KEY_ID + "=" + id;
        Cursor cursor = db.query(DB_USER_TABLE, columns, where, null, null, null, null);
        User user = null; // to return
        if(cursor != null && cursor.moveToFirst()) {
            String nickname = cursor.getString(NICKNAME_COLUMN);
            String phoneInfo = cursor.getString(PHONEINFO_COLUMN);
            user = new User(id, nickname, phoneInfo);
        }
        return user;
    }

    /* COORDINATES */
    public long insertCoordinates(double latitude, double longitude) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LATITUDE, latitude);
        contentValues.put(KEY_LONGITUDE, longitude);
        return db.insert(DB_COORDINATES_TABLE, null, contentValues);
    }

    public boolean deleteCoordinates(long id) {
        String where = KEY_ID + "=" + id;
        return db.delete(DB_COORDINATES_TABLE, where, null) > 0;
    }

    public Cursor getAllCoordinates() {
        String[] columns = {KEY_ID, KEY_LATITUDE, KEY_LONGITUDE};
        return db.query(DB_COORDINATES_TABLE, columns, null, null, null, null, null);
    }

    public Coordinates getCoordinates(long id) {
        String[] columns = {KEY_ID, KEY_LATITUDE, KEY_LONGITUDE};
        String where = KEY_ID + "=" + id;
        Cursor cursor = db.query(DB_COORDINATES_TABLE, columns, where, null, null, null, null);
        Coordinates coordinates = null;
        if(cursor != null && cursor.moveToFirst()) {
            double latitude = cursor.getDouble(LATITUDE_COLUMN);
            double longitude = cursor.getDouble(LONGITUDE_COLUMN);
            coordinates = new Coordinates(id, latitude, longitude);
        }
        return coordinates;
    }

    /* TOILET */
    public long insertToilet(Coordinates coordinates, User user, String description, boolean isFree, boolean hasChangingTable,
                             boolean disabledAccesible, boolean acceptedByAdmin) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_COORDINATES, coordinates.getId());
        contentValues.put(KEY_USERWHOADDED, user.getId());
        contentValues.put(KEY_DESCRIPTION, description);
        contentValues.put(KEY_ISFREE, isFree);
        contentValues.put(KEY_HASCHANGINGTABLE, hasChangingTable);
        contentValues.put(KEY_DISABLEDACCESSIBLE, disabledAccesible);
        contentValues.put(KEY_ACCEPTEDBYADMIN, acceptedByAdmin);
        return db.insert(DB_TOILET_TABLE, null, contentValues);
    }

    public boolean deleteToilet(long id) {
        String where = KEY_ID + "=" + id;
        return db.delete(DB_TOILET_TABLE, where, null) > 0;
    }

    public Cursor getAllToilets() {
        String[] columns = {KEY_ID, KEY_COORDINATES, KEY_USERWHOADDED, KEY_DESCRIPTION, KEY_ISFREE,
                KEY_HASCHANGINGTABLE, KEY_DISABLEDACCESSIBLE, KEY_ACCEPTEDBYADMIN};
        return db.query(DB_TOILET_TABLE, columns, null, null, null, null, null);
    }

    public Toilet getToilet(long id) {
        String[] columns = {KEY_ID, KEY_COORDINATES, KEY_USERWHOADDED, KEY_DESCRIPTION, KEY_ISFREE,
                KEY_HASCHANGINGTABLE, KEY_DISABLEDACCESSIBLE, KEY_ACCEPTEDBYADMIN};
        String where = KEY_ID + "=" + id;
        Cursor cursor = db.query(DB_TOILET_TABLE, columns, where, null, null, null, null);
        Toilet toilet = null;
        if(cursor != null && cursor.moveToFirst()) {
            Coordinates coordinates = getCoordinates(cursor.getLong(COORDINATES_COLUMN));
            User user = getUser(cursor.getLong(USERWHOADDED_COLUMN));
            String description = cursor.getString(DESCRIPTION_COLUMN);
            boolean isFree = cursor.getInt(ISFREE_COLUMN) != 0;
            boolean hasChangingTable = cursor.getInt(HASCHANGINGTABLE_COLUMN) != 0;
            boolean disabledAccesible = cursor.getInt(DISABLEDACCESSIBLE_COLUMN) != 0;
            boolean acceptedByAdmin = cursor.getInt(ACCEPTEDBYADMIN_COLUMN) != 0;
            toilet = new Toilet(id, coordinates, user, description, isFree, hasChangingTable, disabledAccesible, acceptedByAdmin);
        }
        return toilet;
    }
}