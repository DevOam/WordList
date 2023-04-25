package com.example.word_list;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordsDao {

    @Insert
    void insert(Words word);

    @Update
    void update (Words word);

    @Delete
    void delete (Words word);

    @Query("DELETE From wordTable")
    void deleteAllWords ();

    @Query("SELECT * From wordTable")
    LiveData<List<Words>> getAllWords();
}