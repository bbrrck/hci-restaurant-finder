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
	
	public ArrayList<ResultPicture> search() {
		
		ArrayList<ResultPicture> results = new ArrayList<ResultPicture>();
		
		/* add starred pictures to results list	 */
		if (starred != null) {
			for (ResultPicture p : starred) {
				results.add(p);
			}
		}
		
		/* no categories, no keywords -> add everything (if not already there) */
		if ((categories == null || categories.size() == 0) && 
			(keywordsDecomposed == null || keywordsDecomposed.size() == 0)) {
			for (ResultPicture p : pictures) {
				if (results.indexOf(p) < 0) {
					results.add(p);
				}
			}
		}
		else {
			
			
			for (ResultPicture p : pictures) {
				boolean toAdd = true;
				if (results.indexOf(p) < 0) {
					if (categories != null) {
						
						/* check based on categories */
						for (Category c : p.getCategories()) {
							if (!categories.contains(c)) {
								toAdd = false;
							}
						}
					}
					
					/* check based on tags */
					boolean tagMatch = false;
					for (String tag : p.getTags()) {
						if (keywordsDecomposed != null) {
							for (String keyword : keywordsDecomposed) {
								if (keyword.equalsIgnoreCase(tag)) {
									tagMatch = true;
								}
							}							
						}
						else {
							tagMatch = true;
						}
						toAdd = toAdd && tagMatch;
					}
				}
				if (toAdd) {
					results.add(p);
				}
			}
		}
		this.results = results;
		return results;
	}

	public ArrayList<ResultPicture> getPictures() { return pictures; }
	
}
