package com.example.cs460apollopubcrawl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class TstopSQLHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "cs460apollo";
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
			+ KEY_TSTOP_ID + " text," + "," + KEY_STOP_NAME + " text," + KEY_TRANSFER_LOCATION + " text,"
			+ KEY_HANDI_ACCESS + " text,"+ KEY_LINECOLOR + " text," + KEY_STOP_LATITUDE + " text," 
			+ KEY_STOP_LONGITUDE + " text);";
	
	private ContentValues values;
	public TStopTable tstopRecord = new TStopTable();
	private ArrayList<String> tstopIDArray = tstopRecord.geTstopIDArray();
	private ArrayList<String> stopNameArray = tstopRecord.getStopNameArray();
	private ArrayList<String> transferLocationArray = tstopRecord.getTransferLocationArray();
	private ArrayList<String> handiAccessArray = tstopRecord.getHandiAccessArray();
	private ArrayList<String> lineColorArray = tstopRecord.getLineColorArray();
	private ArrayList<String> stopLatitudeArray = tstopRecord.getStopLatitudeArray();
	private ArrayList<String> stopLongitudeArray = tstopRecord.getStopLongitudeArray();
	
	public TstopSQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	//called to create table
	@Override
	public void onCreate(SQLiteDatabase db) {
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
	
	//add bar records to the table
	public void addTstop(){
		int pointer = 0;
		int size = tstopIDArray.size();
		
		for (int i = 0; i < size; i++){
			SQLiteDatabase db = this.getWritableDatabase();
			values = new ContentValues();
			values.put(KEY_TSTOP_ID, tstopIDArray.get(pointer));
			values.put(KEY_STOP_NAME, stopNameArray.get(pointer));
			values.put(KEY_TRANSFER_LOCATION, transferLocationArray.get(pointer));
			values.put(KEY_HANDI_ACCESS, handiAccessArray.get(pointer));
			values.put(KEY_LINECOLOR, lineColorArray.get(pointer));
			values.put(KEY_STOP_LATITUDE, stopLatitudeArray.get(pointer));
			values.put(KEY_STOP_LONGITUDE,stopLongitudeArray.get(pointer));
			db.insert(TABLE_NAME, null, values);
			Log.d("SQLiteDemo", stopNameArray.get(pointer) + " added");
	        db.close();
	        pointer++;
		}
	}

}
