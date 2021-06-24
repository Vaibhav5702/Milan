package com.example.milan.LoginRegister;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;

import com.example.milan.ChooseActivity;
import com.example.milan.R;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class InterestActivity extends AppCompatActivity implements InterestAdapter.ItemClick {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Interest> interestArrayList;
    ImageView proceed_btn;
    public int  i=0;
    ArrayList<String> interestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        proceed_btn=findViewById(R.id.proceed_btn);
        recyclerView=findViewById(R.id.interest_rv);
        interestList=new ArrayList();
        createList();
        layoutManager=new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        adapter=new InterestAdapter(this,interestArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        proceed_btn.setOnClickListener(v -> {
            String userId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            FirebaseFirestore.getInstance().collection("Users").document(userId)
                    .update("interests",interestList).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Intent intent=new Intent(InterestActivity.this, ChooseActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
        });
    }
    public void createList()
    {
        interestArrayList=new ArrayList<>();
        interestArrayList.add(new Interest("Music", Color.parseColor("#B2DFDB")));
        interestArrayList.add(new Interest("Poetry",Color.parseColor("#FFCDD2")));
        interestArrayList.add(new Interest("Sports",Color.parseColor("#FF8C8C")));
        interestArrayList.add(new Interest("Literature",Color.parseColor("#90A4AE")));
        interestArrayList.add(new Interest("Games",Color.parseColor("#BCABE1")));
        interestArrayList.add(new Interest("Movies",Color.parseColor("#B9F6CA")));
        interestArrayList.add(new Interest("Debate",Color.parseColor("#92C6C6")));
        interestArrayList.add(new Interest("Technology",Color.parseColor("#C5CAE9")));
        interestArrayList.add(new Interest("Comedy",Color.parseColor("#F0A9C2")));
        interestArrayList.add(new Interest("Fashion",Color.parseColor("#DDE398")));
    }

    @Override
    public void onItemClick(int pos, boolean onTap) {
        if(i>=2){
            proceed_btn.setVisibility(View.VISIBLE);
        }
        else
            proceed_btn.setVisibility(View.GONE);
        if(onTap){
            i++;
            interestList.add(interestArrayList.get(pos).getInterest());
        }
        else {
            i--;
            interestList.remove(interestArrayList.get(pos).getInterest());
        }
    }
}