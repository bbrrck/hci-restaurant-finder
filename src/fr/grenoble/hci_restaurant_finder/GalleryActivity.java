package fr.grenoble.hci_restaurant_finder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
 
public class GalleryActivity extends Activity implements
        AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory {
 
	BitmapFactory.Options options;
	String[] imageFiles;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.imagegallery);
 
        Intent intent = getIntent();
        imageFiles = intent.getStringArrayExtra("imageFiles");
        String restoName = intent.getStringExtra("restoName");
        int initialPic = intent.getIntExtra("initPic",0);
        
        mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
        mSwitcher.setFactory(this);
        mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));
 
        Gallery g = (Gallery) findViewById(R.id.gallery);
        g.setAdapter(new ImageAdapter(this));
        g.setOnItemSelectedListener(this);
        g.setSelection(initialPic);
        
        TextView t = (TextView) findViewById(R.id.textViewRestoName);
        t.setText(restoName);
        
        options = new BitmapFactory.Options();
    }
 
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
    	Drawable d = Drawable.createFromPath("/sdcard/foodpics/"+imageFiles[position]);
        mSwitcher.setImageDrawable(d);
    }
 
    public void onNothingSelected(AdapterView<?> parent) {
    }
 
    public View makeView() {
        ImageView i = new ImageView(this);
        i.setBackgroundColor(0xFF000000);
        i.setScaleType(ImageView.ScaleType.CENTER_CROP);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        return i;
    }
 
    private ImageSwitcher mSwitcher;
 
    public class ImageAdapter extends BaseAdapter {
        public ImageAdapter(Context c) {
            mContext = c;
        }
 
        public int getCount() {
            return imageFiles.length;
        }
 
        public Object getItem(int position) {
            return position;
        }
 
        public long getItemId(int position) {
            return position;
        }
 
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(mContext);
            options.inSampleSize = 4;
			Bitmap bm = BitmapFactory.decodeFile("/sdcard/foodpics/"+imageFiles[position], options); 
            i.setImageBitmap(bm);
            i.setAdjustViewBounds(true);
            i.setLayoutParams(new Gallery.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            return i;
        }
 
        private Context mContext;
 
    }
 
}
