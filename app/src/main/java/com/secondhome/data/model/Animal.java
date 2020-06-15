package com.secondhome.data.model;

public class Animal {
    private String PID;
    private String name;
    private String birthdate;
    private String state;
    private String description;
    private String food;
    private String type;
    private String breed;
    private int has_request;
    private String request_type;
    private String request_state;
    private String image;

    public String getRequest_type(){
        return this.request_type;
    }
    public String getRequest_state(){
        return this.request_state;
    }
    public int getHas_request(){
        return this.has_request;
    }
    public String getPID(){return this.PID;}
    public String getName(){return this.name;}
    public String getBirthdate(){return this.birthdate;}
    public String getDescription(){return this.description;}
    public String getType(){return this.type;}
    public String getBreed(){return this.breed;}
    public String getImage(){return this.image;}

}
