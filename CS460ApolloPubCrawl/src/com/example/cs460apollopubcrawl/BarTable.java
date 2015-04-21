/**
 * Class that accesses the bar table from a MySQL database
 * and creates an ArrayList of Bar object that is used to store all records
 * from the table into the ArrayList
 * 
 * Input: all records from the bar table from a MySQL server database
 * Output: an ArrayList of type Bar
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



public class BarTable {	

	public ArrayList<Bar> barArray = new ArrayList();
	
	/**
	 * Empty constructor used to call on the methods of this calls
	 */
	public BarTable(){}
	
	/**
	 * Accessor method that accesses the MySQL database and gets all the records
	 * from the bar table and puts all the records into an ArrayList of type Bar and returns it.
	 * @return barArray
	 */
	public ArrayList<Bar> getData(){
		String URL = "jdbc:mysql://frodo.bentley.edu:3306/cs460apollo";
        String username = "cs460Apollo";
        String password = "cs460apollo";
    	String barID, locationName, barLatitude, barLongitude, address, phone,
    					type, closestStop;

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
        
        //for each record in Bar table add Bar object to ArrayList
        while (result.next()) {
        barID = result.getString("Bar_ID");        
        locationName = result.getString("LocationName");
        barLatitude = result.getString("BarLatitude");    
        barLongitude = result.getString("BarLongitude");        
        address = result.getString("Address");     
        phone = result.getString("Phone");        
        type = result.getString("Type");  
        closestStop = result.getString("ClosestStop");
   
        Bar bar = new Bar(barID, locationName, barLatitude, barLongitude,
        					address, phone, type, closestStop);
        
        barArray.add(bar);
        }     
       
        //clean up
        con.close();
    }
    catch (SQLException e) {
    	Log.e("BarTable.java Failure", "SQL Error");
      e.printStackTrace();
    }
		return barArray;
	}
}
