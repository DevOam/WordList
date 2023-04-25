package com.example.word_list;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Words.class, version = 1)
public abstract class WordRoomDb extends RoomDatabase {

    private static WordRoomDb instance;

    public abstract WordsDao wordsDao();

    //Singlton
    public static synchronized WordRoomDb getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDb.class, "word3-database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDataAsyncTask(instance).execute();
        }

    };

    private static class PopulateDataAsyncTask extends AsyncTask<Void,Void, Void>
    {
        private WordsDao mWordsDao;

        PopulateDataAsyncTask(WordRoomDb db)
        {
            mWordsDao = db.wordsDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mWordsDao.insert(new Words("book", "Book", "noun"));
            mWordsDao.insert(new Words("book", "Book", "noun"));
            mWordsDao.insert(new Words("book", "Book", "noun"));
            return null;
        }
    }

}