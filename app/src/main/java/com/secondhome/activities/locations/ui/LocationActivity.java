package com.secondhome.activities.locations.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.secondhome.R;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.data.model.menu.ActivityFactory;

import org.jetbrains.annotations.NotNull;

public class LocationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private int location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_actvity);

        setNavigationViewListener();
        SupportMapFragment mapFragment1 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps1);

        mapFragment1.getMapAsync(this);

        location= AppSingleton.getInstance(getApplicationContext()).getLocation();

    }
    private void setNavigationViewListener() {

        navigationView = (NavigationView) findViewById(R.id.mymenu);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawer=(DrawerLayout) findViewById(R.id.showLocations);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = ActivityFactory.getIntent(LocationActivity.this,item.getItemId());
        startActivity(intent);
//        finish();
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item){

        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng place = null;
        switch(location){
            case 1:
                place=new LatLng(44.439663,  26.096306);
                break;
            case 2:
                place=new LatLng(44.4396,  26.196306);
                break;
            case 3:
                place=new LatLng(44.4316, 26.196306);
                break;
        }
        if(place==null)
           place = new LatLng(44.439663,  26.096306);
        googleMap.addMarker(new MarkerOptions().position(place)
                .title("SecondHome"));
        CameraPosition p=new CameraPosition.Builder()
                .target(place)
                .zoom(18)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(p));


    }

}
