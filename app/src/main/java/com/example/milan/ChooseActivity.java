package com.example.milan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class ChooseActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        adapter=new PagerAdapter(this);

        viewPager.setAdapter(adapter);
        TabLayoutMediator mediator=new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position){
                        case 0:
                            tab.setText("Interests");
                            break;
                        case 1:
                            tab.setText("Anonymous");
                            break;
                        case 2:
                            tab.setText("Restricted");
                            break;
                    }
                });
        mediator.attach();
    }
}