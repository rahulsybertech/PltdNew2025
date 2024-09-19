package com.syber.ssspltd.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.syber.ssspltd.Interface.TopicClickListener;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.CustomAdapters.CustomAdapter;
import com.syber.ssspltd.adapter.FYearAdapter;
import com.syber.ssspltd.adapter.HomeNameAdapter;
import com.syber.ssspltd.fragment.HomeFragment;
import com.syber.ssspltd.fragment.MoreFragment;
import com.syber.ssspltd.fragment.NewGalleryFragment;
import com.syber.ssspltd.response.FinanacialYearListRespon.FYearList;
import com.syber.ssspltd.response.FinanacialYearListRespon.FYearListResult;
import com.syber.ssspltd.response.ModelClass.RowItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.syber.ssspltd.activitys.registered_msg.UsersTyperDetails;

public class MainActivity extends AppCompatActivity implements TopicClickListener, View.OnTouchListener {
    //ShadowGenerator shadowGenerator;
    ImageView popup;
    TextView home, tv3, more, tv5;
    String a;
    List<RowItem> rowItems;
    Context mContext = this;
    LinearLayoutManager linearLayoutManager;
    String psrty;
    private int positionFromBack;
    ArrayList<FYearListResult> fYearListResults = new ArrayList<>();
    // FinanacialYearListAdapter finanacialYearListAdapter;
    FYearAdapter fYearAdapter;
    private static String checked = "";
    int _xDelta;
    int _yDelta;
    float dX, dY;
    AlertDialog alertDialog;


    Boolean isOnePressed = false, isThirdPlace = false, isforthPlace = false;
    Boolean check_one = false, check_two = false;
    Calendar c;
    DatePickerDialog dpd;
    FrameLayout frameLayout;
    String frg_type = "0";
    private Dialog sDialog;
    RecyclerView mainRecy_List;
    HomeNameAdapter homeNameAdapter;
    CustomAdapter adapter;
    List<RowItem> rowItemList;
    public static RecyclerView ListRescyler;
    Type listType, fYearType;
    TextView adminName, singleName;
    RelativeLayout spVisility;
    Toolbar backpressButton;
    boolean isRecyclerOpen = false;
    RecyclerView financial_year;
    public static String db_name="A7" , set_year, selectedYr ="2023-24", fy_StartDate, fy_EndDate,def_db;
    FrameLayout rll;
    TextView title;
    RelativeLayout rl;
    FloatingActionButton support_fab;
    int lastAction;
    ImageView sssLogo;



    //    ArrayList<UsersTypeListResult>usersTypeList ;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sssLogo = findViewById(R.id.sss_logo);

//        Toast.makeText(mContext, SharedPref.read(SharedPref.PARTY_CODE,""), Toast.LENGTH_SHORT).show();
        if (SharedPref.read(SharedPref.clubType,"").equalsIgnoreCase("DIAMOND")){
            sssLogo.setImageDrawable(getDrawable(R.mipmap.ic_launcher__new_diamond));
        } else if (SharedPref.read(SharedPref.clubType,"").equalsIgnoreCase("GOLD")) {
            sssLogo.setImageDrawable(getDrawable(R.mipmap.ic_launcher__new_gold));
        }else {
            sssLogo.setImageDrawable(getDrawable(R.mipmap.ic_launcher_sss_logo));
        }

//        usersTypeList = new ArrayList<>();
        backpressButton = findViewById(R.id.backpressButton);
        setSupportActionBar(backpressButton);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPref.init(mContext);
        title = findViewById(R.id.title);
        rl = findViewById(R.id.rl);
        support_fab = findViewById(R.id.support_fab);

        support_fab.setOnClickListener(view ->
                Lazy.openDialog(mContext)

        );



        if (SharedPref.read(SharedPref.IS_BACK_VISIBLE, "").equals("true")) {
            backpressButton.setVisibility(View.VISIBLE);
        } else {
            backpressButton.setVisibility(View.GONE);
        }

        rowItemList = new
                ArrayList<>();
        fYearType = new TypeToken<FYearList>() {
        }.getType();


        rll = findViewById(R.id.rll);
        frameLayout = findViewById(R.id.fragment_container);
        home = findViewById(R.id.home1);
        tv3 = findViewById(R.id.home3);
        more = findViewById(R.id.home4);

