package com.secondhome.activities.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import com.android.volley.toolbox.StringRequest;
import com.secondhome.R;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.data.model.request.body.WriteUsRequest;
import com.secondhome.data.model.request.listeners.SendEmailListener;

import java.util.Map;

public class WriteUs extends AppCompatActivity {
    private TextView from;
    private static final String UrlForSendEmail="https://secondhome.fragmentedpixel.com/server/sendemail.php/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_us);
        from= findViewById(R.id.emailToContact);

        if(AppSingleton.getInstance(getApplicationContext()).getUser()!=null)
        {
            from.setText(AppSingleton.getInstance(getApplicationContext()).getUser().getUserEmail());
        }

        findViewById(R.id.send).setOnClickListener(v -> {
            sendEmail( new WriteUsRequest(from.getText().toString(),
                    ((TextView) findViewById(R.id.subjectContact)).getText().toString(),
                    ((TextView) findViewById(R.id.messageWriteUs)).getText().toString()));
        }
        );

    }

    private void sendEmail(final WriteUsRequest writeUsRequest) {
        StringRequest strReq= new StringRequest(
                Request.Method.POST, UrlForSendEmail,
                new SendEmailListener(getApplicationContext(), WriteUs.this,this),
                error -> {
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
