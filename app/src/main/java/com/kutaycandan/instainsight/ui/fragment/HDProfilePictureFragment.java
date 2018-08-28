package com.kutaycandan.instainsight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.model.HdProfilePicDto;
import com.kutaycandan.instainsight.util.Utils;
import com.kutaycandan.instainsight.widget.textview.HurmeBoldTextView;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HDProfilePictureFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.btn_save_image)
    LinearLayout btnSaveImage;

    HdProfilePicDto hdProfilePicDto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hd_profile_picture, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            hdProfilePicDto = (HdProfilePicDto) bundle.getSerializable("hdProfilePicDto");
            Glide.with(this)
                    .load(hdProfilePicDto.getProfilePictureHd())
                    .into(ivImage);
        }
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_save_image)
    public void onViewClicked() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Utils.saveImage(hdProfilePicDto.getProfilePictureHd(),hdProfilePicDto.getUserName(),getActivity());
                    Toast.makeText(getActivity(),"Image Saved.",Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }
}
