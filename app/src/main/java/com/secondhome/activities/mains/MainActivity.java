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
import com.secondhome.data.model.listeners.OnClickSwitchActivityListener;
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

        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerButton);

        View.OnClickListener listenerLogin = new OnClickSwitchActivityListener(this,R.id.loginButton);
        View.OnClickListener listenerRegister = new OnClickSwitchActivityListener(this,R.id.registerButton);
        login.setOnClickListener(listenerLogin);
        register.setOnClickListener(listenerRegister);

    }


    private void setNavigationViewListener() {
        navigationView =  findViewById(R.id.mymenu);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawer = findViewById(R.id.mainmenu);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.close);
        navigationView = findViewById(R.id.mymenu);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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