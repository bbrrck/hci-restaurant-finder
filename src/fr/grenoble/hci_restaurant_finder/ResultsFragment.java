package fr.grenoble.hci_restaurant_finder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;

public class ResultsFragment extends Fragment{

	private TableLayout layoutCategories;
	private Button buttonStar;
	private Button buttonCategories;
	private LinearLayout layoutKeywords;
	private Button buttonKeywords;
	private Button buttonRefresh;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
	 	View inflatedView = inflater.inflate(R.layout.fragment_results, container, false);
	 	
	 	layoutCategories = (TableLayout) inflatedView.findViewById(R.id.LayoutCategories);
	 	layoutKeywords = (LinearLayout) inflatedView.findViewById(R.id.LayoutKeywords);
	 	
	 	buttonStar = (Button) inflatedView.findViewById(R.id.buttonStar);
	 	
	 	buttonStar.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				
			}
		});
	 	
	 	buttonCategories = (Button) inflatedView.findViewById(R.id.buttonCategories);
	 	
	 	buttonCategories.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if (layoutCategories.getVisibility()!=0)
				{
				layoutCategories.setVisibility(0);
				layoutKeywords.setVisibility(8);
				}
				else layoutCategories.setVisibility(8);
			}
		});
	 	
	 	buttonKeywords = (Button) inflatedView.findViewById(R.id.buttonKeywords);
	 	
	 	buttonKeywords.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if (layoutKeywords.getVisibility()!=0)
				{
				layoutKeywords.setVisibility(0);
				layoutCategories.setVisibility(8);
				}
				else layoutKeywords.setVisibility(8);
			}
		});
	 	
	 	buttonRefresh = (Button) inflatedView.findViewById(R.id.buttonRefresh);
	 	
	 	buttonRefresh.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				
			}
		});

	 	
        return inflatedView;
    }
	
}
