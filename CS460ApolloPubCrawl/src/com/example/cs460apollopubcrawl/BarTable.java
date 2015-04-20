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
	
	public BarTable(){}
	
	public ArrayList<Bar> getData(){
		String URL = "jdbc:mysql://frodo.bentley.edu:3306/cs460apollo";
        String username = "cs460Apollo";
        String password = "cs460apollo";
    	String barID, locationName, barLatitude, barLongitude, address, phone,
    					type, closestStop;
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
    	Log.e("Bar", "SQL Error");
      e.printStackTrace();
    }
		return barArray;
	} //run
	
}
