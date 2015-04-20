package com.example.cs460apollopubcrawl;

import java.io.Serializable;

public class Bar implements Serializable{
	private String barID;
	private String locationName;
	private String barLatitude;
	private String barLongitude;
	private String address;
	private String phone;
	private String type;
	private String closestStop;
	
	public Bar(String barID, String locationName, String barLatitude, String barLongitude,
			String address, String phone, String type, String closestStop){
		super();
		this.barID = barID;
		this.locationName = locationName;
		this.barLatitude = barLatitude;
		this.barLongitude = barLongitude;
		this.address = address;
		this.phone = phone;
		this.type = type;
		this.closestStop = closestStop;
	}
	
	public void setBarID(String barID){
		this.barID = barID;
	}
	
	public String getBarID(){
		return barID;
	}
	
	public void setLocationName(String locationName){
		this.locationName = locationName;
	}
	
	public String getLocationName(){
		return locationName;		
	}
	
	public void setBarLatitude(String barLatitude){
		this.barLatitude = barLatitude;
	}
	
	public String getBarLatitude(){
		return barLatitude;
	}
	
	public void setBarLongitude(String barLongitude){
		this.barLongitude = barLongitude;
	}
	
	public String getBarLongitude(){
		return barLongitude;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
	
	public void setClosestStop(String closestStop){
		this.closestStop = closestStop;
	}
	
	public String getClosestStop(){
		return closestStop;
	}
	
}
