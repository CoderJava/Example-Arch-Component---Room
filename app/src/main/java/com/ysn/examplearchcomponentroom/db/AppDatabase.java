package com.ysn.examplearchcomponentroom.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ysn.examplearchcomponentroom.db.dao.student.StudentDao;
import com.ysn.examplearchcomponentroom.db.entity.Student;

/**
 * Created by root on 01/07/17.
 */

@Database(entities = {Student.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "university";
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .build();
        }
        return instance;
    }

    public abstract StudentDao studentDao();

}