        rll.setOnClickListener(v -> {
            if (isRecyclerOpen == true) {
                adminName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_up_24, 0);
                ListRescyler.setVisibility(View.GONE);
                isRecyclerOpen = false;
                rll.setVisibility(View.GONE);
            }
        });
        ListRescyler = findViewById(R.id.ListRescyler);
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ListRescyler.setLayoutManager(linearLayoutManager);
        adapter = new CustomAdapter(mContext, rowItemList);
        ListRescyler.setAdapter(adapter);
        adminName = findViewById(R.id.adminName);
        singleName = findViewById(R.id.singleName);
        checked = SharedPref.read(SharedPref.CHECK, "");
//        if (SharedPref.read(SharedPref.TYPE, "").equals("notAdmin")) {
//            adminName.setText(SharedPref.read(SharedPref.SELECTED, ""));
//            singleName.setVisibility(View.VISIBLE);
//            adminName.setVisibility(View.GONE);
//        } else if (SharedPref.read(SharedPref.TYPE, "").equals("Admin")) {
//            adminName.setText(SharedPref.read(SharedPref.SELECTED, ""));
//            singleName.setVisibility(View.GONE);
//            adminName.setVisibility(View.VISIBLE);
//            adminName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
//
//        }


        adminName.setText(SharedPref.read(SharedPref.SELECTED, ""));
        singleName.setVisibility(View.GONE);
        adminName.setVisibility(View.VISIBLE);
        adminName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_down_24, 0);

        adminName.setOnClickListener(v -> {
            if (adapter.getItemCount() == 1) {
                adminName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else {
                if (isRecyclerOpen == false) {
                    adminName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_up_24, 0);
                    //adminName.setCompoundDrawables(null,null,getResources().getDrawable(R.drawable.ic_down),null);
                    ListRescyler.setVisibility(View.VISIBLE);
                    isRecyclerOpen = true;
                    rll.setVisibility(View.VISIBLE);

                } else {
                    adminName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_down_24, 0);
                    ListRescyler.setVisibility(View.GONE);
                    rll.setVisibility(View.GONE);
                    isRecyclerOpen = false;
                }
            }

        });

        try {
            if (getIntent().getStringExtra("mf").equals("1")) {
                frg_type = "1";

            } else {
                frg_type = "0";
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        if (frg_type.equals("1")) {
            loadFragment(new MoreFragment());
//            tv4.setBackground(getResources().getDrawable(R.drawable.button_one));
////           tv4.setTextColor(getResources().getColor(R.color.white));
        } else if (frg_type.equals("0")) {
            loadFragment(new HomeFragment());
        }

        isOnePressed = true;
        home.setBackground(getResources().getDrawable(R.drawable.button_one));
        home.setTextColor(getResources().getColor(R.color.white));
        popup = findViewById(R.id.calender);
        if (SharedPref.read(SharedPref.USER_TYPE, "").equals("new")) {
            popup.setVisibility(View.GONE);
        }

//        cancel_button=findViewById(R.id.cancel_button);
//        cancel_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fileList();
//            }
//        });

        popup.setOnClickListener(v -> FYearDialog());


        home.setOnClickListener(v -> {
            loadFragment(new HomeFragment());
            rl.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);

            isOnePressed = true;
            home.setBackground(getResources().getDrawable(R.drawable.button_one));
            home.setTextColor(getResources().getColor(R.color.white));
            if (isThirdPlace || isforthPlace) {
//                pay.setBackground(getResources().getDrawable(R.drawable.shadow_button_layer_list));
//                pay.setTextColor(getResources().getColor(R.color.black));
                tv3.setBackground(getResources().getDrawable(R.drawable.shadow_button_layer_list));
                tv3.setTextColor(getResources().getColor(R.color.black));
                more.setBackground(getResources().getDrawable(R.drawable.shadow_button_layer_list));
                more.setTextColor(getResources().getColor(R.color.black));
                isThirdPlace = false;
                isforthPlace = false;
            }
            ;
        });
//        pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadFragment(new PayFragment());
//                rl.setVisibility(View.GONE);
//                title.setVisibility(View.VISIBLE);
//                title.setText("Pay");
//                pay.setBackground(getResources().getDrawable(R.drawable.button_one));
//                pay.setTextColor(getResources().getColor(R.color.white));
//                isSecondPlace = true;
//                if (isOnePressed || isThirdPlace || isforthPlace) {
//                    home.setBackground(getResources().getDrawable(R.drawable.shadow_button_layer_list));
//                    home.setTextColor(getResources().getColor(R.color.black));
//                    tv3.setBackground(getResources().getDrawable(R.drawable.shadow_button_layer_list));
//                    tv3.setTextColor(getResources().getColor(R.color.black));
//                    more.setBackground(getResources().getDrawable(R.drawable.shadow_button_layer_list));
//                    more.setTextColor(getResources().getColor(R.color.black));
//                    isOnePressed = false;
//                    isThirdPlace = false;
//                    isforthPlace = false;
//                }
//            }
//        });
        tv3.setOnClickListener(v -> {
            loadFragment(new NewGalleryFragment());
            rl.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            title.setText("Gallery");
            tv3.setBackground(getResources().getDrawable(R.drawable.button_one));
            tv3.setTextColor(getResources().getColor(R.color.white));
            isThirdPlace = true;
            if (isOnePressed || isforthPlace) {
                home.setBackground(getResources().getDrawable(R.drawable.shadow_button_layer_list));
                home.setTextColor(getResources().getColor(R.color.black));
//                pay.setBackground(getResources().getDrawable(R.drawable.shadow_button_layer_list));
//                pay.setTextColor(getResources().getColor(R.color.black));
                more.setBackground(getResources().getDrawable(R.drawable.shadow_button_layer_list));
                more.setTextColor(getResources().getColor(R.color.black));
                isOnePressed = false;
                isforthPlace = false;
            }

        });
        more.setOnClickListener(v -> {
            loadFragment(new MoreFragment());
            rl.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            title.setText("More");
            more.setBackground(getResources().getDrawable(R.drawable.button_one));
            more.setTextColor(getResources().getColor(R.color.white));
            isforthPlace = true;
            if (isOnePressed || isThirdPlace) {
                home.setBackground(getResources().getDrawable(R.drawable.shadow_button_layer_list));
                home.setTextColor(getResources().getColor(R.color.black));
//                pay.setBackground(getResources().getDrawable(R.drawable.shadow_button_layer_list));
//                pay.setTextColor(getResources().getColor(R.color.black));
                tv3.setBackground(getResources().getDrawable(R.drawable.shadow_button_layer_list));
                tv3.setTextColor(getResources().getColor(R.color.black));
                isOnePressed = false;
                isThirdPlace = false;
                //tv3.clearComposingText();
            }

        });
        if (Lazy.haveNetworkConnection(mContext)){
            GetUsersTypeList();
            GetFYearList();
        }else {
            networkConnetion3(mContext);
        }
       // showPermissionDialog();



//        }

//        }else {
//
//        }


//        getIntent().getStringE xtra("partCode");
//        getIntent().getStringExtra("userMob");
//        Log.e("p", getIntent().getStringExtra("partCode"));
//        Log.e("u", getIntent().getStringExtra("userMob"));
    }

    private void GetUsersTypeList() {
        final ProgressDialog progressBar = new ProgressDialog(mContext);
        progressBar.setTitle("Fetching Data");
        // progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://app.ssspltd.com/apipltd/GetUsersTypeList",
                response -> {
                    Log.e("response", response);
                    try {


                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("ResponseStatus") == true) {

                            JSONArray BankListData = jsonObject.getJSONArray("UsersTypeListResult");
                            Log.e("UsersTypeListResult", BankListData + "");
                            rowItemList.clear();
                            for (int i = 0; i < BankListData.length(); i++) {
                                JSONObject ob = BankListData.getJSONObject(i);
                                String name = ob.optString("Name");
                                psrty = ob.optString("PartyCode");
                                String sn = ob.optString("SRNO");
                                String mobNo = ob.optString("UserType");
                                Log.e("name", name);
                                Log.e("partycode", psrty);
                                if (!mobNo.equals("5")) {

                                    rowItemList.add(new RowItem(name, psrty, sn, mobNo));
                                }

//                                    SharedPref.write(SharedPref.PARTY_CODE,psrty);
                                // supplierListResult = new SupplierListResult(name);

                            }
                            if (rowItemList.size() == 1) {
                                adminName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            }

                            adapter.notifyDataSetChanged();
                        } else {

                        }
                    } catch (Exception e) {

                    }
                }, error -> {
            networkConnetion3(mContext);

        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob3 + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void FYearDialog() {
        // ViewGroup viewGroup = findViewById(android.R.id.content);
        //final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.RoundedDialog);

        final View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog,
                findViewById(R.id.dialog));
        dialogView.setClickable(false);
        ImageView cancel_button = dialogView.findViewById(R.id.cancel_button);
        Button sumbitYear = dialogView.findViewById(R.id.sumbitYear);
        financial_year = dialogView.findViewById(R.id.fYear_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        financial_year.setLayoutManager(linearLayoutManager);
        fYearAdapter = new FYearAdapter(mContext, fYearListResults, MainActivity.this);
        financial_year.setAdapter(fYearAdapter);
        fYearAdapter.notifyDataSetChanged();
//        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.BottomSheetDialogTheme2);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.RoundedDialog);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        sumbitYear.setOnClickListener(v -> {
          //  List<FYearListResult> fYearListResultList=new ArrayList<>();
//            Log.e("FYearListResult",fYearListResults.size()+"0");
//            fYearListResults.forEach(e->{
//              Log.e("FYearListResult",e.isChecked()+"0");
//          });

            SharedPref.write(SharedPref.default_db, def_db);
            SharedPref.write(SharedPref.DB_NAME, db_name);
            SharedPref.write(SharedPref.SET_YEAR, set_year);
            Log.e("dh",db_name+"null");
            SharedPref.write(SharedPref.selected_default_yr, selectedYr);
            SharedPref.write(SharedPref.FY_StartDate, fy_StartDate);
            SharedPref.write(SharedPref.FY_EndDate, fy_EndDate);
            loadFragment(new HomeFragment());
            fYearAdapter.notifyDataSetChanged();
//                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(intent);
            alertDialog.dismiss();


        });


        cancel_button.setOnClickListener(v ->
                alertDialog.dismiss()
        );
        alertDialog.show();
    }


    public void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            fragmentManager.beginTransaction();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("OK", (dialog, which) -> finishAffinity());

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();

    }

    void getAGGD() {
        Log.e("test", "fshg");
        Log.e("jsf", "jsj");
        Log.e("login", "log");
    }

    private void HomeTypeDialog() {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.main_list_recy);
        sDialog.setCancelable(true);
        mainRecy_List = sDialog.findViewById(R.id.mainRecy_List);


        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mainRecy_List.setLayoutManager(linearLayoutManager);
        homeNameAdapter = new HomeNameAdapter(mContext, UsersTyperDetails);
        mainRecy_List.setAdapter(homeNameAdapter);


        sDialog.show();
    }

    public void setListRecyler(RowItem rowItem) {
        String n = rowItem.getmName();
        ListRescyler.setVisibility(View.GONE);
        rll.setVisibility(View.GONE);
        adminName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_up_24, 0);
