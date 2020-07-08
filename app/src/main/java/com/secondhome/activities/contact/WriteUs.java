package com.secondhome.activities.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.toolbox.StringRequest;
import com.secondhome.R;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.data.model.request.body.WriteUsRequest;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;

public class WriteUs extends AppCompatActivity {
    private TextView from;
    private static final String UrlForSendEmail="https://secondhome.fragmentedpixel.com/server/sendemail.php/";
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

        findViewById(R.id.send).setOnClickListener(v -> {
            System.out.println("onClick is fine");
            sendEmail( new WriteUsRequest(from.getText().toString(),
                    ((TextView) findViewById(R.id.subjectContact)).getText().toString(),
                    ((TextView) findViewById(R.id.messageWriteUs)).getText().toString()));
        }
        );

    }

    private void sendEmail(final WriteUsRequest writeUsRequest) {
        StringRequest strReq= new StringRequest(
                Request.Method.POST, UrlForSendEmail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d("WriteUsSource", "Register Response: "+ response);
                try{
                    JSONObject obj=new JSONObject(response);
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
                    System.out.println("Unable to parse object.");
                    e.printStackTrace();
                }
            }

        }, error -> {
            Log.e("WriteUsActivity", "Sending error: "+ error.getMessage());
            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
        })
        {
            @Override
            protected Map<String,String> getParams(){
               return writeUsRequest.map();
            }

        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,"sendEmail");
    }
}
