package com.secondhome.data.model.request.body;

import java.util.HashMap;
import java.util.Map;

public class AnimalFormRequest implements Request{
    private static final String securityCode = "8981ASDGHJ22123";
    private String petName;
    private String petDescription;
    private String petType;
    private String petAge;
    private String petBreed;
    private String uid;

    public AnimalFormRequest(String petName,String petAge, String petType,String petBreed, String petDescription, String uid) {
        this.petName = petName;
        this.petDescription = petDescription;
        this.petType = petType;
        this.petAge = petAge;
        this.petBreed = petBreed;
        this.uid = uid;
    }

    @Override
    public Map<String,String> map(){
        Map<String,String> params=new HashMap<>();
        params.put("pet_name", this.petName);
        params.put("pet_description", this.petDescription);
        params.put("pet_type", this.petType);
        params.put("pet_age", this.petAge);
        params.put("pet_breed",this.petBreed);
        params.put("security_code", securityCode);
        params.put("UID",this.uid);
        return params;
    }
}
