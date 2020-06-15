package com.secondhome.data.model.request.call;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.secondhome.data.model.AppSingleton;
import com.secondhome.data.model.LoggedInUser;
import com.secondhome.data.model.request.body.LoginRequestBody;
import com.secondhome.data.model.response.LoginResponse;
import com.secondhome.login.LoginActivity;
import com.secondhome.mains.Main2LoggedInActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginRequestCall {
    private static final String URL_FOR_LOGIN = "https://secondhome.fragmentedpixel.com/server/login.php/";

    protected LoginResponse loginUser(final LoginRequestBody loginRequest) {

        final LoginResponse[] loginResponse = {null};
        System.out.println("Trying to log in");
        StringRequest strReq = new StringRequest(
                Request.Method.POST, URL_FOR_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("LoginDataSource", "Register Response: " + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("correct-credentials").equals("1"))
                        loginResponse[0] = new LoginResponse(obj.getString("user-firstname"), obj.getString("UID"));
                } catch (JSONException e) {
                    Log.e("json", "error here");
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LoginActivity", "Login error: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return loginRequest.map();
            }
        };
        return loginResponse[0];

    }
}
