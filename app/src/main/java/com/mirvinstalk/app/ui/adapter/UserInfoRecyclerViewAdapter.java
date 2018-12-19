package com.mirvinstalk.app.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mirvinstalk.app.R;
import com.mirvinstalk.app.model.InstaUserModel;
import com.mirvinstalk.app.model.InstaUserModelFollowerCount;
import com.mirvinstalk.app.model.InstaUserModelLikeCount;
import com.mirvinstalk.app.model.InstaUserProfileData;
import com.mirvinstalk.app.ui.activity.UserProfileActivity;
import com.mirvinstalk.app.widget.textview.HurmeBoldTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoRecyclerViewAdapter extends RecyclerView.Adapter<UserInfoRecyclerViewAdapter.ViewHolder>{
    ArrayList<InstaUserModel> mDataSet;
    Context context;

    public UserInfoRecyclerViewAdapter(ArrayList<InstaUserModel> mDataSet,ArrayList<InstaUserModelLikeCount> mDataSet2,ArrayList<InstaUserModelFollowerCount> mDataSet3, Context context){
        if(mDataSet!=null){
            this.mDataSet=mDataSet;
        }
        else if(mDataSet2!=null){
            this.mDataSet=new ArrayList<>();
            for (int i = 0; i <mDataSet2.size() ; i++) {
                InstaUserModel model = new InstaUserModel();
                model.setFullName(mDataSet2.get(i).getFullName());
                model.setType(mDataSet2.get(i).getType());
                model.setUserName(mDataSet2.get(i).getUserName());
                model.setProfilePicture(mDataSet2.get(i).getProfilePicture());
                this.mDataSet.add(model);
            }
        }
        else if(mDataSet3!=null){
            this.mDataSet=new ArrayList<>();
            for (int i = 0; i <mDataSet3.size() ; i++) {
                InstaUserModel model = new InstaUserModel();
                model.setFullName(mDataSet3.get(i).getFullName());
                model.setType(mDataSet3.get(i).getType());
                model.setUserName(mDataSet3.get(i).getUserName());
                model.setProfilePicture(mDataSet3.get(i).getProfilePicture());
                this.mDataSet.add(model);
            }
        }
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
                if(mDataSet.get(position).getType().equals("public")){
                    ((UserProfileActivity) context).searchUserInRV(mDataSet.get(position).getUserName());
                }else{
                    Toast.makeText(context, "Sorry, you can’t stalk this profile" +
                            " as it is private.", Toast.LENGTH_SHORT).show();
                }
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

    @Override
    public int getItemViewType(int position) {
        return position;
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
