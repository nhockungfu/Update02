package doan.sayphu.gallery01;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static doan.sayphu.gallery01.PhotosActivity.FOLDER_POS_KEY;
import static doan.sayphu.gallery01.PhotosActivity.IMAGE_LIST_KEY;
import static doan.sayphu.gallery01.PhotosActivity.POS_KEY;

/**
 * Created by BEN on 26/04/2017.
 */

public class ImageListPager extends AppCompatActivity {

    public static final String IMAGE_PATH= "image_path";
    private static ViewPager mPager;
    private int currPos;
    private int currFolderPos;
    private ArrayList<String> imageList;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image_pager);


        context = this.getApplicationContext();
        Intent intent = getIntent();
        imageList = intent.getStringArrayListExtra(IMAGE_LIST_KEY);

        currPos = intent.getIntExtra(POS_KEY,0);
        currFolderPos = intent.getIntExtra(FOLDER_POS_KEY,0);
        init();
    }

    private void init() {
        mPager = (ViewPager)findViewById(R.id.image_pager);
        mPager.setAdapter(new ImageListAdapter(this,imageList,context));
        mPager.setCurrentItem(currPos, true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.to_image, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curr_id = item.getItemId();

        switch(curr_id){

            case R.id.action_edit:{
                Intent intent = new Intent(ImageListPager.this, ImageEffect.class);
                intent.putExtra(IMAGE_PATH,  imageList.get(mPager.getCurrentItem()));
                startActivity(intent);
            }break;

            case R.id.action_delete:{
                deleteMyFile(imageList.get(mPager.getCurrentItem()));
            }break;

            case R.id.action_share:{
                Intent picMessageIntent = new Intent(android.content.Intent.ACTION_SEND);
                picMessageIntent.setType("image/*");

                File downloadedPic =  new File(imageList.get(mPager.getCurrentItem()));

                picMessageIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(downloadedPic));
                //startActivity(picMessageIntent);
                startActivity(Intent.createChooser(picMessageIntent, "Chia sẻ hình ảnh của bạn bằng:"));

            }break;

            case R.id.action_wallpaper:{
                WallpaperManager myWallpaperManager
                        = WallpaperManager.getInstance(getApplicationContext());
                try {
                    File downloadedPic =  new File(imageList.get(mPager.getCurrentItem()));
                    myWallpaperManager.getCropAndSetWallpaperIntent(Uri.fromFile(downloadedPic));
                } catch (Exception e) {
                    //TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(this,"không đặt được...", Toast.LENGTH_SHORT);
                }
            }break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void deleteMyFile(String path){
        currPos = mPager.getCurrentItem();
        File myFile = new File(path);
        if(myFile.exists()){
            if(myFile.delete()){
                MediaScannerConnection.scanFile(context, new String[]{path}, null, null);

                String filePath = imageList.get(mPager.getCurrentItem());
                imageList.remove(currPos);
                MainActivity.al_images.get(currFolderPos).setAl_imagepath(imageList);
                mPager.getAdapter().notifyDataSetChanged();

                if(imageList.size() == 0){
                    xoaThuMuc(filePath);
                    MainActivity.al_images.remove(currFolderPos);
                    finish();
                    startActivity(new Intent(ImageListPager.this, MainActivity.class));
                }

            }else{
                showMessage("Không thể xóa ảnh!");
            }
        }else{
            showMessage("File không tồn tại!");
        }

        MediaScannerConnection.scanFile(context, new String[]{path}, null, null);
    }


    public void xoaThuMuc(String filePath){
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


    public void showMessage(String msg)
    {
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
}
