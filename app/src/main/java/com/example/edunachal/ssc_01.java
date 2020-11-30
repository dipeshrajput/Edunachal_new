package com.example.edunachal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ssc_01 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssc_01);
    }

    public void sscBankReasoning(View view) {
        Intent intent = new Intent(this,PdfListDisplay.class);
        intent.putExtra("flagExtra","sscBank");
        intent.putExtra("flagExtra1","reasoning");
        startActivity(intent);
    }

    public void sscBankMaths(View view) {
        Intent intent = new Intent(this,PdfListDisplay.class);
        intent.putExtra("flagExtra","sscBank");
        intent.putExtra("flagExtra1","maths");
        startActivity(intent);
    }

    public void sscBankGk(View view) {
        Intent intent = new Intent(this,PdfListDisplay.class);
        intent.putExtra("flagExtra","sscBank");
        intent.putExtra("flagExtra1","gk");
        startActivity(intent);
    }

    public void sscBankEnglish(View view) {
        Intent intent = new Intent(this,PdfListDisplay.class);
        intent.putExtra("flagExtra","sscBank");
        intent.putExtra("flagExtra1","english");
        startActivity(intent);
    }
}