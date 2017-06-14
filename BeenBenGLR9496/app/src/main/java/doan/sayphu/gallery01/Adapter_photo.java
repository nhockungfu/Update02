package doan.sayphu.gallery01;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;

import static doan.sayphu.gallery01.R.layout.adapter_photofolder;

/**
 * Created by BEN on 20/04/2017.
 */


public class Adapter_Photo extends ArrayAdapter<Model_images> {

    Context context;
    ViewHolder viewHolder;
    ArrayList<Model_images> al_menu = new ArrayList<>();
    int int_position;


    public Adapter_Photo(Context context, ArrayList<Model_images> al_menu, int int_position) {
        super(context, R.layout.adapter_photofolder, al_menu);
        this.al_menu = al_menu;
        this.context = context;
        this.int_position = int_position;
    }

    @Override
    public int getCount() {

        Log.e("ADAPTER LIST SIZE", al_menu.get(int_position).getAl_imagepath().size() + "");
        return al_menu.get(int_position).getAl_imagepath().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_menu.get(int_position).getAl_imagepath().size() > 0) {
            return al_menu.get(int_position).getAl_imagepath().size();
        } else {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(adapter_photofolder, parent, false);
            viewHolder.tv_foldern = (TextView) convertView.findViewById(R.id.tv_folder);
            viewHolder.tv_foldersize = (TextView) convertView.findViewById(R.id.tv_folder_number);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            viewHolder.relativeLayout_res1 = (RelativeLayout) convertView.findViewById(R.id.rectangle_bottom1);
            viewHolder.relativeLayout_res2 = (RelativeLayout) convertView.findViewById(R.id.rectangle_bottom2);

            Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            //viewHolder.iv_image.getLayoutParams().height = (width-16)/4;
            viewHolder.iv_image.getLayoutParams().height = 150;
            viewHolder.iv_image.requestLayout();

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_foldern.setVisibility(View.GONE);
        viewHolder.tv_foldersize.setVisibility(View.GONE);
        viewHolder.relativeLayout_res1.setVisibility(View.GONE);
        viewHolder.relativeLayout_res2.setVisibility(View.GONE);


        Glide.with(context).load("file://" + al_menu.get(int_position).getAl_imagepath().get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(viewHolder.iv_image);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                showImageView(viewHolder.iv_image, al_menu.get(int_position).getAl_imagepath().get(position));
//            }
//        }).start();

//        String path = al_menu.get(int_position).getAl_imagepath().get(position);
//        viewHolder.iv_image.setImageBitmap(decodeSampledBitmapFromUri(path, 150, 150));

//        File imgFile = new  File(al_menu.get(int_position).getAl_imagepath().get(position));
//        if(imgFile.exists()) {
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            viewHolder.iv_image.setImageBitmap(myBitmap);
//        }

//        Drawable myDrawable = context.getResources().getDrawable(R.drawable.no_image);
//        Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
//        viewHolder.iv_image.setImageBitmap(myLogo);

//        ImageView imageView;
//        if (convertView == null) {  // if it's not recycled, initialize some attributes
//            imageView = new ImageView(context);
//            imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//
//        Bitmap bm = decodeSampledBitmapFromUri(al_menu.get(int_position).getAl_imagepath().get(position), 220, 220);
//
//        imageView.setImageBitmap(bm);

        return convertView;

    }

    private static class ViewHolder {
        TextView tv_foldern, tv_foldersize;
        RelativeLayout relativeLayout_res1, relativeLayout_res2;
        ImageView iv_image;
    }

    private void showImageView(ImageView imageView, String path){
        File imgFile = new  File(path);
        if(imgFile.exists()){
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            imageView.setImageBitmap(myBitmap);

//            Drawable myDrawable = context.getResources().getDrawable(R.drawable.no_image);
//            Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
        }
    }

    public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {

        Bitmap bm = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, options);

        return bm;
    }

    public int calculateInSampleSize( BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }

        return inSampleSize;
    }


}
