package com.kutaycandan.instainsight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.constants.SharedPrefsConstant;
import com.kutaycandan.instainsight.util.SharedPrefsHelper;
import com.kutaycandan.instainsight.widget.textview.HurmeBoldTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GetCoinFragment extends Fragment {

    @BindView(R.id.tv_my_stalks)
    HurmeBoldTextView tvMyStalks;
    @BindView(R.id.ll_footer)
    LinearLayout llFooter;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_coin, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getInt("textCode") != -1) {
                tvMyStalks.setText(tvMyStalks.getText().toString()+ SharedPrefsHelper.getInstance().get(SharedPrefsConstant.AMOUNT_CODE));
            }
        }

        return view;
    }

    public static GetCoinFragment newInstance(int textCode) {
        GetCoinFragment f = new GetCoinFragment();
        Bundle b = new Bundle();
        b.putInt("textCode", textCode);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}