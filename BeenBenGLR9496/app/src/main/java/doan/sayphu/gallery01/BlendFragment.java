package doan.sayphu.gallery01;


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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by USER on 5/15/2017.
 */

public class BlendFragment extends Fragment {

    ImageEffect main;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> mDataset;




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
        for(int i = 0; i < 10; i++)
        {
            mDataset.add("ádasda#" + i);
        }



        mRecyclerView = (RecyclerView)view_layout_effect.findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new Adapter_Effect(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        return view_layout_effect;
       // return inflater.inflate(R.layout.fragment_blend, container, false);
    }
}
