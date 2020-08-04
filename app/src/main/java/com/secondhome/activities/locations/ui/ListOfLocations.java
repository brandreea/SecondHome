package com.secondhome.activities.locations.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.google.android.material.navigation.NavigationView;
import com.secondhome.R;
import com.secondhome.activities.locations.ui.listener.OnClickShowLocationListener;
import com.secondhome.data.model.menu.ActivityFactory;


public class ListOfLocations extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Button [] locationButtons = new Button[3];
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private ActionMenuItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_locations);

        setNavigationViewListener();

        int [] id = new int[]{R.id.location1, R.id.location2,R.id.location3};
        for(int i=1;i<=3;i++){
            locationButtons[i-1] = findViewById(id[i-1]);
            View.OnClickListener listener= new OnClickShowLocationListener(this,
                    R.id.showLocations,i);
            locationButtons[i-1].setOnClickListener(listener);
        }

    }
    private void setNavigationViewListener() {
        navigationView = (NavigationView) findViewById(R.id.mymenu);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawer=(DrawerLayout) findViewById(R.id.locationList);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent intent = ActivityFactory.getIntent(ListOfLocations.this,item.getItemId());
        startActivity(intent);
//        finish();
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
