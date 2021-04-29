package com.example.placesapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FindPlaceFragment extends Fragment {

    private EditText txtFindPlace;

    private HomeActivity home;

    public FindPlaceFragment(HomeActivity home) {
        this.home=home;
    }

    public static FindPlaceFragment newInstance(HomeActivity home) {
        FindPlaceFragment fragment = new FindPlaceFragment(home);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_find_place, container, false);
        txtFindPlace = root.findViewById(R.id.txtFindPlace);

        txtFindPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //filter(s.toString());
            }
        });

        return inflater.inflate(R.layout.fragment_find_place, container, false);
    }
}