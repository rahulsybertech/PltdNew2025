package com.syber.ssspltd.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.syber.ssspltd.R;
import com.syber.ssspltd.adapter.SlidingImage_Adapter;
import com.syber.ssspltd.response.GalleryModel;
import com.viewpagerindicator.LinePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageGalleryActivity extends AppCompatActivity {
    private static ViewPager mPager;
    int currentPage = 0;
    private static int NUM_PAGES = 0;
    public static ArrayList<GalleryModel> ImagesArray = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Gallery");
        toolbar.setNavigationIcon(R.drawable.ic_cross2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImagesArray = new ArrayList<>();
        String a = getIntent().getStringExtra("imglist");
        Log.e("a", a);
        currentPage = getIntent().getIntExtra("pos", 0);
        try {
            JSONArray jsonArray = new JSONArray(a);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String img = jsonObject.getString("source_url");
                String imgtype = jsonObject.getString("linktype");
                ImagesArray.add(new GalleryModel(img, imgtype));
            }
        } catch (Exception e) {

        }


        init();

    }

    private void init() {

        mPager = findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(this, ImagesArray));

        LinePageIndicator indicator =
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setLineWidth(35);
        indicator.setGapWidth(15);
        indicator.setStrokeWidth(20);

        NUM_PAGES = ImagesArray.size();
        mPager.setCurrentItem(currentPage);

        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
//                mPager.setCurrentItem(currentPage++, true);
//            }
//        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }
            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}