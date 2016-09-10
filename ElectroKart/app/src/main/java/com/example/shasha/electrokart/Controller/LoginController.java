package com.example.shasha.electrokart.Controller;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shasha.electrokart.Database.DBHelper;
import com.example.shasha.electrokart.Model.LoginDetails;
import com.example.shasha.electrokart.Model.LoginModel;
import com.example.shasha.electrokart.Model.RegistrationModel;

import static com.example.shasha.electrokart.Utils.DBConstants.*;

/**
 * Created by shasha on 17-01-2016.
 */
public class LoginController {
    DBHelper dbHelper;
    private String emailId = "";

    public LoginController(Context context) {
        dbHelper = new DBHelper(context);
    }


    public void getUserDetails() {


        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + USER_PASSWORD + " FROM " + TABLE_REGISTRATION + " WHERE " + USER_EMAILID + " = '" + LoginModel.getInstance().getEmailId() + "'", null);


        if (!cursor.isAfterLast()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                LoginDetails details = new LoginDetails();
                details.password = cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
                LoginModel.getInstance().setPassword(details.password);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

    }

    public String getAuthentication() {


        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + USER_EMAILID + " FROM " + TABLE_REGISTRATION + " WHERE " + USER_JABBERID + " = '" + 1 + "'", null);


        if (!cursor.isAfterLast()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                emailId = cursor.getString(cursor.getColumnIndex(USER_EMAILID));
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();
        return emailId;
    }

    public  boolean getAllDataFromUser (){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT *" + " FROM " + TABLE_REGISTRATION , null);

        if (!cursor.isAfterLast()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                LoginModel.getInstance().setUserName(cursor.getString(cursor.getColumnIndex(USER_FNAME)));
                LoginModel.getInstance().setEmailId(cursor.getString(cursor.getColumnIndex(USER_EMAILID)));
                LoginModel.getInstance().setUserNumber(cursor.getString(cursor.getColumnIndex(USER_NUMBER)));
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();
        return true;

    }

}
