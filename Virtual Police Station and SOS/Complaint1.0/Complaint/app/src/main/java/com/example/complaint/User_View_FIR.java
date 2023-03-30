package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.complaint.Model.FIR;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class User_View_FIR extends AppCompatActivity {
    private TextView etSubject,etName,etPlace,etType,etAge,etDate,etAddress,etMobile,etDescription;
    private TextView etStatus;
    private Button btnDownload;
    FirebaseAuth mAuth;
    DatabaseReference myRef,insertRef;
    FirebaseDatabase firebaseDatabase;
    String name,email,mobile,address,adhaar,econtact,gender,age,subject,type,place,date,description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_fir);

        String cid=getIntent().getStringExtra("cid");
        String types=getIntent().getStringExtra("type");
        Toast.makeText(this, types, Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid=currentUser.getUid();
        firebaseDatabase=FirebaseDatabase.getInstance();

        if(types.equals("police")){
            myRef=firebaseDatabase.getReference().child("complaints").child(cid);
        }else{
            myRef=firebaseDatabase.getReference().child("usercomplaints").child(uid).child(cid);
        }

        etSubject=findViewById(R.id.etSubject);
        etName=findViewById(R.id.etName);
        etPlace=findViewById(R.id.etPlace);
        etType=findViewById(R.id.etType);
        etAge=findViewById(R.id.etAge);
        etDate=findViewById(R.id.etDate);
        etAddress=findViewById(R.id.etAddress);
        etMobile=findViewById(R.id.etMobile);
        etDescription=findViewById(R.id.etDescription);
        etStatus=findViewById(R.id.etStatus);
        btnDownload=findViewById(R.id.btnDownload);
        retrieveData();

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();

            }
        });
    }

    private void create() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    FIR profileUser=snapshot.getValue(FIR.class);
                    PdfDocument myPdfDoc=new PdfDocument();
                    Paint paint=new Paint();
                    PdfDocument.PageInfo mypageInfo=new PdfDocument.PageInfo.Builder(250,350,1).create();
                    PdfDocument.Page myPage=myPdfDoc.startPage(mypageInfo);
                    Canvas canvas=myPage.getCanvas();
                    paint.setTextSize(15.5f);
                    paint.setColor(Color.rgb(0,50,250));
                    canvas.drawText("First Information Report",20,30,paint);
                    paint.setTextSize(9);
                    canvas.drawText("Name of the Person : "+profileUser.getName(),20,60,paint);
                    canvas.drawText("Subject of Complaint : "+profileUser.getSubject(),20,90,paint);
                    canvas.drawText("Place of Occurence : "+profileUser.getPlace(),20,120,paint);
                    canvas.drawText("Date of Occurance : "+profileUser.getDate(),20,150,paint);
                    canvas.drawText("Type of Complaint : "+ profileUser.getType(),20,180,paint);
                    canvas.drawText("Age of the Person : "+profileUser.getAge(),20,210,paint);
                    canvas.drawText("Address of the Person : "+profileUser.getAddress(),20,240,paint);
                    canvas.drawText("Mobile of the Person : "+profileUser.getMobile(),20,270,paint);
                    canvas.drawText("Complaint Description : "+profileUser.getDescription(),20,300,paint);
                    canvas.drawText("Status of the Complaint : "+profileUser.getStatus(),20,330,paint);

                    myPdfDoc.finishPage(myPage);
                    File file=new File(User_View_FIR.this.getExternalFilesDir("/"),".pdf");

                    try {
                        myPdfDoc.writeTo(new FileOutputStream(file));
                        Toast.makeText(User_View_FIR.this, "Downloaded", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    myPdfDoc.close();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                    etStatus.setText(profileUser.getStatus());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}