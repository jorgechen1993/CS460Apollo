package com.example.cs460apollopubcrawl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class TstopSQLHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "cs460apollo.db";
	public static final int DATABASE_VERSION = 4;
	public static final String TABLE_NAME = "tstop";
	public static final String KEY_TSTOP_ID = "Tstop_ID";
	public static final String KEY_STOP_NAME = "StopName";
	public static final String KEY_TRANSFER_LOCATION = "TransferLocation";
	public static final String KEY_HANDI_ACCESS = "HandiAccess";
	public static final String KEY_LINECOLOR = "LineColor";
	public static final String KEY_STOP_LATITUDE = "StopLatitude";
	public static final String KEY_STOP_LONGITUDE = "StopLongitude";
	public static final String CREATE_TABLE = "CREATE TABLE tstop ("
			+ KEY_TSTOP_ID + " text," + KEY_STOP_NAME + " text," + KEY_TRANSFER_LOCATION + " text,"
			+ KEY_HANDI_ACCESS + " text,"+ KEY_LINECOLOR + " text," + KEY_STOP_LATITUDE + " text," 
			+ KEY_STOP_LONGITUDE + " text);";
	
	private ContentValues values;
	
	public TstopSQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	//called to create table
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		String sql = CREATE_TABLE;
		Log.d("SQLiteDemo", "onCreate: " + sql);
		db.execSQL(sql);
	}

	//called when database version mismatch
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		if (oldVersion >= newVersion) return;

		Log.d("SQLiteDemo", "onUpgrade: Version = " + newVersion);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	//add tstop records to the tstop SQLite table
	public void addTstop(ArrayList<TStop> list){
		
		for (int i = 0; i < list.size(); i++){
			TStop tstop = list.get(i);
			String id, name, transferLocation, handiAccess, lineColor, latitude, longitude;
			id = tstop.getTstopID();
			name = tstop.getStopName();
			transferLocation = tstop.getTransferLocation();
			handiAccess = tstop.getHandiAccess();
			lineColor = tstop.getLineColor();
			latitude = tstop.getStopLatitude();
			longitude = tstop.getStopLongitude();
			SQLiteDatabase db = this.getWritableDatabase();
			values = new ContentValues();
			
			
			values.put(KEY_TSTOP_ID, id);
			values.put(KEY_STOP_NAME, name);
			values.put(KEY_TRANSFER_LOCATION, transferLocation);
			values.put(KEY_HANDI_ACCESS, handiAccess);
			values.put(KEY_LINECOLOR, lineColor);
			values.put(KEY_STOP_LATITUDE, latitude);
			values.put(KEY_STOP_LONGITUDE,longitude);
			db.insert(TABLE_NAME, null, values);
			Log.d("SQLiteDemo", name + " added");
	        db.close();
		}
	}

}
