package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.ConstantVariable.AUTH_TOKEN;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_FILTER_DETAIL_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_FILTER_LIST_NEW;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_SALE_REPORT;

import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.CurrentDateTime;
import com.syber.ssspltd.Interface.FilterChangeSaleReport;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterSaleReport.Branch;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterSaleReport.Brand;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterSaleReport.FilterSaleReportRequest;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterSaleReport.SaleReportPojo;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterSaleReport.SubParty;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterSaleReport.Transporter;
import com.syber.ssspltd.R;
import com.syber.ssspltd.SaleReportResponse.SaleReportPoojo;
import com.syber.ssspltd.SaleReportResponse.SaleReportResult;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.FilterAdapter.BranchListAdapter;
import com.syber.ssspltd.adapter.FilterAdapter.SubPartyListAdapter;
import com.syber.ssspltd.adapter.FilterAdapter.SupplierListAdapter;
import com.syber.ssspltd.adapter.FilterAdapter.TransportListAdapter;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.SaleReportFilter.FilterBranch;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.SaleReportFilter.FilterBrnad_NAdap;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.SaleReportFilter.FilterSub_PartyAdap;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.SaleReportFilter.FilterTesnportAdpter;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.SaleReportFilter.FilterTypeSaleReport;
import com.syber.ssspltd.adapter.SaleReportAdapter;
import com.syber.ssspltd.databinding.ActivitySaleReportBinding;
import com.syber.ssspltd.response.BranchListResponse.FilterListPojo;
import com.syber.ssspltd.response.BranchListResponse.FilterListResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONArray;
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
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class SaleReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, FilterChangeSaleReport {
    Context mContext = this;
    SaleReportAdapter saleReportAdapter;
    static List<SaleReportResult> saleReportDetails;
    Type listType;
    LinearLayoutManager linearLayoutManager;
    String keyTypeList = "";
    String flag = "";
    private ActivitySaleReportBinding binding;

    String banch = "null", subparty = "null", supplier = "null", transport = "null", sale_formDate = "null", sale_toDate = "null", dbNAME = SharedPref.read(SharedPref.DB_NAME, "");
    TextView saleFilter_Date, saleFilter_Branch, saleFilter_SubParty, saleFilter_SuppNikName, saleFilter_transport, nodata;
    BranchListAdapter filterAdapter;
    List<FilterListResult> FilterListDetails;
    SubPartyListAdapter subPartyListAdapter;
    SupplierListAdapter supplierListAdapter;
    TransportListAdapter transportListAdapter;
    List<com.syber.ssspltd.response.SubpartyListRespo.FilterListResult> subpartyList;
    List<com.syber.ssspltd.response.SupplierListPojo.FilterListResult> supplierList;
    List<com.syber.ssspltd.response.TransportListRespo.FilterListResult> trnsportList;
    Type listType2, subpartyListType, transList, supList;
    RecyclerView saleBranch_Recy;
    LinearLayout llRange;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormat2;
    TextView saleDate, sale_ToDate;
    String msgShow;
    String Count = "", Count2 = "", Count3 = "", Count4 = "";
    public static TextView countbranch, countsub_party, countbrand, counttransport;
    boolean isDatePressed = false, isbranchPlace = false, isSub_PartyPlace = false, issuppPlace = false, istransportPlace = false;

    boolean isFilterShowing = false;
    FilterBranch filterBranchAdap;
    FilterBrnad_NAdap filterBrnad_nAdap;
    FilterSub_PartyAdap filterSubPartyAdap;
    FilterTesnportAdpter filterTesnportAdpter;

    List<Branch> branchList;
    List<Brand> brandList;
    List<SubParty> subPartyList;
    List<Transporter> transporterList;
    Stack<FilterTypeSaleReport> filterStack;
    Type branchType, brandType, subPartyType, transportType;
    SaleReportPojo saleReport_Pojo;

    String StartDate_filter, Enddate_filter;
    ProgressBar progressBar;
   Dialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySaleReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.supportChat.supportFab.setOnClickListener(v ->
                Lazy.openDialog(mContext));
        SharedPref.init(getApplicationContext());
        saleReportDetails = new ArrayList<>();
        listType = new TypeToken<SaleReportPoojo>() {
        }.getType();

        //new Filter

        filterStack = new Stack<>();
        branchList = new ArrayList<>();
        subPartyList = new ArrayList<>();
        brandList = new ArrayList<>();
        transporterList = new ArrayList<>();

        filterBranchAdap = new FilterBranch(mContext, branchList, this);
        filterBrnad_nAdap = new FilterBrnad_NAdap(mContext, brandList, this);
        filterSubPartyAdap = new FilterSub_PartyAdap(mContext, subPartyList, this);
        filterTesnportAdpter = new FilterTesnportAdpter(mContext, transporterList, this);


        branchType = new TypeToken<SaleReportPojo>() {
        }.getType();
        subPartyType = new TypeToken<SaleReportPojo>() {
        }.getType();
        brandType = new TypeToken<SaleReportPojo>() {
        }.getType();
        transportType = new TypeToken<SaleReportPojo>() {
        }.getType();

        


//new Filter

        if (Lazy.haveNetworkConnection(mContext)){

            Intent intent = getIntent();
            if (intent != null) {
                if (intent.getStringExtra("formDate") != null || intent.getStringExtra("todate") != null) {
                 //   Log.e("isFilterDate_true", intent.getStringExtra("formDate") + "");
                    GetSaleReport(intent.getStringExtra("branch")
                            , intent.getStringExtra("brand"), intent.getStringExtra("subparty"), intent.getStringExtra("transport")
                            , intent.getStringExtra("formDate"), intent.getStringExtra("todate"), dbNAME, true);
                } else {

                 //   Log.e("isFilterDate_false", "isFilterDate_false");
                    GetSaleReport(banch, supplier, subparty, transport, sale_formDate, sale_toDate, dbNAME, false);
                }
            }else {


            }
        }else {
            networkConnetion3(mContext);
        }


        FilterListDetails = new ArrayList<>();
        subpartyList = new ArrayList<>();
        trnsportList = new ArrayList<>();
        supplierList = new ArrayList<>();

        listType2 = new TypeToken<FilterListPojo>() {
        }.getType();
        subpartyListType = new TypeToken<com.syber.ssspltd.response.SubpartyListRespo.FilterListPojo>() {
        }.getType();
        transList = new TypeToken<com.syber.ssspltd.response.TransportListRespo.FilterListPojo>() {
        }.getType();
        supList = new TypeToken<com.syber.ssspltd.response.SupplierListPojo.FilterListPojo>() {
        }.getType();

        saleReportAdapter = new SaleReportAdapter(mContext, saleReportDetails);
        binding.saleReportRecy.setAdapter(saleReportAdapter);

        binding.l.back3.setImageDrawable(ContextCompat.getDrawable(SaleReportActivity.this, R.drawable.ic_baseline_keyboard_backspace));
        binding.l.back3.setOnClickListener(v -> onBackPressed());

        BranchDetail("BRANCH");
        SubpartyDetail("SUBPARTY");
        SupplerDetail("SUPPLIER");
        TransportDetail("TRANSPORT");

        binding.l.download.setImageDrawable(ContextCompat.getDrawable(SaleReportActivity.this, R.drawable.ic_filter));
        binding.l.download.setOnClickListener(v -> {
            if (!isFilterShowing) {
                filterDialog2();
                //filterDialog();
            } else {

            }
        });
        binding.l.back2.setText("SALE REPORT");
    }
    private void showCustomDialog() {
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.sale_report_dilogs, (RelativeLayout)
                findViewById(R.id.sale_dialogs));
        CheckBox ch;
        Button sale_submit;
        ch = dialogView.findViewById(R.id.sale_dialog);
        sale_submit = dialogView.findViewById(R.id.sale_submit);

