package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.BRANCH_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_ORDER;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_UPDATEBOOKING;
import static com.syber.ssspltd.Constants.NewErpUrls.STATION_LIST;
import static com.syber.ssspltd.Utils.MyConstant.EXTRA_IS_EDIT;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.Util;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.databinding.ActivityBookingListBinding;
import com.syber.ssspltd.databinding.ActivityBookingRequestBinding;
import com.syber.ssspltd.model.booking.StayBooking;
import com.syber.ssspltd.response.BookingData;
import com.syber.ssspltd.response.BookingResponse;
import com.syber.ssspltd.response.StationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingRequestActivity extends AppCompatActivity {
    private ActivityBookingRequestBinding binding;
    private Calendar checkInCalendar, checkOutCalendar;
    private Calendar checkInTimeCalendar, checkOutTimeCalendar;
    private boolean isTodaySelected = false;
    ArrayList<BookingData> branchList;
    private Boolean isPlacedOrderBtnEnabled = true;
    private String selectBranch="";

    boolean isEditMode;
    com.syber.ssspltd.model.booking.BookingData bookingData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initUi();
    }

    private void populateData(com.syber.ssspltd.model.booking.BookingData bookingData) {
        binding.tvCheckInDate.setText(bookingData.getCheckInDate());
        binding.tvCheckOutDate.setText(bookingData.getCheckoutDate());
        binding.tvCheckInTime.setText(bookingData.getCheckInTime());
        binding.tvCheckOutTime.setText(bookingData.getCheckoutTime());
        binding.editNoOfPerson.setText(bookingData.getNoOfPerson());
    }

    private void initUi() {
        ImageView backBookingList = findViewById(R.id.backBookingList);
        branchList = new ArrayList<>();
        backBookingList.setOnClickListener(v -> onBackPressed());
       // setSpinner();
        checkInCalendar = Calendar.getInstance();
        checkOutCalendar = Calendar.getInstance();

        checkInTimeCalendar = Calendar.getInstance();
        checkOutTimeCalendar = Calendar.getInstance();

        binding.llCheckIn.setOnClickListener(v ->showCheckInDatePicker() );
        binding.llCheckOut.setOnClickListener(v ->showCheckOutDatePicker() );
        binding.save.setOnClickListener(v ->{

            if (validate() && isPlacedOrderBtnEnabled) {
                isPlacedOrderBtnEnabled = false;
                binding.save.setEnabled(false);
                binding.save.setBackgroundColor(Color.parseColor("#808080"));
                binding.tvSave.setText("Please Wait...");
                sendData();
            }

        } );

        binding.llCheckInTime.setOnClickListener(v ->showCheckInTimePicker(false) );
        binding.llCheckOutTime.setOnClickListener(v ->showCheckOutTimePicker() );

        isEditMode = getIntent().getBooleanExtra(EXTRA_IS_EDIT, false);
        getStation();
        if (isEditMode) {

            bookingData = getIntent().getParcelableExtra("data");
            if (bookingData != null) {
                populateData(bookingData); // Load existing data into UI fields
            }
        }else {
        ;
        }

       // binding.plusButton.setOnClickListener(v ->   startActivity(new Intent(this, BookingRequestActivity.class)));

    }


    public void handleClear(View view) {
        if (view.getId() == R.id.clear_marketer) {
            binding.editNoOfPerson.setText("");
            binding.clearNoPerson.setVisibility(View.GONE);
        }
    }
    private boolean validate() {
        boolean temp = true;


        // Check if branch is selected
        if (selectBranch.isEmpty()) {
            Toast.makeText(this, "Please select a branch!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if check-in date is selected
        if (binding.tvCheckInDate.getText().toString().isEmpty()) {
            binding.tvCheckInDate.setError("Can't be empty");
            Toast.makeText(this, "Please select a check-in date!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if check-in time is selected
        if (binding.tvCheckInTime.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select a check-in time!", Toast.LENGTH_SHORT).show();
          binding.tvCheckInTime.setError("Can't be empty");
            return false;
        }

        // Check if check-out date is selected
        if (binding.tvCheckOutDate.getText().toString().isEmpty()) {
            binding.tvCheckOutDate.setError("Can't be empty");
            Toast.makeText(this, "Please select a check-out date!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if check-out time is selected
        if (binding.tvCheckOutTime.getText().toString().isEmpty()) {
            binding.tvCheckOutTime.setError("Can't be empty");
            Toast.makeText(this, "Please select a check-out time!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.editNoOfPerson.getText().toString().isEmpty()) {
            binding.editNoOfPerson.setError("Can't be empty");
            Toast.makeText(this, "Please enter No of person", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.editNoOfPerson.getScrollX(), binding.editNoOfPerson.getScrollY());
            temp = false;
        }

        // Check if number of persons is valid
        String persons = binding.editNoOfPerson.getText().toString();
        if (persons.isEmpty() || Integer.parseInt(persons) <= 0) {
            binding.editNoOfPerson.setError("Can't be empty");
            Toast.makeText(this, "Please enter a valid number of persons!", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.editNoOfPerson.getScrollX(), binding.editNoOfPerson.getScrollY());

            return false;
        }
           return temp;

    }
    private void setSpinner() {
        // List of items

        List<String> items = new ArrayList<>();
        items.add("Select Branch"); // Add a default item

        for (BookingData branch : branchList) {
            items.add(branch.getDbPrefix()); // Add each dbPrefix to the list
        }



        // Adapter for the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set adapter to Spinner
        binding.spinner.setAdapter(adapter);
        if (isEditMode){
            String savedValue = bookingData.getBranchName();
            // Find the position of the saved value
            int position = adapter.getPosition(savedValue);

// Set the Spinner selection
            if (position >= 0) {
            binding.spinner.setSelection(position);
            selectBranch = branchList.get(position).getId();
            }
        }





        // Handle item selection
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) { // Ignore first item
                    selectBranch = branchList.get(position).getId();
              //      Toast.makeText(BookingRequestActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void showCheckInDatePicker() {
        Calendar calendar = Calendar.getInstance(); // Get current date
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    checkInCalendar.set(selectedYear, selectedMonth, selectedDay);
                    // Ensure two-digit month format (e.g., "01" for January)
                    String formattedMonth = String.format("%02d", selectedMonth + 1); // Convert 0-based month to 1-based

                    // Ensure two-digit day format (e.g., "01" for single-digit days)
                    String formattedDay = String.format("%02d", selectedDay);
           //         String selectedDate = selectedDay + "/" + formattedMonth + "/" + selectedYear;
                    String selectedDateText = selectedYear + "-" + formattedMonth + "-" + formattedDay;


                    binding.tvCheckInDate.setText(selectedDateText);
                    binding.tvCheckInDate.setError(null, null);
                    binding.tvCheckInTime.setText(""); // Reset Check-In Time
                    binding.tvCheckOutTime.setText(""); // Reset Check-Out Time
                    binding.tvCheckOutDate.setText(""); // Reset Check-Out Time

                    // Check if selected date is today
                    if (isToday(selectedYear, selectedMonth, selectedDay)) {
                        showCheckInTimePicker(true); // Enforce 12-hour restriction
                    } else {
                        showCheckInTimePicker(false); // Allow all times
                    }
                },
                year,
                month,
                day
        );

        // Disable past dates
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void showCheckInTimePicker(boolean enforce12HourRestriction) {
        if (binding.tvCheckInDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select Check-In date first!", Toast.LENGTH_SHORT).show();
            return;
        }
        Calendar currentCalendar = Calendar.getInstance();
        int currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentCalendar.get(Calendar.MINUTE);

        int minHour, minMinute;

        if (enforce12HourRestriction) {
            // Enforce minimum time = current time + 12 hours
            minHour = currentHour + 12;
            if (minHour >= 24) {
                minHour = 23; // Restrict max to 11 PM
                minMinute = 59;
            } else {
                minMinute = currentMinute;
            }
        } else {
            // If future date is selected, allow any time
            minHour = 0;
            minMinute = 0;
        }

        int finalMinHour = minHour;
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    // Check if user selects an invalid time (today + within the past)
                    if (enforce12HourRestriction && (hourOfDay < currentHour || (hourOfDay == currentHour && minute < currentMinute))) {
                        Toast.makeText(this, "Invalid time! Please select a future time.", Toast.LENGTH_SHORT).show();
                    } else if (enforce12HourRestriction && (hourOfDay < finalMinHour || (hourOfDay == finalMinHour && minute < minMinute))) {
                        // Ensure time is at least 12 hours from now
                        Toast.makeText(this, "Check-in time must be at least 12 hours from now.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Valid time selection
                        checkInTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        checkInTimeCalendar.set(Calendar.MINUTE, minute);
                        binding.tvCheckInTime.setError(null, null);
                        binding.tvCheckInTime.setText(formatTime(hourOfDay, minute));
                        binding.tvCheckOutTime.setText(""); // Reset Check-Out Time
                        binding.tvCheckOutDate.setText(""); // Reset Check-Out Time
                    }
                },
                enforce12HourRestriction ? minHour : 0, // Minimum time condition
                enforce12HourRestriction ? minMinute : 0,
                false // Use 24-hour format
        );
        timePickerDialog.show();
    }


    private boolean isToday(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) == year &&
                calendar.get(Calendar.MONTH) == month &&
                calendar.get(Calendar.DAY_OF_MONTH) == day;
    }

    private String formatTime(int hour, int minute) {
        String amPm = (hour >= 12) ? "PM" : "AM";
        int formattedHour = (hour > 12) ? hour - 12 : (hour == 0 ? 12 : hour);
        return String.format("%02d:%02d %s", formattedHour, minute, amPm);
    }



    private void showCheckOutDatePicker() {
        if (binding.tvCheckInDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select Check-In date first!", Toast.LENGTH_SHORT).show();
            return;
        }
        int year = checkOutCalendar.get(Calendar.YEAR);
        int month = checkOutCalendar.get(Calendar.MONTH);
        int day = checkOutCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    checkOutCalendar.set(selectedYear, selectedMonth, selectedDay);
                    String formattedMonth = String.format("%02d", selectedMonth + 1); // Convert 0-based month to 1-based

                    // Ensure two-digit day format (e.g., "01" for single-digit days)
                    String formattedDay = String.format("%02d", selectedDay);
                    //         String selectedDate = selectedDay + "/" + formattedMonth + "/" + selectedYear;
                    String selectedDateText = selectedYear + "-" + formattedMonth + "-" + formattedDay;
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;;
                    binding.tvCheckOutDate.setError(null, null);
                    binding.tvCheckOutDate.setText(selectedDateText);
                    binding.tvCheckOutTime.setText("");
                    // Update Check-Out Time to be at least the same as Check-In
                    showCheckOutTimePicker();
                },
                year,
                month,
                day
        );

        // Set minimum selectable date to Check-In date
        datePickerDialog.getDatePicker().setMinDate(checkInCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void showCheckOutTimePicker() {
        if (binding.tvCheckInTime.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select Check-In time first!", Toast.LENGTH_SHORT).show();
            return;
        }

        int checkInHour = checkInTimeCalendar.get(Calendar.HOUR_OF_DAY);
        int checkInMinute = checkInTimeCalendar.get(Calendar.MINUTE);

        int minHour = checkInHour;
        int minMinute = checkInMinute;

        // Allow any time if check-out date is different from check-in date
        if (checkOutCalendar.get(Calendar.YEAR) != checkInCalendar.get(Calendar.YEAR) ||
                checkOutCalendar.get(Calendar.MONTH) != checkInCalendar.get(Calendar.MONTH) ||
                checkOutCalendar.get(Calendar.DAY_OF_MONTH) != checkInCalendar.get(Calendar.DAY_OF_MONTH)) {
            minHour = 0;
            minMinute = 0;
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    // Validate Check-Out time must be later than Check-In time on the same day
                    if (checkOutCalendar.get(Calendar.YEAR) == checkInCalendar.get(Calendar.YEAR) &&
                            checkOutCalendar.get(Calendar.MONTH) == checkInCalendar.get(Calendar.MONTH) &&
                            checkOutCalendar.get(Calendar.DAY_OF_MONTH) == checkInCalendar.get(Calendar.DAY_OF_MONTH)) {

                        if (hourOfDay < checkInHour || (hourOfDay == checkInHour && minute < checkInMinute)) {
                            binding.tvCheckOutTime.setText("");
                            Toast.makeText(this, "Check-Out time must be later than Check-In time.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (hourOfDay == checkInHour && minute == checkInMinute) {
                            binding.tvCheckOutTime.setText("");
                            Toast.makeText(this, "Check-In and Check-Out time cannot be the same.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    binding.tvCheckOutTime.setError(null, null);
                    // Set valid Check-Out time
                    checkOutTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    checkOutTimeCalendar.set(Calendar.MINUTE, minute);

                    binding.tvCheckOutTime.setText(formatTime(hourOfDay, minute));
                },
                minHour,
                minMinute,
                false
        );
        timePickerDialog.show();
    }


    private void sendData() {
        //test code for disable all views
//        for(int i = 0; i < binding.llLl.getChildCount(); i++){
//            View v = binding.llLl.getChildAt(i);
//            v.setEnabled(false);
//        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

//        myProgress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SAVE_UPDATEBOOKING, response -> {
            Util.getInstance().logLargeString("TaG", "Response " + SAVE_ORDER + "---> " + response);
//            Log.i("TaG", "Response " + SAVE_ORDER  +"---> " + response);

            isPlacedOrderBtnEnabled = true;
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("ResponseCode") == 200) {
                    progressDialog.dismiss();
                    finish();
                    Toast.makeText(this, jsonObject.getString("ResponseMessage") + "", Toast.LENGTH_SHORT).show();
                } else if (jsonObject.getInt("ResponseCode") == 204) {
//                    myProgress.dismiss();
                    Toast.makeText(this, jsonObject.getString("ResponseMessage") + "", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    AlertUtil.responseElse(this, "", jsonObject.getString("ResponseMessage"));
                } else {
//                    myProgress.dismiss();
                    progressDialog.dismiss();
                    new AlertDialog.Builder(this).setMessage(jsonObject.getString("ResponseMessage") + "").setPositiveButton("Retry", (arg0, arg1) -> sendData()).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).create().show();
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
                    sendData();
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

                    jsonObject.put("id", JSONObject.NULL);
                    jsonObject.put("companyID", JSONObject.NULL);
                    jsonObject.put("branchID", selectBranch);
                    jsonObject.put("accountID", JSONObject.NULL);
                    jsonObject.put("checkInDate", binding.tvCheckInDate.getText().toString());
                    jsonObject.put("checkInTime", binding.tvCheckInTime.getText().toString());
                    jsonObject.put("checkoutDate", binding.tvCheckOutDate.getText().toString());
                    jsonObject.put("checkoutTime", binding.tvCheckOutTime.getText().toString());
                    jsonObject.put("noOfPerson", binding.editNoOfPerson.getText().toString());
                    jsonObject.put("updatedDate", JSONObject.NULL);
                    jsonString = jsonObject.toString();
                    System.out.println(jsonString);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("TaG", "Request " + SAVE_ORDER + "---> " + jsonString);
                Util.getInstance().logLargeString("TaG", "Request " + SAVE_ORDER + "---> " + jsonString);

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



 private void getStation() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BRANCH_LIST, response -> {
            Log.i("TaG", "Response " + STATION_LIST + "---> " + response);
            try {
                Gson gson = new Gson();
                BookingResponse bookingResponse = gson.fromJson(response, BookingResponse.class);
             //   JSONObject jsonObject = new JSONObject(response);
                branchList.addAll(bookingResponse.getData());
                setSpinner();
               // stationAdapter.notifyDataSetChanged();
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



}
