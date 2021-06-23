package com.example.milan.TabFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.milan.AnonymousSection.CreateAnonymousRoom;
import com.example.milan.R;
import com.example.milan.RestrictedSection.CreateRestrictedActivity;

import org.jetbrains.annotations.NotNull;

public class RestrictedFragment extends Fragment {

    Context context;
    ImageButton restricted_fab;

    public RestrictedFragment(Context context) {
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_restricted, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restricted_fab=view.findViewById(R.id.restricted_fab);
        restricted_fab.setOnClickListener(v -> startActivity(new Intent(context, CreateRestrictedActivity.class)));
    }
}