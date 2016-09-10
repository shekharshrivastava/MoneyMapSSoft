package com.example.shasha.electrokart.Model;

/**
 * Created by shasha on 12-03-2016.
 */
public class SpendSummaryModel {

    private String totalAmountSpendPerMonth;
    private String spendMonth;

    private static SpendSummaryModel mInstance = null;

    public static SpendSummaryModel getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new SpendSummaryModel();

        }
        return mInstance;

    }

    public SpendSummaryModel() {
    }

    public String getTotalAmountSpendPerMonth() {
        return totalAmountSpendPerMonth;
    }

    public void setTotalAmountSpendPerMonth(String totalAmountSpendPerMonth) {
        this.totalAmountSpendPerMonth = totalAmountSpendPerMonth;
    }

    public String getSpendMonth() {
        return spendMonth;
    }

    public void setSpendMonth(String spendMonth) {
        this.spendMonth = spendMonth;
    }
}
