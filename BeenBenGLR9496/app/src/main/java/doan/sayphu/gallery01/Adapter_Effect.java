package doan.sayphu.gallery01;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;
import java.util.List;

import doan.sayphu.transformations.BlurTransformation;
import doan.sayphu.transformations.ColorFilterTransformation;
import doan.sayphu.transformations.CropCircleTransformation;
import doan.sayphu.transformations.CropSquareTransformation;
import doan.sayphu.transformations.CropTransformation;
import doan.sayphu.transformations.GrayscaleTransformation;
import doan.sayphu.transformations.MaskTransformation;
import doan.sayphu.transformations.RoundedCornersTransformation;
import doan.sayphu.transformations.gpu.BrightnessFilterTransformation;
import doan.sayphu.transformations.gpu.ContrastFilterTransformation;
import doan.sayphu.transformations.gpu.InvertFilterTransformation;
import doan.sayphu.transformations.gpu.KuwaharaFilterTransformation;
import doan.sayphu.transformations.gpu.PixelationFilterTransformation;
import doan.sayphu.transformations.gpu.SepiaFilterTransformation;
import doan.sayphu.transformations.gpu.SketchFilterTransformation;
import doan.sayphu.transformations.gpu.SwirlFilterTransformation;
import doan.sayphu.transformations.gpu.ToonFilterTransformation;
import doan.sayphu.transformations.gpu.VignetteFilterTransformation;
import doan.sayphu.transformations.internal.Utils;

/**
 * Created by USER on 5/15/2017.
 */
public class Adapter_Effect extends  RecyclerView.Adapter<Adapter_Effect.ViewHolder> {

    private ArrayList<String> mDataset;
    private Context mContext;
    private int selectedPosition;
    private List<Type> mEffect;

    enum Type {
        Mask,
        NinePatchMask,
        CropTop,
        CropCenter,
        CropBottom,
        CropSquare,
        CropCircle,
        ColorFilter,
        Grayscale,
        RoundedCorners,
        Blur,
        Toon,
        Sepia,
        Contrast,
        Invert,
        Pixel,
        Sketch,
        Swirl,
        Brightness,
        Kuawahara,
        Vignette
    }


    public Adapter_Effect(Context context, ArrayList<String> mDataset, List<Type> mEffect) {
        this.mContext = context;
        this.mDataset = mDataset;
        this.mEffect = mEffect;

    }
    private Context getContext() {
        return mContext;
    }

    @Override
    public Adapter_Effect.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context)
                .inflate(R.layout.row_fragment_blend, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(selectedPosition == position)
        {

            holder.image_effect.setBackgroundColor(Color.WHITE);
        }
        else
        {

            holder.image_effect.setBackgroundColor(Color.BLACK);


        }
        holder.myImageViewText.setText(mDataset.get(position));
        switch (mEffect.get(position))
        {
            case Mask: {

                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(
                                new CenterCrop(mContext),
                                new MaskTransformation(mContext, R.drawable.abc))
                        .into(holder.image_effect);
                break;
            }
            case NinePatchMask: {
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new CenterCrop(mContext),
                                new MaskTransformation(mContext, R.drawable.mask_chat_right))
                        .into(holder.image_effect);
                break;
            }
            case CropTop:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(
                                new CropTransformation(mContext, 100, 100, CropTransformation.CropType.TOP))
                        .into(holder.image_effect);
                break;
            case CropCenter:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new CropTransformation(mContext, 300, 100))
                        .into(holder.image_effect);
                break;
            case CropBottom:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(
                                new CropTransformation(mContext, 300, 100, CropTransformation.CropType.BOTTOM))
                        .into(holder.image_effect);

                break;
            case CropSquare:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new CropSquareTransformation(mContext))
                        .into(holder.image_effect);
                break;
            case CropCircle:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new CropCircleTransformation(mContext))
                        .into(holder.image_effect);
                break;
            case ColorFilter:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new ColorFilterTransformation(mContext, Color.argb(255, 255, 0, 0)))

                        .into(holder.image_effect);
                break;
            case Grayscale:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new GrayscaleTransformation(mContext))
                        .into(holder.image_effect);
                break;
            case RoundedCorners:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new RoundedCornersTransformation(mContext, 30, 0,
                                RoundedCornersTransformation.CornerType.BOTTOM))
                        .into(holder.image_effect);
                break;
            case Blur:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new BlurTransformation(mContext, 25, 1))
                        .into(holder.image_effect);
                break;
            case Toon:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new ToonFilterTransformation(mContext))
                        .into(holder.image_effect);
                break;
            case Sepia:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new SepiaFilterTransformation(mContext))
                        .into(holder.image_effect);
                break;
            case Contrast:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new ContrastFilterTransformation(mContext, 2.0f))
                        .into(holder.image_effect);
                break;
            case Invert:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new InvertFilterTransformation(mContext))
                        .into(holder.image_effect);
                break;
            case Pixel:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new PixelationFilterTransformation(mContext, 20))
                        .into(holder.image_effect);
                break;
            case Sketch:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new SketchFilterTransformation(mContext))
                        .into(holder.image_effect);
                break;
            case Swirl:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(
                                new SwirlFilterTransformation(mContext, 0.5f, 1.0f, new PointF(0.5f, 0.5f)))
                        .into(holder.image_effect);
                break;
            case Brightness:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new BrightnessFilterTransformation(mContext, 0.5f))
                        .into(holder.image_effect);
                break;
            case Kuawahara:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new KuwaharaFilterTransformation(mContext, 25))
                        .into(holder.image_effect);
                break;
            case Vignette:
                Glide.with(mContext)
                        .load(R.drawable.demo)
                        .bitmapTransform(new VignetteFilterTransformation(mContext, new PointF(0.5f, 0.5f),
                                new float[] { 0.0f, 0.0f, 0.0f }, 0f, 0.75f))
                        .into(holder.image_effect);
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView image_effect;
        public TextView myImageViewText;



        public ViewHolder(View itemView) {
            super(itemView);
            image_effect = (ImageView) itemView.findViewById(R.id.image_effect);
            myImageViewText = (TextView)itemView.findViewById(R.id.myImageViewText);
            itemView.setOnClickListener(this);
            image_effect.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            selectedPosition = getAdapterPosition();
            notifyDataSetChanged();


        }


    }
}
