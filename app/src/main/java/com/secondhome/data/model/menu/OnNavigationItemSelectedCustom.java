package com.secondhome.data.model.menu;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

public interface OnNavigationItemSelectedCustom extends NavigationView.OnNavigationItemSelectedListener {
    static AppCompatActivity APP = new AppCompatActivity();
    public default void setNavigationViewListener(NavigationView.OnNavigationItemSelectedListener a, int id) {
        System.out.println("setting navigation listener");
        NavigationView navigationView = (NavigationView) APP.findViewById(id);
        navigationView.setNavigationItemSelectedListener(a);
    }

}
