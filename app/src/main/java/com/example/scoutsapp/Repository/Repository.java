package com.example.scoutsapp.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.scoutsapp.DB.SQLiteHelper;
import com.example.scoutsapp.Model.Organization;

public class Repository {
    private SQLiteHelper sqLiteHelper;

    public Repository(Context context) {
        this.sqLiteHelper = new SQLiteHelper(context);
    }

    public long addUser(UserModel user)
    {
        return sqLiteHelper.addItem(user);
    }

    public UserModel getUserByID(int id)
    {
        return sqLiteHelper.getItemByID(id);
    }

    public UserModel getUserByEmail(String email)
    {
        return sqLiteHelper.getItemByEmail(email);
    }

    public UserModel getUserByUser(UserModel userModel)
    {
        return sqLiteHelper.getItemByItem(userModel);
    }

    public long deleteUserByID(int id)
    {
        return sqLiteHelper.deleteItemByID(id);
    }

    public long updateUserByID(int id, UserModel user)
    {
        return sqLiteHelper.updateItemByID(id, user);
    }
}
