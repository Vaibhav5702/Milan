package com.example.milan;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.milan.TabFragments.AnonymousFragment;
import com.example.milan.TabFragments.InterestsFragment;
import com.example.milan.TabFragments.RestrictedFragment;

import org.jetbrains.annotations.NotNull;

public class PagerAdapter extends FragmentStateAdapter {

    public PagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new InterestsFragment();
            case 1:
                return new AnonymousFragment();
            default:
                return new RestrictedFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
