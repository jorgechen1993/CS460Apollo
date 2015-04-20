package com.example.cs460apollopubcrawl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;


public class BarToTStopSQLHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "cs460apollo.db";
	public static final int DATABASE_VERSION = 4;
	public static final String TABLE_NAME = "barToTstop";
	public static final String TABLE_NAME_BAR = "bar";
	public static final String TABLE_NAME_TSTOP = "tstop";
	public static final String KEY_TSTOP_ID = "Tstop_ID";
	public static final String KEY_STOP_NAME = "StopName";
	public static final String KEY_LINECOLOR = "LineColor";
	public static final String KEY_STOP_LATITUDE = "StopLatitude";
	public static final String KEY_STOP_LONGITUDE = "StopLongitude";
	public static final String KEY_BAR_ID = "Bar_ID";
	public static final String KEY_LOCATION_NAME = "LocationName";
	public static final String KEY_BAR_LATITUDE = "BarLatitude";
	public static final String KEY_BAR_LONGITUDE = "BarLongitude";
	public static final String CREATE_TABLE = "CREATE TABLE barToTstop ("
			+ KEY_TSTOP_ID + " text," + KEY_STOP_NAME + " text," + KEY_LINECOLOR + " text," + KEY_STOP_LATITUDE + " text,"
			+ KEY_STOP_LONGITUDE + " text,"+ KEY_BAR_ID + " text," + KEY_LOCATION_NAME + " text," 
			+ KEY_BAR_LATITUDE + " text," + KEY_BAR_LONGITUDE + " text);";
	
	private Cursor cursor;
	private ContentValues values;
	private ArrayList<BarToTStop> arrayList;
	
	public BarToTStopSQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

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
	
	public void createQuery(){
		SQLiteDatabase db = this.getWritableDatabase();
	    cursor = db.rawQuery("SELECT tstop.Tstop_ID, tstop.StopName, tstop.LineColor, tstop.StopLatitude, tstop.StopLongitude, " +
	    		"bar.Bar_ID, bar.LocationName, bar.BarLatitude, bar.BarLongitude " +
	    		"FROM tstop INNER JOIN bar ON tstop.Tstop_ID = bar.ClosestStop",null);
	    
	    arrayList = new ArrayList<BarToTStop>();
	    while (cursor.moveToNext()) {
	    	String tstopID = cursor.getString(cursor.getColumnIndex("Tstop_ID"));
	    	String stopName = cursor.getString(cursor.getColumnIndex("StopName"));
	    	String linecolor = cursor.getString(cursor.getColumnIndex("LineColor"));
	    	String stopLat = cursor.getString(cursor.getColumnIndex("StopLatitude"));
	    	String stopLong = cursor.getString(cursor.getColumnIndex("StopLongitude"));
	    	String barID = cursor.getString(cursor.getColumnIndex("Bar_ID"));
	    	String barName = cursor.getString(cursor.getColumnIndex("LocationName"));
	    	String barLat = cursor.getString(cursor.getColumnIndex("BarLatitude"));
	    	String barLong = cursor.getString(cursor.getColumnIndex("BarLongitude"));
	    	
	    	arrayList.add(new BarToTStop(tstopID,stopName,linecolor, stopLat,stopLong,barID,barName,barLat,barLong));
	    };
	    db.close();
	}
	
	public void addRecords(){
		createQuery();
		
		for (int i = 0; i < arrayList.size(); i++){
			BarToTStop record = arrayList.get(i);
			String tstopID, stopName, linecolor, stopLat, stopLong, barID, barName, barLat, barLong;
			tstopID = record.gettStopID();
			stopName = record.gettStopName();
			linecolor = record.getTstopLineColor();
			stopLat = record.getTstopLatitude();
			stopLong = record.getTstopLongitude();
			barID = record.getBarID();
			barName = record.getBarName();
			barLat = record.getBarLatitude();
			barLong = record.getBarLongitude();
			SQLiteDatabase db = this.getWritableDatabase();
			values = new ContentValues();
			
			values.put(KEY_TSTOP_ID, tstopID);
			values.put(KEY_STOP_NAME, stopName);
			values.put(KEY_LINECOLOR, linecolor);
			values.put(KEY_STOP_LATITUDE, stopLat);
			values.put(KEY_STOP_LONGITUDE, stopLong);
			values.put(KEY_BAR_ID, barID);
			values.put(KEY_LOCATION_NAME, barName);
			values.put(KEY_BAR_LATITUDE,barLat);
			values.put(KEY_BAR_LONGITUDE,barLong);
			db.insert(TABLE_NAME, null, values);
			Log.d("SQLiteDemo New Table", stopName + " and " + barName + " added");
	        db.close();
			
		}
	}
}
