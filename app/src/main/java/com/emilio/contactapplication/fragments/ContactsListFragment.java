package com.emilio.contactapplication.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.emilio.contactapplication.R;
import com.emilio.contactapplication.adapters.ContactListAdapter;
import com.emilio.contactapplication.data.models.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsListFragment extends Fragment {


    @BindView(R.id.fragment_contact_list_toolbar)
    Toolbar fragmentContactListToolbar;
    @BindView(R.id.fragment_contact_list_recycler_view)
    RecyclerView fragmentContactListRecyclerView;
    private ContactListAdapter contactListAdapter;
    private ArrayList<Contact> contacts;

    public ContactsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);
        ButterKnife.bind(this, view);
        loadRecyclerView();
        loadListeners();
        return view;
    }


    private void loadRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        contactListAdapter = new ContactListAdapter(getContext(), getContacList());

        fragmentContactListRecyclerView.setLayoutManager(llm);
        fragmentContactListRecyclerView.setAdapter(contactListAdapter);


    }

    private void loadListeners() {
        contactListAdapter.setOnCallButtonListener(new ContactListAdapter.OnCallButtonListener() {
            @Override
            public void onClick(int position) {
                callContactNumber(contactListAdapter.getContact(position).getNumber());
            }
        });
    }

    private void callContactNumber(String number) {
        Toast.makeText(getContext(), "llamando "+ number, Toast.LENGTH_LONG).show();
    }


    public List<Contact> getContacList() {
        contacts = new ArrayList<>();
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            Contact contactModel = ContactToOwnContactModel(cursor);
            if (contactModel != null) {
                contacts.add(contactModel);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return contacts;
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
