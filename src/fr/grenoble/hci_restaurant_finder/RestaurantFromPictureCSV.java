package fr.grenoble.hci_restaurant_finder;

public class RestaurantFromPictureCSV implements RestaurantFromPicture {

	private int restaurantID;
	
	
	public RestaurantFromPictureCSV(int restaurantID) {
		this.restaurantID = restaurantID;
	}

	/**
	 * Parses the CSV file for the picture corresponding to the restaurant id
	 * and returns an instance of the restaurant
	 */
	public Restaurant getRestaurantFromPicture() {
		//TODO actually parse the damn restaurant file
		return null;
	}

}
