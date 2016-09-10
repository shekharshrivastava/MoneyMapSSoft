package com.example.shasha.electrokart.Ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


import com.example.shasha.electrokart.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView profileCardView;
    private LinearLayout appSettingMenu;
    private LinearLayout aboutActivityMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.BLACK);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialize();
    }

    public void initialize() {
        appSettingMenu = (LinearLayout) findViewById(R.id.appSettingMenu);
        appSettingMenu.setOnClickListener(this);
        aboutActivityMenu = (LinearLayout) findViewById(R.id.aboutActivityMenu);
        aboutActivityMenu.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void openProfile(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appSettingMenu:
            Intent intent = new Intent(this,AppSettingActivity.class);
                startActivity(intent);
                break;

            case R.id.aboutActivityMenu:
                Intent aboutIntent = new Intent(this,AboutActivity.class);
                startActivity(aboutIntent);
                break;

        }
    }
}
