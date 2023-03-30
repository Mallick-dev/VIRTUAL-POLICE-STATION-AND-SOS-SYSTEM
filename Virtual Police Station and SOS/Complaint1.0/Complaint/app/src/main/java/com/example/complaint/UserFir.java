package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class UserFir extends AppCompatActivity {
    private EditText etSubject,etDescription,etPlace,etDate;
    private Spinner complanitTypeSpinner;
    private Button btnEnterComplaint;
    FirebaseAuth mAuth;
    DatabaseReference myRef,ComplaintRef,insertRef,insertUserRef;
    FirebaseDatabase firebaseDatabase;

    String name,email,mobile,address,adhaar,econtact,gender,age,subject,type,place,date,description;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_fir);
        etSubject=findViewById(R.id.etSubject);
        etDescription=findViewById(R.id.etDescription);
        etPlace=findViewById(R.id.etPlace);
        etDate=findViewById(R.id.etDate);
        complanitTypeSpinner=findViewById(R.id.complanitTypeSpinner);
        btnEnterComplaint=findViewById(R.id.btnEnterComplaint);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid=currentUser.getUid();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference().child("users").child(uid);
        ComplaintRef=firebaseDatabase.getReference().child("complaints");
        ComplaintRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    count=(int)snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    User profileUser=snapshot.getValue(User.class);
                    name=profileUser.getName();
                    email=profileUser.getEmail();
                    mobile=profileUser.getMobile();
                    address=profileUser.getAddress();
                    adhaar=profileUser.getAdhaar();
                    econtact=profileUser.getEcontact();
                    gender=profileUser.getGender();
                    age=profileUser.getAge();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnEnterComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subject=etSubject.getText().toString().trim();
                description=etDescription.getText().toString().trim();
                place=etPlace.getText().toString().trim();
                date=etDate.getText().toString().trim();
                type=complanitTypeSpinner.getSelectedItem().toString().trim();


                String cNumber=String.valueOf(count+1)+uid;

                HashMap userInfo=new HashMap();
                userInfo.put("id",uid);
                userInfo.put("email",email);
                userInfo.put("name",name);
                userInfo.put("mobile",mobile);
                userInfo.put("address",address);
                userInfo.put("adhaar",adhaar);
                userInfo.put("econtact",econtact);
                userInfo.put("gender",gender);
                userInfo.put("age",age);
                userInfo.put("subject",subject);
                userInfo.put("description",description);
                userInfo.put("place",place);
                userInfo.put("date",date);
                userInfo.put("type",type);
                userInfo.put("cNumber",cNumber);
                userInfo.put("status","Applied");


                insertRef=firebaseDatabase.getReference().child("complaints").child(cNumber);
                insertUserRef=firebaseDatabase.getReference().child("usercomplaints").child(uid).child(cNumber);

                insertRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UserFir.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UserFir.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                        finish();

                    }
                });
                insertUserRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UserFir.this, "Data Updated to User Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UserFir.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                        finish();

                    }
                });
            }
        });



    }
}