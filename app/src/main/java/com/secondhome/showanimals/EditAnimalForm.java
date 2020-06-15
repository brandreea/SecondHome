package com.secondhome.showanimals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.secondhome.R;
import com.secondhome.data.model.AppSingleton;
import com.secondhome.data.model.request.body.EditAnimalFormRequest;
import com.secondhome.mains.Main2LoggedInActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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

        setName();
        setAge();
        setBreed();
        setDescription();
        setSubmit();

    }

    private void setSubmit() {
        submit=findViewById(R.id.editAnimalSubmit);
        View.OnClickListener listenerAddAnimal=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAnimal( new EditAnimalFormRequest(name.getText().toString(),
                        description.getText().toString(),
                        age.getText().toString(),
                        breed.getText().toString(),
                        AppSingleton.getInstance(getApplicationContext()).getUser().getUID(),
                        AppSingleton.getInstance(getApplicationContext()).getCurrentAnimal().getPID()));
            }
        };
        submit.setOnClickListener(listenerAddAnimal);
    }

    private void setDescription() {
        description=findViewById(R.id.editAnimalDescription);
        description.setText(AppSingleton.getInstance(getApplicationContext()).getCurrentAnimal().getDescription());
    }

    private void setBreed() {
        breed= findViewById(R.id.editAnimalBreed);
        breed.setText(AppSingleton.getInstance(getApplicationContext()).getCurrentAnimal().getBreed());
    }

    private void setAge() {
        age= findViewById(R.id.editAnimalAge);
        age.setText(AppSingleton.getInstance(getApplicationContext()).getCurrentAnimal().getBirthdate());
    }

    private void setName() {
        name= findViewById(R.id.editAnimalName);
        name.setText(AppSingleton.getInstance(getApplicationContext()).getCurrentAnimal().getName());
    }


    private void editAnimal(final EditAnimalFormRequest editAnimalFormRequest) {
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
                                Intent intent=new Intent(EditAnimalForm.this, Main2LoggedInActivity.class);
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
                return editAnimalFormRequest.map();
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"addanimal");
    }

}
