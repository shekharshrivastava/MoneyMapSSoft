package com.example.shasha.electrokart.Ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.example.shasha.electrokart.Controller.SpendSumaryController;
import com.example.shasha.electrokart.Database.DBHelper;
import com.example.shasha.electrokart.Model.SpendSummaryModel;
import com.example.shasha.electrokart.R;
import com.example.shasha.electrokart.Utils.HorizontalListView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.shasha.electrokart.Utils.DBConstants.COL_AMOUNT;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_DATE;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_MONTH;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_PAID_TO;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_SPEND_ID;
import static com.example.shasha.electrokart.Utils.DBConstants.TABLE_SPEND;

public class SpendSummaryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private LinearLayout lay;
    HorizontalListView listview;
    private double highest;
    private int[] grossheight;
    private int[] netheight;
    private Double totalSpend;

    /*private Double[] netSal = {12000.0, 13000.0, 14000.25, 10000.1,
            10000.0, 9000.0, 12000.0, 13000.0,
            14000.25, 10000.1, 10000.0, 9000.0};*/
    private String[] datelabel;
    private Double[] arrayTotalSpendPM;
    private Spinner filterSpinner;
    private ArrayList<SpendFilter> spendFilter;
    private SpendFilterAdapter adapterSpendFilter;
    private ListView listviewSpendFilter;
    private Toolbar toolbar;
    private int budget;
    private Double budgetPerMonth;


    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_spend_summary);
        lay = (LinearLayout) findViewById(R.id.linearlay);
        listview = (HorizontalListView) findViewById(R.id.listview);
        listviewSpendFilter = (ListView) findViewById(R.id.listviewSpendFilter);
        toolbar= (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Spend Summary");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        filterSpinner = (Spinner) findViewById(R.id.filterSpinner);
        SpendSumaryController controller = new SpendSumaryController(this);
        spendFilter = new ArrayList<SpendFilter>();
        filterSpinner.setOnItemSelectedListener(this);
        adapterSpendFilter = new SpendFilterAdapter(this, android.R.layout.simple_list_item_1, spendFilter);
        listviewSpendFilter.setAdapter(adapterSpendFilter);
        listviewSpendFilter.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        listview.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        datelabel = controller.getMonths();

        List<String> monthList = Arrays.asList(datelabel);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, monthList);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        filterSpinner.setAdapter(dataAdapter);


        arrayTotalSpendPM = new Double[datelabel.length];
        for (int i = 0; i < (datelabel.length); i++) {
            totalSpend = controller.getSpendAmountAccToMonth(datelabel[i]);
            arrayTotalSpendPM[i] = totalSpend;
        }

        List<Double> b = Arrays.asList(totalSpend);


//        highest = (Collections.max(b));

//        netheight = new int[netSal.length];
        grossheight = new int[arrayTotalSpendPM.length];
        //updateSizeInfo();

        SharedPreferences budgetAmount = this.getSharedPreferences("budget", MODE_PRIVATE);
        budget = budgetAmount.getInt("inputBudget", 0);

         budgetPerMonth = Double.valueOf(budget);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getSpendSummaryList(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class bsAdapter extends BaseAdapter {
        Activity cntx;
        String[] array;

        public bsAdapter(Activity context, String[] arr) {
            // TODO Auto-generated constructor stub
            this.cntx = context;
            this.array = arr;

        }

        public int getCount() {
            // TODO Auto-generated method stub
            return array.length;
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return array[position];
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return array.length;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = null;
            LayoutInflater inflater = cntx.getLayoutInflater();
            row = inflater.inflate(R.layout.simplerow, null);

            DecimalFormat df = new DecimalFormat("#.##");
            final TextView title = (TextView) row.findViewById(R.id.title);
            TextView tvcol1 = (TextView) row.findViewById(R.id.colortext01);
            TextView tvcol2 = (TextView) row.findViewById(R.id.colortext02);

            TextView gt = (TextView) row.findViewById(R.id.text01);
            TextView nt = (TextView) row.findViewById(R.id.text02);
            try {
                tvcol1.setHeight(grossheight[position]);
                if(arrayTotalSpendPM[position]>budgetPerMonth) {
                    tvcol1.setTextColor(Color.RED);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvcol2.setHeight(100);
            title.setText(datelabel[position]);

            gt.setText(df.format(arrayTotalSpendPM[position] / 1000) + " k");
            nt.setText(df.format(budgetPerMonth / 1000) + " k");

            tvcol1.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    Toast.makeText(SpendSummaryActivity.this, "Month/Year: " + title.getText().toString() + "\nSpend: " + arrayTotalSpendPM[position], Toast.LENGTH_SHORT).show();
                }
            });

            tvcol2.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    Toast.makeText(SpendSummaryActivity.this, "Month/Year: " + title.getText().toString() + "\nBudget: " +budget, Toast.LENGTH_SHORT).show();
                }
            });

            return row;

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        updateSizeInfo();
    }

    private void updateSizeInfo() {

        /** This is onWindowFocusChanged method is used to allow the chart to
         * update the chart according to the orientation.
         * Here h is the integer value which can be updated with the orientation
         */
        int h;
        if (getResources().getConfiguration().orientation == 1) {
            h = (int) (lay.getHeight() * 0.5);
            if (h == 0) {
                h = 200;
            }
        } else {
            h = (int) (lay.getHeight() * 0.3);
            if (h == 0) {
                h = 130;
            }
        }
        for (int i = 0; i < (arrayTotalSpendPM.length); i++) {
            if (arrayTotalSpendPM[i] != null) {
                grossheight[i] = (int) ((100 * arrayTotalSpendPM[i]) / budget);
            }
        }
        listview.setAdapter(new bsAdapter(this, datelabel));
    }


    public  void getSpendSummaryList (String month){
        spendFilter.clear();
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT *" +  " FROM " + TABLE_SPEND + " WHERE " + COL_MONTH + " = '" + month + "'", null);
        if (!cursor.isAfterLast()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                SpendFilter filter = new SpendFilter();

                filter.spendFilter_id = cursor.getInt(cursor.getColumnIndex(COL_SPEND_ID));
                filter.amountPaid = cursor.getString(cursor.getColumnIndex(COL_AMOUNT));
                filter.onDate = cursor.getString(cursor.getColumnIndex(COL_DATE));
                filter.paid_to = cursor.getString(cursor.getColumnIndex(COL_PAID_TO));
                spendFilter.add(filter);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();
        adapterSpendFilter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == (android.R.id.home)) {
            finish();
        }
        return true;
    }
}
