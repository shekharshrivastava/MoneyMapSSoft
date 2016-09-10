package com.example.shasha.electrokart.Ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shasha.electrokart.R;

import java.util.ArrayList;

/**
 * Created by shasha on 17-02-2016.
 */

public class DisplayTransactionAdapter extends RecyclerView.Adapter<View_Holder> implements AdapterView.OnItemSelectedListener {

    private final ArrayList<TotalSpendArea> spendArea;

    public DisplayTransactionAdapter(ArrayList<TotalSpendArea> arraySpendArea)
    {
        this.spendArea= arraySpendArea;
    }


    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_transaction_layout, parent, false);
        View_Holder holder = new View_Holder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(),"categoryArea",Toast.LENGTH_SHORT).show();

            }
        });
        return holder;
        }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {
        holder.textTotalAmount.setText(spendArea.get(position).totalAmount);
        holder.textSpendType.setText(spendArea.get(position).categoryArea);
        holder.imageCategory.setImageResource(spendArea.get(position).categoryImage);
    }


    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return spendArea.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(view.getContext(),spendArea.get(position).categoryArea,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
