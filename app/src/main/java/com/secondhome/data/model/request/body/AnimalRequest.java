package com.secondhome.data.model.request.body;

import java.util.HashMap;
import java.util.Map;

public class AnimalRequest implements Request {
    private static final String securityCode="8981ASDGHJ22123";
    private String pid;
    private String uid;
    private String requestType;
    public AnimalRequest(String pid, String uid, String requestType) {
        this.pid = pid;
        this.uid = uid;
        this.requestType = requestType;
    }

    public AnimalRequest(String pid, String uid) {
        this.pid = pid;
        this.uid = uid;
    }

    @Override
    public Map<String, String> map() {
        Map<String,String> params = new HashMap<>();
        params.put("security_code",securityCode);
        params.put("UID",this.uid);
        params.put("PID", this.pid);
        if(this.requestType!=null)
        params.put("request_type",this.requestType);
        return params;
    }
}
