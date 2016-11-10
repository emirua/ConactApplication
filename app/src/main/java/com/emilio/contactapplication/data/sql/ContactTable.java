package com.emilio.contactapplication.data.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.emilio.contactapplication.data.models.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emilio on 07/11/2016.
 */

public class ContactTable {

    private static final String TABLENAME = "contactsTable";
    private static final String CONTACTNAME = "contactName";
    private static final String CONTACTNUNMBER = "contactNunmber";
    private static final String CONTACTIMAGE = "contactImage";

    public static void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLENAME +  "("
                + CONTACTNUNMBER + " TEXT PRIMARY KEY,"
                + CONTACTNAME + " TEXT NOT NULL,"
                + CONTACTIMAGE + " TEXT);");
    }

    public static void onUpgrade(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(sqLiteDatabase);
    }

    public static void createContact(AppSqlHelper appSqlHelper, Contact contact){
        ContentValues contentValues = getContactContentValues(contact);
        SQLiteDatabase database = appSqlHelper.getWritableDatabase();
        database.insert(TABLENAME, null, contentValues);
        database.close();
    }

    public static Contact searchContact(AppSqlHelper appSqlHelper, String query){
        SQLiteDatabase database = appSqlHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(
                "SELECT * FROM "+ TABLENAME + " WHERE "
                        + CONTACTNAME + "= ? OR "
                        + CONTACTNUNMBER + "= ?",
                new String[]{query, query});
        if (cursor.moveToFirst()){
            return cursorToContact(cursor);
        }

        return null;

    }


    public static List<Contact> getAllContacts(AppSqlHelper appSqlHelper){
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase database = appSqlHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(
                "SELECT * FROM "+ TABLENAME,
                null);
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
            contacts.add(cursorToContact(cursor));
            cursor.moveToNext();
            }
        }

        return contacts;

    }



    private static Contact cursorToContact(Cursor cursor) {
        String contactName= cursor.getString(cursor.getColumnIndex(CONTACTNAME));
        String contactNumber = cursor.getString(cursor.getColumnIndex(CONTACTNUNMBER));
        String contactImage= cursor.getString(cursor.getColumnIndex(CONTACTIMAGE));
        return new Contact(contactName, contactNumber, contactImage);
    }

    private static ContentValues getContactContentValues(Contact contact) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTNAME, contact.getName());
        contentValues.put(CONTACTNUNMBER, contact.getNumber());
        contentValues.put(CONTACTIMAGE, contact.getNumber());
        return contentValues;
    }
}
