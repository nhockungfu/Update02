package doan.sayphu.gallery01;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Created by USER on 5/14/2017.
 */

public class ImageEffect extends AppCompatActivity {

    String image_current_path;
    Button button;
    PhotoView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        button = (Button)findViewById(R.id.button);
        image_current_path = getIntent().getStringExtra("image_path");
        imageView = (PhotoView)findViewById(R.id.image_view);


        Glide.with(getApplicationContext()).load("file://" + image_current_path)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
        


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            };
        });
    }
}
