package doan.sayphu.gallery01;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import doan.sayphu.gallery01.Adapter_Effect.Type;

/**
 * Created by USER on 5/15/2017.
 */

public class BlendFragment extends Fragment implements  BlendFragmentCallBack{

    ImageEffect main;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> mDataset;
    int BlendType;



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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FrameLayout view_layout_effect = (FrameLayout) inflater.inflate(
                R.layout.fragment_blend,container, false);
        mDataset = new ArrayList<>();
        mDataset.add("None");
        mDataset.add("Grayscale");
        mDataset.add("Rounded");
        mDataset.add("Blur");
        mDataset.add("Toon");
        mDataset.add("Sepia");
        mDataset.add("Contrast");
        mDataset.add("Sketch");
        mDataset.add("Brightness");
        mDataset.add("Vignette");
        mDataset.add("");

        final List<Type> mEffect = new ArrayList<>();
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

        String image_current_path = this.getArguments().getString("params");
        mRecyclerView = (RecyclerView)view_layout_effect.findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Adapter_Effect mAdapter = new Adapter_Effect(this.getContext(), mDataset, mEffect, image_current_path);
        mAdapter.setOnItemClickListener(new Adapter_Effect.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                main.onMsgFromFragToMain("BLEND-FRAG", mEffect.get(position).toString());
                Log.d("Hiện thị", "vị trí" + mEffect.get(position).toString());
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        return view_layout_effect;
    }

    public void onMsgFromMainToFragmentBlend(String pathImage)
    {
        /*image_current_path = pathImage;
        Log.d("ImagePath Fragment", image_current_path);*/
    }
}
