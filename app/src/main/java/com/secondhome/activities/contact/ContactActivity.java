package com.secondhome.activities.contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.secondhome.R;
import com.secondhome.data.model.listeners.OnClickSwitchActivityListener;
import com.secondhome.data.model.menu.ActivityFactory;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class ContactActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private int [] views = new int[]{R.id.aboutUs, R.id.achievments, R.id.facilities,R.id.contactUs};
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        setNavigationViewListener();
        mDrawer=findViewById(R.id.contactPage);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);

        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setListeners();

    }
    private void setListeners(){
        for(int id: views)
        findViewById(id).setOnClickListener(new OnClickSwitchActivityListener(ContactActivity.this, id));
    }
    private void setNavigationViewListener() {
        NavigationView navigationView =  findViewById(R.id.mymenu);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = ActivityFactory.getIntent(ContactActivity.this,item.getItemId());
        startActivity(intent);
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
