package com.syber.ssspltd.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.syber.ssspltd.R;
import com.syber.ssspltd.adapter.GalleryAllViewAdapter;
import com.syber.ssspltd.adapter.GalleryAllViewItemAdapter;
import com.syber.ssspltd.adapter.GalleryItemAdapter;
import com.syber.ssspltd.response.GalleryResponse.ImageListAppResult;
import com.syber.ssspltd.response.GalleryResponse.ImageListSecondaryDatum;

import java.util.ArrayList;
import java.util.List;

public class GalleryViewAllActivity extends AppCompatActivity {
    RecyclerView allView_recycler;
    Context mContext;
    GalleryAllViewItemAdapter galleryItemAdapter;
    GridLayoutManager linearLayoutManager;
   // List<ImageListSecondaryDatum> imageListDetails;
    ImageListSecondaryDatum ImageGet;
    ImageListAppResult imageListDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view_all);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Gallery");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // imageListDetails=new ArrayList<>();

        imageListDetails=(ImageListAppResult) getIntent().getSerializableExtra("title");
        Log.e("show_image",imageListDetails.getImageListSecondaryData()+"");
        allView_recycler=findViewById(R.id.allView_recycler);
        galleryItemAdapter = new GalleryAllViewItemAdapter(mContext,imageListDetails.getImageListSecondaryData());
        allView_recycler.setAdapter(galleryItemAdapter);
        galleryItemAdapter.notifyDataSetChanged();

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}