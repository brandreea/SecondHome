package com.secondhome.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.secondhome.R;
import com.secondhome.data.model.AppSingleton;
import com.secondhome.data.model.request.RegisterRequest;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private static final String URL_FOR_REGISTRATION = "https://secondhome.fragmentedpixel.com/server/register.php/";
    private static final String urlForEmail = "https://secondhome.fragmentedpixel.com/server/checkemail.php/";
    private boolean isUsed=false;
    private EditText signupInputFirstName, signupInputLastName, signupInputEmail, signupInputPassword, signupInputSecondPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signupInputFirstName = findViewById(R.id.firstName);
       signupInputLastName=  findViewById(R.id.lastName);

       signupInputEmail =  findViewById(R.id.emailRegister);
       signupInputPassword = findViewById(R.id.passwordRegister);
       signupInputSecondPassword= findViewById((R.id.passwordConfirmationRegister));
       btnSignUp =  findViewById(R.id.buttonRegisterPage);
       btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
       });
//
    }
//
    private void submitForm() {
        final RegisterRequest registerRequest= new RegisterRequest(
                signupInputFirstName.getText().toString(),
                signupInputLastName.getText().toString(),
                signupInputEmail.getText().toString(),
                signupInputPassword.getText().toString());
        int validationAnswer = areValid(registerRequest);
        if(validationAnswer==-1)
       {Toast.makeText(getApplicationContext(),"Parola trebuie sa contina minim 6 caractere",Toast.LENGTH_SHORT).show();
           return;}
        if(validationAnswer==-2)
        {Toast.makeText(getApplicationContext(),"Parola trebuie sa contina minim 6 caractere",Toast.LENGTH_SHORT).show();
            return;}
       isUsed=false;
       StringRequest emailReq=new StringRequest(Request.Method.POST,
               urlForEmail,
               new Response.Listener<String>() {
           @Override
                   public void onResponse(String response){
               Log.d(TAG, "Register Response: " + response);
//
               try {
                   JSONObject jObj = new JSONObject(response);
                   String status=jObj.getString("status");
                   char c=status.charAt(0);
                   if(c=='1') {

                       // String user = jObj.getString("user-firstname");
                       String used=jObj.getString("email-used");
                       if(used.equals("1")) {
                           Toast.makeText(getApplicationContext(), "Există deja un cont ce folosește acest email.", Toast.LENGTH_SHORT).show();
                            System.out.println("used");
                            isUsed=true;
                       }
                       else {
                           if(!isUsed){
                    if(signupInputPassword.getText().toString().equals(signupInputSecondPassword.getText().toString())) {
                            registerUser(registerRequest);
                            }
                       else  Toast.makeText(getApplicationContext(),  "Cele două parole nu coincid.", Toast.LENGTH_SHORT).show();}
                       }

                   }
                   else System.out.println("Status is 0");
               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Log.e(TAG, "Registration Error: " + error.getMessage());
               Toast.makeText(getApplicationContext(),
                       error.getMessage(), Toast.LENGTH_LONG).show();
           }
       }
       ){
           @Override
           protected Map<String, String> getParams() {// Posting params to register url
               Map<String, String> params = new HashMap<>();
               params.put("user-email", signupInputEmail.getText().toString());
               return params;}
       };
       String cancel_req_tag = "emailVerification";
       AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(emailReq, cancel_req_tag);

    }
    protected int areValid(RegisterRequest registerRequest){
        if(registerRequest.getPassword().length()<5)
        {
            return -1;
        }
        if(registerRequest.getFirstName().length()<1 || registerRequest.getLastName().length()<1 || registerRequest.getEmail().length()<1 )
            return -2;
        return 1;
    }
    private void registerUser(final RegisterRequest registerRequest) {
       String cancel_req_tag = "register";
       System.out.println("here");
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {
//
           @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
//
                try {
                   JSONObject jObj = new JSONObject(response);
                    String status=jObj.getString("status");
                    char c=status.charAt(0);
                    if(c=='1') {
                        Toast.makeText(getApplicationContext(),  "Ați fost adăugat! Conectați-vă la cont. ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else System.out.println("Status is 0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {// Posting params to register url
               return registerRequest.map();
           }
       };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }
}