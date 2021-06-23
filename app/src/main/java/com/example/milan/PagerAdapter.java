package com.example.milan;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.milan.InterestSection.InterestDetails;
import com.example.milan.TabFragments.AnonymousFragment;
import com.example.milan.TabFragments.InterestsFragment;
import com.example.milan.TabFragments.RestrictedFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStateAdapter {
    Context context;
    public PagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        context=fragmentActivity;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new InterestsFragment(context);
            case 1:
                return new AnonymousFragment(context);
            default:
                return new RestrictedFragment(context);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
