package doan.sayphu.gallery01;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.chrisbanes.photoview.PhotoView;

import doan.sayphu.transformations.ColorFilterTransformation;

/**
 * Created by USER on 5/14/2017.
 */

public class ImageEffect extends AppCompatActivity {

    AHBottomNavigation bottomNavigation;
    AHBottomNavigationItem menu1, menu2, menu3, menu4;

    String image_current_path;

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.effect_image);

        image_current_path = getIntent().getStringExtra("image_path");
        bottomNavigation = (AHBottomNavigation)findViewById(R.id.bottom_bar);
        menu1 = new AHBottomNavigationItem("Blend Colors", R.drawable.blend);
        menu2 = new AHBottomNavigationItem("Picture Frame", R.drawable.frame);
        menu3 = new AHBottomNavigationItem("Picture Crop", R.drawable.ic_crop_white_48dp);


        bottomNavigation.addItem(menu1);
        bottomNavigation.addItem(menu2);
        bottomNavigation.addItem(menu3);

        bottomNavigation.setDefaultBackgroundColor(Color.BLACK);
        bottomNavigation.setAccentColor(Color.WHITE);
        bottomNavigation.setTitleState (AHBottomNavigation.TitleState.ALWAYS_SHOW);



        imageView = (ImageView)findViewById(R.id.image_view);



        Glide.with(getApplicationContext()).load("file://" + image_current_path)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);


    }
}
