package com.example.manish.jsonfilterapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class UnsortedActivity extends AppCompatActivity {


    ListView unsortedlist;
    LaptopAdapter laptopAdapter;
    ArrayList<Laptop> laptopList = new ArrayList<Laptop>();
    ArrayList<String> jsonString = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsorted);

        unsortedlist = (ListView) findViewById(R.id.unsortedlist);


        SharedPreferences sp = getSharedPreferences("SHARED_PREF_NAME", Context.MODE_PRIVATE);
        String jsonString = sp.getString("jsonString", null);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonArray.length(); i++) {


            try {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String modelname = "Modelname:" + jsonObject.getString("modelname");
                String ram = "Ram:" + jsonObject.getString("ram");
                String os = "Os:" + jsonObject.getString("os");
                String price = "Price:" + jsonObject.getString("price");
                String screensize = "Screensize:" + jsonObject.getString("screensize");
                String brand = "Brand:" + jsonObject.getString("brand");
                Laptop laptop = new Laptop(modelname, ram, os, price, screensize, brand);
                laptopList.add(laptop);

            } catch (Exception e) {
            }
        }
        laptopAdapter = new LaptopAdapter(UnsortedActivity.this, R.layout.list_layout, laptopList);

        unsortedlist.setAdapter(laptopAdapter);

        laptopAdapter.notifyDataSetChanged();
    }
}
