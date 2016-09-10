package com.example.shasha.electrokart.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shasha.electrokart.Database.DBHelper;
import com.example.shasha.electrokart.Model.CategoryModel;
import com.example.shasha.electrokart.Model.SpendModel;
import com.example.shasha.electrokart.Model.SpendSummaryModel;
import com.example.shasha.electrokart.Ui.SpendFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.shasha.electrokart.Utils.DBConstants.COL_AMOUNT;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_CATEGORY;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_MONTH;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_NOTE;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_PAID_TO;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_TOTAL_SPEND;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_TOTAL_SPEND_PER_MONTH;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_TRANSACTION_TYPE;
import static com.example.shasha.electrokart.Utils.DBConstants.TABLE_SPEND;
import static com.example.shasha.electrokart.Utils.DBConstants.*;

/**
 * Created by shasha on 12-03-2016.
 */
public class SpendSumaryController {
    int spendAmount = 0;
    DBHelper dbHelper;
    private String totalSpendAmount;
    private Double arraySpend;

    public SpendSumaryController(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void setTotalSpendPerMonth() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_TOTAL_SPEND_PER_MONTH, SpendSummaryModel.getInstance().getTotalAmountSpendPerMonth());
        values.put(COL_SPEND_MONTH, SpendSummaryModel.getInstance().getSpendMonth());

        db.insert(TABLE_SPEND_PER_MONTH, null, values);
        db.close();

    }

    public String[] getMonths() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COL_SPEND_MONTH + " FROM " + TABLE_SPEND_PER_MONTH, null);
        String[] arrayMonth = new String[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            String spendDate = cursor.getString(cursor.getColumnIndex("spendMonth"));
            if (spendDate != null) {
                SimpleDateFormat fmt = new SimpleDateFormat("MMM");
                Date date = null;
                try {
                    date = fmt.parse(spendDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat fmtOut = new SimpleDateFormat("MMM");
                String spendDateFormat = fmtOut.format(date);
                arrayMonth[i] = spendDateFormat;
                i++;
            }
        }
        return arrayMonth;

    }

    public Double getSpendAmountAccToMonth(String amountSpendOnMonth) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COL_TOTAL_SPEND_PER_MONTH + " FROM " + TABLE_SPEND_PER_MONTH + " WHERE " + COL_SPEND_MONTH + " = " + "'" + amountSpendOnMonth + "'", null);

        while (cursor.moveToNext()) {
            String amountSpend = cursor.getString(cursor.getColumnIndex("totalSpendPM"));
            if (amountSpend != null) {
                arraySpend = Double.parseDouble(String.valueOf(amountSpend));
            }
        }
        return arraySpend;

    }

    public boolean updateTotalSpendPerMonth() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String whereClause = (COL_SPEND_MONTH + " = '" + SpendSummaryModel.getInstance().getSpendMonth() + "'");


        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TOTAL_SPEND_PER_MONTH, SpendSummaryModel.getInstance().getTotalAmountSpendPerMonth());
        sqLiteDatabase.update(TABLE_SPEND_PER_MONTH, values, whereClause, null);
        sqLiteDatabase.close();

        db.close();
        return true;
    }

    public String getTotalSpendPerMonth() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COL_TOTAL_SPEND_PER_MONTH + " FROM " + TABLE_SPEND_PER_MONTH + " WHERE " + COL_SPEND_MONTH + " = '" + SpendSummaryModel.getInstance().getSpendMonth() + "'", null);


        if (!cursor.isAfterLast()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                totalSpendAmount = cursor.getString(cursor.getColumnIndex(COL_TOTAL_SPEND_PER_MONTH));
                SpendSummaryModel.getInstance().setTotalAmountSpendPerMonth(totalSpendAmount);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();
        return totalSpendAmount;
    }


}

