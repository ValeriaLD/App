package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.questionModel.QuestionViewModel;

import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    TextView txtQuestion;
    TextView textViewCorrect, textViewWrong, textViewQuestionCount;

    RadioButton rb1, rb2, rb3;
    RadioGroup rbGroup;
    Button buttonNext;

    boolean answered = false;

    List<Questions> quesList;
    Questions currentQ;

    private int questionCounter = 0, questionTotalCount;


    private QuestionViewModel questionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setupUI();

        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getmAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> questions) {
                Toast.makeText(QuizActivity.this, "Get it! ", Toast.LENGTH_SHORT).show();

                fetchContent(questions);

            }
        });
    }


    void setupUI(){

        txtQuestion = findViewById(R.id.txtQuestionContainer);

        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);

        buttonNext = findViewById(R.id.button_next);

    }

    private void fetchContent(List<Questions> questions) {

        quesList = questions;
        startQuiz();

    }

    private void setQuestionView() {

        rbGroup.clearCheck();
        questionTotalCount = quesList.size();
        Collections.shuffle(quesList);
        if (questionCounter < questionTotalCount -1) {
            currentQ = quesList.get(questionCounter);
            txtQuestion.setText(currentQ.getQuestion());
            rb1.setText(currentQ.getOptA());
            rb2.setText(currentQ.getOptB());
            rb3.setText(currentQ.getOptC());
            questionCounter++;
            answered = false;

            buttonNext.setText("Confirm");
            textViewQuestionCount.setText("Questions: " + questionCounter + "/" + questionTotalCount);

        } else {

            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
            startActivity(intent);
        }

    }

    private void startQuiz() {
        setQuestionView();
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!answered) {
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {

                        quizOperation();

                    } else{
                        Toast.makeText(QuizActivity.this, "Please select Answer ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void quizOperation() {

        answered = true;
        RadioButton rbselected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answeredNr = rbGroup.indexOfChild(rbselected) + 1;
        //check solution
        checkSolution(answeredNr, rbselected);
    }

    private void checkSolution (int answerNr, RadioButton rbselected) {
        switch (currentQ.getAnswer()) {

            case 1:
                if(currentQ.getAnswer() == answerNr) {
                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                    setQuestionView();
                } else {
                    changetoIncorrectColor(rbselected);
                    Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
                    setQuestionView();
                } break;

            case 2:
                if(currentQ.getAnswer() == answerNr) {
                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    changetoIncorrectColor(rbselected);
                    Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
                    setQuestionView();
                } break;

            case 3:
                if(currentQ.getAnswer() == answerNr) {
                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    changetoIncorrectColor(rbselected);
                    Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
                    setQuestionView();
                } break;
        }
    }

    private void changetoIncorrectColor(RadioButton rbselected) {
        rbselected.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_wrong));
    }
}