package com.syber.ssspltd.activitys;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.adapter.BrandImageAdapter;
import com.syber.ssspltd.databinding.ActivityBrandDetailsBinding;
import com.syber.ssspltd.response.brand.BrandInsertingRequestDatum;

public class BrandDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Context context = this;
    BrandImageAdapter brandImageAdapter;
    BrandInsertingRequestDatum brandList;
    ActivityBrandDetailsBinding binding;
    public static String brndNmae;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBrandDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Lazy.haveNetworkConnection(context)){
            Intent extra = getIntent();
            if (extra!=null){
                brandList = (BrandInsertingRequestDatum) extra.getSerializableExtra("list");
                Log.e("brandList", new Gson().toJson(brandList));
                binding.includeProgress.progress.setVisibility(View.GONE);
            }

            brndNmae=extra.getStringExtra("brand_name");
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle( extra.getStringExtra("brand_name"));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            recyclerView = findViewById(R.id.brand_img_recycler);
            brandImageAdapter = new BrandImageAdapter(context, brandList.getArrayProductImageA());
            recyclerView.setAdapter(brandImageAdapter);
        }else {
            networkConnetion3(context);
        }

        binding.supportChat.supportFab.setOnClickListener( v ->
                Lazy.openDialog(context));


//        try {
//            assert extra != null;
//            Picasso.with(context)
//                    .load(extra.getStringExtra("brand_img"))
//                    .into(brand_img);
//        }catch (Exception e){
//            Log.e("Ex",e.getMessage());
//        }
//        recyclerView.setLayoutManager(new LinearLayoutManager(this){
//            @Override
//            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
//                // force height of viewHolder here, this will override layout_height from xml
//                lp.height = getHeight() / 3;
//                lp.width = getWidth() / 3;
//                return true;
//            }
//        });



    }

        @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == android.R.id.home)
            {
                finish();
            }
        return super.onOptionsItemSelected(item);
    }
    public void  networkConnetion3(Context mContext) {

        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.network_connetion_dailog, null);
        ImageView cross = dialogView.findViewById(R.id.cross);
        TextView try_button = dialogView.findViewById(R.id.try_button);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.BottomSheetDialogTheme2);
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.RoundedDialog);

        builder.setView(dialogView);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        cross.setOnClickListener(v -> alertDialog.dismiss());
        try_button.setOnClickListener(view -> {
            if (Lazy.haveNetworkConnection(context)){
        Intent extra = getIntent();
        if (extra!=null){
            brandList = (BrandInsertingRequestDatum) extra.getSerializableExtra("list");
            Log.e("brandList", new Gson().toJson(brandList));
            binding.includeProgress.progress.setVisibility(View.GONE);
        }
        brndNmae=extra.getStringExtra("brand_name");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle( extra.getStringExtra("brand_name"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.brand_img_recycler);
        brandImageAdapter = new BrandImageAdapter(context, brandList.getArrayProductImageA());
        recyclerView.setAdapter(brandImageAdapter);
    }else {
        networkConnetion3(context);
    }
            alertDialog.dismiss();
        });
        alertDialog.show();
    }
}