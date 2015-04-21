/**
 * Class that creates a Tstop object that will hold data about a particular Tstop
 * 
 * Created by Jorge Chen on 04/04/2015
 * Edited by Jorge Chen on 04/20/2015
 */

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
	
	/**
	 *  Class constructor that creates a Tstop object
	 *  @param tstopID		the assigned ID of the Tstop
	 *  @param stopName		the name of the Tstop
	 *  @param trasnferLocation	denotes if the Tstop is a transfer to another line
	 *  @param handiAccess		denotes if the Tstop has a handicap access
	 *  @param lineColor		the line color of the Tstop
	 *  @param stopLatitude		latitude coordinate of the Tstop
	 *  @param stopLongitude	longitude coordinate of the Tstop
	 */
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
	
	/**
	 * Mutator method that changes or sets the ID of a Tstop object
	 * @param tstopID	the new ID assigned to the Tstop object
	 */
	public void setTstopID(String tstopID){
		this.tstopID = tstopID;
	}
	
	/**
	 * Accessor method that gets the ID of a Tstop object
	 * @return tstopID
	 */
	public String getTstopID(){
		return tstopID;
	}
	
	/**
	 * Mutator method that changes or sets the name of a Tstop object
	 * @param stopName	the new name assigned to a Tstop object
	 */
	public void setStopName(String stopName){
		this.stopName = stopName;
	}
	
	/**
	 * Accessor method that gets the name of Tstop object
	 * @return stopName
	 */
	public String getStopName(){
		return stopName;
	}
	
	/**
	 * Mutator method that changes or sets the transfer location of a Tstop object
	 * @param transferLocation	the new transferLocation value of a Tstop object
	 */
	public void setTransferLocation(String transferLocation){
		this.transferLocation = transferLocation;
	}
	
	/**
	 * Accessor method that gets the value of transfer location of a Tstop object
	 * @return transferLocation
	 */
	public String getTransferLocation(){
		return transferLocation;
	}
	
	/**
	 * Mutator method that changes or sets the handicap access value of a Tstop object
	 * @param handiAcess	the new value of handiAccess assigned to a Tstop object
	 */
	public void setHandiAccess(String handiAccess){
		this.handiAccess = handiAccess;
	}
	
	/**
	 * Accessor method that gets the handicap access value of a Tstop object
	 * @return handiAccess
	 */
	public String getHandiAccess(){
		return handiAccess;
	}
	
	/**
	 * Mutator method that changes or sets the line color of a Tstop object
	 * @param lineColor	the new line color assigned to a Tstop object/
	 */
	public void setLineColor(String lineColor){
		this.lineColor = lineColor;
	}
	
	/**
	 * Accessor method that gets the line color of a Tstop object
	 * @return lineColor
	 */
	public String getLineColor(){
		return lineColor;
	}
	
	/**
	 * Mutator method that changes or sets the latitude of a Tstop object
	 * @param stopLatitude		the new latitude assigned to a Tstop object
	 */
	public void setStopLatitude(String stopLatitude){
		this.stopLatitude = stopLatitude;
	}
	
	/**
	 * Accesor method that gets the latitude of a Tstop object
	 * @return stopLatitude
	 */
	public String getStopLatitude(){
		return stopLatitude;
	}
	
	/**
	 * Mutator method that changes or sets the longitude of a Tstop object
	 * @param stopLongitude		the new longitude assined to a Tstop object
	 */
	public void setStopLongitude(String stopLongitude){
		this.stopLongitude = stopLongitude;
	}
	
	/**
	 * Accessor method that gets the longitude of a Tstop object
	 * @return stopLongitude
	 */
	public String getStopLongitude(){
		return stopLongitude;
	}
	
}
