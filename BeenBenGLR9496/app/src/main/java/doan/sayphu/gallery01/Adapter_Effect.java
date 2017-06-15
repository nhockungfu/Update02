package doan.sayphu.gallery01;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;
import java.util.List;

import doan.sayphu.transformations.BlurTransformation;
import doan.sayphu.transformations.GrayscaleTransformation;
import doan.sayphu.transformations.RoundedCornersTransformation;
import doan.sayphu.transformations.gpu.BrightnessFilterTransformation;
import doan.sayphu.transformations.gpu.ContrastFilterTransformation;
import doan.sayphu.transformations.gpu.SepiaFilterTransformation;
import doan.sayphu.transformations.gpu.SketchFilterTransformation;
import doan.sayphu.transformations.gpu.ToonFilterTransformation;
import doan.sayphu.transformations.gpu.VignetteFilterTransformation;

/**
 * Created by USER on 5/15/2017.
 */
public class Adapter_Effect extends  RecyclerView.Adapter<Adapter_Effect.ViewHolder> {

    int selectedPosition;
    private OnItemClickListener listener;
    private ArrayList<String> mDataset;
    private Context mContext;
    private List<Type> mEffect;
    private String imagePath;

    enum Type {
        None,
        Grayscale,
        RoundedCorners,
        Blur,
        Toon,
        Sepia,
        Contrast,
        Sketch,
        Brightness,
        Vignette,
        Plus
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public Adapter_Effect(Context context, ArrayList<String> mDataset, List<Type> mEffect, String imagePath) {
        this.mContext = context;
        this.mDataset = mDataset;
        this.mEffect = mEffect;
        this.imagePath = imagePath;

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
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.myImageViewText.setText(mDataset.get(position));
        if(selectedPosition == position)
        {
            holder.image_effect.setBackgroundColor(Color.WHITE);
        }
        else
        {
            holder.image_effect.setBackgroundColor(Color.BLACK);
        }

        switch (mEffect.get(position))
        {
            case None:
                Glide.with(mContext)
                        .load("file://" + imagePath)
                        .into(holder.image_effect);
                break;
            case Grayscale:
                Glide.with(mContext)
                        .load("file://" + imagePath)
                        .bitmapTransform(new GrayscaleTransformation(mContext))
                        .into(holder.image_effect);
                break;
            case RoundedCorners:
                Glide.with(mContext)
                        .load("file://" + imagePath)
                        .bitmapTransform(new RoundedCornersTransformation(mContext, 30, 0,
                                RoundedCornersTransformation.CornerType.BOTTOM))
                        .into(holder.image_effect);
                break;
            case Blur:
                Glide.with(mContext)
                        .load("file://" + imagePath)
                        .bitmapTransform(new BlurTransformation(mContext, 25, 1))
                        .into(holder.image_effect);
                break;
            case Toon:
                Glide.with(mContext)
                        .load("file://" + imagePath)
                        .bitmapTransform(new ToonFilterTransformation(mContext))
                        .into(holder.image_effect);
                break;
            case Sepia:
                Glide.with(mContext)
                        .load("file://" + imagePath)
                        .bitmapTransform(new SepiaFilterTransformation(mContext))
                        .into(holder.image_effect);
                break;
            case Contrast:
                Glide.with(mContext)
                        .load("file://" + imagePath)
                        .bitmapTransform(new ContrastFilterTransformation(mContext, 2.0f))
                        .into(holder.image_effect);
                break;
            case Sketch:
                Glide.with(mContext)
                        .load("file://" + imagePath)
                        .bitmapTransform(new SketchFilterTransformation(mContext))
                        .into(holder.image_effect);
                break;
            case Brightness:
                Glide.with(mContext)
                        .load("file://" + imagePath)
                        .bitmapTransform(new BrightnessFilterTransformation(mContext, 0.5f))
                        .into(holder.image_effect);
                break;
            case Vignette:
                Glide.with(mContext)
                        .load("file://" + imagePath)
                        .bitmapTransform(new VignetteFilterTransformation(mContext, new PointF(0.5f, 0.5f),
                                new float[] { 0.0f, 0.0f, 0.0f }, 0f, 0.75f))
                        .into(holder.image_effect);
                break;
            case Plus:
                holder.image_effect.setImageResource(R.mipmap.ic_plus_math);
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_effect;
        public TextView myImageViewText;



        public ViewHolder(final View itemView) {
            super(itemView);
            image_effect = (ImageView) itemView.findViewById(R.id.image_effect);
            myImageViewText = (TextView)itemView.findViewById(R.id.myImageViewText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                        selectedPosition = getAdapterPosition();
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }


}

