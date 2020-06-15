package com.secondhome.data.model.request.body;

import java.util.HashMap;
import java.util.Map;

public class GetAnimalsCustomRequest implements Request{


        private static final String securityCode ="8981ASDGHJ22123" ;
        private static final String requestType = "1";
        private String UID="-1";

        public GetAnimalsCustomRequest(String UID) {
            this.UID = UID;
        }
        @Override
        public Map<String, String> map() {
            Map<String,String> params=new HashMap<>();
            params.put("security_code", securityCode);
            params.put("request_type", requestType);
            params.put("UID", UID);
            return params;
        }

}
