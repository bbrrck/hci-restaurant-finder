package fr.grenoble.hci_restaurant_finder;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	
    private Context mContext;
    private ArrayList<ResultPicture> pictures;
    private boolean starmode = false;

    public ImageAdapter(Context c) {
        mContext = c;
        pictures = new ArrayList<ResultPicture>();
    }

    public int getCount() {
        return pictures.size();
    }

    public Object getItem(int position) {
        return pictures.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }
    
    public void addItem(ResultPicture pic) {
    	pictures.add(pic);
    }
    
    public void setItems(ArrayList<ResultPicture> pics) {
    	pictures = pics;
    }
    
    public void clearItems() {
    	pictures.clear();
    }
    
    public void starmode (boolean b) {
    	starmode = b;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	grid = new View(mContext);
            LayoutInflater inflater= (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid=inflater.inflate(R.layout.resultimage, parent, false);
        } else {
            grid = convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);
        return grid;
    }
}
