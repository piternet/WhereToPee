package net.nosek.wheretopee;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if(!firstLocation)
            Toast.makeText(getApplicationContext(), R.string.determine_location, Toast.LENGTH_LONG).show();
        loadDatabase();
        getLocation();
    }

    private void getLocation() {
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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 15));
        mMap.addMarker(new MarkerOptions().position(mLocation).title("My location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        for(Toilet toilet : toilets) {
            LatLng position = toilet.getCoordinates().toLatLng();
            mMap.addMarker(new MarkerOptions().position(position).title(toilet.getDescription()).snippet(toilet.toString()));
        }
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                Context mContext = getApplicationContext();
                LinearLayout info = new LinearLayout(mContext);
                info.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 0.7f);
                info.setLayoutParams(params);

                TextView title = new TextView(mContext);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mContext);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }

    private void loadDatabase() {
        dbAdapter = new DatabaseAdapter(getApplicationContext());
        dbAdapter.open();
        if(dbAdapter.isEmpty())
            dbAdapter.insertData();
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
