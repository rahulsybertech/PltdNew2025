package com.syber.ssspltd.activitys.NewGallery;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_YEAR_WISE_ALL_IMAGES;
import static com.syber.ssspltd.activitys.Const.EVENTID;
import static com.syber.ssspltd.activitys.Const.EVENTNAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.NewGalleryAdap.ViewAllAdapter;
import com.syber.ssspltd.databinding.ActivityViewAllBinding;
import com.syber.ssspltd.response.NewGalleryResponse.ViewAll.ImageList;
import com.syber.ssspltd.response.NewGalleryResponse.ViewAll.ViewAllPojo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewAllActivity extends AppCompatActivity {
    private ActivityViewAllBinding binding;
    ViewAllAdapter viewAllAdapter;
    public static ArrayList<ImageList> imageList;
    Type listType;
    Context mContext=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.supportChat.supportFab.setOnClickListener(v ->
                Lazy.openDialog(mContext));


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra(EVENTNAME)+getIntent().getStringExtra("year_name"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageList=new ArrayList<>();
        listType=new TypeToken<ViewAllPojo>(){}.getType();

        viewAllAdapter = new ViewAllAdapter(ViewAllActivity.this,imageList);
        binding.allViewRecycler.setAdapter(viewAllAdapter);
        if (Lazy.haveNetworkConnection(mContext)){
            GetImageList();
        }else {
            networkConnetion3(mContext);
        }

    }

    private void GetImageList() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_YEAR_WISE_ALL_IMAGES,
                response -> {
                    Log.e("Data", GET_YEAR_WISE_ALL_IMAGES + " === " + response);
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    ViewAllPojo pojo = new Gson().fromJson(response,listType);
                    try {
                        if (pojo.getResponseStatus()) {
                            imageList.clear();
                            imageList.addAll(pojo.getImageList());
                            viewAllAdapter.notifyDataSetChanged();
                        } else {
                            AlertUtil.responseElse(mContext, "GetYearWiseAllImages ", pojo.getResponseMessage() + "");
                        }
                    }catch (Exception e){
                        AlertUtil.responseExecption(mContext, "GetYearWiseAllImages ", e.toString());
                    }
                }, error -> AlertUtil.responseError(mContext, "GetYearWiseAllImages ", error.toString())) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"EventID\":\"" + getIntent().getStringExtra(EVENTID) + "\",\"YearID\":\"" + getIntent().getStringExtra("year_id") + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
                return headers;
            }
            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(getApplication()).addToRequestQueue(stringRequest);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
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
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        cross.setOnClickListener(v -> alertDialog.dismiss());
        try_button.setOnClickListener(view -> {
            GetImageList();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }
}