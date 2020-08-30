package com.example.zomentum.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zomentum.R;
import com.example.zomentum.messageActivity;
import com.example.zomentum.model.user;

import java.util.List;

public class useradapter extends RecyclerView.Adapter<useradapter.ViewHolder>
{
    private Context mcontext;
    private List<user> muser;

    public useradapter(Context context,List<useradapter>user)
    {
        this.muser=muser;
        this.mcontext=mcontext;
    }

    public useradapter() {
    }

    @NonNull
    @Override
    public useradapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.user_list,parent,false);
        return  new useradapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final user user=muser.get(position);
        holder.username.setText(user.getUsername());
        if(user.getImageurl().equals("default"))
        {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);

        }
        else
        {
            Glide.with(mcontext).load(user.getImageurl()).into(holder.profile_image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(mcontext, messageActivity.class);
                intent.putExtra("userid",user.getId());
                mcontext.startActivity(intent);
            }
        });



    }



    @Override
    public int getItemCount()
    {
        return muser.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView username;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.user);
            profile_image=itemView.findViewById(R.id.profileimage);
        }
    }
}
