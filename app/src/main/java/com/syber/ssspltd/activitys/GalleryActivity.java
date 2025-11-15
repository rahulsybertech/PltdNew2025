package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_ALL_EVENT_IMAGE;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.NewGalleryAdap.NewGalleryAdapter;
import com.syber.ssspltd.databinding.ActivityGalleryBinding;
import com.syber.ssspltd.response.NewGalleryResponse.Event;
import com.syber.ssspltd.response.NewGalleryResponse.NewGalleryPojo;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryActivity extends AppCompatActivity {
    public static List<Event> imageList;
    RecyclerView imagelistRecy;
    NewGalleryAdapter galleryAdapter;
    Type listType;
    ActivityGalleryBinding binding;


    //    RecyclerView imagelistRecy;
//    GalleryAdapter galleryAdapter;
//    List<ImageListAppResult> imageList;
//    Type listType;
//    LinearLayoutManager linearLayoutManager;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGalleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Handle system bars (status + nav bar) insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.supportChat.supportFab.setOnClickListener(v ->
                Lazy.openDialog(context));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Gallery");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imagelistRecy = findViewById(R.id.imagelistRecy);
        imageList = new ArrayList<>();
        listType = new TypeToken<NewGalleryPojo>() {
        }.getType();

        galleryAdapter = new NewGalleryAdapter(context, imageList);
        imagelistRecy.setAdapter(galleryAdapter);
        GetImageList();

//        imagelistRecy=findViewById(R.id.imagelistRecy);
//        imageList=new ArrayList<>();
//        listType=new TypeToken<GalleryPojo>(){}.getType();
//
//        linearLayoutManager = new LinearLayoutManager(context);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        imagelistRecy.setLayoutManager(linearLayoutManager);
//        galleryAdapter = new GalleryAdapter(context,imageList);
//        imagelistRecy.setAdapter(galleryAdapter);
//        GetImageList();
    }


    private void GetImageList() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_ALL_EVENT_IMAGE, response -> {
            Log.e("Data", GET_ALL_EVENT_IMAGE + " ======== " + response);
            binding.includeProgress.progress.setVisibility(View.GONE);
            NewGalleryPojo pojo = new Gson().fromJson(response, listType);
            try {
                if (pojo.getResponseStatus()) {
                    imageList.clear();
                    imageList.addAll(pojo.getEvents());
                    galleryAdapter.notifyDataSetChanged();
                } else {
                    AlertUtil.responseElse(context, "GetAllEventImages ", pojo.getResponseMessage() + "");
                }
            } catch (JsonIOException e) {
                AlertUtil.responseExecption(context, "GetAllEventImages ", e.toString());
            }

        }, error ->
        {
            try {
                Constants.convertByteToString(context, "GetAllEventImages ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                String str = "{}";
                Log.e("str", str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


//    private void GetImageList() {
//        final ProgressDialog progressBar = new ProgressDialog(context);
//        progressBar.setTitle("Fetching Data");
//        progressBar.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://app.ssspltd.com/apipltd/GetImageListDetailsApp",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("Data", response);
//                        progressBar.dismiss();
//                        //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show()
//                        GalleryPojo pojo = new Gson().fromJson(response,listType);
//                        if (pojo.getResponseStatus()){
//                            imageList.clear();
//                            imageList.addAll(pojo.getImageListAppResult());
//                            galleryAdapter.notifyDataSetChanged();
//                        }
//                        else {
//                            Toast.makeText(context, pojo.getResponseMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressBar.cancel();
//            }
//        }) {
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
//                String mob3 = SharedPref.read(SharedPref.USERMOBILE,"");
//                // String otpp = otp.getText().toString();
//                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME,"") + "\"}";
//                Log.e("str", str);
//                return str.getBytes();
//            }
//            public String getBodyContentType()
//            {
//                return "application/json; charset=utf-8";
//            }
//        };
//        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}