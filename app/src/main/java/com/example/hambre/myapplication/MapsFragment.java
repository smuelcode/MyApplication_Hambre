package com.example.hambre.myapplication;


import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback,GoogleMap.OnMapClickListener{

    private GoogleMap mMap;
   // public LocationManager locationmanager;
    public Location location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34,151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Minha Posição"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12));
        mMap.getUiSettings().setZoomControlsEnabled(true);


    }


    @Override
    public void onMapClick(LatLng latLng) {

    }
}
