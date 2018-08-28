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
import com.kutaycandan.instainsight.constants.SharedPrefsConstant;
import com.kutaycandan.instainsight.util.BusStation;
import com.kutaycandan.instainsight.util.SharedPrefsHelper;
import com.kutaycandan.instainsight.widget.textview.HurmeBoldTextView;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GetCoinFragment extends Fragment {

    @BindView(R.id.tv_my_stalks)
    HurmeBoldTextView tvMyStalks;
    @BindView(R.id.ll_footer)
    LinearLayout llFooter;
    Unbinder unbinder;
    @BindView(R.id.tv_3coin)
    HurmeBoldTextView tv3coin;
    @BindView(R.id.btn_3coin)
    LinearLayout btn3coin;

    GetCoinListener listener;
    @BindView(R.id.tv_5coin)
    HurmeBoldTextView tv5coin;
    @BindView(R.id.btn_5coin)
    LinearLayout btn5coin;
    @BindView(R.id.tv_10coin)
    HurmeBoldTextView tv10coin;
    @BindView(R.id.btn_10coin)
    LinearLayout btn10coin;
    @BindView(R.id.tv_25coin)
    HurmeBoldTextView tv25coin;
    @BindView(R.id.btn_25coin)
    LinearLayout btn25coin;

    public interface GetCoinListener {
        public void get3coin();

        public void get5coin();

        public void get10coin();

        public void get25coin();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (GetCoinListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_coin, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getInt("textCode") != -1) {
                tvMyStalks.setText(tvMyStalks.getText().toString() + SharedPrefsHelper.getInstance().get(SharedPrefsConstant.AMOUNT_CODE));
            }
        }
        setClickListeners();

        return view;
    }


    public void setClickListeners() {
        btn3coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.get3coin();
            }
        });
        btn5coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.get5coin();
            }
        });
        btn10coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.get10coin();
            }
        });
        btn25coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.get25coin();
            }
        });
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

    @Override
    public void onResume() {
        super.onResume();
        BusStation.getBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusStation.getBus().unregister(this);
    }

    @Subscribe
    public void receivedMessage(String message) {
        if (message.startsWith("Stalk:")) {
            tvMyStalks.setText("My stalks: " + message.substring(6));
        }

    }


    //Billing methods


}