package com.example.shasha.electrokart.Model;

/**
 * Created by shasha on 17-01-2016.
 */
public class LoginModel {


    String emailId;
    String userNumber;
    String password;
    String userName;


    private static LoginModel mInstance = null;

    public static LoginModel getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new LoginModel();

        }
        return mInstance;

    }

    public LoginModel() {
    }

        public LoginModel(String emailId, String userNumber, String password) {
            this.emailId = emailId;
            this.userNumber = userNumber;
            this.password = password;
        }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
