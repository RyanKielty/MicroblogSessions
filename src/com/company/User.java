package com.company;

import java.util.ArrayList;

/**
 * Created by ryankielty on 12/5/16.
 */
public class User {
    String userName;
    String password;

    public ArrayList<Messages> messageList = new ArrayList();


    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
