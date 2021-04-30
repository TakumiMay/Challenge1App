package com.example.placesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceView> {

    private ArrayList<Place> places;

    public PlaceAdapter(){
        places = new ArrayList<>();
    }

    public void addPlace(Place place){
        places.add(place);
        this.notifyDataSetChanged();
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    @NonNull
    @Override
    public PlaceView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //XML --> View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.place_row, null);
        ConstraintLayout rowRoot = (ConstraintLayout) row;
        PlaceView placeView = new PlaceView(rowRoot);
        return placeView;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceView holder, int position) {
        holder.getName().setText(places.get(position).getName());
        holder.getImage().setImageBitmap(places.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }


}
