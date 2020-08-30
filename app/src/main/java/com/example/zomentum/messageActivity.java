package com.example.zomentum;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zomentum.adapter.messageadapter;
import com.example.zomentum.model.Chat;
import com.example.zomentum.model.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class messageActivity extends AppCompatActivity
{
    CircleImageView profile_image;
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference dbref;
    ImageButton img;
    EditText e1;
    messageadapter messageadapter;
    List<Chat> mchat;
    RecyclerView recyclerView;
    Intent intent;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar=findViewById(R.id.tl);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        profile_image=findViewById(R.id.profileimage);
        username=findViewById(R.id.user);
        img=findViewById(R.id.img1);
        recyclerView=findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        e1=findViewById(R.id.edi1);
        intent =getIntent();
        final String userid=intent.getStringExtra("userid");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String msg=e1.getText().toString();
                if(!msg.equals(""))
                {
                    sendmessage(firebaseUser.getUid(),userid,msg);
                }
                else
                {
                    Toast.makeText(messageActivity.this,"You can not send empty message",Toast.LENGTH_LONG).show();
                }
                e1.setText("");

            }
        });

        dbref= FirebaseDatabase.getInstance().getReference("users").child(userid);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                user user=dataSnapshot.getValue(com.example.zomentum.model.user.class);
                username.setText(user.getUsername());
                if(user.getImageurl().equals("default"))
                {
                    profile_image.setImageResource(R.mipmap.ic_launcher);

                }
                else
                {
                    Glide.with(messageActivity.this).load(user.getImageurl()).into(profile_image);
                }
                readmessage(firebaseUser.getUid(),userid,user.getImageurl());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    private void sendmessage(String sender,String reciever,String message)
    {
        DatabaseReference dbref=FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object>hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("reciever",reciever);
        hashMap.put("message",message);
        dbref.child("chats").push().setValue(hashMap);
    }
    private void readmessage(final String myid, final String userid, final String imageurl)
    {
        mchat=new ArrayList<>();
        dbref=FirebaseDatabase.getInstance().getReference("chats");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                mchat.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Chat chat=snapshot.getValue(Chat.class);
                    if(chat.getReciever().equals(myid) && chat.getSender().equals(userid)
                    || chat.getReciever().equals(userid) && chat.getSender().equals(myid))
                    {
                        mchat.add(chat);
                    }
                    messageadapter=new messageadapter(messageActivity.this,mchat,imageurl);
                    recyclerView.setAdapter(messageadapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}