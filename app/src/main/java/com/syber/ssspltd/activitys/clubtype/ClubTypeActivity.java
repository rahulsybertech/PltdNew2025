package com.syber.ssspltd.activitys.clubtype;

import static com.syber.ssspltd.Constants.NewErpUrls.CLUB_TYPE_BY_ACOUNT_ID;
import static com.syber.ssspltd.Constants.URLConstants.CLUB_TYPE_DETAILS;
import static com.syber.ssspltd.Constants.URLConstants.CLUB_TYPE_DETAILS_OBJECT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.allattentionhere.autoplayvideos.AAH_CustomRecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.clubtypeadapter.MyVideosAdapter;
import com.syber.ssspltd.response.clubtyperespo.ClubTypePojo;
import com.syber.ssspltd.response.clubtyperespo.Clubdetail;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import tr.com.harunkor.gifviewplayer.GifMovieView;

public class ClubTypeActivity extends AppCompatActivity {

//    List<Clubdetail> clubdetailList;
//    Type listType;
//    AAH_CustomRecyclerView recyclerView;
//    MyVideosAdapter mAdapter;
ImageView imageView,img;
Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_type);
//        ButterKnife.bind(getActivity());
        Picasso p = Picasso.with(this);
        imageView = findViewById(R.id.dd);
        img = findViewById(R.id.img);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        if (SharedPref.read(SharedPref.clubType,"").equalsIgnoreCase("DIAMOND") || SharedPref.read(SharedPref.dashboardClubType,"").equalsIgnoreCase("DIAMOND")) {
            toolbar.setTitle(SharedPref.read(SharedPref.clubType,"DIAMOND"));
            toolbar.setBackgroundResource(R.drawable.diamond_grediend);
        }else if (SharedPref.read(SharedPref.clubType,"").equalsIgnoreCase("GOLD") || SharedPref.read(SharedPref.dashboardClubType,"").equalsIgnoreCase("GOLD")){
            toolbar.setTitle(SharedPref.read(SharedPref.clubType,"GOLD"));
            toolbar.setBackgroundResource(R.drawable.gold_gredient);
        }else {
            toolbar.setTitle(SharedPref.read(SharedPref.clubType,"SSSPLTD"));
            toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_ssspltd));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




//        GifMovieView gifViewPlayer =(GifMovieView) findViewById(R.id.gifViewPlayer);
//        gifViewPlayer.setMovieUrl
//                ("http://app.ssspltd.com/sssimages/app/diamond.gif");
//
//        gifViewPlayer.setPaused(true);
//        // gif animation play
//        gifViewPlayer.setPaused(false);


//        clubdetailList = new ArrayList<>();
//        listType = new TypeToken<ClubTypePojo>() {
//        }.getType();
//
////        recyclerView = findViewById(R.id.rv_home);
//        mAdapter = new MyVideosAdapter(clubdetailList, p);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        //todo before setAdapter
//        recyclerView.setActivity(this);
//
//        //optional - to play only first visible video
//
//        recyclerView.setPlayOnlyFirstVideo(true); // false by default
//        //optional - by default we check if url ends with ".mp4". If your urls do not end with mp4, you can set this param to false and implement your own check to see if video points to url
//        recyclerView.setCheckForMp4(false); //true by default
//        //optional - download videos to local storage (requires "android.permission.WRITE_EXTERNAL_STORAGE" in manifest or ask in runtime)
//        recyclerView.setDownloadPath(Environment.getExternalStorageDirectory() + "/MyVideo"); // (Environment.getExternalStorageDirectory() + "/Video") by default
//        recyclerView.setDownloadVideos(true); // false by default
//        recyclerView.setVisiblePercent(10); // percentage of View that needs to be visible to start playing
//        //extra - start downloading all videos in background before loading RecyclerView
//        List<String> urls = new ArrayList<>();
//        for (Clubdetail object : clubdetailList) {
//            if (object.getIconImage() != null && (object.getIconImage().contains("http")))
//                urls.add(object.getIconImage());
//        }
//        recyclerView.preDownload(urls);
//        recyclerView.setAdapter(mAdapter);
//        new Handler().postDelayed(() -> recyclerView.playAvailableVideos(0), 3000);
//        recyclerView.playAvailableVideos(0);
//        //call this functions when u want to start autoplay on loading async lists (eg firebase)
////        recyclerView.smoothScrollBy(0,-1);
////        recyclerView.smoothScrollBy(0,-1);
//        SnapHelper shaSnapHelper = new PagerSnapHelper();
//        shaSnapHelper.attachToRecyclerView(recyclerView);

        getClubType();


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        recyclerView.playAvailableVideos(0);
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        //add this code to pause videos (when app is minimised or paused)
//        recyclerView.stopVideos();
//    }



    private void getClubType() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, CLUB_TYPE_BY_ACOUNT_ID, response -> {
            Log.e("Api Cat ", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("ResponseStatus")) {
                  loadFile(jsonObject.getString("IconImage"),jsonObject.getString("FeatureImage"));
                } else {
                    Toast.makeText(ClubTypeActivity.this, jsonObject.getString("ResponseMessage")+"", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("Exce3rrr", e.toString());
                Log.e("Exce3rrr", e.getMessage());
            }
        }, error -> {
            Log.e("error", error.toString() + "");
            Log.e("error", error.getMessage() + "");
            // networkDialog();
        }){
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN,""));
            return headers;
        }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = null;
                if (SharedPref.read(SharedPref.noClubType,"").equals("true")){
                    str = "{\"ClubType\":\"" + SharedPref.read(SharedPref.dashboardClubType,"") + "\"}";
                }else {
                     str = "{\"ClubType\":\"" + SharedPref.read(SharedPref.clubType,"") + "\"}";
                }


                Log.e("clubStr",str);
                return str.getBytes();
            }
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFile(String gif,String image){
        if (SharedPref.read(SharedPref.clubType,"").equalsIgnoreCase("DIAMOND")) {
            Glide.with(context) // replace with 'this' if it's in activity
                    .load(gif)
                    .placeholder(R.drawable.diamond_placeholder)
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(imageView);
        }else if (SharedPref.read(SharedPref.clubType,"").equalsIgnoreCase("GOLD")){
            Glide.with(context) // replace with 'this' if it's in activity
                    .load(gif)
                    .placeholder(R.drawable.gold_placeholder)
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(imageView);
        }
        Glide.with(context) // replace with 'this' if it's in activity
                .load(image)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(img);
    }
}