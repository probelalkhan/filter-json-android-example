package com.example.manish.jsonfilterapp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Manish on 9/4/2016.
 */


public class LaptopAdapter extends ArrayAdapter<Laptop> {
    Activity activity;
    int layoutResourceId;
    ArrayList<Laptop> data = new ArrayList<Laptop>();
    Laptop laptop;

    public LaptopAdapter(Activity activity, int layoutResourceId, ArrayList<Laptop> data) {
        super(activity, layoutResourceId, data);

        this.activity = activity;
        this.layoutResourceId = layoutResourceId;
        this.data = data;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        LaptopHolder holder = null;


        if (row == null) {

            LayoutInflater inflater = LayoutInflater.from(activity);

            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new LaptopHolder();

            holder.modelname = (TextView) row.findViewById(R.id.textViewModelname);
            holder.ram = (TextView) row.findViewById(R.id.textViewRam);
            holder.os = (TextView) row.findViewById(R.id.textViewOs);
            holder.price = (TextView) row.findViewById(R.id.textViewPrice);
            holder.screensize = (TextView) row.findViewById(R.id.textViewScreensize);
            holder.brand = (TextView) row.findViewById(R.id.textViewBrand);


            row.setTag(holder);

        } else {
            holder = (LaptopHolder) row.getTag();
        }

        laptop = data.get(position);

        holder.modelname.setText(laptop.getModelname());
        holder.ram.setText(laptop.getRam());
        holder.os.setText(laptop.getOs());
        holder.price.setText(laptop.getPrice());
        holder.screensize.setText(laptop.getScreensize());
        holder.brand.setText(laptop.getBrand());


        return row;
    }


    class LaptopHolder {

        TextView modelname, ram, os, price, screensize, brand;


    }

}

