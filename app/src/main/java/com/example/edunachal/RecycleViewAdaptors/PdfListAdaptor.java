package com.example.edunachal.RecycleViewAdaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edunachal.PdfDisplay;
import com.example.edunachal.PdfListDisplay;
import com.example.edunachal.R;
import com.example.edunachal.model.PdfModel;
import com.github.barteksc.pdfviewer.PDFView;

import java.util.List;

public class PdfListAdaptor extends RecyclerView.Adapter<PdfListAdaptor.PdfListViewHolder> {
    Context context;
    List<PdfModel> pdfModels;
    String flagExtra, flagExtra1;

    public PdfListAdaptor(Context context, List<PdfModel> pdfModels, String flagExtra, String flagExtra1) {
        this.context = context;
        this.pdfModels = pdfModels;
        this.flagExtra = flagExtra;
        this.flagExtra1 = flagExtra1;
    }

    @NonNull
    @Override
    public PdfListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_display_temp,parent,false);
        return new PdfListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfListViewHolder holder, int position) {
        final PdfModel pdfModel = pdfModels.get(position);
        holder.textView.setText(pdfModel.getName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PdfDisplay.class);
                intent.putExtra("url",pdfModel.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfModels.size();
    }

    public class PdfListViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        RelativeLayout relativeLayout;
        public PdfListViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView12);
            relativeLayout = itemView.findViewById(R.id.relativeLayout2);
        }
    }
}
