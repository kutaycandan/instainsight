package com.kutaycandan.instainsight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.model.InstaUserModel;
import com.kutaycandan.instainsight.ui.activity.UserProfileActivity;
import com.kutaycandan.instainsight.ui.adapter.UserInfoRecyclerViewAdapter;
import com.kutaycandan.instainsight.widget.textview.HurmeBoldTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FeatureFragment extends Fragment {

    LinearLayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    Bundle bundle;
    ArrayList<InstaUserModel> mDataSet;


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
            mDataSet = ((UserProfileActivity)getActivity()).getUserList();
            RVInit();
        }
        return view;
    }

    private void RVInit(){
        rvUserInfo.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvUserInfo.setLayoutManager(mLayoutManager);
        mAdapter = new UserInfoRecyclerViewAdapter(mDataSet, getActivity());
        rvUserInfo.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
