package com.example.shasha.electrokart.Ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.shasha.electrokart.R;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
/*import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.flaviofaria.kenburnsview.Transition;*/

/*import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;*/
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "shekharnxtstar";
    private static final String TWITTER_SECRET = "shekhar143";


//    private KenBurnsView kenBurnsView;
    private ImageView imageMoneyMap;
    private Animation animFadein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));*/
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_splash);


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.shasha.electrokart",
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
//        kenBurnsView = (KenBurnsView) findViewById(R.id.image);
        imageMoneyMap = (ImageView) findViewById(R.id.imageMoneymap);

     /*   animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_out);

        // set animation listener
        animFadein.setAnimationListener(this);
        imageMoneyMap.setAnimation(animFadein);
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(5000, ACCELERATE_DECELERATE);
        kenBurnsView.setTransitionGenerator(generator); //set new transition on kenburns view

        kenBurnsView.setTransitionListener(onTransittionListener());*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                    finish();

                    final SharedPreferences pref =
                            PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                    if (pref.getBoolean("KEY_LOGIN_STATUS", false) == true) {
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);

                    } else {
//                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        facebookLoginStats();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
/*
    private KenBurnsView.TransitionListener onTransittionListener() {
        return new KenBurnsView.TransitionListener() {

            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
            }
        };
    }*/

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void facebookLoginStats() {
        //check login
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
