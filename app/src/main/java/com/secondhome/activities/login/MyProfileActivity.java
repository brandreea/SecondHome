package com.secondhome.activities.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
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
import com.secondhome.R;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.data.model.others.User;
import com.secondhome.data.model.menu.ActivityFactory;
import com.secondhome.activities.showanimals.ui.MyAnimalsActivity;
import com.google.android.material.navigation.NavigationView;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ImageView profilePic;
    private TextView name, animalsNo,email;
    private Button viewMyAnimals;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private ActionMenuItem item;
    private static final String UrlForUser="https://secondhome.fragmentedpixel.com/server/getuser.php/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        loadViews();
        setNavigationViewListener();

        // TODO CHANGE ID
        mDrawer=(DrawerLayout) findViewById(R.id.userProfile);
        mToggle= new ActionBarDrawerToggle(this, mDrawer,R.string.open,R.string.close);

        // TODO CHANGE ID
        navigationView = (NavigationView) findViewById(R.id.mymenu);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadUserData();

    }

    private void loadUserData() {
        System.out.println("Trying request userDetails");
        StringRequest strReq= new StringRequest(
                Request.Method.POST, UrlForUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d("UserProfile", "Register Response: "+ response.toString());
                try{

                    System.out.println("Trying to request Object");
                    JSONObject obj=new JSONObject(response);
                    System.out.println(obj.toString());
                    Moshi moshi = new Moshi.Builder().build();
                    JsonAdapter<User> adapter = moshi.adapter(User.class);
                    User a =adapter.fromJson(obj.toString());
                    System.out.println(a.toString());
                    System.out.println(animalsNo.toString());
                    System.out.println(name.toString());
 //                   System.out.println(a.toString());
                    Picasso.get().load("https://i.imgur.com/q52cLwE.png").into(profilePic);
                    profilePic.setPadding(0,40,0,20);
                    profilePic.setMinimumWidth(600);
                    profilePic.setMinimumHeight(600);
                    email.setText(a.getUser_email());
                    name.setText(a.getFirst_name()+" "+a.getLast_name());
                    animalsNo.setText(Integer.toString(a.getNr_owned_pets()));

                    View.OnClickListener listenerViewAnimal=new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(MyProfileActivity.this, MyAnimalsActivity.class);
                            startActivity(intent);
                        }
                    };
                   viewMyAnimals.setOnClickListener(listenerViewAnimal);

                } catch(JSONException e)
                {
                    System.out.println("error here");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("error here");
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("UserProfileActivity", " error: "+ error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG);
            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String,String>();
                params.put("security_code", "8981ASDGHJ22123");
                params.put("UID", AppSingleton.getInstance(getApplicationContext()).getUser().getUID());
                return params;
            }

        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"getUser");

    }

    private void loadViews() {
        profilePic=(ImageView) findViewById(R.id.profilePicUser);
        name=(TextView) findViewById(R.id.userName);
        email=(TextView) findViewById(R.id.email2);
        animalsNo=(TextView) findViewById(R.id.animalNo2);
        viewMyAnimals= (Button) findViewById(R.id.viewMyAnimals);
//        edit=(Button) findViewById(R.id.editProfile);

    }

    private void setNavigationViewListener() {
        System.out.println("setting navigation listener");
        // TODO CHANGE ID
        NavigationView navigationView = (NavigationView) findViewById(R.id.mymenu);
        System.out.println(navigationView.toString());
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        System.out.println("On navigation selected item");
        System.out.println(AppSingleton.getInstance(getApplicationContext()).getAnimalsToShow());
        Intent intent = ActivityFactory.getIntent(MyProfileActivity.this,item.getItemId());
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
