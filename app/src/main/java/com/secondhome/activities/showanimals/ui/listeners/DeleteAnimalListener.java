package com.secondhome.activities.showanimals.ui.listeners;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.secondhome.activities.showanimals.request.body.AnimalRequest;
import com.secondhome.activities.showanimals.ui.MyAnimalActivity;
import com.secondhome.activities.showanimals.ui.MyAnimalsActivity;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.data.model.request.listeners.RequestListener;

import java.util.Map;

public class DeleteAnimalListener extends RequestListener implements View.OnClickListener {
    private static final String URL_FOR_DELETING="https://secondhome.fragmentedpixel.com/server/deleteanimal.php/";
    public DeleteAnimalListener(Context applicationContext, Context packageContext, AppCompatActivity activity) {
        super(applicationContext, packageContext, activity);
    }

    @Override
    public void onClick(View view) {
        StringRequest strReq= new StringRequest(
                Request.Method.POST,
                URL_FOR_DELETING,
                response -> {
                    Log.d("AnimalProfile", "Delete Response: "+ response.toString());
                    Toast.makeText(getApplicationContext(),"Animaluțul a fost sters cu succes",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getPackageContext(), MyAnimalsActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                },
                error -> {
                    Log.e("AnimalProfileActivity", " error: "+ error.getMessage());
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG);
                }){
            @Override
            protected Map<String,String> getParams(){
                return new AnimalRequest(AppSingleton.getInstance(getApplicationContext()).getAnimalPid(),
                        AppSingleton.getInstance(getApplicationContext()).getUser().getUID().toString()).map();
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"deleteAnimal");

    }
}
