package com.example.android.UAS_NIM141807.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * from tb_contact ORDER BY id ASC")
    LiveData<List<Contact>> getAllContacts();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Contact contact);

    @Delete()
    void delete(Contact contact);

    @Query("DELETE FROM tb_contact")
    void deleteAll();
}
