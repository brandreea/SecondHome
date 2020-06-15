package com.secondhome.data.model.request.body;

import java.util.HashMap;
import java.util.Map;

public class LoginRequestBody implements Request{
    private String userEmail;
    private String password;
    public LoginRequestBody(String userEmail, String password){
        this.userEmail = userEmail;
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Map<String, String> map() {
        Map<String,String> params=new HashMap<>();
        params.put("user-email", this.userEmail);
        params.put("user-password", this.password);
        return params;
    }
}
