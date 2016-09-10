package com.example.shasha.electrokart.Model;

/**
 * Created by shasha on 19-02-2016.
 */
public class CategoryModel {
    String categoryType;
    String categoryImage;
    String totalSpendAmount;
    String colCategoryId;

    private static CategoryModel mInstance = null;

    public static CategoryModel getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new CategoryModel();

        }
        return mInstance;

    }

    public CategoryModel() {
    }

    public CategoryModel(String categoryType, String categoryImage, String totalSpendAmount) {
        this.categoryType = categoryType;
        this.categoryImage = categoryImage;
        this.totalSpendAmount = totalSpendAmount;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getTotalSpendAmount() {
        return totalSpendAmount;
    }

    public void setTotalSpendAmount(String totalSpendAmount) {
        this.totalSpendAmount = totalSpendAmount;
    }

    public String getColCategoryId() {
        return colCategoryId;
    }

    public void setColCategoryId(String colCategoryId) {
        this.colCategoryId = colCategoryId;
    }


}
