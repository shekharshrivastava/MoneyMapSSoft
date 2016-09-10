package com.example.shasha.electrokart.Ui;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shasha.electrokart.Controller.CategoryController;
import com.example.shasha.electrokart.Controller.SpendController;
import com.example.shasha.electrokart.Controller.SpendSumaryController;
import com.example.shasha.electrokart.Model.CategoryModel;
import com.example.shasha.electrokart.Model.SpendModel;
import com.example.shasha.electrokart.Model.SpendSummaryModel;
import com.example.shasha.electrokart.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SpendActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerTransactionType;
    private EditText et_SpendAmount;
    private EditText et_PaidTo;
    private TextView textSpendDate;
    private TextView textCategory;
    private EditText et_note;
    private ImageView imageicon;
    private int m_Spend;
    private String getImageId;
    private int getTotalAmount;
    private int yy;
    private int mm;
    private int dd;
    private int getTotalSpendPerMonth;
    private boolean isTotalSpendUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Transaction");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.BLACK);
       /* final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
*/
        initialize();
    }

    public void initialize() {

        final Calendar c = Calendar.getInstance();
        yy = c.get(Calendar.YEAR);
        mm = c.get(Calendar.MONTH);
        dd = c.get(Calendar.DAY_OF_MONTH);
        String currentDate = "" + dd + "/" + (mm + 1) + "/" + yy;
        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("dd-MMM-yyyy");
        String newDateStr = postFormater.format(dateObj);

//        createTranssactionTypeDialog();

        spinnerTransactionType = (Spinner) findViewById(R.id.spinnerTransactionType);

        // Spinner click listener
        spinnerTransactionType.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Cash spend/Income");
        categories.add("Credit/Debit card");
        categories.add("Cheque");
        categories.add("Other");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTransactionType.setAdapter(dataAdapter);

        et_SpendAmount = (EditText) findViewById(R.id.et_SpendAmount);
        et_PaidTo = (EditText) findViewById(R.id.et_PaidTo);
        textCategory = (TextView) findViewById(R.id.textCategory);
        textCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpendActivity.this, CategoriesActivity.class);
                startActivityForResult(intent, 101);

            }
        });
        imageicon = (ImageView) findViewById(R.id.imageView_icon);
        textSpendDate = (TextView) findViewById(R.id.textSpendDate);
        et_note = (EditText) findViewById(R.id.note);
        textSpendDate.setText(newDateStr);
        textSpendDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getSpendDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        CategoryModel.getInstance().setTotalSpendAmount(null);
        SpendSummaryModel.getInstance().setTotalAmountSpendPerMonth(null);
        CategoryModel.getInstance().setColCategoryId(null);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void createTranssactionTypeDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.transaction_type_dialog);
        dialog.setTitle("Select Transaction Type");
        Button btnSet = (Button) dialog.findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup transactionType = (RadioGroup) dialog.findViewById(R.id.transactionType);
                int selectedId = transactionType.getCheckedRadioButtonId();
                RadioButton transactionSelection = (RadioButton) dialog.findViewById(selectedId);
                String transactionTypeSelection = transactionSelection.getText().toString();
                SpendModel.getInstance().setTransactionType(transactionTypeSelection);
                dialog.dismiss();
            }
        });
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_transaction, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == (R.id.menu_add_transaction)) {
            m_Spend = Integer.parseInt(et_SpendAmount.getText().toString());
            SharedPreferences preferences = getSharedPreferences("Spend", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("spend", m_Spend + preferences.getInt("spend", 0));
            editor.commit();


            SpendModel.getInstance().setSpendAmount(et_SpendAmount.getText().toString());
            SpendModel.getInstance().setPaidTo(et_PaidTo.getText().toString());
            SpendModel.getInstance().setCategory(textCategory.getText().toString());
            SpendModel.getInstance().setDate(textSpendDate.getText().toString());
            SpendModel.getInstance().setNote(et_note.getText().toString());
//            ((IconClass)getIntent().getSerializableExtra("icon")).getImageId();

            SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = null;
            try {
                date = fmt.parse(textSpendDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM");
            String spendDateFormat = fmtOut.format(date);
            SpendModel.getInstance().setMonth(spendDateFormat);
            SpendSummaryModel.getInstance().setSpendMonth(spendDateFormat);

            SpendController controller = new SpendController(this);

            String totalSpendPM = et_SpendAmount.getText().toString();
            SpendSumaryController spendSumaryController = new SpendSumaryController(this);
            String getTotalSpendPerMonthFromDB = spendSumaryController.getTotalSpendPerMonth();
            if (getTotalSpendPerMonthFromDB!=null) {
                String totalSpendPerMonth = SpendSummaryModel.getInstance().getTotalAmountSpendPerMonth();
                {
                        getTotalSpendPerMonth = Integer.parseInt(totalSpendPerMonth);

                }
            }
            int amountSpendPerMonth = Integer.parseInt(totalSpendPM);
            int finalSpendAmountPerMonth = amountSpendPerMonth + getTotalSpendPerMonth;
            SpendSummaryModel.getInstance().setTotalAmountSpendPerMonth(String.valueOf(finalSpendAmountPerMonth));

            if (getTotalSpendPerMonthFromDB!=null) {
                if(SpendSummaryModel.getInstance().getTotalAmountSpendPerMonth()!=null) {
                    isTotalSpendUpdated = spendSumaryController.updateTotalSpendPerMonth();
                }

            }else{
                spendSumaryController.setTotalSpendPerMonth();
            }

            if (SpendModel.getInstance().getTransactionType() == null) {
                SpendModel.getInstance().setTransactionType(spinnerTransactionType.getSelectedItem().toString());
            }

            CategoryModel.getInstance().setCategoryType(textCategory.getText().toString());
            if (getImageId != null) {
                CategoryModel.getInstance().setCategoryImage(getImageId);
            }
                controller.setSpendDetails();


//Category Controller

            CategoryController categoryController = new CategoryController(this);
            categoryController.getTotalSpendByCategory();

            String totalAmountSpendByCategory = CategoryModel.getInstance().getTotalSpendAmount();
            if (totalAmountSpendByCategory != null) {
                getTotalAmount = Integer.parseInt(totalAmountSpendByCategory);
            }
            String amountSpend = et_SpendAmount.getText().toString();
            int spendAmountByCategory = Integer.parseInt(amountSpend);
            int totalSpendAmount = getTotalAmount + spendAmountByCategory;
            CategoryModel.getInstance().setTotalSpendAmount("" + totalSpendAmount);
            categoryController.getCategoryType();
            String colCategoryId = CategoryModel.getInstance().getColCategoryId();
            if (colCategoryId != null) {
                categoryController.updateSpendByCategory();
            } else {
                categoryController.setTotalAmount();
            }


            Toast.makeText(this, "Transaction added", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (item.getItemId() == (android.R.id.home)) {
            finish();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            IconClass icon = (IconClass) data.getSerializableExtra("icon");
            if (icon.getIconName().equals("Select Category")) {
                textCategory.setText(icon.getIconName());

            } else {
                textCategory.setText(icon.getIconName());
                imageicon.setImageResource(icon.getImageId());
                int imageId = icon.getImageId();
                getImageId = "" + imageId;

                imageicon.setVisibility(View.VISIBLE);

            }

        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void getSpendDate() throws ParseException {
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String selectedDate = (dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);
                        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy");
                        Date dateObj = null;
                        try {
                            dateObj = curFormater.parse(selectedDate);
                            SimpleDateFormat postFormater = new SimpleDateFormat("dd-MMM-yyyy");
                            String newDateStr = postFormater.format(dateObj);
                            textSpendDate.setText(newDateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                }, yy, mm, dd);
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
        dpd.show();


    }
}
