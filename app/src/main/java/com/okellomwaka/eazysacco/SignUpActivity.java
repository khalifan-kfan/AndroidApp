package com.okellomwaka.eazysacco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class SignUpActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    EditText mEmail;
    EditText mPassword;
    EditText  mMobile;
    EditText mUserName;
    Button mSignUp;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        mProgressBar=findViewById(R.id.progressBar);
        mEmail=findViewById(R.id.signupEmail);
        mMobile=findViewById(R.id.signupMobile);
        mPassword=findViewById(R.id.signUpPass);
        mUserName=findViewById(R.id.signupName);
        mSignUp=findViewById(R.id.btnCreateAccount);

        //intialize our firebaseapp
        FirebaseApp.initializeApp(this);
        //Establishhing Firebaase connection
        mAuth = FirebaseAuth.getInstance();

        //here register user to firebase
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
       /*       mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword()
            }
        }); */

    }

    //sign up user using firebase
    private void signUpUser() {
        //get user input
        //pick the users input and store the inputs in variables
        String signed_name = mUserName.getText().toString().trim();
        String signed_email = mEmail.getText().toString().trim();
        String signed_pass = mPassword.getText().toString().trim();
        String singed_Tel =mMobile.getText().toString().trim();

        //validate the entries
        //check if the inputs are there
        if (TextUtils.isEmpty(signed_email)){
            Toast.makeText(this, "Email field cannot be empty", Toast.LENGTH_LONG).show();
        } else {
            run(signed_email,signed_pass);
        }
        if (TextUtils.isEmpty(signed_pass)) {
            Toast.makeText(this, "Password field cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            run(signed_email,signed_pass);

        }

        if (signed_pass.length() < 4){
            Toast.makeText(this, "Password should be four characters or more", Toast.LENGTH_SHORT).show();
        } else {
            run(signed_email,signed_pass);

        }
    }

    private void run(String signed_email, String signed_pass) {
        //if the above checks are satisfied
        //create the user
        //firebase method ,createUserWithEmailAndPassword()
        //show the progress indication
        mProgressBar.setVisibility(View.VISIBLE);
        //create user


        mAuth.createUserWithEmailAndPassword(signed_email,signed_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mProgressBar.setVisibility(View.GONE);
                if (!task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Account creation Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpActivity.this, "Account creation in progress", Toast.LENGTH_SHORT).show();

                }

            }
        });
        updateUi();
    }

    private void updateUi() {
        Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void load_login(View v){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}