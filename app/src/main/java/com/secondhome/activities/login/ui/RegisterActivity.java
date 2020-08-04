package com.secondhome.activities.login.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import com.android.volley.Request;
import com.secondhome.R;
import com.secondhome.activities.login.RegisterDataLengthValidation;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.activities.login.request.body.RegisterRequest;
import com.secondhome.activities.login.request.listener.RegisterCheckEmailListener;
import com.secondhome.activities.login.request.listener.RegisterListener;

import java.util.HashMap;
import java.util.Map;
public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private static final String URL_FOR_REGISTRATION = "https://secondhome.fragmentedpixel.com/server/register.php/";
    private static final String URL_FOR_EMAIL = "https://secondhome.fragmentedpixel.com/server/checkemail.php/";
    private EditText signupInputFirstName,
            signupInputLastName,
            signupInputEmail,
            signupInputPassword,
            signupInputSecondPassword;
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
        btnSignUp.setOnClickListener(view -> submitForm());

    }

    private void submitForm() {
        final RegisterRequest registerRequest= new RegisterRequest(
                signupInputFirstName.getText().toString(),
                signupInputLastName.getText().toString(),
                signupInputEmail.getText().toString(),
                signupInputPassword.getText().toString());
        int validationAnswer = RegisterDataLengthValidation.areValid(registerRequest,
                getApplicationContext());
        if(validationAnswer!=1) return;
        StringRequest emailReq=new StringRequest(Request.Method.POST,
               URL_FOR_EMAIL,
                new RegisterCheckEmailListener(getApplicationContext(),
                        signupInputPassword.getText().toString(),
                        signupInputSecondPassword.getText().toString(),
                        registerRequest,
                        this), error -> {
                   Log.e(TAG, "Registration Error: " + error.getMessage());
                   Toast.makeText(getApplicationContext(),
                           error.getMessage(), Toast.LENGTH_LONG).show();
               }
        ){
           @Override
           protected Map<String, String> getParams() {
               Map<String, String> params = new HashMap<>();
               params.put("user-email",registerRequest.getEmail());
               return params;}
        };
        String cancel_req_tag = "emailVerification";
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(emailReq, cancel_req_tag);

    }

    public void registerUser(final RegisterRequest registerRequest) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION,
                new RegisterListener(getApplicationContext(),RegisterActivity.this,this),
         error -> {
            Log.e(TAG, "Registration Error: " + error.getMessage());
            Toast.makeText(getApplicationContext(),
                    error.getMessage(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
               return registerRequest.map();
           }
       };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, "register");
    }
}