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
        Intent intent = getIntent();
    }
public void apq(View v)
{
    Intent intent=new Intent(APPSC_01.this,appsc_quiz.class);
}

    public void cf(View view) {


        Intent intent = new Intent(APPSC_01.this, current_appsc01.class);
        startActivity(intent);
    }
    public void apwr(View v)
    {
        Intent intent=new Intent(APPSC_01.this,appsc_writing.class);
    }

    public void aessay(View view)
    {Intent intent = new Intent(APPSC_01.this, apsc_study.class);
        startActivity(intent);}

}