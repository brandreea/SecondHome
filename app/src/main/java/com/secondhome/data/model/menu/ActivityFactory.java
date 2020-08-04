package com.secondhome.data.model.menu;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.secondhome.R;
import com.secondhome.activities.contact.ui.AboutUsActivity;
import com.secondhome.activities.contact.ui.AchievmentsActivity;
import com.secondhome.activities.contact.ui.ContactActivity;
import com.secondhome.activities.contact.ui.FacilitiesActivity;
import com.secondhome.activities.contact.ui.WriteUs;
import com.secondhome.activities.locations.ui.LocationActivity;
import com.secondhome.activities.login.ui.RegisterActivity;
import com.secondhome.activities.showanimals.ui.AddAnimalFormActivity;
import com.secondhome.data.model.others.AppContext;
import com.secondhome.data.model.others.AppSingleton;
import com.secondhome.activities.locations.ui.ListOfLocations;
import com.secondhome.activities.login.ui.LoginActivity;
import com.secondhome.activities.login.MyProfileActivity;
import com.secondhome.activities.mains.Main2LoggedInActivity;
import com.secondhome.activities.mains.MainActivity;
import com.secondhome.activities.showanimals.ui.AnimalsActivity;
import com.secondhome.activities.showanimals.ui.MyAnimalsActivity;

public class ActivityFactory {

    public static synchronized Intent getIntent(AppCompatActivity context, int menuId) {
        Intent intent;
        switch (menuId) {

            case R.id.db0:
                AppSingleton.getInstance(context.getApplicationContext()).setAnimalsToShow("0");
                System.out.println(context.getApplicationContext());
                intent = new Intent(context, AnimalsActivity.class);
                break;
            case R.id.db1:
                AppSingleton.getInstance(context.getApplicationContext()).setAnimalsToShow("1");
                intent = new Intent(context, AnimalsActivity.class);
                break;
            case R.id.db2:
                AppSingleton.getInstance(context.getApplicationContext()).setAnimalsToShow("2");
                intent = new Intent(context, AnimalsActivity.class);
                break;
            case R.id.db3:
                AppSingleton.getInstance(context.getApplicationContext()).setAnimalsToShow("3");
                intent = new Intent(context, AnimalsActivity.class);
                break;
            case R.id.db4:
                AppSingleton.getInstance(context.getApplicationContext()).setAnimalsToShow("4");
                intent = new Intent(context, AnimalsActivity.class);
                break;
            case R.id.db5:
                AppSingleton.getInstance(context.getApplicationContext()).setAnimalsToShow("5");
                intent = new Intent(context, AnimalsActivity.class);
                break;
            case R.id.db6:
                AppSingleton.getInstance(context.getApplicationContext()).setAnimalsToShow("6");
                intent = new Intent(context, AnimalsActivity.class);
                break;
            case R.id.db:
                System.out.println(context.getApplicationContext());
                if (AppSingleton.getInstance(context.getApplicationContext()).getUser() != null) {
                    intent = new Intent(context, Main2LoggedInActivity.class);
                    intent.putExtra("username", AppSingleton.getInstance(context.getApplicationContext()).getLoggedInUserName());
                } else
                    intent = new Intent(context, MainActivity.class);
                break;
            case R.id.db8:
                intent = new Intent(context, ContactActivity.class);
                break;
            case R.id.db9:
                if (AppSingleton.getInstance(context.getApplicationContext()).getUser() != null)
                    intent = new Intent(context, MyProfileActivity.class);
                else intent = new Intent(context, LoginActivity.class);
                break;
            case R.id.db10:
                intent = new Intent(context, ListOfLocations.class);
                break;
            case R.id.db11:
                intent = new Intent(context, MyAnimalsActivity.class);
                break;
            case R.id.aboutUs:
                intent = new Intent(context, AboutUsActivity.class);
                break;
            case R.id.achievments:
                intent = new Intent(context, AchievmentsActivity.class);
                break;
            case R.id.facilities:
                intent = new Intent(context, FacilitiesActivity.class);
                break;
            case R.id.contactUs:
                intent = new Intent(context, WriteUs.class);
                break;
            case R.id.registerButton:
                intent = new Intent(context, RegisterActivity.class);
                break;
            case R.id.loginButton:
                intent = new Intent(context, LoginActivity.class);
                break;
            case R.id.animalForm:
                intent = new Intent(context, AddAnimalFormActivity.class);
                break;
            case R.id.showLocations:
                intent = new Intent(context, LocationActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + menuId);
        }
        return intent;
    }
}
