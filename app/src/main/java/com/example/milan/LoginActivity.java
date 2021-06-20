package com.example.milan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView newUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        newUser=findViewById(R.id.newUser);
        String user=newUser.getText().toString();
        Spannable spannable=new SpannableString(user);
        spannable.setSpan(new ForegroundColorSpan(Color.rgb(63,93,201)),10,user.length(),0);
        spannable.setSpan(new UnderlineSpan(),10,user.length(),0);
        newUser.setText(spannable);
        newUser.setOnClickListener(v->
        {
            Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(intent);
        });
    }
}