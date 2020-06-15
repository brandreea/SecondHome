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
import com.secondhome.locations.LocationActvity;
import com.secondhome.login.AppSingleton;
import com.secondhome.login.LoginActivity;
import com.secondhome.login.MyProfileActivity;
import com.secondhome.mains.Main2LoggedInActivity;
import com.secondhome.mains.MainActivity;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AnimalsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private LinearLayout catsview;
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
        mDrawer= findViewById(R.id.showallCats);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);
        navigationView = findViewById(R.id.mymenuCats);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        catsview= findViewById(R.id.linearCats);
        getCats();

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
                    final JSONArray animals=obj.getJSONArray("animals");

                    for(int i=0;i<animalNo;i++)
                    {
//                        if(i==0){
                        System.out.println(animals.get(i).toString());
                        ImageView img=new ImageView(AnimalsActivity.this);

                        //TODO  this is ok for temporary
//                        Picasso.get().load("https://i.imgur.com/XAuRrVz.jpg").into(img);
//                        img.setPadding(0,60,0,0);

                        String img64 = animals.getJSONObject(i).getString("image");
                        System.out.println(img64);
                        String [] parts= img64.split(",");
                        //img64.replace("\\", "");

                        byte[] decodedString = Base64.getDecoder().decode(parts[1]);
//                        System.out.println(decodedString);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        img.setImageBitmap(decodedByte);
                        img.setPadding(0,40,0,20);
                        img.setMinimumWidth(600);
                        img.setMinimumHeight(600);
                        TextView name=new TextView(AnimalsActivity.this);
                        TextView description=new TextView(AnimalsActivity.this);
                        Drawable buttonBackground=getResources().getDrawable(R.drawable.btn_shape_round_green);
                        Button viewDetails=new Button(AnimalsActivity.this);

                        viewDetails.setBackground(buttonBackground);

                        viewDetails.setTextColor(getResources().getColor(R.color.white));
                        viewDetails.setLayoutParams(new LinearLayout.LayoutParams(400,130));
                        viewDetails.setText("Mai multe detalii");
                        viewDetails.setCompoundDrawables(paw,null,paw,null);
                        final int j=i;
                        View.OnClickListener listenerViewAnimal=new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    AppSingleton.getInstance(getApplicationContext()).setAnimalPid(animals.getJSONObject(j).getString("PID"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Intent intent=new Intent(AnimalsActivity.this, AnimalProfileActivity.class);
                                startActivity(intent);
                            }
                        };
                        viewDetails.setOnClickListener(listenerViewAnimal);


                        name.setText(animals.getJSONObject(i).getString("name"));
                        name.setTextSize(25);
                        name.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                        //name.setPadding(0,60,0,0);

                        description.setText("Vârstă:"+animals.getJSONObject(i).getString("birthdate"));
                        description.setTextSize(20);
                        description.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

                        catsview.addView(img);
                        catsview.addView(name);
                        catsview.addView(description);
                        catsview.addView(viewDetails);

                    }


                } catch (JSONException e) {
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
                Map<String,String> params=new HashMap<>();
                params.put("security_code", "8981ASDGHJ22123");
                params.put("request_type","0");
                params.put("pet_type", animalType);
                return params;
            }

        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"getCats");
    }
    private void setNavigationViewListener() {
        System.out.println("setting navigation listener");
        navigationView = findViewById(R.id.mymenuCats);
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
                Intent intent=new Intent(AnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db1:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("1");
                intent=new Intent(AnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db2:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("2");
                intent=new Intent(AnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db3:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("3");
                intent=new Intent(AnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db4:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("4");
                intent=new Intent(AnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db5:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("5");
                intent=new Intent(AnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db6:
                AppSingleton.getInstance(getApplicationContext()).setAnimalsToShow("6");
                intent=new Intent(AnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
                break;
            case R.id.db:
                if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
                {
                    intent=new Intent(AnimalsActivity.this, Main2LoggedInActivity.class);
                    intent.putExtra("username", AppSingleton.getInstance(getApplicationContext()).getLoggedInUserName());
                }
                else intent=new Intent(AnimalsActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.db8:
                intent=new Intent(AnimalsActivity.this, ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.db9:
                if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
                    intent=new Intent(AnimalsActivity.this, MyProfileActivity.class);
                else intent=new Intent(AnimalsActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.db10:
                intent=new Intent(AnimalsActivity.this, LocationActvity.class);
                startActivity(intent);
                break;
            case R.id.db11:
                intent=new Intent(AnimalsActivity.this, MyAnimalsActivity.class);
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
