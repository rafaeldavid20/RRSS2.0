package com.example.rafae.rrss20;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MapsActivity extends Activity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("coordenadas").child("x");
    DatabaseReference mConditionRef2 = mRootRef.child("coordenadas").child("y");
    private static int longitud = 10;
    private static int latitud = -66;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.bt_action_find) {

            mConditionRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int clongitud = dataSnapshot.getValue(int.class);
                    longitud = clongitud;

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mConditionRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int clatitud = dataSnapshot.getValue(int.class);
                    latitud = clatitud;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            // Add a marker in Sydney and move the camera
            LatLng vehiculo = new LatLng(longitud, latitud);

            mMap.addMarker(new MarkerOptions().position(vehiculo).title("Aqui esta su vehiculo")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.carroicon)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(vehiculo));


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng vehiculo = new LatLng(longitud, latitud);

        mMap.addMarker(new MarkerOptions().position(vehiculo).title("Aqui esta su vehiculo")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.carro)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vehiculo));
    }

}
