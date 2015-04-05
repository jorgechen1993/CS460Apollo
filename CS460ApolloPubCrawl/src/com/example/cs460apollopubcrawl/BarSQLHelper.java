package com.example.cs460apollopubcrawl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class BarSQLHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "cs460apollo";
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
			+ KEY_BAR_ID + " text," + "," + KEY_LOCATION_NAME + " text," + KEY_BAR_LATITUDE + " text,"
			+ KEY_BAR_LONGITUDE + " text,"+ KEY_ADDRESS + " text," + KEY_PHONE + " text," 
			+ KEY_TYPE + " text," + KEY_CLOSEST_STOP + " text);";
	
	private ContentValues values;
	public BarTable barRecord = new BarTable();
	private ArrayList<String> barIDArray = barRecord.getBarIDArray();
	private ArrayList<String> locationNameArray = barRecord.getLocationNameArray();
	private ArrayList<String> barLatitudeArray = barRecord.getBarLatitudeArray();
	private ArrayList<String> barLongitudeArray = barRecord.getBarLongitudeArray();
	private ArrayList<String> addressArray = barRecord.getAddressArray();
	private ArrayList<String> phoneArray = barRecord.getPhoneArray();
	private ArrayList<String> typeArray = barRecord.getTypeArray();
	private ArrayList<String> closestStopArray = barRecord.getClosestStopArray();
	
	public BarSQLHelper(Context context) {
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
	public void addBar(){
		int pointer = 0;
		int size = barIDArray.size();
		
		for (int i = 0; i < size; i++){
			SQLiteDatabase db = this.getWritableDatabase();
			values = new ContentValues();
			values.put(KEY_BAR_ID, barIDArray.get(pointer));
			values.put(KEY_LOCATION_NAME, locationNameArray.get(pointer));
			values.put(KEY_BAR_LATITUDE, barLatitudeArray.get(pointer));
			values.put(KEY_BAR_LONGITUDE, barLongitudeArray.get(pointer));
			values.put(KEY_ADDRESS, addressArray.get(pointer));
			values.put(KEY_PHONE, phoneArray.get(pointer));
			values.put(KEY_TYPE,typeArray.get(pointer));
			values.put(KEY_CLOSEST_STOP, closestStopArray.get(pointer));
			db.insert(TABLE_NAME, null, values);
			Log.d("SQLiteDemo", barIDArray.get(pointer) + " added");
	        db.close();
	        pointer++;
		}
	}

}



