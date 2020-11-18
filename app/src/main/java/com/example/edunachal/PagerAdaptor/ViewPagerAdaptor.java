package com.example.edunachal.PagerAdaptor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.edunachal.CurrentAffairsFragment;

import java.util.List;

public class ViewPagerAdaptor extends FragmentStateAdapter {
    List<String> dateList;

    public ViewPagerAdaptor(@NonNull FragmentActivity fragmentActivity,List<String> dateList) {
        super(fragmentActivity);
        this.dateList=dateList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("date",dateList.get(position));
        CurrentAffairsFragment currentAffairsFragment=new CurrentAffairsFragment();
        currentAffairsFragment.setArguments(bundle);
        return currentAffairsFragment;
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }
}
