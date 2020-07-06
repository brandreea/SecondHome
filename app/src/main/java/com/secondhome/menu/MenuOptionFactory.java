package com.secondhome.menu;

import android.app.Application;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.secondhome.R;
import com.secondhome.contact.ContactActivity;
import com.secondhome.data.model.AppContext;
import com.secondhome.data.model.AppSingleton;
import com.secondhome.locations.ListOfLocations;
import com.secondhome.login.LoginActivity;
import com.secondhome.login.MyProfileActivity;
import com.secondhome.mains.Main2LoggedInActivity;
import com.secondhome.mains.MainActivity;
import com.secondhome.showanimals.AnimalsActivity;
import com.secondhome.showanimals.MyAnimalsActivity;

public class MenuOptionFactory {

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

            default:
                throw new IllegalStateException("Unexpected value: " + menuId);
        }
        return intent;
    }
}
