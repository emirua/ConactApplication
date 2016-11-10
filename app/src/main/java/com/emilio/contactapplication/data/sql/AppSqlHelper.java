package com.emilio.contactapplication.data.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Emilio on 07/11/2016.
 */

public class AppSqlHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "contactsDatabase";
    private static final int DATABASE_VERSION = 2;


    public AppSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ContactTable.onCreate(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ContactTable.onUpgrade(db);
    }
}
