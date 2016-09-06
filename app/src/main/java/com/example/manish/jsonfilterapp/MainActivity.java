package com.example.manish.jsonfilterapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button buttonUnsortedlist, buttonSortedbybrand, buttonSortedbyprice, buttonFilterresults;
    ProgressDialog progressDialog;
    TextView textView;
    SharedPreferences sharedPreferences;
    String URL = "http://internetfaqs.net/laptops/getData.php";
    String mn;
    LaptopAdapter laptopAdapter;
    ArrayList<Laptop> laptopList = new ArrayList<Laptop>();
    String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        buttonFilterresults = (Button) findViewById(R.id.buttonFilterresults);
        buttonSortedbybrand = (Button) findViewById(R.id.buttonSortedbybrand);
        buttonSortedbyprice = (Button) findViewById(R.id.buttonSortedbyprice);
        buttonUnsortedlist = (Button) findViewById(R.id.buttonUnsortedlist);

        buttonSortedbybrand.setOnClickListener(this);
        buttonUnsortedlist.setOnClickListener(this);
        buttonFilterresults.setOnClickListener(this);
        buttonSortedbyprice.setOnClickListener(this);
        getLaptops();
        sharedPreferences = getSharedPreferences("SHARED_PREF_NAME", Context.MODE_PRIVATE);


    }

    @Override
    public void onClick(View v) {
        if (v == buttonUnsortedlist) {
            startActivity(new Intent(this, UnsortedActivity.class));
        }
        if (v == buttonSortedbybrand) {

            startActivity(new Intent(this, Sortedbybrand.class));
        }
        if (v == buttonSortedbyprice) {

            startActivity(new Intent(this, Sortedbyprice.class));
        }
        if (v == buttonFilterresults) {

            startActivity(new Intent(this, Filterresults.class));
        }

    }

    public void getLaptops() {
        progressDialog.setMessage("Fetching data from the Server...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        Toast.makeText(MainActivity.this, "Data Successfully Fetched", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject js = new JSONObject(response);

                            JSONArray jsonArray = js.getJSONArray("laptops");

                            jsonString = jsonArray.toString();


                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("jsonString", jsonString);
                            editor.apply();


                            JSONArray sortedJsonArray = new JSONArray();
                            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonValues.add(jsonArray.getJSONObject(i));
                            }
                            Collections.sort(jsonValues, new Comparator<JSONObject>() {
                                //You can change "Name" with "ID" if you want to sort by ID
                                private static final String KEY_NAME = "modelname";

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


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue request = Volley.newRequestQueue(this);
        request.add(stringRequest);
    }
}
