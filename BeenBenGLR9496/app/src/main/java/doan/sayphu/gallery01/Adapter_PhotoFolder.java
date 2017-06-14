package doan.sayphu.gallery01;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by BEN on 20/04/2017.
 */

public class Adapter_PhotoFolder extends ArrayAdapter<Model_images> {

    Context context;
    ViewHolder viewHolder;
    ArrayList<Model_images> al_menu = new ArrayList<>();


    public Adapter_PhotoFolder(Context context, ArrayList<Model_images> al_menu) {
        super(context, R.layout.adapter_photofolder, al_menu);
        this.al_menu = al_menu;
        this.context = context;

    }

    @Override
    public int getCount() {

        Log.e("ADAPTER LIST SIZE", al_menu.size() + "");
        return al_menu.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_menu.size() > 0) {
            return al_menu.size();
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_photofolder, parent, false);
            viewHolder.tv_foldern = (TextView) convertView.findViewById(R.id.tv_folder);
            viewHolder.tv_foldersize = (TextView) convertView.findViewById(R.id.tv_folder_number);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);

            //đoạn lấy width và height của màn hình
            Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            //set height for Image View
            viewHolder.iv_image.getLayoutParams().height = width/2 - 8;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_foldern.setText(al_menu.get(position).getStr_folder());
        viewHolder.tv_foldersize.setText("(" + al_menu.get(position).getAl_imagepath().size() + ")");

        //Set độ trong suốt cho Image View
        viewHolder.iv_image.setImageAlpha(170);
        if(viewHolder.tv_foldern.getText().toString().equalsIgnoreCase("camera") == true){

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(al_menu.get(position).getAl_imagepath().get(0),bmOptions);
            Bitmap bitmapResize = resize(bitmap, 200 ,150);

            viewHolder.iv_image.setBackground(new BitmapDrawable(context.getResources(), bitmapResize));

            Glide.with(context).load(R.drawable.img_camera)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(viewHolder.iv_image);
        }
        else {
            Glide.with(context).load(al_menu.get(position).getAl_imagepath().get(0))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(viewHolder.iv_image);
        }

        return convertView;
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

    private static class ViewHolder {
        TextView tv_foldern, tv_foldersize;
        ImageView iv_image;
    }
}
