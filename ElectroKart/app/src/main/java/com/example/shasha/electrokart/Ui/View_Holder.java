package com.example.shasha.electrokart.Ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shasha.electrokart.R;

/**
 * Created by shasha on 17-02-2016.
 */


public class View_Holder extends RecyclerView.ViewHolder {

    TextView textTotalAmount;
     ImageView imageCategory;
     TextView textSpendType;

    View_Holder(View itemView) {
        super(itemView);

         textTotalAmount = (TextView) itemView.findViewById(R.id.totalAmount);
         imageCategory=(ImageView) itemView.findViewById(R.id.categoryImage);
         textSpendType = (TextView) itemView.findViewById(R.id.spendType);
    }
}