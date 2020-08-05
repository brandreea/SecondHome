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
import com.secondhome.activities.showanimals.ui.MyAnimalActivity;
import com.secondhome.data.model.others.Animal;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.data.model.request.listeners.RequestListener;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

public class MyAnimalsListener extends RequestListener implements Response.Listener<String> {
    private LinearLayout catsView;
    private Drawable paw;
    public MyAnimalsListener(Context applicationContext,
                             Context packageContext,
                             AppCompatActivity activity,
                             LinearLayout catsView,
                                     Drawable paw) {
        super(applicationContext, packageContext, activity);
        this.paw =paw;
        this.catsView = catsView;
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
            System.out.println(animalNo);
            if(animalNo == 0 ) {
                TextView noAnimals=new TextView(getPackageContext());
                noAnimals.setText("Niciun animal inregistrat.");
                noAnimals.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                catsView.addView(noAnimals);
                return;}
            final JSONArray animals=obj.getJSONArray("animals");
            for(int i=0;i<animalNo;i++)
            {
                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<Animal> adapter = moshi.adapter(Animal.class);
                final Animal a =adapter.fromJson(animals.get(i).toString());
                System.out.println(animals.get(i).toString());
                addAnimalToView(a);
            }

        } catch(JSONException e)
        {
            System.out.println("error here");
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

@RequiresApi(api = Build.VERSION_CODES.O)
void addAnimalToView(Animal animalResponse) throws JSONException {
        setPicture(animalResponse.getImage());
        setName(animalResponse.getName());
        setAge(animalResponse.getBirthdate());
        setButton(animalResponse);
        setState(animalResponse);
    }
    private void setState(Animal a){
        int hasRequest, requestType,requestState;
        hasRequest=a.getHas_request();
        Button adoptionState=new Button(getPackageContext());
        adoptionState.setTextColor(getActivity().getResources().getColor(R.color.white));
        adoptionState.setLayoutParams(new LinearLayout.LayoutParams(500,160));
        if(hasRequest==1)
        {
            requestState=Integer.parseInt(a.getRequest_state());
            requestType=Integer.parseInt(a.getRequest_type());
            if(requestType==0 || requestType == 1) {
                if(requestState==1)
                adoptionState.setText(R.string.adoption_state_acc);
                if(requestState==0)
                    adoptionState.setText(R.string.adoption_state_pend);
                if(requestState==-1)
                    adoptionState.setText(R.string.adoption_state_rej);
            }else{
                if(requestState==1)
                    adoptionState.setText("Cerere cazare acceptată");
                if(requestState==0)
                    adoptionState.setText("Cerere cazare în așteptare");
                if(requestState==-1)
                    adoptionState.setText("Cerere de cazare respinsă");
            }

        }
        else {
            adoptionState.setText("Nici o cerere înregistrată");
        }
        adoptionState.setEnabled(false);
        adoptionState.setCompoundDrawables(paw,null,paw,null);
        catsView.addView(adoptionState);
    }

    private void setButton(Animal a){
        Drawable buttonBackground=getActivity().getResources().getDrawable(R.drawable.btn_shape_round_green);
        Button buttonView=new Button(getActivity());
        buttonView.setBackground(buttonBackground);
        buttonView.setTextColor(getActivity().getResources().getColor(R.color.white));
        buttonView.setLayoutParams(new LinearLayout.LayoutParams(500,160));
        buttonView.setText(R.string.details);
        buttonView.setCompoundDrawables(paw,null,paw,null);

        View.OnClickListener listenerViewAnimal= v -> {
            AppSingleton.getInstance(getApplicationContext()).setAnimalPid(a.getPID());
            AppSingleton.setCurrentAnimal(a);
            AppSingleton.getInstance(getApplicationContext())
                    .setUser(AppSingleton.getInstance(getApplicationContext())
                            .getUser());
            AppSingleton.setAdoptionState(a.getHas_request());
            System.out.println("Ready to see animal");
            Intent intent=new Intent(getPackageContext(), MyAnimalActivity.class);
            getActivity().startActivity(intent);
        };
        buttonView.setOnClickListener(listenerViewAnimal);
        catsView.addView(buttonView);
    }

    private void setAge(String age) {
        TextView ageView=new TextView(getActivity());
        ageView.setText("Vârstă:"+age);
        ageView.setTextSize(20);
        ageView.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
        catsView.addView(ageView);

    }


    private void setName(String name) {
        TextView nameView = new TextView(getActivity());
        nameView.setText(name);
        nameView.setTextSize(25);
        nameView.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
        catsView.addView(nameView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setPicture(String img64) {
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

}
