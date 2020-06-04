package com.example.android.UAS_NIM141807;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.UAS_NIM141807.room.Contact;
import com.example.android.UAS_NIM141807.room.ContactViewModel;

import java.util.List;

public class ListContactActivity extends AppCompatActivity implements ContactListAdapter.ContactEvents {

    public static final int UPDATE_CONTACT_ACTIVITY_REQUEST_CODE = 1;

    private ContactViewModel mContactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ContactListAdapter adapter = new ContactListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        mContactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable final List<Contact> contacts) {
                if (contacts != null) {
                    if (!contacts.isEmpty()) {
                        adapter.setContacts(contacts);
                    }
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            boolean isDelete = data.getBooleanExtra("DELETE", false);
            int id = data.getIntExtra(FormContactActivity.EXTRA_ID, 0);
            if (isDelete) {
                Contact contact = (Contact) data.getSerializableExtra(FormContactActivity.EXTRA_CONTACT);
                mContactViewModel.delete(contact);
                Toast.makeText(this, "Kontak berhasil dihapus", Toast.LENGTH_SHORT).show();
            } else  {
                String name = data.getStringExtra(FormContactActivity.EXTRA_NAME);
                String address = data.getStringExtra(FormContactActivity.EXTRA_ADDRESS);
                String phone = data.getStringExtra(FormContactActivity.EXTRA_PHONE);

                Contact contact = new Contact(name, address, phone);
                contact.setId(id);
                mContactViewModel.update(contact);
                Toast.makeText(this, "Kontak berhasil diubah", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemViewClick(Contact contact) {
        Intent intent = new Intent(this, FormContactActivity.class);
        intent.putExtra(FormContactActivity.EXTRA_CONTACT, contact);
        startActivityForResult(intent, UPDATE_CONTACT_ACTIVITY_REQUEST_CODE);
    }
}
