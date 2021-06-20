package com.example.milan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class InterestActivity extends AppCompatActivity implements InterestAdapter.ItemClick {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Interest> interestArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        recyclerView=findViewById(R.id.interest_rv);
        createList();
        layoutManager=new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        adapter=new InterestAdapter(this,interestArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
    public void createList()
    {
        interestArrayList=new ArrayList<>();
        interestArrayList.add(new Interest("music"));
        interestArrayList.add(new Interest("poetry"));
        interestArrayList.add(new Interest("sports"));
        interestArrayList.add(new Interest("literature"));
        interestArrayList.add(new Interest("games"));
        interestArrayList.add(new Interest("movies"));
        interestArrayList.add(new Interest("debate"));
        interestArrayList.add(new Interest("technology"));
        interestArrayList.add(new Interest("comedy"));
        interestArrayList.add(new Interest("fashion"));
    }

    @Override
    public void onItemClick(int i) {

    }
}