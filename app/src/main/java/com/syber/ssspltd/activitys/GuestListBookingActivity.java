package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.CancelStayBooking;
import static com.syber.ssspltd.Constants.NewErpUrls.DeleteGuestMasterData;
import static com.syber.ssspltd.Constants.NewErpUrls.GetGuestMasterListByCustomerId;
import static com.syber.ssspltd.Constants.NewErpUrls.STATION_LIST;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.MyConstant;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.Util;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.GuestListBookingAdapter;
import com.syber.ssspltd.databinding.ActivityGuestListBookingBinding;
import com.syber.ssspltd.model.booking.branchlist.GuestMasterDetail;
import com.syber.ssspltd.model.booking.branchlist.GuestMasterResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestListBookingActivity extends AppCompatActivity implements GuestListBookingAdapter.OnCancelListener {
    private ActivityGuestListBookingBinding binding;
    ArrayList<GuestMasterDetail> guestList;
    private GuestListBookingAdapter guestListBookingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuestListBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.backBookingList.setOnClickListener(v -> {
            finish();
        });
        initUi();
    }

    private void initUi() {
        guestList = new ArrayList<>();
        String account_id = getIntent().getStringExtra(MyConstant.ACCOUNT_ID);
        getGuestList(account_id);
        List<String> checkGuestList = Arrays.asList("id1", "id2", "id3"); // List of IDs to be checked

        guestListBookingAdapter = new GuestListBookingAdapter(this, guestList,this,checkGuestList, MyConstant.GUEST);
        binding.recycler.setAdapter(guestListBookingAdapter);

    }

    private void getGuestList(String account_id) {
        String getGuestMasterListByCustomerId="";
        getGuestMasterListByCustomerId = GetGuestMasterListByCustomerId+ "?accountId=" + account_id+ "&partyCode=" + SharedPref.read(SharedPref.PARTY_CODE, "");
        String finalGetGuestMasterListByCustomerId = getGuestMasterListByCustomerId;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getGuestMasterListByCustomerId, response -> {
            Log.i("TaG", "Response " + finalGetGuestMasterListByCustomerId + "---> " + response);
            try {
                guestList.clear();
                Gson gson = new Gson();
                GuestMasterResponse bookingResponse = gson.fromJson(response, GuestMasterResponse.class);
                //   JSONObject jsonObject = new JSONObject(response);
                guestList.addAll(bookingResponse.getGuestMasterDetailList());

                List<String> checkGuestList = Arrays.asList("id1", "id2"); // List of IDs to be checked
                guestListBookingAdapter = new GuestListBookingAdapter(this, guestList,this,checkGuestList, MyConstant.GUEST);
                binding.recycler.setAdapter(guestListBookingAdapter);
                guestListBookingAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                Log.e("Exce", e.toString());
            }

        }, error -> {
            Toast.makeText(this, error.getMessage() + "", Toast.LENGTH_LONG).show();
            Log.e("Volly ", error.getMessage() + "");
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonBody = new JSONObject();
                try {

//                    jsonBody.put("SupplierAccountID", SharedPref.read(SharedPref.PARTY_CODE, ""));
                    //     jsonBody.put("SupplierAccountID", selectedAccountId);

                    Log.i("TaG", "Request " + STATION_LIST + "---> " + jsonBody);
                    return jsonBody.toString().getBytes("utf-8");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    public void onBookingCancel(int position, GuestMasterDetail data) {
        new  AlertDialog.Builder(this)
                .setTitle("Cancel Guest")
                .setMessage("Are you sure you want to cancel this Guest?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    bookingCancel(position,data);
                    //   Toast.makeText(this, "Booking Canceled", Toast.LENGTH_SHORT).show();

                })
                .setNegativeButton("No", null)
                .show();
    }

    private void bookingCancel(int position, GuestMasterDetail data) {
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
        String url = DeleteGuestMasterData + "?recordId=" + data.getId();

//        myProgress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            Util.getInstance().logLargeString("TaG", "Response " + url + "---> " + response);
//            Log.i("TaG", "Response " + SAVE_ORDER  +"---> " + response);


            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("ResponseCode") == 200) {
                    progressDialog.dismiss();
                    guestListBookingAdapter.removeItem(position); // Remove item from RecyclerView
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

