package com.kutaycandan.instainsight.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kutaycandan.instainsight.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SpendCoinFragment extends Fragment {


    @BindView(R.id.ll_accept)
    LinearLayout llAccept;
    @BindView(R.id.ll_cancel)
    LinearLayout llCancel;
    Unbinder unbinder;

    SpendCoinListener listener;
    public interface SpendCoinListener{
        public void isSpendAccepted(boolean isAccept);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spend_coin, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_accept, R.id.ll_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_accept:
                listener.isSpendAccepted(true);
                break;
            case R.id.ll_cancel:
                listener.isSpendAccepted(false);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (SpendCoinListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }
}
