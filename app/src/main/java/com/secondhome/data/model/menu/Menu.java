package com.secondhome.data.model.menu;

import android.app.Activity;
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


public class Menu implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private AppCompatActivity activity;


    public Menu(DrawerLayout drawerLayout,
                ActionBarDrawerToggle actionBarDrawerToggle,
                NavigationView navigationView,
                AppCompatActivity activity) {
        this.activity=activity;
        this.mDrawer = drawerLayout;
        this.mToggle = actionBarDrawerToggle;
        this.mDrawer.addDrawerListener(mToggle);
//        this.mToggle.syncState();
        this.activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.navigationView = navigationView;
        this.navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent intent = ActivityFactory.getIntent(activity,item.getItemId());
        this.activity.startActivity(intent);
        this.mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
