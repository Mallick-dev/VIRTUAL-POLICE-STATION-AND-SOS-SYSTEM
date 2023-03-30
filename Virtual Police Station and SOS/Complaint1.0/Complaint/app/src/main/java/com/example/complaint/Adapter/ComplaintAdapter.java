package com.example.complaint.Adapter;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.complaint.Model.FIR;
import com.example.complaint.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.annotations.NotNull;

import java.util.Locale;

public class ComplaintAdapter extends RecyclerView.ViewHolder {
    TextView tvComplaint,tvName,tvType;


    public ComplaintAdapter(@NonNull View itemView) {
        super(itemView);
    }

    public void setitems(FirebaseRecyclerAdapter<FIR, ComplaintAdapter>Activity,String cNumber,String name,String type){
        tvComplaint=itemView.findViewById(R.id.tvComplaint);
        tvName=itemView.findViewById(R.id.tvName);
        tvType=itemView.findViewById(R.id.tvType);

        tvComplaint.setText(cNumber);
        tvName.setText(name);
        tvType.setText(type);
    }


}
