package com.example.shasha.electrokart.Storage;

import android.content.SharedPreferences;

/**
 * Created by shasha on 15-03-2016.
 */
public class AppSharedPreference  {

    private static AppSharedPreference mInstance = null;

    public static AppSharedPreference getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new AppSharedPreference();

        }
        return mInstance;

    }



}
