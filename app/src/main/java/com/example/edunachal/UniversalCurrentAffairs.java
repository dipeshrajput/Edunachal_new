package com.example.edunachal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.edunachal.PagerAdaptor.ViewPagerAdaptor;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UniversalCurrentAffairs extends AppCompatActivity {
    Toolbar toolbar;
    DatabaseReference databaseReference;
    TabLayout tabLayout;
    List<String> dateList;
    ProgressBar progressBar;
    ViewPager2 viewPager2;
    ViewPagerAdaptor viewPagerAdaptor;
    List<String> allDates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universal_current_affairs);
        progressBar=findViewById(R.id.progressBar2);
        viewPager2=findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.appBarCurrent);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Current Affairs");
        tabLayout=findViewById(R.id.tabLayout);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                tabLayout.selectTab(tab,true);
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference().child("current_affairs").child("universal");
        databaseReference.orderByChild("timestamp").limitToLast(7).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dateList=new ArrayList<>();
                if(snapshot.exists())
                {
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        String dateofcur = ds.getKey();
                        dateList.add(dateofcur);
                    }
                    Collections.reverse(dateList);
                    for(String mDate:dateList)
                    {
                        tabLayout.addTab(tabLayout.newTab().setText(mDate.replace(" ","/")));
                    }
                    viewPagerAdaptor=new ViewPagerAdaptor(UniversalCurrentAffairs.this,dateList);
                    viewPager2.setAdapter(viewPagerAdaptor);
                }
                else
                {
                    Toast.makeText(UniversalCurrentAffairs.this, "No data found", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UniversalCurrentAffairs.this, "Error occurred", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.calendar)
                {
                    allDates=new ArrayList<>();
                    progressBar.setVisibility(View.VISIBLE);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                allDates=new ArrayList<>();
                                for(DataSnapshot ds:snapshot.getChildren())
                                {
                                    String dateofcur = ds.getKey();
                                    allDates.add(dateofcur);
                                }
                                Calendar calendar = Calendar.getInstance();
                                DatePickerDialog datePickerDialog = new DatePickerDialog(UniversalCurrentAffairs.this, new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        String date=dayOfMonth+" "+(month+1)+" "+year;
                                        if(allDates.contains(new String(date))){
                                            Intent intent=new Intent(UniversalCurrentAffairs.this,DisplayCurrentAffairs.class);
                                            intent.putExtra("date", date);
                                            startActivity(intent);
                                        }
                                        else{
                                           Toast.makeText(UniversalCurrentAffairs.this, "No current affairs available for this date", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                                datePickerDialog.show();
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(UniversalCurrentAffairs.this, "An Error Occured", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}