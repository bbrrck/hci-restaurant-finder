package fr.grenoble.hci_restaurant_finder;
import java.util.HashSet;

/**
 * The result pictures that come up in our search
 * @author Elizabeth
 *
 */
public class ResultPicture {
	
	private boolean starred = false;
	private HashSet<String> Tags = null;
	private HashSet<Category> Categories = null;
	private int restaurantID;
	private String picFile;
	
	public ResultPicture(boolean starred, HashSet<String> Tags, 
			HashSet<Category> Categories, int restaurantID,	String picFile) {
		this.starred = starred;
		this.Tags = Tags;
		this.Categories = Categories;
		this.restaurantID = restaurantID;
		this.picFile = picFile;
	}
	
	public ResultPicture(boolean starred, Iterable<String> tags, 
			Iterable<Category> categories, int pictureID, int restaurantID,
			String picFile) {
		this.starred = starred;
		this. restaurantID = restaurantID;
		this.Tags = new HashSet<String>();
		this.Categories = new HashSet<Category>();
		this.picFile = picFile;
		
		for (String tag : tags) {
			Tags.add(tag);
		}
		
		for (Category cat : categories) {
			Categories.add(cat);
		}
		
	}
	
	public boolean isStarred() { return starred; }
	public void star() { starred = true; }
	public void unStar() { starred = false; }
	
	public HashSet<String> getTags() { return Tags; }
	public void setTags(HashSet<String> tags) { Tags = tags; }
	public void addTag(String tag) { 
		if (Tags == null) {
			Tags = new HashSet<String>();
		}
		Tags.add(tag);
	}
	public void removeTag(String tag) {
		if (Tags != null) {
			Tags.remove(tag);
		}
	}
	
	public HashSet<Category> getCategories() { return Categories; }
	public void setCategories(HashSet<Category> categories) { Categories = categories; }
	public void addCategory(Category category) { 
		if (Categories == null) {
			Categories = new HashSet<Category>();
		}
		Categories.add(category);
	}
	public void removeCategory(Category category) {
		if (Categories != null) {
			Categories.remove(category);
		}
	}
	
	public int getRestaurantID() { return restaurantID; }
	public void setRestaurantID(int ID) { restaurantID = ID; }
	
	public String getPicFile() { return picFile; }
	public void setPicFile(String filename) { picFile = filename; }
	
}
