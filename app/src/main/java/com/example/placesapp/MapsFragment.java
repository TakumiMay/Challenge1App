package com.example.placesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class MapsFragment extends Fragment implements LocationListener, OnMapReadyCallback, GoogleMap.OnMapLongClickListener, NewPlaceFragment.OnNewModality {

    public static final int VIEW_MOD=0;
    public static final int ADD_MOD=1;

    private LocationManager manager;

    private NewPlaceFragment newPlace;

    private boolean zoom;

    private Marker me;

    private GoogleMap mMap;

    private LocationManager locationManager;

    private int modality;

    private String zoomPlace;

    private PassingInfo observer;

    private HomeActivity home;

    public MapsFragment( NewPlaceFragment newPlace, HomeActivity home){
        this.home=home;
        this.newPlace=newPlace;
        newPlace.setObserver2(this);
    }

    public void paintMarkers(){
        if (home.getPlaces().size()>0){
            for(Place k : home.getPlaces()){
                mMap.addMarker(new MarkerOptions().position(k.getLatLng()).title(k.getName()));
            }
        }
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            LatLng cali = new LatLng(3.42158, -76.5205);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(cali));

            mMap.setOnMapLongClickListener( e -> {
                if(modality==VIEW_MOD){
                    Log.e(">>>>>>", "Modalidad de visualización prro");
                }else{
                    mMap.clear();
                    Log.e(">>>>>>", "Modalidad de adición prro");
                    LatLng pos = new LatLng(e.latitude, e.longitude);
                    mMap.addMarker(new MarkerOptions().position(pos));
                    home.getPlace().setLatLng(pos);
                    observer.continueBtn();
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(this.getContext(), "Sostenga el toque para ubicar el lugar\ndel Place", Toast.LENGTH_LONG).show();
    }

    public void setObserver(PassingInfo act){
        this.observer = act;
    }

    public void setModality(int modality){ this.modality = modality; }

    @Override
    public void onNewMod(int i){
        setModality(i);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        updateMyLocation(location);
        isNear(location);
    }

    public void isNear(Location location){
        boolean found = false;
        Location temp = new Location(LocationManager.GPS_PROVIDER);
        for(int i=0;i<home.getPlaces().size() && found==false;i++){
            temp.setLongitude(home.getPlaces().get(i).getLatLng().longitude);
            temp.setLatitude(home.getPlaces().get(i).getLatLng().latitude);
            if(location.distanceTo(temp)<100){

            }
        }
    }

    public Place searchMarker(String name){
        Place myMarker = null;
        boolean found = false;
        for(int i=0;i<home.getPlaces().size() && found == false ;i++){
            if(home.getPlaces().get(i).getName().toString().equalsIgnoreCase(name)){
                myMarker = home.getPlaces().get(i);
                found = true;
            }
        }
        return myMarker;
    }

    public void zoom(){
        Place myMarker = searchMarker(zoomPlace);
        if(myMarker != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myMarker.getLatLng(), 15));
        }
    }

    public void updateMyLocation(Location location){
        LatLng myPos = new LatLng(location.getLatitude(),location.getLongitude());
        if(me == null) {
            me = mMap.addMarker(new MarkerOptions().position(myPos).title("actual"));
            me.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }else{
            me.setPosition(myPos);
            me.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }
        if(zoom == false) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos, 15));
        }else{
            zoom();
        }
    }

    public interface PassingInfo {
        void continueBtn();
    }

}