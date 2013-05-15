package fr.grenoble.hci_restaurant_finder;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoader extends AsyncTask<String,Void,Void>{
	
	private Context context = null;
	private String error = null;
	private ImageView image;
	private Bitmap bm;
	
	public ImageLoader(Context context, ImageView image)
	{
	    this.context = context; 
	    this.image = image;
	}

	@Override
	protected Void doInBackground(String... params) {
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		bm = BitmapFactory.decodeFile("/sdcard/foodpics/"+params[0], options);
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) 
	{
	    
		image.setImageBitmap(bm);
	}

}
