package com.secondhome.activities.showanimals.request.listener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.secondhome.activities.mains.Main2LoggedInActivity;
import com.secondhome.data.model.request.listeners.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PostAnimalListener extends RequestListener implements Response.Listener<String> {

    private AppCompatActivity postAnimalForm;

    public PostAnimalListener(Context applicationContext,
                              Context packageContext,
                              AppCompatActivity postAnimalForm) {
        super(applicationContext, packageContext);
        this.postAnimalForm = postAnimalForm;
    }

    @Override
    public void onResponse(String response) {
        Log.d("EditAnimalForm", "Register Response: " + response);
        try {
            JSONObject obj = new JSONObject(response);
            if(obj.getString("status").equals("1")){
                Toast.makeText(getApplicationContext(),
                        "Animalul dumneavoastră a fost editat cu succes!",
                        Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getPackageContext(), Main2LoggedInActivity.class);
                postAnimalForm.startActivity(intent);
                postAnimalForm.finish();
            }
        } catch (JSONException e) {
            System.out.println("error here");
            e.printStackTrace();
        }

    }

}
