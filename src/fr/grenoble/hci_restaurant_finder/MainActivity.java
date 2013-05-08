package fr.grenoble.hci_restaurant_finder;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity {
	
	ImageView locationButton;
	ImageView picturesButton;
	//FrameLayout fragmentContent;
	LocatorFragment locatorFragment;
	ResultsFragment resultsFragment;
	Fragment restaurantsFragment;
	AssetManager assetManager;
	PictureSearcher pictureSearcher;
	RestaurantSearcher restaurantSearcher;
	
	byte selected = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		assetManager = getAssets();
		restaurantSearcher = new RestaurantSearcher(assetManager);
		
		locationButton = (ImageView) findViewById(R.id.locationButton);
		picturesButton = (ImageView) findViewById(R.id.pictureButton);
		//fragmentContent = (FrameLayout) findViewById(R.id.fragment_content);
		
		locatorFragment = new LocatorFragment();
		resultsFragment = new ResultsFragment();
		
		locationButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if (selected!=1) {
				selected = 1;
				refreshPictureButtons();
				
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.fragment_content, locatorFragment);
				//transaction.addToBackStack(null);				
				transaction.commit();
				}
			}
		});
		
		picturesButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if (selected!=2) {
				selected = 2;
				refreshPictureButtons();
				createPictureSearcher();
				resultsFragment.addPictureSearcher(pictureSearcher);
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.fragment_content, resultsFragment);
				//transaction.addToBackStack(null);				
				transaction.commit();
				}
			}
		});
		
		selected = 1;
		refreshPictureButtons();
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_content, locatorFragment);
		//transaction.addToBackStack(null);	
		transaction.commit();
	}
	
	public void refreshPictureButtons() {
		switch (selected) {
		case 0: locationButton.setImageDrawable(getResources().getDrawable(R.drawable.nav_location_240));
				picturesButton.setImageDrawable(getResources().getDrawable(R.drawable.nav_taste_240));
				break;
		case 1: locationButton.setImageDrawable(getResources().getDrawable(R.drawable.nav_location_selected_240));
				picturesButton.setImageDrawable(getResources().getDrawable(R.drawable.nav_taste_240));
				break;
		case 2: locationButton.setImageDrawable(getResources().getDrawable(R.drawable.nav_location_240));
				picturesButton.setImageDrawable(getResources().getDrawable(R.drawable.nav_taste_selected_240));
				break;
		default: break;
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private void createPictureSearcher() {
		LatLng position = locatorFragment.getPosition();
		pictureSearcher = new PictureSearcher(position.latitude,position.longitude,locatorFragment.getRadius(),assetManager);
	}
	
	protected PictureSearcher getPictureSearcher() {
		return pictureSearcher;
	}
	
	protected RestaurantSearcher getRestaurantSearcher() {
		return restaurantSearcher;
	}

	public void moveToRestaurantPage(ResultPicture pic) {
		Restaurant r = restaurantSearcher.search(pic);
		RestaurantFragment restFrag = new RestaurantFragment(r);
		
		selected = 0;
		refreshPictureButtons();
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_content, restFrag);
		//transaction.addToBackStack(null);				
		transaction.commit();
	}

}
