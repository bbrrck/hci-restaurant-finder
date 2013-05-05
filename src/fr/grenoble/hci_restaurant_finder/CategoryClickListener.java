package fr.grenoble.hci_restaurant_finder;

import android.view.View;
import android.widget.ToggleButton;

public class CategoryClickListener implements View.OnClickListener{

	Category category;
	PictureSearcher searcher;
	
	public CategoryClickListener(Category c, PictureSearcher s) {
		searcher = s;
		category = c;
	}
	@Override
	public void onClick(View v) {
		
		boolean on = ((ToggleButton) v).isChecked();
	    
	    if (on) {
	        searcher.addCategoryFilter(category);
	    } else {
	        searcher.removeCategoryFilter(category);
	    }
		
	}

}
