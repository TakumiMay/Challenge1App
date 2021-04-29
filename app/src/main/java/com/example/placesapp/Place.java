package com.example.placesapp;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class Place {

    private boolean qualified;
    private String name;
    private LatLng latLng;
    private Bitmap image;

    public Place(){

    }

    public Place(String name, LatLng latLng){
        this.name=name;
        this.latLng=latLng;
        qualified=false;
        image=null;
    }

    public Bitmap getImage(){
        return image;
    }

    public void setImage(Bitmap image){
        this.image=image;
    }

    public boolean isQualified(){
        return qualified;
    }

    public void setQualified(boolean qualified){
        this.qualified=qualified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

}
