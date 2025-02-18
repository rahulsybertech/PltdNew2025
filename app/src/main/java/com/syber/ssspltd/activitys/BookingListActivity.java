package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_BLACK_LIST_NAME;
import static com.syber.ssspltd.Constants.NewErpUrls.StayBookingDataList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Responses.customer.BlackListedName;
import com.syber.ssspltd.Responses.customer.CustomerListPojo;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.MyConstant;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.activitys.supplierorderform.SupplierOrderFormActivity;
import com.syber.ssspltd.adapter.BookingListAdapter;
import com.syber.ssspltd.adapter.PendingOrderReportAdapter;
import com.syber.ssspltd.databinding.ActivityBookingListBinding;
import com.syber.ssspltd.databinding.ActivityPendingOrderBinding;
import com.syber.ssspltd.model.booking.BookingData;

import com.syber.ssspltd.model.booking.StayBookingResponse;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingListActivity extends AppCompatActivity {
    private ActivityBookingListBinding binding;
    public static ArrayList<BookingData> stayBookingList, data;
    BookingListAdapter adapter;
    private Type listType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUi();
    }

    private void initUi() {
        stayBookingList = new ArrayList<>();
       ImageView backBookingList = findViewById(R.id.backBookingList);
        RelativeLayout plusButton = findViewById(R.id.plusButton);
        backBookingList.setOnClickListener(v -> onBackPressed());


        plusButton.setOnClickListener(v ->
               startActivity(new Intent(this, BookingRequestActivity.class)
                        .putExtra(MyConstant.EXTRA_IS_EDIT,false)
                )
        );
        setRecyler();
        getBookingList();

    }
    private void setRecyler(){
        adapter = new BookingListAdapter(this, stayBookingList);
        binding.recyler.setAdapter(adapter);
    }

    private void getBookingList() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, StayBookingDataList, response -> {
//                    Log.e("Data", response);
            binding.includeProgress.progress.setVisibility(View.GONE);
            Log.i("TaG", "url ---" + StayBookingDataList);
            Log.i("TaG", "response ---> " + response);
            StayBookingResponse pojo = new Gson().fromJson(response, StayBookingResponse.class);
            try {
                if (pojo.isResponseStatus()) {
                    stayBookingList.clear();
                    stayBookingList.addAll(pojo.getStayBookingList());
                  //  binding.noOfRecord.setText("(" + pojo.getBlackListedName().size() + " records)");
                    adapter.notifyDataSetChanged();
                } else {
                    AlertUtil.responseElse(this, "", pojo.getResponseMessage() + "");
                }
            } catch (JsonIOException e) {
                AlertUtil.responseExecption(this, "GetStayBookingDataList ", e.toString());
            }

        }, error ->
        {
            try {
                Constants.convertByteToString(this, "GetStayBookingDataList ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}