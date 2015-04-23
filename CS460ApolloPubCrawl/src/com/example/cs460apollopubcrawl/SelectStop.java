package com.example.cs460apollopubcrawl;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.database.*;
import android.database.sqlite.*;


public class SelectStop extends Activity implements OnItemSelectedListener {
	
	public int menuItem;
	private Button buttonGo;
	private Cursor cursor;
	public String menuItemSelected;
	public ArrayList<String> tstops;
	public ArrayList<TStop> mappingArray;
	
	//variables used for the map
	public GoogleMap myMap;
	private static final float zoom = 14.0f;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_stop);
		
		tstops = new ArrayList<String>();
		mappingArray = new ArrayList<TStop>();
		
		buttonGo = (Button)findViewById(R.id.buttonGo);
		
		myMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		
		Intent intent = getIntent();
		String lineString = intent.getStringExtra("line_color");
		
		//SQLite access table and writing into an array
		SQLiteDatabase db = openOrCreateDatabase("cs460apollo.db", Context.MODE_PRIVATE, null);
		
		
	    cursor = db.rawQuery("SELECT DISTINCT tstop.StopName, tstop.StopLatitude, tstop.StopLongitude FROM tstop, bar WHERE tstop.Tstop_ID = bar.ClosestStop " +
	    		"and tstop.LineColor LIKE " + "'%" + lineString + "%'",null);	    	   
	    
	    while (cursor.moveToNext()) {
	    	String stopName = cursor.getString(cursor.getColumnIndex("StopName"));
	    	String latitude = cursor.getString(cursor.getColumnIndex("StopLatitude"));
	    	String longitude = cursor.getString(cursor.getColumnIndex("StopLongitude"));
	    	
	    	//Add to ArrayList that displays the drop menu
	    	tstops.add(stopName);
	    	
	    	//add to the ArrayList that will do mapping
	    	mappingArray.add(new TStop(null,stopName,null,null,null,latitude,longitude));
	    };
	    db.close(); //end of SQLite
	    
	    
		
		Spinner dropMenu = (Spinner)findViewById(R.id.dropMenu);
		dropMenu.setOnItemSelectedListener(this);   //set listener
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tstops);
		//set resource to use for drop down view, Android supplied format
		adapter.setDropDownViewResource(
		   android.R.layout.simple_spinner_dropdown_item);  
		dropMenu.setAdapter(adapter);
		

        buttonGo.setOnClickListener(  new OnClickListener() {
   
                     public void onClick(View v) {
                        Intent i = new Intent(getBaseContext(),SelectPubs.class);
                        i.putExtra("selectedTstop", menuItemSelected);
                        startActivity(i);
                     }    
        } );
        
	}
	
	private void addMarker(GoogleMap map) {
			
	    	TStop tstop = mappingArray.get(menuItem);
	    	String name, latitude, longitude;
	    	name = tstop.getStopName();	    	
	    	latitude = tstop.getStopLatitude();
	    	longitude = tstop.getStopLongitude();
	    	
	    	double stopLat = Double.parseDouble(latitude);
	    	double stopLong = Double.parseDouble(longitude);
	    	
			Marker m = map.addMarker(new MarkerOptions()
	        .position(new LatLng(stopLat,stopLong))
	        .title(name));      
	    	
			m.showInfoWindow();
	    			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_stop, menu);
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
	
	//listener methods for callbacks 
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {		
		menuItemSelected = tstops.get(position);
		menuItem = position;
		myMap.clear();
		
		TStop tstop = mappingArray.get(position);
		String latitude, longitude;
		latitude = tstop.getStopLatitude();
		longitude = tstop.getStopLongitude();
		
    	
    	double stopLat = Double.parseDouble(latitude);
    	double stopLong = Double.parseDouble(longitude);
    	    	
    	addMarker(myMap);
    	myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(stopLat, stopLong), zoom));    	    
		
	}

	public void onNothingSelected(AdapterView<?> parent) {
	}
	
}
