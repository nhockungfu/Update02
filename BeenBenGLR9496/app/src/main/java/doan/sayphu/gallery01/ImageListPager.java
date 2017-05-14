package doan.sayphu.gallery01;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import static doan.sayphu.gallery01.PhotosActivity.IMAGE_LIST_KEY;
import static doan.sayphu.gallery01.PhotosActivity.POS_KEY;

/**
 * Created by BEN on 26/04/2017.
 */

public class ImageListPager extends AppCompatActivity {

    private static ViewPager mPager;
    private int currPos;
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

            }break;

//            case R.id.action_rotate:{
//
//            }break;
//
//            case R.id.action_crop:{
//
//            }break;
//
//            case R.id.action_delete:{
//                showAlertDialog();
//            }break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa bức ảnh này?");
        builder.setCancelable(false);
        builder.setPositiveButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //xử lý Hủy bỏ
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Đồng ý xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void closeActivity()
    {
        this.finish();
    }

    public void nextImage()
    {

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
