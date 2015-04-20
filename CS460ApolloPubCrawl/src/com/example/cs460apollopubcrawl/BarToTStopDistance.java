package com.example.cs460apollopubcrawl;

import java.io.Serializable;

public class BarToTStopDistance implements Serializable {
	
	private String tStopID;
	private String tStopName;
	private String barID;
	private String barName;
	private double distance;
	
	public BarToTStopDistance(String tStopID, String tStopName, String barID,
			String barName, double distance) {
		super();
		this.tStopID = tStopID;
		this.tStopName = tStopName;
		this.barID = barID;
		this.barName = barName;
		this.distance = distance;
	}

	public String gettStopID() {
		return tStopID;
	}

	public void settStopID(String tStopID) {
		this.tStopID = tStopID;
	}

	public String gettStopName() {
		return tStopName;
	}

	public void settStopName(String tStopName) {
		this.tStopName = tStopName;
	}

	public String getBarID() {
		return barID;
	}

	public void setBarID(String barID) {
		this.barID = barID;
	}

	public String getBarName() {
		return barName;
	}

	public void setBarName(String barName) {
		this.barName = barName;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistace(double tstopLatitude, double barLatitude, double tstopLongitude, double barLongitude) {
		this.distance = Math.sqrt(Math.pow((barLatitude - tstopLatitude), 2) + Math.pow((barLongitude - tstopLongitude),2));
	}
	
	

}
