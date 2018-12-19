package com.mirvinstalk.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirvinstalk.app.R;
import com.mirvinstalk.app.model.InstaUserModel;
import com.mirvinstalk.app.model.InstaUserModelFollowerCount;
import com.mirvinstalk.app.model.InstaUserModelLikeCount;
import com.mirvinstalk.app.ui.activity.UserProfileActivity;
import com.mirvinstalk.app.ui.adapter.UserInfoRecyclerViewAdapter;
import com.mirvinstalk.app.widget.textview.HurmeBoldTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FeatureFragment extends Fragment {

    LinearLayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    Bundle bundle;
    ArrayList<InstaUserModel> mDataSet1;
    ArrayList<InstaUserModelLikeCount> mDataSet2;
    ArrayList<InstaUserModelFollowerCount> mDataSet3;


    Unbinder unbinder;
    @BindView(R.id.tv_feature_name)
    HurmeBoldTextView tvFeatureName;
    @BindView(R.id.rv_user_info)
    RecyclerView rvUserInfo;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feature, container, false);
        unbinder = ButterKnife.bind(this, view);
        bundle = getArguments();
        if(bundle!=null){
            tvFeatureName.setText(bundle.getString("featureName",""));
            int type = bundle.getInt("type");
            RVInit(type);
        }
        return view;
    }

    private void RVInit(int type){
        rvUserInfo.setHasFixedSize(true);
        //mAdapter.setHasStableIds(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvUserInfo.setLayoutManager(mLayoutManager);
        if(type==1){
            mDataSet1 = ((UserProfileActivity)getActivity()).getUserList();
            mAdapter = new UserInfoRecyclerViewAdapter(mDataSet1,null,null, getActivity());
        }
        else if(type==2){
            mDataSet2 = ((UserProfileActivity)getActivity()).getUserListWithLikeCount();
            mAdapter = new UserInfoRecyclerViewAdapter(null,mDataSet2,null, getActivity());
        }
        else{
            mDataSet3 = ((UserProfileActivity)getActivity()).getUserListWithFollowerCount();
            mAdapter = new UserInfoRecyclerViewAdapter(null,null,mDataSet3, getActivity());
        }
        rvUserInfo.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
