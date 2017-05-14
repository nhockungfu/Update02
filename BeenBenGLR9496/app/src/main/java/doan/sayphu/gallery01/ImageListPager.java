package doan.sayphu.gallery01;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;

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
    private LayoutInflater inflater;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image_pager);

        context = this.getApplicationContext();
        Intent intent = getIntent();
        imageList = intent.getStringArrayListExtra(IMAGE_LIST_KEY);

        currPos = intent.getIntExtra(POS_KEY,0);
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
        }

        return super.onOptionsItemSelected(item);
    }


    public void deleteMyFile(String path){
        File myFile = new File(path);
        if(myFile.exists()){
            if(myFile.delete()){
                //mPager.removeViewAt(mPager.getCurrentItem());
                MediaScannerConnection.scanFile(context, new String[]{path}, null, null);
                //toNextImage(imageList.size());

            }
        }
        MediaScannerConnection.scanFile(context, new String[]{path}, null, null);
    }

    public void toNextImage(int sizeCurrPath){
        sizeCurrPath--;
        if(sizeCurrPath == 0){
            this.finish();
            String folderPath = MainActivity.al_images.get(currFolderPos).getStr_folder();
            deleteMyFile(folderPath);
            Intent intent = new Intent(ImageListPager.this, MainActivity.class);
        }
        else{
            if(mPager.getCurrentItem() == 0){
                mPager.setCurrentItem(imageList.size()-1);
            }
            else {
                mPager.setCurrentItem(mPager.getCurrentItem()-1);
            }

            //imageList.remove(mPager.getCurrentItem());
        }
    }


//    public void showMessage(String msg)
//    {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Thông báo");
//        builder.setMessage(msg);
//        builder.setCancelable(false);
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
}
