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

    @Override
    public String toString() {
        return "Animal{" +
                "PID='" + PID + '\'' +
                ", name='" + name + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", state='" + state + '\'' +
                ", description='" + description + '\'' +
                ", food='" + food + '\'' +
                ", type='" + type + '\'' +
                ", breed='" + breed + '\'' +
                ", has_request=" + has_request +
                ", request_type='" + request_type + '\'' +
                ", request_state='" + request_state + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public String getType(){return this.type;}
    public String getBreed(){return this.breed;}
    public String getImage(){return this.image;}

    public Animal(String PID, String name, String birthdate, String state, String description, String food, String type, String breed, int has_request, String request_type, String request_state, String image) {
        this.PID = PID;
        this.name = name;
        this.birthdate = birthdate;
        this.state = state;
        this.description = description;
        this.food = food;
        this.type = type;
        this.breed = breed;
        this.has_request = has_request;
        this.request_type = request_type;
        this.request_state = request_state;
        this.image = image;
    }
}
