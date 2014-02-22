package com.astrolabe.iremote;

/**
 * Created by Abu-Umar on 12/22/13.
 for $(COMPANY)
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

public class accountsDB {

    //private variables
    public static final String KEY_ROWID = "_id";
    public static final String KEY_CTYPE = "c_type";
    public static final String KEY_SNAME = "s_name";
    public static final String KEY_SNUMB = "s_numb";
    public static final String KEY_USRN = "user_name";
    public static final String KEY_PASS = "password";
    public static final String KEY_EMPTY = "empty";

    private static final String DATABASE_NAME = "sites.db";
    private static final String DATABASE_TABLE = "sites";
    private static final int DATABASE_VERSION = 111;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public long createEntry(String cType, String siteName, String siteNum, String username, String pass) {

        ContentValues cv = new ContentValues();
        cv.put(KEY_CTYPE, cType);
        cv.put(KEY_SNAME, siteName);
        cv.put(KEY_SNUMB, siteNum);
        cv.put(KEY_USRN, username);
        cv.put(KEY_PASS, pass);
        //cv.put(KEY_EMPTY,0);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public void updateEntry(long i, String cType, String siteName, String siteNum, String username, String pass) {
        Log.e("TEST", "lets update entry");
        ContentValues cv = new ContentValues();
        cv.put(KEY_CTYPE, cType);
        cv.put(KEY_SNAME, siteName);
        cv.put(KEY_SNUMB, siteNum);
        cv.put(KEY_USRN, username);
        cv.put(KEY_PASS, pass);
        //cv.put(KEY_EMPTY,0);

        ourDatabase.update(DATABASE_TABLE, cv, KEY_ROWID + "=" + i, null);
        Log.e("TEST", "update done" + KEY_ROWID + 1);
        //Log.d("DMMM", "update done");
        //support sc= new support(ourContext);
        //sc.showDialog("update done");
        //return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public void deleteEntry(long i) {
        if (i < 0 || i > getProfilesCount() - 1) main.setAccount(0);
        //String[] columns = new String[]{KEY_ROWID, KEY_CTYPE, KEY_SNAME, KEY_SNUMB, KEY_USRN, KEY_PASS};
        Cursor cursor = ourDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            for (int x = 0; x < i; x++) {
                cursor.moveToNext();
            }
            long rowIds = cursor.getLong(0);
            ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowIds, null);
        }
        //  ourDatabase.delete(DATABASE_TABLE, KEY_SNUMB + "=" + siteNum, null);
        //return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }


    public String getName(long i) {
        if (i < 0 || i > getProfilesCount() - 1) main.setAccount(0);
        // String[] columns = new String[]{KEY_ROWID, KEY_CTYPE, KEY_SNAME, KEY_SNUMB, KEY_USRN, KEY_PASS};
        Cursor cursor = ourDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            for (int x = 0; x < i; x++) {
                cursor.moveToNext();
            }
            String name = cursor.getString(2);
            cursor.close();
            return name;
        }
        assert cursor != null;
        cursor.close();
        return null;
    }

    public String getSite(long i) {
        if (i < 0 || i > getProfilesCount() - 1) {
            main.setAccount(0);
            return "";
        }
        String[] columns = new String[]{KEY_ROWID, KEY_CTYPE, KEY_SNAME, KEY_SNUMB, KEY_USRN, KEY_PASS};
        Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            for (int x = 0; x < i; x++) {
                cursor.moveToNext();
            }
            String name = cursor.getString(3);
            cursor.close();
            return name;
        }
        assert cursor != null;
        cursor.close();
        return null;
    }

    public String getUserName(long i) {
        if (i < 0 || i > getProfilesCount() - 1) {
            main.setAccount(0);
            return "";
        }
        String[] columns = new String[]{KEY_ROWID, KEY_CTYPE, KEY_SNAME, KEY_SNUMB, KEY_USRN, KEY_PASS};
        Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            for (int x = 0; x < i; x++) {
                cursor.moveToNext();
            }
            String name = cursor.getString(4);
            cursor.close();
            return name;
        }
        assert cursor != null;
        cursor.close();
        return null;
    }

    public String getPass(long i) {
        if (i < 0 || i > getProfilesCount() - 1) {
            main.setAccount(0);
            return "";
        }
        String[] columns = new String[]{KEY_ROWID, KEY_CTYPE, KEY_SNAME, KEY_SNUMB, KEY_USRN, KEY_PASS};
        Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            for (int x = 0; x < i; x++) {
                cursor.moveToNext();
            }
            String name = cursor.getString(5);
            cursor.close();
            return name;
        }
        assert cursor != null;
        cursor.close();
        return null;
    }

    public String getCType(long i) {
        if (i < 0 || i > getProfilesCount() - 1) {
            main.setAccount(0);
            return "";
        }
        Cursor cursor = ourDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            for (int x = 0; x < i; x++) {
                cursor.moveToNext();
            }
            String name = cursor.getString(1);
            cursor.close();
            return name;
        }
        assert cursor != null;
        cursor.close();
        return null;
    }

    public accountsDB Open() {
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }


    private class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_CTYPE + " TEXT NOT NULL,"
                    + KEY_SNAME + " TEXT NOT NULL,"
                    + KEY_SNUMB + " TEXT NOT NULL,"
                    + KEY_USRN + " TEXT NOT NULL,"
                    + KEY_PASS + " TEXT NOT NULL,"
                    + KEY_EMPTY + " INTEGER);"
            );
            /*for(int x=1;x<9;x++){
                createEntry("Account"+ String.valueOf(x),"","","","");

            }*/

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);

        }
    }

    public accountsDB(Context c) {
        ourContext = c;
    }

    public accountsDB open() throws SQLException {
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + DATABASE_TABLE;
//        SQLiteDatabase db = ourHelper.getReadableDatabase();
        Cursor cursor = ourDatabase.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

}