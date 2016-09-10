package com.example.shasha.electrokart.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shasha.electrokart.Database.DBHelper;
import com.example.shasha.electrokart.Model.CategoryDetails;
import com.example.shasha.electrokart.Model.CategoryModel;
import com.example.shasha.electrokart.Model.LoginDetails;
import com.example.shasha.electrokart.Model.LoginModel;
import com.example.shasha.electrokart.Model.SpendModel;
import com.example.shasha.electrokart.Ui.Spend;

import static com.example.shasha.electrokart.Utils.DBConstants.*;

/**
 * Created by shasha on 11-02-2016.
 */
public class SpendController {

    DBHelper dbHelper;
    private String totalSpendAmount;

    public SpendController(Context context) {
        dbHelper = new DBHelper(context);
    }

public void setSpendDetails() {
    SQLiteDatabase db = dbHelper.getWritableDatabase();

    ContentValues values = new ContentValues();

    values.put(COL_AMOUNT, SpendModel.getInstance().getSpendAmount());
    values.put(COL_NOTE, SpendModel.getInstance().getNote());
    values.put(COL_PAID_TO, SpendModel.getInstance().getPaidTo());
    values.put(COL_CATEGORY, SpendModel.getInstance().getCategory());
    values.put(COL_DATE, SpendModel.getInstance().getDate());
    values.put(COL_MONTH, SpendModel.getInstance().getMonth());
    values.put(COL_TRANSACTION_TYPE, SpendModel.getInstance().getTransactionType());
    db.insert(TABLE_SPEND, null, values);

    db.close();

}


}
