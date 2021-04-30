package com.example.placesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements
        NewPlaceFragment.FragmentChange,
        MapsFragment.PassingInfo,
        Serializable {

    //Places List
    private ArrayList<Place> places;

    //Fragmento del formulario para agregar un nuevo place
    private NewPlaceFragment newPlace;

    //Fragmento para buscar un place
    private FindPlaceFragment findPlace;

    //Fragmento del mapa
    private MapsFragment map;

    //Boton para continuar que se muestra en el mapa cuando se ha agregado un marcador
    private Button btnContinue;

    private Place place;

    //BottomNavigationBar con los 3 fragmentos
    private BottomNavigationView navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        place = new Place();

        places = new ArrayList<>();

        setContentView(R.layout.activity_home);

        //Inicialización del BottomNavigationBar para poder cambiar de fragmento desde los botones
        navigator = findViewById(R.id.navigator);

        //Inicializacion del botón continuar
        btnContinue = findViewById(R.id.btnContinue);

        //Inicializacion de los fragmentos
        newPlace = NewPlaceFragment.newInstance(this);
        findPlace = FindPlaceFragment.newInstance(this);
        map = new MapsFragment(newPlace, this);

        //Se setea el home activity como observador de los fragmentos newPlace y map
        newPlace.setObserver(this);
        map.setObserver(this);

        //Iniciamos la aplicacion mostrando el fragmento para agregar un nuevo place
        showFragment(newPlace);

        //Accion a realizar despues de presionar "Continuar" en el mapa
        btnContinue.setOnClickListener( e -> {
            btnContinue.setVisibility(View.INVISIBLE);
            navigator.setSelectedItemId(navigator.getMenu().getItem(0).getItemId());
            newPlace.getTxtAddress().setVisibility(View.VISIBLE);
            newPlace.getTxtAddress().setText(newPlace.getTxtPlaceName().getText().toString());
            Log.e(">>>>>>>", newPlace.getTxtPlaceName().getText().toString());
            showFragment(newPlace);
        });

        //Seteamos los fragmentos para cada item del BottomNavigationBar
        navigator.setOnNavigationItemSelectedListener( (menuItem) -> {
            switch (menuItem.getItemId()){
                case R.id.newPlace:
                    showFragment(newPlace);
                    map.setModality(0);
                    btnContinue.setVisibility(View.INVISIBLE);
                    break;
                case R.id.map:
                    showFragment(map);
                    map.setModality(0);
                    map.paintMarkers();
                    btnContinue.setVisibility(View.INVISIBLE);
                    break;
                case R.id.find:
                    showFragment(findPlace);
                    map.setModality(0);
                    btnContinue.setVisibility(View.INVISIBLE);
                    break;
            }
            return true;
        });
    }

    //Metodo que muestra el fragmento seleccionado
    public void showFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.commit();
    }

    //Implementacion del metodo
    @Override
    public void changeToMap(Fragment fragment) {
        showFragment(fragment);
        navigator.setSelectedItemId(navigator.getMenu().getItem(1).getItemId());
    }

    @Override
    public Fragment getMapFragment() {
        return map;
    }

    @Override
    public void continueBtn() {
        btnContinue.setVisibility(View.VISIBLE);
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public Place getPlace() {
        return place;
    }

    public FindPlaceFragment getFindPlace() {
        return findPlace;
    }
}