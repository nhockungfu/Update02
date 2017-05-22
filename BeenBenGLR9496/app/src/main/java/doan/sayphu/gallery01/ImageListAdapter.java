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

import static doan.sayphu.gallery01.ImageListPager.appBarLayout;


public class ImageListAdapter extends PagerAdapter {

    private ArrayList<String> imageList;
    private LayoutInflater inflater;
    private Context context;
    private Activity activity;
    private boolean scrollToolBar;
    PhotoView photoView;

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
    public Object instantiateItem(final ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.activity_image, view, false);

        assert imageLayout != null;
        photoView = (PhotoView) imageLayout.findViewById(R.id.image_view);

        photoView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                handlingScrollToolbar();
                photoView.setRotationBy(90);
                //handlingScrollSackBar(view);

            }

        });

        //photoView.setAllowParentInterceptOnEdge(false); //fix lỗi zoom trong photoView + viewPager

//        photoView.setAllowParentInterceptOnVerticalEdge(false);
//        photoView.setAllowParentInterceptOnHorizontalEdge(false);

        Glide.with(activity.getApplicationContext()).load(imageList.get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(photoView);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    private void handlingScrollToolbar() {
        if(!scrollToolBar){
            appBarLayout.setExpanded(false, true);
            scrollToolBar = true;
        }else{
            appBarLayout.setExpanded(true, true);
            scrollToolBar = false;
        }
    }

    private void handlingScrollSackBar(ViewGroup view) {
//        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View images_pager_activity = layoutInflater.inflate(R.layout.show_image_pager, view);
//        CoordinatorLayout coorLayout = (CoordinatorLayout)images_pager_activity.findViewById(R.id.coordinator_layout);
//        Snackbar snackbar = Snackbar.make(coorLayout,"test nackbar", Snackbar.LENGTH_LONG);
//        View snackbar_view = snackbar.getView();
//        TextView snackbar_text = (TextView) snackbar_view.findViewById(android.support.design.R.id.snackbar_text);
//        snackbar_text.setCompoundDrawablesWithIntrinsicBounds(0,0,android.R.drawable.ic_delete,0);
//        snackbar_text.setGravity(Gravity.CENTER);
//        snackbar.show();

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
