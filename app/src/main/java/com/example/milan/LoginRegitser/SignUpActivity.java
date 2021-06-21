package com.example.milan.LoginRegitser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.example.milan.R;

public class SignUpActivity extends AppCompatActivity {

    TextView alreadyUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        alreadyUser=findViewById(R.id.alreadyUser);
        String user=alreadyUser.getText().toString();
        Spannable spannable=new SpannableString(user);
        spannable.setSpan(new ForegroundColorSpan(Color.rgb(63,93,201)),16,user.length(),0);
        spannable.setSpan(new UnderlineSpan(),16,user.length(),0);
        alreadyUser.setText(spannable);
        alreadyUser.setOnClickListener(v->
        {
            Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
            startActivity(intent);
        });
    }
}