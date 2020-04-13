package com.packagename.myapp;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.packagename.myapp.model.base.User;

public class ReviewerListController {
	
	private HashMap<String, User> userData;
	
	private Set<User> reviewerList = new HashSet<>();
	
	public ReviewerListController(HashMap<String, User> userData) {
		this.userData = userData;
	}
	
	public Set<User> getReviewerList() {
		return reviewerList;
	}
	
	public void addReviewer(User reviewer) {
		if (reviewer != null) {
			reviewerList.add(reviewer);
		}
	}
	
	public void removeReviewer(User reviewer) {
		if (reviewer != null) {
			reviewerList.remove(reviewer);
		}
	}
	
	
	public Collection<User> getAllReviewers() {
		return userData.values().stream()
				.filter(user
						-> user.getUserType().equals("Reviewer"))
				.collect(Collectors.toSet());
	}

}
