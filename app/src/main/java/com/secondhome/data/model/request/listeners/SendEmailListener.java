package com.secondhome.data.model.request.listeners;



import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.secondhome.activities.contact.ContactActivity;
import com.secondhome.activities.contact.WriteUs;

import org.json.JSONException;
import org.json.JSONObject;


public class SendEmailListener implements Response.Listener<String> {
    private Context applicationContext;
    private Context packageContext;
    private WriteUs writeUs;

    public SendEmailListener(Context applicationContext, Context packageContext, WriteUs writeUs) {
        this.applicationContext = applicationContext;
        this.packageContext = packageContext;
        this.writeUs = writeUs;
    }

    @Override
    public void onResponse(String response) {
        Log.d("WriteUsSource", "Register Response: "+ response);
        try{
            JSONObject obj=new JSONObject(response);
            String status= obj.getString("status");
            if(status.equals("1"))
            {
                Toast.makeText(applicationContext,"Mesaj trimis cu succes!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(packageContext, ContactActivity.class);
                writeUs.startActivity(intent);
            }
            System.out.println(status);

        } catch(JSONException e)
        {
            System.out.println("Unable to parse object.");
            e.printStackTrace();
        }
    }
}
