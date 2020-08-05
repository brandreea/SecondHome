package com.secondhome.activities.showanimals.ui.listeners;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.secondhome.activities.login.ui.LoginActivity;
import com.secondhome.activities.showanimals.request.body.AnimalRequest;
import com.secondhome.activities.showanimals.ui.AnimalsActivity;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.data.model.request.listeners.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class AdoptListener extends RequestListener implements View.OnClickListener {
    private static final  String URL_FOR_ADOPTION="https://secondhome.fragmentedpixel.com/server/animalrequest.php/";
    private String requestType;
    public AdoptListener(Context applicationContext, Context packageContext,
                         AppCompatActivity activity, String requestType) {
        super(applicationContext, packageContext, activity);
        this.requestType = requestType;
    }

    @Override
    public void onClick(View view) {
        if(AppSingleton.getInstance(getApplicationContext()).getUser()==null){
            Intent intent = new Intent(getPackageContext(), LoginActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
            return;
        }

        StringRequest strReq= new StringRequest(
                Request.Method.POST,
                URL_FOR_ADOPTION,
                response -> {
                    Log.d("AnimalProfile", "Delete Response: "+ response);
                    try {
                        JSONObject jsonObject= new JSONObject(response);
                        if(jsonObject.getString("status").equals("1"))
                        Toast.makeText(getApplicationContext(),
                                "Cererea a fost inregistrata.",
                                Toast.LENGTH_LONG).show();
                        else if(jsonObject.getString("status").equals("0")) Toast.makeText(getApplicationContext(),
                                "Există deja o cerere inregistrata",Toast.LENGTH_LONG).show();
                        else Toast.makeText(getApplicationContext(),
                                    "A aparut o eroare, ne cerem scuze pentru inconveniente.",
                                    Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getPackageContext(), AnimalsActivity.class);
                    getActivity().startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    Log.e("AnimalProfileActivity", " error: "+ error.getMessage());
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG);
                }){
            @Override
            protected Map<String,String> getParams(){
                return new AnimalRequest(
                        AppSingleton.getAnimalPid(),
                        AppSingleton.getInstance(getApplicationContext()).getUser()==null?
                                "-1":AppSingleton.getInstance(getApplicationContext()).getUser().getUID()
                        ,
                        requestType).map();
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"deleteAnimal");
    }
}
