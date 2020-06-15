package com.secondhome.mains;

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
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.secondhome.R;
import com.secondhome.contact.ContactActivity;
import com.secondhome.locations.LocationActvity;
import com.secondhome.login.AppSingleton;
import com.secondhome.login.LoginActivity;
import com.secondhome.login.MyProfileActivity;
import com.secondhome.showanimals.AddAnimalFormActivity;
import com.secondhome.showanimals.AnimalsActivity;
import com.secondhome.showanimals.MyAnimalsActivity;

public class Main2LoggedInActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private TextView nameMessage;
    private DrawerLayout mDrawer;
    private Button logout;
    private Button addAnimal;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private ActionMenuItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_logged_in);
        setNavigationViewListener();
        nameMessage= findViewById(R.id.userMessage);
        nameMessage.append(" Bine ai venit, "+ AppSingleton.getInstance(getApplicationContext()).getLoggedInUserName().toString()+"!");

        mDrawer=findViewById(R.id.mainmenu);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);
        navigationView = findViewById(R.id.mymenu);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //logout
        logout= findViewById(R.id.logout);
        View.OnClickListener lisenerLogout=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSingleton.getInstance(getApplicationContext()).logoutUser();
                Toast.makeText(getApplicationContext(), "Ati fost deconectat", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Main2LoggedInActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };
        logout.setOnClickListener(lisenerLogout);

        addAnimal= findViewById(R.id.animalForm);
        View.OnClickListener listenerAddAnimal=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main2LoggedInActivity.this, AddAnimalFormActivity.class);
                startActivity(intent);
            }
        };
        addAnimal.setOnClickListener(listenerAddAnimal);

    }


    private void setNavigationViewListener() {
        System.out.println("in here");
        navigationView = findViewById(R.id.mymenu);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        System.out.println("On navigation selected item");
        switch (item.getItemId()) {

            case R.id.db0:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("0");
                Intent intent=new Intent(Main2LoggedInActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db1:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("1");
                intent=new Intent(Main2LoggedInActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db2:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("2");
                intent=new Intent(Main2LoggedInActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db3:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("3");
                intent=new Intent(Main2LoggedInActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db4:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("4");
                intent=new Intent(Main2LoggedInActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db5:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("5");
                intent=new Intent(Main2LoggedInActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db6:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("6");
                intent=new Intent(Main2LoggedInActivity.this,AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db:
                if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
                {
                    intent=new Intent(Main2LoggedInActivity.this, Main2LoggedInActivity.class);
                    intent.putExtra("username", AppSingleton.getInstance(getApplicationContext()).getLoggedInUserName());
                }
                else intent=new Intent(Main2LoggedInActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.db8:
                intent=new Intent(Main2LoggedInActivity.this, ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.db9:
                if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null) {
                    intent=new Intent(Main2LoggedInActivity.this, MyProfileActivity.class);
                } else intent=new Intent(Main2LoggedInActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.db10:
                intent=new Intent(Main2LoggedInActivity.this, LocationActvity.class);
                startActivity(intent);
                break;
            case R.id.db11:
                intent=new Intent(Main2LoggedInActivity.this, MyAnimalsActivity.class);
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
        if(item.getItemId()==R.id.db)
        {
            System.out.println("in here");
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
