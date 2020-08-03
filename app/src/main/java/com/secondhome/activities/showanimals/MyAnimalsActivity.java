package com.secondhome.activities.showanimals;

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
import com.android.volley.toolbox.StringRequest;

import com.google.android.material.navigation.NavigationView;
import com.secondhome.R;
import com.secondhome.data.model.others.Animal;
import com.secondhome.data.model.request.body.GetAnimalsCustomRequest;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.activities.login.LoginActivity;
import com.secondhome.data.model.menu.ActivityFactory;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

public class MyAnimalsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private LinearLayout catsview;
    private Drawable paw;

    private static final String UrlForLogin="https://secondhome.fragmentedpixel.com/server/getanimals.php/";
    private String animalType;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void getCats(){
        System.out.println("Trying request animals");
        StringRequest strReq= new StringRequest(
                Request.Method.POST, UrlForLogin, response -> {
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
                        for(int i=0;i<animalNo;i++)
                        {
                            Moshi moshi = new Moshi.Builder().build();
                            JsonAdapter<Animal> adapter = moshi.adapter(Animal.class);
                            final Animal a =adapter.fromJson(animals.get(i).toString());

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

                                byte[] decodedString = new byte[0];
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    decodedString = Base64.getDecoder().decode(parts[1]);
                                }
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
                            viewDetails.setText(R.string.details);
                            viewDetails.setCompoundDrawables(paw,null,paw,null);

                            final int j=i;
                            View.OnClickListener listenerViewAnimal=new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        AppSingleton.getInstance(getApplicationContext()).setAnimalPid(animals.getJSONObject(j).getString("PID"));
                                        AppSingleton.setCurrentAnimal(a);
                                        AppSingleton.getInstance(getApplicationContext()).setUser(AppSingleton.getInstance(getApplicationContext()).getUser());
                                        AppSingleton.setAdoptionState(a.getHas_request());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("Ready to see animal");
                                    Intent intent=new Intent(MyAnimalsActivity.this, MyAnimalActivity.class);
                                    startActivity(intent);
                                }
                            };
                            viewDetails.setOnClickListener(listenerViewAnimal);

                            int hasRequest, requestType,requestState;


                            hasRequest=a.getHas_request();
                            Button adoptionState=new Button(MyAnimalsActivity.this);
                            adoptionState.setTextColor(getResources().getColor(R.color.white));
                            adoptionState.setLayoutParams(new LinearLayout.LayoutParams(500,160));
                            if(hasRequest==1)
                            {
                                requestState=Integer.parseInt(a.getRequest_state());
                                requestType=Integer.parseInt(a.getRequest_type());
                                if(requestType==0 || requestType == 1)
                                {if(requestState==1)
                                         adoptionState.setText(R.string.adoption_state_acc);
                                    if(requestState==0)
                                        adoptionState.setText(R.string.adoption_state_pend);
                                    if(requestState==-1)
                                        adoptionState.setText(R.string.adoption_state_rej);
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

                            description.setText(String.format(getResources().getString(R.string.animal_age),animals.getJSONObject(i).getString("birthdate")));
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
                }, error -> {
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
        System.out.println("setting navigation listener");
        navigationView = findViewById(R.id.mymenu);
        System.out.println(navigationView.toString());
        navigationView.setNavigationItemSelectedListener(this);
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