package com.secondhome.activities.showanimals;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
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
import com.secondhome.data.model.request.body.AnimalRequest;
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
import java.util.HashMap;
import java.util.Map;

public class MyAnimalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private ImageView profilePic;
    private TextView name, age, type,breed, description;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private ActionMenuItem item;
    private Button edit,delete, adopt;
    private static final String UrlForAnimal="https://secondhome.fragmentedpixel.com/server/getanimalextended.php/";
    private static final String UrlForDeletingAnimal="https://secondhome.fragmentedpixel.com/server/deleteanimal.php/";
    private static final String UrlForAdoptingAnimal="https://secondhome.fragmentedpixel.com/server/animalrequest.php/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_profile);

        loadViews();
        setNavigationViewListener();
//
        // TODO CHANGE ID
        mDrawer=(DrawerLayout) findViewById(R.id.animalProfile);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);

        // TODO CHANGE ID
        navigationView = (NavigationView) findViewById(R.id.mymenu);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
        adopt = findViewById(R.id.adopt);
        loadAnimalDetails();
        if(adopt.getText().toString().equals("Da spre adoptie"))
        adopt.setOnClickListener(adoptListener);
        else adopt.setEnabled(false);

    }

    private void loadAnimalDetails() {
        System.out.println("Trying request animal");
        StringRequest strReq= new StringRequest(
                Request.Method.POST, UrlForAnimal, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response)
            {
                Log.d("AnimalProfile", "Register Response: "+ response.toString());
                try{

                    System.out.println("Trying to request Object");
                    JSONObject obj=new JSONObject(response);
                    System.out.println(obj.toString());
                    Moshi moshi = new Moshi.Builder().build();
                    JsonAdapter<Animal> adapter = moshi.adapter(Animal.class);
                    final Animal a =adapter.fromJson(obj.toString());
                    System.out.println(a.toString());
                  //ImageView img=new ImageView(AnimalProfileActivity.this);
//
////                        Picasso.get().load("https://i.imgur.com/XAuRrVz.jpg").into(profilePic);
////                        img.setPadding(0,20,0,20);
                    String img64 = a.getImage();
                    System.out.println(img64);
                    if(img64 == null)
                        Picasso.get().load("https://i.imgur.com/q52cLwE.png").into(profilePic);
                    else{
                    String [] parts= img64.split(",");
                    //img64.replace("\\", "");

                    byte[] decodedString = Base64.getDecoder().decode(parts[1]);
                    System.out.println(decodedString.toString());
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    profilePic.setImageBitmap(decodedByte);}
                    profilePic.setPadding(0,40,0,20);
                    profilePic.setMinimumWidth(600);
                    profilePic.setMinimumHeight(600);
//
                    System.out.println(a.getBirthdate());
                    age.setText(a.getBirthdate());
                    description.setText(a.getDescription());
                    name.setText(a.getName());
                    breed.setText(a.getBreed());
                    type.setText(PetTypes.getInstance().getPetType(a.getType()));
                    View.OnClickListener listenerEdit=new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppSingleton.getInstance(getApplicationContext()).setCurrentAnimal(a);
                            Intent intent=new Intent(MyAnimalActivity.this,EditAnimalForm.class);
                            startActivity(intent);
                        }
                    };
                    edit.setOnClickListener(listenerEdit);
                    delete.setOnClickListener(deleteListener);
                    if(AppSingleton.getInstance(getApplicationContext()).getAdoptionState()>0)
                    {adopt.setText("Cerere dare spre adoptie acceptata");
                    adopt.setEnabled(false);}
                    else adopt.setText("Da spre adoptie.");

                } catch(JSONException e)
                {
                    System.out.println("error here");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("AnimalProfileActivity", " error: "+ error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG);
            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String,String>();
                params.put("security_code", "8981ASDGHJ22123");
                params.put("PID", AppSingleton.getInstance(getApplicationContext()).getAnimalPid());
                if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
                    params.put("UID", AppSingleton.getInstance(getApplicationContext()).getUser().getUID().toString());
                else params.put("UID","-1");

                System.out.println(params.toString());
                return params;
            }

        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"getAnimal");
    }
    private View.OnClickListener adoptListener =new View.OnClickListener()
    {
        @Override
        public void onClick(View v){
            StringRequest strReq= new StringRequest(
                    Request.Method.POST,
                    UrlForAdoptingAnimal,
                    response -> {
                        Log.d("AnimalProfile", "Delete Response: "+ response.toString());
                        Toast.makeText(getApplicationContext(),"Animaluțul a fost dat spre adoptie cu succes",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(MyAnimalActivity.this,MyAnimalsActivity.class);
                        startActivity(intent);
                    },
                    error -> {
                        Log.e("AnimalProfileActivity", " error: "+ error.getMessage());
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG);
                    }){
                @Override
                protected Map<String,String> getParams(){
                    return new AnimalRequest(
                            AppSingleton.getInstance(getApplicationContext()).getAnimalPid(),
                            AppSingleton.getInstance(getApplicationContext()).getUser().getUID().toString(),
                            "0").map();
                }
            };
            AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"deleteAnimal");
        }
    };
    private void loadViews() {
        profilePic=(ImageView) findViewById(R.id.profilePicAnimal);
        name=(TextView) findViewById(R.id.animalName);
        age=(TextView) findViewById(R.id.age2);
        type=(TextView) findViewById(R.id.cathegory2);
        breed =(TextView) findViewById(R.id.pedegree2);
        description=(TextView) findViewById(R.id.description2);
        edit=(Button) findViewById(R.id.editAnimal);
        delete=(Button) findViewById(R.id.deleteAnimal);
    }
    private View.OnClickListener deleteListener = v -> {
        System.out.println("Trying delete animal");
        StringRequest strReq= new StringRequest(
                Request.Method.POST,
                UrlForDeletingAnimal,
                response -> {
                    Log.d("AnimalProfile", "Delete Response: "+ response.toString());
                    Toast.makeText(getApplicationContext(),"Animaluțul a fost sters cu succes",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MyAnimalActivity.this,MyAnimalsActivity.class);
                    startActivity(intent);
                },
                error -> {
                    Log.e("AnimalProfileActivity", " error: "+ error.getMessage());
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG);
                }){
            @Override
            protected Map<String,String> getParams(){
                return new AnimalRequest(AppSingleton.getInstance(getApplicationContext()).getAnimalPid(),
                        AppSingleton.getInstance(getApplicationContext()).getUser().getUID().toString()).map();
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"deleteAnimal");

    };
    private void setNavigationViewListener() {
        System.out.println("setting navigation listener");
        NavigationView navigationView = (NavigationView) findViewById(R.id.mymenu);
        System.out.println(navigationView.toString());
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = ActivityFactory.getIntent(MyAnimalActivity.this,item.getItemId());
        startActivity(intent);
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        System.out.println("in OptionsItemSelected");

        if(mToggle.onOptionsItemSelected(item))
        {
            System.out.println("onOptionsItemSeletced time to shine");
            return true;
        }
        System.out.println("onOptionsItemSeletced time");
        return super.onOptionsItemSelected(item);
    }


}