package com.example.scoutsapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.scoutsapp.Repository.UserModel;

public class SQLiteHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_EMAIL = "_email";
    private static final String COLUMN_PASSWORD = "_password";


    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME
                + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PASSWORD + " TEXT )";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void deleteTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.close();
        onCreate(db);
    }

    public long addItem(UserModel user)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EMAIL, user.getEmail());
        cv.put(COLUMN_PASSWORD, user.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.insert(TABLE_NAME, null, cv);
        db.close();

        return res;
    }

    public UserModel getItemByID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id , null);

        UserModel user = null;
        if(cursor.moveToNext())
        {
            user = new UserModel(cursor.getString(1), cursor.getString(2));
        }
        cursor.close();
        db.close();
        return user;
    }

    public UserModel getItemByEmail(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = '" + email + "'", null);

        UserModel user = null;
        if(cursor.moveToNext())
        {
            user = new UserModel(cursor.getString(1), cursor.getString(2));
        }
        cursor.close();
        //db.close();
        return user;
    }

    public UserModel getItemByItem(UserModel user)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = new String[2];
        args[0] = COLUMN_EMAIL + " = '" + user.getEmail() + "'";
        args[1] = COLUMN_PASSWORD + " = '" + user.getPassword() + "'";
        String query = String.format("SELECT * FROM " + TABLE_NAME + " WHERE %s AND %s ", args[0], args[1]);
        Cursor cursor = db.rawQuery(query, null);

        UserModel new_user = null;
        if(cursor.moveToNext())
        {
            new_user = new UserModel(cursor.getString(1), cursor.getString(2));
        }
        cursor.close();
        db.close();
        return new_user;
    }

    public long deleteItemByID(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.delete(TABLE_NAME,COLUMN_ID + " = " + id, null);
        db.close();
        return res;
    }

    public long updateItemByID(int id, UserModel user)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EMAIL, user.getEmail());
        cv.put(COLUMN_PASSWORD, user.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.update(TABLE_NAME, cv, COLUMN_ID + " = " + id, null);
        db.close();

        return res;
    }
}