//        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.BottomSheetDialogTheme2);
        final AlertDialog.Builder builder = new AlertDialog.Builder(SaleReportActivity.this, R.style.RoundedDialog);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                }
            }
        });
        msgShow = ch.getText().toString();

        sale_submit.setOnClickListener(v -> {

            SharedPref.write(SharedPref.ON, "yes");
            alertDialog.dismiss();
        });

        alertDialog.show();
    }

    private void GetSaleReport(String branch, String supplier, String subparty, String transport, String form_date, String to_date, String db_name,boolean isFillterApplied) {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_SALE_REPORT,
                response -> {
                    Log.e("GetSaleReportData", response);
                    saleReportDetails.clear();
                    SaleReportPoojo pojo = new Gson().fromJson(response, listType);
                    try {
                        if (pojo.getResponseStatus()) {
                            binding.includeProgress.progress.setVisibility(View.GONE);
                            binding.includeProgress.noData.setVisibility(View.GONE);
                            saleReportDetails.addAll(pojo.getSaleReportResult());
                            saleReportAdapter.notifyDataSetChanged();
                            StartDate_filter = pojo.getmDefaultStartDate();
                            Enddate_filter = pojo.getmDefaultEndDate();
                            // Enddate_filter = pojo.getEnddate();
                            SharedPref.write(SharedPref.END_DATE, pojo.getmEnddate());
                            SharedPref.write(SharedPref.START_DATE, pojo.getmStartDate());
                            // Log.e("isfilter",isFillterApplied+"");

                            if (!isFillterApplied) {
                                binding.l.textDate.setVisibility(View.VISIBLE);
                                binding.l.textDate.setText(pojo.getmDefaultStartDate() + " To " + pojo.getmDefaultEndDate());
                            } else {
                                binding.l.textDate.setVisibility(View.VISIBLE);
                                binding.l.textDate.setText(form_date + " To " + to_date);
                            }

                            //  Log.e("dateFilter", pojo.getmStartDate());


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
                            if (!isFillterApplied) {
                                binding.l.textDate.setVisibility(View.VISIBLE);
                                binding.l.textDate.setText(pojo.getmDefaultStartDate() + " To " + pojo.getmDefaultEndDate());
                            } else {
                                binding.l.textDate.setVisibility(View.VISIBLE);
                                binding.l.textDate.setText(form_date + " To " + to_date);
                            }
                            StartDate_filter = pojo.getmDefaultStartDate();
                            Enddate_filter = pojo.getmDefaultEndDate();
                            // Enddate_filter = pojo.getEnddate();
                            SharedPref.write(SharedPref.END_DATE, pojo.getmEnddate());
                            SharedPref.write(SharedPref.START_DATE, pojo.getmStartDate());
                            binding.includeProgress.progress.setVisibility(View.GONE);
                            binding.includeProgress.noData.setVisibility(View.VISIBLE);
                            saleReportAdapter.notifyDataSetChanged();
                            AlertUtil.responseElse(mContext, "GetSaleReport ", pojo.getResponseMessage() + "");
                        }
                    }catch (Exception e){
                        AlertUtil.responseExecption(mContext, "GetSaleReport ", e.toString());

                    }
                }, error -> {
                AlertUtil.responseError(mContext, "GetSaleReport ", error.toString());
                    binding.includeProgress.progress.setVisibility(View.GONE);
            binding.includeProgress.noData.setVisibility(View.VISIBLE);
        }) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FROMDATE\":\"" + form_date + "\",\"TODATE\":\"" + to_date + "\",\"SUBPARTY\":\"" + subparty + "\"" +
                        ",\"SUPPLIERS\":\"" + supplier + "\",\"BRANCH\":\"" + branch + "\",\"TRANSPORT\":\"" + transport + "\",\"DBNAME\":\"" + db_name + "\",\"FilterType\":\"" + "NEW" + "\"}";
                Log.e("str", str);
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                80000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filterDialog2() {
        isFilterShowing = true;
        dialog = new Dialog(mContext, R.style.AppTheme);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogThemeUpDown;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fitter_dailog);
