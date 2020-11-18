package com.example.edunachal.RecycleViewAdaptors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edunachal.R;
import com.example.edunachal.model.CurrentAffairsModel;

import java.util.List;

public class MyRecyclerViewAdaptor extends RecyclerView.Adapter<MyRecyclerViewAdaptor.MyViewHolder> {
    Context context;
    List<CurrentAffairsModel> currentAffairsModels;

    public MyRecyclerViewAdaptor(Context context, List<CurrentAffairsModel> currentAffairsModels) {
        this.context = context;
        this.currentAffairsModels = currentAffairsModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.current_affairs_universal_temp,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int colorId = position%4;
        final CurrentAffairsModel currentAffairsModel=currentAffairsModels.get(position);
        if(colorId==0)
            holder.cardView.setCardBackgroundColor(Color.RED);
        else if(colorId==1)
            holder.cardView.setCardBackgroundColor(Color.parseColor("#089208"));
        else if(colorId==2)
            holder.cardView.setCardBackgroundColor(Color.BLUE);
        else
            holder.cardView.setCardBackgroundColor(Color.YELLOW);
        holder.textView.setText(currentAffairsModel.getTitle());
        holder.textView.setSelected(true);
        holder.textView1.setText(String.format("Tags: %s", currentAffairsModel.getTag()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle(currentAffairsModel.getTitle());
                builder.setMessage(currentAffairsModel.getContent());
                builder.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return currentAffairsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView, textView1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardView2);
            textView=itemView.findViewById(R.id.textView);
            textView1=itemView.findViewById(R.id.textView6);
        }
    }
}
