package com.kutaycandan.instainsight.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.model.InstaUserModel;
import com.kutaycandan.instainsight.model.InstaUserProfileData;
import com.kutaycandan.instainsight.ui.activity.UserProfileActivity;
import com.kutaycandan.instainsight.widget.textview.HurmeBoldTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoRecyclerViewAdapter extends RecyclerView.Adapter<UserInfoRecyclerViewAdapter.ViewHolder>{
    ArrayList<InstaUserModel> mDataSet;
    Context context;

    public UserInfoRecyclerViewAdapter(ArrayList<InstaUserModel> mDataSet, Context context){
        this.mDataSet=mDataSet;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_line,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserProfileActivity) context).searchUserInRV(mDataSet.get(position).getUserName());
            }
        });
        holder.tvFullname.setText(mDataSet.get(position).getFullName());
        Picasso.with(context)
                .load(mDataSet.get(position).getProfilePicture())
                .into(holder.civPhoto);
        if(mDataSet.get(position).getType()!=null){
            if(mDataSet.get(position).getType().equals("public")){
                holder.rlPrivate.setVisibility(View.GONE);
            }else{
                holder.rlPublic.setVisibility(View.GONE);
            }
        }
        else{
            holder.rlPublic.setVisibility(View.GONE);
            holder.rlPrivate.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civPhoto;
        RelativeLayout rlPublic;
        RelativeLayout rlPrivate;
        HurmeBoldTextView tvFullname;

        public ViewHolder(View view){
            super(view);
            civPhoto = view.findViewById(R.id.civ_profile_image);
            rlPrivate = view.findViewById(R.id.rl_private);
            rlPublic = view.findViewById(R.id.rl_public);
            tvFullname = view.findViewById(R.id.tv_fullname);

        }

    }

}
