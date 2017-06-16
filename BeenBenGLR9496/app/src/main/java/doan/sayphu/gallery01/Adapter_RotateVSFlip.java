package doan.sayphu.gallery01;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 5/15/2017.
 */
public class Adapter_RotateVSFlip extends  RecyclerView.Adapter<Adapter_RotateVSFlip.ViewHolder> {

    int selectedPosition;
    private OnItemClickListener listener;
    private ArrayList<String> titleStr;
    private Context mContext;
    private List<RF_Type> mRFType;

    enum RF_Type {
        ROTATE,
        FLIP
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public Adapter_RotateVSFlip(Context context, ArrayList<String> mDataset, List<RF_Type> mEffect) {
        this.mContext = context;
        this.titleStr = mDataset;
        this.mRFType = mEffect;

    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public Adapter_RotateVSFlip.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context)
                .inflate(R.layout.row_fragment_blend, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.myImageViewText.setText(titleStr.get(position));
        if(selectedPosition == position)
        {
            holder.image_effect.setBackgroundColor(Color.WHITE);
        }
        else
        {
            holder.image_effect.setBackgroundColor(Color.BLACK);
        }

        switch (mRFType.get(position))
        {
            case ROTATE:
                Glide.with(mContext)
                        .load(R.drawable.rotate)
                        .into(holder.image_effect);
                break;
            case FLIP:
                Glide.with(mContext)
                        .load(R.drawable.flip)
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
        return titleStr.size();
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

