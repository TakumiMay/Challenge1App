package com.example.placesapp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class PlaceView extends RecyclerView.ViewHolder {

    private ConstraintLayout root;

    private TextView name;
    private ImageView image;
    private ImageButton eye;

    public PlaceView(ConstraintLayout root){
        super(root);
        this.root=root;
        name=root.findViewById(R.id.name);
        image=root.findViewById(R.id.image);
        eye=root.findViewById(R.id.eye);
    }

    public TextView getName() {
        return name;
    }

    public ImageView getImage() {
        return image;
    }

    public ImageButton getEye() {
        return eye;
    }

}
