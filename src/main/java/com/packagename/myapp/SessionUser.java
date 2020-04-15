package com.packagename.myapp;

import com.packagename.myapp.model.base.User;

//STATIC CLASS for current referance CURRENT USER
public class SessionUser {
        public static String name;
        public static String password;
        public static String userType;
        public static String email;

    public SessionUser(User user) {
            name = user.getName();
            password = user.getPassword();
            userType = user.getUserType();
            email = user.getEmail();
    }

    //getter for name, password, usertype and email
    public static String getName() {
        return name;
    }

    public static String getPassword() {
        return password;
    }


    public static String getUsertype() {
        return userType;
    }


    public static String getEmail(){
        return email;
    }


}
