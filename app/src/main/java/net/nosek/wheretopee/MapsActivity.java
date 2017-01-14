package net.nosek.wheretopee;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderApi fusedLocationProviderApi;
    private LocationRequest locationRequest;
    private DatabaseAdapter dbAdapter;
    private boolean firstLocation = false;
    private LatLng mLocation;

    private ArrayList<User> users;
    private ArrayList<Toilet> toilets;

    private void insertData() {
        dbAdapter.insertUser("admin", "Android 3.1");
        dbAdapter.insertUser("piternet", "Android 4.1");
        dbAdapter.insertUser("anonim", "iOS xd");
        dbAdapter.insertCoordinates(52.211997, 20.982090);
        dbAdapter.insertCoordinates(52.188821, 21.002455);
        dbAdapter.insertToilet(dbAdapter.getCoordinates(1), dbAdapter.getUser(1), "MIMowa toaletka", false, false, false, true);
        dbAdapter.insertToilet(dbAdapter.getCoordinates(2), dbAdapter.getUser(3), "Druga toaletka", false, true, true, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if(!firstLocation)
            Toast.makeText(getApplicationContext(), "Waiting to determine your location..." , Toast.LENGTH_LONG).show();
        loadDatabase();
        getLocation();
    }

    private void getLocation(){
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(500);
        locationRequest.setFastestInterval(500);
        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(Bundle arg0) {
        try {
            fusedLocationProviderApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        } catch(SecurityException e) {

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(!firstLocation) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            mLocation = new LatLng(location.getLatitude(), location.getLongitude());
            firstLocation = true;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 15));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 15));
        for(Toilet toilet : toilets) {
            LatLng position = toilet.getCoordinates().toLatLng();
            mMap.addMarker(new MarkerOptions().position(position).title(toilet.getDescription()));
        }
    }

    private void loadDatabase() {
        dbAdapter = new DatabaseAdapter(getApplicationContext());
        dbAdapter.open();
        if(dbAdapter.isEmpty())
            insertData();
        getAllUsers();
        getAllToilets();
        String msg = users.toString() + "\n" + toilets.toString();
        Log.d("USER TAG", msg);
    }

    private void getAllUsers() {
        users = new ArrayList<User>();
        long id = 1;
        User user = null;
        do {
            user = dbAdapter.getUser(id);
            if(user != null)
                users.add(user);
            id++;
        } while(user != null);
    }

    private void getAllToilets() {
        toilets = new ArrayList<Toilet>();
        long id = 1;
        Toilet toilet = null;
        do {
            toilet = dbAdapter.getToilet(id);
            if(toilet != null)
                toilets.add(toilet);
            id++;
        } while(toilet != null);
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void onConnectionSuspended(int i) {}
    public void onConnectionFailed(ConnectionResult c) {}

    @Override
    protected void onDestroy() {
        if(dbAdapter != null)
            dbAdapter.close();
        super.onDestroy();
    }
}
