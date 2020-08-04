package com.secondhome.activities.contact.request.listener;



import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.secondhome.activities.contact.ui.ContactActivity;
import com.secondhome.activities.contact.ui.WriteUs;
import com.secondhome.data.model.request.listeners.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;


public class SendEmailListener extends RequestListener implements Response.Listener<String> {

    public SendEmailListener(Context applicationContext, Context packageContext, WriteUs writeUs) {
        super(applicationContext, packageContext,writeUs);
    }

    @Override
    public void onResponse(String response) {
        Log.d("WriteUsSource", "Register Response: "+ response);
        try{
            JSONObject obj=new JSONObject(response);
            String status= obj.getString("status");
            if(status.equals("1"))
            {
                Toast.makeText(getApplicationContext(),"Mesaj trimis cu succes!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getPackageContext(), ContactActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
            System.out.println(status);

        } catch(JSONException e)
        {
            System.out.println("Unable to parse object.");
            e.printStackTrace();
        }
    }
}
