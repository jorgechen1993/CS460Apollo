/**
 * 
 */
package com.example.cs460apollopubcrawl;

import java.io.Serializable;

/**
 * @author Jorge
 *this class does not work - remember to remove
 * test again
 */
public class BarToTStop implements Serializable {
	
	private String tStopID;
	private String tStopName;
	private String tstopLineColor;
	private String tstopLatitude;
	private String tstopLongitude;	
	private String barID;
	private String barName;
	private String barLatitude;
	private String barLongitude;

	public BarToTStop(String tStopID, String tStopName, String tstopLineColor, String tstopLatitude,
			String tstopLongitude, String barID, String barName,
			String barLatitude, String barLongitude) {
		super();
		this.tStopID = tStopID;
		this.tStopName = tStopName;
		this.tstopLineColor = tstopLineColor;
		this.tstopLatitude = tstopLatitude;
		this.tstopLongitude = tstopLongitude;
		this.barID = barID;
		this.barName = barName;
		this.barLatitude = barLatitude;
		this.barLongitude = barLongitude;
	}


	public String getTstopLineColor() {
		return tstopLineColor;
	}


	public void setTstopLineColor(String tstopLineColor) {
		this.tstopLineColor = tstopLineColor;
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

	public String getTstopLatitude() {
		return tstopLatitude;
	}

	public void setTstopLatitude(String tstopLatitude) {
		this.tstopLatitude = tstopLatitude;
	}

	public String getTstopLongitude() {
		return tstopLongitude;
	}

	public void setTstopLongitude(String tstopLongitude) {
		this.tstopLongitude = tstopLongitude;
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

	public String getBarLatitude() {
		return barLatitude;
	}

	public void setBarLatitude(String barLatitude) {
		this.barLatitude = barLatitude;
	}

	public String getBarLongitude() {
		return barLongitude;
	}

	public void setBarLongitude(String barLongitude) {
		this.barLongitude = barLongitude;
	}
	
	

}
