package com.syber.ssspltd.activitys.supplierorderform;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.gson.Gson;
import com.syber.ssspltd.R;
import com.syber.ssspltd.adapter.supplierformadapter.OrderImageAdapter;
import com.syber.ssspltd.response.SupplierOrderReport.OrderDetail;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class OrderImageActivity extends AppCompatActivity {

    Context mContext = this;
    RecyclerView recyclerView;
    OrderDetail product;
    ImageView imageView;
    SnapHelper snapHelper;
    OrderImageAdapter galleryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_image);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_close_24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        imageView = findViewById(R.id.image);
//        try {
//            Picasso.with(mContext)
//                    .load("http://"+getIntent().getStringExtra("img"))
//                    .priority(Picasso.Priority.HIGH)
//                    // .placeholder(R.drawable.sl)
//                    //.memoryPolicy(MemoryPolicy.)
//                    // .networkPolicy(NetworkPolicy.OFFLINE)
//                    .into(imageView);
//        } catch (Exception e) {
//            e.printStackTrace();
        // }


        recyclerView = findViewById(R.id.gallery_recycler);
        Intent extra = getIntent();
        if (extra != null) {
            product = (OrderDetail) extra.getSerializableExtra("img");
        }
        Log.e("immm",new Gson().toJson(product.getImageList()));
         galleryAdapter = new OrderImageAdapter(mContext, product.getImageList());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
        linearLayoutManager2.setOrientation(GridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager2);
        recyclerView.setAdapter(galleryAdapter);
        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        ScrollingPagerIndicator recyclerIndicator = findViewById(R.id.indicator);
        recyclerIndicator.attachToRecyclerView(recyclerView);
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