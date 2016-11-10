package com.emilio.contactapplication.data.realm;

import com.emilio.contactapplication.data.models.Contact;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Emilio on 09/11/2016.
 */

public class ContactRealm {

    public static void createContact(Contact contact){
        Realm realm = Realm.getDefaultInstance();
        realm.copyToRealmOrUpdate(contact);
        realm.close();
    }

    public static Contact searchContact(String query){
        Realm realm = Realm.getDefaultInstance();
        try {
            return realm.copyFromRealm(realm.where(Contact.class)
                    .equalTo("name", query).or()
                    .equalTo("number", query).findFirst());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            realm.close();
        }
        return null;
    }


    public static List<Contact> getAllContacts(){
        Realm realm = Realm.getDefaultInstance();
        try {
            return realm.copyFromRealm(realm.where(Contact.class)
                    .findAll());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            realm.close();
        }
        return null;

    }

}
