package com.mirvinstalk.app.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mirvinstalk.app.R;
import com.mirvinstalk.app.constants.SharedPrefsConstant;
import com.mirvinstalk.app.ui.activity.MainActivity;
import com.mirvinstalk.app.util.BusStation;
import com.mirvinstalk.app.util.SharedPrefsHelper;
import com.mirvinstalk.app.widget.textview.HurmeBoldTextView;
import com.mirvinstalk.app.widget.textview.HurmeRegularTextView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearchFragment extends Fragment {

    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.tv_insta)
    HurmeRegularTextView tvInsta;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.ll_footer)
    LinearLayout llFooter;
    Unbinder unbinder;
    FragmentTransaction transaction;
    int textCode;
    ArrayList<String> names;
    String username;
    @BindView(R.id.tv_my_stalks)
    HurmeBoldTextView tvMyStalks;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();

        if (bundle != null) {
            if (bundle.getInt("textCode") == -1) {
                llFooter.setVisibility(View.GONE);
                llHeader.setVisibility(View.GONE);
                setInternetConnectionErrorFragment();
            } else if (bundle.getInt("textCode") == 1) {
                tvMyStalks.setText(tvMyStalks.getText().toString() + SharedPrefsHelper.getInstance().get(SharedPrefsConstant.AMOUNT_CODE));
                setSearchBarFragment();
            }
        }
        return view;
    }

    @OnClick(R.id.rl_main)
    public void hideKeyboard(){
        BusStation.getBus().post("hide");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @OnClick(R.id.btn_try_free)
    public void tryFreeClicked() {
        ((MainActivity)getActivity()).startDemo();
        new CountDownTimer(600,100){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                ((MainActivity)getActivity()).endDemo();
            }
        }.start();
    }

    private void setSearchBarFragment() {
        SearchBarFragment sbf = new SearchBarFragment();
        /*if(username!=null){
            Bundle b = new Bundle();
            b.putString("username", username);
            sbf.setArguments(b);
        }*/

        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, sbf);
        transaction.commitAllowingStateLoss();
    }

    private void setInternetConnectionErrorFragment() {
        InternetWarningFragment iwf = new InternetWarningFragment();
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, iwf);
        transaction.commitAllowingStateLoss();
    }

    private void setSpendCoinFragment() {
        SpendCoinFragment scf = new SpendCoinFragment();
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, scf);
        //transaction.addToBackStack("");
        transaction.commitAllowingStateLoss();
    }

    public static SearchFragment newInstance(int textCode) {

        SearchFragment f = new SearchFragment();
        Bundle b = new Bundle();
        b.putInt("textCode", textCode);
        f.setArguments(b);
        return f;
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
        if (message.equals("showSpendCoin")) {
            setSpendCoinFragment();
        } else if (message.equals("notAccept")) {
            setSearchBarFragment();
        } else if (message.equals("showSearchBar")) {
            setSearchBarFragment();
        }
        else if(message.startsWith("Stalk:")){
            tvMyStalks.setText("My stalks: "+message.substring(6));
        }

    }
}
