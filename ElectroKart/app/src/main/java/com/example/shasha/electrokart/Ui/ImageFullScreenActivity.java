package com.example.shasha.electrokart.Ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;


import com.example.shasha.electrokart.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class ImageFullScreenActivity extends AppCompatActivity {

    private ShareActionProvider mShareActionProvider;
    private Bitmap userThumb;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ShareActionProvider share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shekhar shrivastava");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getUserThumbnail();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void getUserThumbnail() {
        ImageView imageFullScreen = (ImageView) findViewById(R.id.imageFullScreen);
        SharedPreferences userProfilePicturePref = this.getSharedPreferences("AppUserProfilePic", MODE_PRIVATE);
        String userProfilePicString = userProfilePicturePref.getString("userProfilePicture", null);
        userThumb = setThumbnail(userProfilePicString);
        imageFullScreen.setImageBitmap(userThumb);
    }

    public Bitmap setThumbnail(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == (android.R.id.home)) {
            finish();
        }
        return true;

    }




}
