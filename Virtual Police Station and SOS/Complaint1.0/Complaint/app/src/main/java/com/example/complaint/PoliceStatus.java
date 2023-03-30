package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.complaint.Adapter.ComplaintVIewAdapter;
import com.example.complaint.Model.FIR;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class PoliceStatus extends AppCompatActivity {
    DatabaseReference reff,Dreff,countref;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_status);

        reff= FirebaseDatabase.getInstance().getReference().child("complaints");
        recyclerView = findViewById(R.id.statusRecylerView);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FIR> options=new FirebaseRecyclerOptions.Builder<FIR>()
                .setQuery(reff,FIR.class)
                .build();


        FirebaseRecyclerAdapter<FIR, ComplaintVIewAdapter> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<FIR, ComplaintVIewAdapter>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull ComplaintVIewAdapter holder, int position, @NonNull @NotNull FIR model) {

                        holder.setitems(this,model.getcNumber(),model.getStatus());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(getApplicationContext(),User_View_FIR.class);
                                intent.putExtra("cid",model.getcNumber());
                                intent.putExtra("type","police");
                                startActivity(intent);
                            }
                        });


                    }

                    @NotNull
                    @Override
                    public ComplaintVIewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.statuslayout,parent,false);
                        return new ComplaintVIewAdapter(view);
                    }
                };

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}