package com.syber.ssspltd.activitys.NewGallery;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_ALL_YEAR_WISE_EVENT_IMAGE;
import static com.syber.ssspltd.activitys.Const.EVENTID;
import static com.syber.ssspltd.activitys.Const.EVENTNAME;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.NewGalleryAdap.YearAdap.YearListAdapter;
import com.syber.ssspltd.databinding.ActivityViewMoreBinding;
import com.syber.ssspltd.response.NewGalleryResponse.YearGallery.Year;
import com.syber.ssspltd.response.NewGalleryResponse.YearGallery.YearGalleryPojo;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewMoreActivity extends AppCompatActivity {
    public static ArrayList<Year> imageList;
    public static String eventId;
    public static String event_name;
    YearListAdapter yearAdapter;
    Type listType;
    TextView eventName;
    Context mContext = this;
    private ActivityViewMoreBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewMoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Handle system bars (status + nav bar) insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.supportChat.supportFab.setOnClickListener(v ->
                Lazy.openDialog(mContext));

//        eventName.setText(getIntent().getStringExtra("eventName"));
        imageList = new ArrayList<>();
        listType = new TypeToken<YearGalleryPojo>() {
        }.getType();
        eventId = getIntent().getStringExtra(EVENTID);
        event_name = getIntent().getStringExtra(EVENTNAME);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra(EVENTNAME));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        yearAdapter = new YearListAdapter(ViewMoreActivity.this, imageList, eventId, event_name);
        binding.eventImgRecyc.setAdapter(yearAdapter);
        if (Lazy.haveNetworkConnection(mContext)) {
            GetImageList();
        } else {
            networkConnetion3(mContext);
        }
    }

    private void GetImageList() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_ALL_YEAR_WISE_EVENT_IMAGE, response -> {
            binding.includeProgress.progress.setVisibility(View.GONE);
            Log.e("Data", GET_ALL_YEAR_WISE_EVENT_IMAGE + " ======= " + response);
            YearGalleryPojo pojo = new Gson().fromJson(response, listType);
            try {
                if (pojo.getResponseStatus()) {
                    imageList.clear();
                    imageList.addAll(pojo.getYear());
                    String brandName = pojo.getEventName();
                    binding.eventName.setText(brandName);
                    Log.e("logo", pojo.getEventLogo());
                    String eventLogo = pojo.getEventLogo();
                    try {
                        Glide
                                .with(mContext)
                                .load(eventLogo)
                                .placeholder(R.drawable.sss_logo)
                                .into((binding.setImgLogo));
                    } catch (Exception e) {

                    }
                    yearAdapter.notifyDataSetChanged();
                } else {
                    AlertUtil.responseElse(mContext, "GetAllYearWiseEventImages ", pojo.getResponseMessage() + "");
                }
            } catch (Exception e) {
                AlertUtil.responseExecption(mContext, "GetAllYearWiseEventImages ", e.toString());
            }
        }, error ->
        {
            try {
                Constants.convertByteToString(mContext, "GetAllYearWiseEventImages ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }) {
            @Override
            public byte[] getBody() {
                String str = "{\"EventID\":\"" + getIntent().getStringExtra(EVENTID) + "\"}";
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
        VolleySingleton.getInstance(getApplication()).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void networkConnetion3(Context mContext) {

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