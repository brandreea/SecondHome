package com.secondhome.locations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
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
import com.secondhome.contact.ContactActivity;
import com.secondhome.login.AppSingleton;
import com.secondhome.mains.Main2LoggedInActivity;
import com.secondhome.mains.MainActivity;
import com.secondhome.showanimals.AnimalsActivity;
import com.secondhome.showanimals.MyAnimalsActivity;

public class LocationActvity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private ActionMenuItem item;
    private SupportMapFragment mapFragment1;
    private int location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_actvity);

        setNavigationViewListener();
        mDrawer=(DrawerLayout) findViewById(R.id.showLocations);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);
        navigationView = (NavigationView) findViewById(R.id.mymenu);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mapFragment1 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps1);
        mapFragment1.getMapAsync(this);

        location= AppSingleton.getInstance(getApplicationContext()).getLocation();

    }
    private void setNavigationViewListener() {
        System.out.println("setting navigation listener");
        NavigationView navigationView = (NavigationView) findViewById(R.id.mymenu);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        System.out.println("On navigation selected item");
        System.out.println(AppSingleton.getInstance(getApplicationContext()).getAnimalsToShow());
        switch (item.getItemId()) {

            case R.id.db0:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("0");
                Intent intent=new Intent(LocationActvity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db1:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("1");
                intent=new Intent(LocationActvity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db2:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("2");
                intent=new Intent(LocationActvity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db3:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("3");
                intent=new Intent(LocationActvity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db4:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("4");
                intent=new Intent(LocationActvity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db5:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("5");
                intent=new Intent(LocationActvity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db6:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("6");
                intent=new Intent(LocationActvity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db:
                if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
                {
                    intent=new Intent(LocationActvity.this, Main2LoggedInActivity.class);
                    intent.putExtra("username", AppSingleton.getInstance(getApplicationContext()).getLoggedInUserName());
                }
                else intent=new Intent(LocationActvity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.db8:
                intent=new Intent(LocationActvity.this, ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.db10:
                intent=new Intent(LocationActvity.this, ListOfLocations.class);
                startActivity(intent);
                break;
            case R.id.db11:
                intent=new Intent(LocationActvity.this, MyAnimalsActivity.class);
                startActivity(intent);
                break;
        }
        //close navigation drawer
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        System.out.println("in here");
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = null;
        System.out.println(location);
        switch(location){
            case 1:
                sydney=new LatLng(44.439663,  26.096306);
                break;
            case 2:
                sydney=new LatLng(44.4396,  26.196306);
                break;
            case 3:
                sydney=new LatLng(44.4316, 26.196306);
                break;
        }
        if(sydney==null)
           sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("SecondHome"));
        CameraPosition p=new CameraPosition.Builder()
                .target(sydney)      // Sets the center of the map to location user
                .zoom(18)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(p));


    }

}
