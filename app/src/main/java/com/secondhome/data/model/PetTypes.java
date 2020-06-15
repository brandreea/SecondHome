package com.secondhome.data.model;

import java.util.HashMap;
import java.util.Map;

public class PetTypes {
    private static final Map<String,String> petTypes = new HashMap<>();
    private static PetTypes instance=null;
    private PetTypes() {
        petTypes.put("1","Pisica");
        petTypes.put("2","Caine");
        petTypes.put("3","Rozator");
        petTypes.put("4","Reptila");
        petTypes.put("5","Pasare");
        petTypes.put("6","Acvatic");

    }
    public static PetTypes getInstance(){
        if(instance ==null)
            instance = new PetTypes();
        return instance;

    }

    public String getPetType(String type) {
        if(petTypes.containsKey(type))
            return petTypes.get(type);
        return null;
    }
}
