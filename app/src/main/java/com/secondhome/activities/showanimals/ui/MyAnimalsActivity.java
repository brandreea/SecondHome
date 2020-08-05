package com.secondhome.activities.showanimals.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import com.google.android.material.navigation.NavigationView;
import com.secondhome.R;
import com.secondhome.activities.showanimals.request.listener.MyAnimalsListener;
import com.secondhome.activities.showanimals.request.body.GetAnimalsCustomRequest;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.activities.login.ui.LoginActivity;
import com.secondhome.data.model.menu.ActivityFactory;
import java.util.Map;

public class MyAnimalsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private LinearLayout catsView;
    private Drawable paw;
    private static final String URL_FOR_MY_ANIMALS="https://secondhome.fragmentedpixel.com/server/getanimals.php/";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_animals);
        paw= getApplicationContext().getResources().getDrawable(R.drawable.ic_pets_white_24dp);
        setNavigationViewListener();
        catsView= findViewById(R.id.myLinearCats);
        if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null) getCats();
        else sendToLogin();

    }

    private void sendToLogin() {
        Intent intent=new Intent(MyAnimalsActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void getCats(){
        System.out.println("Trying request animals");
        StringRequest strReq= new StringRequest(
                Request.Method.POST, URL_FOR_MY_ANIMALS,
                    new MyAnimalsListener(getApplicationContext(),
                            MyAnimalsActivity.this,
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
                return new GetAnimalsCustomRequest(AppSingleton
                        .getInstance(getApplicationContext()).getUser().getUID()).map();
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"getCats");
    }

    private void setNavigationViewListener() {
        navigationView = findViewById(R.id.mymenu);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawer=findViewById(R.id.myanimals);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = ActivityFactory.getIntent(MyAnimalsActivity.this,item.getItemId());
        startActivity(intent);
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
