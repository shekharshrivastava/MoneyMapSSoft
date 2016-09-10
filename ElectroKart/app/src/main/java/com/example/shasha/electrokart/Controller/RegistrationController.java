package com.example.shasha.electrokart.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.shasha.electrokart.Database.DBHelper;
import com.example.shasha.electrokart.Model.RegistrationModel;

import static com.example.shasha.electrokart.Utils.DBConstants.*;

/**
 * Created by shasha on 16-01-2016.
 */
public class RegistrationController {

    DBHelper dbHelper;

    public RegistrationController(Context context) {
        dbHelper = new DBHelper(context);
    }


    public boolean userRegistration() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_FNAME, RegistrationModel.getInstance().getUserName());
        values.put(USER_NUMBER, RegistrationModel.getInstance().getUserNumber());
        values.put(USER_PASSWORD, RegistrationModel.getInstance().getPassword());
        values.put(LOGIN_DATE, RegistrationModel.getInstance().getLoginDate());
        values.put(USER_EMAILID, RegistrationModel.getInstance().getEmailId());

        sqLiteDatabase.insert(TABLE_REGISTRATION, null, values);
        sqLiteDatabase.close();

        return true;
    }


}
