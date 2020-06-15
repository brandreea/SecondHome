package com.secondhome.data.model.request;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    public RegisterRequest(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public Map<String,String> map(){
        Map<String, String> params = new HashMap<>();
        params.put("user-email", this.email);
        params.put("user-password", this.password);
        params.put("user-firstname", this.firstName);
        params.put("user-lastname", this.lastName);
        return params;
    }
}
