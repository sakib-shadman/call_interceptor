package com.example.instorage_caller.roomdb;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.instorage_caller.roomdb.dao.CustomerDao;

@Database(entities = {CustomerInfo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {


    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "instorage")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract CustomerDao customerDao();

}
