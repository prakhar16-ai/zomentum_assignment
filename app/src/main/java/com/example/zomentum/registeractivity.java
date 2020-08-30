package com.example.zomentum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.rengwuxian.materialedittext.MaterialEditText;

public class registeractivity extends AppCompatActivity
{
    MaterialEditText username,email,password;
    TextView t;
    Button b1;
    FirebaseAuth auth;
    DatabaseReference dbref;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.chat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        username=findViewById(R.id.user);
        email=findViewById(R.id.email);
        password=findViewById(R.id.pass);
        t=findViewById(R.id.txt1);
        b1=findViewById(R.id.bt1);
        auth=FirebaseAuth.getInstance();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username=username.getText().toString();
                String txt_email=email.getText().toString();
                String txt_password=password.getText().toString();
                if(TextUtils.isEmpty(txt_username)|| TextUtils.isEmpty(txt_email)|| TextUtils.isEmpty(txt_password))
                {
                    Toast.makeText(registeractivity.this,"All entries are compulsory",Toast.LENGTH_LONG).show();
                }
                else if (txt_password.length()<6)
                {
                    Toast.makeText(registeractivity.this,"Password length is too short",Toast.LENGTH_LONG).show();
                }
                else
                {
                    register(txt_username,txt_email,txt_password);
                }


            }
        });

    }

    private void register(final  String username, String email, String password)
    {
        auth.createUserWithEmailAndPassword(email ,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(registeractivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}