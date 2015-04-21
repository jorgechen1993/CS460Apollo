/**
 * Created by Jorge Chen on 04/17/15
 */

package com.example.cs460apollopubcrawl;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.Toast;
import android.database.*;
import android.database.sqlite.*;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.CameraUpdateFactory;

public class PubCrawl extends Activity implements OnClickListener {

	//instantiating the widgets in the activity
	public GoogleMap myMap;
	private TabHost tabs;
	private static final float zoom = 14.0f;
	
	private Button navigationButton;
	private Button routingButton; 
	
	private ListView listView;
	private SimpleAdapter adapter;
	
	//variables used for SQLite
	private ContentValues values;
	private Cursor cursor;
	
	//variables used in the option menu
	final int PICK1 = Menu.FIRST + 1;
	final int PICK2 = Menu.FIRST + 2;
	
	//array lists
	private ArrayList<Bar> sqlBarArray; //this array will hold the records from the sqlite databse
	private ArrayList<Bar> pubCrawlArray; //this array will hold the pubs selected by the user
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pub_crawl);
		
		//initialize and reference the buttons
		navigationButton = (Button)findViewById(R.id.navigationButton);
		navigationButton.setOnClickListener(this);
		
		routingButton = (Button)findViewById(R.id.routingButton);
		routingButton.setOnClickListener(this);
		
		//instantiating the pub crawl array
		pubCrawlArray = new ArrayList<Bar>();
		sqlBarArray = new ArrayList<Bar>();
		
		//initialize the listView
		listView = (ListView)findViewById(R.id.pubList);
		
	    //setting up the tab host
        tabs=(TabHost)findViewById(R.id.tabhost);        
		tabs.setup();				
		TabHost.TabSpec spec;
		
		
		// Initialize a TabSpec for tab1 and add it to the TabHost
		spec=tabs.newTabSpec("tag1");	//create new tab specification
		spec.setContent(R.id.tab1);    //add tab view content
		spec.setIndicator("Pub List");    //put text on tab
		tabs.addTab(spec);             //put tab in TabHost container
		
		//get bars
		getBars();
		
		
		pubCrawlArray = sqlBarArray;	//make the pubCrawlArray equal sqlBarArray the first time the activity is executed 
		
	    //Put the bars inside the list
		populateList();
  	   

		//-------------------------------------------------------------------------------------
		
		// Initialize a TabSpec for tab2 and add it to the TabHost
		spec=tabs.newTabSpec("tag2");		//create new tab specification
		spec.setContent(R.id.tab2);			//add view tab content
		spec.setIndicator("Map");
		tabs.addTab(spec);					//put tab in TabHost container
		
		myMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		
		//add markers to map
		addMarkers(myMap);
		
		myMap.setOnMarkerClickListener( 
        		new OnMarkerClickListener() {
        			
        			public boolean onMarkerClick(Marker m) {      				
        				m.showInfoWindow();		
        				return true;
        			}
        			
        		}
        );
	}
	
	/**
	 * OnClickListener event execution of navigation and routing buttons
	 * MAXIM AND DEREK IMPLEMENT YOUR METHOD FOR ROUTING AND NAVIGATION HERE
	 */
	public void onClick(View v) {
		
		switch (v.getId()){
		
		case R.id.navigationButton:
			//TODO FUNCTION FOR NAVIGATION
		break;
		
		case R.id.routingButton:
			//TODO FUNCTION FOR ROUTING
		break;
		}
	}
	
	/**
	 * Method that populates the list that displays the crawl
	 */
	private void populateList(){
	    final ArrayList<HashMap<String,String>> listHash = new ArrayList<HashMap<String,String>>();
	    
	    for (int i = 0; i < pubCrawlArray.size();i++){
	    	Bar bar = pubCrawlArray.get(i);
	    	String barName, address, phone;
	    	barName = bar.getLocationName();
	    	address = bar.getAddress();
	    	phone = bar.getPhone();
	    	
	    	HashMap<String,String> temp = new HashMap<String,String>();
	    	temp.put("name", barName);
	    	temp.put("address", address);
	    	temp.put("phone", phone);
	    	listHash.add(temp);
	    }
	    
	    adapter = new SimpleAdapter (
	    	this,listHash, R.layout.custom_row_view, 
	    	new String[] {"name","address","phone"},
	    	new int[] {R.id.text1,R.id.text2,R.id.text3}
	    );
	    listView.setAdapter(adapter);	  
	}
	
	/**
	 * Adds markers to the map
	 * @param map
	 */
	private void addMarkers(GoogleMap map) {
		
		//markers for pubs
		for (int i = 0; i < sqlBarArray.size();i++){
	    	Bar bar = sqlBarArray.get(i);
	    	String barName, address, latitude, longitude;
	    	barName = bar.getLocationName();
	    	address = bar.getAddress();
	    	latitude = bar.getBarLatitude();
	    	longitude = bar.getBarLongitude();
	    	
	    	double barLat = Double.parseDouble(latitude);
	    	double barLong = Double.parseDouble(longitude);
	    	
			map.addMarker(new MarkerOptions()
	        .position(new LatLng(barLat,barLong))
	        .title(barName)
	        .snippet(address));      
	    	
	    }
		
		//marker for t-stop
		Intent i = getIntent();
		String tstop = i.getStringExtra("userTstop");
		String lat = null;
		String lon = null;
		
		//SQLite used to get latitude and longitude of Tstop
		SQLiteDatabase db = openOrCreateDatabase("cs460apollo.db", Context.MODE_PRIVATE, null);
		Cursor cursor2;
		
		cursor2 = db.rawQuery("SELECT StopLatitude, StopLongitude FROM tstop " +
			    "WHERE StopName =" + "'" + tstop + "'",null);
		
		while (cursor2.moveToNext()) {
		lat = cursor2.getString(cursor2.getColumnIndex("StopLatitude"));
		lon = cursor2.getString(cursor2.getColumnIndex("StopLongitude"));
		}
		
		db.close(); //ends of SQLite
		
		//add tstop marker to the map
		double latitude = Double.parseDouble(lat);
		double longitude = Double.parseDouble(lon);
		
		Marker tstopMarker = map.addMarker(new MarkerOptions()
        .position(new LatLng(latitude,longitude))
        .title(tstop)
        .snippet("You Start Here!"));
        
		//format the appearance of the tstop marker and shows info window
		tstopMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		tstopMarker.showInfoWindow();      
		
		//set the view on the map to center the tstop coordinates
		myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoom));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add(0, PICK1, Menu.NONE, "Randomize Crawl");
		MenuItem item2 = menu.add(0, PICK2, Menu.NONE, "Shortest Path Crawl");
		return true;
	}

	/**
	 * MAXIM AND DEREK IMPLEMENT YOUR METHOD TO CALCULATED SHORTEST PATH HERE
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemID = item.getItemId();  //get id of menu item picked	   	    
	    
	    switch (itemID) {
	    case PICK1 :
	    	randomizeList();
	    	populateList();
	    	return true;
	    case PICK2 :
	    	//TODO METHOD PLACE HERE TO CALCULATE SHORTEST PATH AND THEN CALL THE "populateList()" METHOD AT THE END
	    	return true;
	    default: super.onOptionsItemSelected(item);
	    }
	   		   
	    return false;
	}
	
	/**
	 * Method that randomizes the list of pubs in the pub crawl
	 */
	public void randomizeList(){
		int crawl_size = pubCrawlArray.size();
		ArrayList<Bar> tempArray = new ArrayList<Bar>();
		ArrayList<Bar> tempArray2 = new ArrayList<Bar>();		
		
		//Place all bars in the sqlBarArray into tempArray
		tempArray = pubCrawlArray;
		
		//Generate a random list of bars
		for (int i = 0; i < crawl_size; i++) {
			
			int random = (int)((Math.random() * (tempArray.size() - 1 )) + 0);
			
			tempArray2.add(tempArray.get(random));
			tempArray.remove(random);									
		}
		
		pubCrawlArray = tempArray2;
	}
	
	/**
	 * Get bars from SQLite Database
	 */
	public void getBars(){
		//get intent data
		Intent intent = getIntent();
		ArrayList<String> userCrawl = intent.getStringArrayListExtra("userPubCrawlList"); //gets the array of all the bar names from previous activity
				
		Log.i("PubCrawl.java userCrawl size", Integer.toString(userCrawl.size()));
				
				
		//SQLite used to access table and enter records into an array	
		for(int i = 0; i < userCrawl.size(); i++){		//for every element in the array do a query to grab Bar record from SQlite and put it into an array of Bar type
			
				String pubName = userCrawl.get(i);
				
				Log.i("PubCrawl.java userCrawl", pubName);
					
				SQLiteDatabase db = openOrCreateDatabase("cs460apollo.db", Context.MODE_PRIVATE, null);
						
			    cursor = db.rawQuery("SELECT * FROM bar " +
			    		"WHERE LocationName =" + "'" + pubName + "'",null);
					    			   
			    while (cursor.moveToNext()) {
			    	String barID = cursor.getString(cursor.getColumnIndex("Bar_ID"));
			    	String locationName = cursor.getString(cursor.getColumnIndex("LocationName"));
			    	String barLat = cursor.getString(cursor.getColumnIndex("BarLatitude"));
			    	String barLong = cursor.getString(cursor.getColumnIndex("BarLongitude"));
			    	String address = cursor.getString(cursor.getColumnIndex("Address"));
			    	String phone = cursor.getString(cursor.getColumnIndex("Phone"));
			    	String type = cursor.getString(cursor.getColumnIndex("Type"));
			    	String closestStop = cursor.getString(cursor.getColumnIndex("ClosestStop"));
			    	
			    	Log.i("PubCrawl.java", locationName + " had been added");
			    	
			    	sqlBarArray.add(new Bar(barID, locationName, barLat, barLong, address, phone, type, closestStop));
					    	
			    };
			    db.close(); //end of SQLite
				}
	}
	
	//---------------------------------------------------------------------------------------------------------------------------
	
	/** MAXIM AND DEREK: ANY ADDITIONAL METHODS WRITE THEM BELOW THIS LINE. HERE IS WHAT I CAN DO TO HELP YOU GUYS:
	 *  THE "sqlBarArray" ARRAYLIST AUTOMATICALLY HOLDS ALL THE BARS IN THE PUB CRAWL, WHAT YOU WILL WANT TO DO IS GET 
	 *  THE LAT AND LONG FROM THE ARRAY BY CREATING A BAR OBJECT AND STORING
	 *  CONTENTS OF EACH ELEMENT INTO A SEPARATE BAR OBJECT. YOU WILL HAVE TO USE LOOPS BECAUSE ONLY ONE BAR OBJECT CAN BE
	 *  CREATED AT A TIME. ONCE YOU HAVE EXTRACTED THE COORDINATES CREATE METHOD THAT WILL CALCULATE SHORTEST PATH AND DO NAVIGATION
	 *  AND ROUTING WHERE I SET UP THE ONCLICKLISTENER FOR YOU GUYS. ANOTHER THING, DONT TOUCH THE "sqlBarArray" ARRAY, THIS ARRAY
	 *  IS ONLY USED TO STORE THE RECORDS FROM THE SQLITE TABLE, ANY NEW PUB CRAWL LIST SAVE IT IN THE "pubCrawlArray". GOOD LUCK.
	*/
}
