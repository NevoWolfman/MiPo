package com.example.scoutsapp.UI.Login;

import android.content.Context;
import android.content.Intent;

import com.example.scoutsapp.Repository.Repository;
import com.example.scoutsapp.Repository.UserModel;
import com.example.scoutsapp.UI.SignUp.SignUpActivity;

public class ModuleLogin {
    private Context context;

    public ModuleLogin(Context context) {
        this.context = context;
    }

    public int userLogin(String email, String password)
    {
        Repository repository = new Repository(context);
        if (repository.getUserByUser(new UserModel(email, password)) == null)
        {
            return -1;
        }



        return 0;
    }
}
