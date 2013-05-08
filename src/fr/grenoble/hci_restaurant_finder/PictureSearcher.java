package fr.grenoble.hci_restaurant_finder;

import java.util.ArrayList;
import java.util.HashSet;

import android.content.res.AssetManager;

public class PictureSearcher {
	
	private double latitude;
	private double longitude;
	private double radius;
	private HashSet<ResultPicture> starred;
	private HashSet<Category> categories;
	private String keywords;

	private ArrayList<String> keywordsDecomposed;
	
	private ArrayList<ResultPicture> pictures;
	
	public PictureSearcher(double latitude, double longitude, double radius, 
			AssetManager assets) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		
		starred = new HashSet<ResultPicture>();
		categories = new HashSet<Category>();
		keywords = "";
		
		PictureCreatorCSV pictureCreator = new PictureCreatorCSV(assets);
		pictures = pictureCreator.getPictures();
	}
	
	public void starPicture(ResultPicture pic) {
		pic.star();
		starred.add(pic);		
	}
	
	public void unStarPicture(ResultPicture pic) {
		pic.unStar();
		starred.remove(pic);
	}
	
	public void addCategoryFilter(Category cat) { categories.add(cat); }
	public void removeCategoryFilter(Category cat) { categories.remove(cat); }
	
	public void addKeywords(String words) {
		if (words != null && words.matches("\\w+")) {	
			keywords = words.replaceAll("\\s+", " ").trim();			
			keywordsDecomposed = new ArrayList<String>();
			fillNGrams(keywordsDecomposed, keywords);				
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
	
	private void fillNGrams(ArrayList<String> decompList, String wordString) {
		if (wordString != null && decompList != null) {
			String[] words = wordString.split("\\s+");
			for (int i = 0; i < words.length; i++) {
				int n = words.length - i;
				for (int j = 0; j < words.length - n + 1; j++) {
					StringBuilder ngram = new StringBuilder();
					for (int k = j; k < j + n; k++) {
						ngram.append(words[k]);
						if (k < j + n - 1) {
							ngram.append(" ");
						}
					}
					decompList.add(ngram.toString());
				}
			}
		}
	}
	
	public ArrayList<ResultPicture> search() {
		
		ArrayList<ResultPicture> results = new ArrayList<ResultPicture>();
		
		for (ResultPicture p : starred) {
			results.add(p);
		}
		
		if (keywordsDecomposed != null) {
			
		}
		
		return results;
		
		//TODO SEARCH BASED ON CATEGORIES AND ATTRIBUTES OF STARRED STUFF
	}

}
