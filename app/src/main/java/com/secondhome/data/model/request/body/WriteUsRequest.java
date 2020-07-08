package com.secondhome.data.model.request.body;

import java.util.HashMap;
import java.util.Map;

public class WriteUsRequest implements Request {
    private static final String securityCode = "8981ASDGHJ22123";
    private String sender;
    private String messageSubject;
    private String messageContent;

    public WriteUsRequest(String sender, String messageSubject, String messageContent) {
        this.sender = sender;
        this.messageSubject = messageSubject;
        this.messageContent = messageContent;
    }

    @Override
    public Map<String, String> map() {
        Map<String,String> params=new HashMap<>();
        params.put("security-code", this.securityCode);
        params.put("email", sender);
        params.put("subject", messageSubject);
        params.put("body", messageContent);
        return params;
    }
}
