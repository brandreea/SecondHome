package com.secondhome.activities.showanimals.ui;

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
import com.android.volley.toolbox.StringRequest;
import com.secondhome.R;
import com.secondhome.activities.showanimals.request.body.AnimalFormRequest;
import com.secondhome.activities.mains.Main2LoggedInActivity;
import com.secondhome.activities.showanimals.request.listener.PostAnimalListener;
import com.secondhome.data.model.others.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class AddAnimalFormActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    private TextView name;
    private Spinner spinner;
    private TextView age;
    private TextView breed;
    private TextView description;
    private Button submit;
    private String category;
    private static final String UrlForAddAnimal="https://secondhome.fragmentedpixel.com/server/addanimal.php/";

    @Override
    protected void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal_form);
        setView();

    }

    private void setView() {
        setSpinner();
        setName();
        setAge();
        setBreed();
        setDescription();
        setSubmit();
    }

    private void setSubmit() {
        submit=findViewById(R.id.addAnimalSubmit);
        View.OnClickListener listenerAddAnimal= v -> addAnimal(new AnimalFormRequest(name.getText().toString(),
                age.getText().toString(),
                category,
                breed.getText().toString(),
                description.getText().toString(),
                AppSingleton.getInstance(getApplicationContext()).getUser().getUID()));
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

    private void addAnimal(final AnimalFormRequest animalFormRequest) {
        System.out.println("Trying to add animal...");
        StringRequest strReq=new StringRequest(
                Request.Method.POST,
                UrlForAddAnimal,
               new PostAnimalListener(getApplicationContext(),
                       AddAnimalFormActivity.this,this)
        , error -> {
            Log.e("LoginActivity", "Login error: " + error.getMessage());
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        }){
            @Override
            protected Map<String,String> getParams(){
               return animalFormRequest.map();
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"addanimal");
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            category =Integer.toString(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
