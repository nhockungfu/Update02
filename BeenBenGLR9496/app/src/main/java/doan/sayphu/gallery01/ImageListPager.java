package doan.sayphu.gallery01;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
    private int folderPos;
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
        folderPos = intent.getIntExtra(FOLDER_POS_KEY,0);
        init();
    }

    private void init() {
        mPager = (ViewPager)findViewById(R.id.image_pager);
        mPager.setAdapter(new ImageListAdapter(this,imageList,context));
        mPager.setCurrentItem(currPos, true);
        //mPager.setOffscreenPageLimit(2);

//        mPager.post(new Runnable() {
//            @Override
//            public void run() {
//                //mPager.setCurrentItem(currPos);
//            }
//        });
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
        }

        return super.onOptionsItemSelected(item);
    }


    public void deleteMyFile(String path){
        File myFile = new File(path);
        if(myFile.exists()){
            if(myFile.delete()){
                MediaScannerConnection.scanFile(context, new String[]{path}, null, null);
                toNextImage(imageList.size());
            }else{
                showMessage("Không thể xóa ảnh!");
            }
        }else{
            showMessage("File không tồn tại!");
        }

        MediaScannerConnection.scanFile(context, new String[]{path}, null, null);
    }


    public void toNextImage(int listSize){

        int posPrev = mPager.getCurrentItem();
        imageList.remove(mPager.getCurrentItem());
        mPager.setAdapter(new ImageListAdapter(this,imageList,context));
        MainActivity.al_images.get(folderPos).setAl_imagepath(imageList);
        
        if(listSize == 0){
            this.finish();
        }
        else{
            currPos = posPrev;
            if(currPos == imageList.size())
                currPos--;
            mPager.setCurrentItem(currPos,true);
        }
    }


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
