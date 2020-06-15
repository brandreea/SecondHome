package com.secondhome.data.model.request.body;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class GetAnimalsRequest implements Request {
    private static final String securityCode ="8981ASDGHJ22123" ;
    private static final String requestType = "0";
    private static final String UID="-1";
    private String petType;

    public GetAnimalsRequest(String petType) {
        this.petType = petType;
    }
    @Override
    public Map<String, String> map() {
        Map<String,String> params=new HashMap<>();
        params.put("security_code", securityCode);
        params.put("request_type", requestType);
        params.put("UID", UID);
        params.put("pet_type", this.petType);
        return params;
    }
}
