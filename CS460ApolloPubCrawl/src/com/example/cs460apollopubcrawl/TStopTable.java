package com.example.cs460apollopubcrawl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.util.Log;

public class TStopTable {
	private ArrayList<String> tstopIDArray = new ArrayList();
	private ArrayList<String> stopNameArray = new ArrayList();
	private ArrayList<String> transferLocationArray = new ArrayList();
	private ArrayList<String> handiAccessArray = new ArrayList();
	private ArrayList<String> lineColorArray = new ArrayList();
	private ArrayList<String> stopLatitudeArray = new ArrayList();
	private ArrayList<String> stopLongitudeArray = new ArrayList();

public TStopTable(){}
	
	public void getData(){
		String URL = "jdbc:mysql://frodo.bentley.edu:3306/cs460apollo";
        String username = "cs460Apollo";
        String password = "cs460apollo";
    	String tstopID, stopName, transferLocation, handiAccess, lineColor,
    				stopLatitude, stopLongitude;
    	int pointer = 0;

        try { //load driver into VM memory
        	Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            Log.e("JDBC", "Did not load driver");          
        }
        

        Statement stmt = null;
        Connection con=null;
        try { //create connection to database
         con = DriverManager.getConnection (
            URL,
            username,
            password);
        stmt = con.createStatement();
    
        ResultSet result = stmt.executeQuery(
            "SELECT * FROM cs460apollo.bar ORDER BY Bar_ID;");
        
        //for each record in Bar table add Bar object to ArrayList and add bar data to log
        while (result.next()) {
        tstopID = result.getString("Tstop_ID");
        tstopIDArray.add(pointer, tstopID);
        
        stopName = result.getString("StopName");
        stopNameArray.add(pointer, stopName);
        
        transferLocation = result.getString("TransferLocation");
        transferLocationArray.add(pointer, transferLocation);
        
        handiAccess = result.getString("HandiAccess");
        handiAccessArray.add(pointer, handiAccess);
        
        lineColor = result.getString("LineColor");
        lineColorArray.add(pointer, lineColor);
        
        stopLatitude = result.getString("StopLatitude");
        stopLatitudeArray.add(pointer, stopLatitude);
        
        stopLongitude = result.getString("StopLongitude");
        stopLongitudeArray.add(pointer,stopLongitude);
        
        TStop tstop = new TStop(tstopID, stopName, transferLocation, handiAccess,
        					lineColor, stopLatitude, stopLongitude);
        }     
       
        //clean up
        con.close();
    }
    catch (SQLException e) {
    	Log.e("Bar", "SQL Error");
      e.printStackTrace();
    }
	} //run
	
	public ArrayList<String> geTstopIDArray(){
		return tstopIDArray;
	}
	
	public ArrayList<String> getStopNameArray(){
		return stopNameArray;
	}
	
	public ArrayList<String> getTransferLocationArray(){
		return transferLocationArray;
	}
	
	public ArrayList<String> getHandiAccessArray(){
		return handiAccessArray;
	}
	
	public ArrayList<String> getLineColorArray(){
		return lineColorArray;
	}
	
	public ArrayList<String> getStopLatitudeArray(){
		return stopLatitudeArray;
	}
	
	public ArrayList<String> getStopLongitudeArray(){
		return stopLongitudeArray;
	}
}