//        dialog.setCancelable(false);
        saleFilter_Date = dialog.findViewById(R.id.saleFilter_Date);
        saleFilter_Branch = dialog.findViewById(R.id.saleFilter_Branch);
        saleFilter_SubParty = dialog.findViewById(R.id.saleFilter_SubParty);
        saleFilter_SuppNikName = dialog.findViewById(R.id.saleFilter_SuppNikName);
        saleFilter_transport = dialog.findViewById(R.id.saleFilter_transport);
        saleBranch_Recy = dialog.findViewById(R.id.recycler_brand);
        llRange = dialog.findViewById(R.id.ll_price_range);
        saleFilter_Date.setBackgroundColor(getResources().getColor(R.color.light_pink));
        saleDate = dialog.findViewById(R.id.saleDate);
        sale_ToDate = dialog.findViewById(R.id.sale_ToDate);
        progressBar = dialog.findViewById(R.id.progress);
        nodata = dialog.findViewById(R.id.no_data);

        new Handler().post(() -> {
            filterStack.clear();
            // getFilters(FilterType.CLEAR,true);
            getFilters(FilterTypeSaleReport.SALE_REPORT);
        });

        countbranch = dialog.findViewById(R.id.countbranch);
        countsub_party = dialog.findViewById(R.id.countsub_party);
        countbrand = dialog.findViewById(R.id.countbrand);
        counttransport = dialog.findViewById(R.id.counttransport);

        ImageView cancel = dialog.findViewById(R.id.cancle_i);
        TextView clearAll = dialog.findViewById(R.id.clear_all);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat2 = new SimpleDateFormat("dd/MM/yyyy");


        if (sale_formDate.equals("null") && sale_toDate.equals("null")) {
            saleDate.setText(StartDate_filter);
            sale_ToDate.setText(Enddate_filter);
        } else {
            SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
            Date newDate = null;
            Date newDate1 = null;
            try {
                newDate = spf.parse(sale_formDate);
                newDate1 = spf.parse(sale_toDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat("dd/MM/yyyy");
            //spf = new SimpleDateFormat("dd/MM/yyyy");
            sale_formDate = spf.format(newDate);
            sale_toDate = spf.format(newDate1);
            saleDate.setText(sale_formDate);
            sale_ToDate.setText(sale_toDate);
        }

        saleDate.setOnClickListener(v -> {
            flag = "from";
            // StartDate_filter = ledgerDate.getText().toString();
            if (SharedPref.read(SharedPref.FY_StartDate,"").equals("")){
                StartDate_filter = saleDate.getText().toString();
            }else {
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
            filterChangedSaleReport(FilterTypeSaleReport.DATE);
            getFilters(FilterTypeSaleReport.CLEAR);
        });
        sale_ToDate.setOnClickListener(v -> {
         //   Log.e("dare", SharedPref.read(SharedPref.FY_StartDate, "") + "---" + SharedPref.read(SharedPref.selected_default_yr, ""));
            flag = "to";
            // StartDate_filter = "01/04/2020";
            StartDate_filter = saleDate.getText().toString();
            //  StartDate_filter = ledgerDate.getText().toString();
            //  Enddate_filter = ledger_ToDate.getText().toString();
            if (!SharedPref.read(SharedPref.selected_default_yr, "").equals("2023-24"))
                Enddate_filter = SharedPref.read(SharedPref.FY_EndDate, "");
            else
                Enddate_filter = CurrentDateTime.getCurrentDateDDMMYYY();

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
            filterChangedSaleReport(FilterTypeSaleReport.DATE);
            getFilters(FilterTypeSaleReport.CLEAR);
        });
        clearAll.setOnClickListener(v -> {
            isFilterShowing = false;
            filterStack.clear();
            countbrand.setText("0");
            countbranch.setText("0");
            countsub_party.setText("0");
            counttransport.setText("0");
            if (SharedPref.read(SharedPref.selected_default_yr, "").equals("23-24")) {
                saleDate.setText("01/04/2023");
                sale_ToDate.setText(CurrentDateTime.getCurrentDateDDMMYYY());
            }
            else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("22-23")) {
                saleDate.setText("01/04/2022");
                sale_ToDate.setText(CurrentDateTime.getCurrentDateDDMMYYY());
            } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("21-22")) {
                saleDate.setText("01/04/2021");
                sale_ToDate.setText("31/03/2022");
            } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("20-21")) {
                saleDate.setText("01/04/2020");
                sale_ToDate.setText("31/03/2021");
            }else {
                saleDate.setText("01/04/2023");
                sale_ToDate.setText(CurrentDateTime.getCurrentDateDDMMYYY());
            }
            getFilters(FilterTypeSaleReport.CLEAR);
        });
        TextView apply = dialog.findViewById(R.id.apply);
        cancel.setOnClickListener(v -> {
                    isFilterShowing = false;
                    branchList.forEach(item -> item.setSelected(false));
                    subPartyList.forEach(item -> item.setSelected(false));
                    brandList.forEach(item -> item.setSelected(false));
                    transporterList.forEach(item -> item.setSelected(false));
                    Count = countbranch.getText().toString();
                    Count2 = countsub_party.getText().toString();
                    Count3 = countbrand.getText().toString();
                    Count4 = counttransport.getText().toString();
                    dialog.cancel();

                }
        );
        apply.setOnClickListener(v -> {
            isFilterShowing = false;
            //Brand
            StartDate_filter = saleDate.getText().toString();
            Enddate_filter = sale_ToDate.getText().toString();
            Count = countbranch.getText().toString();
            Count2 = countsub_party.getText().toString();
            Count3 = countbrand.getText().toString();
            Count4 = counttransport.getText().toString();
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

            List<Branch> isSelected = branchList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());

            String brand_array;
            if (isSelected.size() > 0) {
                StringBuilder sb = new StringBuilder();
                brand_array = new Gson().toJson(isSelected);
                try {
                    JSONArray jsonArray = new JSONArray(brand_array);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objects = jsonArray.getJSONObject(i);
                        String name = objects.getString("BranchName");
                        sb.append(name + ",");
                       /// Log.e("Branch_name", name);
                    }
                    String sbb = sb.toString();
                    banch = sbb;
                   // Log.e("sbb", sbb);
                } catch (Exception e) {

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
                      //  Log.e("subparty_name", name);
                    }
                    String sbb = sb.toString();
                    subparty = sbb;
                   // Log.e("sub_partys", sbb);
                } catch (Exception e) {

                }
            } else {
                subparty = "null";
            }
            List<Brand> isSelected2 = brandList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
            String supplier_array;
            if (isSelected2.size() > 0) {
                supplier_array = new Gson().toJson(isSelected2);
                try {
                    StringBuilder sb = new StringBuilder();
                    JSONArray jsonArray = new JSONArray(supplier_array);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objects = jsonArray.getJSONObject(i);
                        String name = objects.getString("BrandName");
                        sb.append(name + ",");
                       // Log.e("ssupplier_name", name);
                    }
                    String sbb = sb.toString();
                    supplier = sbb;
                 //   Log.e("supplier_list", sbb);
                } catch (Exception e) {

                }
            } else {
                supplier = "null";
            }

            List<Transporter> isSelected3 = transporterList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
            String transport_array;
            if (isSelected3.size() > 0) {
                transport_array = new Gson().toJson(isSelected3);
                try {
                    StringBuilder sb = new StringBuilder();
                    JSONArray jsonArray = new JSONArray(transport_array);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objects = jsonArray.getJSONObject(i);
                        String name = objects.getString("TransporterName");
                        sb.append(name + ",");
                     //   Log.e("transport_name", name);
                    }
                    String sbb = sb.toString();
                    transport = sbb;
                  //  Log.e("transport_list", sbb);
                } catch (Exception e) {

                }

            } else {
                transport = "null";
            }

            SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date d1 = sdformat.parse(saleDate.getText().toString());
                Date d2 = sdformat.parse(sale_ToDate.getText().toString());
                if (d1.compareTo(d2) < 0 || d1.compareTo(d2) == 0) {
                    startActivity(new Intent(mContext, SaleReportActivity.class)
                            .putExtra("formDate", saleDate.getText().toString())
                            .putExtra("todate", sale_ToDate.getText().toString())
                            .putExtra("branch", banch)
                            .putExtra("subparty", subparty)
                            .putExtra("brand", supplier)
                            .putExtra("transport", transport));
                    finish();
                    dialog.dismiss();
                } else {
                    Toast.makeText(mContext, "From Date छोटी होनी चाहिए To Date से", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //GetSaleReport(banch, supplier, subparty, transport, sale_formDate, sale_toDate, dbNAME);
           // dialog.dismiss();
        });


        isDatePressed = true;
        saleFilter_Date.setBackground(getResources().getDrawable(R.drawable.selected_button));
        saleFilter_Date.setTextColor(getResources().getColor(R.color.white));

        saleFilter_Date.setOnClickListener(v -> {
            isDatePressed = true;
            saleFilter_Date.setBackground(getResources().getDrawable(R.drawable.selected_button));
            saleFilter_Date.setTextColor(getResources().getColor(R.color.white));
            if (isbranchPlace || isSub_PartyPlace || issuppPlace || istransportPlace) {
                llRange.setVisibility(View.VISIBLE);
                saleBranch_Recy.setVisibility(View.GONE);
                countsub_party.setTextColor(getResources().getColor(R.color.black));
                countbrand.setTextColor(getResources().getColor(R.color.black));
                countbranch.setTextColor(getResources().getColor(R.color.black));
                counttransport.setTextColor(getResources().getColor(R.color.black));
                saleFilter_Branch.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_Branch.setTextColor(getResources().getColor(R.color.black));
                saleFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_SubParty.setTextColor(getResources().getColor(R.color.black));
                saleFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_SuppNikName.setTextColor(getResources().getColor(R.color.black));
                saleFilter_transport.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_transport.setTextColor(getResources().getColor(R.color.black));
                isbranchPlace = false;
                isSub_PartyPlace = false;
                issuppPlace = false;
                istransportPlace = false;
               // filterChangedSaleReport(FilterTypeSaleReport.DATE);
            }
        });

        saleFilter_Branch.setOnClickListener(v -> {
            isbranchPlace = true;
            saleFilter_Branch.setBackground(getResources().getDrawable(R.drawable.selected_button));
            saleFilter_Branch.setTextColor(getResources().getColor(R.color.white));
            keyTypeList = "BRANCH";
        //    Log.e("filter", keyTypeList);
            if (isDatePressed || isSub_PartyPlace || issuppPlace || istransportPlace) {
                llRange.setVisibility(View.GONE);
                saleBranch_Recy.setVisibility(View.VISIBLE);
                countsub_party.setTextColor(getResources().getColor(R.color.black));
                countbrand.setTextColor(getResources().getColor(R.color.black));
                countbranch.setTextColor(getResources().getColor(R.color.white));
                counttransport.setTextColor(getResources().getColor(R.color.black));
                saleFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_Date.setTextColor(getResources().getColor(R.color.black));
                saleFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_SubParty.setTextColor(getResources().getColor(R.color.black));
                saleFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_SuppNikName.setTextColor(getResources().getColor(R.color.black));
                saleFilter_transport.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_transport.setTextColor(getResources().getColor(R.color.black));
                saleBranch_Recy.setAdapter(filterBranchAdap);
                filterBranchAdap.notifyDataSetChanged();
                isDatePressed = false;
                isSub_PartyPlace = false;
                issuppPlace = false;
                istransportPlace = false;
                //getFilters(FilterTypeSaleReport.BRANCH);
            }
        });
        saleFilter_SubParty.setOnClickListener(v -> {
            isSub_PartyPlace = true;
            saleFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.selected_button));
            saleFilter_SubParty.setTextColor(getResources().getColor(R.color.white));
            keyTypeList = "SUBPARTY";
          //  Log.e("filter", keyTypeList);
            if (isDatePressed || isbranchPlace || issuppPlace || istransportPlace) {
                saleBranch_Recy.setVisibility(View.VISIBLE);
                llRange.setVisibility(View.GONE);
                countsub_party.setTextColor(getResources().getColor(R.color.white));
                countbrand.setTextColor(getResources().getColor(R.color.black));
                countbranch.setTextColor(getResources().getColor(R.color.black));
                counttransport.setTextColor(getResources().getColor(R.color.black));
                saleFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_Date.setTextColor(getResources().getColor(R.color.black));
                saleFilter_Branch.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_Branch.setTextColor(getResources().getColor(R.color.black));
                ;
                saleFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_SuppNikName.setTextColor(getResources().getColor(R.color.black));
                saleFilter_transport.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_transport.setTextColor(getResources().getColor(R.color.black));
                saleBranch_Recy.setAdapter(filterSubPartyAdap);
                filterSubPartyAdap.notifyDataSetChanged();
                isbranchPlace = false;
                isDatePressed = false;
                issuppPlace = false;
                istransportPlace = false;
                //getFilters(FilterTypeSaleReport.SUB_PARTY);
            }
        });
        saleFilter_SuppNikName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issuppPlace = true;
                saleFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.selected_button));
                saleFilter_SuppNikName.setTextColor(getResources().getColor(R.color.white));
                keyTypeList = "SUPPLIER";
              ///  Log.e("filter", keyTypeList);
                if (isDatePressed || isbranchPlace || isSub_PartyPlace || istransportPlace) {
                    saleBranch_Recy.setVisibility(View.VISIBLE);
                    llRange.setVisibility(View.GONE);
                    countsub_party.setTextColor(getResources().getColor(R.color.black));
                    countbrand.setTextColor(getResources().getColor(R.color.white));
                    countbranch.setTextColor(getResources().getColor(R.color.black));
                    counttransport.setTextColor(getResources().getColor(R.color.black));
                    saleFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    saleFilter_Date.setTextColor(getResources().getColor(R.color.black));
                    saleFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    saleFilter_SubParty.setTextColor(getResources().getColor(R.color.black));
                    saleFilter_Branch.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    saleFilter_Branch.setTextColor(getResources().getColor(R.color.black));
                    saleFilter_transport.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    saleFilter_transport.setTextColor(getResources().getColor(R.color.black));
                    saleBranch_Recy.setAdapter(filterBrnad_nAdap);
                    filterBrnad_nAdap.notifyDataSetChanged();
                    isbranchPlace = false;
                    isSub_PartyPlace = false;
                    isDatePressed = false;
                    istransportPlace = false;
                    //getFilters(FilterTypeSaleReport.BRAND_NAME);
                }
            }
        });
        saleFilter_transport.setOnClickListener(v -> {
            istransportPlace = true;
            saleFilter_transport.setBackground(getResources().getDrawable(R.drawable.selected_button));
            saleFilter_transport.setTextColor(getResources().getColor(R.color.white));
            keyTypeList = "TRANSPORT";
          //  Log.e("filter", keyTypeList);
            if (isDatePressed || isbranchPlace || isSub_PartyPlace || issuppPlace) {
                saleBranch_Recy.setVisibility(View.VISIBLE);
                llRange.setVisibility(View.GONE);
                countsub_party.setTextColor(getResources().getColor(R.color.black));
                countbrand.setTextColor(getResources().getColor(R.color.black));
                countbranch.setTextColor(getResources().getColor(R.color.black));
                counttransport.setTextColor(getResources().getColor(R.color.white));
                saleFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_Date.setTextColor(getResources().getColor(R.color.black));
                saleFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_SubParty.setTextColor(getResources().getColor(R.color.black));
                saleFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_SuppNikName.setTextColor(getResources().getColor(R.color.black));
                saleFilter_Branch.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_Branch.setTextColor(getResources().getColor(R.color.black));
                saleBranch_Recy.setAdapter(filterTesnportAdpter);
                filterTesnportAdpter.notifyDataSetChanged();
                isbranchPlace = false;
                isSub_PartyPlace = false;
                issuppPlace = false;
                isDatePressed = false;
                //getFilters(FilterTypeSaleReport.TRANSPORT);
            }
        });

        dialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                isFilterShowing = false;
                dialog.dismiss();
            }
            return true;
        });
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getFilters(FilterTypeSaleReport mFilterType ) {
        progressBar.setVisibility(View.VISIBLE);
        FilterSaleReportRequest request;
        request = new FilterSaleReportRequest(
                saleDate.getText().toString(), sale_ToDate.getText().toString(), "SALEREPORT",
                SharedPref.read(SharedPref.PARTY_CODE, ""),
                branchList.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterTypeSaleReport.BRANCH))
                        .collect(Collectors.toList()),
                subPartyList.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterTypeSaleReport.SUB_PARTY))
                        .collect(Collectors.toList()),
                brandList.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterTypeSaleReport.BRAND_NAME))
                        .collect(Collectors.toList()),
                transporterList.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterTypeSaleReport.TRANSPORT))
                        .collect(Collectors.toList()),
                SharedPref.read(SharedPref.DB_NAME, "")
        );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_LIST_NEW, response -> {
            SaleReportPojo pojo = new Gson().fromJson(response, branchType);
         //   Log.e("getFilterRespo",response);
            Log.i("TaG","url " + Request.Method.POST + " =--=-=> " + GET_FILTER_LIST_NEW);
            Log.i("TaG","response -=-=-=-=-=-=--=-=> " + pojo);
            if (pojo.getResponseStatus()) {
                progressBar.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
                Log.e("mFilterType",mFilterType+"");
                Log.e("filterStack",filterStack+"");

                switch (mFilterType) {
                    case BRANCH:
                        List<Branch> prevBranchList = new ArrayList<>(branchList);
                        List<Branch> size = prevBranchList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterTypeSaleReport.BRANCH))
                                .collect(Collectors.toList());
                        branchList.clear();
                        if (!filterStack.contains(mFilterType) || saleReport_Pojo == null || saleReport_Pojo.getBranch() == null || saleReport_Pojo.getBranch().isEmpty()) {
                            branchList.addAll(pojo.getBranch());
                            countbranch.setText("0");
                          //  Toast.makeText(mContext, "if", Toast.LENGTH_SHORT).show();
                        } else {
                         //   Toast.makeText(mContext, "else", Toast.LENGTH_SHORT).show();
                            countbranch.setText(size.size() + "");
                            saleReport_Pojo.getBranch().forEach(branch -> {
                                prevBranchList.forEach(branch2 -> {
                                    if (branch.getBranchName().equals(branch2.getBranchName())) {
                                        branch.setSelected(branch2.isSelected());
                                    }
                                });
                            });
                            branchList.addAll(prevBranchList);
                        }
                        filterBranchAdap.notifyDataSetChanged();
                        break;
                    case SUB_PARTY:
                        List<SubParty> prevSubPartyList = new ArrayList<>(subPartyList);
                        List<SubParty> size1 = prevSubPartyList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterTypeSaleReport.SUB_PARTY))
                                .collect(Collectors.toList());
                     //   Log.e("size", size1.size() + "");

                        subPartyList.clear();
                        if (!filterStack.contains(mFilterType) || saleReport_Pojo == null || saleReport_Pojo.getSubParty() == null || saleReport_Pojo.getSubParty().isEmpty()) {
                            subPartyList.addAll(pojo.getSubParty());
                            countsub_party.setText("0");
                        } else {
                        //    Log.e("LedgerActivity", "ZHere");
                            countsub_party.setText(size1.size() + "");
                            saleReport_Pojo.getSubParty().forEach(subParty -> {
                                prevSubPartyList.forEach(account1 -> {
                                    if (subParty.getSubPartyName().equals(account1.getSubPartyName())) {
                                        subParty.setSelected(account1.isSelected());
                                    }
                                });
                            });
                            subPartyList.addAll(prevSubPartyList);
                            // accountTypeList.addAll(ledgerPogo.getAccountType());
                        }
                        filterSubPartyAdap.notifyDataSetChanged();
                        break;
                    case BRAND_NAME:
                        List<Brand> prevBrandList = new ArrayList<>(brandList);
                       // Log.e("prevEntryList", new Gson().toJson(prevBrandList));
                        List<Brand> size11 = prevBrandList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterTypeSaleReport.BRAND_NAME))
                                .collect(Collectors.toList());

                     //   Log.e("entryTypeListbfrclear", new Gson().toJson(brandList));
                        brandList.clear();
                      //  Log.e("justaftrtclear", new Gson().toJson(brandList));
                        if (!filterStack.contains(mFilterType) || saleReport_Pojo == null || saleReport_Pojo.getBrand() == null || saleReport_Pojo.getBrand().isEmpty()) {
                            brandList.addAll(pojo.getBrand());
                            countbrand.setText("0");
                         //   Log.e("entryTypestaftrclearif", new Gson().toJson(brandList));
                        } else {
                            countbrand.setText(size11.size() + "");
                         //   Log.e("ledgerPogo", new Gson().toJson(saleReport_Pojo.getBrand()));
                            saleReport_Pojo.getBrand().forEach(brand -> {
                                prevBrandList.forEach(brand1 -> {
                                    if (brand.getBrandName().equals(brand1.getBrandName())) {
                                        brand.setSelected(brand1.isSelected());
                                    }
                                });
                            });
                            brandList.addAll(prevBrandList);
                            // entryTypeList.addAll(ledgerPogo.getEntryType());
                          //  Log.e("entryTypestaftrclearels", new Gson().toJson(brandList));
                        }
                        filterBrnad_nAdap.notifyDataSetChanged();
                        break;
                    case TRANSPORT:
                        Log.e("filterStack",filterStack+"");
                        Log.e("mFilterType",mFilterType+"");
                        List<Transporter> prevTransporterList = new ArrayList<>(transporterList);
                        List<Transporter> size111 = prevTransporterList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterTypeSaleReport.TRANSPORT))
                                .collect(Collectors.toList());

                        transporterList.clear();
                        if (!filterStack.contains(mFilterType) || saleReport_Pojo == null || saleReport_Pojo.getTransporter() == null || saleReport_Pojo.getTransporter().isEmpty()) {
                            transporterList.addAll(pojo.getTransporter());
                            counttransport.setText("0");
                        } else {
                            counttransport.setText(size111.size() + "");
                            saleReport_Pojo.getTransporter().forEach(transporter -> {
                                prevTransporterList.forEach(transporter1 -> {
                                    if (transporter.getTransporterName().equals(transporter1.getTransporterName())) {
                                        transporter.setSelected(transporter1.isSelected());
                                    }
                                });
                            });
                            transporterList.addAll(prevTransporterList);

                            // entryTypeList.addAll(ledgerPogo.getEntryType());
                        }
                        filterTesnportAdpter.notifyDataSetChanged();

                        break;
                    case CLEAR:
                        branchList.clear();
                        branchList.addAll(pojo.getBranch());
                        subPartyList.clear();
                        subPartyList.addAll(pojo.getSubParty());
                        brandList.clear();
                        brandList.addAll(pojo.getBrand());
                        transporterList.clear();
                        transporterList.addAll(pojo.getTransporter());
                        filterBranchAdap.notifyDataSetChanged();
                        filterTesnportAdpter.notifyDataSetChanged();
                        filterSubPartyAdap.notifyDataSetChanged();
                        filterBrnad_nAdap.notifyDataSetChanged();

                    case SALE_REPORT:
                        saleReport_Pojo = pojo;
                        break;
                }
            } else {
                progressBar.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                countbranch.setText("0");
                countsub_party.setText("0");
                countsub_party.setText("0");
                countbrand.setText("0");
                counttransport.setText("0");
                branchList.clear();
                subPartyList.clear();
                brandList.clear();
                transporterList.clear();

                filterBranchAdap.notifyDataSetChanged();
                filterSubPartyAdap.notifyDataSetChanged();
                filterBrnad_nAdap.notifyDataSetChanged();
                filterTesnportAdpter.notifyDataSetChanged();
                counttransport.setText("0");


            }
            // }
        }, error -> {
            progressBar.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
            branchList.clear();
            subPartyList.clear();
            brandList.clear();
            transporterList.clear();
            countbranch.setText("0");
            countsub_party.setText("0");
            countsub_party.setText("0");
            countbrand.setText("0");
            counttransport.setText("0");
            filterBranchAdap.notifyDataSetChanged();
            filterSubPartyAdap.notifyDataSetChanged();
            filterBrnad_nAdap.notifyDataSetChanged();
            filterTesnportAdpter.notifyDataSetChanged();
        })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = new Gson().toJson(request);
                Log.e("str", str);
                Log.i("TaG","Request -=-=-=> " + str);
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filterDialog() {
        isFilterShowing = true;
        final Dialog dialog = new Dialog(mContext, R.style.AppTheme);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogThemeUpDown;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fitter_dailog);
        dialog.setCancelable(false);
        saleFilter_Date = dialog.findViewById(R.id.saleFilter_Date);
        saleFilter_Branch = dialog.findViewById(R.id.saleFilter_Branch);
        saleFilter_SubParty = dialog.findViewById(R.id.saleFilter_SubParty);
        saleFilter_SuppNikName = dialog.findViewById(R.id.saleFilter_SuppNikName);
        saleFilter_transport = dialog.findViewById(R.id.saleFilter_transport);
        saleBranch_Recy = dialog.findViewById(R.id.recycler_brand);
        llRange = dialog.findViewById(R.id.ll_price_range);
        saleFilter_Date.setBackgroundColor(getResources().getColor(R.color.light_pink));
        saleDate = dialog.findViewById(R.id.saleDate);
        sale_ToDate = dialog.findViewById(R.id.sale_ToDate);

        countbranch = dialog.findViewById(R.id.countbranch);
        countsub_party = dialog.findViewById(R.id.countsub_party);
        countbrand = dialog.findViewById(R.id.countbrand);
        counttransport = dialog.findViewById(R.id.counttransport);

        ImageView cancel = dialog.findViewById(R.id.cancle_i);
        TextView clearAll = dialog.findViewById(R.id.clear_all);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat2 = new SimpleDateFormat("dd/MM/yyyy");

        if (Count.equals("") || Count2.equals("") || Count3.equals("") || Count4.equals("")) {
            countbrand.setText("0");
            countbranch.setText("0");
            countsub_party.setText("0");
            counttransport.setText("0");
        } else {
            countbranch.setText(Count);
            countsub_party.setText(Count2);
            countbrand.setText(Count3);
            counttransport.setText(Count4);
        }
        if (sale_formDate.equals("null") && sale_toDate.equals("null")) {
            saleDate.setText(SharedPref.read(SharedPref.FY_StartDate, ""));
            sale_ToDate.setText(SharedPref.read(SharedPref.FY_EndDate, ""));
        } else {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy/MM/dd");
            Date newDate = null;
            Date newDate1 = null;
            try {
                newDate = spf.parse(sale_formDate);
                newDate1 = spf.parse(sale_toDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat("dd/MM/yyyy");
            //spf = new SimpleDateFormat("dd/MM/yyyy");
            sale_formDate = spf.format(newDate);
            sale_toDate = spf.format(newDate1);
            saleDate.setText(sale_formDate);
            sale_ToDate.setText(sale_toDate);
        }

        saleDate.setOnClickListener(v -> {
            flag = "from";
            sale_formDate = saleDate.getText().toString();
            sale_toDate = sale_ToDate.getText().toString();
            ;
            String[] items1 = sale_formDate.split("/");
            String[] items2 = sale_toDate.split("/");
            String yy = items1[2];
            String mm = items1[1];
            String dd = items1[0];
            String yy1 = items2[2];
            String mm2 = items2[1];
            String dd3 = items2[0];
            showDate(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), Integer.parseInt(yy1), Integer.parseInt(mm2) - 1, Integer.parseInt(dd3), R.style.NumberPickerStyle);
        });
        sale_ToDate.setOnClickListener(v -> {
            flag = "to";
            sale_formDate = saleDate.getText().toString();
            sale_toDate = sale_ToDate.getText().toString();
            ;
            String[] items1 = sale_formDate.split("/");
            String[] items2 = sale_toDate.split("/");
            String yy = items1[2];
            String mm = items1[1];
            String dd = items1[0];
            String yy1 = items2[2];
            String mm2 = items2[1];
            String dd3 = items2[0];
            showDate2(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), Integer.parseInt(yy1), Integer.parseInt(mm2) - 1, Integer.parseInt(dd3), R.style.NumberPickerStyle);
        });
        clearAll.setOnClickListener(v -> {
            isFilterShowing = false;
            saleDate.setText(SharedPref.read(SharedPref.FY_StartDate, ""));
            sale_ToDate.setText(SharedPref.read(SharedPref.FY_EndDate, ""));
            countbrand.setText("0");
            countbranch.setText("0");
            countsub_party.setText("0");
            counttransport.setText("0");

            for (int i = 0; i < FilterListDetails.size(); i++) {
                FilterListDetails.get(i).setSelected(false);
            }
            for (int i = 0; i < subpartyList.size(); i++) {
                subpartyList.get(i).setSelected(false);
            }
            for (int i = 0; i < supplierList.size(); i++) {
                supplierList.get(i).setSelected(false);
            }
            for (int i = 0; i < trnsportList.size(); i++) {
                trnsportList.get(i).setSelected(false);
            }

            new Handler().postDelayed(() -> {

                if (isbranchPlace) {
                    filterAdapter = new BranchListAdapter(mContext, FilterListDetails);
                    saleBranch_Recy.setAdapter(filterAdapter);
                    filterAdapter.notifyDataSetChanged();
                } else if (isSub_PartyPlace) {
                    subPartyListAdapter = new SubPartyListAdapter(mContext, subpartyList);
                    saleBranch_Recy.setAdapter(subPartyListAdapter);
                    subPartyListAdapter.notifyDataSetChanged();
                } else if (issuppPlace) {
                    supplierListAdapter = new SupplierListAdapter(mContext, supplierList);
                    saleBranch_Recy.setAdapter(supplierListAdapter);
                    supplierListAdapter.notifyDataSetChanged();
                } else if (istransportPlace) {
                    transportListAdapter = new TransportListAdapter(mContext, trnsportList);
                    saleBranch_Recy.setAdapter(transportListAdapter);
                    transportListAdapter.notifyDataSetChanged();
                }

            }, 500);
        });
        TextView apply = dialog.findViewById(R.id.apply);
        cancel.setOnClickListener(v -> {
                    isFilterShowing = false;
                    for (int i = 0; i < FilterListDetails.size(); i++) {
                        FilterListDetails.get(i).setSelected(false);
                    }
                    for (int i = 0; i < subpartyList.size(); i++) {
                        subpartyList.get(i).setSelected(false);
                    }
                    for (int i = 0; i < supplierList.size(); i++) {
                        supplierList.get(i).setSelected(false);
                    }
                    for (int i = 0; i < trnsportList.size(); i++) {
                        trnsportList.get(i).setSelected(false);
                    }
                    dialog.cancel();
                }
        );
        apply.setOnClickListener(v -> {
            isFilterShowing = false;
            //Brand
            sale_formDate = saleDate.getText().toString();
            sale_toDate = sale_ToDate.getText().toString();
            Count = countbranch.getText().toString();
            Count2 = countsub_party.getText().toString();
            Count3 = countbrand.getText().toString();
            Count4 = counttransport.getText().toString();
            SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
            Date newDate = null;
            Date newDate1 = null;
            try {
                newDate = spf.parse(sale_formDate);
                newDate1 = spf.parse(sale_toDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat("yyyy/MM/dd");
            sale_formDate = spf.format(newDate);
            sale_toDate = spf.format(newDate1);

            List<FilterListResult> isSelected = FilterListDetails.stream().filter(p -> p.isSelected()).collect(Collectors.toList());

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
                     //   Log.e("brand_name", name);
                    }
                    String sbb = sb.toString();
                    banch = sbb;
                  //  Log.e("sbb", sbb);
                } catch (Exception e) {

                }
            } else {
                banch = "null";
            }
            List<com.syber.ssspltd.response.SubpartyListRespo.FilterListResult> isSelected1 = subpartyList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
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
                       // Log.e("subparty_name", name);
                    }
                    String sbb = sb.toString();
                    subparty = sbb;
                   // Log.e("sub_partys", sbb);
                } catch (Exception e) {

                }
            } else {
                subparty = "null";
            }
            List<com.syber.ssspltd.response.SupplierListPojo.FilterListResult> isSelected2 = supplierList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
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
                     //   Log.e("ssupplier_name", name);
                    }
                    String sbb = sb.toString();
                    supplier = sbb;
                   // Log.e("supplier_list", sbb);
                } catch (Exception e) {

                }
            } else {
                supplier = "null";
            }

            List<com.syber.ssspltd.response.TransportListRespo.FilterListResult> isSelected3 = trnsportList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
            String transport_array;
            if (isSelected3.size() > 0) {
                transport_array = new Gson().toJson(isSelected3);
                try {
                    StringBuilder sb = new StringBuilder();
                    JSONArray jsonArray = new JSONArray(transport_array);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objects = jsonArray.getJSONObject(i);
                        String name = objects.getString("FilterName");
                        sb.append(name + ",");
                      //  Log.e("transport_name", name);
                    }
                    String sbb = sb.toString();
                    transport = sbb;
                  //  Log.e("transport_list", sbb);
                } catch (Exception e) {

                }

            } else {
                transport = "null";
            }
            GetSaleReport(banch, supplier, subparty, transport, sale_formDate, sale_toDate, dbNAME,false);
            dialog.dismiss();
        });


        isDatePressed = true;
        saleFilter_Date.setBackground(getResources().getDrawable(R.drawable.selected_button));
        saleFilter_Date.setTextColor(getResources().getColor(R.color.white));

        saleFilter_Date.setOnClickListener(v -> {
            isDatePressed = true;
            saleFilter_Date.setBackground(getResources().getDrawable(R.drawable.selected_button));
            saleFilter_Date.setTextColor(getResources().getColor(R.color.white));
            if (isbranchPlace || isSub_PartyPlace || issuppPlace || istransportPlace) {
                llRange.setVisibility(View.VISIBLE);
                saleBranch_Recy.setVisibility(View.GONE);
                countsub_party.setTextColor(getResources().getColor(R.color.black));
                countbrand.setTextColor(getResources().getColor(R.color.black));
                countbranch.setTextColor(getResources().getColor(R.color.black));
                counttransport.setTextColor(getResources().getColor(R.color.black));
                saleFilter_Branch.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_Branch.setTextColor(getResources().getColor(R.color.black));
                saleFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_SubParty.setTextColor(getResources().getColor(R.color.black));
                saleFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_SuppNikName.setTextColor(getResources().getColor(R.color.black));
                saleFilter_transport.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_transport.setTextColor(getResources().getColor(R.color.black));
                isbranchPlace = false;
                isSub_PartyPlace = false;
                issuppPlace = false;
                istransportPlace = false;
            }
        });

        saleFilter_Branch.setOnClickListener(v -> {
            isbranchPlace = true;
            saleFilter_Branch.setBackground(getResources().getDrawable(R.drawable.selected_button));
            saleFilter_Branch.setTextColor(getResources().getColor(R.color.white));
            keyTypeList = "BRANCH";
          //  Log.e("filter", keyTypeList);
            if (isDatePressed || isSub_PartyPlace || issuppPlace || istransportPlace) {
                llRange.setVisibility(View.GONE);
                saleBranch_Recy.setVisibility(View.VISIBLE);
                countsub_party.setTextColor(getResources().getColor(R.color.black));
                countbrand.setTextColor(getResources().getColor(R.color.black));
                countbranch.setTextColor(getResources().getColor(R.color.white));
                counttransport.setTextColor(getResources().getColor(R.color.black));
                saleFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_Date.setTextColor(getResources().getColor(R.color.black));
                saleFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_SubParty.setTextColor(getResources().getColor(R.color.black));
                saleFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_SuppNikName.setTextColor(getResources().getColor(R.color.black));
                saleFilter_transport.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_transport.setTextColor(getResources().getColor(R.color.black));
                linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                saleBranch_Recy.setLayoutManager(linearLayoutManager);
                filterAdapter = new BranchListAdapter(mContext, FilterListDetails);
                saleBranch_Recy.setAdapter(filterAdapter);
                filterAdapter.notifyDataSetChanged();
                isDatePressed = false;
                isSub_PartyPlace = false;
                issuppPlace = false;
                istransportPlace = false;
            }
        });
        saleFilter_SubParty.setOnClickListener(v -> {
            isSub_PartyPlace = true;
            saleFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.selected_button));
            saleFilter_SubParty.setTextColor(getResources().getColor(R.color.white));
            keyTypeList = "SUBPARTY";
          //  Log.e("filter", keyTypeList);
            if (isDatePressed || isbranchPlace || issuppPlace || istransportPlace) {
                saleBranch_Recy.setVisibility(View.VISIBLE);
                llRange.setVisibility(View.GONE);
                countsub_party.setTextColor(getResources().getColor(R.color.white));
                countbrand.setTextColor(getResources().getColor(R.color.black));
                countbranch.setTextColor(getResources().getColor(R.color.black));
                counttransport.setTextColor(getResources().getColor(R.color.black));
                saleFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_Date.setTextColor(getResources().getColor(R.color.black));
                saleFilter_Branch.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_Branch.setTextColor(getResources().getColor(R.color.black));
                ;
                saleFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_SuppNikName.setTextColor(getResources().getColor(R.color.black));
                saleFilter_transport.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_transport.setTextColor(getResources().getColor(R.color.black));
                linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                saleBranch_Recy.setLayoutManager(linearLayoutManager);
                subPartyListAdapter = new SubPartyListAdapter(mContext, subpartyList);
                saleBranch_Recy.setAdapter(subPartyListAdapter);
                subPartyListAdapter.notifyDataSetChanged();
                isbranchPlace = false;
                isDatePressed = false;
                issuppPlace = false;
                istransportPlace = false;
            }
        });
        saleFilter_SuppNikName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issuppPlace = true;
                saleFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.selected_button));
                saleFilter_SuppNikName.setTextColor(getResources().getColor(R.color.white));
                keyTypeList = "SUPPLIER";
            //    Log.e("filter", keyTypeList);
                if (isDatePressed || isbranchPlace || isSub_PartyPlace || istransportPlace) {
                    saleBranch_Recy.setVisibility(View.VISIBLE);
                    llRange.setVisibility(View.GONE);
                    countsub_party.setTextColor(getResources().getColor(R.color.black));
                    countbrand.setTextColor(getResources().getColor(R.color.white));
                    countbranch.setTextColor(getResources().getColor(R.color.black));
                    counttransport.setTextColor(getResources().getColor(R.color.black));
                    saleFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    saleFilter_Date.setTextColor(getResources().getColor(R.color.black));
                    saleFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    saleFilter_SubParty.setTextColor(getResources().getColor(R.color.black));
                    saleFilter_Branch.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    saleFilter_Branch.setTextColor(getResources().getColor(R.color.black));
                    saleFilter_transport.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    saleFilter_transport.setTextColor(getResources().getColor(R.color.black));
                    linearLayoutManager = new LinearLayoutManager(mContext);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    saleBranch_Recy.setLayoutManager(linearLayoutManager);
                    supplierListAdapter = new SupplierListAdapter(mContext, supplierList);
                    saleBranch_Recy.setAdapter(supplierListAdapter);
                    supplierListAdapter.notifyDataSetChanged();
                    isbranchPlace = false;
                    isSub_PartyPlace = false;
                    isDatePressed = false;
                    istransportPlace = false;
                }
            }
        });

        saleFilter_transport.setOnClickListener(v -> {
            istransportPlace = true;
            saleFilter_transport.setBackground(getResources().getDrawable(R.drawable.selected_button));
            saleFilter_transport.setTextColor(getResources().getColor(R.color.white));
            keyTypeList = "TRANSPORT";
          //  Log.e("filter", keyTypeList);
            if (isDatePressed || isbranchPlace || isSub_PartyPlace || issuppPlace) {
                saleBranch_Recy.setVisibility(View.VISIBLE);
                llRange.setVisibility(View.GONE);
                countsub_party.setTextColor(getResources().getColor(R.color.black));
                countbrand.setTextColor(getResources().getColor(R.color.black));
                countbranch.setTextColor(getResources().getColor(R.color.black));
                counttransport.setTextColor(getResources().getColor(R.color.white));
                saleFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_Date.setTextColor(getResources().getColor(R.color.black));
                saleFilter_SubParty.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_SubParty.setTextColor(getResources().getColor(R.color.black));
                saleFilter_SuppNikName.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_SuppNikName.setTextColor(getResources().getColor(R.color.black));
                saleFilter_Branch.setBackground(getResources().getDrawable(R.drawable.text_bg));
                saleFilter_Branch.setTextColor(getResources().getColor(R.color.black));
                linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                saleBranch_Recy.setLayoutManager(linearLayoutManager);
                transportListAdapter = new TransportListAdapter(mContext, trnsportList);
                saleBranch_Recy.setAdapter(transportListAdapter);
                transportListAdapter.notifyDataSetChanged();
                isbranchPlace = false;
                isSub_PartyPlace = false;
                issuppPlace = false;
                isDatePressed = false;
            }
        });
        dialog.show();
    }

    private void BranchDetail(final String keyType) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_DETAIL_LIST,
                response -> {
              //      Log.e("Data", response);
                    FilterListPojo pojo = new Gson().fromJson(response, listType2);
                    if (pojo.getResponseStatus()) {
                        FilterListDetails.clear();
                        FilterListDetails.addAll(pojo.getFilterListResult());

                    } else {
                    }
                }, error -> {
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DATAKEY\":\"" + keyType + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FILTERTYPE\":\"" + "SALEREPORT" + "\"}";
              //  Log.e("str", str);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_DETAIL_LIST,
                response -> {
               //     Log.e("Data", response);
                    com.syber.ssspltd.response.SubpartyListRespo.FilterListPojo pojo = new Gson().fromJson(response, subpartyListType);
                    if (pojo.getResponseStatus()) {
                        subpartyList.clear();
                        subpartyList.addAll(pojo.getFilterListResult());
                    } else {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressBar.cancel();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DATAKEY\":\"" + keyType + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FILTERTYPE\":\"" + "SALEREPORT" + "\"}";
             //   Log.e("str", str);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_DETAIL_LIST,
                response -> {
                  //  Log.e("Data", response);
                    com.syber.ssspltd.response.SupplierListPojo.FilterListPojo pojo = new Gson().fromJson(response, supList);
                    if (pojo.getResponseStatus()) {
                        supplierList.clear();
                        supplierList.addAll(pojo.getFilterListResult());
                    } else {
                    }
                }, error -> {
    //                progressBar.cancel();
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DATAKEY\":\"" + keyType + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FILTERTYPE\":\"" + "SALEREPORT" + "\"}";
              //  Log.e("str", str);
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

    private void TransportDetail(final String keyType) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_DETAIL_LIST,
                response -> {
                    Log.e("Data", response);
                    com.syber.ssspltd.response.TransportListRespo.FilterListPojo pojo = new Gson().fromJson(response, transList);
                    if (pojo.getResponseStatus()) {
                        trnsportList.clear();
                        trnsportList.addAll(pojo.getFilterListResult());
                    } else {
                    }
                }, error -> {

                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DATAKEY\":\"" + keyType + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FILTERTYPE\":\"" + "SALEREPORT" + "\"}";
                Log.e("str", str);
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
            // Set the "from" date
            saleDate.setText(simpleDateFormat.format(calendar.getTime()));

            // If the selected "from" date is today, set the "to" date as the same
            if (simpleDateFormat.format(calendar.getTime()).equals(CurrentDateTime.getCurrentDateDDMMYYY())) {
                sale_ToDate.setText(saleDate.getText().toString());
            }

            // Check if "from" date is after "to" date
            Calendar toDateCalendar = Calendar.getInstance();
            try {
                toDateCalendar.setTime(simpleDateFormat2.parse(sale_ToDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (calendar.after(toDateCalendar)) {
                sale_ToDate.setText(saleDate.getText().toString()); // Set "to" date same as "from" date
            }

        } else if (flag.equals("to")) {
            // Set the "to" date
            sale_ToDate.setText(simpleDateFormat2.format(calendar.getTime()));

            // Check if "to" date is before "from" date
            Calendar fromDateCalendar = Calendar.getInstance();
            try {
                fromDateCalendar.setTime(simpleDateFormat.parse(saleDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (calendar.before(fromDateCalendar)) {
                saleDate.setText(sale_ToDate.getText().toString()); // Set "from" date same as "to" date
            }
        }

        isFilterShowing = false;
        filterStack.clear();

        countbrand.setText("0");
        countbranch.setText("0");
        countsub_party.setText("0");
        counttransport.setText("0");

        getFilters(FilterTypeSaleReport.SALE_REPORT);
    }


    @VisibleForTesting
    void showDate(int year1, int monthOfYear1, int dayOfMonth1, int year2, int monthOfYear2, int dayOfMonth2, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(SaleReportActivity.this)
                .callback(SaleReportActivity.this)
                .spinnerTheme(spinnerTheme)
                .minDate(year1, monthOfYear1, dayOfMonth1)
                .maxDate(year2, monthOfYear2, dayOfMonth2)
                .build()
                .show();

    }

    @VisibleForTesting
    void showDate2(int year1, int monthOfYear1, int dayOfMonth1, int year2, int monthOfYear2, int dayOfMonth2, int spinnerTheme) {
        try {
            new SpinnerDatePickerDialogBuilder()
                    .context(SaleReportActivity.this)
                    .callback(SaleReportActivity.this)
                    .spinnerTheme(spinnerTheme)
                    .minDate(year1, monthOfYear1, dayOfMonth1)
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
    protected void onResume() {
        if (SharedPref.read(SharedPref.ON, "").equals("")) {
            showCustomDialog();
        } else {

        }
        super.onResume();

    }

    @Override
    public void filterChangedSaleReport(FilterTypeSaleReport mFilterType) {
        if (filterStack.contains(mFilterType)) {
            while (filterStack.pop() != mFilterType) {
            }
        }
        filterStack.push(mFilterType);
        Log.e("Seq", filterStack.toString());

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
                GetSaleReport(banch, supplier, subparty, transport, sale_formDate, sale_toDate, dbNAME,false);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

}