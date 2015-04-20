package com.example.cs460apollopubcrawl;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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



public class RandomPubCrawl extends Activity {
	
	public GoogleMap myMap;
	private TabHost tabs;
	private static final float zoom = 14.0f;
	private String startingTstop;
	private ArrayList<Bar> sqlBarArray;
	private ArrayList<Bar> pubCrawlArray;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_pub_crawl);
		
		//initialize the arrays
		sqlBarArray = new ArrayList<Bar>();
		pubCrawlArray = new ArrayList<Bar>();
		
		//Getting the initial tstop from previous activity
		Intent intent = getIntent();
		startingTstop = intent.getStringExtra("randomTstop");			
		
		//initialize the listview
		listView = (ListView)findViewById(R.id.pubList);
		
	    //setting up the tab host
        tabs=(TabHost)findViewById(R.id.tabhost);        
		tabs.setup();				
		TabHost.TabSpec spec;
		
		//get bars from SQLite
		getBars();
		
		//randomize the pub crawl
		randomizePubCrawl();
		
		//-------------------------------------------------------------------------------------
		
		// Initialize a TabSpec for tab1 and add it to the TabHost
		spec=tabs.newTabSpec("tag1");	//create new tab specification
		spec.setContent(R.id.tab1);    //add tab view content
		spec.setIndicator("Pub List");    //put text on tab
		tabs.addTab(spec);             //put tab in TabHost container
		
		//Put the bars inside the list
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
	    
	    SimpleAdapter adapter = new SimpleAdapter (
	    	this,listHash, R.layout.custom_row_view, 
	    	new String[] {"name","address","phone"},
	    	new int[] {R.id.text1,R.id.text2,R.id.text3}
	    );
	    listView.setAdapter(adapter);	    	   
				
		
		//-------------------------------------------------------------------------------------
		
		// Initialize a TabSpec for tab2 and add it to the TabHost
		spec=tabs.newTabSpec("tag2");		//create new tab specification
		spec.setContent(R.id.tab2);			//add view tab content
		spec.setIndicator("Map");
		tabs.addTab(spec);					//put tab in TabHost container
		
		//initializing and referencing of the map fragment
		myMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		//myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(42.351479, -71.081127), zoom));

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

private void addMarkers(GoogleMap map) {
		
		//markers for pubs
		for (int i = 0; i < pubCrawlArray.size();i++){
	    	Bar bar = pubCrawlArray.get(i);
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
		String lat = null;
		String lon = null;
		
		//SQLite used to get latitude and longitude of Tstop
		SQLiteDatabase db = openOrCreateDatabase("cs460apollo.db", Context.MODE_PRIVATE, null);
		Cursor cursor2;
		
		cursor2 = db.rawQuery("SELECT StopLatitude, StopLongitude FROM tstop " +
			    "WHERE StopName =" + "'" + startingTstop + "'",null);
		
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
        .title(startingTstop)
        .snippet("You Start Here!"));
        
		//format the appearance of the tstop marker and shows info window
		tstopMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		tstopMarker.showInfoWindow();      
		
		//set the view on the map to center the tstop coordinates
		myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoom));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.random_pub_crawl, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void getBars() {
		Cursor cursor;
		
		//Use SQLite to query which bars to pick
		SQLiteDatabase db = openOrCreateDatabase("cs460apollo.db", Context.MODE_PRIVATE, null);
		
	    cursor = db.rawQuery("SELECT * FROM bar INNER JOIN tstop " +
	    		"ON tstop.Tstop_ID = bar.ClosestStop" +
	    		" WHERE tstop.StopName =" + "'" + startingTstop + "'",null);	    
	    
	    while (cursor.moveToNext()) {
	    	String barID = cursor.getString(cursor.getColumnIndex("Bar_ID"));
	    	String locationName = cursor.getString(cursor.getColumnIndex("LocationName"));
	    	String barLat = cursor.getString(cursor.getColumnIndex("BarLatitude"));
	    	String barLong = cursor.getString(cursor.getColumnIndex("BarLongitude"));
	    	String address = cursor.getString(cursor.getColumnIndex("Address"));
	    	String phone = cursor.getString(cursor.getColumnIndex("Phone"));
	    	String type = cursor.getString(cursor.getColumnIndex("Type"));
	    	String closestStop = cursor.getString(cursor.getColumnIndex("ClosestStop"));
	    	
	    	Log.i("RandomPubCrawl.Java", locationName + " had been added");
	    	
	    	sqlBarArray.add(new Bar(barID, locationName, barLat, barLong, address, phone, type, closestStop));
	    	
	    };
	    db.close(); //end of SQLite
	}
	
	public void randomizePubCrawl(){
		int array_size = sqlBarArray.size();
		int crawl_size;
		ArrayList<Bar> tempArray = new ArrayList<Bar>();
		ArrayList<Bar> tempArray2 = new ArrayList<Bar>();
		
		//pick a random number for pub crawl bar numbers
		crawl_size = (int)((Math.random() * array_size) + 1);
		
		//Place all bars in the sqlBarArray into tempArray
		tempArray = sqlBarArray;
		
		//Generate a random list of bars
		for (int i = 0; i < crawl_size; i++) {
			
			int random = (int)((Math.random() * (tempArray.size() - 1 )) + 0);
			
			tempArray2.add(tempArray.get(random));
			tempArray.remove(random);									
		}
		
		pubCrawlArray = tempArray2;
	}
}
