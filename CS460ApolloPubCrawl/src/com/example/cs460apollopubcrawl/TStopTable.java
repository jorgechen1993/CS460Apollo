/**
 * Class that accesses the tstop table from a MySQL database
 * and creates an ArrayList of Tstop object that is used to store all records
 * from the table into the ArrayList
 * 
 * Input: all records from the tstop table from a MySQL server database
 * Output: an ArrayList of type Tstop
 * Error Handling: returns an exception if there was an error connecting to the MySQL database
 * 
 * Created by Jorge Chen on 04/05/2015
 * 
 */

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
	
	/**
	 * Empty constructor used to call on the methods of this calls
	 */
	public TStopTable(){}
	
	/**
	 * Accessor method that accesses the MySQL database and gets all the records
	 * from the tstop table and puts all the records into an ArrayList of type Tstop and returns it.
	 * @return tstopArray
	 */
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
        
        //for each record in the Tstop table add it to an ArrayList of type Tstop
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
    	Log.e("TStopTable.java Failure", "SQL Error");
      e.printStackTrace();
    }
		return tstopArray;
	}	
}
