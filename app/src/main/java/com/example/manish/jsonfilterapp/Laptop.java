package com.example.manish.jsonfilterapp;

/**
 * Created by Manish on 9/4/2016.
 */
public class Laptop {

    public String modelname, ram, os, price, screensize, brand;

    Laptop(String modelname, String ram, String os, String price, String screensize, String brand) {

        this.modelname = modelname;
        this.ram = ram;
        this.os = os;
        this.price = price;
        this.screensize = screensize;
        this.brand = brand;
    }

    public String getModelname() {
        return modelname;
    }

    public String getRam() {
        return ram;
    }

    public String getOs() {
        return os;
    }

    public String getPrice() {
        return price;
    }

    public String getScreensize() {
        return screensize;
    }

    public String getBrand() {
        return brand;
    }
}
