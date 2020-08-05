package com.secondhome.activities.showanimals.request.listener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.secondhome.R;
import com.secondhome.activities.showanimals.ui.AnimalProfileActivity;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.data.model.request.listeners.RequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

public class AnimalsListener extends RequestListener implements Response.Listener<String> {
    private LinearLayout catsView;
    private Drawable paw;

    public AnimalsListener(Context applicationContext,
                           Context packageContext,
                           AppCompatActivity animalsActivity,
                           LinearLayout catsView,
                           Drawable paw) {
        super(applicationContext, packageContext, animalsActivity);
        this.catsView = catsView;
        this.paw = paw;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResponse(String response) {
        Log.d("AnimalSource", "Register Response: "+ response);
        try{

            System.out.println("Trying to request Object");
            JSONObject obj=new JSONObject(response);
            System.out.println(obj.toString());
            int animalNo=Integer.parseInt(obj.getString("nr_animals"));
            final JSONArray animals=obj.getJSONArray("animals");

            for(int i=0;i<animalNo;i++)
            {
                System.out.println(animals.get(i).toString());
                addAnimalToView((animals.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    void addAnimalToView(JSONObject animalResponse) throws JSONException {
        setPicture(animalResponse.getString("image"));
        setName(animalResponse.getString("name"));
        setAge(animalResponse.getString("birthdate"));
       setButton(animalResponse.getString("PID"));
    }
    protected void setButton(final String pid){
        Drawable buttonBackground=getActivity().getResources().getDrawable(R.drawable.btn_shape_round_green);
        Button buttonView=new Button(getActivity());
        buttonView.setBackground(buttonBackground);
        buttonView.setTextColor(getActivity().getResources().getColor(R.color.white));
        buttonView.setLayoutParams(new LinearLayout.LayoutParams(400,130));
        buttonView.setText("Mai multe detalii");
        buttonView.setCompoundDrawables(paw,null,paw,null);
        View.OnClickListener listenerViewAnimal= v -> {
            AppSingleton.getInstance(getApplicationContext())
                    .setAnimalPid(pid);
//            AppSingleton.setAdoptionState(adoptionState);
            Intent intent=new Intent(getActivity(), AnimalProfileActivity.class);
            getActivity().startActivity(intent);
        };
        buttonView.setOnClickListener(listenerViewAnimal);
        catsView.addView(buttonView);
    }

    protected void setAge(String age) {
        TextView ageView=new TextView(getActivity());
        ageView.setText("Vârstă:"+age);
        ageView.setTextSize(20);
        ageView.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
        catsView.addView(ageView);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected void setName(String name) {
        TextView nameView = new TextView(getActivity());
        nameView.setText(name);
        nameView.setTextSize(25);
        nameView.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
        catsView.addView(nameView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void setPicture(String img64) {
        ImageView img = new ImageView(getActivity());
        System.out.println(img64);
        if(img64 == null)
            Picasso.get().load("https://i.imgur.com/q52cLwE.png").into(img);
        else{
            String [] parts= img64.split(",");
            byte[] decodedString = Base64.getDecoder().decode(parts[1]);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            img.setImageBitmap(decodedByte);
        }
        img.setPadding(0,40,0,20);
        img.setMinimumWidth(600);
        img.setMinimumHeight(600);
        catsView.addView(img);
    }


    public LinearLayout getCatsView() {
        return catsView;
    }

    public Drawable getPaw() {
        return paw;
    }
}
