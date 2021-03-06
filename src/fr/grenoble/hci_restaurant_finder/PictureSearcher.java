package fr.grenoble.hci_restaurant_finder;

import java.util.ArrayList;
import java.util.HashSet;

import android.content.res.AssetManager;

public class PictureSearcher {
	
	private double latitude;
	private double longitude;
	private double radius;
	private RestaurantSearcher restaurantSearcher;
	private HashSet<ResultPicture> starred;
	private HashSet<Category> categories;
	private String keywords;
	private AssetManager assets;

	private ArrayList<String> keywordsDecomposed;
	
	private ArrayList<ResultPicture> pictures;
	private ArrayList<ResultPicture> results;
	
	public PictureSearcher(double latitude, double longitude, double radius, 
			AssetManager assets, RestaurantSearcher restSearcher) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.assets = assets;
		
		starred = new HashSet<ResultPicture>();
		categories = new HashSet<Category>();
		keywords = "";
		keywordsDecomposed = new ArrayList<String>();
		
		PictureCreatorCSV pictureCreator = new PictureCreatorCSV(latitude, 
				longitude, radius, restSearcher, assets);
		pictures = new ArrayList<ResultPicture>(pictureCreator.getPictures());
		this.results = new ArrayList<ResultPicture>(pictureCreator.getPictures());
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
		if (words != null && words.trim().length() > 0) {
			keywords = words.replaceAll(",+", " ");
			keywords = keywords.replaceAll("\\s+", " ").trim();			
			keywordsDecomposed = new ArrayList<String>();
			fillNGrams(keywordsDecomposed, keywords);				
		}
	}
	
	public void clearKeywords() {
		keywords = "";
		keywordsDecomposed = new ArrayList<String>();
	}
	
	public void setLocation(double latitude, double longitude, double radius) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		PictureCreatorCSV pictureCreator = new PictureCreatorCSV(latitude, 
				longitude, radius, null, assets);
		pictures = new ArrayList<ResultPicture>(pictureCreator.getPictures());
	}
	
	public void setRestaurantSearcher(RestaurantSearcher restSearcher) {
		this.restaurantSearcher = restSearcher;
		PictureCreatorCSV pictureCreator = new PictureCreatorCSV(latitude, 
				longitude, radius, restSearcher, assets);
		pictures = new ArrayList<ResultPicture>(pictureCreator.getPictures());
		
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
	
	/**
	 * check whether our categories are a subset of the picture's 
	 * Elizabeth fucked it up.
	 * @param pic
	 * @return
	 */
	private boolean categoryMatch(ResultPicture pic) {
		for (Category c : categories) {
			if (!pic.getCategories().contains(c)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * check whether our keywords match with any of the picture's
	 * Tibor is a silly goose
	 * @param pic
	 * @return
	 */
	private boolean tagMatch(ResultPicture pic) {		
		for (String keyword : keywordsDecomposed) {
			for (String tag : pic.getTags()) {
				if (keyword.equalsIgnoreCase(tag)) return true;
			}
		}
		return false;
	}
	
	public ArrayList<ResultPicture> search() {
		
		ArrayList<ResultPicture> results = new ArrayList<ResultPicture>();
		
		/* add starred pictures to results list	 */
		if (starred != null) {
			for (ResultPicture p : starred) {
				if (results.indexOf(p) < 0) {
					results.add(p);
				}
			}
		}
		
		// beautiful
		for (ResultPicture pic : pictures) {
			boolean cat = true;
			if (categories != null && categories.size() > 0)
				cat = categoryMatch(pic);
			
			boolean tag = true;
			if (keywordsDecomposed != null && keywordsDecomposed.size() > 0)
				tag = tagMatch(pic);
			
			if (cat && tag && results.indexOf(pic) < 0) {
				results.add(pic);
			}
		}

		this.results = results;
		return results;
	}

	public ArrayList<ResultPicture> getPictures() { return pictures; }
	
}
