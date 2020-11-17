package com.example.edunachal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

public class DisplayCurrentAffairs extends AppCompatActivity {
    String date;
    FragmentManager fragmentManager;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date=getIntent().getStringExtra("date");
        Bundle bundle=new Bundle();
        textView=findViewById(R.id.textView7);
        textView.setText(date);
        bundle.putString("date",date);
        setContentView(R.layout.activity_display_current_affairs);
        CurrentAffairsFragment currentAffairsFragment=new CurrentAffairsFragment();
        currentAffairsFragment.setArguments(bundle);
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment,currentAffairsFragment);
        fragmentTransaction.commit();
    }
}