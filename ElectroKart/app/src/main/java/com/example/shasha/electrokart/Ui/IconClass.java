package com.example.shasha.electrokart.Ui;

import com.example.shasha.electrokart.R;

import java.io.Serializable;

/**
 * Created by shasha on 11-02-2016.
 */
public class IconClass implements Serializable {


    int imageId;
    String IconName;

    public IconClass(int imageId, String iconName)
    {
        this.imageId = imageId;
        IconName = iconName;
    }

    public int getImageId() {
        return imageId;
    }

    public String getIconName() {
        return IconName;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setIconName(String iconName) {
        IconName = iconName;
    }

    @Override
    public String toString() {
        return "IconClass{" +
                "imageId=" + imageId +
                ", IconName='" + IconName + '\'' +
                '}';
    }

    public IconClass()
    {
        this.IconName="Select Category";
        this.imageId= R.id.home;


    }
}
