package com.example.mily_alpha.DB_Classes;

public class User {
    int User_Id;
    String UserName;
    String UserEmail;

    public User(String userName, String userEmail) {
        UserName = userName;
        UserEmail = userEmail;
    }

    public int getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(int user_Id) {
        User_Id = user_Id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }
}
