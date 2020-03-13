package com.coder.applicationhostel;

/**
 * Created by Hanzalah on 2/12/2019.
 */

public class LoginAttr {
    private String Email;
    private String Password;

    public LoginAttr() {
    }

    public LoginAttr(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
