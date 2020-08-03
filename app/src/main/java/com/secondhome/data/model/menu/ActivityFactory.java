package com.secondhome.data.model.menu;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.secondhome.R;
import com.secondhome.activities.contact.AboutUsActivity;
import com.secondhome.activities.contact.AchievmentsActivity;
import com.secondhome.activities.contact.ContactActivity;
import com.secondhome.activities.contact.FacilitiesActivity;
import com.secondhome.activities.contact.WriteUs;
import com.secondhome.activities.locations.LocationActivity;
import com.secondhome.activities.login.RegisterActivity;
import com.secondhome.activities.showanimals.AddAnimalFormActivity;
import com.secondhome.data.model.others.AppContext;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.activities.locations.ListOfLocations;
import com.secondhome.activities.login.LoginActivity;
import com.secondhome.activities.login.MyProfileActivity;
import com.secondhome.activities.mains.Main2LoggedInActivity;
import com.secondhome.activities.mains.MainActivity;
import com.secondhome.activities.showanimals.AnimalsActivity;
import com.secondhome.activities.showanimals.MyAnimalsActivity;

public class ActivityFactory {

    private static AppCompatActivity packageContext;
    public static synchronized Intent getIntent(AppCompatActivity context, int menuId) {
        Intent intent;
        packageContext = context;
        switch (menuId) {

            case R.id.db0:
                AppSingleton.getInstance(AppContext.getAppContext()).setAnimalsToShow("0");
                intent = new Intent(packageContext, AnimalsActivity.class);
                break;
            case R.id.db1:
                AppSingleton.getInstance(AppContext.getAppContext()).setAnimalsToShow("1");
                intent = new Intent(packageContext, AnimalsActivity.class);
                break;
            case R.id.db2:
                AppSingleton.getInstance(AppContext.getAppContext()).setAnimalsToShow("2");
                intent = new Intent(packageContext, AnimalsActivity.class);
                break;
            case R.id.db3:
                AppSingleton.getInstance(AppContext.getAppContext()).setAnimalsToShow("3");
                intent = new Intent(packageContext, AnimalsActivity.class);
                break;
            case R.id.db4:
                AppSingleton.getInstance(AppContext.getAppContext()).setAnimalsToShow("4");
                intent = new Intent(packageContext, AnimalsActivity.class);
                break;
            case R.id.db5:
                AppSingleton.getInstance(AppContext.getAppContext()).setAnimalsToShow("5");
                intent = new Intent(packageContext, AnimalsActivity.class);
                break;
            case R.id.db6:
                AppSingleton.getInstance(AppContext.getAppContext()).setAnimalsToShow("6");
                intent = new Intent(packageContext, AnimalsActivity.class);
                break;
            case R.id.db:
                System.out.println(AppSingleton.getInstance(AppContext.getAppContext()).getUser());
                if (AppSingleton.getInstance(AppContext.getAppContext()).getUser() != null) {
                    intent = new Intent(packageContext, Main2LoggedInActivity.class);
                    intent.putExtra("username", AppSingleton.getInstance(AppContext.getAppContext()).getLoggedInUserName());
                } else
                    intent = new Intent(packageContext, MainActivity.class);
                break;
            case R.id.db8:
                intent = new Intent(packageContext, ContactActivity.class);
                break;
            case R.id.db9:
                if (AppSingleton.getInstance(AppContext.getAppContext()).getUser() != null)
                    intent = new Intent(packageContext, MyProfileActivity.class);
                else intent = new Intent(packageContext, LoginActivity.class);
                break;
            case R.id.db10:
                intent = new Intent(packageContext, ListOfLocations.class);
                break;
            case R.id.db11:
                intent = new Intent(packageContext, MyAnimalsActivity.class);
                break;
            case R.id.aboutUs:
                intent = new Intent(packageContext, AboutUsActivity.class);
                break;
            case R.id.achievments:
                intent = new Intent(packageContext, AchievmentsActivity.class);
                break;
            case R.id.facilities:
                intent = new Intent(packageContext, FacilitiesActivity.class);
                break;
            case R.id.contactUs:
                intent = new Intent(packageContext, WriteUs.class);
                break;
            case R.id.registerButton:
                intent = new Intent(packageContext, RegisterActivity.class);
                break;
            case R.id.loginButton:
                intent = new Intent(packageContext, LoginActivity.class);
                break;
            case R.id.animalForm:
                intent = new Intent(packageContext, AddAnimalFormActivity.class);
                break;
            case R.id.showLocations:
                intent = new Intent(packageContext, LocationActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + menuId);
        }
        return intent;
    }
}
