package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_BRANCH_DETAILS;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.databinding.ActivityBranchItemDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BranchItemDetailsActivity extends AppCompatActivity {
    TextView marketer;
    Context mContext = this;
    TextView managerName, branch_MobNo, branch_EmailID, branch_OffCont, branch_weekly_off, branch_address, billing, account, go_down_packing, G_R;
    CircleImageView branchItem_Img;
    ImageView location_map;
    ImageView officeConcat;
    String lat = null;
    String lng = null;
    RelativeLayout rlBranchDetails;
    ActivityBranchItemDetailsBinding binding;
    String stayfacility_count = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBranchItemDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Handle system bars (status + nav bar) insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        marketer = findViewById(R.id.marketer);
        managerName = findViewById(R.id.managerName);
        branch_MobNo = findViewById(R.id.branch_MobNo);
        branch_EmailID = findViewById(R.id.branch_EmailID);
        branch_OffCont = findViewById(R.id.branch_OffCont);
        branch_weekly_off = findViewById(R.id.branch_weekly_off);
        branch_address = findViewById(R.id.branch_address);
        billing = findViewById(R.id.billing);
        account = findViewById(R.id.account);
        go_down_packing = findViewById(R.id.go_down_packing);
        G_R = findViewById(R.id.G_R);
        branchItem_Img = findViewById(R.id.branchItem_Img);
        location_map = findViewById(R.id.location_map);
        officeConcat = findViewById(R.id.officeConcat);
        rlBranchDetails = findViewById(R.id.rl_branch_details);
        if (SharedPref.read(SharedPref.USER_TYPE, "").equals("new")) {
            rlBranchDetails.setVisibility(View.GONE);
        } else {
            rlBranchDetails.setVisibility(View.VISIBLE);
        }

//        binding.supportChat.supportFab.setOnClickListener((View.OnClickListener) v ->
//                Lazy.openDialog(mContext));

        officeConcat.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + branch_OffCont.getText().toString()));
            Intent chooseIntent = Intent.createChooser(callIntent, "");
            mContext.startActivity(chooseIntent);
        });
        branch_OffCont.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + branch_OffCont.getText().toString()));
            Intent chooseIntent = Intent.createChooser(callIntent, "");
            mContext.startActivity(chooseIntent);
        });
        branch_MobNo.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + branch_MobNo.getText().toString()));
            Intent chooseIntent = Intent.createChooser(callIntent, "");
            mContext.startActivity(chooseIntent);
        });

        binding.callMobile.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + branch_MobNo.getText().toString()));
            Intent chooseIntent = Intent.createChooser(callIntent, "");
            mContext.startActivity(chooseIntent);
        });


        location_map.setOnClickListener(v -> {
            String label = branch_address.getText().toString();
            String uriBegin = "geo:" + lat + "," + lng;
            String query = lat + "," + lng + "(" + label + ")";
            String encodedQuery = Uri.encode(query);
            String uriString = uriBegin + "?q=" + encodedQuery;
            Uri uri = Uri.parse(uriString);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra("branchName"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//
//        ImageView backImage =findViewById(R.id.back3);
//        backImage.setImageDrawable(ContextCompat.getDrawable(BranchItemDetailsActivity.this, R.drawable.ic_baseline_keyboard_backspace));
//        backImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(BranchItemDetailsActivity.this,O_BranchesActivity.class));
//                finish();
//
//            }
//        });
//
//        TextView backImage2 =findViewById(R.id.back2);
//        backImage2.setText("BRANCH DETAIL");
        marketer.setOnClickListener(v -> startActivity(new Intent(BranchItemDetailsActivity.this, BranchMarketerActivity.class)
                .putExtra("branchMarketer_id", getIntent().getStringExtra("branches_id"))));
        billing.setOnClickListener(v -> startActivity(new Intent(BranchItemDetailsActivity.this, BillingActivity.class)
                .putExtra("branchMarketer_id", getIntent().getStringExtra("branches_id"))));
        account.setOnClickListener(v -> startActivity(new Intent(BranchItemDetailsActivity.this, AccountActivity.class)));
        go_down_packing.setOnClickListener(v -> startActivity(new Intent(BranchItemDetailsActivity.this, GodownPackingActivity.class)));
        G_R.setOnClickListener(v -> startActivity(new Intent(BranchItemDetailsActivity.this, GoodsReturnActivity.class)));
        binding.supportChat.supportFab.setOnClickListener((View.OnClickListener) v ->
                Lazy.openDialog(mContext));

        if (Lazy.haveNetworkConnection(mContext)) {
            GetBranchDetails();
        } else {
            networkConnetion3(mContext);
        }

    }

    private void GetBranchDetails() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_BRANCH_DETAILS, response -> {
            Log.e("Data", response);
            binding.includeProgress.progress.setVisibility(View.GONE);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("ResponseStatus")) {
                    JSONArray TotalCustomer = jsonObject.getJSONArray("BranchDetailsResult");
                    Log.e("test", TotalCustomer + "");
                    for (int i = 0; i < TotalCustomer.length(); i++) {
                        JSONObject ob = TotalCustomer.getJSONObject(i);

                        managerName.setText(ob.optString("ManagerName"));
                        branch_MobNo.setText(ob.optString("MobileNo"));
                        branch_EmailID.setText(ob.optString("EmailId"));
                        branch_OffCont.setText(ob.optString("ContactNo"));
                        branch_weekly_off.setText(ob.optString("WeeklyOff"));
                        branch_address.setText(ob.optString("BranchAddress"));
                        stayfacility_count = ob.optString("Stayfacility");
                        if (stayfacility_count.equals("1")) {
                            binding.stayfacility.setText("Yes");
                        } else if (stayfacility_count.equals("0") || stayfacility_count.equals("")) {
                            binding.stayfacility.setText("No");
                        }
                        lat = ob.optString("Latitude");
                        lng = ob.optString("Longitude");
                        Glide
                                .with(mContext)
                                .load(ob.optString("ImagePath"))
                                .placeholder(R.drawable.ic_user)
                                .into((branchItem_Img));
                    }
                } else {
                    AlertUtil.responseElse(mContext, "GetBranchDetails ", jsonObject.optString("ResponseMessage") + "");
                }
            } catch (JSONException e) {
                AlertUtil.responseExecption(mContext, "GetBranchDetails ", e.toString());

            }
        }, error -> {
            try {
                Constants.convertByteToString(mContext, "GetBranchDetails ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"BranchID\":\"" + SharedPref.read(SharedPref.D_ID, "") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    //    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(this,O_BranchesActivity.class));
//        finish();
//        super.onBackPressed();
//    }
//    private void openMapView(String latitude , String longitude , String locationName){
//        Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longitude+"?q="+locationName);
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
//            mContext.startActivity(mapIntent);
//        }
//    }
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
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        cross.setOnClickListener(v -> alertDialog.dismiss());
        try_button.setOnClickListener(view -> {
            GetBranchDetails();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }
}