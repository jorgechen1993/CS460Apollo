package com.example.cs460apollopubcrawl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.database.Cursor;
import android.content.Context;
import android.content.Intent;
import android.database.*;
import android.database.sqlite.*;

public class MainScreen extends Activity implements OnClickListener {

	private Button button1;
	private Button button2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		
        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
        
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);


	}
	
    public void onClick(View v) {
    	
    	switch (v.getId()){
    	
    	case R.id.button1:
        	String tstop = getRandomTstop();
        	Log.i("MainScreen.java Tstop", tstop);
    		Intent i = new Intent(this, RandomPubCrawl.class);
    		i.putExtra("randomTstop", tstop);
        	startActivity(i);
        	break;
    	case R.id.button2:
        	Intent i2 = new Intent(this, SelectLine.class);
        	startActivity(i2);
        	break;
    	}
    	

   }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
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
	
	public String getRandomTstop() {
		Cursor cursor;
		String randTstop = null;
		
		//SQLite query used to get random tstop	
			
				
				//Log.i("PubCrawl.java userCrawl", pubName);
			
			SQLiteDatabase db = openOrCreateDatabase("cs460apollo.db", Context.MODE_PRIVATE, null);
				
			cursor = db.rawQuery("SELECT DISTINCT tstop.StopName FROM tstop, bar " +
			    		"WHERE tstop.Tstop_ID = bar.ClosestStop " +
			    		"ORDER BY RANDOM() LIMIT 1", null);
			    			   
			while (cursor.moveToNext()) {
				randTstop = cursor.getString(cursor.getColumnIndex("StopName"));
			 };
			db.close(); //end of SQLite
		
		return randTstop;
	}
}
