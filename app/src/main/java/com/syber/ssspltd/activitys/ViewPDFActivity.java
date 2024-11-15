package com.syber.ssspltd.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.FileDownloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class ViewPDFActivity extends AppCompatActivity implements DownloadFile.Listener {

    PDFPagerAdapter adapter;
    RemotePDFViewPager remotePDFViewPager;
    LinearLayout linear_layout_pdf;
    String pdfUrl;
    ImageView share,downloadPdf, ivBack;
    ProgressBar progressBar;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        progressBar = findViewById(R.id.progress);
        ivBack = findViewById(R.id.ivBack);
        progressBar.setVisibility(View.VISIBLE);
//        toolbar = findViewById(R.id.toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        setSupportActionBar(toolbar);
        //  toolbar.setTitle(fileName);


        pdfUrl =  getIntent().getStringExtra("pdfUrl");



        share = findViewById(R.id.share);
        downloadPdf = findViewById(R.id.download);
        ivBack.setOnClickListener(v -> onBackPressed());
        share.setOnClickListener(v->sharePDF());
        downloadPdf.setOnClickListener(v-> FileDownloader.downloadPDF(this,pdfUrl));
        linear_layout_pdf = findViewById(R.id.linear_layout_pdf);
        remotePDFViewPager = new RemotePDFViewPager(this, pdfUrl, this);
    }

    @Override
    protected void onDestroy() {
            super.onDestroy();
            try {
                adapter.close();
            }catch (Exception e){
                e.printStackTrace();
            }

    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        progressBar.setVisibility(View.GONE);
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url)); // Setup adapter with the file
        remotePDFViewPager.setAdapter(adapter); // Attach adapter to remote pdf viewpager

        // Add it to the container
        linear_layout_pdf.removeAllViews();
        linear_layout_pdf.addView(
                remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
    }

    @Override
    public void onFailure(Exception e) {
        AlertUtil.responseExecption(this,"PDF Error","PDF Not Found");
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }

    private void sharePDF(){
        File file = new File(getCacheDir(), FileUtil.extractFileNameFromURL(pdfUrl));
        if (file.exists()) {
            Uri fileUri = FileProvider.getUriForFile(
                    this,
                    getApplicationContext().getPackageName() + ".provider",
                    file
            );
            Log.e("fileUri",fileUri+"");
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            shareIntent.addFlags( Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share PDF"));
        }
    }
}