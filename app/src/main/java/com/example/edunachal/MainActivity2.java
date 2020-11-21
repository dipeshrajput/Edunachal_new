package com.example.edunachal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
    }
    public void  upsc(View view)
    {
        Intent intent=new Intent(MainActivity2.this, UPSC_01.class);
        startActivity(intent);
    }    public void  appsc(View view)
    {
        Intent intent=new Intent(MainActivity2.this, APPSC_01.class);
        startActivity(intent);
    }    public void  appsb(View view)
    {
        Intent intent=new Intent(MainActivity2.this, APPSC01.class);
        startActivity(intent);
    }
    public void  ssc(View view)
    {
        Intent intent=new Intent(MainActivity2.this, ssc_01.class);
        startActivity(intent);
    }
    public  void  logout(View v)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),login.class));
        finish();
    }
    public void  edu(View view)
    {
        Intent intent=new Intent(MainActivity2.this, edu_classes.class);
        startActivity(intent);
    }


    public void discussionChatSend(View view) {
        Intent intent = new Intent(MainActivity2.this, DiscussionChats.class);
        startActivity(intent);
    }
}