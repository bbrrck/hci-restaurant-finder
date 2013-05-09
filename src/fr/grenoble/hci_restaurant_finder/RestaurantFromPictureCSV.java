package fr.grenoble.hci_restaurant_finder;

import java.util.ArrayList;
import java.util.Scanner;

import android.content.res.AssetManager;
import android.util.Log;

public class RestaurantFromPictureCSV implements RestaurantFromPicture {

	private ArrayList<Restaurant> restaurants = null;
	
	private String csvFilename = "csv/restaurants.csv";
	
	public RestaurantFromPictureCSV(AssetManager assets) {
		getRestaurantsFromCSV(assets);
	}
	
	/**
	 * parses restaurants from CSV with the following form, with the address in quotes:
	 * ID,Name,Address,Telephone,Website,Rating,Coordinate1,Coordinate2,Mon,Tue,Wed,Thu,Fri,Sat,Sun,Photo
	 */
	private void getRestaurantsFromCSV(AssetManager assets) {
		try {
			Scanner csvReader = new Scanner(assets.open(csvFilename));
			String firstLine = csvReader.nextLine();
			while (csvReader.hasNext()) {
				String line = csvReader.nextLine();
				String[] fields = line.split("\t");
				
				int id = Integer.parseInt(fields[0]);
				String name = fields[1];
				String streetAddress = fields[2];
				String phone = fields[3];
				String URL = fields[4];
				double rating = Double.parseDouble(fields[5]);
				double latitude = Double.parseDouble(fields[6]);
				double longitude = Double.parseDouble(fields[7]);
				String[] hours = new String[7];
				for (int i = 0; i < 7; i++) {
					hours[i] = fields[i+8];
				}
				String photo = fields[15];
				ArrayList<String> photos = null;
				String map = null;
				
				Restaurant restaurant = new Restaurant(id, name, 
						phone, streetAddress, latitude, longitude, hours, null,
						photo, photos, rating, URL);
				
				restaurants.add(restaurant);
			}
		} catch (Exception e) {
			Log.e("getRestaurantFromPictureCSV", e.getCause().getLocalizedMessage());			
		}
		
	}
	
	/**
	 * Parses the CSV file for the picture corresponding to the restaurant id
	 * and returns an instance of the restaurant
	 */
	public Restaurant getRestaurantFromPicture(ResultPicture pic) {
		for (Restaurant r : restaurants) {
			if (r.getID() == pic.getRestaurantID()) {
				return r;
			}
		}
		return null;
	} 
	
	public ArrayList<Restaurant> getRestaurants() {return restaurants;}
	

}