//         partyCode = employeeListResult.getPartyCode();
//         mobNo = employeeListResult.getUserMobileNo();
        SharedPref.write(SharedPref.LIST_TYPE, rowItem.getmUserType());
        SharedPref.write(SharedPref.SELECTED, n);
        SharedPref.write(SharedPref.PARTY_CODE, rowItem.getmPartyCode());

        Log.e("DB_NAME", rowItem.getmUserType());
        Log.e("part", rowItem.getmPartyCode());
        if (SharedPref.read(SharedPref.LIST_TYPE, "").equals("2")) {
            SharedPref.read(SharedPref.PARTY_CODE, "");
            SharedPref.write(SharedPref.DASHBOARD_TYPE, "Supplier");
            //  SharedPref.write(SharedPref.TYPE, "notAdmin");
        } else if (SharedPref.read(SharedPref.LIST_TYPE, "").equals("1")) {
            SharedPref.read(SharedPref.PARTY_CODE, "");
            SharedPref.write(SharedPref.DASHBOARD_TYPE, "Customer");
            // SharedPref.write(SharedPref.TYPE, "notAdmin");
        } else if (SharedPref.read(SharedPref.LIST_TYPE, "").equals("3")) {
            SharedPref.read(SharedPref.PARTY_CODE, "");
            SharedPref.write(SharedPref.DASHBOARD_TYPE, "Other");
            //SharedPref.write(SharedPref.TYPE, "notAdmin");
        }

