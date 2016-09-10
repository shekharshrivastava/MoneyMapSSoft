package com.example.shasha.electrokart.Ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shasha.electrokart.R;

import java.util.ArrayList;

/**
 * Created by shasha on 13-03-2016.
 */
public class SpendFilterAdapter extends ArrayAdapter<SpendFilter> {
    private final Context context;
    private final ArrayList<SpendFilter> spendsFilter;

    public SpendFilterAdapter(Context context, int resource, ArrayList<SpendFilter> arraySpendsFilter) {
        super(context, resource);
        this.context = context;
        this.spendsFilter = arraySpendsFilter;
    }

    @Override
    public int getCount() {
        return this.spendsFilter.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layout = null;
        if (convertView == null) {
            Activity activity = (Activity) context;
            layout = (LinearLayout) activity.getLayoutInflater()
                    .inflate(R.layout.row_filter_list, null);

        } else {
            layout = (LinearLayout) convertView;
        }

        SpendFilter spendFilter = spendsFilter.get(position);

        TextView spendPaidTo = (TextView) layout.findViewById(R.id.spendPaidTo);
        TextView amountPaid = (TextView) layout.findViewById(R.id.amountPaid);
        TextView textDate = (TextView) layout.findViewById(R.id.datePaid);


        spendPaidTo.setText(spendFilter.paid_to);
        amountPaid.setText(spendFilter.amountPaid);
        textDate.setText(spendFilter.onDate);

        return layout;
    }
}