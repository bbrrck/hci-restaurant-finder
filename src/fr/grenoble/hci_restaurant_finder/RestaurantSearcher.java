package fr.grenoble.hci_restaurant_finder;

import android.content.res.AssetManager;

public class RestaurantSearcher {
	
	private RestaurantFromPicture finder;
	
	private AssetManager assets; 
	
	public RestaurantSearcher(AssetManager assets) {
		this.assets = assets;
	}
	
	public void setPicture(ResultPicture picture) {
		finder = new RestaurantFromPictureCSV(assets);
	}
	
	public Restaurant search(ResultPicture pic) {
		return finder.getRestaurantFromPicture(pic);
	}

}
