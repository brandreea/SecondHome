package com.secondhome.activities.showanimals.request.listener;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.secondhome.data.model.others.Animal;

public interface GetAnimal {
    @RequiresApi(api = Build.VERSION_CODES.O)
    default void setAnimalDetails(Animal a){
        setPic(a.getImage());
        setDetails(a);
        setButtons(a);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    void setPic(String image);
    void setDetails(Animal a);
    void setButtons(Animal a);


}
