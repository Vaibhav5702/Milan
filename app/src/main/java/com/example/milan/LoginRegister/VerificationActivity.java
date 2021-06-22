package com.example.milan.LoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.Toast;

import com.example.milan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class VerificationActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        user=FirebaseAuth.getInstance().getCurrentUser();
        firestore=FirebaseFirestore.getInstance();
        Button button=findViewById(R.id.verify_btn);
        button.setOnClickListener(v -> {
            isVerified();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVerified();
    }

    public void isVerified(){
        user.reload().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                boolean verified=user.isEmailVerified();

                if(verified){
            HashMap<String,String> map=new HashMap();
            map.put("name",getIntent().getStringExtra("name"));
            firestore.collection("Users").document().set(map);
                    Intent intent=new Intent(VerificationActivity.this, InterestActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
            else
                Toast.makeText(VerificationActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
        });
    }
}