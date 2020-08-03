package com.secondhome.data.model.request.errors;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.secondhome.activities.contact.WriteUs;

public class SendEmailError implements Response.ErrorListener {
    private WriteUs writeUs;

    public SendEmailError(WriteUs writeUs) {
        this.writeUs = writeUs;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("WriteUsActivity", "Sending error: "+ error.getMessage());
        Toast.makeText(writeUs.getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
    }
}
