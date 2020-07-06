package com.secondhome.menu;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.secondhome.R;

public interface OnNavigationItemSelectedCustom extends NavigationView.OnNavigationItemSelectedListener {
    static AppCompatActivity APP = new AppCompatActivity();
    public default void setNavigationViewListener(NavigationView.OnNavigationItemSelectedListener a, int id) {
        System.out.println("setting navigation listener");
        NavigationView navigationView = (NavigationView) APP.findViewById(id);
        navigationView.setNavigationItemSelectedListener(a);
    }

}
