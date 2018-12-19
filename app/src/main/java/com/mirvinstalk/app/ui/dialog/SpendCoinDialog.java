package com.mirvinstalk.app.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirvinstalk.app.R;
import com.mirvinstalk.app.ui.activity.UserProfileActivity;
import com.mirvinstalk.app.widget.textview.HurmeBoldTextView;
import com.mirvinstalk.app.widget.textview.HurmeRegularTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SpendCoinDialog extends DialogFragment {
    Unbinder unbinder;
    @BindView(R.id.tv_cancel)
    HurmeRegularTextView tvCancel;
    @BindView(R.id.tv_accept)
    HurmeBoldTextView tvAccept;

    String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_spend_coin, null);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username", "");
        }
        return view;
    }
    public void goUserProfile(){
        if(!username.equals("")){
            UserProfileActivity.newIntent(getActivity(),username);
        }
        dismiss();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static SpendCoinDialog newInstance(String s) {
        SpendCoinDialog f = new SpendCoinDialog();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("username", s);
        f.setArguments(args);
        return f;
    }

    @OnClick({R.id.tv_cancel, R.id.tv_accept})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_accept:
                goUserProfile();
                break;
        }
    }
}
