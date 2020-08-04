package com.secondhome.data.model.listeners;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.secondhome.data.model.menu.ActivityFactory;
import com.secondhome.data.model.others.AppContext;

public class OnClickSwitchActivityListener implements View.OnClickListener {
    private int activityId;



    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    private AppCompatActivity appCompatActivity;
    public OnClickSwitchActivityListener(AppCompatActivity appCompatActivity, int activityId){
        this.activityId = activityId;
        this.appCompatActivity = appCompatActivity;
    }
    @Override
    public void onClick(View v) {
        Intent intent = ActivityFactory.getIntent(appCompatActivity,activityId);
        appCompatActivity.startActivity(intent);
//        appCompatActivity.finish();
    }
}
