package com.example.app.roomdb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.app.Questions;
import com.example.app.repository.dao.QuestionDao;

@Database(entities = {Questions.class}, version = 1)
public abstract class QuestionRoomDatabase extends RoomDatabase {

    private static QuestionRoomDatabase INSTANCE;

    public abstract QuestionDao questionDao();
    public static synchronized QuestionRoomDatabase getInstance(final Context context) {

        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        QuestionRoomDatabase.class, "questions_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(RoomDBCallback)
                        .build();
        }

        return INSTANCE;

    }
    private static RoomDatabase.Callback RoomDBCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(INSTANCE).execute();

        }

    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private QuestionDao questionDao;

        private PopulateDbAsyncTask(QuestionRoomDatabase db) {
            questionDao = db.questionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            questionDao.insert(new Questions("Do you speak English?", "No. My is American.", "No. I is American.", "No. I am American.", 3));
            questionDao.insert(new Questions("Where do you live?", "Mi live is London.", "I live in London.", "I lives in London.", 2));
            questionDao.insert(new Questions("Do you like music?", "Yes, I do.", "Yes, I like.", "Yes, I am.", 1));
            return null;
        }
    }

}
