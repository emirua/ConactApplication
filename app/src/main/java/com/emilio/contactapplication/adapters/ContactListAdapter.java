package com.emilio.contactapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emilio.contactapplication.R;
import com.emilio.contactapplication.data.models.Contact;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Emilio on 17/10/2016.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private final Context context;
    private List<Contact> contacts;
    private LayoutInflater layoutInflater;

    public ContactListAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.contacts_list_item, parent));
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(contacts.get(position));

    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.contact_name_text_view)
        TextView contactNameTextView;
        @BindView(R.id.contact_number_text_view)
        TextView contactNumberTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        public void setData(Contact contact) {
            contactNameTextView.setText(contact.getName());
            contactNumberTextView.setText(String.valueOf(contact.getNumber()));

        }
    }
}
