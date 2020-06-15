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
import android.view.View;
import android.widget.Button;


import com.google.android.material.navigation.NavigationView;
import com.secondhome.R;
import com.secondhome.contact.ContactActivity;
import com.secondhome.data.model.AppSingleton;
import com.secondhome.login.LoginActivity;
import com.secondhome.login.MyProfileActivity;
import com.secondhome.mains.Main2LoggedInActivity;
import com.secondhome.mains.MainActivity;
import com.secondhome.showanimals.AnimalsActivity;
import com.secondhome.showanimals.MyAnimalsActivity;


public class ListOfLocations extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button b1,b2,b3;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private ActionMenuItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_locations);

        setNavigationViewListener();
        mDrawer=(DrawerLayout) findViewById(R.id.locationList);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);
        navigationView = (NavigationView) findViewById(R.id.mymenu);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        b1=(Button) findViewById(R.id.location1);
        View.OnClickListener listenerb1=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSingleton.getInstance(getApplicationContext()).setLocation(1);
                Intent intent=new Intent(ListOfLocations.this, LocationActvity.class);
                startActivity(intent);
            }
        };
        b1.setOnClickListener(listenerb1);

        b2=(Button) findViewById(R.id.location2);
        View.OnClickListener listenerb2=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSingleton.getInstance(getApplicationContext()).setLocation(2);
                Intent intent=new Intent(ListOfLocations.this, LocationActvity.class);
                startActivity(intent);
            }
        };
        b2.setOnClickListener(listenerb2);

        b3=(Button) findViewById(R.id.location3);
        View.OnClickListener listenerb3=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSingleton.getInstance(getApplicationContext()).setLocation(3);
                Intent intent=new Intent(ListOfLocations.this, LocationActvity.class);
                startActivity(intent);
            }
        };
        b3.setOnClickListener(listenerb3);

    }
    private void setNavigationViewListener() {
        System.out.println("setting navigation listener");
        NavigationView navigationView = (NavigationView) findViewById(R.id.mymenu);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        System.out.println("On navigation selected item");
        System.out.println(AppSingleton.getInstance(getApplicationContext()).getAnimalsToShow());
        switch (item.getItemId()) {

            case R.id.db0:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("0");
                Intent intent=new Intent(ListOfLocations.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db1:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("1");
                intent=new Intent(ListOfLocations.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db2:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("2");
                intent=new Intent(ListOfLocations.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db3:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("3");
                intent=new Intent(ListOfLocations.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db4:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("4");
                intent=new Intent(ListOfLocations.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db5:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("5");
                intent=new Intent(ListOfLocations.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db6:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("6");
                intent=new Intent(ListOfLocations.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db:
                if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
                {
                    intent=new Intent(ListOfLocations.this, Main2LoggedInActivity.class);
                    intent.putExtra("username", AppSingleton.getInstance(getApplicationContext()).getLoggedInUserName());
                }
                else intent=new Intent(ListOfLocations.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.db8:
                intent=new Intent(ListOfLocations.this, ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.db9:
                if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
                    intent=new Intent(ListOfLocations.this, MyProfileActivity.class);
                else intent=new Intent(ListOfLocations.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.db10:
                intent=new Intent(ListOfLocations.this, ListOfLocations.class);
                startActivity(intent);
                break;
            case R.id.db11:
                intent=new Intent(ListOfLocations.this, MyAnimalsActivity.class);
                startActivity(intent);
                break;
        }
        //close navigation drawer
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
