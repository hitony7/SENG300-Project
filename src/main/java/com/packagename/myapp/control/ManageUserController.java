
package com.packagename.myapp.control;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.packagename.myapp.model.JsonModel;
import com.packagename.myapp.model.Paper;
import com.packagename.myapp.model.Submission;
import com.packagename.myapp.model.Submission.SubStatus;
import com.packagename.myapp.model.User;
import com.vaadin.flow.component.notification.Notification;

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
    private HashMap <String, User> userData;

    private User user;
    private InputStream inputStream;
    private String filename;



    public ManageUserController() {
        try{
            userData = JsonModel.getUserData();
        } catch (IOException e){
            e.printStackTrace();
            userData = new HashMap<>();
        }
    }


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



    public void setUserName(String username){
        user.setUserID(username);
    }
    public String getUserName(){
        return user.getUserID();
    }

    public String getPassword() {
        return user.getPassword();
    }

    public void setPassword(String password){
        user.setPassword(password);
    }

    public String getUserType(){
        return user.getUserType();
    }

    public void setUserType(String userType){
        user.setUserType(userType);
    }

    public String getEmail(){
        return user.getEmail();
    }

    public void setEmail(String email){
        user.setEmail(email);
    }

    public String getUserId(){
        return user.getUserID();
    }

    public void setUserId(String userId){
        user.setUserID(userId);
    }

    public String getField(){
        return user.getField();
    }

    public void setField(String field){
        user.setField(field);
    }

}
