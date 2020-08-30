package com.example.zomentum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zomentum.fragments.chatFragment;
import com.example.zomentum.fragments.usersFragment;
import com.example.zomentum.model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
{
      CircleImageView profileimage;
      TextView t;
      FirebaseUser firebaseUser;
      DatabaseReference dbref;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.chat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        profileimage=findViewById(R.id.profileimage);
        t=findViewById(R.id.user);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        dbref= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
               user user=dataSnapshot.getValue(user.class);
               t.setText(user.getUsername());
               if(user.getImageurl().equals("default"))
               {
                   profileimage.setImageResource(R.mipmap.ic_launcher);
               }
               else
               {
                   Glide.with(MainActivity.this).load(user.getImageurl()).into(profileimage);
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        TabLayout tabLayout=findViewById(R.id.tbl);

        ViewPageadapter viewPageadapter=new ViewPageadapter(getSupportFragmentManager());

        viewPageadapter.addFragment(new chatFragment(),"Chats");
        viewPageadapter.addFragment(new usersFragment(),"Users");







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,startActivity.class));
                finish();
                return  true;

        }
        return  false;
    }
    class ViewPageadapter extends FragmentPagerAdapter
    {
        private ArrayList<Fragment>fragments;
        private ArrayList<String>titles;
        ViewPageadapter(FragmentManager fragmentManager)
        {
            super(fragmentManager);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();

        }


        @NonNull
        @Override
        public Fragment getItem(int position)
        {
            return fragments.get(position);
        }

        @Override
        public int getCount()
        {
            return fragments.size();
        }
        public  void addFragment(Fragment fragment,String title)
        {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position)
        {
            return titles.get(position);
        }
    }

}