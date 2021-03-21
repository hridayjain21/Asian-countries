package com.example.practice_app;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.practice_app.data.country_dao;
import com.example.practice_app.model.country;
import com.example.practice_app.util.country_room_database;

import java.util.List;

import static com.example.practice_app.util.country_room_database.*;

public class country_repository {
    private country_dao country_dao;
    private LiveData<List<country>> allcountry;

    public country_repository(Application application){
        country_room_database db = get_database(application);
        country_dao = db.country_dao();

        allcountry = country_dao.get_all_country();
    }
    public LiveData<List<country>> getAllcountry(){
        return allcountry;
    }
    public void deleteall(){
        country_room_database.databaseexecutorservice.execute(new Runnable() {
            @Override
            public void run() {
                country_dao.delete_all();
            }
        });
    }
    public void insert(final country country){
        country_room_database.databaseexecutorservice.execute(new Runnable() {
            @Override
            public void run() {
                country_dao.insert(country);
            }
        });
    }
}
