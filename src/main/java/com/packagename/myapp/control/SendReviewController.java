package com.packagename.myapp.control;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.packagename.myapp.model.base.Review;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.packagename.myapp.ReviewerListController;
import com.packagename.myapp.model.base.Submission.SubStatus;
import com.vaadin.flow.component.notification.Notification;

public class SendReviewController {
    //variables to hold review, comment and paperId
    private Review review;

    private String comment;
    private int paperId;

    //getter and setter for comment
    public String getComment(){
           return review.getComment();
    }

    public void setComment(String comment){
        review.setComment(comment);
    }

    //getter and setter for paper data
    public int getPaper(){
        return review.getPaperID();
    }

    public void setPaper(int paperId ){
        review.setPaperID(paperId);
    }


}
