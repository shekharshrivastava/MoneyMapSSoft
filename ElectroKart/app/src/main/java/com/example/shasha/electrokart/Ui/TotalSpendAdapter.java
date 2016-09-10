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
 * Created by shasha on 26-02-2016.
 */
public class TotalSpendAdapter extends ArrayAdapter<TotalSpendAreasDetails> {
    private final Context context;
    private final ArrayList<TotalSpendAreasDetails> totalSpendAreas;

    public TotalSpendAdapter(Context context,int resource, ArrayList<TotalSpendAreasDetails> arraySpendAreas)
    {
        super(context,resource );
        this.context = context;
        this.totalSpendAreas= arraySpendAreas;
    }
    @Override
    public int getCount() {
        return this.totalSpendAreas.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layout = null;
        if (convertView==null)
        {
            Activity activity = (Activity) context;
            layout = (LinearLayout) activity.getLayoutInflater()
                    .inflate(R.layout.row_spend_areas,null);

        }
        else {
            layout = (LinearLayout) convertView;
        }

        TotalSpendAreasDetails e = totalSpendAreas.get(position);

        TextView spendPaidTo = (TextView) layout.findViewById(R.id.spendPaidTo);
        TextView amountPaidTo=(TextView) layout.findViewById(R.id.amountPaid);
        TextView textDate = (TextView) layout.findViewById(R.id.datePaid);



        spendPaidTo.setText(e.paidTo);
        amountPaidTo.setText(e.amountPaid);
        textDate.setText(e.datePaid);

        return layout;
    }
}

