/**
* Class that stores information about a Bar and creates a Bar object with this information
* 
* Created by Jorge Chen on 04/04/2015
* Edited by Jorge Chen on 04/20/2015
*/

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
	
	/**
	 * Class constructor that stores the values of a Bar object
	 * @param barID			the ID of the bar
	 * @param locationName		the name of the bar
	 * @param barLatitude		the latitude coordinate of the bar
	 * @param barLongitude		the longitude coordinate of the bar
	 * @param address		the address of the bar
	 * @param phone			the phone number of the bar
	 * @param type			the type of bar
	 * @param closestStop		the ID of Tstop of which is closest to the bar
	 */
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
	
	/**
	 * Mutator method that changes or sets the ID of a Bar object
	 * @param barID		new ID that is set to a Bar object
	 */
	public void setBarID(String barID){
		this.barID = barID;
	}
	
	/**
	 * Accessor method that gets the barID from a Bar object
	 * @return barID
	 */
	public String getBarID(){
		return barID;
	}
	
	/**
	 * Mutator method that changes or sets the name of a Bar object
	 * @param locationName	the new name that is set to a Bar object
	public void setLocationName(String locationName){
		this.locationName = locationName;
	}
	
	/**
	 * Accessor method that gets the name of a Bar object
	 * @return locationName
	 */
	public String getLocationName(){
		return locationName;		
	}
	
	/**
	 * Mutator method that changes or sets the latitude of a Bar object
	 * @param barLatitude	the new latitude that is set to a Bar object
	 */
	public void setBarLatitude(String barLatitude){
		this.barLatitude = barLatitude;
	}
	
	/**
	 * Accessor method that gets the latitude of a Bar object
	 * @return barLatitude
	 */
	public String getBarLatitude(){
		return barLatitude;
	}
	
	/**
	 * Mutator method that changes or sets the longitude of a Bar object
	 * @param barLongitude	the new longitude that is set to a Bar object
	 */
	public void setBarLongitude(String barLongitude){
		this.barLongitude = barLongitude;
	}
	
	/**
	 * Accessor method that gets the longitude of a Bar object
	 * @return barLongitude
	 */
	public String getBarLongitude(){
		return barLongitude;
	}
	
	/**
	 * Mutator method that changes or sets the address of a Bar object
	 * @param address	the new address that is assigned to a Bar object
	 */
	public void setAddress(String address){
		this.address = address;
	}
	
	/**
	 * Accessor method that gets the address of a Bar object
	 * @return address
	 */
	public String getAddress(){
		return address;
	}
	
	/**
	 * Mutator method that changes or sets the phone of a Bar object
	 * @param phone		the new phone number that is assgiend to a Bar object
	 */
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	/**
	 * Accessor method that gets the phone of a Bar object
	 * @return phone
	 */
	public String getPhone(){
		return phone;
	}
	
	/**
	 * Mutator method that changes or sets the type of a Bar object
	 * @param type		the new type assigned to a Bar object
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * Accessor method that gets the type of a Bar object
	 * @return type
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * Mutator method that changes or sets the ID of the closest stop of a Bar object
	 * @param closestSTop	the new Tstop ID that is closest to the bar that is assigned to a Bar object
	 */
	public void setClosestStop(String closestStop){
		this.closestStop = closestStop;
	}
	
	/**
	 * Accessor method that gets the ID of the closest stop of a Bar object
	 * @return closestStop
	 */
	public String getClosestStop(){
		return closestStop;
	}
	
}
