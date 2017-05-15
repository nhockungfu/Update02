package doan.sayphu.gallery01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    GridViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gv_folder);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new GridViewAdapter(this, al_images, int_position);
        gridView.setAdapter(adapter);

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

    @Override
    protected void onResume() {
        setContentView(R.layout.activity_main);

        gridView = (GridView)findViewById(R.id.gv_folder);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new GridViewAdapter(this, al_images,int_position);
        gridView.setAdapter(adapter);

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

        super.onResume();
    }
}
