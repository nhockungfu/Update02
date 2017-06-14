package doan.sayphu.gallery01;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import static doan.sayphu.gallery01.MainActivity.al_images;

/**
 * Created by BEN on 20/04/2017.
 */

public class PhotosActivity  extends AppCompatActivity {

    public static final String POS_KEY = "pos_key";
    public static final String IMAGE_LIST_KEY = "image_list_key";
    public static final String FOLDER_POS_KEY = "folder_pos_key";

    int int_position;
    private GridView gridView;
    Adapter_Photo adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_list);

        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void init(){

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        //tiêu đề
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        gridView = (GridView) findViewById(R.id.gv_img);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new Adapter_Photo(this, al_images, int_position);
        gridView.setAdapter(adapter);

        Display display = ((Activity)PhotosActivity.this).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        gridView.setNumColumns(width/(150+16));

        getSupportActionBar().setTitle( al_images.get(int_position).getStr_folder());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> test = MainActivity.al_images.get(int_position).getAl_imagepath();
                Intent intent = new Intent(PhotosActivity.this, ImageListPager.class);
                intent.putExtra(POS_KEY, position);
                intent.putExtra(IMAGE_LIST_KEY, test);
                intent.putExtra(FOLDER_POS_KEY, int_position);
                startActivity(intent);
            }
        });
    }
}

