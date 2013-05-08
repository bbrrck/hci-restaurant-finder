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
	
	private String csvFilename = "csv/pictures.csv";
	
	public PictureCreatorCSV(double latitude, double longitude, double radius,
			AssetManager assets) {
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
				pictures.add(pic);				
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
				if (c.equalsIgnoreCase("snacks")) {
					result.add(Category.SNACKS);
				}
				else if (c.equalsIgnoreCase("breakfast")) {
					result.add(Category.BREAKFAST);
				}
				else if (c.equalsIgnoreCase("main dishes")) {
					result.add(Category.MAIN_DISHES);
				}
				else if (c.equalsIgnoreCase("appetizers")) {
					result.add(Category.APPETIZERS);
				}
				else if (c.equalsIgnoreCase("drinks")) {
					result.add(Category.DRINKS);
				}
				else if (c.equalsIgnoreCase("main dishes")) {
					result.add(Category.MAIN_DISHES);
				}
				else if (c.equalsIgnoreCase("fast food")) {
					result.add(Category.FAST_FOOD);
				}
				else if (c.equalsIgnoreCase("healthy")) {
					result.add(Category.HEALTHY);					
				}
				else if (c.equalsIgnoreCase("dessert")) {
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
	
	public ArrayList<ResultPicture> getPictures() { return pictures; }

}
