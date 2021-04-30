package com.example.placesapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FindPlaceFragment extends Fragment {

    private EditText txtFindPlace;

    private HomeActivity home;

    private RecyclerView recyclerView;
    private LinearLayoutManager llm;

    private PlaceAdapter pAdp;

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
                find(s.toString());
            }
        });

        recyclerView = root.findViewById(R.id.recyclerView);

        llm = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(llm);

        pAdp = new PlaceAdapter();
        pAdp.setPlaces(home.getPlaces());
        recyclerView.setAdapter(pAdp);

        /**for(int i=0;i<home.getPlaces().size();i++){
            pAdp.addPlace(home.getPlaces().get(i));
        }**/
        pAdp.setPlaces(home.getPlaces());

        return root;
    }

    public void find(String string){
        ArrayList<Place> list = new ArrayList<>();
        for(Place p : home.getPlaces()){
            if (p.getName().toLowerCase().contains(string.toLowerCase())){
                list.add(p);
            }
        }
    }

    public PlaceAdapter getpAdp() {
        return pAdp;
    }

}