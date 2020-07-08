package com.secondhome.activities.mains;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.secondhome.R;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.activities.login.LoginActivity;
import com.secondhome.activities.login.RegisterActivity;
import com.secondhome.data.model.menu.ActivityFactory;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button login,register;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNavigationViewListener();
        mDrawer = findViewById(R.id.mainmenu);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.close);
//
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        login = findViewById(R.id.button5);
        register = findViewById(R.id.button6);

        View.OnClickListener listenerLogin = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };
        View.OnClickListener listenerRegister = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        };
        login.setOnClickListener(listenerLogin);
        register.setOnClickListener(listenerRegister);

    }


    private void setNavigationViewListener() {
        System.out.println("setting navigation listener");
        navigationView = (NavigationView) findViewById(R.id.mymenu);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        System.out.println("On navigation selected item");
        System.out.println(AppSingleton.getInstance(getApplicationContext()).getAnimalsToShow());
        Intent intent = ActivityFactory.getIntent(MainActivity.this,item.getItemId());
        startActivity(intent);
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
}
