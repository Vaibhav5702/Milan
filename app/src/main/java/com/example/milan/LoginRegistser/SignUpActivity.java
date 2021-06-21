package com.example.milan.LoginRegistser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPasswordField;
    private ImageButton mRegisterBtn;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    TextView alreadyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        alreadyUser=findViewById(R.id.alreadyUser);
        mProgress=new ProgressDialog( this );
        mNameField=(EditText) findViewById(R.id.etName);
        mEmailField=(EditText) findViewById(R.id.etEmail);
        mPasswordField=(EditText) findViewById(R.id.etPassword);
        mRegisterBtn=(ImageButton) findViewById(R.id.btnGo);
        mAuth=FirebaseAuth.getInstance();
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
        mRegisterBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=mNameField.getText().toString().trim();
                String email=mEmailField.getText().toString().trim();
                String password=mPasswordField.getText().toString().trim();
                if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){
                    mProgress.setMessage( "registering user" );
                    mProgress.setCanceledOnTouchOutside( true );
                    mProgress.show();
                    startRegister(name,email,password);}
            }
        } );
    }

    private void startRegister(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword( email,password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {  mProgress.dismiss();

                    Intent mainIntent =new Intent(SignUpActivity.this,InterestActivity.class);
                    startActivity( mainIntent );
                }
                else
                {   mProgress.hide();
                    String message=task.getException().toString();
                    Toast.makeText(SignUpActivity.this,message,Toast.LENGTH_LONG).show();

                }
            }
        } );

    }
    }
