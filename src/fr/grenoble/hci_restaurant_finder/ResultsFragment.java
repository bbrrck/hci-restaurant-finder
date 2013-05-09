package fr.grenoble.hci_restaurant_finder;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.ToggleButton;

public class ResultsFragment extends Fragment{

	private GridView pictureGrid;
	private ImageAdapter pictureAdapter;
	private TableLayout layoutCategories;
	private ImageView buttonStar;
	private ImageView buttonCategories;
	private LinearLayout layoutKeywords;
	private ImageView buttonKeywords;
	private ImageView buttonRefresh;
	private byte selected;
	private Button buttonClearKeywords;
	private EditText keywords;
	private ArrayList<ToggleButton> categoryButtons;
	private PictureSearcher pictureSearcher;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
	 	View inflatedView = inflater.inflate(R.layout.fragment_results, container, false);
	 	
	 	layoutCategories = (TableLayout) inflatedView.findViewById(R.id.LayoutCategories);
	 	layoutKeywords = (LinearLayout) inflatedView.findViewById(R.id.LayoutKeywords);
	 	
	 	pictureGrid = (GridView) inflatedView.findViewById(R.id.gridView1);
	 	
	 	pictureAdapter = new ImageAdapter(getActivity());
	 	
	 	pictureGrid.setAdapter(pictureAdapter);

