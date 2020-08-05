package com.secondhome.activities.locations.ui.listener;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.secondhome.data.model.listeners.OnClickSwitchActivityListener;
import com.secondhome.data.model.others.AppSingleton;

public class OnClickShowLocationListener extends OnClickSwitchActivityListener {
    private int location;

    public OnClickShowLocationListener(AppCompatActivity appCompatActivity, int activityId, int location){
        super(appCompatActivity,activityId);
        this.location = location;
    }
    @Override
    public void onClick(View v) {
        AppSingleton.getInstance(getAppCompatActivity().getApplicationContext()).setLocation(3);
        super.onClick(v);
    }
}
