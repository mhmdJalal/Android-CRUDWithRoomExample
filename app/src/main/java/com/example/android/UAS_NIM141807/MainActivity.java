package com.example.android.UAS_NIM141807;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.UAS_NIM141807.room.Contact;
import com.example.android.UAS_NIM141807.room.ContactViewModel;


public class MainActivity extends AppCompatActivity {

    public static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;

    private ContactViewModel mContactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        Button new_contact = findViewById(R.id.button_new_contact);
        new_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormContactActivity.class);
                intent.putExtra("new_contact", true);
                startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
            }
        });

        Button see_contact = findViewById(R.id.button_see_contact);
        see_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListContactActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(FormContactActivity.EXTRA_NAME);
            String address = data.getStringExtra(FormContactActivity.EXTRA_ADDRESS);
            String phone = data.getStringExtra(FormContactActivity.EXTRA_PHONE);

            Contact contact = new Contact(name, address, phone);
            mContactViewModel.insert(contact);
            Toast.makeText(this, "Kontak berhasil disimpan", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
