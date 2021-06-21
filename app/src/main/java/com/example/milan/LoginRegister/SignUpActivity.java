package com.example.milan.LoginRegister;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        mRegisterBtn.setOnClickListener(view -> {
            String name=mNameField.getText().toString().trim();
            String email=mEmailField.getText().toString().trim();
            String password=mPasswordField.getText().toString().trim();
            if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){
                mProgress.setMessage( "Registering" );
                mProgress.setCanceledOnTouchOutside( true );
                mProgress.show();
                startRegister(name,email,password);
            }
        });
    }

    private void startRegister(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword( email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {  mProgress.dismiss();
               FirebaseUser user=mAuth.getCurrentUser();
                assert user != null;
                user.sendEmailVerification().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()){
                        Intent mainIntent =new Intent(SignUpActivity.this,VerificationActivity.class);
                        startActivity( mainIntent );
                    }
                    else{
                        Toast.makeText(this, "Some Error Occurred while creating" +
                                "your account", Toast.LENGTH_SHORT).show();
                    }
                });

            }
            else
            {   mProgress.hide();
                Toast.makeText(this, "Some Error Occurred while creating" +
                        "your account", Toast.LENGTH_SHORT).show();
            }
        });

    }
    }
