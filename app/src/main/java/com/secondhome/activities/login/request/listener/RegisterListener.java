package com.secondhome.activities.login.request.listener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.secondhome.activities.login.ui.LoginActivity;
import com.secondhome.activities.login.ui.RegisterActivity;
import com.secondhome.data.model.request.listeners.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterListener extends RequestListener implements Response.Listener<String> {



    public RegisterListener(Context applicationContext, Context packageContext, RegisterActivity registerActivity) {
        super(applicationContext,packageContext, registerActivity);

    }

    @Override
    public void onResponse(String response) {
        Log.d("RegisterInApp", "Register Response: " + response);
        try {
            JSONObject jObj = new JSONObject(response);
            String status=jObj.getString("status");
            char c=status.charAt(0);
            if(c=='1') {
                Toast.makeText(getApplicationContext(),  "Ați fost adăugat! Conectați-vă la cont. ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getPackageContext(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
            else System.out.println("Status is 0, registration failed.");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
