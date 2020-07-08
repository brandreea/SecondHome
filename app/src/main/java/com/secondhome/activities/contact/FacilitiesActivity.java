package com.secondhome.activities.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.secondhome.R;

public class FacilitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ((TextView)findViewById(R.id.contactTitle)).setText(R.string.facilities);
        ((TextView)findViewById(R.id.contactContent1)).setText(R.string.facilitiesContent);
    }
}
