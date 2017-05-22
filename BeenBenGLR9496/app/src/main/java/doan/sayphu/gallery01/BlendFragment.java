package doan.sayphu.gallery01;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import doan.sayphu.gallery01.Adapter_Effect.Type;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 5/15/2017.
 */

public class BlendFragment extends Fragment {

    ImageEffect main;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> mDataset;
    private String image_current_path;





    public static BlendFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        BlendFragment firstFragment = new BlendFragment();
        firstFragment.setArguments(args);
        return firstFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        main = (ImageEffect) getActivity();
        //image_current_path = main.getIntent().getStringExtra("image_path");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        FrameLayout view_layout_effect = (FrameLayout) inflater.inflate(
                R.layout.fragment_blend,container, false);
        mDataset = new ArrayList<>();
        mDataset.add("None");
        mDataset.add("Grayscale");
        mDataset.add("RoundedCorners");
        mDataset.add("Blur");
        mDataset.add("Toon");
        mDataset.add("Sepia");
        mDataset.add("Contrast");
        mDataset.add("Sketch");
        mDataset.add("Brightness");
        mDataset.add("Vignette");
        mDataset.add("");

        List<Type> mEffect = new ArrayList<>();
        mEffect.add(Type.None);
        mEffect.add(Type.Grayscale);
        mEffect.add(Type.RoundedCorners);
        mEffect.add(Type.Blur);
        mEffect.add(Type.Toon);
        mEffect.add(Type.Sepia);
        mEffect.add(Type.Contrast);
        mEffect.add(Type.Sketch);
        mEffect.add(Type.Brightness);
        mEffect.add(Type.Vignette);
        mEffect.add(Type.Plus);

        mRecyclerView = (RecyclerView)view_layout_effect.findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new Adapter_Effect(this.getContext(), mDataset, mEffect);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);



        return view_layout_effect;
    }
}
