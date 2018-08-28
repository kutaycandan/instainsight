package com.kutaycandan.instainsight.ui.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.model.InstaUserModel;
import com.kutaycandan.instainsight.model.InstaUserModelFollowerCount;
import com.kutaycandan.instainsight.model.InstaUserModelLikeCount;
import com.kutaycandan.instainsight.model.InstaUserProfileData;
import com.kutaycandan.instainsight.model.LatestPopularPostDto;
import com.kutaycandan.instainsight.ui.activity.UserProfileActivity;
import com.kutaycandan.instainsight.widget.textview.HurmeBoldTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LatestPopularPostRecyclerViewAdapter extends RecyclerView.Adapter<LatestPopularPostRecyclerViewAdapter.ViewHolder>{
    ArrayList<LatestPopularPostDto> mDataSet;
    Context context;

    public LatestPopularPostRecyclerViewAdapter(ArrayList<LatestPopularPostDto> mDataSet, Context context){
        this.mDataSet=mDataSet;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_latest_popular_post,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvComment.setText(mDataSet.get(position).getCommentCount());
        holder.tvLike.setText(mDataSet.get(position).getLikeCount());
        Glide.with(context)
                .load(mDataSet.get(position).getUrl())
                .into(holder.ivContainer);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivContainer;
        TextView tvLike;
        TextView tvComment;

        public ViewHolder(View view){
            super(view);
            ivContainer = view.findViewById(R.id.iv_container);
            tvLike = view.findViewById(R.id.tv_like);
            tvComment = view.findViewById(R.id.tv_comment);

        }

    }

}
