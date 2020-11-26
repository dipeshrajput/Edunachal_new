package com.example.edunachal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edunachal.RecycleViewAdaptors.ChatAdaptor;
import com.example.edunachal.model.ChatModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.grpc.Server;

public class DiscussionChats extends AppCompatActivity {
    ProgressBar progressBar;
    EditText editText;
    ImageButton imageButton;
    DatabaseReference databaseReference, userReference;
    RecyclerView recyclerView;
    String uid;
    String name, tag;
    ChatAdaptor chatAdaptor;
    List<ChatModel> chatModels;
    List<String> keys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_chats);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        editText = findViewById(R.id.editTextTextMultiLine);
        recyclerView=findViewById(R.id.recyclerView2);
        progressBar=findViewById(R.id.progressBar4);
        imageButton=findViewById(R.id.imageButton);
        chatModels=new ArrayList<>();
        keys=new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        chatAdaptor = new ChatAdaptor(chatModels,this,uid);
        recyclerView.setAdapter(chatAdaptor);
        userReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    name=snapshot.child("name").getValue().toString();
                    tag=snapshot.child("domain").getValue().toString();
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Toast.makeText(DiscussionChats.this, "Your data not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DiscussionChats.this, "Failed to get your info", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference().child("chats");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot ds, @Nullable String previousChildName) {
                if(ds.exists())
                {
                    progressBar.setVisibility(View.VISIBLE);
                    String messageId = ds.getKey();
                    String from = ds.child("from").getValue().toString();
                    String type = ds.child("type").getValue().toString();
                    String message = ds.child("message").getValue().toString();
                    long timestamp = Long.parseLong(ds.child("timestamp").getValue().toString());
                    String sender = ds.child("uid").getValue().toString();
                    String domain = ds.child("tag").getValue().toString();
                    ChatModel chatModel = new ChatModel(message,from,type,messageId,timestamp,sender,domain);
                    chatModels.add(chatModel);
                    keys.add(messageId);
                    chatAdaptor.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chatModels.size()-1);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                int index = keys.indexOf(snapshot.getKey());
                chatModels.remove(index);
                keys.remove(index);
                chatAdaptor.notifyDataSetChanged();
                if(chatModels.size()-1>=0)
                recyclerView.scrollToPosition(chatModels.size()-1);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(bottom<oldBottom)
                {
                    int pos= Objects.requireNonNull(recyclerView.getAdapter()).getItemCount()-1;
                    if(pos>=0)
                        recyclerView.smoothScrollToPosition(pos);
                }
            }
        });
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                progressBar.setVisibility(View.VISIBLE);
                imageButton.setEnabled(false);
                final int position = viewHolder.getAdapterPosition();
                final String messageId = chatModels.get(position).getMessageId();
                String fromId = chatModels.get(position).getUid();
                if(uid.equals(fromId) || uid.equals("rtvHTQwcp7VRVpbCzUMZRUesReX2"))
                {
                    databaseReference.child(chatModels.get(position).getMessageId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                imageButton.setEnabled(true);
                                chatAdaptor.notifyDataSetChanged();
                            }
                            else
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                imageButton.setEnabled(true);
                                chatAdaptor.notifyItemChanged(viewHolder.getAdapterPosition());
                                Toast.makeText(DiscussionChats.this, "Error while deleting message", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(DiscussionChats.this, "You are not allowed to delete this message", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    imageButton.setEnabled(true);
                    chatAdaptor.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void sendMessage(View view) {
        imageButton.setEnabled(false);
        String message=editText.getText().toString().trim();
        if(message.equals(""))
        {
            editText.setError("Empty Text");
            Toast.makeText(this, "Can't send an Empty message", Toast.LENGTH_SHORT).show();
            imageButton.setEnabled(true);
            return;
        }
        DatabaseReference pushMessageRef = databaseReference.push();
        String messageId = pushMessageRef.getKey();
        Map map = new HashMap();
        map.put("message",message);
        map.put("from",name);
        map.put("timestamp", ServerValue.TIMESTAMP);
        map.put("type","text");
        map.put("uid",uid);
        map.put("tag",tag);

        Map map1 = new HashMap();
        map1.put(messageId,map);
        databaseReference.updateChildren(map1, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if(error!=null)
                {
                    Toast.makeText(DiscussionChats.this, "Some Error Occurred while sending your message\nTry again later", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    editText.setText("");
                }
                imageButton.setEnabled(true);
            }
        });
    }
}