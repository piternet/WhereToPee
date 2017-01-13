package net.nosek.wheretopee;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseAdapter dbUserAdapter;

    private Cursor userCursor;
    private ArrayList<User> users;

    private ArrayList<Toilet> toilets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        dbUserAdapter = new DatabaseAdapter(getApplicationContext());
        dbUserAdapter.open();
        dbUserAdapter.insertUser("admin", "Android 3.1");
        dbUserAdapter.insertUser("piternet", "Android 4.1");
        dbUserAdapter.insertUser("anonim", "iOS xd");
        dbUserAdapter.insertCoordinates(12, 21);
        getAllUsers();
        String msg = users.toString();
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

        // Add a marker in Warsaw and move the camera
        Coordinates coordinates = dbUserAdapter.getCoordinates(1);
        LatLng sydney = coordinates.toLatLng();
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Warsaw"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void getAllUsers() {
        users = new ArrayList<User>();
        userCursor = getAllUsersFromDatabase();
        updateUserList();
    }

    private Cursor getAllUsersFromDatabase() {
        userCursor = dbUserAdapter.getAllUsers();
        if(userCursor != null) {
            startManagingCursor(userCursor);
            userCursor.moveToFirst();
        }
        return userCursor;
    }

    void updateUserList() {
        if(userCursor != null && userCursor.moveToFirst()) {
            do {
                long id = userCursor.getLong(dbUserAdapter.ID_COLUMN);
                String nickname = userCursor.getString(dbUserAdapter.NICKNAME_COLUMN);
                String phoneInfo = userCursor.getString(dbUserAdapter.PHONEINFO_COLUMN);
                users.add(new User(id, nickname, phoneInfo));
            } while(userCursor.moveToNext());
        }
    }

    @Override
    protected void onDestroy() {
        if(dbUserAdapter != null)
            dbUserAdapter.close();
        super.onDestroy();
    }
}
