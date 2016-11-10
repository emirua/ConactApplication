package com.emilio.contactapplication.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.emilio.contactapplication.R;
import com.emilio.contactapplication.adapters.ContactListAdapter;
import com.emilio.contactapplication.data.models.Contact;
import com.emilio.contactapplication.data.sql.AppSqlHelper;
import com.emilio.contactapplication.data.sql.ContactTable;
import com.emilio.contactapplication.service.asynctasks.GetContactListAsyncTask;
import com.emilio.contactapplication.service.eventbus.ContactsEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

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
    private List<Contact> contacts = new ArrayList<>();
    private AppSqlHelper appSqlHelper;
    private Context applicationContext;

    public ContactsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        loadData();
        loadToolbar();
        loadRecyclerView();
        loadListeners();
        return view;
    }

    private void loadData() {
        applicationContext = getContext().getApplicationContext();
        appSqlHelper = new AppSqlHelper(applicationContext);
        new GetContactListAsyncTask(applicationContext).execute();
    }

    private void loadToolbar() {
        fragmentContactListToolbar.setTitle(getActivity().getString(R.string.contacts));
        fragmentContactListToolbar.setNavigationIcon(R.drawable.ic_toolbar);
        fragmentContactListToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Adios", Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();
            }
        });
        fragmentContactListToolbar.inflateMenu(R.menu.main_menu);
        fragmentContactListToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_contact:
                        addContact();
                }
                return true;
            }
        });
    }

    private void addContact() {
        Contact contact = new Contact("Alguien", "55555235314", "");
        ContactTable.createContact(appSqlHelper, contact);
        loadData();
    }


    private void loadRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        contactListAdapter = new ContactListAdapter(getContext(), contacts);

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

    @Subscribe
    public void onEvent(ContactsEvent contactsEvent){
        contacts = contactsEvent.getContacts();
        loadRecyclerView();
        loadListeners();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
