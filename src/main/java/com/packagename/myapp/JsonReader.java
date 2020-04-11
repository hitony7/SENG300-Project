package com.packagename.myapp;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.packagename.myapp.control.Paper;
import com.packagename.myapp.control.Submission;

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
        //get orignal list
        JSONArray userList= readOLD("users.json");

        userList.add(user);
        write("users.json", userList);
    }
    public JSONArray readOLD(String filename){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);
            return employeeList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return userList;
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
    
    private static JSONObject readData(String filename) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
    	
    	try (FileReader reader = new FileReader(filename)) {
            //Read JSON file
            return (JSONObject) jsonParser.parse(reader);
            
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    private static void writeData(String filename, JSONObject obj) {
    	try (FileWriter writer = new FileWriter(filename, false)) {
    		obj.writeJSONString(writer);
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    private void write(String filename, JSONArray userList){
        try (FileWriter file = new FileWriter(filename,false)) {

            file.write(userList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static HashMap<Integer,Paper> getPaperData() {
    	HashMap<String,JSONObject> jsonData = readData("data\\paper.json");
    	HashMap<Integer,Paper> paperData = new HashMap<>();
    	
		for(Map.Entry<String, JSONObject> pair : jsonData.entrySet()) {
    		paperData.put(new Integer(pair.getKey()), new Paper(pair.getValue()));
    	}
    	
    	return paperData;
    }
    
    public static HashMap<Pair<Integer,String>,Paper> getSubmissionData() {
    	HashMap<String,JSONObject> jsonData = readData("data\\submission.json");
    	HashMap<Pair<Integer,String>,Paper> submissionData = new HashMap<>();
    	
    	for(Map.Entry<String, JSONObject> pair : jsonData.entrySet()) {
    		String[] splitKey = pair.getKey().split("_");
    		Integer paperID = new Integer(splitKey[0]);
    		String version = splitKey[1];
    		submissionData.put(new ImmutablePair<Integer, String>(paperID, version), new Paper(pair.getValue()));
    	}
    	
    	return submissionData;
    }
    
    public static void newPaperSubmission(HashMap<String,Paper> paperData, 
    		HashMap<Pair<Integer,String>,Paper> submissionData, int ResearcherID, String journal, 
    		String editorEmail, String title, String message, String reviewerNominations) {
    	
    }
    
    public static int getNumberOfPapers(HashMap<String,Paper> paperData) {
    	return paperData.size();
    }
}
