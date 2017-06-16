package doan.sayphu.gallery01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import doan.sayphu.transformations.BlurTransformation;
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

    Bundle bundle;
    BlendFragment blendFragment;
    RF_Fragment rfFragment;
    String typeBlend;
    String image_current_path;
    ImageView imageView;
    private BottomBar mBottomBar;
    private FragNavController fragNavController;

    private final int TAB_FIRST = FragNavController.TAB1;
    private final int TAB_SECOND = FragNavController.TAB2;


    public static final int FLIP_VERTICAL = 1;
    public static final int FLIP_HORIZONTAL = 2;

    ImageView img_save;
    TextView text_save;


    Bitmap bitmap, daFlip;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.effect_image);

        img_save = (ImageView)findViewById(R.id.imageView_save);
        text_save = (TextView)findViewById(R.id.textView_save);

        Bitmap bm_save = BitmapFactory.decodeResource(this.getApplicationContext().getResources(),
                R.drawable.img_save);
        img_save.setImageBitmap(bm_save);
        text_save.setText("Save");

        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveImage();
            }
        });
        text_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveImage();
            }
        });
        imageView = (ImageView)findViewById(R.id.image_view);
        image_current_path = getIntent().getStringExtra("image_path");
        bundle = new Bundle();
        bundle.putString("params", image_current_path);

        blendFragment = BlendFragment.newInstance(0);
        blendFragment.setArguments(bundle);

        rfFragment = RF_Fragment.newInstance(0);

        final List<Fragment> fragments = new ArrayList<>(3);
        fragments.add(blendFragment);
        fragments.add(rfFragment);

        Glide.with(getApplicationContext())
                .load("file://" + image_current_path)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
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
                        break;
                    case R.id.bottomBarItemSecond:
                        fragNavController.switchTab(TAB_SECOND);
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

    public void SaveImage() {

        Bitmap finalBitmap = ((BitmapDrawable)imageView.getDrawable().getCurrent()).getBitmap();

        File file_temp = new File(image_current_path);
        String parent_name = file_temp.getParent();
        String file_name = image_current_path.substring(image_current_path.lastIndexOf("/")+1);
        String thanh_phan_ten[] = file_name.split("\\.");

        Random generator = new Random();
        int n = 1000;
        n = generator.nextInt(n);

        String file_name_final = thanh_phan_ten[0]+"_"+n+"."+thanh_phan_ten[1];
        String file_path_final = parent_name+"/"+file_name_final;


        File file = new File (file_path_final);

        if (file.exists ()) file.delete ();
        try {

            FileOutputStream out = new FileOutputStream(file);

            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            File imageFile = new File (file_path_final);
            MediaScannerConnection.scanFile(this.getApplicationContext(), new String[] { imageFile.getPath() }, new String[] { "image/jpeg","image/png" }, null);

            ImageListPager.imageList.add(file_path_final);

            ImageListPager.edit_boolean = 1;

            Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                            .asBitmap()
                            .into(imageView);

                    break;
                case "Grayscale":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .asBitmap()
                            .transform(new GrayscaleTransformation(getApplicationContext()))
                            .into(imageView);
                    break;
                case "RoundedCorners":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .asBitmap()
                            .transform(new RoundedCornersTransformation(getApplicationContext(), 30, 0,
                                    RoundedCornersTransformation.CornerType.BOTTOM))
                            .into(imageView);
                    break;
                case "Blur":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .asBitmap()
                            .transform(new BlurTransformation(getApplicationContext(), 25, 1))
                            .into(imageView);
                    break;
                case "Toon":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .asBitmap()
                            .transform(new ToonFilterTransformation(getApplicationContext()))
                            .into(imageView);
                    break;
                case "Sepia":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .asBitmap()
                            .transform(new SepiaFilterTransformation(getApplicationContext()))
                            .into(imageView);
                    break;
                case "Contrast":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .asBitmap()
                            .transform(new ContrastFilterTransformation(getApplicationContext(), 2.0f))
                            .into(imageView);
                    break;
                case "Sketch":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .asBitmap()
                            .transform(new SketchFilterTransformation(getApplicationContext()))
                            .into(imageView);
                    break;
                case "Brightness":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .asBitmap()
                            .transform(new BrightnessFilterTransformation(getApplicationContext(), 0.5f))
                            .into(imageView);
                    break;
                case "Vignette":
                    Glide.with(getApplicationContext())
                            .load("file://" + image_current_path)
                            .asBitmap()
                            .transform(new VignetteFilterTransformation(getApplicationContext(), new PointF(0.5f, 0.5f),
                                    new float[] { 0.0f, 0.0f, 0.0f }, 0f, 0.75f))
                            .into(imageView);
                    break;
                /*case "Plus":
                    holder.image_effect.setImageResource(R.mipmap.ic_plus_math);
                    break;*/
            }
        }
        else{
            if(sender.equals("ROTATE-FLIP-FRAG")){
//                imageView.setDrawingCacheEnabled(true);
//                Bitmap bitmap1 = imageView.getDrawingCache();
                Bitmap bitmap1 = ((BitmapDrawable)imageView.getDrawable().getCurrent()).getBitmap();
                /*Bitmap bitmap1 = ((GlideBitmapDrawable)imageView.getDrawable()).getBitmap();*/
                typeBlend = BlendType;
                switch (typeBlend) {
                    case "ROTATE":
                        rotate02(90);
                        //imageView.setImageBitmap(rotateBitmap(bitmap1, 90));
                        //imageView.setRotation(imageView.getRotation() + 90);

                        break;
                    case "FLIP":
                        imageView.setImageBitmap(flipImage(bitmap1,2));
                        break;
                }
            }
        }
    }

    private byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void rotate(float degree) {
        final RotateAnimation rotateAnim = new RotateAnimation(0.0f, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnim.setDuration(0);
        rotateAnim.setFillAfter(true);
        imageView.startAnimation(rotateAnim);
    }

    void rotate02(float x)
    {
        Bitmap bitmapOrg = ((BitmapDrawable)imageView.getDrawable().getCurrent()).getBitmap();

        int width = bitmapOrg.getWidth();

        int height = bitmapOrg.getHeight();


        int newWidth = 200;

        int newHeight  = 200;

        // calculate the scale - in this case = 0.4f

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();

        //matrix.postScale(scaleWidth, scaleHeight);
        matrix.postRotate(x);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,width, height, matrix, true);

        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageBitmap(resizedBitmap);
    }

    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
        Canvas canvas = new Canvas(rotatedBitmap);
        canvas.drawBitmap(original, 5.0f, 0.0f, null);

        return rotatedBitmap;
    }

    public Bitmap flipImage(Bitmap src, int type) {
        // create new matrix for transformation
        Matrix matrix = new Matrix();
        // if vertical
        if(type == FLIP_VERTICAL) {
            // y = y * -1
            matrix.preScale(1.0f, -1.0f);
        }
        // if horizonal
        else if(type == FLIP_HORIZONTAL) {
            // x = x * -1
            matrix.preScale(-1.0f, 1.0f);
            // unknown type
        } else {
            return null;
        }

        // return transformed image
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

}
