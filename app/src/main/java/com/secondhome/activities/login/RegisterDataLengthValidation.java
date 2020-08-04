package com.secondhome.activities.login;

import android.content.Context;
import android.widget.Toast;

import com.secondhome.activities.login.request.body.RegisterRequest;

public class RegisterDataLengthValidation {

    public static int areValid(RegisterRequest registerRequest, Context applicationContext){
        if(registerRequest.getPassword().length()<5)
        {
            Toast.makeText(applicationContext,
                    "Parola trebuie sa contina minim 6 caractere",
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        if(registerRequest.getFirstName().length()<1 || registerRequest.getLastName().length()<1
                || registerRequest.getEmail().length()<1 ) {
            Toast.makeText(applicationContext,
                    "Va rugam sa completati toate campurile",
                    Toast.LENGTH_SHORT).show();
            return -2;
        }
        return 1;
    }
}
