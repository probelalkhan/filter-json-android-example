package com.example.manish.jsonfilterapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sortedbyprice extends AppCompatActivity {

    ListView unsortedlist;
    LaptopAdapter laptopAdapter;
    ArrayList<Laptop> laptopList = new ArrayList<Laptop>();
    String jsonString;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sortedbyprice);


        unsortedlist = (ListView) findViewById(R.id.unsortedlist);

        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF_NAME", Context.MODE_PRIVATE);
        jsonString = sharedPreferences.getString("jsonString", null);

        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonValues.add(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            //You can change "Name" with "ID" if you want to sort by ID
            private static final String KEY_NAME = "price";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();

                try {
                    valA = (String) a.get(KEY_NAME);
                    valB = (String) b.get(KEY_NAME);
                } catch (JSONException e) {
                    //do something
                }

                return valA.compareTo(valB);
                //if you want to change the sort order, simply use the following:
                //return -valA.compareTo(valB);
            }
        });

        for (int i = 0; i < jsonArray.length(); i++) {
            sortedJsonArray.put(jsonValues.get(i));
        }


        for (int i = 0; i < sortedJsonArray.length(); i++) {

            try {
                JSONObject jsonObject = sortedJsonArray.getJSONObject(i);
                String modelname = "Modelname:" + jsonObject.getString("modelname");
                String ram = "Ram:" + jsonObject.getString("ram");
                String os = "Os:" + jsonObject.getString("os");
                String price = "Price:" + jsonObject.getString("price");
                String screensize = "Screensize:" + jsonObject.getString("screensize");
                String brand = "Brand:" + jsonObject.getString("brand");
                Laptop laptop = new Laptop(modelname, ram, os, price, screensize, brand);
                laptopList.add(laptop);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            laptopAdapter = new LaptopAdapter(Sortedbyprice.this, R.layout.list_layout, laptopList);

            unsortedlist.setAdapter(laptopAdapter);

            laptopAdapter.notifyDataSetChanged();
        }
    }
}
