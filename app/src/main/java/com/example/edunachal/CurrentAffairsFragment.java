package com.example.edunachal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edunachal.RecycleViewAdaptors.MyRecyclerViewAdaptor;
import com.example.edunachal.model.CurrentAffairsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CurrentAffairsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CurrentAffairsFragment() {
        // Required empty public constructor
    }


    public static CurrentAffairsFragment newInstance(String param1, String param2) {
        CurrentAffairsFragment fragment = new CurrentAffairsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    List<CurrentAffairsModel> currentAffairsModels;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_current_affairs, container, false);
        final RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        progressBar=view.findViewById(R.id.progressBar3);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("current_affairs").child("universal").child(getArguments().getString("date"));
        databaseReference.orderByChild("timestamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    currentAffairsModels = new ArrayList<>();
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        if(!ds.getKey().equals("timestamp"))
                        {
                            CurrentAffairsModel currentAffairsModel=ds.getValue(CurrentAffairsModel.class);
                            currentAffairsModels.add(currentAffairsModel);
                        }
                    }
                    MyRecyclerViewAdaptor myRecyclerViewAdaptor=new MyRecyclerViewAdaptor(view.getContext(),currentAffairsModels);
                    recyclerView.setAdapter(myRecyclerViewAdaptor);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else{
                    Toast.makeText(view.getContext(), "No data found", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(view.getContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }
}