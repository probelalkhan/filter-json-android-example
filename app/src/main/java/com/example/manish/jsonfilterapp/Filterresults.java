package com.example.manish.jsonfilterapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Filterresults extends AppCompatActivity {

    ListView unsortedlist;
    LaptopAdapter laptopAdapter;
    String mn;
    ArrayList<Laptop> laptopList = new ArrayList<Laptop>();
    String jsonString;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filterresults);


        unsortedlist = (ListView) findViewById(R.id.unsortedlist);
        //here, the data in String is being retreived.
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF_NAME", Context.MODE_PRIVATE);
        jsonString = sharedPreferences.getString("jsonString", null);
        //here, string to jsonArray conversion takes place
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonArray.length(); i++) {


            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String price1 = jsonObject.getString("price");
                //simple if statement allows only those jsonObjects to be added the laptopList where price is less than 40000.
                if (Integer.parseInt(price1) < 40000) {

                    String modelname = "Modelname:" + jsonObject.getString("modelname");
                    String ram = "Ram:" + jsonObject.getString("ram");
                    String os = "Os:" + jsonObject.getString("os");
                    String price = "Price:" + jsonObject.getString("price");
                    String screensize = "Screensize:" + jsonObject.getString("screensize");
                    String brand = "Brand:" + jsonObject.getString("brand");
                    Laptop laptop = new Laptop(modelname, ram, os, price, screensize, brand);
                    laptopList.add(laptop);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(Filterresults.this, "Filtered as Price less than 40000", Toast.LENGTH_SHORT).show();


            laptopAdapter = new LaptopAdapter(Filterresults.this, R.layout.list_layout, laptopList);

            unsortedlist.setAdapter(laptopAdapter);

            laptopAdapter.notifyDataSetChanged();


        }

    }
}

