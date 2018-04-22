package com.kutaycandan.instainsight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.model.IIInvoice;
import com.kutaycandan.instainsight.widget.textview.HurmeRegularTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    int textCode;
    ArrayList<String> names;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if(bundle!=null){
            if(bundle.getInt("textCode")==-1){
                llFooter.setVisibility(View.GONE);
                llHeader.setVisibility(View.GONE);
                setInternetConnectionErrorFragment();
            }
            else if(bundle.getInt("textCode")==1){
                setSearchBarFragment();
            }
            else if(bundle.getInt("textCode")==2){

            }


        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setSearchBarFragment(){
        SearchBarFragment sbf = new SearchBarFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fl_container, sbf);
        transaction.commit();
    }
    private void setInternetConnectionErrorFragment(){
        InternetWarningFragment iwf = new InternetWarningFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fl_container, iwf);
        transaction.commit();
    }

    public static SearchFragment newInstance(int textCode, ArrayList<String> names){

        SearchFragment f = new SearchFragment();
        Bundle b = new Bundle();
        b.putInt("textCode", textCode);
        b.putStringArrayList("names",names);
        f.setArguments(b);
        return f;
    }

}
