package com.mirvinstalk.app.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mirvinstalk.app.R;
import com.mirvinstalk.app.model.HdProfilePicDto;
import com.mirvinstalk.app.util.Utils;
import com.mirvinstalk.app.widget.textview.HurmeBoldTextView;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HDProfilePictureFragment extends Fragment {
    private static final String TAG = "HDProfilePictureFragment";
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

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getActivity(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permission","Permission is granted");
                return true;
            } else {

                Log.v("Permission","Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Permission","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            onViewClicked();
        }
    }

    @OnClick(R.id.btn_save_image)
    public void onViewClicked() {
        if(isStoragePermissionGranted()){
            thread.start();
        }
    }
    Thread thread = new Thread() {
        @Override
        public void run() {
            try {
                Utils.saveImage(hdProfilePicDto.getProfilePictureHd(),hdProfilePicDto.getUserName(),getActivity());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"Image Saved.",Toast.LENGTH_LONG).show();
                    }
                });
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
