package com.example.milan.TabFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.milan.InterestSection.InterestDetails;
import com.example.milan.InterestSection.InterestDetailsAdapter;
import com.example.milan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class InterestsFragment extends Fragment {

    Context context;
    ArrayList<InterestDetails> list;
    public InterestsFragment(Context context) {
        this.context=context;
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_interests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list=new ArrayList<>();
        recyclerView=view.findViewById(R.id.interestsRv);
        layoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        adapter=new InterestDetailsAdapter(context,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        createList();

    }
    public void createList()
    {
        String uid= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        FirebaseFirestore.getInstance().collection("Users").document(uid).get()
                .addOnCompleteListener(task -> {
                   if(task.isSuccessful())
                   {
                       DocumentSnapshot snapshot=task.getResult();
                       assert snapshot != null;
                       for(String interest:(ArrayList<String>) Objects.requireNonNull(snapshot.get("interests")))
                       {

                           list.add(new InterestDetails(interest,4));
                       }
                       adapter.notifyDataSetChanged();
                   }
                });
    }

}