	 	pictureGrid.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	if (pictureAdapter.getStarMode()) {
	        		pictureAdapter.starItem(position,pictureSearcher);
	        		pictureAdapter.notifyDataSetChanged();
	        	}
	        	else {
	            ResultPicture pic = (ResultPicture) pictureAdapter.getItem(position);
	            ((MainActivity) getActivity()).moveToRestaurantPage(pic);
	        	}
	        }
	    });
	 	
	 	buttonStar = (ImageView) inflatedView.findViewById(R.id.buttonStar);
	 	
	 	buttonStar.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if (selected!=1) {
					selected=1;
					pictureAdapter.starmode(true);
					pictureAdapter.notifyDataSetChanged();
					layoutKeywords.setVisibility(8);
					layoutCategories.setVisibility(8);
				}
				else {
					selected = 0;
					pictureAdapter.starmode(false);
					pictureAdapter.notifyDataSetChanged();
				}
				refreshPictureButtons();
			}
		});
	 	
	 	buttonCategories = (ImageView) inflatedView.findViewById(R.id.buttonCategories);
	 	
	 	buttonCategories.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if (selected!=2)
				{
					layoutCategories.setVisibility(0);
					layoutKeywords.setVisibility(8);
					pictureAdapter.starmode(false);
					pictureAdapter.notifyDataSetChanged();
					selected=2;
				}
				else 
				{
					layoutCategories.setVisibility(8);
					selected = 0;
				}
				refreshPictureButtons();
			}
		});
	 	
	 	buttonKeywords = (ImageView) inflatedView.findViewById(R.id.buttonKeywords);
	 	
	 	buttonKeywords.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if (selected!=3)
				{
					layoutKeywords.setVisibility(0);
					layoutCategories.setVisibility(8);
					pictureAdapter.starmode(false);
					pictureAdapter.notifyDataSetChanged();
					selected=3;
				}
				else 
				{
					layoutKeywords.setVisibility(8);
					selected = 0;
				}
				refreshPictureButtons();
			}
		});
	 	
	 	buttonRefresh = (ImageView) inflatedView.findViewById(R.id.buttonRefresh);
	 	
	 	buttonRefresh.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				layoutKeywords.setVisibility(8);
				layoutCategories.setVisibility(8);
				selected = 0;
				refreshPictureButtons();
				pictureAdapter.starmode(false);
				pictureSearcher.addKeywords(keywords.getText().toString());
				pictureAdapter.setItems(pictureSearcher.search());
				//pictureAdapter.addItem(new ResultPicture(false,null,null,0,"0")); //dummy code
				pictureAdapter.notifyDataSetChanged();
			}
		});
	 	
	 	keywords = (EditText) inflatedView.findViewById(R.id.editTextKeywords);
	 	
	 	buttonClearKeywords = (Button) inflatedView.findViewById(R.id.buttonClearKeywords);
	 	
	 	buttonClearKeywords.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				pictureSearcher.clearKeywords();
				keywords.setText("");
			}
		});
	 	
	 	categoryButtons = new ArrayList<ToggleButton>();
	 	
	 	categoryButtons.add((ToggleButton) inflatedView.findViewById(R.id.toggleButtonSnacks));
	 	categoryButtons.add((ToggleButton) inflatedView.findViewById(R.id.toggleButtonBreakfast));
	 	categoryButtons.add((ToggleButton) inflatedView.findViewById(R.id.toggleButtonAppetizers));
	 	categoryButtons.add((ToggleButton) inflatedView.findViewById(R.id.toggleButtonDrinks));
	 	categoryButtons.add((ToggleButton) inflatedView.findViewById(R.id.toggleButtonMaindishes));
	 	categoryButtons.add((ToggleButton) inflatedView.findViewById(R.id.toggleButtonFastfood));
	 	categoryButtons.add((ToggleButton) inflatedView.findViewById(R.id.toggleButtonHealthy));
	 	categoryButtons.add((ToggleButton) inflatedView.findViewById(R.id.toggleButtonDessert));
	 	
	 	for (int i=0; i<8; i++) {
	 		Category c;
	 		switch (i) {
	 		case 0: c = Category.SNACKS; break;
	 		case 1: c = Category.BREAKFAST; break;
	 		case 2: c = Category.APPETIZERS; break;
	 		case 3: c = Category.DRINKS; break;
	 		case 4: c = Category.MAIN_DISHES; break;
	 		case 5: c = Category.FAST_FOOD; break;
	 		case 6: c = Category.HEALTHY; break;
	 		case 7: c = Category.DESSERT; break;
	 		default: c = Category.SNACKS; break;
	 		}
	 		categoryButtons.get(i).setOnClickListener(new CategoryClickListener(c,pictureSearcher));
	 	}
	 	
        return inflatedView;
    }
	
	public void refreshPictureButtons() {
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(keywords.getWindowToken(), 0);
		switch (selected) {
		case 0: buttonStar.setImageDrawable(getResources().getDrawable(R.drawable.taste_nav_star_120));
				buttonCategories.setImageDrawable(getResources().getDrawable(R.drawable.taste_nav_categories_120));
				buttonKeywords.setImageDrawable(getResources().getDrawable(R.drawable.taste_nav_keywords_120));
				break;
		case 1: buttonStar.setImageDrawable(getResources().getDrawable(R.drawable.taste_nav_star_selected_120));
				buttonCategories.setImageDrawable(getResources().getDrawable(R.drawable.taste_nav_categories_120));
				buttonKeywords.setImageDrawable(getResources().getDrawable(R.drawable.taste_nav_keywords_120));
				break;
		case 2: buttonStar.setImageDrawable(getResources().getDrawable(R.drawable.taste_nav_star_120));
				buttonCategories.setImageDrawable(getResources().getDrawable(R.drawable.taste_nav_categories_selected_120));
				buttonKeywords.setImageDrawable(getResources().getDrawable(R.drawable.taste_nav_keywords_120));
				break;
		case 3: buttonStar.setImageDrawable(getResources().getDrawable(R.drawable.taste_nav_star_120));
				buttonCategories.setImageDrawable(getResources().getDrawable(R.drawable.taste_nav_categories_120));
				buttonKeywords.setImageDrawable(getResources().getDrawable(R.drawable.taste_nav_keywords_selected_120));
				break;
		default: break;
		}
	}
	
	public void addPictureSearcher(PictureSearcher s) {
		pictureSearcher = s;
	}
	
}
