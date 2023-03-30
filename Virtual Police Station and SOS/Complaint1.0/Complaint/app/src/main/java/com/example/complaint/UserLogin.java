package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.hbb20.CountryCodePicker;

public class UserLogin extends AppCompatActivity {
    private EditText etUserEmail,etUserPwd;
    private Button btnUserLogin;
    private TextView tvSignup;
    private ProgressDialog loader;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mAuth = FirebaseAuth.getInstance();

        etUserEmail=findViewById(R.id.etUserEmail);
        etUserPwd=findViewById(R.id.etUserPwd);
        btnUserLogin=findViewById(R.id.btnUserLogin);
        tvSignup=findViewById(R.id.tvSignup);

        btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=etUserEmail.getText().toString().trim();
                String pass=etUserPwd.getText().toString().trim();

                signinwithemail(email,pass);
            }
        });
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),UserRegister.class);
                startActivity(intent);
            }
        });
    }

    private void signinwithemail(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UserLogin.this, "User Logged in Successful...", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),UserMain.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(UserLogin.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(),UserMain.class);
            startActivity(intent);
        }
    }
}