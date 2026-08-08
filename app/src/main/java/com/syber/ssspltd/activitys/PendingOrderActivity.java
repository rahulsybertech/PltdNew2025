package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.ConstantVariable.AUTH_TOKEN;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_COURIER_REPORT;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_FILTER_DETAIL_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_FILTER_LIST_NEW;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_PENDING_ORDER_REPORT;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.Interface.FilterChangedPending;
import com.syber.ssspltd.NewFilter.PendingOrder.Branch;
import com.syber.ssspltd.NewFilter.PendingOrder.Brand;
import com.syber.ssspltd.NewFilter.PendingOrder.PendingOrderFilterRequest;
import com.syber.ssspltd.NewFilter.PendingOrder.PendingOrderPojo;
import com.syber.ssspltd.NewFilter.PendingOrder.SubParty;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.CurrentDateTime;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.FilterBranchAdap;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.FilterBrandNameAdap;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.FilterSubPartyAdap;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.FilterTypePendingOrder;
import com.syber.ssspltd.adapter.PendingOrdFilterAdapter.BranchAdapter;
import com.syber.ssspltd.adapter.PendingOrdFilterAdapter.Sup_PartyAdapter;
import com.syber.ssspltd.adapter.PendingOrdFilterAdapter.SupplierAdapter;
import com.syber.ssspltd.adapter.PendingOrderReportAdapter;
import com.syber.ssspltd.databinding.ActivityPendingOrderBinding;
import com.syber.ssspltd.response.PendingOrdBranchRespo.FilterListPojo;
import com.syber.ssspltd.response.PendingOrdBranchRespo.FilterListResult;
import com.syber.ssspltd.response.PendingOrderReport.PendingOrderPoojo;
import com.syber.ssspltd.response.PendingOrderReport.PendingOrderReportResult;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class PendingOrderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, FilterChangedPending {
    public static TextView countbranch, countsub_party, countbrand;
    static List<PendingOrderReportResult> pendingOrderDetails;
    Context mContext = this;
    PendingOrderReportAdapter pendingOrdertAdapter;
    Type listType;
    LinearLayoutManager linearLayoutManager;
    String banch = "null", subparty = "null", supplier = "null", form_date = "null", to_Date = "null", dbNAME = SharedPref.read(SharedPref.DB_NAME, "");
    Boolean isDatePressed = false, isBranchPlace = false, isSubPartyPlace = false, isSuppNPlace = false, isTransportPlace = false;
    TextView pendingFilter_Date, pendingFilter_Branch, pendingFilter_SubParty, pendingFilter_SuppNikName, nodata;
    RecyclerView pending_Recy;
    LinearLayout llRange;
    TextView formDate, todate;
    String keyTypeList = "";
    Type branchType, subpartyListType, transList, supList;
    BranchAdapter branchAdapter;
    Sup_PartyAdapter sup_partyAdapter;
    SupplierAdapter supplierAdapter;
    List<com.syber.ssspltd.response.PendingOrdBranchRespo.FilterListResult> branchList;
    List<com.syber.ssspltd.response.PendingOrdBranchRespo.Sup_PartyRespo.FilterListResult> subpartyList;
    List<com.syber.ssspltd.response.PendingOrdBranchRespo.SupplierRespo.FilterListResult> supplierList;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormat2;
    String flag = "";
    String Count = "", Count2 = "", Count3 = "";
    boolean isFilterShowing = false;
    ImageView backImage3;
    PendingOrderPojo pendingOrderPojo;
    Stack<FilterTypePendingOrder> filterStack;
    List<Branch> branch_List;
    List<SubParty> subPartyList;
    List<Brand> brandList;
    FilterBranchAdap filterBranchAdap;
    FilterBrandNameAdap filterBrandNameAdap;
    FilterSubPartyAdap filterSubPartyAdap;
    Type branch_Type, subParty_Type, brand_Type;
    ProgressBar progressBar;
    String StartDate_filter, Enddate_filter;
    Dialog dialog;
    private ActivityPendingOrderBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPendingOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Handle system bars (status + nav bar) insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.supportChat.supportFab.setOnClickListener(v ->
                Lazy.openDialog(mContext));

        pendingOrderDetails = new ArrayList<>();

        listType = new TypeToken<PendingOrderPoojo>() {
        }.getType();

        pendingOrdertAdapter = new PendingOrderReportAdapter(mContext, pendingOrderDetails);
        binding.pendingOrderRecy.setAdapter(pendingOrdertAdapter);

        ImageView backImage = findViewById(R.id.back3);
        backImage.setImageDrawable(ContextCompat.getDrawable(PendingOrderActivity.this, R.drawable.ic_baseline_keyboard_backspace));
        backImage.setOnClickListener(v -> onBackPressed());

        backImage3 = findViewById(R.id.download);
        backImage3.setImageDrawable(ContextCompat.getDrawable(PendingOrderActivity.this, R.drawable.ic_filter));
        backImage3.setOnClickListener(v -> {
            if (!isFilterShowing) {

                filterDialog2();
                //filterDialog();
            }
        });
        TextView backImage2 = findViewById(R.id.back2);
        backImage2.setText(" PENDING ORDER ");


//new Filter
        filterStack = new Stack<>();
        branch_List = new ArrayList<>();
        subPartyList = new ArrayList<>();
        brandList = new ArrayList<>();
        brandList = new ArrayList<>();

        filterBranchAdap = new FilterBranchAdap(mContext, branch_List, this);
        filterSubPartyAdap = new FilterSubPartyAdap(mContext, subPartyList, this);
        filterBrandNameAdap = new FilterBrandNameAdap(mContext, brandList, this);


        branch_Type = new TypeToken<PendingOrderPojo>() {
        }.getType();
        subParty_Type = new TypeToken<PendingOrderPojo>() {
        }.getType();
        brand_Type = new TypeToken<PendingOrderPojo>() {
        }.getType();
//new Filter

        branchList = new ArrayList<>();
        subpartyList = new ArrayList<>();
        supplierList = new ArrayList<>();

        branchType = new TypeToken<FilterListPojo>() {
        }.getType();
        subpartyListType = new TypeToken<com.syber.ssspltd.response.PendingOrdBranchRespo.Sup_PartyRespo.FilterListPojo>() {
        }.getType();
        supList = new TypeToken<com.syber.ssspltd.response.PendingOrdBranchRespo.SupplierRespo.FilterListPojo>() {
        }.getType();


        if (Lazy.haveNetworkConnection(mContext)) {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.getStringExtra("formDate") != null || intent.getStringExtra("todate") != null) {
                    GetPendingOrderReport(intent.getStringExtra("branch"),
                            intent.getStringExtra("subparty"), intent.getStringExtra("brand")
                            , intent.getStringExtra("formDate"), intent.getStringExtra("todate"), dbNAME, true);
                } else {
                    GetPendingOrderReport(banch, subparty, supplier, form_date, to_Date, dbNAME, false);
                }
            } else {

            }
        } else {
            networkConnetion3(mContext);
        }

        BranchDetail("BRANCH");
        SubpartyDetail("SUBPARTY");
        SupplerDetail("SUPPLIER");

    }

    private void GetPendingOrderReport(String branch, String subParty, String supplier, String form_Date, String to_Date, String db_name, boolean isisFilterApplied) {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_PENDING_ORDER_REPORT, response -> {
//                    Log.e("Data", response);
            Log.i("TaG", "URL1 ------->" + GET_PENDING_ORDER_REPORT);
            Log.i("TaG", "response ------->" + response);
            PendingOrderPoojo pojo = new Gson().fromJson(response, listType);
            pendingOrderDetails.clear();
            try {
                if (pojo.getResponseStatus()) {
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    binding.includeProgress.noData.setVisibility(View.GONE);
                    pendingOrderDetails.addAll(pojo.getPendingOrderReportResult());
                    pendingOrdertAdapter.notifyDataSetChanged();

                    StartDate_filter = pojo.getDefaultStartDate();
                    Enddate_filter = pojo.getDefaultEndDate();
                    // Enddate_filter = pojo.getEnddate();
                    SharedPref.write(SharedPref.END_DATE, pojo.getEnddate());
                    SharedPref.write(SharedPref.START_DATE, pojo.getStartDate());
                    if (!isisFilterApplied) {
                        binding.tool.textDate.setVisibility(View.VISIBLE);
                        binding.tool.textDate.setText(pojo.getDefaultStartDate() + " To " + pojo.getDefaultEndDate());
                    } else {
                        binding.tool.textDate.setVisibility(View.VISIBLE);
                        binding.tool.textDate.setText(form_Date + " To " + to_Date);
                    }

                    Log.e("dateFilter", pojo.getStartDate());


                    SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
                    Date newDateFilter = null;
                    Date newDateFilter_to = null;
                    try {
                        newDateFilter = date.parse(StartDate_filter);
                        newDateFilter_to = date.parse(Enddate_filter);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    date = new SimpleDateFormat("dd/MM/yyyy");
                    StartDate_filter = date.format(newDateFilter);
                    Enddate_filter = date.format(newDateFilter_to);
                } else {
                    backImage3.setVisibility(View.GONE);
                    if (!isisFilterApplied) {
                        binding.tool.textDate.setVisibility(View.VISIBLE);
                        binding.tool.textDate.setText(pojo.getDefaultStartDate() + " To " + pojo.getDefaultEndDate());
                    } else {
                        binding.tool.textDate.setVisibility(View.VISIBLE);
                        binding.tool.textDate.setText(form_Date + " To " + to_Date);
                    }
//
                    StartDate_filter = pojo.getDefaultStartDate();
                    Enddate_filter = pojo.getDefaultEndDate();
                    // Enddate_filter = pojo.getEnddate();
                    SharedPref.write(SharedPref.END_DATE, pojo.getEnddate());
                    SharedPref.write(SharedPref.START_DATE, pojo.getStartDate());
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    binding.includeProgress.noData.setVisibility(View.VISIBLE);
                    pendingOrdertAdapter.notifyDataSetChanged();
                    AlertUtil.responseElse(mContext, "GetPendingOrderReport ", pojo.getResponseMessage() + "");
                    //     Toast.makeText(mContext, pojo.getResponseMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                AlertUtil.responseExecption(mContext, "GetPendingOrderReport ", e.toString());
            }
        }, error -> {
            try {
                Constants.convertByteToString(mContext, "GetPendingOrderReport ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
//                    binding.includeProgress.progress.setVisibility(View.GONE);
//                    binding.includeProgress.noData.setVisibility(View.VISIBLE);

        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"PartyCode\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"Status\":\"" + "PENDING" + "\",\"FromDate\":\"" + form_Date + "\",\"ToDate\":\"" + to_Date + "\",\"Branch\":\"" + branch + "\",\"Subparty\":\"" + subParty + "\",\"SUPPLIERS\":\"" + supplier + "\",\"DBNAME\":\"" + db_name + "\"}";
                Log.e("str", str);
                Log.i("TaG", "request ---=-=-=" + str);
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
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filterDialog2() {
        isFilterShowing = true;
        dialog = new Dialog(mContext, R.style.AppTheme);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogThemeUpDown;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pending_fitter_dailog);

        View rootView = dialog.findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(
                    v.getPaddingLeft(),
                    systemBars.top,      // ✅ TOP for X button
                    v.getPaddingRight(),
                    systemBars.bottom    // ✅ BOTTOM for Apply button
            );
            return insets;
        });
//        dialog.setCancelable(false);
        new Handler().post(() -> {
            filterStack.clear();
            // getFilters(FilterType.CLEAR,true);
            getFilters(FilterTypePendingOrder.PENDING_ORDER);
        });


        pendingFilter_Branch = dialog.findViewById(R.id.pendingFilter_Branch);
        pendingFilter_Date = dialog.findViewById(R.id.pendingFilter_Date);
        pendingFilter_SubParty = dialog.findViewById(R.id.pendingFilter_SubParty);
        pendingFilter_SuppNikName = dialog.findViewById(R.id.pendingFilter_SuppNikName);
        pending_Recy = dialog.findViewById(R.id.pending_Recy);
        progressBar = dialog.findViewById(R.id.progress);
        llRange = dialog.findViewById(R.id.ll_price_range);
        pendingFilter_Date.setBackgroundColor(getResources().getColor(R.color.light_pink));
        formDate = dialog.findViewById(R.id.stockDate);
        todate = dialog.findViewById(R.id.stock_ToDate);
        nodata = dialog.findViewById(R.id.no_data);

        countbranch = dialog.findViewById(R.id.countbranch);
        countsub_party = dialog.findViewById(R.id.countsub_party);
        countbrand = dialog.findViewById(R.id.countbrand);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        simpleDateFormat2 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

//        if (Count.equals("") || Count2.equals("") || Count3.equals("")) {
//            countbrand.setText("0");
//            countbranch.setText("0");
//            countsub_party.setText("0");
//        } else {
//            countbranch.setText(Count);
//            countsub_party.setText(Count2);
//            countbrand.setText(Count3);
//        }

        if (form_date.equals("null") && to_Date.equals("null")) {
            formDate.setText(StartDate_filter);
            todate.setText(Enddate_filter);
        } else {
            SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
            Date newDate = null;
            Date newDate1 = null;
            try {
                newDate = spf.parse(form_date);
                newDate1 = spf.parse(to_Date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat("dd/MM/yyyy");
            //spf = new SimpleDateFormat("dd/MM/yyyy");
            form_date = spf.format(newDate);
            to_Date = spf.format(newDate1);
            formDate.setText(form_date);
            todate.setText(to_Date);
        }


        formDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = "from";
                // StartDate_filter = ledgerDate.getText().toString();
                if (SharedPref.read(SharedPref.FY_StartDate, "").equals("")) {
                    StartDate_filter = formDate.getText().toString();
                } else {
                    StartDate_filter = SharedPref.read(SharedPref.FY_StartDate, "");

                }
                Log.e("startDate", SharedPref.read(SharedPref.FY_StartDate, ""));
                if (!SharedPref.read(SharedPref.selected_default_yr, "").equals("2023-24"))
                    Enddate_filter = SharedPref.read(SharedPref.FY_EndDate, "");
                else {
                    Enddate_filter = CurrentDateTime.getCurrentDateDDMMYYY();
                }
                // StartDate_filter = ledgerDate.getText().toString();

                //Enddate_filter = ledger_ToDate.getText().toString();
                String[] items1 = StartDate_filter.split("/");
                String[] items2 = Enddate_filter.split("/");
                String yy = items1[2];
                String mm = items1[1];
                String dd = items1[0];
                String yy1 = items2[2];
                String mm2 = items2[1];
                String dd3 = items2[0];
                showDate(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), Integer.parseInt(yy1), Integer.parseInt(mm2) - 1, Integer.parseInt(dd3), R.style.NumberPickerStyle);
                filterStack.clear();
                filterChangedPending(FilterTypePendingOrder.DATE);
                getFilters(FilterTypePendingOrder.CLEAR);
            }
        });
        todate.setOnClickListener(v -> {
            Log.e("dare", SharedPref.read(SharedPref.FY_StartDate, "") + "---" + SharedPref.read(SharedPref.selected_default_yr, ""));
            flag = "to";
            // StartDate_filter = "01/04/2020";
            StartDate_filter = formDate.getText().toString();
            //  StartDate_filter = ledgerDate.getText().toString();
            //  Enddate_filter = ledger_ToDate.getText().toString();
            if (!SharedPref.read(SharedPref.selected_default_yr, "").equals("2023-24")) {
                Enddate_filter = SharedPref.read(SharedPref.FY_EndDate, "");
                // Toast.makeText(mContext, "!if", Toast.LENGTH_SHORT).show();
            } else {
                Enddate_filter = CurrentDateTime.getCurrentDateDDMMYYY();
                //
                //
                //Toast.makeText(mContext, "else", Toast.LENGTH_SHORT).show();
            }

            // Enddate_filter =CurrentDateTime.getCurrentDateDDMMYYY();
            String[] items1 = StartDate_filter.split("/");
            String[] items2 = Enddate_filter.split("/");
            String yy = items1[2];
            String mm = items1[1];
            String dd = items1[0];
            String yy1 = items2[2];
            String mm2 = items2[1];
            String dd3 = items2[0];
            showDate2(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), Integer.parseInt(yy1), Integer.parseInt(mm2) - 1, Integer.parseInt(dd3), R.style.NumberPickerStyle);
            filterStack.clear();
            filterChangedPending(FilterTypePendingOrder.DATE);
            getFilters(FilterTypePendingOrder.CLEAR);
        });

        ImageView cancel = dialog.findViewById(R.id.cancle_i);
        TextView clearAll = dialog.findViewById(R.id.clear_all);
        clearAll.setOnClickListener(v -> {
            isFilterShowing = false;
            filterStack.clear();
            // ledgerDate.setText(SharedPref.read(SharedPref.START_DATE, ""));
            // ledger_ToDate.setText(SharedPref.read(SharedPref.END_DATE, ""));
            countbrand.setText("0");
            countsub_party.setText("0");
            countbranch.setText("0");
            if (SharedPref.read(SharedPref.selected_default_yr, "").equals("23-24")) {
                formDate.setText("01/04/2023");
                todate.setText(CurrentDateTime.getCurrentDateDDMMYYY());
            } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("22-23")) {
                formDate.setText("01/04/2022");
                todate.setText("31/03/2023");
            } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("21-22")) {
                formDate.setText("01/04/2021");
                todate.setText("31/03/2022");
            } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("20-21")) {
                formDate.setText("01/04/2020");
                todate.setText("31/03/2021");
            } else {
                formDate.setText("01/04/2023");
                todate.setText(CurrentDateTime.getCurrentDateDDMMYYY());
            }
            getFilters(FilterTypePendingOrder.CLEAR);
        });
        TextView apply = dialog.findViewById(R.id.apply);
        cancel.setOnClickListener(v -> {
            isFilterShowing = false;
            branch_List.forEach(item -> item.setSelected(false));
            subPartyList.forEach(item -> item.setSelected(false));
            brandList.forEach(item -> item.setSelected(false));
            Count = countbranch.getText().toString();
            Count2 = countsub_party.getText().toString();
            Count3 = countbrand.getText().toString();
            dialog.dismiss();
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                isFilterShowing = false;
                //Brand
                StartDate_filter = formDate.getText().toString();
                Enddate_filter = todate.getText().toString();
                Count = countbranch.getText().toString();
                Count2 = countsub_party.getText().toString();
                Count3 = countbrand.getText().toString();

                SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
                Date newDate = null;
                Date newDate1 = null;
                try {
                    newDate = spf.parse(StartDate_filter);
                    newDate1 = spf.parse(Enddate_filter);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                spf = new SimpleDateFormat("dd/MM/yyyy");
                StartDate_filter = spf.format(newDate);
                Enddate_filter = spf.format(newDate1);
                List<Branch> isSelected = branch_List.stream().filter(p -> p.isSelected()).collect(Collectors.toList());

                String branch_array;
                if (isSelected.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    branch_array = new Gson().toJson(isSelected);
                    try {
                        JSONArray jsonArray = new JSONArray(branch_array);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject objects = jsonArray.getJSONObject(i);
                            String name = objects.getString("BranchName");
                            sb.append(name + ",");
                            Log.e("BranchName", name);
                        }
                        String sbb = sb.toString();
                        banch = sbb;
                        Log.e("sbb", sbb);
                    } catch (Exception ignored) {

                    }
                } else {
                    banch = "null";
                }
                List<SubParty> isSelected1 = subPartyList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
                String subPartyArray;

                if (isSelected1.size() > 0) {
                    subPartyArray = new Gson().toJson(isSelected1);
                    try {
                        StringBuilder sb = new StringBuilder();
                        JSONArray jsonArray = new JSONArray(subPartyArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject objects = jsonArray.getJSONObject(i);
                            String name = objects.getString("SubPartyName");
                            sb.append(name + ",");
                            Log.e("subparty_name", name);
                        }
                        String sbb = sb.toString();
                        subparty = sbb;
                        Log.e("sub_partys", sbb);
                    } catch (Exception e) {

                    }
                } else {
                    subparty = "null";
                }

                List<Brand> isSelected2 = brandList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
                String brand_array;
                if (isSelected2.size() > 0) {
                    brand_array = new Gson().toJson(isSelected2);
                    try {
                        StringBuilder sb = new StringBuilder();
                        JSONArray jsonArray = new JSONArray(brand_array);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject objects = jsonArray.getJSONObject(i);
                            String name = objects.getString("BrandName");
                            if (name.equals("")) {
                                sb.append(name + "NA,");
                                Log.e("brand_name", name);
                            } else {
                                sb.append(name + ",");
                                Log.e("brand_name", name);
                            }

                        }
                        String sbb = sb.toString();
                        supplier = sbb;
                        Log.e("brand_name", sbb);
                    } catch (Exception e) {

                    }
                } else {
                    supplier = "null";
                }

                SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date d1 = sdformat.parse(formDate.getText().toString());
                    Date d2 = sdformat.parse(todate.getText().toString());
                    if (d1.compareTo(d2) < 0 || d1.compareTo(d2) == 0) {
                        startActivity(new Intent(mContext, PendingOrderActivity.class)
                                .putExtra("formDate", formDate.getText().toString())
                                .putExtra("todate", todate.getText().toString())
                                .putExtra("branch", banch)
                                .putExtra("subparty", subparty)
                                .putExtra("brand", supplier));
                        finish();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(mContext, "From Date छोटी होनी चाहिए To Date से", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


//                GetPendingOrderReport(banch, subparty, supplier, form_date, to_Date, dbNAME);
//                dialog.dismiss();
            }
        });

        isDatePressed = true;
        pendingFilter_Date.setBackground(getResources().getDrawable(R.drawable.selected_button));
        pendingFilter_Date.setTextColor(getResources().getColor(R.color.white));


        pendingFilter_Date.setOnClickListener(v -> {
            isDatePressed = true;
            pendingFilter_Date.setBackground(getResources().getDrawable(R.drawable.selected_button));
            pendingFilter_Date.setTextColor(getResources().getColor(R.color.white));
            if (isBranchPlace || isSubPartyPlace || isSuppNPlace) {
                llRange.setVisibility(View.VISIBLE);
                pending_Recy.setVisibility(View.GONE);
                countsub_party.setTextColor(getResources().getColor(R.color.black));
                countbrand.setTextColor(getResources().getColor(R.color.black));
                countbranch.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_Branch.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_Branch.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_SubParty.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_SuppNikName.setTextColor(getResources().getColor(R.color.black));
                isBranchPlace = false;
                isSubPartyPlace = false;
                isSuppNPlace = false;
            }
        });

        pendingFilter_Branch.setOnClickListener(v -> {
            isBranchPlace = true;
            pendingFilter_Branch.setBackground(getResources().getDrawable(R.drawable.selected_button));
            pendingFilter_Branch.setTextColor(getResources().getColor(R.color.white));
            keyTypeList = "BRANCH";
            Log.e("filter", keyTypeList);
            if (isDatePressed || isSubPartyPlace || isSuppNPlace) {
                llRange.setVisibility(View.GONE);
                pending_Recy.setVisibility(View.VISIBLE);
                countsub_party.setTextColor(getResources().getColor(R.color.black));
                countbrand.setTextColor(getResources().getColor(R.color.black));
                countbranch.setTextColor(getResources().getColor(R.color.white));
                pendingFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_Date.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_SubParty.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_SuppNikName.setTextColor(getResources().getColor(R.color.black));
                ;
                linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                pending_Recy.setLayoutManager(linearLayoutManager);
                filterBranchAdap = new FilterBranchAdap(mContext, branch_List, this);
                pending_Recy.setAdapter(filterBranchAdap);
                filterBranchAdap.notifyDataSetChanged();
                isDatePressed = false;
                isSubPartyPlace = false;
                isSuppNPlace = false;
                getFilters(FilterTypePendingOrder.BRANCH);
            }
        });
        pendingFilter_SubParty.setOnClickListener(v -> {
            isSubPartyPlace = true;
            pendingFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.selected_button));
            pendingFilter_SubParty.setTextColor(getResources().getColor(R.color.white));
            keyTypeList = "SUBPARTY";
            Log.e("filter", keyTypeList);
            if (isDatePressed || isBranchPlace || isSuppNPlace || isTransportPlace) {
                pending_Recy.setVisibility(View.VISIBLE);
                llRange.setVisibility(View.GONE);
                countsub_party.setTextColor(getResources().getColor(R.color.white));
                countbrand.setTextColor(getResources().getColor(R.color.black));
                countbranch.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_Date.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_Branch.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_Branch.setTextColor(getResources().getColor(R.color.black));
                ;
                pendingFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_SuppNikName.setTextColor(getResources().getColor(R.color.black));
                linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                pending_Recy.setLayoutManager(linearLayoutManager);
                filterSubPartyAdap = new FilterSubPartyAdap(mContext, subPartyList, this);
                pending_Recy.setAdapter(filterSubPartyAdap);
                filterSubPartyAdap.notifyDataSetChanged();
                isDatePressed = false;
                isBranchPlace = false;
                isSuppNPlace = false;

                getFilters(FilterTypePendingOrder.SUB_PARTY);
            }
        });
        pendingFilter_SuppNikName.setOnClickListener(v -> {
            isSuppNPlace = true;
            pendingFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.selected_button));
            pendingFilter_SuppNikName.setTextColor(getResources().getColor(R.color.white));
            keyTypeList = "SUPPLIER";
            Log.e("filter", keyTypeList);
            if (isDatePressed || isBranchPlace || isSubPartyPlace) {
                pending_Recy.setVisibility(View.VISIBLE);
                llRange.setVisibility(View.GONE);
                countsub_party.setTextColor(getResources().getColor(R.color.black));
                countbrand.setTextColor(getResources().getColor(R.color.white));
                countbranch.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_Date.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_SubParty.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_Branch.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_Branch.setTextColor(getResources().getColor(R.color.black));
                linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                pending_Recy.setLayoutManager(linearLayoutManager);
                filterBrandNameAdap = new FilterBrandNameAdap(mContext, brandList, this);
                pending_Recy.setAdapter(filterBrandNameAdap);
                filterBrandNameAdap.notifyDataSetChanged();
                isDatePressed = false;
                isBranchPlace = false;
                isSubPartyPlace = false;
            }
            getFilters(FilterTypePendingOrder.BRAND_NAME);
        });

        dialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                isFilterShowing = false;
                dialog.dismiss();
            }
            return true;
        });
        Window window = dialog.getWindow();
        if (window != null) {
            WindowCompat.setDecorFitsSystemWindows(window, false);
        }
        //   getSIze(getIntent().getStringExtra("d_code"),false);
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getFilters(FilterTypePendingOrder mFilterType) {
        progressBar.setVisibility(View.VISIBLE);
        PendingOrderFilterRequest request;
        request = new PendingOrderFilterRequest(
                formDate.getText().toString(), todate.getText().toString(), "PENDINGORDER",
                SharedPref.read(SharedPref.PARTY_CODE, ""),
                branch_List.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterTypePendingOrder.BRANCH))
                        .collect(Collectors.toList()),
                subPartyList.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterTypePendingOrder.SUB_PARTY))
                        .collect(Collectors.toList()),
                brandList.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterTypePendingOrder.BRAND_NAME))
                        .collect(Collectors.toList()),
                SharedPref.read(SharedPref.DB_NAME, "")
        );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_LIST_NEW, response -> {
            Log.e("Data", response);
            Log.i("TaG", "URL2 ------->" + GET_FILTER_LIST_NEW);
            Log.i("TaG", "response2 ------->" + response);
            PendingOrderPojo pojo = new Gson().fromJson(response, branch_Type);
            if (pojo.getResponseStatus()) {
                progressBar.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
                switch (mFilterType) {
                    case BRANCH:
                        List<Branch> prevAdjustmentList = new ArrayList<>(branch_List);
                        List<Branch> size = prevAdjustmentList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterTypePendingOrder.BRANCH))
                                .collect(Collectors.toList());
                        Log.e("size", size.size() + "");

                        branch_List.clear();
                        if (!filterStack.contains(mFilterType) || pendingOrderPojo == null || pendingOrderPojo.getBranch() == null || pendingOrderPojo.getBranch().isEmpty()) {
                            branch_List.addAll(pojo.getBranch());
                            countbranch.setText("0");
                        } else {
                            countbranch.setText(size.size() + "");
                            pendingOrderPojo.getBranch().forEach(branch -> {
                                prevAdjustmentList.forEach(branch2 -> {
                                    if (branch.getBranchName().equals(branch2.getBranchName())) {
                                        branch.setSelected(branch2.isSelected());
                                    }
                                });
                            });
                            branch_List.addAll(prevAdjustmentList);
                            //  adjustmentTypeList.addAll(ledgerPogo.getAdjustmentType());
                        }
                        filterBranchAdap.notifyDataSetChanged();
                        break;
                    case SUB_PARTY:
                        List<SubParty> prevAccountList = new ArrayList<>(subPartyList);
                        List<SubParty> size1 = prevAccountList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterTypePendingOrder.SUB_PARTY))
                                .collect(Collectors.toList());
                        Log.e("size", size1.size() + "");

                        subPartyList.clear();
                        if (!filterStack.contains(mFilterType) || pendingOrderPojo == null || pendingOrderPojo.getSubParty() == null || pendingOrderPojo.getSubParty().isEmpty()) {
                            subPartyList.addAll(pojo.getSubParty());
                            countsub_party.setText("0");
                        } else {
                            countsub_party.setText(size1.size() + "");
                            Log.e("LedgerActivity", "ZHere");
                            pendingOrderPojo.getSubParty().forEach(subParty -> {
                                prevAccountList.forEach(account1 -> {
                                    if (subParty.getSubPartyName().equals(account1.getSubPartyName())) {
                                        subParty.setSelected(account1.isSelected());
                                    }
                                });
                            });
                            subPartyList.addAll(prevAccountList);
                            // accountTypeList.addAll(ledgerPogo.getAccountType());
                        }
                        filterSubPartyAdap.notifyDataSetChanged();
                        break;
                    case BRAND_NAME:
                        List<Brand> prevEntryList = new ArrayList<>(brandList);
                        Log.e("prevEntryList", new Gson().toJson(prevEntryList));
                        List<Brand> size11 = prevEntryList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterTypePendingOrder.BRAND_NAME))
                                .collect(Collectors.toList());
                        Log.e("entryTypeListbfrclear", new Gson().toJson(brandList));
                        brandList.clear();
                        Log.e("justaftrtclear", new Gson().toJson(brandList));
                        if (!filterStack.contains(mFilterType) || pendingOrderPojo == null || pendingOrderPojo.getBrand() == null || pendingOrderPojo.getBrand().isEmpty()) {
                            brandList.addAll(pojo.getBrand());
                            countbrand.setText("0");
                            Log.e("entryTypestaftrclearif", new Gson().toJson(brandList));
                        } else {
                            countbrand.setText(size11.size() + "");
                            Log.e("ledgerPogo", new Gson().toJson(pendingOrderPojo.getBrand()));
                            pendingOrderPojo.getBrand().forEach(brand -> {
                                prevEntryList.forEach(brand1 -> {
                                    if (brand.getBrandName().equals(brand1.getBrandName())) {
                                        brand.setSelected(brand1.isSelected());
                                    }
                                });
                            });
                            brandList.addAll(prevEntryList);
                            // entryTypeList.addAll(ledgerPogo.getEntryType());
                            Log.e("entryTypestaftrclearels", new Gson().toJson(brandList));
                        }
                        filterBrandNameAdap.notifyDataSetChanged();

                        break;
                    case CLEAR:
                        branch_List.clear();
                        branch_List.addAll(pojo.getBranch());
                        subPartyList.clear();
                        subPartyList.addAll(pojo.getSubParty());
                        brandList.clear();
                        brandList.addAll(pojo.getBrand());
                        filterBranchAdap.notifyDataSetChanged();
                        filterBrandNameAdap.notifyDataSetChanged();
                        filterSubPartyAdap.notifyDataSetChanged();

                    case PENDING_ORDER:
                        pendingOrderPojo = pojo;
                        break;
                }
            } else {
                progressBar.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                branch_List.clear();
                subPartyList.clear();
                brandList.clear();
                countbrand.setText("0");
                countbranch.setText("0");
                countsub_party.setText("0");
                filterBranchAdap.notifyDataSetChanged();
                filterSubPartyAdap.notifyDataSetChanged();
                filterBrandNameAdap.notifyDataSetChanged();
            }
            // }
        }, error -> {
            progressBar.setVisibility(View.GONE);
            // nodata.setVisibility(View.VISIBLE);
            branch_List.clear();
            subPartyList.clear();
            brandList.clear();
            countbrand.setText("0");
            countbranch.setText("0");
            countsub_party.setText("0");
            filterBranchAdap.notifyDataSetChanged();
            filterSubPartyAdap.notifyDataSetChanged();
            filterBrandNameAdap.notifyDataSetChanged();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = new Gson().toJson(request);
                Log.e("str", str);
                Log.e("TaG", "request 2 -=-=-==- " + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));

                Log.i("TaG", "GetCourierReport header : URl " + GET_COURIER_REPORT + " " + "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    public void filterDialog() {
        isFilterShowing = true;
        final Dialog dialog = new Dialog(mContext, R.style.AppTheme);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogThemeUpDown;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pending_fitter_dailog);
        dialog.setCancelable(false);


        pendingFilter_Branch = dialog.findViewById(R.id.pendingFilter_Branch);
        pendingFilter_Date = dialog.findViewById(R.id.pendingFilter_Date);
        pendingFilter_SubParty = dialog.findViewById(R.id.pendingFilter_SubParty);
        pendingFilter_SuppNikName = dialog.findViewById(R.id.pendingFilter_SuppNikName);
        pending_Recy = dialog.findViewById(R.id.pending_Recy);
        llRange = dialog.findViewById(R.id.ll_price_range);
        pendingFilter_Date.setBackgroundColor(getResources().getColor(R.color.light_pink));
        formDate = dialog.findViewById(R.id.stockDate);
        todate = dialog.findViewById(R.id.stock_ToDate);

        countbranch = dialog.findViewById(R.id.countbranch);
        countsub_party = dialog.findViewById(R.id.countsub_party);
        countbrand = dialog.findViewById(R.id.countbrand);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        simpleDateFormat2 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        if (Count.equals("") || Count2.equals("") || Count3.equals("")) {
            countbrand.setText("0");
            countbranch.setText("0");
            countsub_party.setText("0");
        } else {
            countbranch.setText(Count);
            countsub_party.setText(Count2);
            countbrand.setText(Count3);
        }

        if (form_date.equals("null") && to_Date.equals("null")) {
            formDate.setText(SharedPref.read(SharedPref.FY_StartDate, ""));
            todate.setText(SharedPref.read(SharedPref.FY_EndDate, ""));
        } else {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy/MM/dd");
            Date newDate = null;
            Date newDate1 = null;
            try {
                newDate = spf.parse(form_date);
                newDate1 = spf.parse(to_Date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat("dd/MM/yyyy");
            //spf = new SimpleDateFormat("dd/MM/yyyy");
            form_date = spf.format(newDate);
            to_Date = spf.format(newDate1);
            formDate.setText(form_date);
            todate.setText(to_Date);
        }


        formDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = "from";
                form_date = formDate.getText().toString();
                to_Date = todate.getText().toString();
                String[] items1 = form_date.split("/");
                String[] items2 = to_Date.split("/");
                String yy = items1[2];
                String mm = items1[1];
                String dd = items1[0];
                String yy1 = items2[2];
                String mm2 = items2[1];
                String dd3 = items2[0];
                showDate(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), Integer.parseInt(yy1), Integer.parseInt(mm2) - 1, Integer.parseInt(dd3), R.style.NumberPickerStyle);
            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = "to";
                form_date = formDate.getText().toString();
                to_Date = todate.getText().toString();
                String[] items1 = form_date.split("/");
                String[] items2 = to_Date.split("/");
                String yy = items1[2];
                String mm = items1[1];
                String dd = items1[0];
                String yy1 = items2[2];
                String mm2 = items2[1];
                String dd3 = items2[0];
                showDate2(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), Integer.parseInt(yy1), Integer.parseInt(mm2) - 1, Integer.parseInt(dd3), R.style.NumberPickerStyle);
            }
        });

        ImageView cancel = dialog.findViewById(R.id.cancle_i);
        TextView clearAll = dialog.findViewById(R.id.clear_all);
        clearAll.setOnClickListener(v -> {
            isFilterShowing = false;
            // formDate.setText(SharedPref.read(SharedPref.FY_StartDate, ""));
            //  todate.setText(SharedPref.read(SharedPref.FY_EndDate, ""));
            countbranch.setText("0");
            countsub_party.setText("0");
            countbrand.setText("0");
            filterStack.clear();
            // ledgerDate.setText(SharedPref.read(SharedPref.START_DATE, ""));
            // ledger_ToDate.setText(SharedPref.read(SharedPref.END_DATE, ""));
            Log.e("selected_default_yr", SharedPref.read(SharedPref.selected_default_yr, ""));


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                getFilters(FilterTypePendingOrder.CLEAR);
            }


        });
        TextView apply = dialog.findViewById(R.id.apply);
        cancel.setOnClickListener(v -> {
            isFilterShowing = false;
            for (int i = 0; i < branchList.size(); i++) {
                branchList.get(i).setSelected(false);
            }
            for (int i = 0; i < subpartyList.size(); i++) {
                subpartyList.get(i).setSelected(false);
            }
            for (int i = 0; i < supplierList.size(); i++) {
                supplierList.get(i).setSelected(false);
            }
            Count = countbranch.getText().toString();
            Count2 = countsub_party.getText().toString();
            Count3 = countbrand.getText().toString();
            dialog.dismiss();
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                isFilterShowing = false;
                //Brand
                form_date = formDate.getText().toString();
                to_Date = todate.getText().toString();
                Count = countbranch.getText().toString();
                Count2 = countsub_party.getText().toString();
                Count3 = countbrand.getText().toString();

                SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
                Date newDate = null;
                Date newDate1 = null;
                try {
                    newDate = spf.parse(form_date);
                    newDate1 = spf.parse(to_Date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                spf = new SimpleDateFormat("yyyy/MM/dd");
                form_date = spf.format(newDate);
                to_Date = spf.format(newDate1);
                List<FilterListResult> isSelected = branchList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());

                String brand_array;
                if (isSelected.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    brand_array = new Gson().toJson(isSelected);
                    try {
                        JSONArray jsonArray = new JSONArray(brand_array);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject objects = jsonArray.getJSONObject(i);
                            String name = objects.getString("FilterName");
                            sb.append(name + ",");
                            Log.e("brand_name", name);
                        }
                        String sbb = sb.toString();
                        banch = sbb;
                        Log.e("sbb", sbb);
                    } catch (Exception ignored) {

                    }
                } else {
                    banch = "null";
                }
                List<com.syber.ssspltd.response.PendingOrdBranchRespo.Sup_PartyRespo.FilterListResult> isSelected1 = subpartyList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
                String subPartyArray;

                if (isSelected1.size() > 0) {
                    subPartyArray = new Gson().toJson(isSelected1);
                    try {
                        StringBuilder sb = new StringBuilder();
                        JSONArray jsonArray = new JSONArray(subPartyArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject objects = jsonArray.getJSONObject(i);
                            String name = objects.getString("FilterName");
                            sb.append(name + ",");
                            Log.e("subparty_name", name);
                        }
                        String sbb = sb.toString();
                        subparty = sbb;
                        Log.e("sub_partys", sbb);
                    } catch (Exception e) {

                    }
                } else {
                    subparty = "null";
                }

                List<com.syber.ssspltd.response.PendingOrdBranchRespo.SupplierRespo.FilterListResult> isSelected2 = supplierList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
                String supplier_array;
                if (isSelected2.size() > 0) {
                    supplier_array = new Gson().toJson(isSelected2);
                    try {
                        StringBuilder sb = new StringBuilder();
                        JSONArray jsonArray = new JSONArray(supplier_array);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject objects = jsonArray.getJSONObject(i);
                            String name = objects.getString("FilterName");
                            sb.append(name + ",");
                            Log.e("ssupplier_name", name);
                        }
                        String sbb = sb.toString();
                        supplier = sbb;
                        Log.e("supplier_list", sbb);
                    } catch (Exception e) {

                    }
                } else {
                    supplier = "null";
                }


                GetPendingOrderReport(banch, subparty, supplier, form_date, to_Date, dbNAME, true);
                dialog.dismiss();
            }
        });

        isDatePressed = true;
        pendingFilter_Date.setBackground(getResources().getDrawable(R.drawable.selected_button));
        pendingFilter_Date.setTextColor(getResources().getColor(R.color.white));


        pendingFilter_Date.setOnClickListener(v -> {
            isDatePressed = true;
            pendingFilter_Date.setBackground(getResources().getDrawable(R.drawable.selected_button));
            pendingFilter_Date.setTextColor(getResources().getColor(R.color.white));
            if (isBranchPlace || isSubPartyPlace || isSuppNPlace) {
                llRange.setVisibility(View.VISIBLE);
                pending_Recy.setVisibility(View.GONE);
                countsub_party.setTextColor(getResources().getColor(R.color.black));
                countbrand.setTextColor(getResources().getColor(R.color.black));
                countbranch.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_Branch.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_Branch.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_SubParty.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_SuppNikName.setTextColor(getResources().getColor(R.color.black));
                isBranchPlace = false;
                isSubPartyPlace = false;
                isSuppNPlace = false;
            }
        });

        pendingFilter_Branch.setOnClickListener(v -> {
            isBranchPlace = true;
            pendingFilter_Branch.setBackground(getResources().getDrawable(R.drawable.selected_button));
            pendingFilter_Branch.setTextColor(getResources().getColor(R.color.white));
            keyTypeList = "BRANCH";
            Log.e("filter", keyTypeList);
            if (isDatePressed || isSubPartyPlace || isSuppNPlace) {
                llRange.setVisibility(View.GONE);
                pending_Recy.setVisibility(View.VISIBLE);
                countsub_party.setTextColor(getResources().getColor(R.color.black));
                countbrand.setTextColor(getResources().getColor(R.color.black));
                countbranch.setTextColor(getResources().getColor(R.color.white));
                pendingFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_Date.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_SubParty.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_SuppNikName.setTextColor(getResources().getColor(R.color.black));
                ;
                linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                pending_Recy.setLayoutManager(linearLayoutManager);
                branchAdapter = new BranchAdapter(mContext, branchList);
                pending_Recy.setAdapter(branchAdapter);
                branchAdapter.notifyDataSetChanged();
                isDatePressed = false;
                isSubPartyPlace = false;
                isSuppNPlace = false;
            }
        });
        pendingFilter_SubParty.setOnClickListener(v -> {
            isSubPartyPlace = true;
            pendingFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.selected_button));
            pendingFilter_SubParty.setTextColor(getResources().getColor(R.color.white));
            keyTypeList = "SUBPARTY";
            Log.e("filter", keyTypeList);
            if (isDatePressed || isBranchPlace || isSuppNPlace || isTransportPlace) {
                pending_Recy.setVisibility(View.VISIBLE);
                llRange.setVisibility(View.GONE);
                countsub_party.setTextColor(getResources().getColor(R.color.white));
                countbrand.setTextColor(getResources().getColor(R.color.black));
                countbranch.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_Date.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_Branch.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_Branch.setTextColor(getResources().getColor(R.color.black));
                ;
                pendingFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_SuppNikName.setTextColor(getResources().getColor(R.color.black));
                linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                pending_Recy.setLayoutManager(linearLayoutManager);
                sup_partyAdapter = new Sup_PartyAdapter(mContext, subpartyList);
                pending_Recy.setAdapter(sup_partyAdapter);
                sup_partyAdapter.notifyDataSetChanged();
                isDatePressed = false;
                isBranchPlace = false;
                isSuppNPlace = false;
            }
        });
        pendingFilter_SuppNikName.setOnClickListener(v -> {
            isSuppNPlace = true;
            pendingFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.selected_button));
            pendingFilter_SuppNikName.setTextColor(getResources().getColor(R.color.white));
            keyTypeList = "SUPPLIER";
            Log.e("filter", keyTypeList);
            if (isDatePressed || isBranchPlace || isSubPartyPlace) {
                pending_Recy.setVisibility(View.VISIBLE);
                llRange.setVisibility(View.GONE);
                countsub_party.setTextColor(getResources().getColor(R.color.black));
                countbrand.setTextColor(getResources().getColor(R.color.white));
                countbranch.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_Date.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_SubParty.setTextColor(getResources().getColor(R.color.black));
                pendingFilter_Branch.setBackground(getResources().getDrawable(R.drawable.text_bg));
                pendingFilter_Branch.setTextColor(getResources().getColor(R.color.black));
                linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                pending_Recy.setLayoutManager(linearLayoutManager);
                supplierAdapter = new SupplierAdapter(mContext, supplierList);
                pending_Recy.setAdapter(supplierAdapter);
                supplierAdapter.notifyDataSetChanged();
                isDatePressed = false;
                isBranchPlace = false;
                isSubPartyPlace = false;
            }
        });

        //   getSIze(getIntent().getStringExtra("d_code"),false);
        dialog.show();
    }


    private void BranchDetail(final String keyType) {
//        final ProgressDialog progressBar = new ProgressDialog(mContext);
//        progressBar.setTitle("Fetching Data");
//        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_DETAIL_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Data", response);
                        Log.i("TaG", "URL3 ------->" + GET_FILTER_DETAIL_LIST);
                        Log.i("TaG", "response3 ------->" + response);
//                        progressBar.dismiss();
//                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        FilterListPojo pojo = new Gson().fromJson(response, branchType);
                        if (pojo.getResponseStatus()) {
                            branchList.clear();
                            branchList.addAll(pojo.getFilterListResult());
                            //filterAdapter.notifyDataSetChanged();
                        } else {
                            //   Toast.makeText(mContext, pojo.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressBar.cancel();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DATAKEY\":\"" + keyType + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FILTERTYPE\":\"" + "PENDINGORDER" + "\"}";
                Log.e("str", str);
                Log.e("TaG", "request 3 --=-=-=" + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, AUTH_TOKEN));
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void SubpartyDetail(final String keyType) {
//        final ProgressDialog progressBar = new ProgressDialog(mContext);
//        progressBar.setTitle("Fetching Data");
//        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_DETAIL_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Data", response);

                        Log.i("TaG", "URL4 ------->" + GET_FILTER_DETAIL_LIST);
                        Log.i("TaG", "response4 ------->" + response);
//                        progressBar.dismiss();
//                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        com.syber.ssspltd.response.PendingOrdBranchRespo.Sup_PartyRespo.FilterListPojo pojo = new Gson().fromJson(response, subpartyListType);
                        if (pojo.getResponseStatus()) {
                            subpartyList.clear();
                            subpartyList.addAll(pojo.getFilterListResult());
                            ///  subPartyListAdapter.notifyDataSetChanged();
                        } else {
                            // Toast.makeText(mContext, pojo.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressBar.cancel();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DATAKEY\":\"" + keyType + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FILTERTYPE\":\"" + "PENDINGORDER" + "\"}";
                Log.e("str", str);
                Log.e("TaG", "request 4 -=-=-=" + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, AUTH_TOKEN));
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void SupplerDetail(final String keyType) {
//        final ProgressDialog progressBar = new ProgressDialog(mContext);
//        progressBar.setTitle("Fetching Data");
//        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_DETAIL_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Data", response);
                        Log.i("TaG", "URL5 ------->" + GET_FILTER_DETAIL_LIST);
                        Log.i("TaG", "response5 ------->" + response);
//                        progressBar.dismiss();
//                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        com.syber.ssspltd.response.PendingOrdBranchRespo.SupplierRespo.FilterListPojo pojo = new Gson().fromJson(response, supList);
                        if (pojo.getResponseStatus()) {
                            supplierList.clear();
                            supplierList.addAll(pojo.getFilterListResult());
                            ///  subPartyListAdapter.notifyDataSetChanged();
                        } else {
                            //  Toast.makeText(mContext, pojo.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Lazy.networkConnetion(mContext);
//                progressBar.cancel();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DATAKEY\":\"" + keyType + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FILTERTYPE\":\"" + "PENDINGORDER" + "\"}";
                Log.e("str", str);
                Log.e("TaG", "request 5 -=-=-=-=" + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, AUTH_TOKEN));
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        if (flag.equals("from")) {
            formDate.setText(simpleDateFormat.format(calendar.getTime()));
            if (simpleDateFormat.format(calendar.getTime()).equals(CurrentDateTime.getCurrentDateDDMMYYY())) {
                todate.setText(formDate.getText().toString());
            }
        } else if (flag.equals("to")) {
            todate.setText(simpleDateFormat2.format(calendar.getTime()));
        }

    }

    @VisibleForTesting
    void showDate(int year1, int monthOfYear1, int dayOfMonth1, int year2, int monthOfYear2, int dayOfMonth2, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(PendingOrderActivity.this)
                .callback(PendingOrderActivity.this)
                .spinnerTheme(spinnerTheme)
                .minDate(year1 - 1, monthOfYear1, dayOfMonth1)
                .maxDate(year2, monthOfYear2, dayOfMonth2)
                .build()
                .show();

    }

    @VisibleForTesting
    void showDate2(int year1, int monthOfYear1, int dayOfMonth1, int year2, int monthOfYear2, int dayOfMonth2, int spinnerTheme) {
        try {
            new SpinnerDatePickerDialogBuilder()
                    .context(PendingOrderActivity.this)
                    .callback(PendingOrderActivity.this)
                    .spinnerTheme(spinnerTheme)
                    .minDate(year1 - 1, monthOfYear1, dayOfMonth1)
                    .maxDate(year2, monthOfYear2, dayOfMonth2)
                    .build()
                    .show();
        } catch (Exception e) {
            //ledger_ToDate.setText(ledgerDate.getText().toString());
            Toast.makeText(mContext, "आज की date से ज़्यादा नहीं कर सकतें ।", Toast.LENGTH_SHORT).show();
            e.toString();
        }

    }

    @Override
    public void filterChangedPending(FilterTypePendingOrder mFilterType) {
        if (filterStack.contains(mFilterType)) {
            while (filterStack.pop() != mFilterType) {
            }
        }
        filterStack.push(mFilterType);
        Log.e("Seq", filterStack.toString());
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
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        try_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetPendingOrderReport(banch, subparty, supplier, form_date, to_Date, dbNAME, false);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


}