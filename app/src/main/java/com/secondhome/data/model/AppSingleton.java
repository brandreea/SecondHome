package com.secondhome.data.model;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppSingleton {
    private static AppSingleton mAppSingletonInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;
    private static LoggedInUser user=null;
    private static String animalsToShow=null;
    private static String animalPid=null;
    private static Animal currentAnimal=null;
    private static int adoptionState=0;
    private static int location=0;
    private AppSingleton(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static int getAdoptionState() {
        return adoptionState;
    }

    public static void setAdoptionState(int adoptionState) {
        AppSingleton.adoptionState = adoptionState;
    }

    public static synchronized AppSingleton getInstance(Context context) {
        if (mAppSingletonInstance == null) {
            mAppSingletonInstance = new AppSingleton(context);
        }
        return mAppSingletonInstance;
    }
    public void setLocation(int l){ location=l;}
    public int getLocation(){return location;}
    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }
    public LoggedInUser getUser(){
        return user;
    }
    public String getLoggedInUserName()
    {
        return user.getDisplayName();

    }
    public void setAnimalsToShow(String s){
        System.out.println("in animals");
        animalsToShow=s;}
    public String getAnimalsToShow(){if(animalsToShow!=null) return animalsToShow;
        return "0";}
    public void setUser(LoggedInUser u)
    {
        user=u;
    }
    public <T> void addToRequestQueue(Request<T> req,String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }
    public void logoutUser()
    {
        user=null;
    }

    public void setAnimalPid(String pid){
        animalPid=pid;
    }

    public static String getAnimalPid() {
        return animalPid;
    }

    public static Animal getCurrentAnimal() {
        return currentAnimal;
    }

    public static void setCurrentAnimal(Animal currentAnimal) {
        AppSingleton.currentAnimal = currentAnimal;
    }
}

