package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.CancelStayBooking;
import static com.syber.ssspltd.Constants.NewErpUrls.GetStayBookingDataListByBranchId;
import static com.syber.ssspltd.Constants.NewErpUrls.StayBookingDataList;
import static com.syber.ssspltd.Constants.NewErpUrls.StayBookingTime;
import static com.syber.ssspltd.Constants.NewErpUrls.UpdateStayBookingActualTime;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
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
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.MyConstant;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.Util;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.BookingListAdapter;
import com.syber.ssspltd.databinding.ActivityBookingListBinding;
import com.syber.ssspltd.model.booking.BookingData;

import com.syber.ssspltd.model.booking.StayBookingResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookingListActivity extends AppCompatActivity implements  BookingListAdapter.OnBookingCancelListener{
    private ActivityBookingListBinding binding;
    public static ArrayList<BookingData> stayBookingList, data,filterList;
    BookingListAdapter adapter;
    private Type listType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingListBinding.inflate(getLayoutInflater());
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(binding.getRoot());
        // Handle system bars (status + nav bar) insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void initUi() {
        stayBookingList = new ArrayList<>();
        filterList = new ArrayList<>();
       ImageView backBookingList = findViewById(R.id.backBookingList);
        RelativeLayout plusButton = findViewById(R.id.plusButton);
        backBookingList.setOnClickListener(v -> onBackPressed());


        plusButton.setOnClickListener(v -> {

            startActivity(new Intent(this, BookingRequestActivity.class)
                    .putExtra(MyConstant.EXTRA_IS_EDIT, false)
                    .putExtra(MyConstant.SCREEN, MyConstant.BOOKINGlIST)
                    .putExtra(MyConstant.USERTYPE, SharedPref.read(SharedPref.DASHBOARD_TYPE, ""))
            );
      //      this.finish(); // now inside the lambda
        });
        setRecyler();
        getBookingList();

        if (!stayBookingList.isEmpty()) {
            filterBc(stayBookingList);
        } else {
            getBookingList();
        }
        binding. search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterList.clear();
                for (int p = 0; p < stayBookingList.size(); p++) {
                    if (stayBookingList.get(p).getBranchName().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || stayBookingList.get(p).getCompanyID().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || stayBookingList.get(p).getCheckInDate().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || stayBookingList.get(p).getCheckoutDate().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || stayBookingList.get(p).getNoOfPerson().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || stayBookingList.get(p).getBookingID().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || stayBookingList.get(p).getaccountName().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || stayBookingList.get(p).getNickName().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || stayBookingList.get(p).getNickNameID().toLowerCase().contains(charSequence.toString().toLowerCase())

                    ) {
                        filterList.add(stayBookingList.get(p));
                    }
                }
                filterBc(filterList);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    void filterBc(ArrayList<BookingData> bc) {
        binding.noOfRecord.setText("(" + bc.size() + " records )");
        adapter = new BookingListAdapter(this, bc,this);
        binding.recyler.setAdapter(adapter);
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


        String userType = getIntent().getStringExtra(MyConstant.USERTYPE);
        String urlWithPartyCode="";
        if(userType.equals("Other")){
            urlWithPartyCode = GetStayBookingDataListByBranchId+ "?partyCode=" + Uri.encode(SharedPref.read(SharedPref.PARTY_CODE, ""));
        }else {
            urlWithPartyCode = StayBookingDataList + "?partyCode=" + Uri.encode(SharedPref.read(SharedPref.PARTY_CODE, ""));
        }


        String finalUrlWithPartyCode = urlWithPartyCode;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlWithPartyCode, response -> {
//                    Log.e("Data", response);
            binding.includeProgress.progress.setVisibility(View.GONE);
            Log.i("TaG", "url ---" + finalUrlWithPartyCode);
            Log.i("TaG", "response ---> " + response);
            StayBookingResponse pojo = new Gson().fromJson(response, StayBookingResponse.class);
            try {
                if (pojo.isResponseStatus()) {
                    stayBookingList.clear();
                    stayBookingList.addAll(pojo.getStayBookingList());
                    Collections.sort(stayBookingList, new Comparator<BookingData>() {
                        @Override
                        public int compare(BookingData o1, BookingData o2) {
                            return Integer.compare(
                                    Integer.parseInt(o2.getBookingID()),
                                    Integer.parseInt(o1.getBookingID())
                            );
                        }
                    });
                    binding.noOfRecord.setText("(" + pojo.getStayBookingList().size() + " records)");
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
        )

        {

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonBody = new JSONObject();
                try {
                    Log.i("TaG", "Request " + StayBookingTime + "---> " + jsonBody);
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

    @Override
    public void onCheckInClicked(int position, BookingData data,String value) {
        bookingCheckInCheckOut(position,data,value);
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
    private void bookingCheckInCheckOut(int position, BookingData data, String value) {
        //test code for disable all views
//        for(int i = 0; i < binding.llLl.getChildCount(); i++){
//            View v = binding.llLl.getChildAt(i);
//            v.setEnabled(false);
//        }
        Boolean isStaySucess; // Nullable Boolean
        String checkIn = data.getActualCheckInDate();
        String checkOut = data.getActualCheckoutDate();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());
        Log.d("DateTime", currentDateTime);
        if(value.equals("Stay")){
            Boolean isStay = data.getIsStay(); // Nullable Boolean
            if (isStay == null) {
                url = UpdateStayBookingActualTime + "?bookingId=" + data.getId()+"&isStay=" + true;
                isStaySucess=true;
            } else if (isStay) {
                url = UpdateStayBookingActualTime + "?bookingId=" + data.getId() + "&isStay=" +false;
                isStaySucess=false;
            } else {
                url = UpdateStayBookingActualTime + "?bookingId=" + data.getId() + "&isStay=" +true;
                isStaySucess=true;
            }

        }else {
            isStaySucess = data.getIsStay();
            if ((checkIn == null || checkIn.isEmpty()) && (checkOut == null || checkOut.isEmpty())) {
                url = UpdateStayBookingActualTime + "?bookingId=" + data.getId() + "&actualCheckInDate=" + currentDateTime;
            } else if (checkOut == null || checkOut.isEmpty()) {

                url = UpdateStayBookingActualTime + "?bookingId=" + data.getId() + "&actualCheckoutDate=" +currentDateTime;
            } else {
                url = "";
            }
        }

        // Append recordId as a query parameter in the URL


//        myProgress.show();
        String finalUrl = url;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            Util.getInstance().logLargeString("TaG", "Response " + finalUrl + "---> " + response);
//            Log.i("TaG", "Response " + SAVE_ORDER  +"---> " + response);


            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("ResponseCode") == 200) {
                    progressDialog.dismiss();
                    if ((checkIn == null || checkIn.isEmpty()) && (checkOut == null || checkOut.isEmpty())) {
                        data.setActualCheckInDate(currentDateTime); // Update check-in
                    } else if (checkOut == null || checkOut.isEmpty()) {
                        if(value.equals("Stay")){
                            data.setIsStay(isStaySucess);
                        }else {
                            data.setActualCheckoutDate(currentDateTime); // Update check-out
                        }

                    }
                    if(value.equals("Stay")){{
                          if (checkOut!= null && checkIn!=null) {
                            //  data.setActualCheckoutDate(currentDateTime); // Update check-out
                            data.setIsStay(isStaySucess); // Update check-out
                        }
                    }}

                    // Notify the adapter that item has changed
                    adapter.notifyItemChanged(position);  // 🔁 Refresh this item in RecyclerView


                    //    adapter.removeItem(position); // Remove item from RecyclerView
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
                    bookingCheckInCheckOut(position,data, value);
                }
            }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).create().show();
        }) {
           /* @Override
            public byte[] getBody() throws AuthFailureError {


                String jsonString = "";
                try {
                    JSONObject jsonObject = new JSONObject();

                *//*    {
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
                    }*//*


                    if ((checkIn == null || checkIn.isEmpty()) && (checkOut == null || checkOut.isEmpty())) {
                        jsonObject.put("actualCheckInDate", currentDateTime);
                        jsonObject.put("actualCheckoutDate", JSONObject.NULL);

                     //   url = UpdateStayBookingActualTime + "?bookingId=" + data.getId() + "?actualCheckInDate=" + currentDateTime + "?actualCheckoutDate=" + JSONObject.NULL;
                    } else if (checkIn == null || checkIn.isEmpty()) {
                        jsonObject.put("actualCheckInDate", JSONObject.NULL);
                        jsonObject.put("actualCheckoutDate", currentDateTime);
                     //   url = UpdateStayBookingActualTime + "?bookingId=" + data.getId() + "?actualCheckInDate=" + JSONObject.NULL + "?actualCheckoutDate=" +currentDateTime ;
                    } else {
                        *//*jsonObject.put("actualCheckInDate", currentDateTime);
                        jsonObject.put("actualCheckoutDate", currentDateTime);*//*
                    }
                    jsonObject.put("bookingId", data.getId());

                    jsonString = jsonObject.toString();
                    System.out.println(jsonString);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("TaG", "Request " + CancelStayBooking + "---> " + jsonString);
                Util.getInstance().logLargeString("TaG", "Request " + CancelStayBooking + "---> " + jsonString);

                return jsonString.getBytes();
            }*/

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