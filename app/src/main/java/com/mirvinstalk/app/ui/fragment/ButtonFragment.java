package com.mirvinstalk.app.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mirvinstalk.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ButtonFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.ll_profile_stalkers)
    LinearLayout llProfileStalkers;
    @BindView(R.id.ll_top_likers)
    LinearLayout llTopLikers;
    @BindView(R.id.ll_popular_followers)
    LinearLayout llPopularFollowers;
    @BindView(R.id.ll_like_trend)
    LinearLayout llLikeTrend;
    @BindView(R.id.ll_stalk_count)
    LinearLayout llStalkCount;
    @BindView(R.id.ll_latest_popular_post)
    LinearLayout llLatestPopularPost;


    ButtonFragmentListener listener;


    public interface ButtonFragmentListener {
        public void hdProfilePictureClicked();

        public void profileStalkersClicked();

        public void nonFollowersClicked();

        public void topLikersClicked();

        public void mostActiveFollowersClicked();

        public void latestPopularPostClicked();

        public void nonFollowingClicked();

        public void popularfollowersClicked();

        public void likeTrendClicked();

        public void stalkCountCliked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ButtonFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_hd_profile_picture, R.id.ll_non_followers, R.id.ll_top_likers, R.id.ll_most_active_followers, R.id.ll_latest_popular_post, R.id.ll_non_following, R.id.ll_popular_followers, R.id.ll_like_trend, R.id.ll_stalk_count, R.id.ll_profile_stalkers})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_hd_profile_picture:
                listener.hdProfilePictureClicked();
                break;
            case R.id.ll_profile_stalkers:
                listener.profileStalkersClicked();
                break;
            case R.id.ll_non_followers:
                listener.nonFollowersClicked();
                break;
            case R.id.ll_top_likers:
                listener.topLikersClicked();
                break;
            case R.id.ll_most_active_followers:
                listener.mostActiveFollowersClicked();
                break;
            case R.id.ll_latest_popular_post:
                listener.latestPopularPostClicked();
                break;
            case R.id.ll_non_following:
                listener.nonFollowingClicked();
                break;
            case R.id.ll_popular_followers:
                listener.popularfollowersClicked();
                break;
            case R.id.ll_like_trend:
                listener.likeTrendClicked();
                break;
            case R.id.ll_stalk_count:
                listener.stalkCountCliked();
                break;
        }
    }
}
