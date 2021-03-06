package com.secondhome.activities.showanimals.request.body;

import com.secondhome.data.model.request.body.Request;

import java.util.HashMap;
import java.util.Map;

public class GetAnimalRequest implements Request {
    private static final String securityCode="8981ASDGHJ22123";
    private String pid;
    private String uid;

    public GetAnimalRequest(String pid, String uid) {
        this.pid = pid;
        this.uid = uid;
    }

    @Override
    public Map<String,String> map(){
        Map<String,String> params=new HashMap<>();
        params.put("security_code", securityCode);
        params.put("PID", this.pid);
        params.put("UID", this.uid);
        return params;
    }
}
