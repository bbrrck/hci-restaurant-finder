package fr.grenoble.hci_restaurant_finder;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

public class LocatorFragment extends Fragment implements OnSeekBarChangeListener, LocationListener{
	
	MapView mapView;
	GoogleMap map;
	Circle circle;
	SeekBar seekbarRadius;
	TextView textViewRadius;
	RelativeLayout layoutGPSOff;
	Button buttonTurnGPS;
	Button buttonGPS;
	Button buttonAddress;
	LinearLayout layoutAddress;
	EditText editTextAddress;
	Button buttonSearchAddress;
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
		 	
		 	buttonAddress = (Button) inflatedView.findViewById(R.id.buttonAddress);
		 	
		 	buttonAddress.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					layoutAddress.setVisibility(0);
					locateByGPS = false;
					removeUpdates();
				}
			});
		 	
		 	buttonGPS = (Button) inflatedView.findViewById(R.id.buttonGPS);
		 	
		 	buttonGPS.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					layoutAddress.setVisibility(8);
					locateByGPS = true;
					requestUpdates();
				}
			});
		 	
		 	locationManager = (LocationManager) getActivity().getSystemService( getActivity().LOCATION_SERVICE );

		 	
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
		
		/*CameraPosition cp = new CameraPosition.Builder()
        .target(new LatLng(location.getLatitude(),location.getLongitude()))
        .zoom(12)
        .build();
		
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cp));*/
        
        if (circle!=null) circle.setCenter(new LatLng(location.getLatitude(),location.getLongitude()));
        else {
        	circle = map.addCircle(new CircleOptions()
		     .center(new LatLng(location.getLatitude(), location.getLongitude()))
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
				
				map.setMyLocationEnabled(true);
		 	}
	}
	
	public void removeUpdates() {
		if (locationManager != null) locationManager.removeUpdates(this);
		map.setMyLocationEnabled(false);
	}

}
