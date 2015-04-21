/**
 * Main activity that has a progress bar that displays the progress of getting all records from 
 * the tstop and bar table from the MySQL server database, inserting all records into ArrayLists, 
 * placing elements from ArrayList into a SQLite database, and starting the next activity.
 * 
 * Created by Jorge Chen on 04/02/2015
 * Modified by Jorge Chen on 04/16/2015
 * 
 */

package com.example.cs460apollopubcrawl;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.database.*;
import android.net.Uri;
import android.database.sqlite.*;

public class MainActivity extends Activity {

	private Thread t = null;
	private SQLiteDatabase db;
	private TstopSQLHelper sqltstop = new TstopSQLHelper(this);
	private BarSQLHelper sqlbar = new BarSQLHelper(this);
	public TStopTable stop = new TStopTable();
	public BarTable bar = new BarTable();
	public ArrayList<TStop> tstopArray;
	public ArrayList<Bar> barArray;
	private ProgressBar progressBar;
	private TextView msgWorking;
	private boolean isRunning = true;	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        
		progressBar.setVisibility(View.VISIBLE);
        
        t = new Thread(background);
        t.start();
      
        
    }
    
    /**
     * Background thread that grabs the records from all the tables in the MySQL database,
     * inserts records into ArrayLists, puts all contents in the ArrayList into SQLite tables,
     * sends a message to the handler of the class to advance the progress bar and starts a
     * new activity once progress bar has been filled.
     */
	private Runnable background = new Runnable() {
		public void run(){
			
		//Return tstop array from JDBM database	
		tstopArray = stop.getData();
		
		//Return bar array from JDBM database
		barArray = bar.getData();
		
		//Create SQLite database
		try {
			db = sqltstop.getWritableDatabase();
		} catch (SQLException e) {
			Log.d("SQLite", "Could not create SQLite Database");
		}
		
		//Puts the contents of the ArrayList into a SQLite database
		sqltstop.onCreate(db);
		sqlbar.onCreate(db);
		sqltstop.addTstop(tstopArray);
		sqlbar.addBar(barArray); 			
		
		//Starts next activity once the progress bar has been filled
		Intent i = new Intent(getBaseContext(),MainScreen.class);
		startActivity(i);
		
		}
	};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
}
