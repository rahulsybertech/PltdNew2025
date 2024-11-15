package com.syber.ssspltd.activitys.Offers;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_COUPON_DETAILS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.syber.ssspltd.adapter.Offers.OffersAdapter;
import com.syber.ssspltd.databinding.ActivityOffersBinding;
import com.syber.ssspltd.response.Offers.CouponList;
import com.syber.ssspltd.response.Offers.OffersPojo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OffersActivity extends AppCompatActivity {
    ActivityOffersBinding binding;
    Context mContext=this;
    OffersAdapter offersAdapter;
    List<CouponList>couponLists;
    Type listType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOffersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setTitle("Offers");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        couponLists= new ArrayList<>();
        listType=new TypeToken<OffersPojo>(){}.getType();
        binding.supportChat.supportFab.setOnClickListener(v ->
                Lazy.openDialog(mContext));


        offersAdapter= new OffersAdapter(mContext,couponLists);
        binding.officeRecy.setAdapter(offersAdapter);
        if (Lazy.haveNetworkConnection(mContext)){
            getOffice();
        }else {
            networkConnetion3(mContext);
        }


    }
    private void getOffice()
    {
        final ProgressDialog progressBar = new ProgressDialog(mContext);
//        progressBar.setTitle("Fetching Data");
//        progressBar.show();
        binding.spinKit.setVisibility(View.VISIBLE);// "http://app.ssspltd.com/apipltd/GetCouponDetails" old url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_COUPON_DETAILS,
                response -> {
                    Log.e("Data", GET_COUPON_DETAILS + " ===== " + response);
                    //progressBar.dismiss();
                    //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show()
                    OffersPojo pojo = new Gson().fromJson(response,listType);
                    try {
                    if (pojo.getResponseStatus()){
                        binding.spinKit.setVisibility(View.GONE);
                        couponLists.clear();
                        couponLists.addAll(pojo.getCouponList());
                        offersAdapter.notifyDataSetChanged();
                    }
                    else {
                        AlertUtil.responseElse(mContext, "GetCouponDetails ", pojo.getResponseMessage() + "");
                    }}catch (Exception e){
                        AlertUtil.responseExecption(mContext, "GetCouponDetails ", e.toString());
                    }
                }, error ->  AlertUtil.responseError(mContext, "GetCouponDetails ", error.toString())) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                // String otpp = otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob + "\"}";
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
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
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
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        try_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getOffice();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}