package com.example.shasha.electrokart.Model;

/**
 * Created by shasha on 26-03-2016.
 */
public class BudgetModel {
    String budgetAmount;
    String budgetType;
    String budgetMonth;
    String budgetAddDate;

    private static BudgetModel mInstance = null;

    public static BudgetModel getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new BudgetModel();

        }
        return mInstance;

    }
    public BudgetModel(){

    }

    public BudgetModel(String budgetAmount, String budgetType, String budgetMonth, String budgetAddDate) {
        this.budgetAmount = budgetAmount;
        this.budgetType = budgetType;
        this.budgetMonth = budgetMonth;
        this.budgetAddDate = budgetAddDate;
    }

    public String getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(String budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public String getBudgetMonth() {
        return budgetMonth;
    }

    public void setBudgetMonth(String budgetMonth) {
        this.budgetMonth = budgetMonth;
    }

    public String getBudgetAddDate() {
        return budgetAddDate;
    }

    public void setBudgetAddDate(String budgetAddDate) {
        this.budgetAddDate = budgetAddDate;
    }
}
