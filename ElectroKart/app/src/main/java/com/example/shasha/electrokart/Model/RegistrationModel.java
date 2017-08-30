package com.example.shasha.electrokart.Model;

/**
 * Created by shasha on 16-01-2016.
 */
public class RegistrationModel {

    String userName;
    String userNumber;
    String password;
    String emailId;
    String loginDate;
    private static RegistrationModel mInstance = null;


    public static RegistrationModel getInstance()
    {
         if (mInstance == null)
         {
             mInstance = new RegistrationModel();

         }
        return mInstance;
    }

    private RegistrationModel()
    {

    }
    public RegistrationModel(String userName, String userNumber, String password, String emailId, String loginDate) {
        this.userName = userName;
        this.userNumber = userNumber;
        this.password = password;
        this.emailId = emailId;
        this.loginDate = loginDate;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
