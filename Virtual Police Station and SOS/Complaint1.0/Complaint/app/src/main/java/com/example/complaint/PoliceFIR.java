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

import com.example.complaint.Adapter.ComplaintAdapter;
import com.example.complaint.Model.FIR;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class PoliceFIR extends AppCompatActivity {
    DatabaseReference reff,Dreff,countref;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_fir);
        reff= FirebaseDatabase.getInstance().getReference().child("complaints");
        recyclerView = findViewById(R.id.tipsRecylerView);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FIR> options=new FirebaseRecyclerOptions.Builder<FIR>()
                .setQuery(reff,FIR.class)
                .build();


        FirebaseRecyclerAdapter<FIR, ComplaintAdapter> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<FIR, ComplaintAdapter>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull ComplaintAdapter holder, int position, @NonNull @NotNull FIR model) {
                        holder.setitems(this,model.getcNumber(),model.getName(),model.getType());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(getApplicationContext(),ViewFir.class);
                                intent.putExtra("cid",model.getcNumber());
                                startActivity(intent);
                            }
                        });


                    }

                    @NotNull
                    @Override
                    public ComplaintAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.complaintlayout,parent,false);
                        return new ComplaintAdapter(view);
                    }
                };

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}