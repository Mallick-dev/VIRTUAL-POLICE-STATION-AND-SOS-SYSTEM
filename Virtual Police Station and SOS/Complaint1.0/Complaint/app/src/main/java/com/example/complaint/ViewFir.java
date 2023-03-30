package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.complaint.Model.FIR;
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

import java.util.HashMap;

public class ViewFir extends AppCompatActivity {
    private EditText etSubject,etName,etPlace,etType,etAge,etDate,etAddress,etMobile,etDescription;
    private Spinner complanitTypeSpinner;
    private Button btnEnterComplaint;
    FirebaseAuth mAuth;
    DatabaseReference myRef,insertRef,insertUserRef;
    FirebaseDatabase firebaseDatabase;
    String name,email,mobile,address,adhaar,econtact,gender,age,subject,type,place,date,description,userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fir);

        String cid=getIntent().getStringExtra("cid");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid=currentUser.getUid();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference().child("complaints").child(cid);

        etSubject=findViewById(R.id.etSubject);
        etName=findViewById(R.id.etName);
        etPlace=findViewById(R.id.etPlace);
        etType=findViewById(R.id.etType);
        etAge=findViewById(R.id.etAge);
        etDate=findViewById(R.id.etDate);
        etAddress=findViewById(R.id.etAddress);
        etMobile=findViewById(R.id.etMobile);
        etDescription=findViewById(R.id.etDescription);
        complanitTypeSpinner=findViewById(R.id.complanitTypeSpinner);
        btnEnterComplaint=findViewById(R.id.btnEnterComplaint);
        retrieveData();

        btnEnterComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                subject=etSubject.getText().toString().trim();
                description=etDescription.getText().toString().trim();
                place=etPlace.getText().toString().trim();
                date=etDate.getText().toString().trim();
                type=etType.getText().toString().trim();
                name=etName.getText().toString().trim();
                mobile=etMobile.getText().toString().trim();
                address=etAddress.getText().toString().trim();
                age=etAge.getText().toString().trim();
                String status=complanitTypeSpinner.getSelectedItem().toString();

                HashMap userInfo=new HashMap();
                userInfo.put("id",userid);
                userInfo.put("email",email);
                userInfo.put("name",name);
                userInfo.put("mobile",mobile);
                userInfo.put("address",address);
                userInfo.put("age",age);
                userInfo.put("subject",subject);
                userInfo.put("description",description);
                userInfo.put("place",place);
                userInfo.put("date",date);
                userInfo.put("type",type);
                userInfo.put("cNumber",cid);
                userInfo.put("status",status);

                insertRef=firebaseDatabase.getReference().child("complaints").child(cid);

                insertRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ViewFir.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ViewFir.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                        finish();

                    }
                });

                insertUserRef=firebaseDatabase.getReference().child("usercomplaints").child(userid).child(cid);
                insertUserRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ViewFir.this, "Data Updated User Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ViewFir.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                        finish();

                    }
                });
            }
        });

    }

    private void retrieveData() {
        myRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.hasChildren()){
                FIR profileUser=snapshot.getValue(FIR.class);
                etSubject.setText(profileUser.getSubject());
                etName.setText(profileUser.getName().toString());
                etPlace.setText(profileUser.getPlace());
                etType.setText(profileUser.getType());
                etDate.setText(profileUser.getDate());
                etDescription.setText(profileUser.getDescription());
                etMobile.setText(profileUser.getMobile());
                etAddress.setText(profileUser.getAddress());
                etAge.setText(profileUser.getAge());
                email=profileUser.getEmail();
                userid=profileUser.getId();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    }
}