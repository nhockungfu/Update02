package doan.sayphu.gallery01;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;


public class ImageListAdapter extends PagerAdapter {

    private ArrayList<String> imageList;
    private LayoutInflater inflater;
    private Context context;
    private Activity activity;


    public ImageListAdapter(Activity acyivity, ArrayList<String> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.activity = acyivity;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.activity_image, view, false);

        assert imageLayout != null;
        final PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.image_view);

        Glide.with(activity.getApplicationContext()).load(imageList.get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.no_photo)
                .skipMemoryCache(true)
                .into(imageView);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
