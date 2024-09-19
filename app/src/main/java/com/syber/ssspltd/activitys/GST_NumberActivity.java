package com.syber.ssspltd.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.databinding.ActivityGstNumberBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GST_NumberActivity extends AppCompatActivity {
    private ActivityGstNumberBinding binding;
    Context mContext = this;
    EditText userName ;
    ImageView back;
    public String strGST_PAN = "";
    LocationManager locationManager;
    String latitude, longitude,add_result;
    private static final int REQUEST_LOCATION = 1;
    List<Address> addresses;
    Geocoder geocoder;
    String pan_gst="gst";
    String station_add;
    boolean isVaildReferal = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGstNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        station_add =binding.stationAdd.getText().toString();
        binding.userName.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        binding.firmName.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        binding.stationAdd.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        binding.refCodeSubmit.setOnClickListener(v ->
        {
            REFERRAL(binding.haveReferal.getText().toString());
            hideKeyboard();
        });

        binding.firmName.addTextChangedListener(textWatcher3);
        binding.userName.addTextChangedListener(textWatcher);
        binding.stationAdd.addTextChangedListener(textWatcher2);

//        ArrayList<InputFilter> curInputFilters = new ArrayList<InputFilter>(Arrays.asList(binding.gstAndPan.getFilters()));
//        curInputFilters.add(1, new InputFilter.AllCaps());
//        InputFilter[] newInputFilters = curInputFilters.toArray(new InputFilter[curInputFilters.size()]);
//        binding.gstAndPan.setFilters(newInputFilters);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.refCodeSubmit.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        binding.back.setOnClickListener(v ->
        {
            startActivity(new Intent(mContext,LoginPage.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        });
        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        //getLocation();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        };
        findViewById(R.id.login).setOnClickListener(v ->{
            if (Lazy.haveNetworkConnection(mContext)){
                getLocation();
                if(validateGstPan())
                {
                    getGST_PAN(SharedPref.read(SharedPref.USERMOBILE,""),binding.haveReferal.getText().toString(),"",binding.userName.getText().toString()
                            ,latitude,latitude,binding.stationAdd.getText().toString(),binding.firmName.getText().toString());
                }
            }else {
                networkConnetion3(mContext);
            }


        });

//        binding.radioGroupId.setOnCheckedChangeListener((radioGroup, checkedId) -> {
//            if (checkedId == R.id.panNo){
//                pan_gst = "pan";
//                binding.gstAndPan.getText().clear();
//                int maxLength = 10;
//                binding.gstAndPan.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength), new InputFilter.AllCaps()});
//                //   binding.gstAndPan.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//            }else if (checkedId == R.id.gstNo){
//                pan_gst = "gst";
//                binding.gstAndPan.getText().clear();
//                int maxLength = 15;
//                binding.gstAndPan.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength), new InputFilter.AllCaps()});
//
//                //    binding.gstAndPan.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//            }else {
//
//            }
//
//        });
//        binding.gstAndPan.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS|InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS | InputType.TYPE_TEXT_FLAG_CAP_WORDS | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        binding.userName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS|InputType.TYPE_TEXT_VARIATION_NORMAL);
        binding.firmName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS|InputType.TYPE_TEXT_VARIATION_NORMAL);
        binding.stationAdd.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS|InputType.TYPE_TEXT_VARIATION_NORMAL);
    }
    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.haveReferal.getWindowToken(), 0);
    }

    private void getGST_PAN(String mob,String referal,String gst,String name,String latitude,String longitude,String add_result,String firmName) {
        final ProgressDialog progressBar = new ProgressDialog(mContext);
        progressBar.setTitle("Loading....");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://app.ssspltd.com/apipltd/SaveNewUserDetails",
                response -> {
                    Log.e("response", response);
                    progressBar.dismiss();
                    //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("jsonObject", jsonObject + "");
                        if (jsonObject.getBoolean("ResponseStatus")) {
                            //  SharedPref.write(SharedPref.USERMOBILE, enter_mobile_number.getText().toString());
                            startActivity(new Intent(mContext,MainActivity.class));
                            SharedPref.write(SharedPref.PARTY_CODE,"new");
                            SharedPref.write(SharedPref.USER_TYPE,"new");
                            SharedPref.write(SharedPref.DASHBOARD_TYPE,"New User");
                            SharedPref.write(SharedPref.SELECTED, "");
                            SharedPref.write(SharedPref.WHERE_TO_GO,"main_act");
                            SharedPref.write(SharedPref.TYPE,"notAdmin");
                            SharedPref.write(SharedPref.isLogin, "true");
                            finish();
                        } else
                        {
                            //  Toast.makeText(mContext, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkConnetion3(mContext);
                progressBar.cancel();
            }
        }) {
            @Override
            public byte[] getBody() {
                String str = "{\"MOBILENO\":\"" + mob + "\",\"REFERRAL\":\"" + referal + "\",\"GSTNO\":\"" + gst + "\",\"Name\":\"" + name + "\",\"Address\":\"" + add_result+ "\"" +
                        ",\"Lattitude\":\"" + latitude + "\",\"Longitude\":\"" + longitude + "\",\"FirmName\":\"" + firmName + "\"}";
                //key - new
                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", (dialog, which) -> dialog.cancel());
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                GST_NumberActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                GST_NumberActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                Log.e("latitude",latitude);
                Log.e("longitude",longitude);
                Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
                try {
                    List<Address> addressesLocation = geocoder.getFromLocation(lat, longi, 1);

                    if (addressesLocation != null && addressesLocation.size() > 0) {
                        Address address = addressesLocation.get(0);
                        add_result= address.getAddressLine(0);
                        Log.e("add",add_result);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private boolean validateGstPan(){
        boolean temp = true;
        if (binding.userName.getText().toString().isEmpty()){
            Toast.makeText(mContext, "Enter Name", Toast.LENGTH_SHORT).show();
            temp = false;
        }
        else if (binding.firmName.getText().toString().isEmpty()){
            Toast.makeText(mContext, "Enter Firm Name", Toast.LENGTH_SHORT).show();
            temp = false;
        }
        else if (binding.stationAdd.getText().toString().isEmpty()){
            Toast.makeText(mContext, "Enter Station", Toast.LENGTH_SHORT).show();
            temp = false;
        }
//        else if (pan_gst.equals("gst") && binding.gstAndPan.getText().toString().length() <15){
//            Toast.makeText(mContext, "Invalid GST", Toast.LENGTH_SHORT).show();
//            temp = false;
//        }else if (pan_gst.equals("pan") && binding.gstAndPan.getText().toString().length() <10){
//            Toast.makeText(mContext, "Invalid PAN", Toast.LENGTH_SHORT).show();
//            temp = false;
//        }
        else if (!binding.haveReferal.getText().toString().isEmpty())
        {
            if (!isVaildReferal) {
                Toast.makeText(mContext, "Validate referral first", Toast.LENGTH_SHORT).show();
                temp = false;
            }
            else if (!binding.haveReferal.getText().toString().equals(SharedPref.read(SharedPref.Referal_code,"")))
            {
                Log.e("ref_cde",binding.haveReferal.getText().toString());
                Log.e("shardRef_code",SharedPref.read(SharedPref.Referal_code,""));
                Toast.makeText(mContext, "Invalid referral", Toast.LENGTH_SHORT).show();
                temp = false;
            }
        }
        return temp;
    }

    private void REFERRAL(String referal) {
        final ProgressDialog progressBar = new ProgressDialog(mContext);
        progressBar.setTitle("REFERRAL");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://app.ssspltd.com/apipltd/VerifyReferral",
                response -> {
                    Log.e("Data", response);
                    progressBar.dismiss();
                    //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("jsonObject", jsonObject + "");
                        if (jsonObject.getBoolean("ResponseStatus") == true) {
                            SharedPref.write(SharedPref.Referal_code,binding.haveReferal.getText().toString());
                            isVaildReferal = true;
                            binding.refName.setText(jsonObject.getString("Name"));

                        }
                        //dialog.dismiss();
                        else {
                            isVaildReferal=false;
                            binding.refName.setText("Invalid referral");
                            Toast.makeText(mContext, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkConnetion3(mContext);
                progressBar.cancel();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"REFERRAL\":\"" + referal + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext,LoginPage.class));
        finish();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //binding.userName.setText(s);
            int maxLength = 30;
            if (s.toString().length() == 30){
                binding.userName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength), new InputFilter.AllCaps()});
                binding.userNameSize.setVisibility(View.VISIBLE);
                binding.userNameSize.setText("Maximum 30 Characters");

            }
            else {
                binding.userNameSize.setVisibility(View.GONE);

            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    TextWatcher textWatcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //binding.userName.setText(s);
            int maxLength = 20;
            if (s.toString().length() == 20){
                binding.stationAdd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength), new InputFilter.AllCaps()});
                binding.stationAddSize.setText("Maximum 20 Characters");
                binding.stationAddSize.setVisibility(View.VISIBLE);

            }
            else {
                binding.stationAddSize.setVisibility(View.GONE);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    TextWatcher textWatcher3 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //binding.userName.setText(s);
            int maxLength = 30;
            if (s.toString().length() == 30){
                binding.firmName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength), new InputFilter.AllCaps()});
                binding.firmNameSize.setText("Maximum 30 Characters");
                binding.firmNameSize.setVisibility(View.VISIBLE);
            }
            else {
                binding.firmNameSize.setVisibility(View.GONE);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

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
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });
        try_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Lazy.haveNetworkConnection(mContext)){
                    alertDialog.dismiss();
                }else {
                    networkConnetion3(mContext);
                }
            }
        });
        alertDialog.show();
    }
}