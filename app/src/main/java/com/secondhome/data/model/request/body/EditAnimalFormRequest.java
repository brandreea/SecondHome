package com.secondhome.data.model.request.body;

import com.secondhome.data.model.AppSingleton;

import java.util.HashMap;
import java.util.Map;

public class EditAnimalFormRequest implements Request{
    private static final String securityCode = "8981ASDGHJ22123";
    private String petName;
    private String petDescription;
    private String petAge;
    private String petBreed;
    private String uid;
    private String pid;

    public EditAnimalFormRequest(String petName, String petDescription,
                                 String petAge, String petBreed, String uid, String pid) {
        this.petName = petName;
        this.petDescription = petDescription;
        this.petAge = petAge;
        this.petBreed = petBreed;
        this.uid = uid;
        this.pid = pid;
    }

    @Override
    public Map<String, String> map() {
            Map<String,String> params=new HashMap<>();
            params.put("pet_name", this.petName);
            params.put("pet_description", this.petDescription);
            params.put("pet_age", this.petAge);
            params.put("pet_breed",this.petBreed);
            params.put("security_code", securityCode);
            params.put("UID",this.uid);
            params.put("PID", this.pid);
            return params;
    }
}
