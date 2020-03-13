package com.packagename.myapp;

public class GreetService {
    // Can be removed Later
    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello anonymous user";
        } else {
            return "Hello " + name;
        }
    }
}
