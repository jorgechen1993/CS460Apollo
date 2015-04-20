package com.example.cs460apollopubcrawl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.util.Log;

public class TStopTable {
	public ArrayList<TStop> tstopArray = new ArrayList();

public TStopTable(){}
	
	public ArrayList<TStop> getData(){
		String URL = "jdbc:mysql://frodo.bentley.edu:3306/cs460apollo";
        String username = "cs460Apollo";
        String password = "cs460apollo";
    	String tstopID, stopName, transferLocation, handiAccess, lineColor,
    				stopLatitude, stopLongitude;

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
            "SELECT * FROM cs460apollo.tstop ORDER BY Tstop_ID;");
        
        //for each record in Bar table add Bar object to ArrayList and add bar data to log
        while (result.next()) {
        tstopID = result.getString("Tstop_ID");
        stopName = result.getString("StopName");
        transferLocation = result.getString("TransferLocation");
        handiAccess = result.getString("HandiAccess");
        lineColor = result.getString("LineColor");
        stopLatitude = result.getString("StopLatitude");
        stopLongitude = result.getString("StopLongitude");

        TStop tstop = new TStop(tstopID, stopName, transferLocation, handiAccess,
        					lineColor, stopLatitude, stopLongitude);
        
        tstopArray.add(tstop);
        
        }     
       
        //clean up
        con.close();
    }
    catch (SQLException e) {
    	Log.e("Bar", "SQL Error");
      e.printStackTrace();
    }
		return tstopArray;
	} //run
	

}
