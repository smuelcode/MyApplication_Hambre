package com.example.hambre.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private static final String PREF_NAME = "MainActivityPreferences";
    private GoogleMap mMap;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    public LocationManager locationmanager;
    public double Latitude,Longitude;
    public String lat,lng;
    public boolean  isNetworkEnabled,isGPSEnabled;
    public Location location = new Location(GPS_PROVIDER);
    private static final String URL = "http://tellunar.com.br/busca_cozinha.php";
    public Cozinheiro chefe = new Cozinheiro();
    public HashMap<LatLng, Cozinheiro> cozinheiros = new HashMap<>();
    public List<Cozinheiro> cozinheiro = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        locationmanager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        location = new Location(NETWORK_PROVIDER);


        new GetCoordinates().execute(URL);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);

        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_FINE_LOCATION);

            }
        }

        isGPSEnabled = locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        locationmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0, this);
        locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, this);


      if (isGPSEnabled) {
          if (locationmanager != null) {

              location = locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
              if (location != null) {
                  Latitude = location.getLatitude();
                  Longitude = location.getLongitude();
              }
          }
      }
      if (isNetworkEnabled) {

          if (locationmanager != null) {

              location = locationmanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
             if (location != null) {

                 Latitude = location.getLatitude();
                 Longitude = location.getLongitude();

             }
          }
      }


       /////////////////////  Pega a posição do GPS or NETWORK e marca na tela \\\\\\\\\\\\\\\\\\\\\\\\\\

         LatLng position = new LatLng(Latitude,Longitude);
        //mMap.addMarker(new MarkerOptions().position(position).title("Minha Posição"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,16));
        mMap.getUiSettings().setZoomControlsEnabled(true);



    }

    public void onRequestPermissionsResult (int requestCode,String [] permissions,int [] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        switch (requestCode){

            case MY_PERMISSION_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mMap.setMyLocationEnabled(true);

                    }
                    else {

                        Toast.makeText(getApplicationContext(),"Essa aplicação necessita de permissão.",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    break;
                }
        }

    }


    //////////////////// Busca as coordenadas dos cozinheiros existentes  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


    private class GetCoordinates extends AsyncTask<String,Void,String> {

        ProgressDialog dialog = new ProgressDialog(MapsActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {

            String response;
            try {

                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("http://tellunar.com.br/busca_cozinha.php");
                response = http.getHttpData(url);
                return response;

            } catch (Exception ex) {

            }
            return null;
        }


        protected void onPostExecute(String s) {

            try {

                JSONArray json = new JSONArray(s);

                for (int i = 0; i <= json.length(); i++) {

                    chefe = new Cozinheiro();

                    chefe.setNome(json.getJSONObject(i).get("cozinheiro").toString());
                    chefe.setLatidude(json.getJSONObject(i).get("latitude").toString());
                    chefe.setLongitude(json.getJSONObject(i).get("longitude").toString());
                    cozinheiro.add(chefe);

                    lat = chefe.getLatidude();
                    lng = chefe.getLongitude();

                   double Lat = Double.parseDouble(lat);
                   double Lng = Double.parseDouble(lng);

                    LatLng p = new LatLng(Lat, Lng);
                    cozinheiros.put(p,chefe);

              }

            } catch (JSONException e) {

                e.printStackTrace();

            }
            if (dialog.isShowing())
                dialog.dismiss();


             //////////////////// Pega as coordenadas e marca no mapa a localização \\\\\\\\\\\\\\\\\\\


           for ( int j = 0; j < cozinheiro.size();j++) {

              // cozinheiro = cozinheiros.get(j).getNome();
               lat = cozinheiro.get(j).getLatidude();
               lng = cozinheiro.get(j).getLongitude();

             if (!lat.isEmpty() || !lng.isEmpty()) {

                double Lat = Double.parseDouble(cozinheiro.get(j).getLatidude());
                double Lng = Double.parseDouble(cozinheiro.get(j).getLongitude());

                 LatLng position = new LatLng(Lat, Lng);
                 mMap.addMarker(new MarkerOptions().position(position).title(cozinheiro.get(j).getNome()));

                 mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                     @Override
                     public boolean onMarkerClick(Marker marker) {

                         SharedPreferences sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                         SharedPreferences.Editor editor = sp.edit();

                         Cozinheiro cozinheiro = cozinheiros.get(marker.getPosition());
                         editor.putString("cozinheiro", cozinheiro.getNome());
                         editor.apply();
                        

                         Intent intent = new Intent(MapsActivity.this,Prato.class);
                         startActivity(intent);
                         return false;
                     }
                 });

             }

           }
         }
    }

    public void onLocationChanged(Location location) {}
    public void onStatusChanged(String provider, int status, Bundle extras) {}
    public void onProviderEnabled(String provider) {}
    public void onProviderDisabled(String provider) {}
    public void onBackPressed(){

        startActivity(new Intent(getApplicationContext(),Main2Activity.class));



    }
}
