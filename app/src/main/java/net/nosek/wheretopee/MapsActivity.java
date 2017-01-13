package net.nosek.wheretopee;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseAdapter dbAdapter;

    private Cursor userCursor;
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
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        try {
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
            else {
                mapFragment.getMapAsync(this);
                overridePendingTransition(R.anim.push_up_out, R.anim.push_up_in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        dbAdapter = new DatabaseAdapter(getApplicationContext());
        dbAdapter.open();
        if(dbAdapter.isEmpty())
            insertData();
        getAllDataFromDatabase();
        String msg = users.toString() + "\n" + toilets.toString();
        Log.d("USER TAG", msg);
    }

    /**
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for(Toilet toilet : toilets) {
            LatLng position = toilet.getCoordinates().toLatLng();
            mMap.addMarker(new MarkerOptions().position(position).title(toilet.getDescription()));
        }
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private void getAllDataFromDatabase() {
        getAllUsers();
        getAllToilets();
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

    @Override
    protected void onDestroy() {
        if(dbAdapter != null)
            dbAdapter.close();
        super.onDestroy();
    }
}
