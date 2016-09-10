package com.example.shasha.electrokart.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shasha.electrokart.Database.DBHelper;
import com.example.shasha.electrokart.Model.CategoryDetails;
import com.example.shasha.electrokart.Model.CategoryModel;
import com.example.shasha.electrokart.Model.SpendModel;

import static com.example.shasha.electrokart.Utils.DBConstants.*;

/**
 * Created by shasha on 19-02-2016.
 */
public class CategoryController {

    DBHelper dbHelper;

    public CategoryController(Context context) {
        dbHelper = new DBHelper(context);
    }


    public boolean setTotalAmount() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_CATEGORY_TYPE, CategoryModel.getInstance().getCategoryType());
        values.put(COL_CATEGORY_IMAGE, CategoryModel.getInstance().getCategoryImage());
        values.put(COL_TOTAL_SPEND, CategoryModel.getInstance().getTotalSpendAmount());
        sqLiteDatabase.insert(TABLE_CATEGORY, null, values);
        sqLiteDatabase.close();

        return true;
    }

    public boolean getTotalSpendByCategory() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COL_TOTAL_SPEND + " FROM " + TABLE_CATEGORY + " WHERE " + COL_CATEGORY_TYPE + " = '" + CategoryModel.getInstance().getCategoryType() + "'", null);


        if (!cursor.isAfterLast()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                CategoryDetails categoryDetails = new CategoryDetails();
                categoryDetails.totalSpendAmount = cursor.getString(cursor.getColumnIndex(COL_TOTAL_SPEND));
                CategoryModel.getInstance().setTotalSpendAmount(categoryDetails.totalSpendAmount);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();
        return true;
    }

    public boolean getCategoryType() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COL_CATEGORY_ID + " FROM " + TABLE_CATEGORY + " WHERE " + COL_CATEGORY_TYPE + " = '" + SpendModel.getInstance().getCategory() + "'", null);


        if (!cursor.isAfterLast()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                CategoryDetails categoryDetails = new CategoryDetails();
                categoryDetails.colCategoryID = cursor.getString(cursor.getColumnIndex(COL_CATEGORY_ID));
                CategoryModel.getInstance().setColCategoryId(categoryDetails.colCategoryID);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return true;
    }

    public void updateSpendByCategory() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String whereClause = (COL_CATEGORY_TYPE + " = '" + SpendModel.getInstance().getCategory() + "'");


            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_TOTAL_SPEND, CategoryModel.getInstance().getTotalSpendAmount());
            sqLiteDatabase.update(TABLE_CATEGORY, values, whereClause, null);
            sqLiteDatabase.close();

            db.close();

    }
}

