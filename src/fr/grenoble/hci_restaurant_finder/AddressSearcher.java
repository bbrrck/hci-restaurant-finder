package fr.grenoble.hci_restaurant_finder;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

public class AddressSearcher extends AsyncTask<String,Void,Address>{
	
	private Context context = null;
	private String error = null;
	private AddressSearchCompleteListener listener;
	
	public AddressSearcher(Context context, AddressSearchCompleteListener listener)
	{
	    this.context = context; 
	    this.listener = listener;
	}

	@Override
	protected Address doInBackground(String... params) {
		
		Geocoder g = new Geocoder(context);
		List<Address> temp = null;
		
		try {
			temp = g.getFromLocationName(params[0], 1);
		} catch (IOException e) {
			error = e.getMessage();
		}
		
		if (temp!=null)
		return temp.get(0);
		
		return null;
	}
	
	protected void onPostExecute(Address add) 
	{
	    
	    if (error != null) {
	    	listener.onError(error);
	    }
	    else {
	    	listener.onTaskComplete(add);
	    }
	}
	
	public interface AddressSearchCompleteListener {
		   public void onTaskComplete(Address add);
		   public void onError(String aError);
	}

}
