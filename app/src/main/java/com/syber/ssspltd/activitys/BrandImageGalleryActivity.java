package com.syber.ssspltd.activitys;

import static com.veinhorn.scrollgalleryview.loader.picasso.dsl.DSL.images;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.builder.GallerySettings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class BrandImageGalleryActivity extends AppCompatActivity {
    private ScrollGalleryView galleryView;
    ArrayList<String>imgList;
    int itemToSwapAtPosition = 0;
    Context context;
    FloatingActionButton supportFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_image_gallery);
        supportFab=findViewById(R.id.support_fab);
        supportFab.setOnClickListener((View.OnClickListener) v ->
                Lazy.openDialog(context));
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("titleName"));
        toolbar.setNavigationIcon(R.drawable.ic_cross2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgList = new ArrayList<>();
        String a = getIntent().getStringExtra("imglist");
        Log.e("sdjh", a);
        itemToSwapAtPosition = getIntent().getIntExtra("pos",0);
        Log.e("itemToSwapAtPosition",itemToSwapAtPosition+"");

        try {
            JSONArray jsonArray = new JSONArray(a);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String img = jsonObject.getString("ProductImageA");
                imgList.add(img);
                // Collections.swap(imgList,imgList.indexOf(4),0);
                Log.e("imgList", i + "");
            }
            Collections.swap(imgList,0,itemToSwapAtPosition);
            Log.e("imgList",imgList+"");
            //Collections.swap(imgList,imgList.indexOf(4),0);

            galleryView = ScrollGalleryView
                    .from(findViewById(R.id.scroll_gallery_view))
                    .settings(
                            GallerySettings
                                    .from(getSupportFragmentManager())
                                    .thumbnailSize(100)
                                    .enableZoom(true)
                                    .build()
                    )
                    .add(images(imgList))
                    .build();
        } catch (Exception e) {

        }
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