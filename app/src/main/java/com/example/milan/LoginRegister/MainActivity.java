package com.example.milan.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import com.example.milan.ChooseActivity;
import com.example.milan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeActivity();
    }

    public void changeActivity(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent= new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else{
            if(!user.isEmailVerified()){
                Intent intent= new Intent(MainActivity.this, VerificationActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent= new Intent(MainActivity.this, ChooseActivity.class);
                startActivity(intent);
            }
        }
    }
}