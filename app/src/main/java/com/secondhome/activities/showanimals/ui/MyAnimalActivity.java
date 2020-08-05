package com.secondhome.activities.showanimals.ui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.secondhome.activities.showanimals.request.body.GetAnimalRequest;
import com.secondhome.activities.showanimals.request.listener.GetMyAnimalListener;
import com.secondhome.R;
import com.secondhome.data.model.others.AppSingleton;
import com.google.android.material.navigation.NavigationView;
import com.secondhome.data.model.menu.ActivityFactory;

import org.jetbrains.annotations.NotNull;
import java.util.Map;
import java.util.Objects;

public class MyAnimalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private static final String URL_FOR_MY_ANIMAL="https://secondhome.fragmentedpixel.com/server/getanimalextended.php/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_profile);
        setNavigationViewListener();
        loadAnimalDetails();

    }

    private void loadAnimalDetails() {
        System.out.println("Trying request animal");
        StringRequest strReq= new StringRequest(
                Request.Method.POST, URL_FOR_MY_ANIMAL,
                    new GetMyAnimalListener(getApplicationContext(),
                            MyAnimalActivity.this,
                            this)
                , error -> {
            Log.e("AnimalProfileActivity", " error: "+ error.getMessage());
            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new GetAnimalRequest(AppSingleton.getAnimalPid(),
                        (AppSingleton.getInstance(getApplicationContext()).getUser()==null?"-1":
                                AppSingleton.getInstance(getApplicationContext()).getUser().getUID())).map();
                return params;
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"getAnimal");
    }

    private void setNavigationViewListener() {
        navigationView =  findViewById(R.id.mymenu);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawer=(DrawerLayout) findViewById(R.id.animalProfile);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = ActivityFactory.getIntent(MyAnimalActivity.this,item.getItemId());
        startActivity(intent);
        finish();
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item){
        System.out.println("in OptionsItemSelected");

        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
