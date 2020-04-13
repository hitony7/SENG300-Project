package com.packagename.myapp.model.base;
import java.io.FileNotFoundException;
import java.io.FileReader;
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


public class JsonModel {
    JSONArray userList = new JSONArray();

    public void newUser(String username, String password, String usertype) {

        JSONObject userDetails = new JSONObject();
        userDetails.put("username", username);
        userDetails.put("password", password);
        userDetails.put("usertype", usertype);
        //nested
        JSONObject user = new JSONObject();
        user.put("user",userDetails );
        //get orignal list
        JSONArray userList= readOLD("users.json");
        //appends the new user
        userList.add(user);
        //write to file
        write("users.json", userList);
    }
    //Read the OLDlist
    public JSONArray readOLD(String filename){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray userList = (JSONArray) obj;
            System.out.println(userList);
            return userList;
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

            JSONArray userList = (JSONArray) obj;
            System.out.println(userList);

            //Iterate over employee array
            userList.forEach( emp -> parseUserObject( (JSONObject) emp ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseUserObject(JSONObject user) {
        //Get user object within list
        JSONObject userObject = (JSONObject) user.get("user");

        //Get user first name
        String username = (String) userObject.get("username");
        System.out.println(username);
        //return string
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
    
    private static void writeData(String filename, JSONObject obj) throws IOException {
    	FileWriter writer = new FileWriter(filename, false);
		obj.writeJSONString(writer);
		writer.flush();
		writer.close();
    }
    
    public static HashMap<String,User> getUserData() throws IOException {
    	HashMap<String,JSONObject> jsonData = readData("data\\user.json");
    	HashMap<String,User> userData = new HashMap<>();
    	
		for (Map.Entry<String, JSONObject> entry : jsonData.entrySet()) {
			JSONObject jsonUser = entry.getValue();

			userData.put(entry.getKey(), new User(jsonUser));
    	}
    	
    	return userData;
    }
    
    public static void setUserData(HashMap<String,User> userData) throws IOException {
    	JSONObject jsonData = new JSONObject();
    	
    	for (Map.Entry<String, User> entry : userData.entrySet()) {
    		User user = entry.getValue();
    		
    		jsonData.put(entry.getKey(), user.jsonObject());
    	}
    	
    	writeData("data\\user.json", jsonData);
    }
    
    public static HashMap<String,Journal> getJournalData() throws IOException {
    	HashMap<String,JSONObject> jsonData = readData("data\\journal.json");
    	HashMap<String,Journal> journalData = new HashMap<>();
    	
		for (Map.Entry<String, JSONObject> entry : jsonData.entrySet()) {
			JSONObject jsonJournal = entry.getValue();

			journalData.put(entry.getKey(), new Journal(jsonJournal));
    	}
    	
    	return journalData;
    }
    
    public static void setJournalData(HashMap<String,Journal> journalData) throws IOException {
    	JSONObject jsonData = new JSONObject();
    	
    	for (Map.Entry<String, Journal> entry : journalData.entrySet()) {
    		Journal journal = entry.getValue();
    		
    		jsonData.put(entry.getKey(), journal.jsonObject());
    	}
    	
    	writeData("data\\journal.json", jsonData);
    }

    public static HashMap<String,EditorJournal> getEditorJournalData() throws IOException {
    	HashMap<String,JSONObject> jsonData = readData("data\\editor_journal.json");
    	HashMap<String,EditorJournal> journalEditorData = new HashMap<>();
    	
		for (Map.Entry<String, JSONObject> entry : jsonData.entrySet()) {
			JSONObject jsonJournalEditor = entry.getValue();

			journalEditorData.put(entry.getKey(), new EditorJournal(jsonJournalEditor));
    	}
    	
    	return journalEditorData;
    }
    
    public static void setEditorJournalData(HashMap<String,EditorJournal> journalData) throws IOException {
    	JSONObject jsonData = new JSONObject();
    	
    	for (Map.Entry<String, EditorJournal> entry : journalData.entrySet()) {
    		EditorJournal journalEditor = entry.getValue();
    		
    		jsonData.put(entry.getKey(), journalEditor.jsonObject());
    	}
    	
    	writeData("data\\editor_journal.json", jsonData);
    }
    
    public static HashMap<Integer,Paper> getPaperData() throws IOException {
    	HashMap<String,JSONObject> jsonData = readData("data\\paper.json");
    	HashMap<Integer,Paper> paperData = new HashMap<>();
    	
		for (Map.Entry<String, JSONObject> entry : jsonData.entrySet()) {
			JSONObject jsonPaper = entry.getValue();

    		paperData.put(new Integer(entry.getKey()), new Paper(jsonPaper));
    	}
    	
    	return paperData;
    }
    
    public static void setPaperData(HashMap<Integer,Paper> paperData) throws IOException {
    	JSONObject jsonData = new JSONObject();
    	
    	for (Map.Entry<Integer, Paper> entry : paperData.entrySet()) {
    		Paper paper = entry.getValue();
    		
    		jsonData.put(entry.getKey(), paper.jsonObject());
    	}
    	
    	writeData("data\\paper.json", jsonData);
    }
    
    public static HashMap<Pair<Integer,String>,Submission> getSubmissionData()
    		throws IOException {
    	HashMap<String,JSONObject> jsonData = readData("data\\submission.json");
    	HashMap<Pair<Integer,String>,Submission> submissionData = new HashMap<>();
    	
    	for (Map.Entry<String, JSONObject> entry : jsonData.entrySet()) {
    		String[] splitKey = entry.getKey().split("_");
    		Integer paperID = new Integer(splitKey[0]);
    		String version = splitKey[1];
    		JSONObject jsonSubmission = entry.getValue();
    		
    		submissionData.put(ImmutablePair.of(paperID, version),
    				new Submission(jsonSubmission));
    	}
    	
    	return submissionData;
    }
    
    public static void setSubmissionData(
    		HashMap<Pair<Integer,String>,Submission> submissionData) throws IOException {
    	JSONObject jsonData = new JSONObject();
    	
    	for (Map.Entry<Pair<Integer,String>,Submission> entry : submissionData.entrySet()) {
    		Pair<Integer,String> pair = entry.getKey();
    		String primaryKey = pair.getLeft() + "_" + pair.getRight();
    		Submission submission = entry.getValue();
    		
    		jsonData.put(primaryKey, submission.jsonObject());
    	}
    	
    	writeData("data\\submission.json", jsonData);
    }
    
    public static HashMap<Pair<Integer,String>,NominatedReviewer> getNominatedReviewerData()
    		throws IOException {
    	HashMap<String,JSONObject> jsonData = readData("data\\nominated_reviewer.json");
    	HashMap<Pair<Integer,String>,NominatedReviewer> nominatedReviewerData
    		= new HashMap<>();
    	
    	for (Map.Entry<String, JSONObject> entry : jsonData.entrySet()) {
    		String[] splitKey = entry.getKey().split("_");
    		Integer paperID = new Integer(splitKey[0]);
    		String reviewerID = splitKey[1];
    		JSONObject jsonNominatedReviewer = entry.getValue();
    		
    		nominatedReviewerData.put(ImmutablePair.of(paperID, reviewerID),
    				new NominatedReviewer(jsonNominatedReviewer));
    	}
    	
    	return nominatedReviewerData;
    }
    
    public static void setNominatedReviewerData(
    		HashMap<Pair<Integer,String>,NominatedReviewer> nominatedReviewerData)
    				throws IOException {
    	JSONObject jsonData = new JSONObject();
    	
    	for (Map.Entry<Pair<Integer,String>,NominatedReviewer> entry 
    			: nominatedReviewerData.entrySet()) {
    		Pair<Integer,String> pair = entry.getKey();
    		String primaryKey = pair.getLeft() + "_" + pair.getRight();
    		NominatedReviewer nominatedReviewer = entry.getValue();
    		
    		jsonData.put(primaryKey, nominatedReviewer.jsonObject());
    	}
    	
    	writeData("data\\nominated_reviewer.json", jsonData);
    }
}