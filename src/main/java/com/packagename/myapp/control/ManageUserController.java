
package com.packagename.myapp.control;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    private User user;
    private InputStream inputStream;
    private String filename;



    public ManageUserController() {

    }


    public static User validateLogin(HashMap<String, User> userList, String username, String password) {
        for (User i : userList.values()) {
            if(i.checkUserPass(username,password)){
                return i;
            }
        }
        return null;
    }
    
    
    public void setUserName(String userName) {
        user.setName(userName);
    }

    public void setUsername(String username){
        user.setUserID(username);
    }
    public String getUserName(){
        return user.getUserID();
    }





}
