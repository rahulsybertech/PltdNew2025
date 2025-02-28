package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.BRANCH_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_ORDER;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_UPDATEBOOKING;
import static com.syber.ssspltd.Constants.NewErpUrls.STATION_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.StayBookingTime;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.syber.ssspltd.Utils.MyConstant;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.SnackbarUtils;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    com.syber.ssspltd.model.booking.BookingData bookingData;
    private String selectedDateTextCheckIn="";
    private String selectedDateTextCheckOut="";

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
        binding.tvCheckOutDate.setText(checkOut);
        binding.tvCheckInTime.setText(bookingData.getCheckInTime());
        selectedDateTextCheckIn=bookingData.getCheckInDate();
        selectedDateTextCheckOut=bookingData.getCheckoutDate();
        binding.tvCheckOutTime.setText(bookingData.getCheckoutTime());
        binding.textViewNumber.setText(bookingData.getNoOfPerson());
        int number = Integer.valueOf(bookingData.getNoOfPerson());
        count=number;
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
        binding.save.setOnClickListener(v ->{

            if (validate() && isPlacedOrderBtnEnabled) {
                isPlacedOrderBtnEnabled = false;
            /*    binding.save.setEnabled(false);
                binding.save.setBackgroundColor(Color.parseColor("#808080"));
                binding.tvSave.setText("Please Wait...")*/;
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
            if (bookingData != null) {
                populateData(bookingData); // Load existing data into UI fields
            }
        }else {
            binding.tvSave.setText(R.string.save);
        }

       // binding.plusButton.setOnClickListener(v ->   startActivity(new Intent(this, BookingRequestActivity.class)));

    }

    private void updateUI() {
      binding.textViewNumber.setText(Integer.toString(count));
    }

    private void initializeCheckInDateTime() {
        Calendar calendar = Calendar.getInstance();

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
        String checkInTimeText = formatTime1(checkInHour, checkInMinute);


        // Set the values in the UI
        binding.tvCheckInDate.setText(convertDateFormat(checkInDateText));
        binding.tvCheckInTime.setText(checkInTimeText);

        // Save to calendar object
        checkInCalendar.set(currentYear, currentMonth, currentDay);
        checkInTimeCalendar.set(Calendar.HOUR_OF_DAY, checkInHour);
        checkInTimeCalendar.set(Calendar.MINUTE, checkInMinute);
        // Call checkout initializer to ensure it is later than check-in
        initializeCheckOutDateTime();
    }
    private void initializeCheckOutDateTime() {
        Calendar checkOutCalendar = (Calendar) checkInCalendar.clone();

        // Ensure checkout is at least 4 hours after check-in
        checkOutCalendar.add(Calendar.HOUR_OF_DAY, 4);

        int checkOutYear = checkOutCalendar.get(Calendar.YEAR);
        int checkOutMonth = checkOutCalendar.get(Calendar.MONTH);
        int checkOutDay = checkOutCalendar.get(Calendar.DAY_OF_MONTH);
        int checkOutHour = checkOutCalendar.get(Calendar.HOUR_OF_DAY);
        int checkOutMinute = checkOutCalendar.get(Calendar.MINUTE);


        // Format checkout date
        String formattedMonth = String.format("%02d", checkOutMonth + 1);
        String formattedDay = String.format("%02d", checkOutDay);
        String checkOutDateText = checkOutYear + "-" + formattedMonth + "-" + formattedDay;
        selectedDateTextCheckOut=checkOutDateText;
        // Format checkout time
        String checkOutTimeText = formatTime(checkOutHour, checkOutMinute);

        // Set values in UI
        binding.tvCheckOutDate.setText(convertDateFormat(checkOutDateText));
        binding.tvCheckOutTime.setText(checkOutTimeText);
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
        if (view.getId() == R.id.clear_marketer) {
            binding.editNoOfPerson.setText("");
            binding.textViewNumber.setText("");
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
        if (binding.tvCheckInDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select Check-In date first!", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar currentCalendar = Calendar.getInstance();
        int currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentCalendar.get(Calendar.MINUTE);

        int selectedYear = checkInCalendar.get(Calendar.YEAR);
        int selectedMonth = checkInCalendar.get(Calendar.MONTH);
        int selectedDay = checkInCalendar.get(Calendar.DAY_OF_MONTH);

        boolean isToday = isToday(selectedYear, selectedMonth, selectedDay);
        boolean isTomorrow = isTomorrow(selectedYear, selectedMonth, selectedDay);

        // Calculate minTime for today
        Calendar minTimeCalendar = (Calendar) currentCalendar.clone();
        minTimeCalendar.add(Calendar.HOUR_OF_DAY, minHoursSelect);
        int minHourToday = minTimeCalendar.get(Calendar.HOUR_OF_DAY);
        int minMinuteToday = minTimeCalendar.get(Calendar.MINUTE);

        int minHour, minMinute;

        if (isToday) {
            minHour = currentHour + minHoursSelect;
            if (minHour >= 24) {
                SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Time unavailable, check-in too late");
                return;
            } else {
                minMinute = currentMinute;
            }
        } else if (isTomorrow && currentHour >= minHoursSelect) {
            minHour = minHourToday;
            minMinute = minMinuteToday;
        } else {
            minHour = 0;
            minMinute = 0;
        }

        int initialHour, initialMinute;

        // ✅ Get selected Check-In date as a string
        String selectedDateTextCheckIn = binding.tvCheckInDate.getText().toString();

        // ✅ If the user selects a new date, reset time to 00:00
        if (previousSelectedCheckInDate == null ||
                !previousSelectedCheckInDate.equals(selectedDateTextCheckIn)) {
            selectedCheckInHour = -1; // Reset stored values
            selectedCheckInMinute = -1;
        }

        // ✅ Retain last selected time if available, else use minHour/minMinute
        if (selectedCheckInHour != -1 && selectedCheckInMinute != -1) {
            initialHour = selectedCheckInHour;
            initialMinute = selectedCheckInMinute;
        } else {
            initialHour = minHour;
            initialMinute = minMinute;
        }

        // ✅ Store the selected date for future comparison
        previousSelectedCheckInDate = selectedDateTextCheckIn;

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    // Validate selection based on restrictions
                    if (isToday && (hourOfDay < minHour || (hourOfDay == minHour && minute < minMinute))) {
                        SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Check-in time must be at least 12 hours from now");
                    } else if (isTomorrow && currentHour >= minHoursSelect && (hourOfDay < minHour || (hourOfDay == minHour && minute < minMinute))) {
                        SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Check-in time must be at least current time + 12 hours.");
                    } else {
                        // ✅ Save the selected time
                        selectedCheckInHour = hourOfDay;
                        selectedCheckInMinute = minute;

                        checkInTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        checkInTimeCalendar.set(Calendar.MINUTE, minute);
                        binding.tvCheckInTime.setError(null, null);
                        binding.tvCheckInTime.setText(formatTime(hourOfDay, minute));
                        binding.tvCheckOutTime.setText(""); // Reset Check-Out Time
                        binding.tvCheckOutDate.setText(""); // Reset Check-Out Date
                    }
                },
                initialHour,  // ✅ Show last selected time or reset to 00:00 if date changed
                initialMinute, // ✅ Show last selected time or reset to 00:00 if date changed
                true  // ✅ Use 24-hour format
        );

        timePickerDialog.show();
    }




    private void showCheckInTimePicker1() {
        if (binding.tvCheckInDate.getText().toString().isEmpty()) {
            SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Please select Check-In date first!");
            return;
        }

        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);
        int currentMonth = currentCalendar.get(Calendar.MONTH);
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        int currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentCalendar.get(Calendar.MINUTE);

        // Add 12 hours to current time
        Calendar minTimeCalendar = (Calendar) currentCalendar.clone();
        minTimeCalendar.add(Calendar.HOUR_OF_DAY, minHoursSelect);
        int minHourToday = minTimeCalendar.get(Calendar.HOUR_OF_DAY);
        int minMinuteToday = minTimeCalendar.get(Calendar.MINUTE);

        Calendar selectedDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            selectedDate.setTime(sdf.parse(binding.tvCheckInDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
            SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Invalid date format.");
            return;
        }

        int selectedYear = selectedDate.get(Calendar.YEAR);
        int selectedMonth = selectedDate.get(Calendar.MONTH);
        int selectedDay = selectedDate.get(Calendar.DAY_OF_MONTH);

        int minHour, minMinute;

        if (selectedYear == currentYear && selectedMonth == currentMonth) {
            if (selectedDay == currentDay + 1) { // If tomorrow (22nd Feb) is selected
                minHour = minHourToday;
                minMinute = minMinuteToday;
            } else if (selectedDay > currentDay + 1) { // If any future date is selected (23rd Feb or later)
                minHour = 0;
                minMinute = 0; // No restriction
            } else if (selectedDay == currentDay) { // If today is selected
                minHour = minHourToday;
                minMinute = minMinuteToday;

                // If the selected date is today, enforce a 12-hour restriction
                minHour = currentHour + minHoursSelect;

                if (minHour >= 24) {
                    SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Time unavailable, check-in too late.");
                    return;
                }
            } else {
                SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Invalid date selection.");
                return;
            }
        } else {
            minHour = 0;
            minMinute = 0;
        }

        // If the minimum hour exceeds 24, time selection is unavailable
        if (minHour >= 24) {
            SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Check-in time unavailable, too late.");
            return;
        }

        int finalMinHour = minHour;
        int finalMinMinute = minMinute;

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    // Create a Calendar instance for selected time
                    Calendar selectedTime = Calendar.getInstance();
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedTime.set(Calendar.MINUTE, minute);

                    // If tomorrow is selected (22nd Feb), enforce 12-hour rule
                    if (selectedDay == currentDay + 1) {
                        if (hourOfDay < minHourToday || (hourOfDay == minHourToday && minute < minMinuteToday)) {
                            // User selected a time less than current time + 12 hours
                            SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Check-in time must be at least 12 hours from now.");
                            return;
                        }
                    }

                    // Valid time selection
                    checkInTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    checkInTimeCalendar.set(Calendar.MINUTE, minute);
                    binding.tvCheckInTime.setError(null, null);
                    binding.tvCheckInTime.setText(formatTime(hourOfDay, minute));
                    binding.tvCheckOutTime.setText(""); // Reset Check-Out Time
                    binding.tvCheckOutDate.setText(""); // Reset Check-Out Date
                },
                finalMinHour,
                finalMinMinute,
                false // Use 24-hour format
        );
        timePickerDialog.show();
    }


    private boolean isTomorrow(int year, int month, int day) {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1); // Move to next day
        return (year == tomorrow.get(Calendar.YEAR) &&
                month == tomorrow.get(Calendar.MONTH) &&
                day == tomorrow.get(Calendar.DAY_OF_MONTH));
    }




    private void showCheckInTimePicker(boolean enforce12HourRestriction) {
        if (binding.tvCheckInDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select Check-In date first!", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar currentCalendar = Calendar.getInstance();
        int currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentCalendar.get(Calendar.MINUTE);

        int selectedYear = checkInCalendar.get(Calendar.YEAR);
        int selectedMonth = checkInCalendar.get(Calendar.MONTH);
        int selectedDay = checkInCalendar.get(Calendar.DAY_OF_MONTH);

        Calendar selectedDateCalendar = Calendar.getInstance();
        selectedDateCalendar.set(selectedYear, selectedMonth, selectedDay);

        boolean isToday = isToday(selectedYear, selectedMonth, selectedDay);
        boolean isTomorrow = isTomorrow(selectedYear, selectedMonth, selectedDay);
        // Add 12 hours to current time
        Calendar minTimeCalendar = (Calendar) currentCalendar.clone();
        minTimeCalendar.add(Calendar.HOUR_OF_DAY, minHoursSelect);
        int minHourToday = minTimeCalendar.get(Calendar.HOUR_OF_DAY);
        int minMinuteToday = minTimeCalendar.get(Calendar.MINUTE);

        int minHour, minMinute;

        if (isToday) {
            // Today: Enforce a minimum check-in time of "current time + 12 hours"
            minHour = currentHour + minHoursSelect;
            if (minHour >= 24) {
                SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Time unavailable, check-in too late");
            //    Toast.makeText(this, "Time unavailable, check-in too late.", Toast.LENGTH_SHORT).show();
                return;
            } else {
                minMinute = currentMinute;
            }
        } else if (isTomorrow && currentHour >= minHoursSelect) {
            // Tomorrow: Enforce restriction only if current time is AFTER 12 PM
            minHour = currentHour + minHoursSelect;
            if (minHour >= 24) {
                minHour = 0; // Reset for next day
            }
            minHour = minHourToday;
            minMinute = minMinuteToday;
        } else {
            // Future date or tomorrow (before 12 PM): No restrictions
            minHour = 0;
            minMinute = 0;
        }

        int finalMinHour = minHour;
        int finalMinMinute = minMinute;

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    // Ensure valid time selection based on restrictions
                    if (isToday && (hourOfDay < finalMinHour || (hourOfDay == finalMinHour && minute < finalMinMinute))) {
                        SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Check-in time must be at least 12 hours from now");
                     //   Toast.makeText(this, "Check-in time must be at least 12 hours from now.", Toast.LENGTH_SHORT).show();
                    } else if (isTomorrow && currentHour >= minHoursSelect && (hourOfDay < finalMinHour || (hourOfDay == finalMinHour && minute < finalMinMinute))) {
                        SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Check-in time must be at least current time + 12 hours.");
                     //   Toast.makeText(this, "Check-in time must be at least current time + 12 hours.", Toast.LENGTH_SHORT).show();
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
                minHour,
                minMinute,
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


    private void showCheckOutTimePicker() {

        if (binding.tvCheckOutDate.getText().toString().isEmpty()) {
            //    SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Please select Check-Out date first!");

            Toast.makeText(this, "Please select Check-Out date first!", Toast.LENGTH_SHORT).show();
            return;
        }
         else  if (binding.tvCheckInTime.getText().toString().isEmpty()) {
          //  SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Please select Check-In time first!");
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
                            SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Check-Out time must be later than Check-In time.");
                        //    Toast.makeText(this, "Check-Out time must be later than Check-In time.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (hourOfDay == checkInHour && minute == checkInMinute) {
                            binding.tvCheckOutTime.setText("");
                            SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "Check-In and Check-Out time cannot be the same");
                         //   Toast.makeText(this, "Check-In and Check-Out time cannot be the same.", Toast.LENGTH_SHORT).show();
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



    private boolean isCheckOutTomorrow() {
        // Get the current date
        Calendar tomorrowCalendar = Calendar.getInstance();

        // Add 1 day to current date
        tomorrowCalendar.add(Calendar.DAY_OF_MONTH, 1);

        // Compare with Check-Out date
        return (checkOutCalendar.get(Calendar.YEAR) == tomorrowCalendar.get(Calendar.YEAR) &&
                checkOutCalendar.get(Calendar.MONTH) == tomorrowCalendar.get(Calendar.MONTH) &&
                checkOutCalendar.get(Calendar.DAY_OF_MONTH) == tomorrowCalendar.get(Calendar.DAY_OF_MONTH));
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
                       startActivity(new Intent(this, BookingListActivity.class));
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

                    if (isEditMode) {
                        jsonObject.put("id",bookingData.getId() );
                    }else {
                        jsonObject.put("id", JSONObject.NULL);
                    }


                    jsonObject.put("companyID", JSONObject.NULL);
                    jsonObject.put("branchID", selectBranch);
                    jsonObject.put("accountID", JSONObject.NULL);
                    jsonObject.put("checkInDate", selectedDateTextCheckIn.toString());
                    jsonObject.put("checkInTime", binding.tvCheckInTime.getText().toString());
                    jsonObject.put("checkoutDate", selectedDateTextCheckOut);
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
                initializeCheckInDateTime();
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



}
