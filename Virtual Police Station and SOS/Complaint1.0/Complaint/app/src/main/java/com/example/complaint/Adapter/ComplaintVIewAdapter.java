package com.example.complaint.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.complaint.Model.FIR;
import com.example.complaint.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class ComplaintVIewAdapter extends RecyclerView.ViewHolder{
    TextView tvUserComplaint,tvUserComplaintStatus;
    FirebaseAuth mAuth;

    public ComplaintVIewAdapter(@NonNull View itemView) {
        super(itemView);
    }

    public void setitems(FirebaseRecyclerAdapter<FIR, ComplaintVIewAdapter> Activity, String cNumber, String status){

        tvUserComplaint=itemView.findViewById(R.id.tvUserComplaint);
        tvUserComplaintStatus=itemView.findViewById(R.id.tvUserComplaintStatus);
        tvUserComplaint.setText(cNumber);
        tvUserComplaintStatus.setText(status);
    }


}
