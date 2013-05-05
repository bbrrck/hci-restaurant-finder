package fr.grenoble.hci_restaurant_finder;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class RestaurantFragment extends Fragment {
	
	MapView mapView;
	GoogleMap map;
	Marker restaurantMarker;
	LatLng position;
	ImageView header;
	LinearLayout imageholder;
	Restaurant restaurant;
	Button buttonWeb;
	Button buttonCall;
	TextView name;
	TextView address;
	TextView hours;
	ArrayList<ImageView> stars;
	
	public RestaurantFragment (Restaurant r) {
		restaurant = r;
	}
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
		 	View inflatedView = inflater.inflate(R.layout.fragment_restaurant, container, false);
		 	
		 	mapView = (MapView) inflatedView.findViewById(R.id.restaurantmap);
		 	mapView.onCreate(null);
		 	
		 	 try {
		 	     MapsInitializer.initialize(getActivity());
		 	 } catch (GooglePlayServicesNotAvailableException e) {
		 	     e.printStackTrace();
		 	 }
		 	
		 	header = (ImageView) inflatedView.findViewById(R.id.imageViewRestaurant);
		 	
		 	name = (TextView) inflatedView.findViewById(R.id.textViewRestaurantName);
		 	//name.setText(restaurant.getName());
		 	 
		 	address = (TextView) inflatedView.findViewById(R.id.textViewRestaurantAddress);
		 	//name.setText(restaurant.getAddress().toString());
		 	
		 	hours = (TextView) inflatedView.findViewById(R.id.textViewOpening);
		 	
		 	stars = new ArrayList<ImageView>();
		 	
		 	stars.add((ImageView) inflatedView.findViewById(R.id.imageViewStar1));
		 	stars.add((ImageView) inflatedView.findViewById(R.id.imageViewStar2));
		 	stars.add((ImageView) inflatedView.findViewById(R.id.imageViewStar3));
		 	stars.add((ImageView) inflatedView.findViewById(R.id.imageViewStar4));
		 	stars.add((ImageView) inflatedView.findViewById(R.id.imageViewStar5));
		 	
		 	
		 	imageholder = (LinearLayout) inflatedView.findViewById(R.id.restaurantPictureHolder);
		 	
		 	buttonWeb = (Button) inflatedView.findViewById(R.id.buttonVisit);
		 	
		 	buttonWeb.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					//Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
					//startActivity(myIntent);
				}
			});
		 	
		 	buttonCall = (Button) inflatedView.findViewById(R.id.buttonCall);
		 	
		 	buttonCall.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					
				}
			});
		 	
		 	//position = new LatLng(restaurant.getCoordinates()[0], restaurant.getCoordinates()[1]);
		 	
	        return inflatedView;
	    }


	 @Override
	  public void onResume() {
		
		 mapView.onResume();
		 map = mapView.getMap();
		 
	     super.onResume();
	  }

	  @Override
	  public void onPause() { 
		  
		  mapView.onPause();
		  
	     super.onPause();
	  }
	
	private void changeLocationOnMap(LatLng coordinates) {
		
		position = coordinates;
		
		CameraPosition cp = new CameraPosition.Builder()
        .zoom(12)
        .target(position)
        .build();
		
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
		
		//if (ownself!=null) ownself.setPosition(position);
        
	}

}
