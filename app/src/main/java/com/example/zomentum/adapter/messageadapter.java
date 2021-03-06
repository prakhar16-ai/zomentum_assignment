package com.example.zomentum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zomentum.R;
import com.example.zomentum.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

    public class messageadapter extends RecyclerView.Adapter<messageadapter.ViewHolder>
    {
        public static final int MSG_TYPE_LEFT=0;
        public static final int MSG_TYPE_RIGHT=0;
        private Context mcontext;
        private List<Chat> mchat;
        private String imageurl;
        FirebaseUser fuser;
        private ViewGroup parent;
        private int viewType;
        private ViewHolder holder;
        private int position;

        public messageadapter(Context context,List<Chat>mchat,String imageurl)
        {
            this.mchat=mchat;
            this.mcontext=mcontext;
            this.imageurl=imageurl;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            if(viewType==MSG_TYPE_RIGHT) {
                View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_item_right, parent, false);
                return new messageadapter.ViewHolder(view);
            }
            else
            {
                View view=LayoutInflater.from(mcontext).inflate(R.layout.chat_item_left,parent,false);
                return new messageadapter.ViewHolder(view);
            }
        }



        @Override
        public void onBindViewHolder(@NonNull messageadapter.ViewHolder holder, int position)
        {
            this.holder = holder;
            this.position = position;
            Chat chat=mchat.get(position);
                 holder.show_message.setText(chat.getMessage());
                 if(imageurl.equals("default"))
                 {
                     holder.profile_image.setImageResource(R.mipmap.ic_launcher);
                 }
                 else
                 {
                     Glide.with(mcontext).load(imageurl).into(holder.profile_image);
                 }
        }





        @Override
        public int getItemCount()
        {
            return mchat.size();

        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            public TextView show_message;
            public ImageView profile_image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                show_message=itemView.findViewById(R.id.text1);
                profile_image=itemView.findViewById(R.id.profile);
            }
        }

        @Override
        public int getItemViewType(int position)
        {
            fuser= FirebaseAuth.getInstance().getCurrentUser();
            if(mchat.get(position).getSender().equals(fuser.getUid()))
            {
                return MSG_TYPE_RIGHT;
            }
            else
            {
                return MSG_TYPE_LEFT;
            }
        }
    }

