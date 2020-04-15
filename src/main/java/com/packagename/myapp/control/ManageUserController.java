
package com.packagename.myapp.control;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import com.packagename.myapp.model.base.JsonModel;
import com.packagename.myapp.model.base.User;

/**
 * 'Controller object' for new paper submission page.
 *
 * Used to help bind form data of researcher's new submission page to model data,
 * which is then used to write to files using JsonModel.
 * @Ref Sean
 * @author Hitony7
 *
 */
public class ManageUserController {
    //Set up variables to hold values
    private HashMap <String, User> userData;

    private User user;
    private InputStream inputStream;
    private String filename;


    //gets the user info and puts into a hashmap
    public ManageUserController() {
        try{
            userData = JsonModel.getUserData();
        } catch (IOException e){
            e.printStackTrace();
            userData = new HashMap<>();
        }
    }


    //checks if login is right
    public static User validateLogin(HashMap<String, User> userList, String username, String password) {
        for (User i : userList.values()) {
            System.out.println(i.getUserID()+i.getPassword());
            if(i.checkUserPass(username,password)){
                System.out.println("TRUE");
                return i;
            }
        }
        return null;
    }


    //getter and setter for username
    public void setUserName(String username){
        user.setUserID(username);
    }
    public String getUserName(){
        return user.getUserID();
    }

    //getter and setter for the password
    public String getPassword() {
        return user.getPassword();
    }

    public void setPassword(String password){
        user.setPassword(password);
    }

    //getter and setter for the user type
    public String getUserType(){
        return user.getUserType();
    }

    public void setUserType(String userType){
        user.setUserType(userType);
    }

    //getter and setter for email
    public String getEmail(){
        return user.getEmail();
    }

    public void setEmail(String email){
        user.setEmail(email);
    }

    //getter and setter for userID
    public String getUserId(){
        return user.getUserID();
    }

    public void setUserId(String userId){
        user.setUserID(userId);
    }

    //getter and setter for field the user works in
    public String getField(){
        return user.getField();
    }

    public void setField(String field){
        user.setField(field);
    }

}
