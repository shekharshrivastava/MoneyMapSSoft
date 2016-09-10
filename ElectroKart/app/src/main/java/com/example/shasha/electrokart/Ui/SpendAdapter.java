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
import java.util.SortedMap;

/**
 * Created by shasha on 10-08-2015.
 */
public class SpendAdapter extends ArrayAdapter<Spend> {
        private final Context context;
    private final ArrayList<Spend> spends;

    public SpendAdapter(Context context,int resource, ArrayList<Spend> arraySpends)
    {
        super(context,resource );
        this.context = context;
        this.spends= arraySpends;
    }
    @Override
    public int getCount() {
        return this.spends.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layout = null;
        if (convertView==null)
        {
            Activity activity = (Activity) context;
            layout = (LinearLayout) activity.getLayoutInflater()
                    .inflate(R.layout.spend_list,null);

        }
        else {
            layout = (LinearLayout) convertView;
        }

        Spend e = spends.get(position);

        TextView textName = (TextView) layout.findViewById(R.id.textName_contacts);
        TextView textNumber=(TextView) layout.findViewById(R.id.textNumber_Contacts);
        TextView textDate = (TextView) layout.findViewById(R.id.textDate);



        textName.setText(e.Paid_To);
        textNumber.setText(e.AmountPaid);
        textDate.setText(e.Date);

        return layout;
    }
}

