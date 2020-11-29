package com.example.edunachal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edunachal.RecycleViewAdaptors.PdfListAdaptor;
import com.example.edunachal.model.PdfModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PdfListDisplay extends AppCompatActivity {
    List<PdfModel> pdfModels;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    PdfListAdaptor pdfListAdaptor;
    DatabaseReference databaseReference;
    String flagExtra, flagExtra1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_list_display);
        recyclerView = findViewById(R.id.recyclerView3);
        progressBar=findViewById(R.id.progressBar5);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        flagExtra=getIntent().getStringExtra("flagExtra");
        flagExtra1=getIntent().getStringExtra("flagExtra1");
        databaseReference = FirebaseDatabase.getInstance().getReference().child(flagExtra).child(flagExtra1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pdfModels = new ArrayList<>();
        pdfListAdaptor = new PdfListAdaptor(this,pdfModels,flagExtra,flagExtra1);
        recyclerView.setAdapter(pdfListAdaptor);
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.orderByChild("timestamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        String name = ds.child("name").getValue().toString();
                        String url = ds.child("url").getValue().toString();
                        String storageName = ds.getKey();
                        PdfModel pdfModel = new PdfModel(name,storageName,url);
                        pdfModels.add(pdfModel);
                        pdfListAdaptor.notifyDataSetChanged();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(PdfListDisplay.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PdfListDisplay.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}