package com.secondhome.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.secondhome.R;

public class AchievmentsActivity extends AppCompatActivity {
    TextView title;
    TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        title= findViewById(R.id.contactTitle);
        content=findViewById(R.id.contactContent1);
        title.setText(R.string.achievements);
        content.setText(R.string.achievementsContent);
    }
}
