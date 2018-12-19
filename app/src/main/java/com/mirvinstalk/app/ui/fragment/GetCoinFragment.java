package com.mirvinstalk.app.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mirvinstalk.app.R;
import com.mirvinstalk.app.constants.ServiceConstant;
import com.mirvinstalk.app.constants.SharedPrefsConstant;
import com.mirvinstalk.app.util.BusStation;
import com.mirvinstalk.app.util.SharedPrefsHelper;
import com.mirvinstalk.app.widget.textview.HurmeBoldTextView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

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


    String currencyType;
    ArrayList<ArrayList<Double>> prices;
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
        setPrices();
        return view;
    }

    public void setPrices(){
        Log.d("Locale",""+Locale.getDefault().getDisplayCountry());
        Currency currency = Currency.getInstance(Locale.getDefault());
        currencyType = currency.getCurrencyCode();
        if(currencyType.equalsIgnoreCase("TRY")){
            prices = SharedPrefsHelper.getInstance().getArrayList(SharedPrefsConstant.PRICE_IN_TRY);
        }
        else if(currencyType.equalsIgnoreCase("EUR")){
            prices = SharedPrefsHelper.getInstance().getArrayList(SharedPrefsConstant.PRICE_IN_EUR);
        }else{
            currencyType="USD";
            prices = SharedPrefsHelper.getInstance().getArrayList(SharedPrefsConstant.PRICE_IN_USD);
        }
        if(prices!=null){
            if(prices.size()==4){
                tv3coin.setText(""+prices.get(0).get(1)+" "+currencyType);
                tv5coin.setText(""+prices.get(1).get(1)+" "+currencyType);
                tv10coin.setText(""+prices.get(2).get(1)+" "+currencyType);
                tv25coin.setText(""+prices.get(3).get(1)+" "+currencyType);
            }
        }
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