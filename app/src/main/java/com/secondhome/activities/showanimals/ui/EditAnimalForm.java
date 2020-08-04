package com.secondhome.activities.showanimals.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.secondhome.R;
import com.secondhome.activities.showanimals.request.listener.PostAnimalListener;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.activities.showanimals.request.body.EditAnimalFormRequest;

import java.util.Map;

public class EditAnimalForm extends AppCompatActivity {

    private TextView name;
    private TextView age;
    private TextView breed;
    private TextView description;
    private Button submit;
    private static final String UrlForAddAnimal="https://secondhome.fragmentedpixel.com/server/updateanimal.php/";

    @Override
    protected void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_animal_form);
        setView();

    }
    private void setView(){
        setName();
        setAge();
        setBreed();
        setDescription();
        setSubmit();
    }
    private void setSubmit() {
        submit=findViewById(R.id.editAnimalSubmit);
        View.OnClickListener listenerAddAnimal= v -> editAnimal( new EditAnimalFormRequest(name.getText().toString(),
                description.getText().toString(),
                age.getText().toString(),
                breed.getText().toString(),
                AppSingleton.getInstance(getApplicationContext()).getUser().getUID(),
                AppSingleton.getCurrentAnimal().getPID()));
        submit.setOnClickListener(listenerAddAnimal);
    }

    private void setDescription() {
        description=findViewById(R.id.editAnimalDescription);
        description.setText(AppSingleton.getCurrentAnimal().getDescription());
    }

    private void setBreed() {
        breed= findViewById(R.id.editAnimalBreed);
        breed.setText(AppSingleton.getCurrentAnimal().getBreed());
    }

    private void setAge() {
        age= findViewById(R.id.editAnimalAge);
        age.setText(AppSingleton.getCurrentAnimal().getBirthdate());
    }

    private void setName() {
        name= findViewById(R.id.editAnimalName);
        name.setText(AppSingleton.getCurrentAnimal().getName());
    }


    private void editAnimal(final EditAnimalFormRequest editAnimalFormRequest) {
        System.out.println("Trying to add animal...");
        StringRequest strReq=new StringRequest(
                Request.Method.POST,
                UrlForAddAnimal,
                new PostAnimalListener(getApplicationContext(),
                        EditAnimalForm.this,
                        this)
                , error -> {
                    Log.e("LoginActivity", "Login error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }){
            @Override
            protected Map<String,String> getParams(){
                return editAnimalFormRequest.map();
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"addanimal");
    }

}
