package com.example.shasha.electrokart.Ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shasha.electrokart.Database.DBHelper;
import com.example.shasha.electrokart.Model.LoginDetails;
import com.example.shasha.electrokart.Model.LoginModel;
import com.example.shasha.electrokart.Model.SpendModel;
import com.example.shasha.electrokart.R;

import org.w3c.dom.Text;

import static com.example.shasha.electrokart.Utils.DBConstants.COL_AMOUNT;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_CATEGORY;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_DATE;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_NOTE;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_PAID_TO;
import static com.example.shasha.electrokart.Utils.DBConstants.COL_SPEND_ID;
import static com.example.shasha.electrokart.Utils.DBConstants.TABLE_SPEND;
import static com.example.shasha.electrokart.Utils.DBConstants.USER_PASSWORD;

public class EditTransactionActivity extends AppCompatActivity {

    private EditText textPaidTo;
    private TextView textSpendDate;
    private EditText textAmountSpend;
    private CollapsingToolbarLayout collapse_toolbar;
    private TextView textCategory;
    private EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend);
        Toolbar myToolBar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolBar);
        myToolBar.setTitleTextColor(Color.BLACK);
        getSupportActionBar().setTitle("Edit Tramsactions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();

    }

    public void initialize(){
        textPaidTo = (EditText)findViewById(R.id.et_PaidTo);
        textSpendDate = (TextView)findViewById(R.id.textSpendDate);
        textAmountSpend = (EditText)findViewById(R.id.et_SpendAmount);
        textCategory= (TextView)findViewById(R.id.textCategory);
        note = (EditText)findViewById(R.id.note);

        Intent intent = getIntent();
       int positionID= intent.getIntExtra("positionId", 0);
        SpendModel.getInstance().setEditTrasactionId("" + (positionID + 1));
        getTransactionDetails();
    }
    public void getTransactionDetails() {

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery("SELECT * FROM " + TABLE_SPEND + " WHERE " + COL_SPEND_ID + " = '" + SpendModel.getInstance().getEditTrasactionId() + "'", null);



        if (!cursor.isAfterLast()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                Spend w = new Spend();

                w.AmountPaid = cursor.getString(cursor.getColumnIndex(COL_AMOUNT));
                w.Date = cursor.getString(cursor.getColumnIndex(COL_DATE));
                w.Paid_To = cursor.getString(cursor.getColumnIndex(COL_PAID_TO));
                w.category=cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
                w.note = cursor.getString(cursor.getColumnIndex(COL_NOTE));

                cursor.moveToNext();

                textPaidTo.setText(w.Paid_To);
                textSpendDate.setText(w.Date);
                textAmountSpend.setText(w.AmountPaid);
                textCategory.setText(w.category);
                note.setText(w.note);

            }
        }

        cursor.close();
        db.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_transaction, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == (android.R.id.home)) {
            finish();

        }
        return true;
    }

}
