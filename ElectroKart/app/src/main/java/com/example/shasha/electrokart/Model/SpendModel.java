package com.example.shasha.electrokart.Model;

import java.util.Date;

/**
 * Created by shasha on 11-02-2016.
 */
public class SpendModel {
    String transactionType;
    String spendAmount;
    String paidTo;
    String category;
    String  date;
    String note;
    String editTrasactionId;
    String month;
    String totalSpendAmountPerMonth;


    private static SpendModel mInstance = null;

    public static SpendModel getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new SpendModel();

        }
        return mInstance;

    }

    public SpendModel() {
    }


    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getSpendAmount() {
        return spendAmount;
    }

    public void setSpendAmount(String spendAmount) {
        this.spendAmount = spendAmount;
    }

    public String getPaidTo() {
        return paidTo;
    }

    public void setPaidTo(String paidTo) {
        this.paidTo = paidTo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEditTrasactionId() {
        return editTrasactionId;
    }

    public void setEditTrasactionId(String editTrasactionId) {
        this.editTrasactionId = editTrasactionId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
    public String getTotalSpendAmountPerMonth() {
        return totalSpendAmountPerMonth;
    }

    public void setTotalSpendAmountPerMonth(String totalSpendAmountPerMonth) {
        this.totalSpendAmountPerMonth = totalSpendAmountPerMonth;
    }
    @Override
    public String toString() {
        return "SpendModel{" +
                "transactionType='" + transactionType + '\'' +
                ", spendAmount='" + spendAmount + '\'' +
                ", paidTo='" + paidTo + '\'' +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", note='" + note + '\'' +
                '}';
    }
}
