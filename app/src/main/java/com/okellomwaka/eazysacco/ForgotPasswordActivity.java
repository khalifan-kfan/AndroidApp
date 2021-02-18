package com.okellomwaka.eazysacco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    //declare elements
    TextInputEditText forgotEmail;
    Button resetEmail;
    ProgressBar progressBar;
    //declare the firebase
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //intialize auth
        auth = FirebaseAuth.getInstance();
        //ref our views
        forgotEmail = findViewById(R.id.resetEmail);
        resetEmail = findViewById(R.id.btnResetPassword);
        progressBar = findViewById(R.id.progressBar);

        resetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });
    }

    //forgot password
    private void forgotPassword() {
        //pick users input
        String forgot_email = forgotEmail.getText().toString().trim();
        //check
        if (TextUtils.isEmpty(forgot_email)){
            Toast.makeText(this, "Email Field cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            run(forgot_email);
        }
    }

    private void run(String forgot_email) {
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(forgot_email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                if (!task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, "Password reset Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Password reset link sent to email", Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateUI();
    }
    private void updateUI() {
        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void back2Login(View v){
        Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
        startActivity(intent);
    }

}