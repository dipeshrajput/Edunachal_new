package com.example.edunachal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class current_appsc01 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_appsc01);
        Intent intent = getIntent();
    }

    public void openQuiz(View view) {
        Intent intent = new Intent(this,CurrentQuiz.class);
        startActivity(intent);
    }
}