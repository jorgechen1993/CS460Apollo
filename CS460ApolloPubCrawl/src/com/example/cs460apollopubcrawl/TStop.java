package com.example.cs460apollopubcrawl;

import java.io.Serializable;

public class TStop implements Serializable {
	private String tstopID;
	private String stopName;
	private String transferLocation;
	private String handiAccess;
	private String lineColor;
	private String stopLatitude;
	private String stopLongitude;
	
	public TStop(String tstopID, String stopName, String transferLocation, String handiAccess,
					String lineColor, String stopLatitude, String stopLongitude){
		super();
		this.tstopID = tstopID;
		this.stopName = stopName;
		this.transferLocation = transferLocation;
		this.handiAccess = handiAccess;
		this.lineColor = lineColor;
		this.stopLatitude = stopLatitude;
		this.stopLongitude = stopLongitude;
	}
	
	public void setTstopID(String tstopID){
		this.tstopID = tstopID;
	}
	
	public String getTstopID(){
		return tstopID;
	}
	
	public void setStopName(String stopName){
		this.stopName = stopName;
	}
	
	public String getStopName(){
		return stopName;
	}
	
	public void setTransferLocation(String transferLocation){
		this.transferLocation = transferLocation;
	}
	
	public String getTransferLocation(){
		return transferLocation;
	}
	
	public void setHandiAccess(String handAccess){
		this.handiAccess = handAccess;
	}
	
	public String getHandiAccess(){
		return handiAccess;
	}
	
	public void setLineColor(String lineColor){
		this.lineColor = lineColor;
	}
	
	public String getLineColor(){
		return lineColor;
	}
	
	public void setStopLatitude(String stopLatitude){
		this.stopLatitude = stopLatitude;
	}
	
	public String getStopLatitude(){
		return stopLatitude;
	}
	
	public void setStopLongitude(String stopLongitude){
		this.stopLongitude = stopLongitude;
	}
	
	public String getStopLongitude(){
		return stopLongitude;
	}
	
}
