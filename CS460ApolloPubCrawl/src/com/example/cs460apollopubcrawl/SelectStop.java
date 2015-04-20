package com.example.cs460apollopubcrawl;

import java.util.ArrayList;

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
import android.widget.Toast;
import android.database.*;
import android.database.sqlite.*;


public class SelectStop extends Activity implements OnItemSelectedListener {
	
	private ImageView subImage;
	public int menuItem;
	private Button buttonGo;
	private ContentValues values;
	private Cursor cursor;
	public String menuItemSelected;
	public ArrayList<String> tstops;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_stop);
		
		subImage = (ImageView)findViewById(R.id.image);
		buttonGo = (Button)findViewById(R.id.buttonGo);
		
		setImage();
		
		Intent intent = getIntent();
		String lineString = intent.getStringExtra("line_color");
		
		//SQLite access table and writing into an array
		SQLiteDatabase db = openOrCreateDatabase("cs460apollo.db", Context.MODE_PRIVATE, null);
		
		
	    cursor = db.rawQuery("SELECT DISTINCT tstop.StopName FROM tstop, bar WHERE tstop.Tstop_ID = bar.ClosestStop " +
	    		"and tstop.LineColor LIKE " + "'%" + lineString + "%'",null);
	    
	    tstops = new ArrayList<String>();
	    
	    while (cursor.moveToNext()) {
	    	String stopName = cursor.getString(cursor.getColumnIndex("StopName"));
	    	
	    	tstops.add(stopName);
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
	}

	public void onNothingSelected(AdapterView<?> parent) {
	}
	
	public void setImage(){
		Intent intent = getIntent();
		int line = intent.getIntExtra("line_color_num" , 0);
		
		if (line == 1) {
			subImage.setImageResource(R.drawable.green_line);
		} else if (line == 2) {
			subImage.setImageResource(R.drawable.orange_line);
		} else if (line == 3) {
			subImage.setImageResource(R.drawable.red_line);
		} else if (line == 4) {
			subImage.setImageResource(R.drawable.blue_line);
		} 
		
	}
}
