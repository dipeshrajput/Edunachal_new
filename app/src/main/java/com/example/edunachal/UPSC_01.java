package com.example.edunachal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UPSC_01 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_p_s_c_01);
    }

    public void UniCurrentAffairs(View view) {
        Intent intent = new Intent(UPSC_01.this,UniversalCurrentAffairs.class);
        startActivity(intent);
    }

    public void upscQuiz(View view) {
        Intent intent = new Intent(UPSC_01.this,CurrentQuiz.class);
        intent.putExtra("flag","upsc");
        startActivity(intent);
    }

    public void studyNotesUpsc(View view) {
        Intent intent = new Intent(this,PdfListDisplay.class);
        intent.putExtra("flagExtra","upsc");
        intent.putExtra("flagExtra1","studyNotes");
        startActivity(intent);
    }

    public void essayWritingUpsc(View view) {
        Intent intent = new Intent(this,PdfListDisplay.class);
        intent.putExtra("flagExtra","upsc");
        intent.putExtra("flagExtra1","essayWriting");
        startActivity(intent);
    }

    public void answerPracticeUpsc(View view) {
        Intent intent = new Intent(this,PdfListDisplay.class);
        intent.putExtra("flagExtra","upsc");
        intent.putExtra("flagExtra1","answerWriting");
        startActivity(intent);
    }
}