package com.example.android.UAS_NIM141807;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.UAS_NIM141807.room.Contact;

import java.util.List;


public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView textName;
        private final TextView textAddress;
        private final TextView textPhone;

        private ContactViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            textAddress = itemView.findViewById(R.id.text_address);
            textPhone = itemView.findViewById(R.id.text_phone);
        }
    }

    private final LayoutInflater mInflater;
    private List<Contact> mContacts;
    private ContactEvents mListener;

    ContactListAdapter(Context context, ContactEvents mListener) {
        mInflater = LayoutInflater.from(context);
        this.mListener = mListener;
    }

    @Override
    public @NonNull ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (mContacts != null) {
            Contact current = mContacts.get(position);
            Log.e("CONTACT", "contact " + position + ", " + current.getName() + ", " + current.getAddress() + ", " + current.getPhone());
            holder.textName.setText(current.getName());
            holder.textAddress.setText(current.getAddress());
            holder.textPhone.setText(current.getPhone());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemViewClick(current);
                }
            });
        }
    }

    void setContacts(List<Contact> mContacts) {
        this.mContacts = mContacts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mContacts != null)
            return mContacts.size();
        else return 0;
    }

    interface ContactEvents {
        void onItemViewClick(Contact contact);
    }
}


