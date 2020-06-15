package com.secondhome.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.secondhome.R;
import com.secondhome.login.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WriteUs extends AppCompatActivity {
    private TextView from, subject, message;
    private Button send;
    private static final String UrlForSendEmail="http://secondhome.fragmentedpixel.com/server/sendemail.php/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_us);

        from= findViewById(R.id.emailToContact);
        String email="";
        if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
        {
            from.setText(email);
        }

        subject=findViewById(R.id.subjectContact);
        message= findViewById(R.id.messageWriteUs);
        send= findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         System.out.println("onClick is fine");
                                         sendEmail(from.getText().toString(), subject.getText().toString(), message.getText().toString());
                                     }
                                 }

        );


    }

    private void sendEmail(final String sender, final String messageSubject, final String messageContent) {
        StringRequest strReq= new StringRequest(
                Request.Method.POST, UrlForSendEmail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d("WriteUsSource", "Register Response: "+ response);
                try{
                    System.out.println("Trying to request Object");
                    JSONObject obj=new JSONObject(response);
                    System.out.println(obj.toString());
                    System.out.println("here");
                    String status= obj.getString("status");
                    if(status.equals("1"))
                    {
                        Toast.makeText(getApplicationContext(),"Mesaj trimis cu succes!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(WriteUs.this, ContactActivity.class);
                        startActivity(intent);
                    }
                    System.out.println(status);

                } catch(JSONException e)
                {
                    System.out.println("error here");
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("WriteUsActivity", "Sending error: "+ error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("security-code", "8981ASDGHJ22123");
                params.put("email", sender);
                params.put("subject", messageSubject);
                params.put("body", messageContent);
                return params;
            }

        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"sendEmail");


    }
}
