package com.secondhome.mains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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
import com.secondhome.locations.ListOfLocations;
import com.secondhome.login.AppSingleton;
import com.secondhome.login.LoginActivity;
import com.secondhome.login.MyProfileActivity;
import com.secondhome.login.RegisterActivity;
import com.secondhome.showanimals.AnimalsActivity;
import com.secondhome.showanimals.MyAnimalsActivity;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private Button login,register;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setNavigationViewListener();
        mDrawer=findViewById(R.id.mainmenu);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);

        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        login =findViewById(R.id.button5);
        register= findViewById(R.id.button6);

        View.OnClickListener lisenerLogin=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };
        View.OnClickListener lisenerRegister=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        };
        login.setOnClickListener(lisenerLogin);
        register.setOnClickListener(lisenerRegister);

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
                Intent intent=new Intent(MainActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db1:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("1");
                intent=new Intent(MainActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db2:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("2");
                intent=new Intent(MainActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db3:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("3");
                intent=new Intent(MainActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db4:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("4");
                intent=new Intent(MainActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db5:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("5");
                intent=new Intent(MainActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db6:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("6");
                intent=new Intent(MainActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db:
                if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
                {
                    intent=new Intent(MainActivity.this, Main2LoggedInActivity.class);
                    intent.putExtra("username", AppSingleton.getInstance(getApplicationContext()).getLoggedInUserName());
                }
                else intent=new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.db8:
                intent=new Intent(MainActivity.this, ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.db9:
                if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
                    intent=new Intent(MainActivity.this, MyProfileActivity.class);
                else intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.db10:
                intent=new Intent(MainActivity.this, ListOfLocations.class);
                startActivity(intent);
                break;
            case R.id.db11:
                intent=new Intent(MainActivity.this, MyAnimalsActivity.class);
                startActivity(intent);
        }
        //close navigation drawer
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item){
        System.out.println("in here");
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
