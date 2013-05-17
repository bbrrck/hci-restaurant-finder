package fr.grenoble.hci_restaurant_finder;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.google.android.gms.maps.model.MarkerOptions;

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
		 	BitmapFactory.Options options = new BitmapFactory.Options();
			//options.inSampleSize = 2;
		 	String filename = restaurant.getPictureFilename();
			Bitmap bm = BitmapFactory.decodeFile("/sdcard/restopics/"+filename, options);
			header.setImageBitmap(bm);
			
		 	name = (TextView) inflatedView.findViewById(R.id.textViewRestaurantName);
		 	name.setText(restaurant.getName());
		 	 
		 	address = (TextView) inflatedView.findViewById(R.id.textViewRestaurantAddress);
		 	address.setText(restaurant.getAddressString());
		 	
		 	hours = (TextView) inflatedView.findViewById(R.id.textViewOpening);
		 	Calendar cal = Calendar.getInstance();
		 	int day = cal.DAY_OF_WEEK;
		 	if (day==1) day = 7;
		 	else day = day-1;
		 	hours.setText(restaurant.getHours()[day-1]);
		 	
		 	stars = new ArrayList<ImageView>();
		 	
		 	stars.add((ImageView) inflatedView.findViewById(R.id.imageViewStar1));
		 	stars.add((ImageView) inflatedView.findViewById(R.id.imageViewStar2));
		 	stars.add((ImageView) inflatedView.findViewById(R.id.imageViewStar3));
		 	stars.add((ImageView) inflatedView.findViewById(R.id.imageViewStar4));
		 	stars.add((ImageView) inflatedView.findViewById(R.id.imageViewStar5));
		 	
		 	double rating = restaurant.getRating();
		 	
		 	int i;
		 	for (i=0; i<(int)rating; i++) stars.get(i).setImageResource(R.drawable.resto_rating_star_full);
		 	if (rating-i>=0.5) stars.get(i).setImageResource(R.drawable.resto_rating_star_half);
		 	
		 	imageholder = (LinearLayout) inflatedView.findViewById(R.id.restaurantPictureHolder);
		 	final String[] tempfiles = restaurant.getPictureFilenames();
		 	for (int j=0;j<tempfiles.length;j++) {
		 		ImageView imageView = new ImageView(getActivity());
		 		LinearLayout.LayoutParams vp = 
		 		    new LinearLayout.LayoutParams(100, 100);
		 		imageView.setLayoutParams(vp);        
				options.inSampleSize = 4;
				bm = BitmapFactory.decodeFile("/sdcard/foodpics/"+tempfiles[j], options); 
				final int tempfile = j;
				imageView.setImageBitmap(bm);
				imageView.setOnClickListener(new OnClickListener(){
						public void onClick(View v) {
							Intent myIntent = new Intent(getActivity(), GalleryActivity.class);
							myIntent.putExtra("restoName", restaurant.getName());
							myIntent.putExtra("initPic", tempfile);
							myIntent.putExtra("imageFiles", tempfiles);
							startActivity(myIntent);
						}
				});
		 		imageholder.addView(imageView); 
		 	}
		 	
		 	buttonWeb = (Button) inflatedView.findViewById(R.id.buttonVisit);
		 	
		 	buttonWeb.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getURLString()));
					startActivity(myIntent);
				}
			});
		 	
		 	buttonCall = (Button) inflatedView.findViewById(R.id.buttonCall);
		 	
		 	buttonCall.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					Intent callIntent = new Intent(Intent.ACTION_DIAL);
			        callIntent.setData(Uri.parse("tel:"+restaurant.getPhoneNumber()));
			        startActivity(callIntent);
				}
			});
		 	
		 	position = new LatLng(restaurant.getCoordinates()[0], restaurant.getCoordinates()[1]);
		 	
	        return inflatedView;
	    }


	 @Override
	  public void onResume() {
		
		 mapView.onResume();
		 map = mapView.getMap();
		 map.addMarker(new MarkerOptions().position(position));
		 map.setMyLocationEnabled(true);
		 CameraPosition cp = new CameraPosition.Builder()
	        .zoom(12)
	        .target(position)
	        .build();
		 map.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
		 
	     super.onResume();
	  }

	  @Override
	  public void onPause() { 
		  
		  mapView.onPause();
		  
	     super.onPause();
	  }

}
