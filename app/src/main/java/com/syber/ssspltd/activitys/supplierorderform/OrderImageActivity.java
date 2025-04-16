package com.syber.ssspltd.activitys.supplierorderform;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.syber.ssspltd.Utils.MyConstant;
import com.syber.ssspltd.adapter.supplierformadapter.OrderImageAdapter;
import com.syber.ssspltd.model.booking.branchlist.GuestMasterDetail;
import com.syber.ssspltd.response.SupplierOrderReport.ImageList;
import com.syber.ssspltd.response.SupplierOrderReport.OrderDetail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
             List<ImageList> galleryList=new ArrayList<>();
            if(extra.getStringExtra(MyConstant.SCREEN).equals(MyConstant.GUEST)){
                String imgListJson = getIntent().getStringExtra("imgList");
                // If imgList is an object, convert back from JSON
                Gson gson = new Gson();
                GuestMasterDetail imgList = gson.fromJson(imgListJson, GuestMasterDetail.class);
                // Add front image if available
                if (imgList != null) {
                    if (imgList.getFrontDocPath() != null) {
                        ImageList frontImage = new ImageList();
                        frontImage.setImagepath(imgList.getFrontDocPath());
                        galleryList.add(frontImage);
                    }

                    // Add back image if available
                    if (imgList.getBackDocPath() != null) {
                        if(imgList.getBackDocPath().equals("")){

                        }else {
                            ImageList backImage = new ImageList();
                            backImage.setImagepath(imgList.getBackDocPath());
                            galleryList.add(backImage);
                        }

                    }
                }
                galleryAdapter = new OrderImageAdapter(mContext, galleryList);
                LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
                linearLayoutManager2.setOrientation(GridLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager2);
                recyclerView.setAdapter(galleryAdapter);
                snapHelper = new LinearSnapHelper();
                snapHelper.attachToRecyclerView(recyclerView);
                ScrollingPagerIndicator recyclerIndicator = findViewById(R.id.indicator);
                recyclerIndicator.attachToRecyclerView(recyclerView);
            }else {
                product = (OrderDetail) extra.getSerializableExtra("img");
                System.out.println("immm"+ new Gson().toJson(product.getImageList()));
                if ( (product.getImageList() != null)) {
                    galleryAdapter = new OrderImageAdapter(mContext, (product.getImageList() != null) ? product.getImageList() : Collections.emptyList());
                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
                    linearLayoutManager2.setOrientation(GridLayoutManager.HORIZONTAL);
                    recyclerView.setLayoutManager(linearLayoutManager2);
                    recyclerView.setAdapter(galleryAdapter);
                    snapHelper = new LinearSnapHelper();
                    snapHelper.attachToRecyclerView(recyclerView);
                    ScrollingPagerIndicator recyclerIndicator = findViewById(R.id.indicator);
                    recyclerIndicator.attachToRecyclerView(recyclerView);
                } else {
                    Toast.makeText(this, "Image Not Available", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

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