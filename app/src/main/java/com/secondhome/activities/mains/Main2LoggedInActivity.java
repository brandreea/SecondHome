package com.secondhome.activities.mains;

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
import com.secondhome.data.model.listeners.OnClickSwitchActivityListener;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.activities.login.LoginActivity;
import com.secondhome.data.model.menu.ActivityFactory;

import java.util.Objects;

public class Main2LoggedInActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
        nameMessage = findViewById(R.id.userMessage);
        nameMessage.append(" Bine ai venit, " + AppSingleton.getInstance(getApplicationContext()).getLoggedInUserName().toString() + "!");

        logout = findViewById(R.id.logout);
        View.OnClickListener listenerLogout = v -> {
            AppSingleton.getInstance(getApplicationContext()).logoutUser();
            Toast.makeText(getApplicationContext(), "Ati fost deconectat", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Main2LoggedInActivity.this, MainActivity.class);
            startActivity(intent);
        };
        logout.setOnClickListener(listenerLogout);

        addAnimal = findViewById(R.id.animalForm);
        View.OnClickListener listenerAddAnimal = new OnClickSwitchActivityListener(this,R.id.animalForm);

        addAnimal.setOnClickListener(listenerAddAnimal);
    }


    private void setNavigationViewListener() {
        navigationView = findViewById(R.id.mymenu);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawer = findViewById(R.id.mainmenu);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.close);
        navigationView = findViewById(R.id.mymenu);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = ActivityFactory.getIntent(Main2LoggedInActivity.this,item.getItemId());
        startActivity(intent);
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
