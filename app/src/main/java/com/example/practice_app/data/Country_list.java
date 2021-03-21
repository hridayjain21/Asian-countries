package com.example.practice_app.data;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.practice_app.MainActivity;
import com.example.practice_app.controller.Appcontroller;
import com.example.practice_app.country_view_model;
import com.example.practice_app.model.AnswerListAsyncResponse;
import com.example.practice_app.model.country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Country_list {
    ArrayList<country> countryArrayList = new ArrayList<>();
    String url = "https://restcountries.eu/rest/v2/region/Asia";

    public List<country> get_country_list(final AnswerListAsyncResponse callback){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url
                , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    country country = new country();
                    try {
                        String name = response.getJSONObject(i).getString("name");
                        String flag = response.getJSONObject(i).getString("flag");
                        String capital = response.getJSONObject(i).getString("capital");
                        String region = response.getJSONObject(i).getString("region");
                        String subregion = response.getJSONObject(i).getString("subregion");
                        String population = response.getJSONObject(i).getString("population");
                        JSONArray borders = response.getJSONObject(i).getJSONArray("borders");
                        JSONArray languages = response.getJSONObject(i).getJSONArray("languages");
                        StringBuilder bor = new StringBuilder(" ");
                        for(int j=0;j<borders.length();j++){
                            bor.append(borders.get(j)).append(" ");
                        }
                        StringBuilder lang = new StringBuilder(" ");
                        for(int j=0;j<languages.length();j++){
                            JSONObject language = languages.getJSONObject(j);
                            lang.append(language.getString("name")).append(" ");
                        }

                        country.setName(name);
                        country.setCapital(capital);
                        country.setRegion(region);
                        country.setSubregion(subregion);
                        country.setBorders(bor.toString());
                        country.setLanguages(lang.toString());
                        country.setImage(flag);
                        countryArrayList.add(country);
                        country_view_model.insert(country);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }if(callback != null)callback.processfinished(countryArrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response error", "onErrorResponse: "+ error.getMessage());
            }
        }
        );
        Appcontroller.AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return countryArrayList;
    }
}
