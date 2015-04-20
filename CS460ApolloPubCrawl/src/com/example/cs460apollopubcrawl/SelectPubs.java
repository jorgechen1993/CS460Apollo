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


public class SelectPubs extends Activity {

	//variables used for the dialog displaying the current selection of pubs
	private ArrayList<String> pubNamesArray;
	
	//variables used for the map
	public GoogleMap myMap;
	private TabHost tabs;
	private static final float zoom = 14.0f;
	
	//variables used for SQLite
	private ContentValues values;
	private Cursor cursor;
	
	//array lists
	private ArrayList<Bar> sqlBarArray; //this array will hold the records from the sqlite databse
	private ArrayList<Bar> pubCrawlArray; //this array will hold the pubs selected by the user
	
	private ListView listView;
	
	//variables used in the option menu
	final int PICK1 = Menu.FIRST + 1;
	final int PICK2 = Menu.FIRST + 2;
	final int PICK3 = Menu.FIRST + 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_pubs);
		
		//instantiating the pub crawl array
		pubCrawlArray = new ArrayList<Bar>();
		   	
		
		//setting up the tab host
        tabs=(TabHost)findViewById(R.id.tabhost);        
		tabs.setup();				
		TabHost.TabSpec spec;
		
	    //initializing the array
	    pubNamesArray = new ArrayList<String>();		
		
		// Initialize a TabSpec for tab1 and add it to the TabHost
		spec=tabs.newTabSpec("tag1");	//create new tab specification
		spec.setContent(R.id.tab1);    //add tab view content
		spec.setIndicator("Available Pubs");    //put text on tab
		tabs.addTab(spec);             //put tab in TabHost container
		
		//get intent data
		Intent intent = getIntent();
		String tstop = intent.getStringExtra("selectedTstop");
		
		//SQLite used to access table and enter records into an array
		SQLiteDatabase db = openOrCreateDatabase("cs460apollo.db", Context.MODE_PRIVATE, null);
		
	    cursor = db.rawQuery("SELECT * FROM bar INNER JOIN tstop " +
	    		"ON tstop.Tstop_ID = bar.ClosestStop" +
	    		" WHERE tstop.StopName =" + "'" + tstop + "'",null);
	    
	    sqlBarArray = new ArrayList<Bar>();
	    
	    while (cursor.moveToNext()) {
	    	String barID = cursor.getString(cursor.getColumnIndex("Bar_ID"));
	    	String locationName = cursor.getString(cursor.getColumnIndex("LocationName"));
	    	String barLat = cursor.getString(cursor.getColumnIndex("BarLatitude"));
	    	String barLong = cursor.getString(cursor.getColumnIndex("BarLongitude"));
	    	String address = cursor.getString(cursor.getColumnIndex("Address"));
	    	String phone = cursor.getString(cursor.getColumnIndex("Phone"));
	    	String type = cursor.getString(cursor.getColumnIndex("Type"));
	    	String closestStop = cursor.getString(cursor.getColumnIndex("ClosestStop"));
	    	
	    	Log.i("SelectPubs.Java", locationName + " had been added");
	    	
	    	sqlBarArray.add(new Bar(barID, locationName, barLat, barLong, address, phone, type, closestStop));
	    	
	    };
	    db.close(); //end of SQLite
	    
	    //Put the bars inside the list
	    final ArrayList<HashMap<String,String>> listHash = new ArrayList<HashMap<String,String>>();
	    
	    for (int i = 0; i < sqlBarArray.size();i++){
	    	Bar bar = sqlBarArray.get(i);
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
	    listView = (ListView)findViewById(R.id.list);
	    listView.setAdapter(adapter);
	    
	    //OnClickListener for the listView
	    listView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	                final int position, long id) {
	        	
	        	String barName = listHash.get(position).get("name");
	        	Toast.makeText(getBaseContext(), barName + " has been added to the crawl", Toast.LENGTH_SHORT).show();
	        	addPub(barName);	        	
	        }
	    }); //end of the OnClickListener
		
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
		String tstop = i.getStringExtra("selectedTstop");
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
		MenuItem item1 = menu.add(0, PICK1, Menu.NONE, "Create Pub Crawl");
		MenuItem item2 = menu.add(0, PICK2, Menu.NONE, "View Current Selection");
		MenuItem item3 = menu.add(0, PICK3, Menu.NONE, "Start Over");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    int itemID = item.getItemId();  //get id of menu item picked	   	    
	    
	    switch (itemID) {
	    case PICK1 :
	    	pubNamesArray.clear();
	    	
	    	if (pubCrawlArray.size() > 0) {
	    		createPubNameList();
	    		
	    		//get intent data
	    		Intent intent = getIntent();
	    		String tstop = intent.getStringExtra("selectedTstop");
	    		
	    		Intent i = new Intent(this,PubCrawl.class);
	    		i.putStringArrayListExtra("userPubCrawlList", pubNamesArray);
	    		i.putExtra("userTstop",tstop);	//this is to know which tstop was selected
	    		startActivity(i);
	    	} else {
	    		Toast.makeText(this, "The list is empty!", Toast.LENGTH_LONG).show();
	    	}
	    	return true;
	    case PICK2 :
	    	pubNamesArray.clear();	   	
		    
	    	if (pubCrawlArray.size() > 0) {
		    //populate the array with the names of the selected pubs
		    createPubNameList();
	    	}
	    	
			//create dialog
		    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SelectPubs.this);
	        LayoutInflater inflater = getLayoutInflater();
	        View convertView = (View) inflater.inflate(R.layout.dialog_frag, null);
	        alertDialog.setView(convertView);
	        ListView lv = (ListView) convertView.findViewById(R.id.crawlList);
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,pubNamesArray);
	        lv.setAdapter(adapter);
	        alertDialog.show();

	        
	    	return true;
	    case PICK3 : 
	    	pubCrawlArray.clear();
	    	Toast.makeText(this, "The list has been cleared!", Toast.LENGTH_LONG).show();
	    	
	    	return true;
	    default: super.onOptionsItemSelected(item);
	    }
	   		   
    return false;
	}
	
	/**
	 * Method that adds a selected pub from the list into a bar array
	 * @param name
	 */
	public void addPub(String name){
		//SQLite used to access table and enter records into an array
				SQLiteDatabase db = openOrCreateDatabase("cs460apollo.db", Context.MODE_PRIVATE, null);
				
			    cursor = db.rawQuery("SELECT * FROM bar " +
			    		"WHERE LocationName =" + "'" + name + "'",null);
			    
			    
			    while (cursor.moveToNext()) {
			    	String barID = cursor.getString(cursor.getColumnIndex("Bar_ID"));
			    	String locationName = cursor.getString(cursor.getColumnIndex("LocationName"));
			    	String barLat = cursor.getString(cursor.getColumnIndex("BarLatitude"));
			    	String barLong = cursor.getString(cursor.getColumnIndex("BarLongitude"));
			    	String address = cursor.getString(cursor.getColumnIndex("Address"));
			    	String phone = cursor.getString(cursor.getColumnIndex("Phone"));
			    	String type = cursor.getString(cursor.getColumnIndex("Type"));
			    	String closestStop = cursor.getString(cursor.getColumnIndex("ClosestStop"));
			    	
			    	Log.i("Crawl Pub Added", locationName + " had been added");
			    	
			    	pubCrawlArray.add(new Bar(barID, locationName, barLat, barLong, address, phone, type, closestStop));
			    	
			    };
			    db.close(); //end of SQLite
	}
	
	/**
	 * Method used to retrieve an array that will include the names of the selected pubs by the user
	 * @return
	 */
	public void createPubNameList(){
		
		for (int i = 0; i < pubCrawlArray.size(); i++){
			Bar bar = pubCrawlArray.get(i);
			String name = bar.getLocationName();
			
			pubNamesArray.add(name);
		}
	
	}
}
