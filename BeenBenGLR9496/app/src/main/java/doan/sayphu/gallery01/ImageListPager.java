package doan.sayphu.gallery01;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.io.File;
import java.util.ArrayList;

import static doan.sayphu.gallery01.PhotosActivity.FOLDER_POS_KEY;
import static doan.sayphu.gallery01.PhotosActivity.IMAGE_LIST_KEY;
import static doan.sayphu.gallery01.PhotosActivity.POS_KEY;

/**
 * Created by BEN on 26/04/2017.
 */

public class ImageListPager extends AppCompatActivity {

    Animation animation;
    public static final String IMAGE_PATH = "image_path";
    public static ViewPager mPager;
    private int currPos;
    private int currFolderPos;
    public static ArrayList<String> imageList;
    public static AppBarLayout appBarLayout; //public để thằng photoView Adapter có thể sử dụng
    public static int edit_boolean = 0;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image_pager);

        appBarLayout = (AppBarLayout) findViewById(R.id.appBar_imagePager);
        context = this.getApplicationContext();
        Intent intent = getIntent();
        imageList = intent.getStringArrayListExtra(IMAGE_LIST_KEY);
        currPos = intent.getIntExtra(POS_KEY, 0);
        currFolderPos = intent.getIntExtra(FOLDER_POS_KEY, 0);

        setToolBar();
        init();
        showStateCurrImage();

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_rotate);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setContentView(R.layout.show_image_pager);

        appBarLayout = (AppBarLayout) findViewById(R.id.appBar_imagePager);
        context = this.getApplicationContext();
        Intent intent = getIntent();
        imageList = intent.getStringArrayListExtra(IMAGE_LIST_KEY);
        currPos = intent.getIntExtra(POS_KEY, 0);
        currFolderPos = intent.getIntExtra(FOLDER_POS_KEY, 0);

        setToolBar();
        init();
        showStateCurrImage();

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_rotate);

        MainActivity.al_images.get(currFolderPos).setAl_imagepath(imageList);

        if(edit_boolean != 0){
            mPager.setCurrentItem(imageList.size()-1);
            edit_boolean = 0;
        }
        mPager.getAdapter().notifyDataSetChanged();
    }

    private void init() {
        mPager = (ViewPager) findViewById(R.id.image_pager);
        mPager.setAdapter(new ImageListAdapter(this, imageList, context));
        mPager.setCurrentItem(currPos, true);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());


        //sự kiện này thay thế cho setOnPageChangeListener.
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //không làm gì cả
            }

            @Override
            public void onPageSelected(int position) {
                showStateCurrImage();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //không làm gì cả
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(2000);
                    appBarLayout.post(new Runnable() {
                        public void run() {
                            //2 dòng này: Sroll thanh toolBar
                            //true: là hiện | false: là ẩn
                            appBarLayout.setExpanded(false, true);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void setToolBar() {

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_image);
        setSupportActionBar(toolbar);

        //tiêu đề
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showStateCurrImage() {
        int iCurrImage = mPager.getCurrentItem() + 1;
        int numberOfPhotos = imageList.size();
        getSupportActionBar().setTitle(iCurrImage + "/" + numberOfPhotos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.to_image, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        int curr_id = item.getItemId();

        switch (curr_id) {

            case R.id.action_edit: {
                Intent intent = new Intent(ImageListPager.this, ImageEffect.class);
                intent.putExtra(IMAGE_PATH, imageList.get(mPager.getCurrentItem()));
                startActivity(intent);
            }
            break;

            case R.id.action_delete: {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Bạn có muốn thoát xóa ảnh này không?");
                // thiết lập nội dung cho dialog
                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteMyFile(imageList.get(mPager.getCurrentItem()));
                        showStateCurrImage();
                    }
                });

                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // button "no" ẩn dialog đi
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.show();
                // hiển thị dialog
            }
            break;

            case R.id.action_share: {
                Intent picMessageIntent = new Intent(android.content.Intent.ACTION_SEND);
                picMessageIntent.setType("image/*");

                File downloadedPic = new File(imageList.get(mPager.getCurrentItem()));

                picMessageIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(downloadedPic));
                //startActivity(picMessageIntent);
                startActivity(Intent.createChooser(picMessageIntent, "Chia sẻ hình ảnh bằng:"));

            }
            break;

            case R.id.action_wallpaper: {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        WallpaperManager myWallpaperManager
                                = WallpaperManager.getInstance(ImageListPager.this);
                        try {

                            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                            Bitmap bitmap = BitmapFactory.
                                    decodeFile(imageList.get(mPager.getCurrentItem()), bmOptions);

                            DisplayMetrics displayMetrics = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                            int height = displayMetrics.heightPixels;
                            int width = displayMetrics.widthPixels;
                            myWallpaperManager.setBitmap(resize(bitmap, width, height));

                        } catch (Exception e) {
                            //TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;


            if (ratioMax > 1) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }


    public void deleteMyFile(String path) {
        currPos = mPager.getCurrentItem();
        File myFile = new File(path);
        if (myFile.exists()) {
            if (myFile.delete()) {
                MediaScannerConnection.scanFile(context, new String[]{path}, null, null);

                String filePath = imageList.get(mPager.getCurrentItem());
                imageList.remove(currPos);
                MainActivity.al_images.get(currFolderPos).setAl_imagepath(imageList);
                mPager.getAdapter().notifyDataSetChanged();

                if (imageList.size() == 0) {
                    xoaThuMuc(filePath);
                    MainActivity.al_images.remove(currFolderPos);
                    finish();
                    startActivity(new Intent(ImageListPager.this, MainActivity.class));
                }

            } else {
                showMessage("Không thể xóa ảnh!");
            }
        } else {
            showMessage("File không tồn tại!");
        }

        MediaScannerConnection.scanFile(context, new String[]{path}, null, null);
    }


    public void xoaThuMuc(String filePath) {
        File file = new File(filePath);
        String folderPath = file.getParent();

        File myFolder = new File(folderPath);
        myFolder.delete();
        MediaScannerConnection.scanFile(context, new String[]{folderPath}, null, null);
    }

//    public void toNextImage(int listSize){
//
//        currPos = mPager.getCurrentItem();
//
//        String filePath = imageList.get(currPos);
//
//        imageList.remove(currPos);
//
//        mPager.setAdapter(new ImageListAdapter(this, imageList, context));
//        MainActivity.al_images.get(currFolderPos).setAl_imagepath(imageList);
//
//        if(imageList.size() == 0){
//            MainActivity.al_images.remove(currFolderPos);
//
//            File file = new File(filePath);
//            String folderPath = file.getParent();
//
//            File myFolder = new File(folderPath);
//            myFolder.delete();
//
//            MediaScannerConnection.scanFile(context, new String[]{folderPath}, null, null);
//
//            this.finish();
//
//            Intent intent = new Intent();
//            intent.setClass(this.context, MainActivity.class);
//            startActivity(intent);
//        }
//        else{
//            if(currPos == imageList.size())
//                currPos--;
//            mPager.setCurrentItem(currPos,true);
//        }
//    }


    public void showMessage(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage(msg);
        builder.setCancelable(false);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}



