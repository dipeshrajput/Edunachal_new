package com.example.edunachal.RecycleViewAdaptors;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edunachal.R;
import com.example.edunachal.model.ChatModel;

import java.util.Calendar;
import java.util.List;

public class ChatAdaptor extends RecyclerView.Adapter<ChatAdaptor.ChatViewHolder> {
    List<ChatModel> chatModels;
    Context context;
    String uid;

    public ChatAdaptor(List<ChatModel> chatModels, Context context, String uid) {
        this.chatModels = chatModels;
        this.context = context;
        this.uid = uid;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_send_temp,parent,false);
            return new ChatViewHolder(view,viewType);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_received_temp,parent,false);
            return new ChatViewHolder(view,viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatModel chatModel = chatModels.get(position);
        if(uid.equals(chatModel.getUid()))
            return 1;
        else
            return 2;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatViewHolder holder, final int position) {
        final ChatModel chatModel = chatModels.get(position);
        if(holder.vType==2)
        {
            holder.textView1.setText(chatModel.getFrom()+" - "+chatModel.getTag());
            holder.textView.setText(chatModel.getMessage());
            if(chatModel.getUid().equals("rtvHTQwcp7VRVpbCzUMZRUesReX2"))
                holder.textView1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_verified_24,0);
            else
                holder.textView1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
        }
        else
        {
            holder.textView.setText(chatModel.getMessage());
        }
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                calendar.setTimeInMillis(chatModels.get(position).getTimestamp());
                String[] months={"January","February","March","April","May","June","July","August","September","October","November","December"};
                String[] days={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
                String[] am_pm={"AM","PM"};
                String messageInfo = days[calendar.get(Calendar.DAY_OF_WEEK)-1]+", "+months[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.get(Calendar.YEAR)+"\n"+calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND)+" "+am_pm[calendar.get(Calendar.AM_PM)];
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(messageInfo);
                builder.setTitle("Message Info");
                builder.setIcon(R.mipmap.ic_launcher_round);
                builder.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        holder. textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboardManager= (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Message",chatModel.getMessage());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, "Text has been copied", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView1;
        int vType;
        public ChatViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            vType=viewType;
            if(viewType == 1)
            {
                textView = itemView.findViewById(R.id.textView11);
            }
            else
            {
                textView=itemView.findViewById(R.id.textView8);
                textView1=itemView.findViewById(R.id.textView7);
            }
        }
    }
}
