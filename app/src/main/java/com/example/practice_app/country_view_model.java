package com.example.practice_app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.practice_app.model.country;

import java.util.List;

public class country_view_model extends AndroidViewModel {
    public static country_repository repository;
    public LiveData<List<country>> get_all_contacts;
    public country_view_model(@NonNull Application application) {
        super(application);
        repository = new country_repository(application);
        get_all_contacts = repository.getAllcountry();
    }
    public  void deleteall(){
        repository.deleteall();
    }
    public LiveData<List<country>> get_all_contacts(){return get_all_contacts;}
    public static void insert(country country){
        repository.insert(country);
    }

}
