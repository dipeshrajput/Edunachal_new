package com.example.edunachal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class APPSC_01 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_p_p_s_c_01);
    }

    public void apq(View v)
    {
        Intent intent=new Intent(APPSC_01.this,CurrentQuiz.class);
        intent.putExtra("flag","appsc");
        startActivity(intent);
    }

    public void cf(View view) {
        Intent intent = new Intent(APPSC_01.this, current_appsc01.class);
        startActivity(intent);
    }
    public void apwr(View v)
    {
        Intent intent = new Intent(this,PdfListDisplay.class);
        intent.putExtra("flagExtra","appsc");
        intent.putExtra("flagExtra1","essayWriting");
        startActivity(intent);
    }

    public void studyNote(View view) {
        Intent intent = new Intent(this,PdfListDisplay.class);
        intent.putExtra("flagExtra","appsc");
        intent.putExtra("flagExtra1","studyNotes");
        startActivity(intent);
    }

    public void answerWriting(View view) {
        Intent intent = new Intent(this,PdfListDisplay.class);
        intent.putExtra("flagExtra","appsc");
        intent.putExtra("flagExtra1","answerWriting");
        startActivity(intent);
    }
}