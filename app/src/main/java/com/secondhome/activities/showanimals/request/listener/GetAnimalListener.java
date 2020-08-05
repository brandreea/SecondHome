package com.secondhome.activities.showanimals.request.listener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.secondhome.R;
import com.secondhome.data.model.others.Animal;
import com.secondhome.data.model.others.PetTypes;
import com.secondhome.data.model.request.listeners.RequestListener;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

public class GetAnimalListener extends RequestListener implements Response.Listener<String>, GetAnimal{
    public GetAnimalListener(Context applicationContext, Context packageContext, AppCompatActivity activity) {
        super(applicationContext, packageContext, activity);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResponse(String response) {
        Log.d("AnimalProfile", "Register Response: "+ response.toString());
        try{
            System.out.println("Trying to request Object");
            JSONObject obj=new JSONObject(response);
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<Animal> adapter = moshi.adapter(Animal.class);
            final Animal a =adapter.fromJson(obj.toString());
            System.out.println(a.toString());
            setAnimalDetails(a);
        } catch(JSONException e)
        {
            System.out.println("error here");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setAnimalDetails(Animal a){
        setPic(a.getImage());
        setDetails(a);
        setButtons(a);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setPic(String image) {
        ImageView profilePic=getActivity().findViewById(R.id.profilePicAnimal);
        String img64 = image;
        System.out.println(img64);
        if(img64 == null)
            Picasso.get().load("https://i.imgur.com/q52cLwE.png").into(profilePic);
        else{
            String [] parts= img64.split(",");
            byte[] decodedString = Base64.getDecoder().decode(parts[1]);
            System.out.println(decodedString.toString());
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profilePic.setImageBitmap(decodedByte);
        }
        profilePic.setPadding(0,40,0,20);
        profilePic.setMinimumWidth(600);
        profilePic.setMinimumHeight(600);
    }

    @Override
    public void setDetails(Animal a) {
        int [] textView = new int[]{R.id.animalName,
                R.id.age2,
                R.id.cathegory2,
                R.id.pedegree2,
                R.id.description2};
        String [] text = new String[]{a.getName(), a.getBirthdate(),
                PetTypes.getInstance().getPetType(a.getType()), a.getBreed(), a.getDescription()};
        for(int i=0;i<textView.length;i++){
            TextView t = (TextView)getActivity().findViewById(textView[i]);
            t.setText(text[i]);
        }
    }

    @Override
    public void setButtons(Animal a) {
        getActivity().findViewById(R.id.editAnimal).setVisibility(View.GONE);
        getActivity().findViewById(R.id.deleteAnimal).setVisibility(View.GONE);
        Button adopt= getActivity().findViewById(R.id.adopt);
        adopt.setVisibility(View.GONE);
//        adopt.setText("Trimite o cerere de adoptie");
//        if(AppSingleton.getAdoptionState()==0) {
//            adopt.setText("Trimite o cerere de adopție");
//            adopt.setOnClickListener(new AdoptListener(getApplicationContext(), getPackageContext(),
//                    getActivity(),"2"));
//        }
//        else {
//            adopt.setText("Nu puteti trimite o cerere.");
//        }

    }

}
