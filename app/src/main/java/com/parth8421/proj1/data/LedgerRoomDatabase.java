package com.parth8421.proj1.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class LedgerRoomDatabase extends RoomDatabase {
    public abstract ProductDao productDao();

    public static volatile LedgerRoomDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static LedgerRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LedgerRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            LedgerRoomDatabase.class,
                            "ledger_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
