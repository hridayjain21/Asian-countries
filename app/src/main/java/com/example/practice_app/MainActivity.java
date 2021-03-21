package com.example.practice_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.practice_app.Adapter.RecyclerViewAdapter;
import com.example.practice_app.data.Country_list;
import com.example.practice_app.data.country_dao;
import com.example.practice_app.model.AnswerListAsyncResponse;
import com.example.practice_app.model.country;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<country> countryList;
    private List<country> countryList1;
    private country_view_model country_view_model1;
    private FloatingActionButton delete;
    private Button yesconf,noconf;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        delete = findViewById(R.id.button);
        country_view_model1 = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
                .getApplication()).create(country_view_model.class);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            country_view_model1.deleteall();
            countryList = new Country_list().get_country_list(new AnswerListAsyncResponse() {
                @Override
                public void processfinished(ArrayList<country> countryArrayList) {
                    countryList.addAll(countryArrayList);
                    recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, countryList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            });
        } else {
            country_view_model1.get_all_contacts.observe(this, new Observer<List<country>>() {
                @Override
                public void onChanged(List<country> countryList2) {
                    recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, countryList2);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            });
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(MainActivity.this);
                inflater = LayoutInflater.from(MainActivity.this);
                View view = inflater.inflate(R.layout.delete_confirmation, null);
                yesconf = view.findViewById(R.id.conf_yes);
                noconf = view.findViewById(R.id.conf_no);
                builder.setView(view);
                alertDialog = builder.create();
                alertDialog.show();

                noconf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                yesconf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        country_view_model1.deleteall();
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }
}
