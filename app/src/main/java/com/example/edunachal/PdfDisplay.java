package com.example.edunachal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PdfDisplay extends AppCompatActivity {
    ProgressBar progressBar;
    PDFView pdfView;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_display);
        url=getIntent().getStringExtra("url");
        progressBar = findViewById(R.id.progressBar6);
        progressBar.setMax(100);
        progressBar.setIndeterminate(true);
        pdfView = findViewById(R.id.pdfview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        new DownloadPdf().execute(url);
    }

    private class DownloadPdf extends AsyncTask<String, Integer, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream=null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                if(httpURLConnection.getResponseCode() == 200)
                {
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                }
                else
                {
                    Toast.makeText(PdfDisplay.this, "Http connection error", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            super.onPostExecute(inputStream);
            pdfView.fromStream(inputStream).onRender(new OnRenderListener() {
                @Override
                public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                    pdfView.fitToWidth();
                }
            }).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }).scrollHandle(new DefaultScrollHandle(PdfDisplay.this))
                    .enableSwipe(true).defaultPage(0).load();
        }
    }
}