//        SharedPref.write(SharedPref.USERMOBILE,rowItem.getUserMobileNo());
//        Log.e("party_code",employeeListResult.getPartyCode());
//        Log.e("mobNo",employeeListResult.getUserMobileNo());


        adminName.setText(n);

        loadFragment(new HomeFragment());
        adapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void GetFYearList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://app.ssspltd.com/apipltd/GetFYearList",
                response -> {
                    Log.e("Data", response);
                    //JSONObject jsonObject = new JSONObject(response);
                    FYearList pojo = new Gson().fromJson(response, fYearType);
                    fYearListResults.clear();
                    if (pojo.getResponseStatus()) {
                        for (int i = 0; i < pojo.getFYearListResult().size(); i++) {
                            Log.e("defult_db",pojo.getFYearListResult().get(i).getmDEFAULTDB());
                            if (SharedPref.read(SharedPref.selected_default_yr, "").equals(pojo.getFYearListResult().get(i).getFYEAR())) {
                                SharedPref.write(SharedPref.default_db, i + "");
                                SharedPref.write(SharedPref.selected_default_yr, pojo.getFYearListResult().get(i).getFYEAR());
                                SharedPref.write(SharedPref.FY_StartDate,pojo.getFYearListResult().get(i).getmFY_StartDate());
                                SharedPref.write(SharedPref.FY_EndDate,pojo.getFYearListResult().get(i).getmFY_EndDate());
                                Log.e("slecteYr", SharedPref.read(SharedPref.selected_default_yr, "") + i);

                                break;
                            } else if (pojo.getFYearListResult().get(i).getmDEFAULTDB().equals("True")) {
                                Log.e("defult_db_secound",pojo.getFYearListResult().get(i).getmDEFAULTDB());
                                pojo.getFYearListResult().get(i).setChecked(true);
                                SharedPref.write(SharedPref.default_db, i + "");
                                SharedPref.write(SharedPref.selected_default_yr, pojo.getFYearListResult().get(i).getFYEAR());
                                SharedPref.write(SharedPref.FY_StartDate,pojo.getFYearListResult().get(i).getmFY_StartDate());
                                SharedPref.write(SharedPref.FY_EndDate,pojo.getFYearListResult().get(i).getmFY_EndDate());
                                Log.e("slecteYr", SharedPref.read(SharedPref.selected_default_yr, "") + i);
                                break;
                            }
                        }
                        fYearListResults.addAll(pojo.getFYearListResult());
                        //fYearAdapter.notifyDataSetChanged();

                    }

                }, error -> {
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob + "\"}";
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (SharedPref.read(SharedPref.IS_ANY_CHOOSEN, "").equals("true")) {
                    startActivity(new Intent(mContext, ChooseCategries.class));
                    finish();
                } else if (SharedPref.read(SharedPref.IS_SUPPER_SELECTED, "").equals("true")) {
                    startActivity(new Intent(mContext, registered_msg.class));
                    finish();
                } else {
                    finish();
                }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(FYearListResult topic, int parentPos, boolean checked) {
        financial_year.setAdapter(fYearAdapter);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = v.getX() - event.getRawX();
                dY = v.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                v.setY(event.getRawY() + dY);
                v.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN)
                    Lazy.openDialog(mContext);
                break;

            default:
                return false;
        }
        return true;
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
                GetUsersTypeList();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
//    private void showPermissionDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Permission required")
//                .setMessage("Some permissions are need to be allowed to use this app without any problems.")
//                .setPositiveButton("Done", (dialog, which) -> {
//                    dialog.dismiss();
//                });
//        if (alertDialog == null) {
//            alertDialog = builder.create();
//            if (!alertDialog.isShowing()) {
//                alertDialog.show();
//            }
//        }
//    }



}



