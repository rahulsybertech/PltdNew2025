package com.syber.ssspltd.Utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public  class FileDownloader {
        private static final int  MEGABYTE = 1024 * 1024;
        public static void downloadFile(String fileUrl, File directory){
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(directory);
                int totalSize = urlConnection.getContentLength();

                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while((bufferLength = inputStream.read(buffer))>0 ){
                    fileOutputStream.write(buffer, 0, bufferLength);
                }
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public static class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName="t";  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "Downloads");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);
         //   Log.e("pdfLiles",pdfFile+"");

            try{
                pdfFile.createNewFile();
                FileDownloader.downloadFile(fileUrl, pdfFile);
            }catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }
    }

    public static void downloadPDF(Context context,String pdfUrl) {
        String fileName;
        URL url = null;
        try {
            url = new URL(pdfUrl);
        } catch (IOException e) {
            AlertUtil.responseExecption(context,"PDF url is invalid", e.toString());
            throw new RuntimeException(e);
        }
        fileName = url.getPath();
        fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfUrl));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(fileName);
        request.setDescription("Downloading PDF...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
         manager.enqueue(request);
      //  long downloadID = manager.enqueue(request);
//        BroadcastReceiver onComplete = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//                if (downloadID == id) {
//                    sharePDF();
//                }
//            }
//        };
//        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
}
