package com.example.shasha.electrokart.Ui;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shasha.electrokart.Database.BackupDatabase;
import com.example.shasha.electrokart.Database.DBHelper;
import com.example.shasha.electrokart.Model.BudgetModel;
import com.example.shasha.electrokart.Model.RegistrationModel;
import com.example.shasha.electrokart.Model.SpendModel;
import com.example.shasha.electrokart.R;
import com.example.shasha.electrokart.Utils.DBConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppSettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, OnClickListener, AdapterView.OnItemSelectedListener {

    private Toolbar toolBar;
    private Switch showBudgetSwitch;
    private Switch switchProtectBal;
    private Switch switchIncome;
    private Switch switchPin;
    private LinearLayout setBudget;
    private LinearLayout clearData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("App Settings");
        toolBar.setTitleTextColor(Color.BLACK);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialize();

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void initialize() {
        final SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(AppSettingActivity.this);
        showBudgetSwitch = (Switch) findViewById(R.id.showBudgetSwitch);
        switchProtectBal = (Switch) findViewById(R.id.switchProtectBal);
        switchIncome = (Switch) findViewById(R.id.switchIncome);
        switchPin = (Switch) findViewById(R.id.switchPin);
        setBudget = (LinearLayout) findViewById(R.id.setBudget);
        clearData = (LinearLayout) findViewById(R.id.clearData);
        setBudget.setOnClickListener(this);
        showBudgetSwitch.setOnCheckedChangeListener(this);
        switchProtectBal.setOnCheckedChangeListener(this);
        switchIncome.setOnCheckedChangeListener(this);
        switchPin.setOnCheckedChangeListener(this);
        clearData.setOnClickListener(this);

        if (pref.getBoolean("SHOW BUDGET SWITCH", true) == false) {
            showBudgetSwitch.setChecked(false);
        } else {
            showBudgetSwitch.setChecked(true);
        }

        if (pref.getBoolean("PROTECT BUDGET SWITCH", false) == true) {
            switchProtectBal.setChecked(true);
        } else {
            switchProtectBal.setChecked(false);
        }

        if (pref.getBoolean("SWITCH INCOME", false) == true) {
            switchIncome.setChecked(true);
        } else {
            switchIncome.setChecked(false);
        }

        if (pref.getBoolean("SWITCH PIN", false) == true) {
            switchPin.setChecked(true);
        } else {
            switchPin.setChecked(false);
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);

        final SharedPreferences.Editor editor = pref.edit();


        if (buttonView == showBudgetSwitch) {
            if (isChecked) {
                editor.putBoolean("SHOW BUDGET SWITCH", true);
                editor.commit();

            } else {
                editor.putBoolean("SHOW BUDGET SWITCH", false);
                editor.commit();


            }
        }

        if (buttonView == switchProtectBal) {
            if (isChecked) {
                editor.putBoolean("PROTECT BUDGET SWITCH", true);
                editor.commit();

            } else {
                editor.putBoolean("PROTECT BUDGET SWITCH", false);
                editor.commit();

            }
        }
        if (buttonView == switchIncome) {
            if (isChecked) {
                editor.putBoolean("SWITCH INCOME", true);
                editor.commit();

            } else {
                editor.putBoolean("SWITCH INCOME", false);
                editor.commit();

            }
        }

        if (buttonView == switchPin) {
            if (isChecked) {
                editor.putBoolean("SWITCH PIN", true);
                editor.commit();

            } else {
                editor.putBoolean("SWITCH PIN", false);
                editor.commit();

            }
        }
    }

    public void backUpData(View v) {
        BackupDatabase backupDatabase = new BackupDatabase();
        backupDatabase.exportDB(getApplicationContext().getPackageName());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == (android.R.id.home)) {
            finish();
        }
        return true;
    }

    private void createBudgetDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_budget);
        dialog.setCancelable(false);
        dialog.setTitle("Add Budget");
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        final EditText et_Budget = (EditText) dialog.findViewById(R.id.editBudget);
        final Spinner budgetType = (Spinner) dialog.findViewById(R.id.budgetType);
        final TextView budgetDate = (TextView) dialog.findViewById(R.id.dateText);

        // Spinner click listener
        budgetType.setOnItemSelectedListener(AppSettingActivity.this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Monthly");
        categories.add("Weekly");
        categories.add("Daily");
        categories.add("Yearly");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        budgetType.setAdapter(dataAdapter);


        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
        String dateString = sdf.format(date);
        budgetDate.setText(dateString);
        budgetDate.setTextColor(Color.RED);
        budgetDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AppSettingActivity.this, "OnClickInsideDialog", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        Button btnSet = (Button) dialog.findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String budgetAmount = et_Budget.getText().toString();
                BudgetModel.getInstance().setBudgetAmount(budgetAmount);
                BudgetModel.getInstance().setBudgetType(budgetType.getSelectedItem().toString());
                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
                String dateString = sdf.format(date);
                try {
                    Date budgetDate = sdf.parse(dateString);
                    BudgetModel.getInstance().setBudgetAddDate(dateString);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat fmt = new SimpleDateFormat("dd/MMM/yyyy");
                Date parseDate = null;
                try {
                    parseDate = fmt.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat fmtOut = new SimpleDateFormat("MMM");
                String spendDateFormat = fmtOut.format(parseDate);
                BudgetModel.getInstance().setBudgetMonth(spendDateFormat);
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setBudget:
                createBudgetDialog();
                break;

            case R.id.clearData:
                resetAllData();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void resetAllData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("This will delete all your spends amount do you want to continue ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DBHelper helper = new DBHelper(AppSettingActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                db.delete(DBConstants.TABLE_SPEND, null, null);
                db.delete(DBConstants.TABLE_SPEND_PER_MONTH, null, null);
                db.delete(DBConstants.TABLE_CATEGORY,null,null);
                SharedPreferences spendAmount = getSharedPreferences("Spend", MODE_PRIVATE);
                spendAmount.edit().clear().commit();
                Toast.makeText(AppSettingActivity.this, "Your data is reset", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }
}
