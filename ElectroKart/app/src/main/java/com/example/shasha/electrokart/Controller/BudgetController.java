package com.example.shasha.electrokart.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.shasha.electrokart.Database.DBHelper;
import com.example.shasha.electrokart.Model.BudgetModel;
import com.example.shasha.electrokart.Model.CategoryModel;

import static com.example.shasha.electrokart.Utils.DBConstants.*;


/**
 * Created by shasha on 26-03-2016.
 */
public class BudgetController {
    DBHelper dbHelper;

    public BudgetController(Context context) {
        dbHelper = new DBHelper(context);
    }


    public boolean setBudget() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_BUDGET, BudgetModel.getInstance().getBudgetAmount());
        values.put(COL_BUDGET_TYPE, BudgetModel.getInstance().getBudgetType());
        values.put(COL_BUDGET_DATE, BudgetModel.getInstance().getBudgetAddDate());
        values.put(COL_BUDGET_MONTH, BudgetModel.getInstance().getBudgetMonth());

        sqLiteDatabase.insert(TABLE_BUDGET, null, values);
        sqLiteDatabase.close();

        return true;
    }

}
