package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.complaint.Adapter.ComplaintVIewAdapter;
import com.example.complaint.Adapter.LocationViewHolder;
import com.example.complaint.Model.FIR;
import com.example.complaint.Model.Location;
import com.example.complaint.Model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.Locale;

public class PoliceSos extends AppCompatActivity {
    DatabaseReference reff,Dreff,countref;
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    String name,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_sos);
        mAuth=FirebaseAuth.getInstance();
        String uid=mAuth.getCurrentUser().getUid();
        reff= FirebaseDatabase.getInstance().getReference().child("Location");
        recyclerView = findViewById(R.id.sosRecylerView);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Location> options=new FirebaseRecyclerOptions.Builder<Location>()
                .setQuery(reff, Location.class)
                .build();
        FirebaseRecyclerAdapter<Location, LocationViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Location, LocationViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull LocationViewHolder holder, int position, @NonNull @NotNull Location model) {
                        String Userid=model.getId();
                        holder.setitems(this,Userid);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", Double.valueOf(model.getLat()),Double.valueOf(model.getLng()));
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivity(intent);
                            }
                        });
                    }

                    @NotNull
                    @Override
                    public LocationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.soslayout,parent,false);
                        return new LocationViewHolder(view);
                    }
                };

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}