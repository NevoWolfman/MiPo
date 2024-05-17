package net.nevowolfman.mipo.Repository;

import android.content.Context;

import net.nevowolfman.mipo.DB.SQLiteHelper;

public class Repository {
    //TODO: add a remote database such as MySQL or FireBase
    private SQLiteHelper sqLiteHelper;

    public Repository(Context context) {
        this.sqLiteHelper = new SQLiteHelper(context);
    }

    public long addUser(UserModel user)
    {
        return sqLiteHelper.addUser(user);
    }

    public UserModel getUserByEmail(String email)
    {
        return sqLiteHelper.getUserByEmail(email);
    }

    public long deleteUserByEmail(String email)
    {
        return sqLiteHelper.deleteUserByEmail(email);
    }

    public long updateUserByEmail(String email, UserModel user) {
        return sqLiteHelper.updateUserByEmail(email, user);
    }
}
