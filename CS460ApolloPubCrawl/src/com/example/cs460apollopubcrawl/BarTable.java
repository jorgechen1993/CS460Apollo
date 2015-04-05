package com.example.cs460apollopubcrawl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.util.Log;



public class BarTable {
	
	private ArrayList<String> barIDArray = new ArrayList();
	private ArrayList<String> locationNameArray = new ArrayList();
	private ArrayList<String> barLatitudeArray = new ArrayList();
	private ArrayList<String> barLongitudeArray = new ArrayList();
	private ArrayList<String> addressArray = new ArrayList();
	private ArrayList<String> phoneArray = new ArrayList();
	private ArrayList<String> typeArray = new ArrayList();
	private ArrayList<String> closestStopArray = new ArrayList();
	
	public BarTable(){}
	
	public void getData(){
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
        barIDArray.add(pointer, barID);
        
        locationName = result.getString("LocationName");
        locationNameArray.add(pointer, locationName);
        
        barLatitude = result.getString("BarLatitude");
        barLatitudeArray.add(pointer, barLatitude);
        
        barLongitude = result.getString("BarLongitude");
        barLongitudeArray.add(pointer, barLongitude);
        
        address = result.getString("Address");
        addressArray.add(pointer, address);
        
        phone = result.getString("Phone");
        phoneArray.add(pointer, phone);
        
        type = result.getString("Type");
        typeArray.add(pointer, type);
        
        closestStop = result.getString("ClosestStop");
        closestStopArray.add(pointer, type);
        
        Bar bar = new Bar(barID, locationName, barLatitude, barLongitude,
        					address, phone, type, closestStop);
        
        pointer++;
        Log.i("Bar", barID + " " + locationName);
        }     
       
        //clean up
        con.close();
    }
    catch (SQLException e) {
    	Log.e("Bar", "SQL Error");
      e.printStackTrace();
    }
	} //run
	
	public ArrayList<String> getBarIDArray(){
		return barIDArray;
	}
	
	public ArrayList<String> getLocationNameArray(){
		return locationNameArray;
	}
	
	public ArrayList<String> getBarLatitudeArray(){
		return barLatitudeArray;
	}
	
	public ArrayList<String> getBarLongitudeArray(){
		return barLongitudeArray;
	}
	
	public ArrayList<String> getAddressArray(){
		return addressArray;
	}
	
	public ArrayList<String> getPhoneArray(){
		return phoneArray;
	}
	
	public ArrayList<String> getTypeArray(){
		return typeArray;
	}
	
	public ArrayList<String> getClosestStopArray(){
		return closestStopArray;
	}	
	
}
