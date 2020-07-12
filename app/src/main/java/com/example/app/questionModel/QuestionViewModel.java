package com.example.app.questionModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.app.Questions;
import com.example.app.repository.QuestionsRepository;

import java.util.List;

public class QuestionViewModel extends AndroidViewModel {

    private QuestionsRepository mRepository;
    private LiveData<List<Questions>> mAllQuestions;

    public QuestionViewModel(Application application) {
        super(application);
        mRepository = new QuestionsRepository(application);
        mAllQuestions = mRepository.getmAllQuestions();
    }

    public LiveData<List<Questions>> getmAllQuestions() {
        return mAllQuestions;
    }

}
