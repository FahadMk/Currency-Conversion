package com.dgsl.currencyconverter.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CurrencyDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CurrencyDB";
    public static final String CURRENCY_TB = "CurrencyTB";
    private static final String KEY_ID = "id";
    private static final String KEY_CURRENCY_CODE = "currency_name";
    private static final String KEY_CURRENCY_OBJECT = "currency_data";

    public CurrencyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CURRENCY_DATA_TABLE = "CREATE TABLE " + CURRENCY_TB + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CURRENCY_CODE + " TEXT UNIQUE,"
                + KEY_CURRENCY_OBJECT + " TEXT" + ")";
        db.execSQL(CREATE_CURRENCY_DATA_TABLE);

        //ContentValues row = new ContentValues();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + CURRENCY_TB);
        // Create tables again
        onCreate(db);
    }

    public void addCurrencyData(String currencyCode, String currencyDataObject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CURRENCY_CODE, currencyCode); // Contact Name
        values.put(KEY_CURRENCY_OBJECT, currencyDataObject ); // Contact Phone

        // Inserting Row
        db.insert(CURRENCY_TB, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public int updateCurrencyData(String currencyCode, String currencyDataObject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CURRENCY_CODE, currencyCode);
        values.put(KEY_CURRENCY_OBJECT, currencyDataObject);

        // updating row
        return db.update(CURRENCY_TB, values, KEY_CURRENCY_CODE + " = ?",
                new String[] { String.valueOf(currencyCode)});
    }

    public void deleteCurrencyData(String currencyCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CURRENCY_TB, KEY_CURRENCY_CODE + " = ?",
                new String[] { String.valueOf(currencyCode) });
        db.close();
    }

    public String getCurrencyData(String currencyCode) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CURRENCY_TB, new String[] { KEY_ID,
                        KEY_CURRENCY_CODE, KEY_CURRENCY_OBJECT }, KEY_CURRENCY_CODE + "=?",
                new String[] { String.valueOf(currencyCode) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
//
//        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
        // return contact
        return cursor.getString(2);
    }

}