package fr.grenoble.hci_restaurant_finder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends FragmentActivity {
	
	ImageView locationButton;
	ImageView picturesButton;
	//FrameLayout fragmentContent;
	Fragment locatorFragment;
	Fragment resultsFragment;
	Fragment restaurantsFragment;
	
	byte selected = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		locationButton = (ImageView) findViewById(R.id.locationButton);
		picturesButton = (ImageView) findViewById(R.id.pictureButton);
		//fragmentContent = (FrameLayout) findViewById(R.id.fragment_content);
		
		locationButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				selected = 1;
				refreshPictureButtons();
			}
		});
		
		picturesButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				selected = 2;
				refreshPictureButtons();
			}
		});
		
		locatorFragment = new LocatorFragment();
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		transaction.replace(R.id.fragment_content, locatorFragment);
		transaction.addToBackStack(null);
		
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

}
