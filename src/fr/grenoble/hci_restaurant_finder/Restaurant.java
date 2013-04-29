package fr.grenoble.hci_restaurant_finder;

import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.telephony.PhoneNumberUtils;

public class Restaurant {

	private int id;
	
	private String name;
	private Address address;
	
	private int[] hours;
	
	/**
	 * image resource for the map and restaurant picture
	 */
	private int map;
	private int picture;
	private int[] foodPictures;
	
	private double rating;
	
	private String URL;
	
	public Restaurant(int id, String name, String phone, String address, 
			double latitude, double longitude, int[] hours,
			int map, int picture, List<Integer> foodPictures, 
			double rating, String URL) {
		
		this.id = id;
		this.name = name;
		this.address = new Address(Locale.getDefault());
		this.address.setPhone(PhoneNumberUtils.formatNumber(phone));
		this.address.setLatitude(latitude);
		this.address.setLongitude(longitude);
		this.hours = hours;
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
	
	public String getPhoneNumber() { return address.getPhone(); }
	public void setPhoneNumber(String number) { this.address.setPhone(PhoneNumberUtils.formatNumber(number)); }
	
	public Address getAddress() { return address; }
	public void setAddress(Address address) { this.address = address; }
	
	public int[] getHours() { return hours; }
	public void setHours(int[] hours) { this.hours = hours; }
	
	public int getMapId() { return map; }
	public void setMapId(int id) { this.map = id; }
	
	public int getPictureId() { return picture; }
	public void setPictureId(int id) { this.picture = id; }
	
	public double getRating() { return rating; }
	public void setRating(double rating) { this.rating = rating; }
	
	public String getURLString() { return URL; }
	public void setURLString(String url) { this.URL = url; }
	
	public void setCoordinates(double latitude, double longitude) {
		this.address.setLatitude(latitude);
		this.address.setLongitude(longitude);
	}
	public double[] getCoordinates() { 
		return new double[] 
				{ this.address.getLatitude(), this.address.getLongitude() }; 
	}
	
}
