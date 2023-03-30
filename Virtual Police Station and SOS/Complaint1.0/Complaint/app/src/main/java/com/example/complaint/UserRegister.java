package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserRegister extends AppCompatActivity {
    private EditText etRegUserEmail,etRegUserPwd,etRegUserName,etRegUserMobile,etRegUserAddress;
    private Button btnUserReg;
    private TextView tvSignin;
    private ProgressDialog loader;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        mAuth = FirebaseAuth.getInstance();

        etRegUserEmail=findViewById(R.id.etRegUserEmail);
        etRegUserPwd=findViewById(R.id.etRegUserPwd);
        etRegUserName=findViewById(R.id.etRegUserName);
        etRegUserMobile=findViewById(R.id.etRegUserMobile);
        etRegUserAddress=findViewById(R.id.etRegUserAddress);
        btnUserReg=findViewById(R.id.btnRegUser);

        btnUserReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=etRegUserEmail.getText().toString().trim();
                String pass=etRegUserPwd.getText().toString().trim();
                String name=etRegUserName.getText().toString().trim();
                String mobile=etRegUserMobile.getText().toString().trim();
                String address=etRegUserAddress.getText().toString().trim();
                signinwithemail(email,pass,name,mobile,address);
            }
        });
    }

    private void signinwithemail(String email, String pass, String name, String mobile, String address) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String currentUserId=mAuth.getCurrentUser().getUid();
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
                    HashMap userInfo=new HashMap();
                    userInfo.put("id",currentUserId);
                    userInfo.put("email",email);
                    userInfo.put("name",name);
                    userInfo.put("mobile",mobile);
                    userInfo.put("address",address);


                    databaseReference.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                Toast.makeText(UserRegister.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(UserRegister.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                            finish();

                        }
                    });
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