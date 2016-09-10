package com.example.shasha.electrokart.Model;

import java.io.Serializable;

/**
 * Created by shasha on 17-01-2016.
 */
public class LoginDetails implements Serializable {
    public String emailId;
    public int userJid;
    public String password;

    @Override
    public String toString() {
        return "LoginDetails{" +
                "emailId='" + emailId + '\'' +
                ", userJid='" + userJid + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
