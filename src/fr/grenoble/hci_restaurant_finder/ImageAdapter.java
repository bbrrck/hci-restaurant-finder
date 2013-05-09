package fr.grenoble.hci_restaurant_finder;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
    
    public void starItem(int position, PictureSearcher pictureSearcher) {
    	if (pictures.get(position).isStarred()) {
    		pictures.get(position).unStar();
    		pictureSearcher.unStarPicture(pictures.get(position));
    	}
    	else {
    		pictures.get(position).star();
    		pictureSearcher.starPicture(pictures.get(position));
    	}
    }
    
    public void starmode (boolean b) {
    	starmode = b;
    }
    
    public boolean getStarMode() {
    	return starmode;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid = convertView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater= (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid=inflater.inflate(R.layout.resultimage, parent, false);
        }
        
        ImageView starimage = (ImageView) grid.findViewById(R.id.starImageView);
        if (starmode) {
        	if (pictures.get(position).isStarred())
        	starimage.setImageResource(R.drawable.taste_star_selected_35);
        	else
        		starimage.setImageResource(R.drawable.taste_star_35);
        	starimage.setVisibility(0);
        }
        else {
        	starimage.setVisibility(8);
        }

        //imageView.setImageResource(mThumbIds[position]);
        return grid;
    }
}
