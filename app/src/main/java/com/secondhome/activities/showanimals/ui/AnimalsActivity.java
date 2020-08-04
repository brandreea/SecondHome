package com.secondhome.activities.showanimals.ui;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import android.widget.LinearLayout;

import android.widget.Toast;

import com.android.volley.Request;

import com.android.volley.toolbox.StringRequest;

import com.google.android.material.navigation.NavigationView;
import com.secondhome.R;
import com.secondhome.activities.showanimals.request.body.GetAnimalsRequest;
import com.secondhome.activities.showanimals.request.listener.AnimalsListener;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.data.model.menu.ActivityFactory;
import java.util.Map;
import java.util.Objects;


public class AnimalsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private LinearLayout catsView;
    private Drawable paw;
    private static final String UrlForLogin="https://secondhome.fragmentedpixel.com/server/getanimals.php/";
    private String animalType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);
        paw= getApplicationContext().getResources().getDrawable(R.drawable.ic_pets_white_24dp);
        animalType= AppSingleton.getInstance(getApplicationContext()).getAnimalsToShow();
        setNavigationViewListener();
        catsView= findViewById(R.id.linearCats);
        getAnimals();

    }

    private void getAnimals(){
        System.out.println("Trying request animals");
        StringRequest strReq= new StringRequest(
                Request.Method.POST, UrlForLogin,
                new AnimalsListener(getApplicationContext(),
                        AnimalsActivity.this,
                        this,
                        catsView,
                        paw)
        , error -> {
            Log.e("CatsActivity", "Login error: "+ error.getMessage());
            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
        })
        {
            @Override
            protected Map<String,String> getParams(){
                return new GetAnimalsRequest(animalType).map();
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"getAnimals");
    }
    private void setNavigationViewListener() {
        navigationView = findViewById(R.id.mymenuCats);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawer= findViewById(R.id.showallCats);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        System.out.println(AppSingleton.getInstance(getApplicationContext()).getAnimalsToShow());
        Intent intent = ActivityFactory.getIntent(AnimalsActivity.this,item.getItemId());
        startActivity(intent);
//        finish();
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
