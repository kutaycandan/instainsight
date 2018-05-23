package com.kutaycandan.instainsight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.ui.activity.UserProfileActivity;
import com.kutaycandan.instainsight.ui.adapter.UserInfoRecyclerViewAdapter;
import com.kutaycandan.instainsight.widget.textview.HurmeBlackTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StalkCountFragment extends Fragment {


    Bundle bundle;
    int stalkCount;

    Unbinder unbinder;
    @BindView(R.id.tv_stalk_count)
    HurmeBlackTextView tvStalkCount;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stalk_count, container, false);
        unbinder = ButterKnife.bind(this, view);
        bundle = getArguments();
        if (bundle != null) {
            stalkCount = bundle.getInt("stalkCount");
            tvStalkCount.setText(""+stalkCount);

        }
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
