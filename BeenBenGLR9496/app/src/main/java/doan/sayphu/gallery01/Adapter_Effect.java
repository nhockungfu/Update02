package doan.sayphu.gallery01;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by USER on 5/15/2017.
 */
public class Adapter_Effect extends  RecyclerView.Adapter<Adapter_Effect.ViewHolder> {

    private ArrayList<String> mDataset;
    private int pos1 = -1;


    public Adapter_Effect(ArrayList<String> mDataset) {
        this.mDataset = mDataset;

    }

    @Override
    public Adapter_Effect.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_fragment_blend, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Adapter_Effect.ViewHolder holder, int position) {
        holder.myImageViewText.setText(mDataset.get(position));
        holder.image_effect.setImageResource(R.drawable.background);

    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private String mItem;
        public final ImageView image_effect;
        public TextView myImageViewText;
        private String TAG = Adapter_Effect.class.getSimpleName();


        public ViewHolder(View itemView) {
            super(itemView);
            image_effect = (ImageView) itemView.findViewById(R.id.image_effect);
            myImageViewText = (TextView)itemView.findViewById(R.id.myImageViewText);
            itemView.setOnClickListener(this);





        }
        public void setItem(String item) {
            mItem = item;
            myImageViewText.setText(item);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if(pos == pos1)
            {
                myImageViewText.setText(String.valueOf(pos));
                //images.get(pos).setBackgroundColor(Color.WHITE);
            }
            else
            {
                if(pos1 == -1)
                {
                    myImageViewText.setText(String.valueOf(pos));
                }
                else
                {
                    myImageViewText.setText(String.valueOf(pos));
                    myImageViewText.setText(String.valueOf(pos));

                }



            }

        }
    }
}
