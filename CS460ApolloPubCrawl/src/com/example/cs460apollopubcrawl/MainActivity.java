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
	//private BarToTStopSQLHelper stopBarQuery = new BarToTStopSQLHelper(this);
	public TStopTable stop = new TStopTable();
	public BarTable bar = new BarTable();
	public ArrayList<TStop> tstopArray;
	public ArrayList<Bar> barArray;
	private ProgressBar progressBar;
	private TextView msgWorking;
	private boolean isRunning = true;
	
	//Create Handler object to handle messages placed on queue 
	Handler handler = new Handler() {
		
		public void handleMessage(Message msg) {
			progressBar.incrementProgressBy(5);
			
			if (progressBar.getProgress() == progressBar.getMax()){
				msgWorking.setText("Welcome!");	
			}
			else {
				msgWorking.setText("Working..." +
						progressBar.getProgress() +"%" );
			}
			//write message contents to log
			Log.i("Message", (String)msg.obj + " " + msg.what);
					
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        msgWorking = (TextView)findViewById(R.id.TextView01);
        
		progressBar.setMax(100);
		progressBar.setVisibility(View.VISIBLE);
		progressBar.setProgress(0);
        
        t = new Thread(background);
        t.start();
      
        
    }
    
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
		
		
		sqltstop.onCreate(db);
		sqlbar.onCreate(db);
		//stopBarQuery.onCreate(db);
		sqltstop.addTstop(tstopArray);
		sqlbar.addBar(barArray); 	
		//stopBarQuery.addRecords();
		
		try {
			//for each iteration of loop, create a Message object and place on queue
			for (int i = 0; i < 20 && isRunning; i++) {
				Thread.sleep(200);
				Message msg = handler.obtainMessage(i, "Hello");
				handler.sendMessage(msg);					
			}
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} finally {
			// end the background thread
			isRunning = false;
			
			}
			
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
