package com.kutaycandan.instainsight.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.widget.edittext.HurmeRegularObliqueEditText;

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
    @BindView(R.id.rv_recent_users)
    RecyclerView rvRecentUsers;
    @BindView(R.id.ll_recent_searches)
    LinearLayout llRecentSearches;
    SearchBarListener listener;
    public interface SearchBarListener{
        public void askUserToSpendCoin(String username);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_bar, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        //if(bundle!=null){
        //    et.
        //}
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (SearchBarListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    @OnClick(R.id.ll_go)
    public void onViewClicked() {
        listener.askUserToSpendCoin(etUsername.getText().toString());
    }
}
