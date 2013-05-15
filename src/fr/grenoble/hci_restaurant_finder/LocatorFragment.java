package fr.grenoble.hci_restaurant_finder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.grenoble.hci_restaurant_finder.AddressSearcher.AddressSearchCompleteListener;

public class LocatorFragment extends Fragment implements OnSeekBarChangeListener, LocationListener, AddressSearchCompleteListener{
	
	MapView mapView;
	GoogleMap map;
	Circle circle;
	Marker ownself;
	LatLng position;
	SeekBar seekbarRadius;
	TextView textViewRadius;
	RelativeLayout layoutGPSOff;
	Button buttonTurnGPS;
	ImageView buttonGPS;
	ImageView buttonAddress;
	LinearLayout layoutAddress;
	Button buttonLocate;
	EditText editTextAddress;
	Button buttonSearchAddress;
	AddressSearcher searcher;
	private LocationManager locationManager; 
	int radius = 300;
	boolean locateByGPS = true;
	
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
		 
	        // Inflate the layout for this fragment
		 	View inflatedView = inflater.inflate(R.layout.fragment_locator, container, false);
		 	
		 	mapView = (MapView) inflatedView.findViewById(R.id.map);
		 	mapView.onCreate(null);
		 	
		 	 try {
		 	     MapsInitializer.initialize(getActivity());
		 	 } catch (GooglePlayServicesNotAvailableException e) {
		 	     e.printStackTrace();
		 	 }
		 	
		 	seekbarRadius = (SeekBar) inflatedView.findViewById(R.id.seekBarRadius);
		 	seekbarRadius.setProgress(3);
		 	seekbarRadius.setOnSeekBarChangeListener(this);
		 	
		 	textViewRadius = (TextView) inflatedView.findViewById(R.id.textViewRadius);
		 	
		 	layoutGPSOff = (RelativeLayout) inflatedView.findViewById(R.id.LayoutGPSOff);
		 	
		 	buttonTurnGPS = (Button) inflatedView.findViewById(R.id.buttonTurnGPS);
		 	
		 	buttonTurnGPS.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
					startActivity(myIntent);
				}
			});
		 	
		 	layoutAddress = (LinearLayout) inflatedView.findViewById(R.id.LayoutAddress);
		 	
		 	buttonAddress = (ImageView) inflatedView.findViewById(R.id.buttonAddress);
		 	
		 	buttonGPS = (ImageView) inflatedView.findViewById(R.id.buttonGPS);
		 	
		 	buttonAddress.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					buttonAddress.setImageDrawable(getResources().getDrawable(R.drawable.loc_address_selected_120));
					buttonGPS.setImageDrawable(getResources().getDrawable(R.drawable.loc_gps_120));
					if (layoutAddress.getVisibility()!=0)
					{
					layoutAddress.setVisibility(0);
					locateByGPS = false;
					removeUpdates();
					}
					else {
						layoutAddress.setVisibility(8);
						InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(editTextAddress.getWindowToken(), 0);
					}
				}
			});
		 	
		 	buttonGPS.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(editTextAddress.getWindowToken(), 0);
					buttonAddress.setImageDrawable(getResources().getDrawable(R.drawable.loc_address_120));
					buttonGPS.setImageDrawable(getResources().getDrawable(R.drawable.loc_gps_selected_120));
					layoutAddress.setVisibility(8);
					locateByGPS = true;
					requestUpdates();
				}
			});
		 	
		 	buttonLocate = (Button) inflatedView.findViewById(R.id.buttonSearchAddress);
		 	
		 	buttonLocate.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(editTextAddress.getWindowToken(), 0);
					searchForAddress();
				}
			});
		 	
		 	editTextAddress = (EditText) inflatedView.findViewById(R.id.editTextAddress);
		 	
		 	locationManager = (LocationManager) getActivity().getSystemService( getActivity().LOCATION_SERVICE );

		 	buttonGPS.setImageDrawable(getResources().getDrawable(R.drawable.loc_gps_selected_120));
		 	
		 	position = new LatLng(0, 0);
		 	
	        return inflatedView;
	    }


	 @Override
	  public void onResume() {
		
		 mapView.onResume();
		 map = mapView.getMap();
		 
		 if (locateByGPS) requestUpdates();
		 
	     super.onResume();
	  }

	  @Override
	  public void onPause() {
		  
		  removeUpdates(); 
		  
		  mapView.onPause();
		  
		  circle = null;
		  
	     super.onPause();
	  }
	  
	  
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		
		if (progress>0) radius = progress*100;
		else radius = 100;
		
		if (radius<1000) textViewRadius.setText(radius+" m");
		else textViewRadius.setText((double)radius/1000+" km");
		
		if (circle!=null) circle.setRadius(radius);
		
	}


	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onLocationChanged(Location location) {
		
		changeLocationOnMap(new LatLng(location.getLatitude(), location.getLongitude()));
		
	}
	
	private void changeLocationOnMap(LatLng coordinates) {
		
		position = coordinates;
		
		CameraPosition cp = new CameraPosition.Builder()
        .zoom(12)
        .target(position)
        .build();
		
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
		
		if (ownself!=null) ownself.setPosition(position);
        
        if (circle!=null) circle.setCenter(coordinates);
        else {
        	circle = map.addCircle(new CircleOptions()
		     .center(coordinates)
		     .radius(radius)
		     .strokeColor(Color.RED)
		     .fillColor(0x55FF0000));
        }
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	public void requestUpdates() {
		 if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
		        layoutGPSOff.setVisibility(0);
		    }
		 	
		 	else {
		 		layoutGPSOff.setVisibility(8);
		 		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 12000, 0,this); 
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000, 0,this);
				
				//map.setMyLocationEnabled(true);
				ownself = map.addMarker(new MarkerOptions()
	             .position(position));
		 	}
	}
	
	public void removeUpdates() {
		if (locationManager != null) locationManager.removeUpdates(this);
		//map.setMyLocationEnabled(false);
	}

	private void searchForAddress() {
		searcher = new AddressSearcher(getActivity(),this);
		String str = editTextAddress.getText().toString();
		searcher.execute(str);
	}

	@Override
	public void onTaskComplete(Address add) {

		if (add!=null)
		changeLocationOnMap(new LatLng(add.getLatitude(),add.getLongitude()));
		
	}


	@Override
	public void onError(String aError) {
		// TODO Auto-generated method stub
		
	}
	
	public LatLng getPosition() {
		return position;
	}
	
	public int getRadius() {
		return radius;
	}

}
