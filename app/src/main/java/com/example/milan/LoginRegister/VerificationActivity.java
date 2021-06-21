package com.example.milan.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.Toast;

import com.example.milan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class VerificationActivity extends AppCompatActivity {
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        user=FirebaseAuth.getInstance().getCurrentUser();
        Button button=findViewById(R.id.verify_btn);
        button.setOnClickListener(v -> isVerified());
    }

    public void isVerified(){
        user.reload();
        boolean verified=user.isEmailVerified();

        if(verified){
            Intent intent=new Intent(VerificationActivity.this, InterestActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}