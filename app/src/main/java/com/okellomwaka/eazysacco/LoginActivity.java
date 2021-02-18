package com.okellomwaka.eazysacco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    //declare our elements

    EditText email,pass;
    Button btnLogin;
    //declare your Firebase Auth
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        //intialize firebase app
        FirebaseApp.initializeApp(this);

        //intialize the firebase auth
        auth = FirebaseAuth.getInstance();
        //ref ur views
        email = findViewById(R.id.etEmail);
        pass = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);

        //onclicklistener for login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    private void login() {
        //store the users input
        String login_email = email.getText().toString().trim();
        String login_pass = pass.getText().toString().trim();
        //check if the entries b4 login in the user
        if (TextUtils.isEmpty(login_email)){
            Toast.makeText(this, "Email field must not be empty", Toast.LENGTH_SHORT).show();
        } else {
            run(login_email,login_pass);
        }


        if (TextUtils.isEmpty(login_pass)){
            Toast.makeText(this, "Password field must not be empty", Toast.LENGTH_SHORT).show();
        } else {
            run(login_email,login_pass);
        }
    }


    //login in my user
    private void run(String login_email, String login_pass) {
        //show the progress indication
        progressBar.setVisibility(View.VISIBLE);
        //login
        auth.signInWithEmailAndPassword(login_email,login_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (!task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login In User Failed", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(LoginActivity.this, "Login In User", Toast.LENGTH_SHORT).show();

                }

            }
        });
        updateUi();
    }

    private void updateUi() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
     //   Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
        startActivity(intent);
    }


    public void forgotPassword(View v){
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
    public void signUp(View v){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

}