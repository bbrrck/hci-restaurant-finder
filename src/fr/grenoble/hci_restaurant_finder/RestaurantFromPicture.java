package fr.grenoble.hci_restaurant_finder;

import java.util.ArrayList;

public interface RestaurantFromPicture {
	
	public Restaurant getRestaurantFromPicture(ResultPicture pic);
	
	public ArrayList<Restaurant> getRestaurants();
}
