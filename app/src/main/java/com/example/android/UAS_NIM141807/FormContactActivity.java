package com.example.android.UAS_NIM141807;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.UAS_NIM141807.room.Contact;

public class FormContactActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.android.uas.contact.ID";
    public static final String EXTRA_NAME = "com.example.android.uas.contact.NAME";
    public static final String EXTRA_ADDRESS = "com.example.android.uas.contact.ADDRESS";
    public static final String EXTRA_PHONE = "com.example.android.uas.contact.PHONE";
    public static final String EXTRA_CONTACT = "com.example.android.uas.CONTACT";

    private EditText mEditNameView;
    private EditText mEditAddressView;
    private EditText mEditPhoneView;
    private Button buttonSave;
    private Button buttonDelete;
    private Button buttonBack;

    private Boolean isNewContact = false;
    private Contact mContact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_contact);

        mEditNameView = findViewById(R.id.edit_name);
        mEditAddressView = findViewById(R.id.edit_address);
        mEditPhoneView = findViewById(R.id.edit_phone);
        buttonSave = findViewById(R.id.button_save);
        buttonBack = findViewById(R.id.button_back);
        buttonDelete = findViewById(R.id.button_delete);

        isNewContact = getIntent().getBooleanExtra("new_contact", false);

        if (isNewContact) {
            buttonSave.setText("Simpan");
        } else {
            buttonSave.setText("Ubah");
            mContact = (Contact) getIntent().getSerializableExtra(EXTRA_CONTACT);
            buttonDelete.setVisibility(View.VISIBLE);

            if (mContact != null) {
                mEditNameView.setText(mContact.getName());
                mEditAddressView.setText(mContact.getAddress());
                mEditPhoneView.setText(mContact.getPhone());
            }
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditNameView.getText())) {
                    Toast.makeText(FormContactActivity.this, "Field Nama is required", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(mEditAddressView.getText())) {
                    Toast.makeText(FormContactActivity.this, "Field Alamat is required", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(mEditPhoneView.getText())) {
                    Toast.makeText(FormContactActivity.this, "Field No Telepon is required", Toast.LENGTH_SHORT).show();
                    return;
                } else  {
                    String name = mEditNameView.getText().toString();
                    String address = mEditAddressView.getText().toString();
                    String phone = mEditPhoneView.getText().toString();
                    if (!isNewContact) {
                        replyIntent.putExtra(EXTRA_ID, mContact.getId());
                    }
                    replyIntent.putExtra(EXTRA_NAME, name);
                    replyIntent.putExtra(EXTRA_ADDRESS, address);
                    replyIntent.putExtra(EXTRA_PHONE, phone);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("DELETE", true);
                intent.putExtra(EXTRA_CONTACT, mContact);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormContactActivity.super.onBackPressed();
            }
        });
    }
}

