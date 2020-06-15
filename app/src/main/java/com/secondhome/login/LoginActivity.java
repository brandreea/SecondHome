package com.secondhome.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.secondhome.data.model.LoggedInUser;
import com.secondhome.mains.Main2LoggedInActivity;
import com.secondhome.R;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String UrlForLogin="https://secondhome.fragmentedpixel.com/server/login.php/";
    private EditText loginInputEmail,loginInputPassword;
    private Button login;
    private LoggedInUser user;

      @Override
      public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginInputEmail= findViewById(R.id.email);
        loginInputPassword=findViewById(R.id.password);
        login= findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         System.out.println("onClick is fine");
                                         loginUser(loginInputEmail.getText().toString(), loginInputPassword.getText().toString());
                                     }
                                 }

        );
      }
      protected void loginUser(final String email, final String password)
      {
          System.out.println("Trying to log in");
          StringRequest strReq= new StringRequest(
                  Request.Method.POST, UrlForLogin, new Response.Listener<String>() {
              @Override
              public void onResponse(String response)
              {
                  Log.d("LoginDataSource", "Register Response: "+ response);
                  try{
                      System.out.println("Trying to request Object");
                      JSONObject obj=new JSONObject(response);
                      System.out.println(obj.toString());

                          System.out.println("here");
                          String userName= obj.getString("user-firstname");
                          String UID=obj.getString("UID");
                          System.out.println(obj);
                          System.out.println("user");

                          user=new LoggedInUser(UID,email,userName);
                          AppSingleton.getInstance(getApplicationContext()).setUser(user);
                          Intent intent=new Intent(LoginActivity.this, Main2LoggedInActivity.class);
                          System.out.println("We want to add user");
                          intent.putExtra("username", userName);
                          startActivity(intent);
//                      }
//                      else{
//                          String errorMsg=obj.getString("error_msg");
//                          Toast.makeText(getApplicationContext(),errorMsg,Toast.LENGTH_LONG).show();
//                      }

                  } catch(JSONException e)
                  {
                      System.out.println("error here");
                      e.printStackTrace();
                  }
              }

          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                Log.e("LoginActivity", "Login error: "+ error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
              }
          })
          {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("user-email", email);
                params.put("user-password", password);
                return params;
            }

          };

          AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"login");


      }
}
