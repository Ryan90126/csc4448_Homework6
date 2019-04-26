package com.example.transactionmanagerX.room.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.transactionmanagerX.room.data.User;

@Database(entities={User.class, Transaction.class,Account.class,Label.class,VisualData.class},version = 1)
public abstract class MyRoomDatabase extends RoomDatabase {

    private static MyRoomDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract TransactionDao transactionDao();
    public abstract AccountDao accountDao();
    public abstract LabelDao labelDao();
    public abstract VisualDao visualDao();

    public static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyRoomDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
