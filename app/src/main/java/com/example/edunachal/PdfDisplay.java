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
import com.github.barteksc.pdfviewer.listener.OnRenderListener;

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
                    //long filelength = httpURLConnection.getContentLength();
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
//                    byte data[]= new byte[1024];
//                    long count,total = 0;
//                    while((count = inputStream.read(data))>0)
//                    {
//                        total+=count;
//                        publishProgress((int) (total*100/filelength));
//                    }
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
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            super.onPostExecute(inputStream);
            progressBar.setVisibility(View.INVISIBLE);
            pdfView.fromStream(inputStream).load();
        }
    }
}