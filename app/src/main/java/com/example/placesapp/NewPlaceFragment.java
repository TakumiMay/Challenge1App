package com.example.placesapp;

import android.Manifest;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

//Clase Observada
public class NewPlaceFragment extends Fragment {

    //Location logo button to add a position on the map
    private ImageButton btnAddPosition;

    //Plus logo button to add an image to the place
    private ImageButton btnAddImg;

    //Imagen que se seteará cuando el usuario seleccione la foto
    private ImageView imgPlace;

    private EditText txtPlaceName;

    //Referencia al observador
    private FragmentChange observer;

    //Referencia al observador
    private OnNewModality observer2;

    //Boton de registrar
    private Button btnReg;

    private Place place;

    private TextView txtAddress;

    private HomeActivity home;

    public NewPlaceFragment(HomeActivity home) {
        this.home=home;
    }

    public static NewPlaceFragment newInstance(HomeActivity home) {
        NewPlaceFragment fragment = new NewPlaceFragment(home);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_place, container, false);
        btnAddPosition = root.findViewById(R.id.btnAddPosition);

        btnAddImg = root.findViewById(R.id.btnAddImg);

        imgPlace = root.findViewById(R.id.imgPlace);

        btnReg = root.findViewById(R.id.btnReg);

        txtAddress = root.findViewById(R.id.txtAddress);

        txtPlaceName = root.findViewById(R.id.txtPlaceName);

        btnReg.setOnClickListener( e -> {
            place.setName(txtPlaceName.toString());
            home.getPlaces().add(place);
        });

        btnAddPosition.setOnClickListener( e -> {
            Toast.makeText(this.getContext(), "Sostenga el toque para ubicar el lugar\ndel Place", Toast.LENGTH_LONG).show();
            observer.changeToMap(observer.getMapFragment());
            observer2.onNewMod(1, txtPlaceName.getText().toString());
        });

        btnAddImg.setOnClickListener(e -> {
            Intent j = new Intent(Intent.ACTION_GET_CONTENT);
            // Para video sería j.setType("video/*");
            j.setType("image/*");
            startActivityForResult(j, 11);
        });

        return root;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11 && resultCode==RESULT_OK){
            Uri uri = data.getData();
            Log.e(">>>>", uri+"");
            String path = UtilDomi.getPath(this.getContext(), uri);
            Log.e(">>>>", path+"");
            Bitmap image = BitmapFactory.decodeFile(path);
            imgPlace.setVisibility(View.VISIBLE);
            imgPlace.setImageBitmap(image);
            place.setImage(image);
        }
    }

    //Método suscripcion al observador
    public void setObserver(FragmentChange act){
        this.observer = act;
    }

    //Método suscripcion al observador
    public void setObserver2(OnNewModality act){
        this.observer2 = act;
    }

    //Toda clase que implemente esta interfaz podrá obsevar la clase
    public interface FragmentChange {
        //Lista de metodos
        void changeToMap(Fragment fragment);
        Fragment getMapFragment();
    }

    public TextView getTxtAddress(){
        return txtAddress;
    }

    public EditText getTxtPlaceName(){
        return txtPlaceName;
    }

    public interface OnNewModality{
        void onNewMod(int i, String name);
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place.setLatLng(place.getLatLng());
    }

}