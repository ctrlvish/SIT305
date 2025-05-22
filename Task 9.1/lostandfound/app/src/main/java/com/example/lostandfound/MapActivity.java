package com.example.lostandfound;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        dbHelper = new DatabaseHelper(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<Item> items = dbHelper.getAllItems();
        if (!items.isEmpty()) {
            for (Item item : items) {
                LatLng itemLocation = new LatLng(item.getLatitude(), item.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(itemLocation)
                        .title(item.getPostType() + ": " + item.getName()));
            }

            LatLng first = new LatLng(items.get(0).getLatitude(), items.get(0).getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(first, 12f));
        }
    }
}