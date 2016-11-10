package com.emilio.contactapplication.service.eventbus;

import com.emilio.contactapplication.data.models.Contact;

import java.util.List;

/**
 * Created by Emilio on 09/11/2016.
 */
public class ContactsEvent {
    private final List<Contact> contacts;

    public ContactsEvent(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
