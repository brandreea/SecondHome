package com.secondhome.activities.showanimals.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.secondhome.data.model.others.PetTypes;
import com.secondhome.activities.showanimals.request.body.GetAnimalRequest;
import com.secondhome.R;
import com.secondhome.data.model.others.Animal;

import com.secondhome.data.model.others.AppSingleton;
import com.google.android.material.navigation.NavigationView;
import com.secondhome.data.model.menu.ActivityFactory;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

import java.util.Map;

public class AnimalProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    
    private ImageView profilePic;
    private TextView name, age, type,breed, description;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private Button edit,delete,adopt;
    private static final String UrlForAnimal="https://secondhome.fragmentedpixel.com/server/getanimalextended.php/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_profile);
            
        loadViews();
        setNavigationViewListener();
        
        loadAnimalDetails();


    }

    private void loadAnimalDetails() {
        System.out.println("Trying request animal");
        StringRequest strReq= new StringRequest(
                Request.Method.POST, UrlForAnimal, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response)
            {
                Log.d("AnimalProfile", "Register Response: "+ response);
                try{

                        System.out.println("Trying to request Object");
                        JSONObject obj=new JSONObject(response);
                        System.out.println(obj.toString());
                        Moshi moshi = new Moshi.Builder().build();
                        JsonAdapter<Animal> adapter = moshi.adapter(Animal.class);
                        final Animal a =adapter.fromJson(obj.toString());
                        String img64 = a.getImage();
                        System.out.println(img64);
                        if(img64 == null)
                            Picasso.get().load("https://i.imgur.com/q52cLwE.png").into(profilePic);
                        else{
                        String [] parts= img64.split(",");
                        byte[] decodedString = Base64.getDecoder().decode(parts[1]);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        profilePic.setImageBitmap(decodedByte);}

                        profilePic.setPadding(0,40,0,20);
                        profilePic.setMinimumWidth(600);
                        profilePic.setMinimumHeight(600);

                        System.out.println(a.getBirthdate());
                        age.setText(a.getBirthdate());
                        description.setText(a.getDescription());
                        name.setText(a.getName());
                        breed.setText(a.getBreed());
                        type.setText(PetTypes.getInstance().getPetType(a.getType()));

                        adopt.setOnClickListener(listenerAdopt);
                        adopt.setText("Adoptă");


                } catch(JSONException e)
                {
                    System.out.println("error here");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }, error -> {
            Log.e("AnimalProfileActivity", " error: "+ error.getMessage());
            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
        })
        {
            @Override
            protected Map<String,String> getParams(){
                String pid = AppSingleton.getInstance(getApplicationContext()).getAnimalPid();
                String uid;
                if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
                    uid= AppSingleton.getInstance(getApplicationContext()).getUser().getUID();
                else uid ="-1";
                return new GetAnimalRequest(pid,uid).map();
            }

        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"getAnimal");
    }
    View.OnClickListener listenerAdopt=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private void loadViews() {
        profilePic= findViewById(R.id.profilePicAnimal);
        name= findViewById(R.id.animalName);
        age=findViewById(R.id.age2);
        type= findViewById(R.id.cathegory2);
        breed = findViewById(R.id.pedegree2);
        description= findViewById(R.id.description2);
        edit= findViewById(R.id.editAnimal);
        delete= findViewById(R.id.deleteAnimal);
        delete.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);
        adopt= findViewById(R.id.adopt);
        adopt.setVisibility(View.GONE);
    }

    private void setNavigationViewListener() {
        navigationView =findViewById(R.id.mymenu);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawer=findViewById(R.id.animalProfile);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);

        navigationView = findViewById(R.id.mymenu);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = ActivityFactory.getIntent(  AnimalProfileActivity.this,item.getItemId());
        startActivity(intent);
        finish();
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        System.out.println("in OptionsItemSelected");

        if(mToggle.onOptionsItemSelected(item))
        {
            System.out.println("onOptionsItemSelected time to shine");
            return true;
        }
        System.out.println("onOptionsItemSeletced time");
        return super.onOptionsItemSelected(item);
    }


}
