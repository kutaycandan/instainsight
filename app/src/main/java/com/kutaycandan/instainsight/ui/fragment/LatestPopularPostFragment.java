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
import com.kutaycandan.instainsight.model.LatestPopularPostDto;
import com.kutaycandan.instainsight.ui.activity.UserProfileActivity;
import com.kutaycandan.instainsight.ui.adapter.LatestPopularPostRecyclerViewAdapter;
import com.kutaycandan.instainsight.ui.adapter.UserInfoRecyclerViewAdapter;
import com.kutaycandan.instainsight.widget.textview.HurmeBoldTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LatestPopularPostFragment extends Fragment {

    LinearLayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    Bundle bundle;
    ArrayList<LatestPopularPostDto> latestPopularPostDtos;

    Unbinder unbinder;
    @BindView(R.id.tv_feature_name)
    HurmeBoldTextView tvFeatureName;
    @BindView(R.id.rv_latest_popular_post)
    RecyclerView rvLatestPopularPost;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest_popular_post, container, false);
        unbinder = ButterKnife.bind(this, view);
        bundle = getArguments();
        if (bundle != null) {
            latestPopularPostDtos = (ArrayList<LatestPopularPostDto>) bundle.getSerializable("latestPopularPost");
            RVInit();
        }

        return view;
    }

    private void RVInit(){
        rvLatestPopularPost.setHasFixedSize(true);
        //mAdapter.setHasStableIds(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvLatestPopularPost.setLayoutManager(mLayoutManager);
        mAdapter = new LatestPopularPostRecyclerViewAdapter(latestPopularPostDtos, getActivity());

        rvLatestPopularPost.setAdapter(mAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
