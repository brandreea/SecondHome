package com.secondhome.activities.login.request.listener;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.secondhome.activities.login.ui.RegisterActivity;
import com.secondhome.activities.login.request.body.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterCheckEmailListener implements Response.Listener<String> {
private Context applicationContext;
private String password, secondPassword;
private RegisterRequest registerRequest;
private RegisterActivity registerActivity;

    public RegisterCheckEmailListener(Context applicationContext,
                                      String password,
                                      String secondPassword,
                                      RegisterRequest registerRequest,
                                      RegisterActivity registerActivity) {
        this.applicationContext = applicationContext;
        this.password = password;
        this.secondPassword = secondPassword;
        this.registerRequest = registerRequest;
        this.registerActivity = registerActivity;
    }

    @Override
    public void onResponse(String response) {
        Log.d("CheckingIfEmailInUse", "Register Response: " + response);
        try {
            JSONObject jObj = new JSONObject(response);
            String status=jObj.getString("status");
            char c=status.charAt(0);
            boolean isUsed = false;
            if(c=='1') {
                String used=jObj.getString("email-used");
                if(used.equals("1")) {
                    Toast.makeText(this.applicationContext, "Există deja un cont ce folosește acest email.", Toast.LENGTH_SHORT).show();
                    System.out.println("used");
                    isUsed=true;
                }
                else {
                    if(!isUsed){
                        if(password.equals(secondPassword)) {
                            this.registerActivity.registerUser(registerRequest);
                        }
                        else  Toast.makeText(this.applicationContext,  "Cele două parole nu coincid.", Toast.LENGTH_SHORT).show();}
                }

            }
            else System.out.println("Status is 0");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
