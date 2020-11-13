package com.example.edunachal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.edunachal.model.QuizModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

public class CurrentQuiz extends AppCompatActivity {
    DatabaseReference databaseReference;
    String question,opt1,opt2,opt3,opt4,explanation,name,uid;
    int correct,score=0,total=0,optionChoose;
    TextView textView;
    CardView cardView, cardView1;
    TextView obtained, totalMarks, remarks;
    GifImageView gifImageView;
    RadioButton textView1,textView2,textView3,textView4;
    List<QuizModel> quizModels;
    Button button;
    Animation animationIn, animationOut;
    ProgressBar progressBar;
    float accuracy, percentage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_quiz);
        progressBar=findViewById(R.id.progressBar);
        quizModels=new ArrayList<>();
        button=findViewById(R.id.button2);
        animationIn = AnimationUtils.loadAnimation(this,R.anim.slide_left_in);
        animationOut = AnimationUtils.loadAnimation(this,R.anim.slide_left_out);
        animationIn.setDuration(500);
        animationOut.setDuration(500);
        textView=findViewById(R.id.textView2);
        textView1=findViewById(R.id.radioButton);
        textView2=findViewById(R.id.radioButton1);
        textView3=findViewById(R.id.radioButton2);
        textView4=findViewById(R.id.radioButton3);
        gifImageView=findViewById(R.id.gifImageView);
        cardView=findViewById(R.id.cardView1);
        cardView1=findViewById(R.id.cardView);
        cardView1.setVisibility(View.INVISIBLE);
        obtained=findViewById(R.id.textView4);
        totalMarks=findViewById(R.id.textView5);
        remarks = findViewById(R.id.textView3);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        DatabaseReference nameReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        nameReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    name=snapshot.child("name").getValue().toString();
                }
                else{
                    Toast.makeText(CurrentQuiz.this, "Failed to get your name", Toast.LENGTH_SHORT).show();
                    name="noUserFound";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CurrentQuiz.this, "Database Error", Toast.LENGTH_SHORT).show();
                name="unidentified";
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference().child("current_affairs").child("appsc");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        question = ds.getKey();
                        correct = Integer.parseInt(ds.child("answer").getValue().toString());
                        opt1 = ds.child("options").child("a").getValue().toString();
                        opt2 = ds.child("options").child("b").getValue().toString();
                        opt3 = ds.child("options").child("c").getValue().toString();
                        opt4 = ds.child("options").child("d").getValue().toString();
                        explanation = ds.child("explanation").getValue().toString().replace("\\n","\n");
                        QuizModel quizModel = new QuizModel(question,opt1,opt2,opt3,opt4,explanation,correct);
                        quizModels.add(quizModel);
                    }
                    Collections.shuffle(quizModels);
                    updateQuestion();
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Toast.makeText(CurrentQuiz.this, "No Quiz Found", Toast.LENGTH_SHORT).show();
                    button.setEnabled(false);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CurrentQuiz.this, "Database error Occurred", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void nextQuestion(View view) {
        String correctAnswer = "No Correct answer";
        if(correct == 1)
        {
            textView1.setTextColor(Color.GREEN);
            correctAnswer = quizModels.get(total).getOption1();
        }
        else if(correct == 2)
        {
            textView2.setTextColor(Color.GREEN);
            correctAnswer = quizModels.get(total).getOption2();
        }
        else if(correct == 3)
        {
            textView3.setTextColor(Color.GREEN);
            correctAnswer = quizModels.get(total).getOption3();
        }
        else if(correct == 4)
        {
            textView4.setTextColor(Color.GREEN);
            correctAnswer = quizModels.get(total).getOption4();
        }
        if(correct == optionChoose)
        {
            score++;
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            dialogNext();
        }
        else{
            if(optionChoose != 0)
            {
                if(optionChoose == 1)
                    textView1.setTextColor(Color.RED);
                else if(optionChoose == 2)
                    textView2.setTextColor(Color.RED);
                else if(optionChoose == 3)
                    textView3.setTextColor(Color.RED);
                else if(optionChoose == 4)
                    textView4.setTextColor(Color.RED);
                final AlertDialog.Builder builder = new AlertDialog.Builder(CurrentQuiz.this);
                builder.setTitle("Wrong Answer");
                builder.setMessage("Explanation: "+explanation+"\nCorrect Answer: "+correctAnswer);
                builder.setCancelable(false);
                builder.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogNext();
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(CurrentQuiz.this);
                builder.setTitle("UnAttempted Answer");
                builder.setMessage("Explanation: "+explanation+"\nCorrect Answer: "+correctAnswer);
                builder.setCancelable(false);
                builder.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogNext();
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        }

    }
    private void dialogNext()
    {
        if(total+2 == quizModels.size())
        {
            button.setText("Finish");
        }
        if(total+1 == quizModels.size())
        {
            progressBar.setVisibility(View.VISIBLE);
            total++;
            accuracy=score/(float)total;
            percentage=accuracy*100;
            Map map = new HashMap();
            map.put("name",name);
            map.put("obtained marks",String.valueOf(score));
            map.put("total marks",String.valueOf(total));
            map.put("percentage",String.valueOf(percentage));
            map.put("timestamp", ServerValue.TIMESTAMP);
            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("appsc quiz response").child(uid);
            databaseReference1.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(CurrentQuiz.this, "Response Updated", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(CurrentQuiz.this, "Failed to update your response\nDue to Error "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
            obtained.setText(String.valueOf(score));
            totalMarks.setText("/"+total);
            cardView1.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.INVISIBLE);
            if(percentage>=60)
            {
                obtained.setTextColor(Color.parseColor("#43e97b"));
                gifImageView.setImageResource(R.drawable.great);
                remarks.setText("Great Job You have scored more than 60%\nYour Calculated percentage is: "+percentage+"%");
            }
            else
            {
                obtained.setTextColor(Color.RED);
                gifImageView.setImageResource(R.drawable.sad);
                remarks.setText("You were almost there but you have scored less than 60%\nYour Percentage is: "+percentage+"%");
            }
            Toast.makeText(this, "Score: "+score+"/"+total, Toast.LENGTH_SHORT).show();
        }
        else
        {
            total++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateQuestion();
                }
            },1000);
        }
    }
    private void updateQuestion() {
        cardView.startAnimation(animationOut);
        optionChoose = 0;
        textView1.setTextColor(Color.BLACK);
        textView2.setTextColor(Color.BLACK);
        textView3.setTextColor(Color.BLACK);
        textView4.setTextColor(Color.BLACK);
        textView1.setChecked(false);
        textView2.setChecked(false);
        textView3.setChecked(false);
        textView4.setChecked(false);
        QuizModel quizModel = quizModels.get(total);
        textView.setText(quizModel.getQuestion());
        textView1.setText(quizModel.getOption1());
        textView2.setText(quizModel.getOption2());
        textView3.setText(quizModel.getOption3());
        textView4.setText(quizModel.getOption4());
        correct = quizModel.getCorrect();
        explanation = quizModel.getExplanation();
        animationIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.startAnimation(animationIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked)
                    optionChoose=1;
                    break;
            case R.id.radioButton1:
                if (checked)
                    optionChoose=2;
                    break;
            case R.id.radioButton2:
                if (checked)
                    optionChoose=3;
                    break;
            case R.id.radioButton3:
                if (checked)
                    optionChoose=4;
                    break;
        }
    }
}