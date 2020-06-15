package com.secondhome.showanimals;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import com.google.android.material.navigation.NavigationView;
import com.secondhome.R;
import com.secondhome.contact.ContactActivity;
import com.secondhome.data.model.Animal;
import com.secondhome.data.model.request.body.GetAnimalsCustomRequest;
import com.secondhome.locations.ListOfLocations;
import com.secondhome.data.model.AppSingleton;
import com.secondhome.login.LoginActivity;
import com.secondhome.login.MyProfileActivity;
import com.secondhome.mains.Main2LoggedInActivity;
import com.secondhome.mains.MainActivity;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class MyAnimalsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private LinearLayout catsview;
    private Drawable paw;
    private String uid;
    private String pid;
    private static final String UrlForLogin="https://secondhome.fragmentedpixel.com/server/getanimals.php/";
    private String animalType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_animals);
      animalType= AppSingleton.getInstance(getApplicationContext()).getAnimalsToShow();
      paw= getApplicationContext().getResources().getDrawable(R.drawable.ic_pets_white_24dp);
       setNavigationViewListener();
        mDrawer=findViewById(R.id.myanimals);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);
        navigationView = findViewById(R.id.mymenu);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        catsview= findViewById(R.id.myLinearCats);
        if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
        getCats();
        else
            sendToLogin();

    }

    private void sendToLogin() {
        Intent intent=new Intent(MyAnimalsActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    private void getCats(){
        System.out.println("Trying request animals");
        StringRequest strReq= new StringRequest(
                Request.Method.POST, UrlForLogin, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response)
            {
                Log.d("AnimalSource", "Register Response: "+ response);
                try{

                    System.out.println("Trying to request Object");
                    JSONObject obj=new JSONObject(response);
                    System.out.println(obj.toString());
                    int animalNo=Integer.parseInt(obj.getString("nr_animals"));
                    System.out.println(animalNo);
                    if(animalNo == 0 ) {
                        TextView noAnimals=new TextView(MyAnimalsActivity.this);
                        noAnimals.setText("Niciun animal inregistrat.");
                        noAnimals.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                        catsview.addView(noAnimals);
                        return;}
                    final JSONArray animals=obj.getJSONArray("animals");
//
                    for(int i=0;i<animalNo;i++)
                    {
                        Moshi moshi = new Moshi.Builder().build();
                        JsonAdapter<Animal> adapter = moshi.adapter(Animal.class);
                        final Animal a =adapter.fromJson(animals.get(i).toString());
//                        if(i==0){
                        System.out.println(animals.get(i).toString());
                        ImageView img=new ImageView(MyAnimalsActivity.this);
                       // Picasso.get().load("https://i.imgur.com/XAuRrVz.jpg").into(img);

                        String img64 = a.getImage();
                        System.out.println(img64);
                        if(img64 == null)
                            Picasso.get().load("https://i.imgur.com/q52cLwE.png").into(img);
                        else{
                        String [] parts= img64.split(",");
                        //img64.replace("\\", "");

                        byte[] decodedString = Base64.getDecoder().decode(parts[1]);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        img.setImageBitmap(decodedByte);}
                        img.setPadding(0,40,0,20);
                        img.setMinimumWidth(600);
                        img.setMinimumHeight(600);

                        TextView name=new TextView(MyAnimalsActivity.this);
                        TextView description=new TextView(MyAnimalsActivity.this);
                        Drawable buttonBackground=getResources().getDrawable(R.drawable.btn_shape_round_green);

                        Button viewDetails=new Button(MyAnimalsActivity.this);
                        viewDetails.setBackground(buttonBackground);
                        viewDetails.setTextColor(getResources().getColor(R.color.white));
                        viewDetails.setLayoutParams(new LinearLayout.LayoutParams(500,160));
                        viewDetails.setText("Mai multe detalii");
                        viewDetails.setCompoundDrawables(paw,null,paw,null);

                        final int j=i;
                        View.OnClickListener listenerViewAnimal=new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    AppSingleton.getInstance(getApplicationContext()).setAnimalPid(animals.getJSONObject(j).getString("PID"));
                                    AppSingleton.getInstance(getApplicationContext()).setCurrentAnimal(a);
                                    AppSingleton.getInstance(getApplicationContext()).setUser(AppSingleton.getInstance(getApplicationContext()).getUser());
                                    AppSingleton.getInstance(getApplicationContext()).setAdoptionState(a.getHas_request());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Ready to see animal");
                                Intent intent=new Intent(MyAnimalsActivity.this, MyAnimalActivity.class);
                                startActivity(intent);
                            }
                        };
                        viewDetails.setOnClickListener(listenerViewAnimal);

                        Integer hasRequest, requestType,requestState;


                        hasRequest=a.getHas_request();
                        Button adoptionState=new Button(MyAnimalsActivity.this);
                        //viewDetails.setBackground(buttonBackground);
                        adoptionState.setTextColor(getResources().getColor(R.color.white));
                        adoptionState.setLayoutParams(new LinearLayout.LayoutParams(500,160));
                        if(hasRequest==1)
                        {
                            requestState=Integer.parseInt(a.getRequest_state());
                            requestType=Integer.parseInt(a.getRequest_type());
                            if(requestType==0)
                            {if(requestState==1)
                                     adoptionState.setText("Cererea adopție acceptată");
                                if(requestState==0)
                                    adoptionState.setText("Cererea adopție în așteptare");
                                if(requestState==-1)
                                    adoptionState.setText("Cererea adopție respinsă");
                            }
                            if(requestType==1)
                            {if(requestState==1)
                                adoptionState.setText("Cererea cazare acceptată");
                                if(requestState==0)
                                    adoptionState.setText("Cererea cazare în așteptare");
                                if(requestState==-1)
                                    adoptionState.setText("Cererea cazare respinsă");
                            }

                        }
                        else {
                            adoptionState.setText("Dă spre adopție");
                            adoptionState.setOnClickListener(listenerViewAnimal);
                        }

                        adoptionState.setCompoundDrawables(paw,null,paw,null);


                        name.setText(animals.getJSONObject(i).getString("name"));
                        name.setTextSize(25);
                        name.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

                        description.setText("Vârstă:"+animals.getJSONObject(i).getString("birthdate"));
                        description.setTextSize(20);
                        description.setGravity(View.TEXT_ALIGNMENT_GRAVITY);


                        catsview.addView(img);
                        catsview.addView(name);
                        catsview.addView(description);
                        catsview.addView(viewDetails);
                        catsview.addView(adoptionState);

                    }


                } catch(JSONException e)
                {
                    System.out.println("error here");
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("CatsActivity", "Login error: "+ error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
//                Map<String,String> params=new HashMap<>();
//                System.out.println(AppSingleton.getInstance(getApplicationContext()).getUser().toString());
//                params.put("security_code", "8981ASDGHJ22123");
//                params.put("request_type","1");
//                params.put("UID", AppSingleton.getInstance(getApplicationContext()).getUser().getUID());
//                System.out.println(params);
//                return params;
                return new GetAnimalsCustomRequest(AppSingleton
                        .getInstance(getApplicationContext()).getUser().getUID()).map();
            }

        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"getCats");
    }
//    private View.OnClickListener animalRequestListener= new View.OnClickListener(){
//        @Override
//        public void onClick(View v)  {
//            StringRequest strReq = new StringRequest(
//                    Request.Method.POST, "https://secondhome.fragmentedpixel.com/server/animalrequest.php/", new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.d("LoginDataSource", "Register Response: " + response);
//                }
//
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("LoginActivity", "Login error: " + error.getMessage());
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() {
//
//                }
//            };
//        }
//    };
    private void setNavigationViewListener() {
        System.out.println("setting navigation listener");
        navigationView = findViewById(R.id.mymenu);
        System.out.println(navigationView.toString());
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        System.out.println("On navigation selected item");
        System.out.println(AppSingleton.getInstance(getApplicationContext()).getAnimalsToShow());
        switch (item.getItemId()) {

            case R.id.db0:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("0");
                Intent intent=new Intent(MyAnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db1:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("1");
                intent=new Intent(MyAnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db2:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("2");
                intent=new Intent(MyAnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db3:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("3");
                intent=new Intent(MyAnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db4:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("4");
                intent=new Intent(MyAnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db5:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("5");
                intent=new Intent(MyAnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db6:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("6");
                intent=new Intent(MyAnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db:
                if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
                {
                    intent=new Intent(MyAnimalsActivity.this, Main2LoggedInActivity.class);
                    intent.putExtra("username", AppSingleton.getInstance(getApplicationContext()).getLoggedInUserName());
                }
                else intent=new Intent(MyAnimalsActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.db8:
                intent=new Intent(MyAnimalsActivity.this, ContactActivity.class);
                startActivity(intent);
            case R.id.db9:
                if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
                    intent=new Intent(MyAnimalsActivity.this, MyProfileActivity.class);
                else intent=new Intent(MyAnimalsActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.db10:
                intent=new Intent(MyAnimalsActivity.this, ListOfLocations.class);
                startActivity(intent);
                break;
            case R.id.db11:
                intent=new Intent(MyAnimalsActivity.this, MyAnimalsActivity.class);
                startActivity(intent);
                break;

        }
        //close navigation drawer
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
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
