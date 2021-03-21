package com.example.practice_app.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.practice_app.data.country_dao;
import com.example.practice_app.model.country;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {country.class},version = 1,exportSchema = false)
public abstract class country_room_database extends RoomDatabase {
    public abstract country_dao country_dao();
    private static volatile country_room_database INSTANCE;
    private static int no_of_threads = 6;
    public static final ExecutorService databaseexecutorservice
            = Executors.newFixedThreadPool(no_of_threads);

    public static country_room_database get_database(Context context){
        if(INSTANCE == null){
            synchronized (country_room_database.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                        ,country_room_database.class,"country_database")
                        .addCallback(sroomdatabasecallback).build();
            }
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback sroomdatabasecallback =
            new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseexecutorservice.execute(new Runnable() {
                        @Override
                        public void run() {
                            country_dao country_dao = INSTANCE.country_dao();
                            country_dao.delete_all();
                        }
                    });
                }
            };
}

