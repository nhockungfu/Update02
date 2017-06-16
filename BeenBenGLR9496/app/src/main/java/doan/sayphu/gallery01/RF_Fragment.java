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

import doan.sayphu.gallery01.Adapter_RotateVSFlip.RF_Type;

/**
 * Created by USER on 5/15/2017.
 */

public class RF_Fragment extends Fragment implements  BlendFragmentCallBack{

    ImageEffect main;
    private RecyclerView RF_RecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> titleStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        main = (ImageEffect) getActivity();
    }


    public static RF_Fragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        RF_Fragment firstFragment = new RF_Fragment();
        firstFragment.setArguments(args);
        return firstFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout view_layout_effect = (FrameLayout) inflater.inflate(
                R.layout.fragment_rotate_flip, container, false);

        titleStr = new ArrayList<>();
        titleStr.add("Xoay");
        titleStr.add("Lật");

        final List<Adapter_RotateVSFlip.RF_Type> mRFType = new ArrayList<>();
        mRFType.add(RF_Type.ROTATE);
        mRFType.add(RF_Type.FLIP);

        RF_RecyclerView = (RecyclerView)view_layout_effect.findViewById(R.id.RF_RecyclerView);
        RF_RecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RF_RecyclerView.setLayoutManager(mLayoutManager);

        Adapter_RotateVSFlip mAdapter = new Adapter_RotateVSFlip(this.getContext(), titleStr, mRFType);
        mAdapter.setOnItemClickListener(new Adapter_RotateVSFlip.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                main.onMsgFromFragToMain("ROTATE-FLIP-FRAG", mRFType.get(position).toString());
                Log.d("Hiện thị", "vị trí" + mRFType.get(position).toString());
            }
        });

        RF_RecyclerView.setAdapter(mAdapter);

        return view_layout_effect;
    }

    @Override
    public void onMsgFromMainToFragmentBlend(String imagePath) {
        //DO NOTTHING!
    }
}
