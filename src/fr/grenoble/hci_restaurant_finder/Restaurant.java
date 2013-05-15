package fr.grenoble.hci_restaurant_finder;

import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.telephony.PhoneNumberUtils;

public class Restaurant {

	private int id;
	
	private String name;
	private Address address;
	private String addressString;
	
	private String[] hours;
	
	/**
	 * image filenames for the map and restaurant picture
	 */
	private String map;
	private String picture;
	private String[] foodPictures;
	
	private double rating;
	
	private String URL;
	
	public Restaurant(int id, String name, String phone, String address, 
			double latitude, double longitude, String[] hours,
			String map, String picture, List<String> foodPictures, 
			double rating, String URL) {
		
		this.id = id;
		this.name = name;
		this.address = new Address(Locale.getDefault());
		this.address.setPhone(PhoneNumberUtils.formatNumber(phone));
		this.address.setLatitude(latitude);
		this.address.setLongitude(longitude);
		this.addressString = address;
		this.hours = hours;
		this.map = map;
		this.picture = picture;
		this.rating = rating;
		this.URL = URL;
		this.foodPictures = 
				new String[foodPictures == null ? 0 : foodPictures.size()];
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
	
	public String getAddressString() { return addressString; }
	
	public String[] getHours() { return hours; }
	public void setHours(String[] hours) { this.hours = hours; }
	
	public String getMapFilename() { return map; }
	public void setMapFilename(String filename) { this.map = filename; }
	
	public String getPictureFilename() { return picture; }
	public void setPictureId(String filename) { this.picture = filename; }
	
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
	
	public void setPictureFilenames(String[] filenames) { this.foodPictures = filenames; }
	public void getPictureFilenames() { return foodPictures; }
	
}
