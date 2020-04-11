package com.packagename.myapp.model;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;


public class JsonModel {
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
    
    private static void writeData(String filename, JSONObject obj) throws IOException {
    	FileWriter writer = new FileWriter(filename, false);
		obj.writeJSONString(writer);
		writer.flush();
		writer.close();
    }

    private void write(String filename, JSONArray userList){
        try (FileWriter file = new FileWriter(filename,false)) {

            file.write(userList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static JSONObject readData(String filename) throws IOException {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
    	
    	try (FileReader reader = new FileReader(filename)) {
            //Read JSON file
            return (JSONObject) jsonParser.parse(reader);
            
    	} catch (ParseException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public static HashMap<Integer,Paper> getPaperData() throws IOException {
    	HashMap<String,JSONObject> jsonData = readData("data\\paper.json");
    	HashMap<Integer,Paper> paperData = new HashMap<>();
    	
		for (Map.Entry<String, JSONObject> entry : jsonData.entrySet()) {
			JSONObject jsonPaper = entry.getValue();

			System.out.println(((Long) jsonPaper.get("PaperID")).intValue());
    		paperData.put(new Integer(entry.getKey()), new Paper(jsonPaper));
    	}
    	
    	return paperData;
    }
    
    public static void setPaperData(HashMap<Integer,Paper> paperData) throws IOException {
    	JSONObject jsonData = new JSONObject();
    	
    	for (Map.Entry<Integer, Paper> entry : paperData.entrySet()) {
    		Paper paper = entry.getValue();
    		
    		jsonData.put(entry.getKey(), paper.getJSONObject());
    	}
    	
    	writeData("data\\paper.json", jsonData);
    }
    
    public static HashMap<Pair<Integer,String>,Submission> getSubmissionData() throws IOException {
    	HashMap<String,JSONObject> jsonData = readData("data\\submission.json");
    	HashMap<Pair<Integer,String>,Submission> submissionData = new HashMap<>();
    	
    	for (Map.Entry<String, JSONObject> entry : jsonData.entrySet()) {
    		String[] splitKey = entry.getKey().split("_");
    		Integer paperID = new Integer(splitKey[0]);
    		String version = splitKey[1];
    		JSONObject jsonSubmission = entry.getValue();
    		
    		submissionData.put(new ImmutablePair<Integer, String>(paperID, version), new Submission(jsonSubmission));
    	}
    	
    	return submissionData;
    }
    
    public static void setSubmissionData(HashMap<Pair<Integer,String>,Submission> submissionData) throws IOException {
    	JSONObject jsonData = new JSONObject();
    	
    	for (Map.Entry<Pair<Integer,String>,Submission> entry : submissionData.entrySet()) {
    		Pair<Integer,String> pair = entry.getKey();
    		String primaryKey = pair.getLeft() + "_" + pair.getRight();
    		Submission submission = entry.getValue();
    		
    		jsonData.put(primaryKey, submission.getJSONObject());
    	}
    	
    	writeData("data\\submission.json", jsonData);
    }
}
