package com.example.app.repository.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.app.Questions;

import java.util.List;

@Dao
public interface QuestionDao {

    @Query("SELECT * from questions_table")
    LiveData<List<Questions>> getAllQuestions();

    @Insert
    void insert(Questions questions);

}
