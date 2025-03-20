package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.BRANCH_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.GetAccountNameList;
import static com.syber.ssspltd.Constants.NewErpUrls.GetNickNameList;
import static com.syber.ssspltd.Constants.NewErpUrls.GetStayBookingDataListByBranchId;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_ORDER;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_UPDATEBOOKING;
import static com.syber.ssspltd.Constants.NewErpUrls.STATION_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.StayBookingTime;
import static com.syber.ssspltd.Utils.MyConstant.EXTRA_IS_EDIT;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.syber.ssspltd.Utils.MyConstant;
import com.syber.ssspltd.Utils.MyProgress;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.SnackbarUtils;
import com.syber.ssspltd.Utils.Util;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.AccountListAdapter;
import com.syber.ssspltd.adapter.NickNameListAdapter;
import com.syber.ssspltd.databinding.ActivityBookingRequestBinding;
import com.syber.ssspltd.model.booking.branchlist.Account;
import com.syber.ssspltd.model.booking.branchlist.ApiResponse;
import com.syber.ssspltd.model.booking.branchlist.NickNameList;
import com.syber.ssspltd.response.BookingData;
import com.syber.ssspltd.response.BookingResponse;
import com.syber.ssspltd.response.SalepartyModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BookingRequestActivity extends AppCompatActivity {
    private ActivityBookingRequestBinding binding;
    private Calendar checkInCalendar, checkOutCalendar;
    private Calendar checkInTimeCalendar, checkOutTimeCalendar;
    private boolean isTodaySelected = false;
    ArrayList<BookingData> branchList;
    private Boolean isPlacedOrderBtnEnabled = true;
    private Boolean notAllowllTime = false;
    private String selectBranch="";
    private int count = 1;  // Initial value
    private int minHoursSelect=0;
    boolean isEditMode;
    boolean isCustomerCode=true;
    com.syber.ssspltd.model.booking.BookingData bookingData;
    private String selectedDateTextCheckIn="";
    private String selectedDateTextCheckOut="";
    AccountListAdapter accountListAdapter;
    NickNameListAdapter nickNameListAdapter;

    TextView titile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initUi();
    }

    private void populateData(com.syber.ssspltd.model.booking.BookingData bookingData) {
      String checkin=  convertDateFormat(bookingData.getCheckInDate());
      String checkOut=  convertDateFormat(bookingData.getCheckoutDate());
        binding.tvCheckInDate.setText(checkin);
        existingCheckInDate=checkin;
        existingCheckOutDate=checkOut;
        binding.tvAccountName.setText(bookingData.getaccountName());
        accountNameId=bookingData.getaccountID();
        binding.tvCheckOutDate.setText(checkOut);
        binding.tvCheckInTime.setText(bookingData.getCheckInTime());
        existingCheckOutTime=bookingData.getCheckInTime();
        existingCheckOutDateNew=bookingData.getCheckoutTime();
        selectedDateTextCheckIn=bookingData.getCheckInDate();
        selectedDateTextCheckOut=bookingData.getCheckoutDate();
        binding.tvCheckOutTime.setText(bookingData.getCheckoutTime());
        binding.textViewNumber.setText(bookingData.getNoOfPerson());
        int number = Integer.valueOf(bookingData.getNoOfPerson());
        count=number;
        initializeCheckInDateTime(existingCheckInDate);
    }
    public String convertDateFormat(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle error case
        }
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
        updateUI();
        getStayBookingTime();


        binding.buttonPlus.setOnClickListener(v ->{
            if (count < 9) {
                count++;
                updateUI();
            }
        } );
        binding.buttonMinus.setOnClickListener(v ->{
            if (count > 1) {
                count--;
                updateUI();
            }
        });
        // Handle Radio Button Clicks
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                if (selectedRadioButton.getId() == R.id.rbCustomerCode) {
                    isCustomerCode=true;
                    binding.tvNickName.setText("");
                        binding.llCustomer.setVisibility(View.VISIBLE);
                        binding.llNickName.setVisibility(View.GONE);
                } else {
                    isCustomerCode=false;
                    binding.tvAccountName.setText("");

                    binding.llNickName.setVisibility(View.VISIBLE);
                    binding.llCustomer.setVisibility(View.GONE);
                }
            }
        });

    /*  binding.plusButton.setOnClickListener {
            if (count < 9) {
                count++
                updateUI()
            }
        }

     binding.buttonMinus.setOnClickListener {
            if (count > 1) {
                count--
                updateUI()
            }
        }*/


        binding.tvAccountName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDialog("");
            }
        });

        binding.tvNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickNameDialog("");
            }
        });

        binding.save.setOnClickListener(v ->{
            if (validate() && isPlacedOrderBtnEnabled) {
                isPlacedOrderBtnEnabled = false;
               /* binding.save.setEnabled(false);
                binding.save.setBackgroundColor(Color.parseColor("#808080"));
                binding.tvSave.setText("Please Wait...");*/
                sendData();

            }

        } );

        binding.llCheckInTime.setOnClickListener(v -> {
          //  showCheckInTimePicker(notAllowllTime);
       //     showCheckInTimePicker1();
            showCheckInTimePicker24Hours(true);
        } );
        binding.noStayNoPurchase.setOnClickListener(v ->finish() );
        binding.llCheckOutTime.setOnClickListener(v -> {
         //   showCheckOutTimePicker();
            showCheckOutTimePicker24Hours();
        } );

        isEditMode = getIntent().getBooleanExtra(EXTRA_IS_EDIT, false);
        getStation();
        if (isEditMode) {
            bookingData = getIntent().getParcelableExtra("data");
            binding.tvSave.setText(R.string.update);

            String userType = getIntent().getStringExtra(MyConstant.USERTYPE);
            if(userType.equals("Other")){
                binding.llEmployee.setVisibility(View.VISIBLE);
                binding.llBranch.setVisibility(View.GONE);
            }else {
                binding.llEmployee.setVisibility(View.GONE);
                binding.llBranch.setVisibility(View.VISIBLE);
            }
            if (bookingData != null) {
                populateData(bookingData); // Load existing data into UI fields
            }
        }
        else {

            String userType = getIntent().getStringExtra(MyConstant.USERTYPE);
            if(userType.equals("Other")){
                binding.llEmployee.setVisibility(View.VISIBLE);
                binding.llBranch.setVisibility(View.GONE);
            }else {
                binding.llEmployee.setVisibility(View.GONE);
                binding.llBranch.setVisibility(View.VISIBLE);
            }
            initializeCheckInDateTime(existingCheckInDate);
            binding.tvSave.setText(R.string.save);
        }

       // binding.plusButton.setOnClickListener(v ->   startActivity(new Intent(this, BookingRequestActivity.class)));

    }

    private void updateUI() {
      binding.textViewNumber.setText(Integer.toString(count));
    }

  String  existingCheckInDate="";
  String  existingCheckOutTime="";
    private void initializeCheckInDateTime(String existingCheckInDate) {


        Calendar calendar = Calendar.getInstance();


        if (existingCheckInDate != null && !existingCheckInDate.isEmpty()) {
            // Parse the existing check-in date (YYYY-MM-DD format)
            String[] parts = existingCheckInDate.split("-");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1; // Convert to 0-based month
            int year = Integer.parseInt(parts[2]);

            calendar.set(year, month, day);
            if (existingCheckOutTime != null && !existingCheckOutTime.isEmpty()) {
                // Parse the existing check-out time (HH:mm format)
                try {
                    // Convert 12-hour format to 24-hour format
                    SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                    SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
                //    String[] timeParts = existingCheckOutTime.split(":");
                    Date date = inputFormat.parse(existingCheckOutTime); // Parse input time
                    String time24HourFormat = outputFormat.format(date); // Convert to 24-hour format

                    // Extract hour, minute, and second

                    // Extract hour, minute, second
                    String[] timeParts = time24HourFormat.split(":");  // 24-hour format hour
                    int checkOutHour = Integer.parseInt(timeParts[0]);  // 24-hour format hour
                    int checkOutMinute = Integer.parseInt(timeParts[1]); // Minute



                    checkInTimeCalendar.set(Calendar.HOUR_OF_DAY, checkOutHour);
                    checkInTimeCalendar.set(Calendar.MINUTE, checkOutMinute);
                    // Print values
                    System.out.println("Hour: " + checkOutHour);
                    System.out.println("Minute: " + checkOutMinute);

                    String checkInTimeText = formatTime1(checkOutHour, checkOutMinute);


                    // Set the values in the UI
                    binding.tvCheckInTime.setText(checkInTimeText);
                    binding.tvCheckOutTime.setText("");

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        } else {
            // Default behavior: set the check-in date to today + minHoursSelect hours
            calendar.add(Calendar.HOUR_OF_DAY, minHoursSelect);
        }
        // Get current date and time
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // Calculate check-in time (current time + 12 hours)
        calendar.add(Calendar.HOUR_OF_DAY, minHoursSelect);
        int checkInHour = calendar.get(Calendar.HOUR_OF_DAY);
        int checkInMinute = calendar.get(Calendar.MINUTE);

        // If check-in time crosses midnight, adjust check-in date to tomorrow
        if (calendar.get(Calendar.DAY_OF_MONTH) != currentDay) {
            currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            currentMonth = calendar.get(Calendar.MONTH);
            currentYear = calendar.get(Calendar.YEAR);
        }

        // Format check-in date (YYYY-MM-DD)
        String formattedMonth = String.format("%02d", currentMonth + 1); // Convert 0-based month to 1-based
        String formattedDay = String.format("%02d", currentDay);
        String checkInDateText = currentYear + "-" + formattedMonth + "-" + formattedDay;
        selectedDateTextCheckIn=checkInDateText;
        // Format check-in time (HH:mm AM/PM)

        if (existingCheckOutTime != null && !existingCheckOutTime.isEmpty()){

        }else {
            String checkInTimeText = formatTime1(checkInHour, checkInMinute);
            binding.tvCheckInTime.setText(checkInTimeText);
            binding.tvCheckOutTime.setText("");
            checkInTimeCalendar.set(Calendar.HOUR_OF_DAY, checkInHour);
            checkInTimeCalendar.set(Calendar.MINUTE, checkInMinute);
        }
        // Set the values in the UI
        binding.tvCheckInDate.setText(convertDateFormat(checkInDateText));

        // Save to calendar object
        checkInCalendar.set(currentYear, currentMonth, currentDay);

        // Call checkout initializer to ensure it is later than check-in
        initializeCheckOutDateTime(existingCheckOutDate);
    }

    String existingCheckOutDate="";
    String existingCheckOutDateNew="";
    private void initializeCheckOutDateTime(String existingCheckOutDate) {
        Calendar checkOutCalendar = (Calendar) checkInCalendar.clone();


        if (existingCheckOutDate != null && !existingCheckOutDate.isEmpty()) {
            // Parse the existing check-out date (YYYY-MM-DD format)
            String[] parts = existingCheckOutDate.split("-");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1; // Convert to 0-based month
            int year = Integer.parseInt(parts[2]);
            checkOutCalendar.set(year, month, day);
            if (existingCheckOutDateNew != null && !existingCheckOutDateNew.isEmpty()) {
                // Parse the existing check-out time (HH:mm format)
                try {
                    // Convert 12-hour format to 24-hour format
                    SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                    SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
                    //    String[] timeParts = existingCheckOutTime.split(":");
                    Date date = inputFormat.parse(existingCheckOutDateNew); // Parse input time
                    String time24HourFormat = outputFormat.format(date); // Convert to 24-hour format

                    // Extract hour, minute, and second

                    // Extract hour, minute, second
                    String[] timeParts = time24HourFormat.split(":");  // 24-hour format hour
                    int checkOutHour = Integer.parseInt(timeParts[0]);  // 24-hour format hour
                    int checkOutMinute = Integer.parseInt(timeParts[1]); // Minute



                    checkOutTimeCalendar.set(Calendar.HOUR_OF_DAY, checkOutHour);
                    checkOutTimeCalendar.set(Calendar.MINUTE, checkOutMinute);
                    // Print values
                    System.out.println("Hour: " + checkOutHour);
                    System.out.println("Minute: " + checkOutMinute);

                    String checkInTimeText = formatTime1(checkOutHour, checkOutMinute);


                    // Set the values in the UI
                    binding.tvCheckOutTime.setText(checkInTimeText);
                //    binding.tvCheckOutTime.setText("");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        } else {
            // Default behavior: Set checkout to at least 4 hours after check-in
            checkOutCalendar = (Calendar) checkInCalendar.clone();
            checkOutCalendar.add(Calendar.HOUR_OF_DAY, minHoursSelect);
        }
        /*// Ensure checkout is at least 4 hours after check-in
        checkOutCalendar.add(Calendar.HOUR_OF_DAY, 4);*/

        int checkOutYear = checkOutCalendar.get(Calendar.YEAR);
        int checkOutMonth = checkOutCalendar.get(Calendar.MONTH);
        int checkOutDay = checkOutCalendar.get(Calendar.DAY_OF_MONTH);
        int checkOutHour = checkOutCalendar.get(Calendar.HOUR_OF_DAY);
        int checkOutMinute = checkOutCalendar.get(Calendar.MINUTE);


        // Format checkout date
        String formattedMonth = String.format("%02d", checkOutMonth + 1);
        String formattedDay = String.format("%02d", checkOutDay);
        String checkOutDateText = checkOutYear + "-" + formattedMonth + "-" + formattedDay;


        if (existingCheckOutTime != null && !existingCheckOutTime.isEmpty()){

        }else {
            selectedDateTextCheckOut=checkOutDateText;
            // Format checkout time
            String checkOutTimeText = formatTime(checkOutHour, checkOutMinute);

            // Set values in UI
            binding.tvCheckOutDate.setText(convertDateFormat(checkOutDateText));
            binding.tvCheckOutTime.setText(checkOutTimeText);
        }

    }

    /**
     * Formats time in HH:mm AM/PM format
     */
    private String formatTime1(int hourOfDay, int minute) {
        return String.format("%02d:%02d %s",
                (hourOfDay % 12 == 0) ? 12 : hourOfDay % 12, // Convert 24-hour to 12-hour format
                minute,
                (hourOfDay < 12) ? "AM" : "PM");
    }



    public void handleClear(View view) {
        if (view.getId() == R.id.clearAccountName) {
            binding.tvAccountName.setText("");
        }
        if(view.getId() == R.id.imgClearNickName){
            binding.tvNickName.setText("");
        }
    }


    private boolean validate() {
        boolean temp = true;


        // Check if branch is selected
        String userType = getIntent().getStringExtra(MyConstant.USERTYPE);
        if(userType.equals("Other")){
            if(isCustomerCode){
                if (binding.tvAccountName.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please select Customer Code!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else {
                if (binding.tvNickName.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please select NickName!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

        }else {
            if (selectBranch.isEmpty()) {
                Toast.makeText(this, "Please select a branch!", Toast.LENGTH_SHORT).show();
                return false;
            }
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
      /*  if (binding.editNoOfPerson.getText().toString().isEmpty()) {
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
        }*/
           return temp;

    }
    private void setSpinner() {
        // List of items

        List<String> items = new ArrayList<>();
        items.add("Select Branch"); // Add a default item

        // HashMap to store branch availability
        HashMap<String, String> branchAvailabilityMap = new HashMap<>();

        for (BookingData branch : branchList) {
            String branchName = branch.getDbPrefix();
            String isStayAvailable = branch.getStayFacility(); // Assume this method exists

            items.add(branchName);
            branchAvailabilityMap.put(branchName, isStayAvailable); // Store branch availability
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
                selectBranch = branchList.get(position - 1).getId(); // Adjust for "Select Branch"
            }
        }





        // Handle item selection
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) { // Ignore "Select Branch"
                    String selectedBranch = items.get(position);
                    selectBranch = branchList.get(position - 1).getId(); // Adjust for default item

                    // Get availability from HashMap
                    String isStayAvailable = branchAvailabilityMap.get(selectedBranch);

                    // Log or show a message based on availability
                    if (isStayAvailable.equals("1")) {
                    //    Toast.makeText(BookingRequestActivity.this, selectedBranch + " has Stay Facility", Toast.LENGTH_SHORT).show();
                    } else {
                        binding.spinner.setSelection(0);
                        selectBranch = ""; // Adjust for default item
                        customToast("Stay facility is not available at this branch");
                     //   showSuccessSnackbar(binding,"");
                       // Toast.makeText(BookingRequestActivity.this,  " Stay facility is not available at this branch", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    public void customToast(String msg){
        // Example: Show success Snackbar when the activity starts
        SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), msg);

    }

    private void showCheckInDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Declare datePickerDialog first
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, null, year, month, day);

        datePickerDialog.setOnDateSetListener((view, selectedYear, selectedMonth, selectedDay) -> {
            checkInCalendar.set(selectedYear, selectedMonth, selectedDay);

            String formattedMonth = String.format("%02d", selectedMonth + 1); // Convert 0-based month to 1-based
            String formattedDay = String.format("%02d", selectedDay);
            selectedDateTextCheckIn = selectedYear + "-" + formattedMonth + "-" + formattedDay;
            String selectCheckInDate = formattedDay + "-" + formattedMonth + "-" + selectedYear;

            binding.tvCheckInDate.setText(selectCheckInDate);
            binding.tvCheckInDate.setError(null, null);
            binding.tvCheckInTime.setText(""); // Reset Check-In Time
            binding.tvCheckOutTime.setText(""); // Reset Check-Out Time
            binding.tvCheckOutDate.setText(""); // Reset Check-Out Time

            if (isToday(selectedYear, selectedMonth, selectedDay)) {
                notAllowllTime = true;
                showCheckInTimePicker24Hours(true);
            } else if (isTomorrow(selectedYear, selectedMonth, selectedDay)) {
                showCheckInTimePicker24Hours(true);
            } else {
                notAllowllTime = false;
                showCheckInTimePicker24Hours(false);
            }

            // Dismiss dialog after selection
            datePickerDialog.dismiss();
        });

        // Disable past dates
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Auto-close when a date is selected
        datePickerDialog.setOnShowListener(dialog -> {
            DatePicker datePicker = datePickerDialog.getDatePicker();
            datePicker.init(year, month, day, (view, selectedYear, selectedMonth, selectedDay) -> {
                datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick();
            });
        });

        datePickerDialog.show();
    }

    // Store the previously selected Check-In date
    private String previousSelectedCheckInDate = null;
    private int selectedCheckInHour = -1, selectedCheckInMinute = -1; // Store selected time

    private void showCheckInTimePicker24Hours(boolean enforce12HourRestriction) {
        // ✅ Ensure Check-In Date is selected
        String selectedDateTextCheckIn = binding.tvCheckInDate.getText().toString().trim();
       String date= convertDateFormatnew(selectedDateTextCheckIn);
        if (selectedDateTextCheckIn.isEmpty()) {
            Toast.makeText(this, "Please select Check-In date first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Parse Check-In Date from tvCheckInDate
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar selectedCheckInCalendar = Calendar.getInstance();

        try {
            Date selectedDate = dateFormat.parse(date);
            if (selectedDate == null) {
                throw new ParseException("Invalid date format", 0);
            }
            selectedCheckInCalendar.setTime(selectedDate);
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid Check-In Date format!", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Get current date and time
        Calendar currentCalendar = Calendar.getInstance();
        int currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentCalendar.get(Calendar.MINUTE);

        // ✅ Get Check-In Date components
        int selectedYear = selectedCheckInCalendar.get(Calendar.YEAR);
        int selectedMonth = selectedCheckInCalendar.get(Calendar.MONTH);
        int selectedDay = selectedCheckInCalendar.get(Calendar.DAY_OF_MONTH);

        boolean isToday = isToday(selectedYear, selectedMonth, selectedDay);
        boolean isTomorrow = isTomorrow(selectedYear, selectedMonth, selectedDay);

        // ✅ Calculate minimum allowed Check-In Time
        Calendar minTimeCalendar = (Calendar) currentCalendar.clone();
        minTimeCalendar.add(Calendar.HOUR_OF_DAY, minHoursSelect);
        int minHourToday = minTimeCalendar.get(Calendar.HOUR_OF_DAY);
        int minMinuteToday = minTimeCalendar.get(Calendar.MINUTE);

        int minHour, minMinute;

        if (isToday) {
            minHour = currentHour + minHoursSelect;
            if (minHour >= 24) {
                binding.tvCheckInDate.setText("");
                SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Check-in is not allowed for the current date.");
                return;
            }
            minMinute = currentMinute;
        } else if (isTomorrow && currentHour >= minHoursSelect) {
            minHour = minHourToday;
            minMinute = minMinuteToday;
        } else {
            minHour = 0;
            minMinute = 0;
        }

        int initialHour, initialMinute;

        // ✅ Reset time if the user selects a new date
        if (previousSelectedCheckInDate == null || !previousSelectedCheckInDate.equals(selectedDateTextCheckIn)) {
            selectedCheckInHour = -1;
            selectedCheckInMinute = -1;
        }

        // ✅ Retain last selected time if available; otherwise, set the minimum allowed time
        if (selectedCheckInHour != -1 && selectedCheckInMinute != -1) {
            initialHour = selectedCheckInHour;
            initialMinute = selectedCheckInMinute;
        } else {
            initialHour = minHour;
            initialMinute = minMinute;
        }

        // ✅ Store the selected Check-In date for future comparisons
        previousSelectedCheckInDate = selectedDateTextCheckIn;

        // ✅ Show TimePickerDialog with the 24-hour format
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    // ✅ Validate the selected time based on restrictions
                    if (isToday && (hourOfDay < minHour || (hourOfDay == minHour && minute < minMinute))) {
                        binding.tvCheckInTime.setText("");
                    /*    SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content),
                                "Check-in time must be at least " + minHoursSelect + " hours from the current time."); */
                        SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content),
                                "Check-in time must be greater than current time.");
                    } /*else if (isTomorrow && currentHour >= minHoursSelect &&
                            (hourOfDay < minHour || (hourOfDay == minHour && minute < minMinute))) {
                        SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content),
                                "Check-in time must be at least " + minHoursSelect + " hours from the current time.");
                        binding.tvCheckInTime.setText("");
                    }*/ else {
                        // ✅ Save the selected Check-In time
                        selectedCheckInHour = hourOfDay;
                        selectedCheckInMinute = minute;

                        checkInTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        checkInTimeCalendar.set(Calendar.MINUTE, minute);

                        binding.tvCheckInTime.setError(null);
                        binding.tvCheckInTime.setText(formatTime(hourOfDay, minute));

                        // ✅ Reset Check-Out Date & Time when Check-In changes
                        binding.tvCheckOutTime.setText("");
                      //  binding.tvCheckOutDate.setText("");
                    }
                },
                initialHour,
                initialMinute,
                true  // ✅ Use 24-hour format
        );

        timePickerDialog.show();
    }

    public static String convertDateFormatnew(String dateStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            Date date = inputFormat.parse(dateStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid Date";
        }
    }

    private boolean isTomorrow(int year, int month, int day) {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1); // Move to next day
        return (year == tomorrow.get(Calendar.YEAR) &&
                month == tomorrow.get(Calendar.MONTH) &&
                day == tomorrow.get(Calendar.DAY_OF_MONTH));
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

        // Declare DatePickerDialog first
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, null, year, month, day);

        datePickerDialog.setOnDateSetListener((view, selectedYear, selectedMonth, selectedDay) -> {
            checkOutCalendar.set(selectedYear, selectedMonth, selectedDay);

            String formattedMonth = String.format("%02d", selectedMonth + 1); // Convert 0-based month to 1-based
            String formattedDay = String.format("%02d", selectedDay);
            selectedDateTextCheckOut = selectedYear + "-" + formattedMonth + "-" + formattedDay;
            String selectedCheckOutDate = formattedDay + "-" + formattedMonth + "-" + selectedYear;

            binding.tvCheckOutDate.setError(null, null);
            binding.tvCheckOutDate.setText(selectedCheckOutDate);
            binding.tvCheckOutTime.setText(""); // Reset Check-Out Time

            // Automatically show Check-Out Time Picker after selecting a date
            showCheckOutTimePicker24Hours();

            // Dismiss dialog after selection
            datePickerDialog.dismiss();
        });

        // Set minimum selectable date to Check-In date
        datePickerDialog.getDatePicker().setMinDate(checkInCalendar.getTimeInMillis());

        // Auto-close when a date is selected
        datePickerDialog.setOnShowListener(dialog -> {
            DatePicker datePicker = datePickerDialog.getDatePicker();
            datePicker.init(year, month, day, (view, selectedYear, selectedMonth, selectedDay) -> {
                datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick();
            });
        });

        datePickerDialog.show();
    }

    private int selectedCheckOutHour = -1, selectedCheckOutMinute = -1; // Store selected time

    private String previousSelectedCheckOutDate = null;

    private void showCheckOutTimePicker24Hours() {
        if (binding.tvCheckOutDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select Check-Out date first!", Toast.LENGTH_SHORT).show();
            return;
        } else if (binding.tvCheckInTime.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select Check-In time first!", Toast.LENGTH_SHORT).show();
            return;
        }


        int checkInHour = checkInTimeCalendar.get(Calendar.HOUR_OF_DAY);
        int checkInMinute = checkInTimeCalendar.get(Calendar.MINUTE);


        int minHour, minMinute;
        int initialHour, initialMinute;

        // Allow any time if check-out date is different from check-in date
        if (checkOutCalendar.get(Calendar.YEAR) != checkInCalendar.get(Calendar.YEAR) ||
                checkOutCalendar.get(Calendar.MONTH) != checkInCalendar.get(Calendar.MONTH) ||
                checkOutCalendar.get(Calendar.DAY_OF_MONTH) != checkInCalendar.get(Calendar.DAY_OF_MONTH)) {

            minHour = 0;
            minMinute = 0;

            // ✅ If the user has changed the date, reset time to 00:00
            if (previousSelectedCheckOutDate == null ||
                    !previousSelectedCheckOutDate.equals(selectedDateTextCheckOut)) {
                initialHour = 0;
                initialMinute = 0;
                selectedCheckOutHour = -1; // Reset stored values
                selectedCheckOutMinute = -1;
            } else {
                // ✅ Otherwise, retain the last selected time
                initialHour = (selectedCheckOutHour != -1) ? selectedCheckOutHour : minHour;
                initialMinute = (selectedCheckOutMinute != -1) ? selectedCheckOutMinute : minMinute;
            }

        } else {
            // ✅ Check-Out date is the same as Check-In date
            minHour = checkInHour;
            minMinute = checkInMinute;

            initialHour = (selectedCheckOutHour != -1) ? selectedCheckOutHour : minHour;
            initialMinute = (selectedCheckOutMinute != -1) ? selectedCheckOutMinute : minMinute;
        }

        // Store the last selected date for future comparison
        previousSelectedCheckOutDate = selectedDateTextCheckOut;

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    // ✅ If Check-Out date is same as Check-In date, ensure the time is later
                    if (checkOutCalendar.get(Calendar.YEAR) == checkInCalendar.get(Calendar.YEAR) &&
                            checkOutCalendar.get(Calendar.MONTH) == checkInCalendar.get(Calendar.MONTH) &&
                            checkOutCalendar.get(Calendar.DAY_OF_MONTH) == checkInCalendar.get(Calendar.DAY_OF_MONTH)) {

                        if (hourOfDay < checkInHour || (hourOfDay == checkInHour && minute < checkInMinute)) {
                            binding.tvCheckOutTime.setText("");
                            SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Check-Out time must be later than Check-In time.");
                            return;
                        }

                        if (hourOfDay == checkInHour && minute == checkInMinute) {
                            binding.tvCheckOutTime.setText("");
                            SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Check-In and Check-Out time cannot be the same");
                            return;
                        }
                    }

                    // ✅ Save the selected time
                    selectedCheckOutHour = hourOfDay;
                    selectedCheckOutMinute = minute;

                    binding.tvCheckOutTime.setError(null, null);
                    checkOutTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    checkOutTimeCalendar.set(Calendar.MINUTE, minute);
                    binding.tvCheckOutTime.setText(formatTime(hourOfDay, minute));
                },
                initialHour,  // ✅ Show last selected time or reset to 00:00 if date changed
                initialMinute, // ✅ Show last selected time or reset to 00:00 if date changed
                true  // ✅ Use 24-hour format
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
                   String screen = getIntent().getStringExtra(MyConstant.SCREEN);
                   if(screen.equals(MyConstant.HOME)){
                       startActivity(new Intent(this, BookingListActivity.class)
                               .putExtra(MyConstant.USERTYPE,SharedPref.read(SharedPref.DASHBOARD_TYPE, ""))
                       );
                       finish();
                   }else {
                       finish();
                   }

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
                    new AlertDialog.Builder(this).setMessage(jsonObject.getString("ResponseMessage") + "")
                            .setNegativeButton("Cancel", (dialog, which) -> {
                                isPlacedOrderBtnEnabled = true; // Enable the button on cancel
                                dialog.cancel();
                            })
                            .create().show();
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
            new AlertDialog.Builder(this)
                    .setMessage("Try again.. Something went wrong")
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            // Dismiss the progress dialog and retry sending data
                            progressDialog.dismiss();
                            sendData();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Dismiss the progress dialog and perform cancel action
                            progressDialog.dismiss();
                            dialog.dismiss();  // Close the dialog
                            isPlacedOrderBtnEnabled = true;
                            binding.save.setEnabled(false);

                            binding.save.setBackgroundColor(Color.parseColor("#2bab1c"));
                            binding.tvSave.setText("Save");
                        }
                    })
                    .create()
                    .show();

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

                    if (isEditMode) {
                        jsonObject.put("id",bookingData.getId() );
                        String userType = getIntent().getStringExtra(MyConstant.USERTYPE);
                        if(userType.equals("Other")){
                            jsonObject.put("accountID", accountNameId);
                            jsonObject.put("branchID", JSONObject.NULL);

                        }else {
                            jsonObject.put("accountID", JSONObject.NULL);
                            jsonObject.put("branchID", selectBranch);
                        }
                    }else {
                        jsonObject.put("id", JSONObject.NULL);
                        String userType = getIntent().getStringExtra(MyConstant.USERTYPE);
                        if(userType.equals("Other")){
                            jsonObject.put("accountID", accountNameId);
                            jsonObject.put("branchID", JSONObject.NULL);
                        }else {
                            jsonObject.put("accountID", JSONObject.NULL);
                            jsonObject.put("branchID", selectBranch);
                        }
                    }




                    jsonObject.put("companyID", JSONObject.NULL);

                    jsonObject.put("partyCode",SharedPref.read(SharedPref.PARTY_CODE, ""));

                    jsonObject.put("checkInDate", selectedDateTextCheckIn.toString());
                    jsonObject.put("checkInTime", binding.tvCheckInTime.getText().toString());
                    jsonObject.put("checkoutDate", selectedDateTextCheckOut);
                    jsonObject.put("partyCode", Uri.encode(SharedPref.read(SharedPref.PARTY_CODE, "")));
                    jsonObject.put("checkoutTime", binding.tvCheckOutTime.getText().toString());
                    // jsonObject.put("noOfPerson", binding.editNoOfPerson.getText().toString());
                    jsonObject.put("noOfPerson", binding.textViewNumber.getText().toString());
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

    private void getStayBookingTime() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, StayBookingTime, response -> {
            Log.i("TaG", "Response " + StayBookingTime + "---> " + response);

            try {
                // Convert String to JSONObject
                JSONObject jsonObject = new JSONObject(response);

                // Extract BookingTime
                int bookingTime = jsonObject.optInt("BookingTime");

                minHoursSelect=bookingTime;
              //  initializeCheckInDateTime();
                // Print BookingTime
                System.out.println("Booking Time: " + bookingTime);
            } catch (Exception e) {
                e.printStackTrace();
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

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


   // ArrayList<SalepartyModel> salepartyModelList, saleData;
    public static ArrayList<SalepartyModel> sData = new ArrayList<>();
    private ArrayList<Account> accountNameList= new ArrayList<>();;
    private ArrayList<Account> saleData= new ArrayList<>();

    private ArrayList<NickNameList> nickNameList= new ArrayList<>();;
    private ArrayList<NickNameList> filternicNameList= new ArrayList<>();;

    private String nickNameId="";
    private String accountNameId="";

    EditText search;
    RecyclerView recyclerView;
    private Dialog sDialog;
    private void searchDialog(final String title) {

        sDialog = new Dialog(this);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
        search = sDialog.findViewById(R.id.search);
        if (accountNameList.size() > 0) {
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        }
        sDialog.findViewById(R.id.cancle).setOnClickListener(v -> sDialog.dismiss());

        if (!accountNameList.isEmpty()) {
            filterBc(accountNameList);
        } else {
            getAccountNameList(GetAccountNameList);
        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saleData.clear();
                for (int p = 0; p < accountNameList.size(); p++) {
                    if (accountNameList.get(p).getName().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || accountNameList.get(p).getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        saleData.add(accountNameList.get(p));
                    }
                }
                filterBc(saleData);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        accountListAdapter = new AccountListAdapter(this, accountNameList, account -> {
            sDialog.dismiss();
           binding.tvAccountName.setText(account.getName());
            accountNameId=account.getId();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(accountListAdapter);
        getAccountNameList(GetAccountNameList);
        sDialog.show();

    }

    void filterBc(ArrayList<Account> bc) {
        accountListAdapter = new AccountListAdapter(this, bc, account -> {
            sDialog.dismiss();
            binding.tvAccountName.setText(account.getName());
            accountNameId=account.getId();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(accountListAdapter);
    }

    private void getAccountNameList(final String url) {
        final MyProgress myProgress = new MyProgress(this);
        myProgress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            Util.getInstance().logLargeString("TaG", "Response " + url + " -=-=-=>" + response);
            myProgress.dismiss();
            try {
                accountNameList.clear();
                Gson gson = new Gson();
                ApiResponse newresponse = gson.fromJson(response, ApiResponse.class);

             /*   JSONObject jsonObject = new JSONObject(newresponse);
                JSONArray jsonArray = jsonObject.getJSONArray("salesPartyNames");
                salepartyModelList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    String name = ob.optString("NickName");
                    String accountId = ob.optString("AccountCode");
                    salepartyModel = new SalepartyModel(name, false, "", accountId);
                    salepartyModelList.add(salepartyModel);

                }*/
                accountNameList.addAll(newresponse.getAccountNameList());
                accountListAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Exce", e.toString());
            }
        }, error -> {
            myProgress.dismiss();
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
//                String str = "{\"MOBILENO\":\"" + SharedPref.read(SharedPref.USERMOBILE, "") + "\",\"Filter\":\"" + "selected" + "\"}";
                String str = "{}";
                Log.i("TaG", "Request " + url + " -=-=-=>" + str);

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
        stringRequest.setShouldCache(true);
        stringRequest.shouldCache();
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void getNickNameList(final String url) {
        final MyProgress myProgress = new MyProgress(this);
        myProgress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            Util.getInstance().logLargeString("TaG", "Response " + url + " -=-=-=>" + response);
            myProgress.dismiss();
            try {
                nickNameList.clear();
                Gson gson = new Gson();
                ApiResponse newresponse = gson.fromJson(response, ApiResponse.class);

             /*   JSONObject jsonObject = new JSONObject(newresponse);
                JSONArray jsonArray = jsonObject.getJSONArray("salesPartyNames");
                salepartyModelList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    String name = ob.optString("NickName");
                    String accountId = ob.optString("AccountCode");
                    salepartyModel = new SalepartyModel(name, false, "", accountId);
                    salepartyModelList.add(salepartyModel);

                }*/
                nickNameList.addAll(newresponse.getNickNameList());
                nickNameListAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Exce", e.toString());
            }
        }, error -> {
            myProgress.dismiss();
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
//                String str = "{\"MOBILENO\":\"" + SharedPref.read(SharedPref.USERMOBILE, "") + "\",\"Filter\":\"" + "selected" + "\"}";
                String str = "{}";
                Log.i("TaG", "Request " + url + " -=-=-=>" + str);

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
        stringRequest.setShouldCache(true);
        stringRequest.shouldCache();
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void nickNameDialog(final String title) {

        sDialog = new Dialog(this);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
        search = sDialog.findViewById(R.id.search);
        if (nickNameList.size() > 0) {
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        }
        sDialog.findViewById(R.id.cancle).setOnClickListener(v -> sDialog.dismiss());

        if (!nickNameList.isEmpty()) {
            filternicNameList(nickNameList);
        } else {
            getNickNameList(GetNickNameList);
        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filternicNameList.clear();
                for (int p = 0; p < nickNameList.size(); p++) {
                    if (nickNameList.get(p).getName().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || nickNameList.get(p).getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filternicNameList.add(nickNameList.get(p));
                    }
                }
                filternicNameList(filternicNameList);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        nickNameListAdapter = new NickNameListAdapter(this, nickNameList, account -> {
            sDialog.dismiss();
            binding.tvNickName.setText(account.getName());
            accountNameId=account.getId();

        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(nickNameListAdapter);
        getNickNameList(GetNickNameList);
        sDialog.show();

    }

    void filternicNameList(ArrayList<NickNameList> bc) {
        nickNameListAdapter = new NickNameListAdapter(this, bc, account -> {
            sDialog.dismiss();
            binding.tvNickName.setText(account.getName());
            accountNameId=account.getId();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(nickNameListAdapter);
    }

    private void parseTime(String time12HourFormat) {
        try {
            // Convert 12-hour format to 24-hour format
            SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a", Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);

            Date date = inputFormat.parse(time12HourFormat); // Parse input time
            String time24HourFormat = outputFormat.format(date); // Convert to 24-hour format

            // Extract hour, minute, and second
            String[] timeParts = time24HourFormat.split(":");
            int checkOutHour = Integer.parseInt(timeParts[0]);  // 24-hour format hour
            int checkOutMinute = Integer.parseInt(timeParts[1]); // Minute
            int checkOutSecond = Integer.parseInt(timeParts[2]); // Second


            // Print values
            System.out.println("Hour: " + checkOutHour);
            System.out.println("Minute: " + checkOutMinute);
            System.out.println("Second: " + checkOutSecond);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
