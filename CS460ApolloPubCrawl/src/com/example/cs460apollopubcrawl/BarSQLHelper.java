/**
 * Class that gets an ArrayList of type Bar and saves the data inside the ArrayList into
 * a SQLite Database table
 * 
 * Created by Jorge Chen 04/16/2015
 */

package com.example.cs460apollopubcrawl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class BarSQLHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "cs460apollo.db";
	public static final int DATABASE_VERSION = 4;
	public static final String TABLE_NAME = "bar";
	public static final String KEY_BAR_ID = "Bar_ID";
	public static final String KEY_LOCATION_NAME = "LocationName";
	public static final String KEY_BAR_LATITUDE = "BarLatitude";
	public static final String KEY_BAR_LONGITUDE = "BarLongitude";
	public static final String KEY_ADDRESS = "Address";
	public static final String KEY_PHONE = "Phone";
	public static final String KEY_TYPE = "Type";
	public static final String KEY_CLOSEST_STOP = "ClosestStop";
	public static final String CREATE_TABLE = "CREATE TABLE bar ("
			+ KEY_BAR_ID + " text," + KEY_LOCATION_NAME + " text," + KEY_BAR_LATITUDE + " text,"
			+ KEY_BAR_LONGITUDE + " text,"+ KEY_ADDRESS + " text," + KEY_PHONE + " text," 
			+ KEY_TYPE + " text," + KEY_CLOSEST_STOP + " text);";
	
	private ContentValues values;

	
	public BarSQLHelper(Context context) {
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
	
	/**
	 * Gets an ArrayList of type Bar and reads every single element and puts them into
	 * a SQLite Database table
	 * @param list
	 */
	public void addBar(ArrayList<Bar> list){
		
		for (int i = 0; i < list.size(); i++){
			Bar bar = list.get(i);
			String id, name, latitude, longitude, address, phone, type, closestStop;
			id = bar.getBarID();
			name = bar.getLocationName();
			latitude = bar.getBarLatitude();
			longitude = bar.getBarLongitude();
			address = bar.getAddress();
			phone = bar.getPhone();
			type = bar.getType();
			closestStop = bar.getClosestStop();
			SQLiteDatabase db = this.getWritableDatabase();
			values = new ContentValues();
			
			//adds the record into SQLite Database
			values.put(KEY_BAR_ID, id);
			values.put(KEY_LOCATION_NAME, name);
			values.put(KEY_BAR_LATITUDE, latitude);
			values.put(KEY_BAR_LONGITUDE, longitude);
			values.put(KEY_ADDRESS, address);
			values.put(KEY_PHONE, phone);
			values.put(KEY_TYPE,type);
			values.put(KEY_CLOSEST_STOP, closestStop);
			db.insert(TABLE_NAME, null, values);
			Log.d("BarSQLHelper.java SQLite", name + " added");	//added to log to check if record was added
	        db.close();
		}
	}

}



