package com.secondhome.activities.login.request.listener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;
import com.secondhome.activities.login.ui.LoginActivity;
import com.secondhome.activities.mains.Main2LoggedInActivity;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.activities.login.LoggedInUser;
import com.secondhome.data.model.request.listeners.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginListener extends RequestListener implements Response.Listener<String> {
    private String email;
    public LoginListener(Context applicationContext,
                         Context packageContext,
                         LoginActivity loginActivity,
                         String email) {
        super(applicationContext, packageContext,loginActivity);
        this.email=email;
    }


    @Override
    public void onResponse(String response) {
        Log.d("LoginDataSource", "Register Response: "+ response);
        try{
            JSONObject obj=new JSONObject(response);
            String userName= obj.getString("user-firstname");
            String UID=obj.getString("UID");
            LoggedInUser user=new LoggedInUser(UID, email,userName);
            AppSingleton.getInstance(getApplicationContext()).setUser(user);
            Intent intent=new Intent(getPackageContext(), Main2LoggedInActivity.class);
            intent.putExtra("username", userName);
            getActivity().startActivity(intent);
            getActivity().finish();
        } catch(JSONException e)
        {
            System.out.println("error here");
            e.printStackTrace();
        }
    }
}
