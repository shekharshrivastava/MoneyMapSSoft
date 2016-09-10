package com.example.shasha.electrokart.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Switch;

import static com.example.shasha.electrokart.Utils.DBConstants.*;

/**
 * Created by shasha on 10-01-2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 4;
    private static final String DB_NAME = "moneymap.sqlite";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_REGISTRATION + " ( "
                + USER_JABBERID + " integer primary key autoincrement, "
                + USER_FNAME + " text, "
                + USER_LNAME + " text, "
                + USER_NUMBER + " text, "
                + USER_EMAILID + " text, "
                + USER_PASSWORD + " text, "
                + LOGIN_DATE + " date); ");

        db.execSQL("create table " + TABLE_SPEND + " ( "
                + COL_SPEND_ID + " integer primary key autoincrement, "
                + COL_AMOUNT + " integer, "
                + COL_CATEGORY + " text, "
                + COL_PAID_TO + " text, "
                + COL_MONTH + " text, "
                + COL_TRANSACTION_TYPE + " text, "
                + COL_DATE + " date, "
                + COL_NOTE + " text); ");

        db.execSQL("create table " + TABLE_CATEGORY + " ( "
                + COL_CATEGORY_ID + " integer primary key autoincrement, "
                + COL_CATEGORY_TYPE + " text, "
                + COL_TOTAL_SPEND + " text, "
                + COL_CATEGORY_IMAGE + " text); ");

        db.execSQL("create table " + TABLE_SPEND_PER_MONTH + " ( "
                + COL_SPEND_PER_MONTH_ID + " integer primary key autoincrement, "
                + COL_TOTAL_SPEND_PER_MONTH + " text, "
                + COL_SPEND_MONTH + " text); ");


        db.execSQL("create table " + TABLE_BUDGET + " ( "
                + COL_BUDGET_ID + " integer primary key autoincrement, "
                + COL_BUDGET + " text, "
                + COL_BUDGET_TYPE + " text, "
                + COL_BUDGET_DATE + " text, "
                + COL_BUDGET_MONTH + " text); ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If you need to add a column
        if (newVersion > oldVersion) {
            switch (newVersion) {
                case 2:
                    db.execSQL("ALTER TABLE " + TABLE_SPEND + " ADD COLUMN " + COL_MONTH + " TEXT ");
                    break;
                case 3:
                    db.execSQL("ALTER TABLE " + TABLE_SPEND + " ADD COLUMN " + COL_TOTAL_SPEND_PER_MONTH + " TEXT ");
                    break;

                case 4:
                    db.execSQL("create table " + TABLE_SPEND_PER_MONTH + " ( "
                            + COL_SPEND_PER_MONTH_ID + " integer primary key autoincrement, "
                            + COL_SPEND_MONTH + " text); ");
                    break;

                case 5:
                    db.execSQL("create table " + TABLE_BUDGET + " ( "
                            + COL_BUDGET_ID + " integer primary key autoincrement, "
                            + COL_BUDGET + " text, "
                            + COL_BUDGET_TYPE + " text, "
                            + COL_BUDGET_DATE + " date, "
                            + COL_BUDGET_MONTH + " text); ");
                    break;


            }

        }
    }
}
