package com.secondhome.showanimals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.secondhome.R;
import com.secondhome.mains.Main2LoggedInActivity;
import com.secondhome.login.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddAnimalFormActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    private TextView name;
    private Spinner spinner;
    private TextView age;
    private TextView breed;
    private TextView description;
    private Button submit;
    private String cathegory;
    private static final String UrlForAddAnimal="https://secondhome.fragmentedpixel.com/server/addanimal.php/";

    @Override
    protected void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal_form);

        setSpinner();
        setName();
        setAge();
        setBreed();
        setDescription();
        setSubmit();
        //set the spinner

        //submit button

    }

    private void setSubmit() {
        submit=findViewById(R.id.addAnimalSubmit);
        View.OnClickListener listenerAddAnimal=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnimal(name.getText().toString(),
                        age.getText().toString(),
                        breed.getText().toString(),
                        description.getText().toString(),
                        cathegory,
                        AppSingleton.getInstance(getApplicationContext()).getUser().getUID());
            }
        };
        submit.setOnClickListener(listenerAddAnimal);
    }

    private void setDescription() {
        description= findViewById(R.id.addAnimalDescription);
    }

    private void setBreed() {
        breed= findViewById(R.id.addAnimalBreed);
    }

    private void setAge() {
        age= findViewById(R.id.addAnimalAge);
    }

    private void setName() {
        name= findViewById(R.id.addAnimalName);
    }

    private void setSpinner() {
        spinner = findViewById(R.id.animalCathegorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.animal_cathegory_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void addAnimal(final String name, final String age, final String breed, final String description, final String cathegory, final String uid) {
        System.out.println("Trying to add animal...");
        StringRequest strReq=new StringRequest(
                Request.Method.POST,
                UrlForAddAnimal,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("LoginDataSource", "Register Response: " + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("status").equals("1")){
                                //submit.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),  "Animalul dumneavoastră a fost adăugat cu succes!", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(AddAnimalFormActivity.this, Main2LoggedInActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            System.out.println("error here");
                            e.printStackTrace();
                        }

                    }
                }
        ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LoginActivity", "Login error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("pet_name", name);
                params.put("pet_description", description);
                params.put("pet_type", cathegory);
                params.put("pet_age", age);
                params.put("pet_breed",breed);
                params.put("security_code", "8981ASDGHJ22123");
                params.put("UID",uid);
                System.out.println(params.toString());
                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"addanimal");
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            cathegory=Integer.toString(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
