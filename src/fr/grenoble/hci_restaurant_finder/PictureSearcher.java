package fr.grenoble.hci_restaurant_finder;

import java.util.ArrayList;
import java.util.HashSet;

public class PictureSearcher {
	
	private double latitude;
	private double longitude;
	private double radius;
	private HashSet<Integer> starred;
	private HashSet<Category> categories;
	private String keywords;

	private ArrayList<String> keywordsDecomposed;
	
	private PictureFromID picFromID;
	
	public PictureSearcher(double latitude, double longitude, double radius) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		
		starred = new HashSet<Integer>();
		categories = new HashSet<Category>();
		keywords = "";
		
		picFromID = new PictureFromIDCSV();
	}
	
	public void starPicture(ResultPicture pic) {
		pic.star();
		starred.add(pic.getPictureID());		
	}
	
	public void unStarPicture(ResultPicture pic) {
		pic.unStar();
		starred.remove(pic.getPictureID());
	}
	
	public void addCategoryFilter(Category cat) { categories.add(cat); }
	public void removeCategoryFilter(Category cat) { categories.remove(cat); }
	
	public void addKeywords(String words) {
		if (words != null && words.matches("\\w+")) {	
			
			keywords = words.replaceAll("\\s+", " ").trim();
						
			keywordsDecomposed = new ArrayList<String>();
			String[] split = words.split("\\s+");
			
			//TODO split up the keywords into ngrams for keywordsDecomposed
				
		}
		
	}
	
	public void clearKeywords() {
		keywords = "";
		keywordsDecomposed = null;
	}
	
	public void setLocation(double latitude, double longitude, double radius) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
	}
	
	public ArrayList<ResultPicture> search() {
		
		ArrayList<ResultPicture> results = new ArrayList<ResultPicture>();
		
		for (int i : starred) {
			results.add(picFromID.getPictureFromID(i));
		}
		
		return results;
		
		//TODO SEARCH BASED ON CATEGORIES AND ATTRIBUTES OF STARRED STUFF
	}

}
