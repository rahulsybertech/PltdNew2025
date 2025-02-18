package com.syber.ssspltd.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.rajat.pdfviewer.PdfRendererView;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.FileDownloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;


public class ViewPDFActivity extends AppCompatActivity implements DownloadFile.Listener {

    PDFPagerAdapter adapter;
    RemotePDFViewPager remotePDFViewPager;
    LinearLayout linear_layout_pdf;
    RelativeLayout rlwaterMark;
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
        rlwaterMark = findViewById(R.id.rlwaterMark);
        downloadPdf = findViewById(R.id.download);
        PdfRendererView view = findViewById(R.id.pdfView);
        ivBack.setOnClickListener(v -> onBackPressed());
        share.setOnClickListener(v->sharePDF(pdfUrl));
        downloadPdf.setOnClickListener(v-> FileDownloader.downloadPDF(this,pdfUrl));
        System.out.println("MY_PDF_URL " + pdfUrl);
        linear_layout_pdf = findViewById(R.id.linear_layout_pdf);

    /*    File file = new File(pdfUrl); // Change path as needed

        try {
            view.initWithFile(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
/*        Bitmap bitmap = getBitmapFromPath(pdfUrl);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAlpha(100);
        paint.setTextSize(50);
        canvas.drawText("WATERMARK", 100, 100, paint);*/
          remotePDFViewPager = new RemotePDFViewPager(this, pdfUrl, this);

    }
    public static Bitmap getBitmapFromPath(String filePath) {
        return BitmapFactory.decodeFile(filePath);
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
        System.out.println("MY_PDF_URL 2 " + url);
        System.out.println("MY_PDF_URL 3 " + FileUtil.extractFileNameFromURL(url));
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url)); // Setup adapter with the file
        remotePDFViewPager.setAdapter(adapter); // Attach adapter to remote pdf viewpager
     //water mark show
      //  remotePDFViewPager.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // Add it to the container
        linear_layout_pdf.removeAllViews();
        linear_layout_pdf.addView(
                remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
//        showWatermark();


    }

    private void showWatermark() {
        // Create watermark text
        TextView watermark = new TextView(this);
        watermark.setText("HOLD");
        watermark.setTextSize(40);
        watermark.setTextColor(Color.parseColor("#80AAAAAA")); // Semi-transparent gray
        watermark.setRotation(-30); // Tilted effect1
        watermark.setGravity(Gravity.CENTER);

        // Set layout params to center it
        FrameLayout.LayoutParams watermarkParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
        );

        // Add watermark to a FrameLayout overlay
        FrameLayout overlay = new FrameLayout(this);
        overlay.addView(watermark, watermarkParams);

        // Ensure it covers the entire PDF layout
        rlwaterMark.addView(overlay, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        ));
    }




    @Override
    public void onFailure(Exception e) {
        AlertUtil.responseExecption(this,"PDF Error","PDF Not Found");
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }

//    private void sharePDF(){
//
//        File file = new File(getCacheDir(), FileUtil.extractFileNameFromURL(pdfUrl));
//        System.out.println("My file uri  " + file.getAbsoluteFile().exists());
//        if (file.exists()) {
//            Uri fileUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file.getAbsoluteFile());
//
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("application/pdf");
//            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Allow access to the file
//
//            startActivity(Intent.createChooser(intent, "Share PDF using"));
//        } else {
//            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
//        }
////        File file = new File(getFilesDir(), FileUtil.extractFileNameFromURL(pdfUrl));
////        System.out.println("My file uri  " + file.getAbsolutePath());
//
////        if (file.exists()) {
////            Uri fileUri = Uri.fromFile(file.getAbsoluteFile());
////            Uri fileUri = FileProvider.getUriForFile(
////                    this,
////                    getApplicationContext().getPackageName() + ".provider",
////                    file
////            );
//
////            Intent shareIntent = new Intent(Intent.ACTION_SEND);
////            shareIntent.setType("application/pdf");
////            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
////            shareIntent.addFlags( Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
////            startActivity(Intent.createChooser(shareIntent, "Share PDF"));
////        }
//    }

    public void sharePDF(String pdfUrl) {
        try {
            // Step 1: Download the file to cache directory
            File file = new File(getCacheDir(), FileUtil.extractFileNameFromURL(pdfUrl));
            if (!file.exists()) {
                downloadPdfFile(pdfUrl, file);
            }

            // Step 2: Get URI using FileProvider
            Uri fileUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);

            // Step 3: Create share intent
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Launch the share intent
            startActivity(Intent.createChooser(intent, "Share PDF"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadPdfFile(String pdfUrl, File file) throws Exception {
        URL url = new URL(pdfUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        try (InputStream input = connection.getInputStream();
             FileOutputStream output = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        }
    }

}