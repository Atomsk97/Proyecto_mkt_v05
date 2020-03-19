package com.example.proyecto_mkt_v05.utilities;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.proyecto_mkt_v05.entities.Coordenadas;
import com.example.proyecto_mkt_v05.entities.Fecha;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Calendar;
import java.util.Objects;

public class MapsActivity extends FragmentActivity{

    private double userLatitude, userLongitude;
    private static OnMapsActivityInteractionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getApplicationContext());

        if (status == ConnectionResult.SUCCESS) {
            Toast.makeText(getApplicationContext(), "Conexión exitosa", Toast.LENGTH_SHORT)
                    .show();
        } else if (ConnectivityIsNotWorking.getConnectivityStatus(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "El dispositivo movil " +
                    "no tiene conexion a internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), " Error inesperado\nNo se pudo conectar con" +
                    " Google Play services", Toast.LENGTH_SHORT).show();
        }

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 1000);
        } else {
            locationStart();
        }

    }

    private void locationStart(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion local = new Localizacion();
        local.setMapsActivity(this);
        final boolean gpsEnabled = Objects.requireNonNull(locationManager).isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!gpsEnabled){
            Toast.makeText(getApplicationContext(),"Por favor active su GPS", Toast.LENGTH_SHORT)
                    .show();
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 1000);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, local);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, local);
    }

    public class Localizacion implements LocationListener{
        MapsActivity mapsActivity;

        public MapsActivity getMapsActivity(){
            return  mapsActivity;
        }

        public void setMapsActivity(MapsActivity mapsActivity){
            this.mapsActivity = mapsActivity;
        }

        public void onLocationChanged(Location loc){
            userLatitude = loc.getLatitude();
            userLongitude = loc.getLatitude();
            onBackPressed();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status){
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    public static void passVal(OnMapsActivityInteractionListener communicator){
        mListener = communicator;
    }

    @Override
    public void onBackPressed() {
        sendBack();
        finish();
    }

    public void sendBack() {
        if(mListener != null){
            Calendar calendar = Calendar.getInstance();
            mListener.onMapsActivityInteraction(new Coordenadas(userLatitude, userLongitude),
                    new Fecha(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1),
                            calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE)), true);
        }
    }

    public interface OnMapsActivityInteractionListener{
        void onMapsActivityInteraction(Coordenadas coordenadas, Fecha fecha, boolean isBack);
    }
}
