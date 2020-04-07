package com.packagename.myapp;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class JsonReader {
    JSONArray userList = new JSONArray();

    public void newUser(String username, String password, String usertype) {

        JSONObject employeeDetails = new JSONObject();
        employeeDetails.put("username", username);
        employeeDetails.put("password", password);
        employeeDetails.put("usertype", usertype);
        //nested
        JSONObject user = new JSONObject();
        user.put("user",employeeDetails );

        userList.add(user);
        write("users.json", userList);
    }

    public void getUsername(String filename){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);

            //Iterate over employee array
            employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseEmployeeObject(JSONObject employee) {
        //Get employee object within list
        JSONObject employeeObject = (JSONObject) employee.get("user");

        //Get employee first name
        String username = (String) employeeObject.get("username");
        System.out.println(username);
        //return string
    }


        private void write(String filename, JSONArray userList){
        try (FileWriter file = new FileWriter(filename)) {

            file.write(userList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
