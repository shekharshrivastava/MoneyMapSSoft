package com.example.shasha.electrokart.Ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import com.example.shasha.electrokart.R;

import java.util.ArrayList;


public class CategoriesActivity extends AppCompatActivity {

    private ArrayList<IconClass> iconList;
    private GridView gridView;
    private CustomCategoryPage custom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Drawable drawable;
//        actionBar.setBackgroundDrawable(Drawable.createFromPath("@drawable/"));

        iconList = new ArrayList<IconClass>();
        gridView = (GridView) findViewById(R.id.gridview);
        iconList.add(new IconClass(R.drawable.icon_cat_shopping, "Shopping"));
        iconList.add(new IconClass(R.drawable.icon_cat_healthmedical, "Health"));
        iconList.add(new IconClass(R.drawable.icon_cat_cartransport, "Transports"));
        iconList.add(new IconClass(R.drawable.icon_cat_cashspend, "Cash Spend"));
        iconList.add(new IconClass(R.drawable.icon_cat_cashwithdrawal, "Withdraw"));
        iconList.add(new IconClass(R.drawable.icon_cat_cheque, "Cheque"));
        iconList.add(new IconClass(R.drawable.icon_cat_domestichelp, "Domestic "));
        iconList.add(new IconClass(R.drawable.icon_cat_emi, "emi"));
        iconList.add(new IconClass(R.drawable.icon_cat_financeinsurance, "Insurance"));
        iconList.add(new IconClass(R.drawable.icon_cat_fooddrinks, "Food"));
        iconList.add(new IconClass(R.drawable.icon_cat_grocery, "Grocery"));
        iconList.add(new IconClass(R.drawable.icon_cat_income, "Income"));
        iconList.add(new IconClass(R.drawable.icon_cat_beautyfitness, "Beauty care"));
        iconList.add(new IconClass(R.drawable.icon_cat_creditcard, "Credit Card"));


        custom = new CustomCategoryPage(this, iconList, R.layout.category_icon_layout);
        gridView.setAdapter(custom);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(CategoriesActivity.this, position + "#Selected",
                //        Toast.LENGTH_SHORT).show();
                IconClass icon = iconList.get(position);
                Intent intent = new Intent();
                intent.putExtra("icon", icon);
                setResult(101, intent);
                finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == event.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("icon", new IconClass());
            setResult(101, intent);
            finish();
            return true;


        }
        return false;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            Intent intent = new Intent();
            intent.putExtra("icon", new IconClass());
            setResult(101, intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
