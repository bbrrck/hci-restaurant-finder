package fr.grenoble.hci_restaurant_finder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import android.content.res.AssetManager;
import android.util.Log;

/**
 * Creates the list of pictures stored in a CSV file under the following format:
 * filename,categories,tags,restaurant id
 * @author Elizabeth
 *
 */
public class PictureCreatorCSV implements PictureCreator {
	
	private ArrayList<ResultPicture> pictures;
	private RestaurantSearcher restSearcher;
	
	private String csvFilename = "csv/pictures.csv";
	
	public PictureCreatorCSV(double latitude, double longitude, double radius,
			RestaurantSearcher restSearcher, AssetManager assets) {
		this.restSearcher = restSearcher;
		pictures = new ArrayList<ResultPicture>();
		try {
			Scanner csvReader = new Scanner(assets.open(csvFilename));
			String firstLine = csvReader.nextLine();
			while(csvReader.hasNext()) {
				String line = csvReader.nextLine();
				String[] separated = split(line);
				String filename = separated[0];
				String[] rp_categories = separated[1].split(",");
				HashSet<Category> cats = parseCategories(rp_categories);
				String[] rp_tags = separated[2].split(",");
				HashSet<String> tags = parseTags(rp_tags);
				int restID = Integer.parseInt(separated[3]);
				ResultPicture pic = new ResultPicture(false, tags, cats, 
						restID, filename);
				if (restSearcher == null) {
					pictures.add(pic);
				}
				else {
					Restaurant rest = restSearcher.search(pic);
					double rlat = rest.getAddress().getLatitude();
					double rlong = rest.getAddress().getLongitude();
					if (distance(rlat, rlong, latitude, longitude) <= radius) {
						pictures.add(pic);
					}
				}
								
			}
		} catch (Exception e) {
			Log.e("PictureCreatorCSV", e.getCause().getLocalizedMessage());
		}
		
	}
	
	private String[] split(String line) {
		return line.split("\t");
	}
	
	
	private HashSet<Category> parseCategories(String[] categories) {
		HashSet<Category> result = new HashSet<Category>();
		if (categories != null) {
			for (String c : categories) {
				String cat = c.trim();
				if (cat.equalsIgnoreCase("snacks")) {
					result.add(Category.SNACKS);
				}
				else if (cat.equalsIgnoreCase("breakfast")) {
					result.add(Category.BREAKFAST);
				}
				else if (cat.equalsIgnoreCase("main dishes")) {
					result.add(Category.MAIN_DISHES);
				}
				else if (cat.equalsIgnoreCase("appetizers")) {
					result.add(Category.APPETIZERS);
				}
				else if (cat.equalsIgnoreCase("drinks")) {
					result.add(Category.DRINKS);
				}
				else if (cat.equalsIgnoreCase("fast food")) {
					result.add(Category.FAST_FOOD);
				}
				else if (cat.equalsIgnoreCase("healthy")) {
					result.add(Category.HEALTHY);					
				}
				else if (cat.equalsIgnoreCase("dessert")) {
					result.add(Category.DESSERT);
				}
			}
		}
		return result;
	}
	
	private HashSet<String> parseTags(String[] tags) {
		HashSet<String> result = new HashSet<String>();
		if (tags != null) {
			for (String t : tags) {
				result.add(t);
			}
		}
		return result;
	}
	
	private double rad(double x) {
		return x * Math.PI / 180;
	}
	
	private double distance(double lat1, double long1, double lat2, double long2) {
		double radius = 6371000;
		double dlat = rad(lat1 - lat2);
		double dlong = rad(long1 - long2);
		double a = Math.sin(dlat/2) * Math.sin(dlat/2) +
				Math.cos(rad(lat1)) * Math.cos(rad(lat2)) * Math.sin(rad(dlong/2)) * Math.sin(rad(dlong/2));
		double c = Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return radius * c;
	}
	
	public ArrayList<ResultPicture> getPictures() { return pictures; }

}
