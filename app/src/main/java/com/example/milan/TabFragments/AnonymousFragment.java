package com.example.milan.TabFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.milan.AnonymousSection.CreateAnonymousRoom;
import com.example.milan.InterestSection.CreateInterestRoom;
import com.example.milan.R;
import com.example.milan.RestrictedSection.RestrictedRoomAdapter;
import com.example.milan.RoomDetails;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AnonymousFragment extends Fragment {

    Context context;
    ImageButton anonymous_fab;
    ArrayList<RoomDetails> list;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public AnonymousFragment(Context context) {
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anonymous, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anonymous_fab=view.findViewById(R.id.anonymous_fab);
        anonymous_fab.setOnClickListener(v -> startActivity(new Intent(context, CreateAnonymousRoom.class)));

        list=new ArrayList<>();
        recyclerView=view.findViewById(R.id.anonymousRv);
        layoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        adapter=new RestrictedRoomAdapter(context,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        createList();
    }
    private void createList() {
        FirebaseFirestore.getInstance().collection("Anonymous").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        QuerySnapshot snapshots=task.getResult();

                        for (QueryDocumentSnapshot snapshot : snapshots){
                            list.add(new RoomDetails(snapshot.get("roomName").toString(),
                                    snapshot.get("category").toString(),"anonymous"));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}