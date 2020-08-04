package com.secondhome.data.model.request.listeners;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;


public class RequestListener {
    private Context applicationContext;
    private Context packageContext;
    private AppCompatActivity activity;

    public RequestListener(Context applicationContext, Context packageContext) {
        this.applicationContext = applicationContext;
        this.packageContext = packageContext;
    }

    public RequestListener(Context applicationContext,
                           Context packageContext,
                           AppCompatActivity activity) {
        this.applicationContext = applicationContext;
        this.packageContext = packageContext;
        this.activity = activity;
    }
    public AppCompatActivity getActivity() {
        return activity;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }




    public Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Context getPackageContext() {
        return packageContext;
    }

    public void setPackageContext(Context packageContext) {
        this.packageContext = packageContext;
    }
}
