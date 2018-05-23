package com.kutaycandan.instainsight.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.ui.activity.MainActivity;
import com.kutaycandan.instainsight.ui.activity.UserProfileActivity;
import com.kutaycandan.instainsight.widget.textview.HurmeBoldTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecentSearchesItem extends FrameLayout {
    Context context;
    @BindView(R.id.tv_username)
    HurmeBoldTextView tvUsername;

    String username;
    public RecentSearchesItem(@NonNull Context context) {
        super(context);
        init(context);
    }

    public RecentSearchesItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public RecentSearchesItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        layoutInflater.inflate(R.layout.item_recent_searches, this);
        ButterKnife.bind(this);
        this.context = context;

    }

    public void getParams(String username) {
        this.username=username;
        final String userNamee = username;
        tvUsername.setText("@"+username);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    UserProfileActivity.newIntent((MainActivity)context,userNamee);
                }
                catch (Exception e){
                    Log.d("lol", "performClick: problem");
                }
            }
        });
    }


}
