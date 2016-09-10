package com.example.shasha.electrokart.Model;

import java.io.Serializable;

/**
 * Created by shasha on 20-02-2016.
 */
public class CategoryDetails implements Serializable {
   public String categoryType;
   public String categoryImage;
   public String totalSpendAmount;
    public String colCategoryID;

    @Override
    public String toString() {
        return "CategoryDetails{" +
                "categoryType='" + categoryType + '\'' +
                ", categoryImage='" + categoryImage + '\'' +
                ", totalSpendAmount='" + totalSpendAmount + '\'' +
                ", colCategoryID='" + colCategoryID + '\'' +
                '}';
    }
}
