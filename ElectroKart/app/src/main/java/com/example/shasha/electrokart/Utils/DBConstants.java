package com.example.shasha.electrokart.Utils;

/**
 * Created by shasha on 10-01-2016.
 */
public class DBConstants {
    //REGISTRATION TABLE
    public static final String TABLE_REGISTRATION = "registration";
    public static final String USER_JABBERID = "jid";
    public static final String USER_FNAME = "userFName";
    public static final String USER_LNAME = "userLName";
    public static final String USER_NUMBER = "userNumber";
    public static final String USER_PASSWORD = "userPassword";
    public static final String USER_EMAILID = "userEmailId";
    public static final String LOGIN_DATE = "loginDate";

    //Spend Details
    public static final String COL_PAID_TO = "paid";
    public static final String COL_SPEND_ID = "SpendId";
    public static final String TABLE_SPEND = "spend";
    public static final String COL_DATE = "dateSpend";
    public static final String COL_CATEGORY = "category";
    public static final String COL_NOTE = "note";
    public static final String COL_MONTH = "month";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_TRANSACTION_TYPE = "transactionType";


    //Total spend Areas
    public static final String TABLE_CATEGORY = "tableCategory";
    public static final String COL_CATEGORY_ID = "categoryID";
    public static final String COL_CATEGORY_TYPE = "categoryType";
    public static final String COL_CATEGORY_IMAGE = "categoryImage";
    public static final String COL_TOTAL_SPEND = "totalSpend";


    //Total spend per month
    public static final String TABLE_SPEND_PER_MONTH = "tableSpendPerMonth";
    public static final String COL_SPEND_PER_MONTH_ID = "colSpendPerMonthId";
    public static final String COL_SPEND_MONTH = "spendMonth";
    public static final String COL_TOTAL_SPEND_PER_MONTH = "totalSpendPM";



    //Budget Table
    public static final String TABLE_BUDGET = "tableBudget";
    public static final String COL_BUDGET_ID = "colBudgetId";
    public static final String COL_BUDGET = "budget";
    public static final String COL_BUDGET_TYPE = "budgetType"; //Monthly // weeekly // daily //yearly
    public static final String COL_BUDGET_DATE = "budgetDate";
    public static final String COL_BUDGET_MONTH = "budgetMonth";


}
