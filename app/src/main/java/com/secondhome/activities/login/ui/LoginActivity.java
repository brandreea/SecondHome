package com.secondhome.activities.login.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.activities.login.LoggedInUser;
import com.secondhome.activities.login.request.body.LoginRequestBody;
import com.secondhome.R;

import com.android.volley.Request;
import com.secondhome.activities.login.request.listener.LoginListener;

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
        login.setOnClickListener(v -> {
            loginUser(new LoginRequestBody(loginInputEmail.getText().toString()
                    , loginInputPassword.getText().toString()));
        }

        );
      }
      protected void loginUser(final LoginRequestBody loginRequest)
      {
          System.out.println("Trying to log in");
          StringRequest strReq= new StringRequest(
                  Request.Method.POST, UrlForLogin,
                 new LoginListener(getApplicationContext(),
                                    LoginActivity.this,
                                    this,
                                    loginRequest.getUserEmail())
                  , error -> {
                    Log.e("LoginActivity", "Login error: "+ error.getMessage());
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                  })
          {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("user-email", loginRequest.getUserEmail());
                params.put("user-password", loginRequest.getPassword());
                return params;
            }

          };

          AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"login");

      }
}
