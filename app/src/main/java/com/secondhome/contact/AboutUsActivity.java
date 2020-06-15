package com.secondhome.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.secondhome.R;

public class AboutUsActivity extends AppCompatActivity {
    TextView title;
    TextView content1,content2,content3,content4;
    TextView quote1,quote2,quote3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        title= findViewById(R.id.contactTitle);
        content1=findViewById(R.id.contactContent1);
        title.setText(R.string.aboutus);
        content1.setText(R.string.intro);

        content2=findViewById(R.id.contactContent2);
        content2.setText(R.string.aboutUsSecond);

        content3=findViewById(R.id.contactContent3);
        content3.setText(R.string.aboutUsThird);


        quote1= findViewById(R.id.contactQuote1);
        quote1.setText(R.string.quoteBianca);

        quote2= findViewById(R.id.contactQuote2);
        quote2.setText( R.string.quoteVlad);

        quote3=findViewById(R.id.contactQuote3);
        quote3.setText(R.string.quoteAndreea);


        content4=findViewById(R.id.contactContent4);
        content4.setText(R.string.aboutUsEnding);
    }
}
