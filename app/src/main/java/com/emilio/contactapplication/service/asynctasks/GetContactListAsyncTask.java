package com.emilio.contactapplication.service.asynctasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.emilio.contactapplication.data.models.Contact;
import com.emilio.contactapplication.data.sql.AppSqlHelper;
import com.emilio.contactapplication.data.sql.ContactTable;
import com.emilio.contactapplication.service.eventbus.ContactsEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Emilio on 09/11/2016.
 */

public class GetContactListAsyncTask extends AsyncTask<Void, Void, List<Contact>> {

    private final Context context;

    public GetContactListAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Contact> doInBackground(Void... params) {
        List<Contact> contacts = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            Contact contactModel = ContactToOwnContactModel(cursor);
            if (contactModel != null) {
                contacts.add(contactModel);
            }
        }
        if (cursor != null) {
            cursor.close();
        }

        contacts.addAll(ContactTable.getAllContacts(new AppSqlHelper(context)));

        return contacts;
    }


    @Override
    protected void onPostExecute(List<Contact> contacts) {
        EventBus.getDefault().post(new ContactsEvent(contacts));
    }

    public Contact ContactToOwnContactModel(Cursor cursor) {
        Contact contactModel = new Contact();
        int hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        if (hasPhoneNumber == 1) {
            contactModel.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            String PATTERN = "[^\\d]";
            Pattern pattern = Pattern.compile(PATTERN);
            String regularNumber = pattern.matcher(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))).replaceAll("");
            contactModel.setNumber(regularNumber);
            contactModel.setImage(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)));
        } else {
            return null;
        }
        return contactModel;
    }
}
