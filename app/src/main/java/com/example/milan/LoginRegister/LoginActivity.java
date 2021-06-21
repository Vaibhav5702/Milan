package com.example.milan.LoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milan.ChooseActivity;
import com.example.milan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText mLoginEmailField;
    private EditText mLoginPasswordField;
    private ImageButton mLoginBtn;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    TextView newUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        newUser=findViewById(R.id.newUser);
        mLoginEmailField = findViewById( R.id.etEmail );
        mLoginPasswordField =  findViewById( R.id.etPassword);
        mLoginBtn =  findViewById( R.id.btnGo);
        mAuth = FirebaseAuth.getInstance();
        String user=newUser.getText().toString();
        mProgress = new ProgressDialog( this );
        Spannable spannable=new SpannableString(user);
        spannable.setSpan(new ForegroundColorSpan(Color.rgb(63,93,201)),10,user.length(),0);
        spannable.setSpan(new UnderlineSpan(),10,user.length(),0);
        newUser.setText(spannable);
        newUser.setOnClickListener(v->
        {
            Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
        mLoginBtn.setOnClickListener(view -> {
            String email = mLoginEmailField.getText().toString().trim();
            String password = mLoginPasswordField.getText().toString().trim();
            if (!TextUtils.isEmpty( email ) || !TextUtils.isEmpty( password )) {
                mProgress.setMessage( "logging in" );
                mProgress.setCanceledOnTouchOutside( true );
                mProgress.show();
                loginUser( email, password );
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword( email, password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mProgress.dismiss();
                    Intent mainIntent = new Intent( LoginActivity.this, ChooseActivity.class );
                    mainIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity( mainIntent );
                    finish();
                } else {
                    mProgress.hide();
                    Toast.makeText( LoginActivity.this, "cannot sign in user", Toast.LENGTH_LONG ).show();

                }
            }
        } );
    }

    }
