package com.mirvinstalk.app.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirvinstalk.app.R;
import com.mirvinstalk.app.constants.SharedPrefsConstant;
import com.mirvinstalk.app.model.IIInvoices;
import com.mirvinstalk.app.ui.activity.MainActivity;
import com.mirvinstalk.app.ui.activity.UserProfileActivity;
import com.mirvinstalk.app.util.BusStation;
import com.mirvinstalk.app.util.SharedPrefsHelper;
import com.mirvinstalk.app.util.Utils;
import com.mirvinstalk.app.widget.RecentSearchesItem;
import com.mirvinstalk.app.widget.edittext.HurmeRegularObliqueEditText;
import com.mirvinstalk.app.widget.textview.HurmeRegularObliqueTextView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearchBarFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.iv_search_bar)
    ImageView ivSearchBar;
    @BindView(R.id.et_username)
    HurmeRegularObliqueEditText etUsername;
    @BindView(R.id.ll_go)
    LinearLayout llGo;
    @BindView(R.id.ll_recent_searches)
    LinearLayout llRecentSearches;
    SearchBarListener listener;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.ll_container_parent)
    LinearLayout llContainerParent;
    @BindView(R.id.tv_clear_searches)
    HurmeRegularObliqueTextView tvClearSearches;
    @BindView(R.id.ll_main)
    LinearLayout llMain;


    public interface SearchBarListener {
        public void askUserToSpendCoin(String username);
    }

    ArrayList<IIInvoices> iIInvoices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_bar, container, false);
        unbinder = ButterKnife.bind(this, view);
        iIInvoices = ((MainActivity) getActivity()).getIiInvoices();
        etUsername.addTextChangedListener(tw);
        llGo.setVisibility(View.VISIBLE);
        llRecentSearches.setVisibility(View.GONE);
        keyboardCheckClicked();
        return view;
    }

    private void keyboardCheckClicked() {
        etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(etUsername==null){
                    return;
                }
                if (hasFocus) {
                    etUsername.setHint("");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    Utils.setMargins(llMain,0,(int)Utils.pxFromDp(getActivity(),20),0,0);

                } else {
                    etUsername.setHint("Search Instagram Usernames");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etUsername.getWindowToken(), 0);
                    Utils.setMargins(llMain,0,(int)Utils.pxFromDp(getActivity(),100),0,0);

                }
            }
        });
        etUsername.setOnEditorActionListener(new HurmeRegularObliqueEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here
                    Utils.setMargins(llMain,0,(int)Utils.pxFromDp(getActivity(),100),0,0);
                    if (controlIsSearched(etUsername.getText().toString())) {
                        UserProfileActivity.newIntent((MainActivity) getActivity(), etUsername.getText().toString());
                    } else {
                        onGoClicked();
                    }
                }
                return false;
            }
        });
    }

    private boolean controlIsSearched(String s) {
        for (int i = 0; i < iIInvoices.size(); i++) {
            if (iIInvoices.get(i).getInstaUsername().equals(s)) {
                return true;
            }
        }
        return false;
    }


    private void setRecentSearches(String search) {
        int tmp = 0;
        if (search.length() == 0) {
            llGo.setVisibility(View.VISIBLE);
            llRecentSearches.setVisibility(View.GONE);
            return;
        }
        Set<String> container = new HashSet<>();
        if (((LinearLayout) llContainer).getChildCount() > 0) {
            ((LinearLayout) llContainer).removeAllViews();
        }
        for (int i = 0; i < iIInvoices.size(); i++) {
            if (iIInvoices.get(i).getInstaUsername().toLowerCase().contains(search.toLowerCase()) || search.equals("")) {
                container.add(iIInvoices.get(i).getInstaUsername());
            }
        }
        for (String ss : container) {
            if (!ss.equalsIgnoreCase(SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TRY_FOR_FREE_USERNAME,""))) {
                tmp++;
                if (tmp == 1) {
                    RecentSearchesItem recentSearchesItem = new RecentSearchesItem(getActivity());
                    recentSearchesItem.getParams(etUsername.getText().toString(),tmp);
                    llContainer.addView(recentSearchesItem);
                    tmp++;
                }
                RecentSearchesItem recentSearchesItem = new RecentSearchesItem(getActivity());
                recentSearchesItem.getParams(ss,tmp);
                llContainer.addView(recentSearchesItem);
            }
        }
        if (tmp > 0) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llContainerParent.getLayoutParams();
            if (tmp > 4) {
                params.height = 240;
            } else {
                params.height = tmp * 60;
            }
            llContainerParent.setLayoutParams(params);
            llGo.setVisibility(View.GONE);
            llRecentSearches.setVisibility(View.VISIBLE);
        } else {
            llGo.setVisibility(View.VISIBLE);
            llRecentSearches.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_clear_searches)
    public void clearSearches() {
        llGo.setVisibility(View.VISIBLE);
        llRecentSearches.setVisibility(View.GONE);
    }


    private TextWatcher tw = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // you can check for enter key here
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //if(etUsername.getText().toString().length()==0){
            //    Utils.setMargins(llMain,0,(int)Utils.pxFromDp(getActivity(),100),0,0);
            //}
            String search = etUsername.getText().toString();
            if (iIInvoices != null) {
                if (iIInvoices.size() > 0) {
                    setRecentSearches(search);
                }
            }

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_search_bar)
    public void openKeyboard() {
        etUsername.requestFocus();
        etUsername.setSelection(etUsername.getText().toString().length());
        Utils.setMargins(llMain,0,(int)Utils.pxFromDp(getActivity(),20),0,0);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (SearchBarListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @OnClick(R.id.ll_go)
    public void onGoClicked() {
        if (etUsername.getText().toString().length() > 0) {
            Utils.hideSoftKeyboard(getActivity());
            listener.askUserToSpendCoin(etUsername.getText().toString());
        }

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
        if (message.equals("hide")) {
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                Utils.hideSoftKeyboard(getActivity());
            }
        }
        if (message.equals("showDemo")) {
            etUsername.setText(SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TRY_FOR_FREE_USERNAME,""));
        }
        if(message.equals("IIInvoices")){
            iIInvoices = ((MainActivity) getActivity()).getIiInvoices();

        }
        /*if (message.equals("goDown")) {
            Utils.setMargins(llMain,0,(int)Utils.pxFromDp(getActivity(),100),0,0);
        }*/

    }
}