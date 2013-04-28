package fr.grenoble.hci_restaurant_finder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

public class ResultsFragment extends Fragment{

	private TableLayout layoutCategories;
	private ImageView buttonStar;
	private ImageView buttonCategories;
	private LinearLayout layoutKeywords;
	private ImageView buttonKeywords;
	private ImageView buttonRefresh;
	private byte selected;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
	 	View inflatedView = inflater.inflate(R.layout.fragment_results, container, false);
	 	
	 	layoutCategories = (TableLayout) inflatedView.findViewById(R.id.LayoutCategories);
	 	layoutKeywords = (LinearLayout) inflatedView.findViewById(R.id.LayoutKeywords);
	 	
	 	buttonStar = (ImageView) inflatedView.findViewById(R.id.buttonStar);
	 	
	 	buttonStar.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if (selected!=1) selected=1;
				else selected = 0;
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
				
			}
		});

	 	
        return inflatedView;
    }
	
	public void refreshPictureButtons() {
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
	
}
