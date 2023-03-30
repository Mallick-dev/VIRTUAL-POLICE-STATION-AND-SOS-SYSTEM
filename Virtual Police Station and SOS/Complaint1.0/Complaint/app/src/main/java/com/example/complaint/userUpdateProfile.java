package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.complaint.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class userUpdateProfile extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    FirebaseDatabase firebaseDatabase;
    private TextView tvWelcome;
    private Button btnUpdate;
    private EditText etName,etMobile,etAddress,etEmergency,etAdhaar,etGender,etAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_profile);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid=currentUser.getUid();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference().child("users").child(uid);

        tvWelcome=findViewById(R.id.tvWelcome);
        btnUpdate=findViewById(R.id.btnUpdate);
        etName=findViewById(R.id.etName);
        etMobile=findViewById(R.id.etMobile);
        etAddress=findViewById(R.id.etAddress);
        etEmergency=findViewById(R.id.etEmergency);
        etAdhaar=findViewById(R.id.etAdhaar);
        etGender=findViewById(R.id.etGender);
        etAge=findViewById(R.id.etAge);
        retriveData();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=currentUser.getEmail();
                HashMap userInfo=new HashMap();
                userInfo.put("id",uid);
                userInfo.put("email",email);
                userInfo.put("name",etName.getText().toString().trim());
                userInfo.put("mobile",etMobile.getText().toString().trim());
                userInfo.put("address",etAddress.getText().toString().trim());
                userInfo.put("adhaar",etAdhaar.getText().toString().trim());
                userInfo.put("econtact",etEmergency.getText().toString().trim());
                userInfo.put("gender",etGender.getText().toString().trim());
                userInfo.put("age",etAge.getText().toString().trim());

                myRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(userUpdateProfile.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(userUpdateProfile.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                        finish();

                    }
                });
            }
        });
    }

    private void retriveData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    User profileUser=snapshot.getValue(User.class);
                    etName.setText(profileUser.getName().toString());
                    etMobile.setText(profileUser.getMobile());
                    etAddress.setText(profileUser.getAddress());
                    etAdhaar.setText(profileUser.getAdhaar());
                    etEmergency.setText(profileUser.getEcontact());
                    etGender.setText(profileUser.getGender());
                    etAge.setText(profileUser.getAge());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}