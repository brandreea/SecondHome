package com.secondhome.activities.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.secondhome.R;

public class AboutUsActivity extends AppCompatActivity {
    private int [] views =new int[]{R.id.contactTitle,
            R.id.contactContent1,
            R.id.contactContent2,
            R.id.contactContent3,
            R.id.contactContent4,
            R.id.contactQuote1,
            R.id.contactQuote2,
            R.id.contactQuote3};
    private int [] strings =new int[]{R.string.aboutus,
            R.string.intro,
            R.string.aboutUsSecond,
            R.string.aboutUsThird,
            R.string.aboutUsEnding,
            R.string.quoteBianca,
            R.string.quoteVlad,
            R.string.quoteAndreea
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setViews();
    }
    private void setViews(){
        for(int i=0;i<views.length;i++)
            ((TextView)findViewById(views[i])).setText(strings[i]);
    }
}
