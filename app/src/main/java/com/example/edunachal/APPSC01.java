package com.example.edunachal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class APPSC01 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_p_p_s_c01);
    }

    public void appsbMaths(View view) {
        Intent intent = new Intent(this,PdfListDisplay.class);
        intent.putExtra("flagExtra","appsb");
        intent.putExtra("flagExtra1","maths");
        startActivity(intent);
    }

    public void appsbGk(View view) {
        Intent intent = new Intent(this,PdfListDisplay.class);
        intent.putExtra("flagExtra","appsb");
        intent.putExtra("flagExtra1","gk");
        startActivity(intent);
    }

    public void appsbEnglish(View view) {
        Intent intent = new Intent(this,PdfListDisplay.class);
        intent.putExtra("flagExtra","appsb");
        intent.putExtra("flagExtra1","english");
        startActivity(intent);
    }

    public void appsbReasoning(View view) {
        Intent intent = new Intent(this,PdfListDisplay.class);
        intent.putExtra("flagExtra","appsb");
        intent.putExtra("flagExtra1","reasoning");
        startActivity(intent);
    }
}