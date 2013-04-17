package fr.grenoble.hci_restaurant_finder;

import java.util.List;

import android.location.Address;
import android.telephony.PhoneNumberUtils;

public class Restaurant {

	private int id;
	
	private String name;
	private String phoneNumber;
	private Address address;
	/**
	 * image resource for the map and restaurant picture
	 */
	private int map;
	private int picture;
	private int[] foodPictures;
	
	private double rating;
	
	private String URL;
	
	public Restaurant(int id, String name, String phone, Address address, 
			int map, int picture, List<Integer> foodPictures, 
			double rating, String URL) {
		
		this.id = id;
		this.name = name;
		this.phoneNumber = PhoneNumberUtils.formatNumber(phone);
		this.address = address;
		this.map = map;
		this.picture = picture;
		this.rating = rating;
		this.URL = URL;
		this.foodPictures = new int[foodPictures.size()];
		for (int i = 0; i < this.foodPictures.length; i++)
			this.foodPictures[i] = foodPictures.get(i);		
	}
	
	public int getID() { return id; }
	public void setID(int id) { this.id = id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getPhoneNumber() { return phoneNumber; }
	public void setPhoneNumber(String number) { this.phoneNumber = PhoneNumberUtils.formatNumber(number); }
	
	public Address getAddress() { return address; }
	public void setAddress(Address address) { this.address = address; }
	
	public int getMapId() { return map; }
	public void setMapId(int id) { this.map = id; }
	
	public int getPictureId() { return picture; }
	public void setPictureId(int id) { this.picture = id; }
	
	public double getRating() { return rating; }
	public void setRating(double rating) { this.rating = rating; }
	
	public String getURLString() { return URL; }
	public void setURLString(String url) { this.URL = url; }
	
}
