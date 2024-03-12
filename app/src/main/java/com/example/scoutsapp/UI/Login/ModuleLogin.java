package com.example.scoutsapp.UI.Login;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.scoutsapp.Repository.Repository;
import com.example.scoutsapp.Repository.UserModel;
import com.example.scoutsapp.UI.Main.MainActivity;
import com.example.scoutsapp.UI.SignUp.SignUpActivity;

public class ModuleLogin {
    private LoginActivity activity;
    private SharedPreferences prefs;

    public ModuleLogin(LoginActivity activity) {
        this.activity = activity;
        this.prefs = activity.getSharedPreferences(activity.SAVED_CREDENTIALS, Activity.MODE_PRIVATE);
    }

    public int userLogin(String email, String password)
    {
        Repository repository = new Repository(activity);
        if (repository.getUserByUser(new UserModel(email, password)) == null)
        {
            return -1;
        }
        return 0;
    }

    public void rememberMe(String email, String password)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.putString("pass", password);
        editor.apply();
    }

    public String checkCredentials()
    {
        return prefs.getString("email", null);
    }

    public void logIn(String email)
    {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("email", email);
        activity.startActivity(intent);
    }
}
