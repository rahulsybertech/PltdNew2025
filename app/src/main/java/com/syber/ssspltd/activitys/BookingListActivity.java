package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.CancelStayBooking;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_BLACK_LIST_NAME;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_ORDER;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_UPDATEBOOKING;
import static com.syber.ssspltd.Constants.NewErpUrls.StayBookingDataList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Responses.customer.BlackListedName;
import com.syber.ssspltd.Responses.customer.CustomerListPojo;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.MyConstant;
import com.syber.ssspltd.Utils.Util;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.activitys.supplierorderform.SupplierOrderFormActivity;
import com.syber.ssspltd.adapter.BookingListAdapter;
import com.syber.ssspltd.adapter.PendingOrderReportAdapter;
import com.syber.ssspltd.databinding.ActivityBookingListBinding;
import com.syber.ssspltd.databinding.ActivityPendingOrderBinding;
import com.syber.ssspltd.model.booking.BookingData;

import com.syber.ssspltd.model.booking.StayBookingResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingListActivity extends AppCompatActivity implements  BookingListAdapter.OnBookingCancelListener{
    private ActivityBookingListBinding binding;
    public static ArrayList<BookingData> stayBookingList, data;
    BookingListAdapter adapter;
    private Type listType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

    private void initUi() {
        stayBookingList = new ArrayList<>();
       ImageView backBookingList = findViewById(R.id.backBookingList);
        RelativeLayout plusButton = findViewById(R.id.plusButton);
        backBookingList.setOnClickListener(v -> onBackPressed());


        plusButton.setOnClickListener(v ->
               startActivity(new Intent(this, BookingRequestActivity.class)
                        .putExtra(MyConstant.EXTRA_IS_EDIT,false).putExtra(MyConstant.SCREEN,MyConstant.BOOKINGlIST)
                )
        );
        setRecyler();
        getBookingList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initUi();
    }

    private void setRecyler(){
        adapter = new BookingListAdapter(this, stayBookingList,this);
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

    // Callback method when cancel button is clicked
    @Override
    public void onBookingCancel(int position,BookingData data) {
        // Show confirmation dialog before deleting
        new  AlertDialog.Builder(this)
                .setTitle("Cancel Booking")
                .setMessage("Are you sure you want to cancel this booking?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    bookingCancel(position,data);
                 //   Toast.makeText(this, "Booking Canceled", Toast.LENGTH_SHORT).show();

                })
                .setNegativeButton("No", null)
                .show();
    }


    private void bookingCancel(int position,BookingData data) {
        //test code for disable all views
//        for(int i = 0; i < binding.llLl.getChildCount(); i++){
//            View v = binding.llLl.getChildAt(i);
//            v.setEnabled(false);
//        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        // Append recordId as a query parameter in the URL
        String url = CancelStayBooking + "?recordId=" + data.getId();

//        myProgress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            Util.getInstance().logLargeString("TaG", "Response " + url + "---> " + response);
//            Log.i("TaG", "Response " + SAVE_ORDER  +"---> " + response);


            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("ResponseCode") == 200) {
                    progressDialog.dismiss();
                    adapter.removeItem(position); // Remove item from RecyclerView
                    Toast.makeText(this, jsonObject.getString("ResponseMessage") + "", Toast.LENGTH_SHORT).show();
                } else if (jsonObject.getInt("ResponseCode") == 204) {
//                    myProgress.dismiss();
                    Toast.makeText(this, jsonObject.getString("ResponseMessage") + "", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    AlertUtil.responseElse(this, "", jsonObject.getString("ResponseMessage"));
                } else {
//                    myProgress.dismiss();
                    progressDialog.dismiss();
                    new AlertDialog.Builder(this).setMessage(jsonObject.getString("ResponseMessage") + "").setPositiveButton("Retry", (arg0, arg1) -> bookingCancel(position,data)).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).create().show();
                }
            } catch (JSONException e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        }, error -> {
//            myProgress.dismiss();
            progressDialog.dismiss();
            NetworkResponse response = error.networkResponse;
            if (error instanceof ServerError && response != null) {
                try {
                    String res = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                    // Now you can use any deserializer to make sense of data
                    JSONObject obj = new JSONObject(res);
                    System.out.println("GETTING_ERROR_IN_ORDER " + obj);
                } catch (UnsupportedEncodingException e1) {
                    // Couldn't properly decode data to string
                    e1.printStackTrace();
                } catch (JSONException e2) {
                    // returned data is not JSONObject?
                    e2.printStackTrace();
                }
            }

            // isPlacedOrderBtnEnabled = true;
            new AlertDialog.Builder(this).setMessage("Try again.. Somthing went wrong").setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
//                    myProgress.dismiss();
                    progressDialog.dismiss();
                    bookingCancel(position,data);
                }
            }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).create().show();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                String jsonString = "";
                try {
                    JSONObject jsonObject = new JSONObject();

                /*    {
                        "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                            "companyID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                            "branchID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                            "accountID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                            "date": "2025-02-17T12:36:59.862Z",
                            "checkInDate": "2025-02-17T12:36:59.862Z",
                            "checkInTime": "string",
                            "checkoutDate": "2025-02-17T12:36:59.862Z",
                            "checkoutTime": "string",
                            "noOfPerson": 0,
                            "updatedDate": "2025-02-17T12:36:59.862Z"
                    }*/



                    jsonObject.put("recordId", data.getId());

                    jsonString = jsonObject.toString();
                    System.out.println(jsonString);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("TaG", "Request " + CancelStayBooking + "---> " + jsonString);
                Util.getInstance().logLargeString("TaG", "Request " + CancelStayBooking + "---> " + jsonString);

                return jsonString.getBytes();
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
        RetryPolicy retryPolicy = new DefaultRetryPolicy(800000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}