package com.example.complaint.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.complaint.Model.FIR;
import com.example.complaint.Model.Location;
import com.example.complaint.Model.User;
import com.example.complaint.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LocationViewHolder extends RecyclerView.ViewHolder{
    TextView tvUserName,tvUserMobile,tvRoute;
    public LocationViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setitems(FirebaseRecyclerAdapter<Location, LocationViewHolder> Activity, String Name){
        DatabaseReference Dreff= FirebaseDatabase.getInstance().getReference().child("users").child(Name);
        Dreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    User profileUser=snapshot.getValue(User.class);
                    tvUserName=itemView.findViewById(R.id.tvUserName);
                    tvUserMobile=itemView.findViewById(R.id.tvUserMobile);
                    tvRoute=itemView.findViewById(R.id.tvRoute);
                    tvUserName.setText(profileUser.getName());
                    tvUserMobile.setText(profileUser.getMobile());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
