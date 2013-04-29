package fr.grenoble.hci_restaurant_finder;

public class RestaurantSearcher {
	
	private RestaurantFromPicture finder;
	
	public RestaurantSearcher() {}
	
	public RestaurantSearcher(ResultPicture picture) { 
		finder = new RestaurantFromPictureCSV(picture.getPictureID());
	}
	
	public void setPicture(ResultPicture picture) {
		finder = new RestaurantFromPictureCSV(picture.getPictureID());
	}
	
	public Restaurant search() {
		return finder.getRestaurantFromPicture();
	}

}
