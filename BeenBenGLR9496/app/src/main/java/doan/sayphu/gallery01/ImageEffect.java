package doan.sayphu.gallery01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;


import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

import doan.sayphu.transformations.BlurTransformation;
import doan.sayphu.transformations.ColorFilterTransformation;
import doan.sayphu.transformations.GrayscaleTransformation;
import doan.sayphu.transformations.RoundedCornersTransformation;
import doan.sayphu.transformations.gpu.BrightnessFilterTransformation;
import doan.sayphu.transformations.gpu.ContrastFilterTransformation;
import doan.sayphu.transformations.gpu.SepiaFilterTransformation;
import doan.sayphu.transformations.gpu.SketchFilterTransformation;
import doan.sayphu.transformations.gpu.ToonFilterTransformation;
import doan.sayphu.transformations.gpu.VignetteFilterTransformation;

/**
 * Created by USER on 5/14/2017.
 */

public class ImageEffect extends AppCompatActivity implements ImageEffectCallBack{

    BlendFragment blendFragment;
    String typeBlend;
    String image_current_path;
    ImageView imageView;
    private BottomBar mBottomBar;
    private FragNavController fragNavController;

    private final int TAB_FIRST = FragNavController.TAB1;
    private final int TAB_SECOND = FragNavController.TAB2;
    private final int TAB_THIRD = FragNavController.TAB3;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.effect_image);

        imageView = (ImageView)findViewById(R.id.image_view);
        image_current_path = getIntent().getStringExtra("image_path");

        blendFragment = BlendFragment.newInstance(0);
        final List<Fragment> fragments = new ArrayList<>(3);
        fragments.add(blendFragment);
        fragments.add(FrameFragment.newInstance(0));
        fragments.add(CropFragment.newInstance(0));

        Glide.with(getApplicationContext()).load("file://" + image_current_path)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);

        fragNavController = new FragNavController(getSupportFragmentManager(),R.id.frame_effect,fragments);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.useDarkTheme();
        mBottomBar.setActiveTabColor(Color.YELLOW);



        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.bottomBarItemOne:
                        fragNavController.switchTab(TAB_FIRST);
                        Bundle bundle = new Bundle();
                        bundle.putString("params", image_current_path);
                        blendFragment.setArguments(bundle);
                        break;
                    case R.id.bottomBarItemSecond:
                        fragNavController.switchTab(TAB_SECOND);
                        break;
                    case R.id.bottomBarItemThird:
                        fragNavController.switchTab(TAB_THIRD);
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    fragNavController.clearStack();
                }
            }
        });

        imageView = (ImageView)findViewById(R.id.image_view);
    }
    @Override
    public void onBackPressed() {
        if (fragNavController.getCurrentStack().size() > 1) {
            fragNavController.pop();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }
    public void onMsgFromFragToMain (String sender, String BlendType)
    {
        if (sender.equals("BLEND-FRAG")) {
            typeBlend = BlendType;
            switch (typeBlend)
            {
                case "None":
                    Glide.with(getApplicationContext()).
                            load("file://" + image_current_path)
                            .into(imageView);
                    break;
                case "Grayscale":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .bitmapTransform(new GrayscaleTransformation(getApplicationContext()))
                            .into(imageView);
                    break;
                case "RoundedCorners":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .bitmapTransform(new RoundedCornersTransformation(getApplicationContext(), 30, 0,
                                    RoundedCornersTransformation.CornerType.BOTTOM))
                            .into(imageView);
                    break;
                case "Blur":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .bitmapTransform(new BlurTransformation(getApplicationContext(), 25, 1))
                            .into(imageView);
                    break;
                case "Toon":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .bitmapTransform(new ToonFilterTransformation(getApplicationContext()))
                            .into(imageView);
                    break;
                case "Sepia":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .bitmapTransform(new SepiaFilterTransformation(getApplicationContext()))
                            .into(imageView);
                    break;
                case "Contrast":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .bitmapTransform(new ContrastFilterTransformation(getApplicationContext(), 2.0f))
                            .into(imageView);
                    break;
                case "Sketch":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .bitmapTransform(new SketchFilterTransformation(getApplicationContext()))
                            .into(imageView);
                    break;
                case "Brightness":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .bitmapTransform(new BrightnessFilterTransformation(getApplicationContext(), 0.5f))
                            .into(imageView);
                    break;
                case "Vignette":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .bitmapTransform(new VignetteFilterTransformation(getApplicationContext(), new PointF(0.5f, 0.5f),
                                    new float[] { 0.0f, 0.0f, 0.0f }, 0f, 0.75f))
                            .into(imageView);
                    break;
                /*case "Plus":
                    holder.image_effect.setImageResource(R.mipmap.ic_plus_math);
                    break;*/
            }
        }
    }


}
