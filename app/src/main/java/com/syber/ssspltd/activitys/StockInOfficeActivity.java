package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.ConstantVariable.AUTH_TOKEN;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_FILTER_DETAIL_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_FILTER_LIST_NEW;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_STOCK_IN_OFFICE_REPORT;

import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.syber.ssspltd.NewFilterResponse.FilterType;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.CurrentDateTime;
import com.syber.ssspltd.Interface.FilterChangedStockInOffice;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterStockInOff.Branch;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterStockInOff.Brand;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterStockInOff.FilterStockInORequest;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterStockInOff.StockInOffPojo;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterStockInOff.SubParty;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.SaleReportFilter.FilterTypeSaleReport;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.StockInOff.FilterBranch_SAdap;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.StockInOff.FilterBrand_SAdap;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.StockInOff.FilterSubParty_SAdap;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.StockInOff.FilterTypeStockInOffice;
import com.syber.ssspltd.adapter.PendingOrdFilterAdapter.BranchAdapter;
import com.syber.ssspltd.adapter.PendingOrdFilterAdapter.Sup_PartyAdapter;
import com.syber.ssspltd.adapter.PendingOrdFilterAdapter.SupplierAdapter;
import com.syber.ssspltd.adapter.StockInOfficeAdapter;
import com.syber.ssspltd.databinding.ActivityStockInOfficeBinding;
import com.syber.ssspltd.response.PendingOrdBranchRespo.FilterListPojo;
import com.syber.ssspltd.response.PendingOrdBranchRespo.FilterListResult;
import com.syber.ssspltd.response.StockInOfficeReportRespo.StockInOfficePojo;
import com.syber.ssspltd.response.StockInOfficeReportRespo.StockInOfficeReportResult;
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
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class StockInOfficeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, FilterChangedStockInOffice {

    Context mContext = this;
    RecyclerView stockInOfficeRecy;
    StockInOfficeAdapter stockInOfficeAdapter;
    List<StockInOfficeReportResult> stockInOfficeDetails;
    Type listType;
    LinearLayoutManager linearLayoutManager;
    private ActivityStockInOfficeBinding binding;

    String banch = "null", subparty = "null", supplier = "null", stock_formDate = "null", stock_toDate = "null", dbNAME = SharedPref.read(SharedPref.DB_NAME, "");
    Boolean isDatePressed = false, isBranchPlace = false, isSubPartyPlace = false, isSuppNPlace = false, isTransportPlace = false;
    TextView pendingFilter_Date, pendingFilter_Branch, pendingFilter_SubParty, pendingFilter_SuppNikName, nodata;
    RecyclerView pending_Recy;
    LinearLayout llRange;

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
    TextView stockDate, stock_ToDate;
    String flag = "";
    String Count = "", Count2 = "", Count3 = "";
    public static TextView countbranch, countsub_party, countbrand;
    boolean isFilterShowing = false;
    Dialog dialog;
    // boolean isbranchPlace=false,isSub_PartyPlace=false, isBrandPlace=false;

    FilterBranch_SAdap filterBranch_sAdap;
    FilterSubParty_SAdap filterSubParty_sAdap;
    FilterBrand_SAdap filterBrand_sAdap;

    List<Branch> branch_List;
    List<SubParty> subParty_List;
    List<Brand> brand_List;
    Stack<FilterTypeStockInOffice> filterStack;
    StockInOffPojo stockInOffPojo;
    Type branch_Type, subParty_Type, brand_type;
    ProgressBar progressBar;
    String StartDate_filter, Enddate_filter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStockInOfficeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.supportChat.supportFab.setOnClickListener(v ->
                Lazy.openDialog(mContext));
        //new Filter

        filterStack = new Stack<>();
        branch_List = new ArrayList<>();
        subParty_List = new ArrayList<>();
        brand_List = new ArrayList<>();

        filterBranch_sAdap = new FilterBranch_SAdap(mContext, branch_List, this);
        filterSubParty_sAdap = new FilterSubParty_SAdap(mContext, subParty_List, this);
        filterBrand_sAdap = new FilterBrand_SAdap(mContext, brand_List, this);

        branch_Type = new TypeToken<StockInOffPojo>() {
        }.getType();
        subParty_Type = new TypeToken<StockInOffPojo>() {
        }.getType();
        brand_type = new TypeToken<StockInOffPojo>() {
        }.getType();
        //new Filter

        if (Lazy.haveNetworkConnection(mContext)) {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.getStringExtra("stockDate") != null || intent.getStringExtra("stock_ToDate") != null) {
                    GetStockInOfficeReport(intent.getStringExtra("banch"),
                            intent.getStringExtra("subparty"), intent.getStringExtra("supplier")
                            , intent.getStringExtra("stockDate"), intent.getStringExtra("stock_ToDate"), dbNAME, true);
                } else {
                    if (isSetFYDate()) {
                        GetStockInOfficeReport(banch, subparty, supplier, StartDate_filter, Enddate_filter, dbNAME, false);

                    }else {
                        GetStockInOfficeReport(banch, subparty, supplier, StartDate_filter, Enddate_filter, dbNAME, false);
                    }

                }
            } else {

            }
        } else {
            networkConnetion3(mContext);
        }


        stockInOfficeDetails = new ArrayList<>();
        listType = new TypeToken<StockInOfficePojo>() {
        }.getType();
        branchList = new ArrayList<>();
        subpartyList = new ArrayList<>();
        supplierList = new ArrayList<>();

        branchType = new TypeToken<FilterListPojo>() {
        }.getType();
        subpartyListType = new TypeToken<com.syber.ssspltd.response.PendingOrdBranchRespo.Sup_PartyRespo.FilterListPojo>() {
        }.getType();
        supList = new TypeToken<com.syber.ssspltd.response.PendingOrdBranchRespo.SupplierRespo.FilterListPojo>() {
        }.getType();


        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.stockInOfficeRecy.setLayoutManager(linearLayoutManager);
        stockInOfficeAdapter = new StockInOfficeAdapter(mContext, stockInOfficeDetails);
        binding.stockInOfficeRecy.setAdapter(stockInOfficeAdapter);


        binding.tool.back3.setImageDrawable(ContextCompat.getDrawable(StockInOfficeActivity.this, R.drawable.ic_baseline_keyboard_backspace));
        binding.tool.back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.tool.download.setImageDrawable(ContextCompat.getDrawable(StockInOfficeActivity.this, R.drawable.ic_filter));

        binding.tool.download.setOnClickListener(v -> {
                    if (!isFilterShowing) {
                        filterDialog2();
                    }
                    //filterDialog();
                }
        );
        binding.tool.back2.setText("STOCK IN OFFICE");
        BranchDetail("BRANCH");
        SubpartyDetail("SUBPARTY");
        SupplerDetail("SUPPLIER");
    }

    private Boolean isSetFYDate() {
        if (SharedPref.read(SharedPref.selected_default_yr, "").equals("2024-2025")) {
            StartDate_filter = "01/04/2024";
            Enddate_filter = CurrentDateTime.getCurrentDateDDMMYYY();
        } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("2023-2024")) {
            StartDate_filter = "01/04/2023";
            Enddate_filter = "31/03/2024";
        } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("2022-2023")) {
            StartDate_filter = "01/04/2022";
            Enddate_filter = "31/03/2023";
        } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("2021-2022")) {
            StartDate_filter = "01/04/2021";
            Enddate_filter = "31/03/2022";
        }  else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("2020-2021")) {
            StartDate_filter = "01/04/2020";
            Enddate_filter = "31/03/2021";
        } else {
            return false;

        }
        return true;
    }


    private void GetStockInOfficeReport(String branch, String subParty, String supplier, String form_Date, String to_Date, String db_name, boolean isFilterApplied) {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_STOCK_IN_OFFICE_REPORT,
                response -> {
                    Log.e("StockInOfficeRespo", response);
                    Log.i("TaG","Url 5 -=-=-=-==" + GET_STOCK_IN_OFFICE_REPORT);
                    Log.i("TaG","response5 -=-=-=-= " + response);
                    StockInOfficePojo pojo = new Gson().fromJson(response, listType);
                    try {
                        if (pojo.getResponseStatus()) {
                            binding.includeProgress.progress.setVisibility(View.GONE);
                            binding.includeProgress.noData.setVisibility(View.GONE);
                            stockInOfficeDetails.clear();
                            stockInOfficeDetails.addAll(pojo.getStockInOfficeReportResult());
                            stockInOfficeAdapter.notifyDataSetChanged();

                            if(isSetFYDate() == false){
                                StartDate_filter = pojo.getmDefaultStartDate();
                                Enddate_filter = pojo.getmDefaultEndDate();
                            }

                            // Enddate_filter = pojo.getEnddate();
                            SharedPref.write(SharedPref.END_DATE, pojo.getmEnddate());
                            SharedPref.write(SharedPref.START_DATE, pojo.getmStartDate());
                            if (!isFilterApplied) {

                                binding.tool.textDate.setVisibility(View.VISIBLE);
                                if (pojo.getmDefaultStartDate() != null && pojo.getmDefaultEndDate() != null && !pojo.getmDefaultStartDate().isEmpty() && !pojo.getmDefaultEndDate().isEmpty()) {
                                    binding.tool.textDate.setText(pojo.getmDefaultStartDate() + " To " + pojo.getmDefaultEndDate());
                                } else {
                                    binding.tool.textDate.setText("");
                                }

                            } else {
                                binding.tool.textDate.setVisibility(View.VISIBLE);
                                if(form_Date != null && to_Date != null && !form_Date.isEmpty() && !to_Date.isEmpty()) {

                                    binding.tool.textDate.setText(form_Date + " To " + to_Date);
                                }else {
                                    binding.tool.textDate.setText("");
                                }
                            }


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
                            if (!isFilterApplied) {
                                binding.tool.textDate.setVisibility(View.VISIBLE);
                                if (pojo.getmDefaultStartDate() != null && pojo.getmDefaultEndDate() != null && !pojo.getmDefaultStartDate().isEmpty() && !pojo.getmDefaultEndDate().isEmpty()) {
                                    binding.tool.textDate.setText(pojo.getmDefaultStartDate() + " To " + pojo.getmDefaultEndDate());
                                }else {
                                    binding.tool.textDate.setText("");
                                }
                            } else {
                                binding.tool.textDate.setVisibility(View.VISIBLE);
                                if(form_Date != null && to_Date != null && !form_Date.isEmpty() && !to_Date.isEmpty()) {
                                    binding.tool.textDate.setText(form_Date + " To " + to_Date);
                                }else {
                                    binding.tool.textDate.setText("");
                                }
                            }

                            StartDate_filter = pojo.getmDefaultStartDate();
                            Enddate_filter = pojo.getmDefaultEndDate();
                            stockInOfficeAdapter.notifyDataSetChanged();
                            binding.includeProgress.progress.setVisibility(View.GONE);
                            binding.includeProgress.noData.setVisibility(View.VISIBLE);
                            AlertUtil.responseElse(mContext, "GetStockInOfficeReport ", pojo.getResponseMessage() + "");
                        }
                    } catch (Exception e) {
                        AlertUtil.responseExecption(mContext, "GetStockInOfficeReport ", e.toString());
                    }
                }, error -> {
            AlertUtil.responseError(mContext, "GetStockInOfficeReport ", error.toString());
            binding.includeProgress.progress.setVisibility(View.GONE);
            binding.includeProgress.noData.setVisibility(View.VISIBLE);
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                Object a = null;
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"PartyCode\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FromDate\":\"" + form_Date + "\",\"ToDate\":\"" + to_Date + "\",\"Branch\":\"" + branch + "\",\"Subparty\":\"" + subParty + "\",\"SUPPLIERS\":\"" + supplier + "\",\"DBNAME\":\"" + db_name + "\"}";
                Log.e("StockInOfcstr", str);
                Log.e("TaG", "request 5 -=-=- =" + str);
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
    public void filterDialog2() {
        isFilterShowing = true;
        dialog = new Dialog(mContext, R.style.AppTheme);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogThemeUpDown;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pending_fitter_dailog);
//        dialog.setCancelable(false);
        pendingFilter_Date = dialog.findViewById(R.id.pendingFilter_Date);
        pendingFilter_Branch = dialog.findViewById(R.id.pendingFilter_Branch);
        pendingFilter_SubParty = dialog.findViewById(R.id.pendingFilter_SubParty);
        pendingFilter_SuppNikName = dialog.findViewById(R.id.pendingFilter_SuppNikName);
        pending_Recy = dialog.findViewById(R.id.pending_Recy);
        llRange = dialog.findViewById(R.id.ll_price_range);
        pendingFilter_Date.setBackgroundColor(getResources().getColor(R.color.light_pink));
        stockDate = dialog.findViewById(R.id.stockDate);
        stock_ToDate = dialog.findViewById(R.id.stock_ToDate);
        progressBar = dialog.findViewById(R.id.progress);
        nodata = dialog.findViewById(R.id.no_data);

        countbranch = dialog.findViewById(R.id.countbranch);
        countsub_party = dialog.findViewById(R.id.countsub_party);
        countbrand = dialog.findViewById(R.id.countbrand);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        simpleDateFormat2 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        stockDate.setText(StartDate_filter);
        stock_ToDate.setText(Enddate_filter);
//        Log.e("stock_formDate",stock_formDate);
//        Log.e("stock_toDate",stock_toDate);
//        Log.e("StartDate_filter",StartDate_filter);
//        Log.e("Enddate_filter",Enddate_filter);


        new Handler().post(() -> {
            filterStack.clear();
            // getFilters(FilterType.CLEAR,true);
            getFilters(FilterTypeStockInOffice.STOCK_IN_OFFICE_REPORT);
        });

        if (stock_formDate.equals("null") && stock_toDate.equals("null")) {
            stockDate.setText(StartDate_filter);
            stock_ToDate.setText(Enddate_filter);
        } else {
            SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
            Date newDate = null;
            Date newDate1 = null;
            try {
                newDate = spf.parse(stock_formDate);
                newDate1 = spf.parse(stock_toDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat("dd/MM/yyyy");
            //spf = new SimpleDateFormat("dd/MM/yyyy");
            stock_formDate = spf.format(newDate);
            stock_toDate = spf.format(newDate1);
            stockDate.setText(stock_formDate);
            stock_ToDate.setText(stock_toDate);
        }


        ImageView cancel = dialog.findViewById(R.id.cancle_i);
        TextView clearAll = dialog.findViewById(R.id.clear_all);

        stockDate.setOnClickListener(v -> {
            flag = "from";
            // StartDate_filter = ledgerDate.getText().toString();
            if (SharedPref.read(SharedPref.FY_StartDate, "").equals("")) {
                StartDate_filter = stockDate.getText().toString();
            } else {
                StartDate_filter = SharedPref.read(SharedPref.FY_StartDate, "");

            }
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
            filterChangedStockInOffice(FilterTypeStockInOffice.DATE);
            getFilters(FilterTypeStockInOffice.CLEAR);
        });
        stock_ToDate.setOnClickListener(v -> {
            Log.e("dare", SharedPref.read(SharedPref.FY_StartDate, "") + "---" + SharedPref.read(SharedPref.selected_default_yr, ""));
            flag = "to";
            // StartDate_filter = "01/04/2020";
            if (!stockDate.getText().toString().isEmpty()) {
                StartDate_filter = stockDate.getText().toString();

                //  StartDate_filter = ledgerDate.getText().toString();
                //  Enddate_filter = ledger_ToDate.getText().toString();
                if (!SharedPref.read(SharedPref.selected_default_yr, "").equals("2023-24")) {
                    Enddate_filter = SharedPref.read(SharedPref.FY_EndDate, "");
                } else {
                    Enddate_filter = CurrentDateTime.getCurrentDateDDMMYYY();
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
                filterChangedStockInOffice(FilterTypeStockInOffice.DATE);
                getFilters(FilterTypeStockInOffice.CLEAR);
            } else {
                Toast.makeText(mContext, "Please Select From Date", Toast.LENGTH_SHORT).show();
            }
        });

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFilterShowing = false;
                filterStack.clear();
                countbranch.setText("0");
                countsub_party.setText("0");
                countbrand.setText("0");
                if (SharedPref.read(SharedPref.selected_default_yr, "").equals("23-24")) {
                    stockDate.setText("01/04/2023");
                    stock_ToDate.setText(CurrentDateTime.getCurrentDateDDMMYYY());
                } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("22-23")) {
                    stockDate.setText("01/04/2022");
                    stock_ToDate.setText("31/03/2023");
                } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("21-22")) {
                    stockDate.setText("01/04/2021");
                    stock_ToDate.setText("31/03/2022");
                } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("20-21")) {
                    stockDate.setText("01/04/2020");
                    stock_ToDate.setText("31/03/2021");
                } else {
                    stockDate.setText("01/04/2023");
                    stock_ToDate.setText(CurrentDateTime.getCurrentDateDDMMYYY());
                }
                getFilters(FilterTypeStockInOffice.CLEAR);
            }

        });

        TextView apply = dialog.findViewById(R.id.apply);
        cancel.setOnClickListener(v -> {
            isFilterShowing = false;
            branch_List.forEach(item -> item.setSelected(false));
            subParty_List.forEach(item -> item.setSelected(false));
            brand_List.forEach(item -> item.setSelected(false));

            Count = countbranch.getText().toString();
            Count2 = countsub_party.getText().toString();
            Count3 = countbrand.getText().toString();
            dialog.dismiss();
        });
        apply.setOnClickListener(v -> {
            isFilterShowing = false;
            StartDate_filter = stockDate.getText().toString();
            Enddate_filter = stock_ToDate.getText().toString();
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
                        Log.e("branch_name", name);
                    }
                    String sbb = sb.toString();
                    banch = sbb;
                    Log.e("sbb", sbb);
                } catch (Exception e) {

                }
            } else {
                banch = "null";
            }
            List<SubParty> isSelected1 = subParty_List.stream().filter(p -> p.isSelected()).collect(Collectors.toList());

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
            List<Brand> isSelected2 = brand_List.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
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

            Log.e("led_formDate", stockDate.getText().toString() + "/" + stock_ToDate.getText().toString());
            //  GetLedgerReport(ledgerDate.getText().toString(), ledger_ToDate.getText().toString(), entry,adjustment, dnNAME, account);
            SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date d1 = sdformat.parse(stockDate.getText().toString());
                Date d2 = sdformat.parse(stock_ToDate.getText().toString());
                if (d1.compareTo(d2) < 0 || d1.compareTo(d2) == 0) {
                    startActivity(new Intent(mContext, StockInOfficeActivity.class)
                            .putExtra("stockDate", stockDate.getText().toString())
                            .putExtra("stock_ToDate", stock_ToDate.getText().toString())
                            .putExtra("banch", banch)
                            .putExtra("subparty", subparty)
                            .putExtra("supplier", supplier));
                    finish();
                    dialog.dismiss();
                } else {
                    Toast.makeText(mContext, "From Date छोटी होनी चाहिए To Date से", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


            //GetStockInOfficeReport(banch,subparty,supplier,stock_formDate,stock_toDate,dbNAME);
            // dialog.dismiss();
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
                pending_Recy.setAdapter(filterBranch_sAdap);
                filterBranch_sAdap.notifyDataSetChanged();
                isDatePressed = false;
                isSubPartyPlace = false;
                isSuppNPlace = false;

                //getFilters(FilterTypeStockInOffice.BRANCH);
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

                pending_Recy.setAdapter(filterSubParty_sAdap);
                filterSubParty_sAdap.notifyDataSetChanged();
                isDatePressed = false;
                isBranchPlace = false;
                isSuppNPlace = false;
                //getFilters(FilterTypeStockInOffice.SUB_PARTY);
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

                pending_Recy.setAdapter(filterBrand_sAdap);
                filterBrand_sAdap.notifyDataSetChanged();
                isDatePressed = false;
                isBranchPlace = false;
                isSubPartyPlace = false;
                //getFilters(FilterTypeStockInOffice.BRAND_NAME);
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

        getFilters(FilterTypeStockInOffice.BRANCH);
        getFilters(FilterTypeStockInOffice.SUB_PARTY);
        getFilters(FilterTypeStockInOffice.BRAND_NAME);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getFilters(FilterTypeStockInOffice mFilterType) {
        progressBar.setVisibility(View.VISIBLE);
        FilterStockInORequest request;
        request = new FilterStockInORequest(
                stockDate.getText().toString(), stock_ToDate.getText().toString(), "STOCKINOFFICE",
                SharedPref.read(SharedPref.PARTY_CODE, ""),
                branch_List.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterTypeStockInOffice.BRANCH))
                        .collect(Collectors.toList()),
                subParty_List.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterTypeStockInOffice.SUB_PARTY))
                        .collect(Collectors.toList()),
                brand_List.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterTypeStockInOffice.BRAND_NAME))
                        .collect(Collectors.toList()),
                SharedPref.read(SharedPref.DB_NAME, "")
        );

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_LIST_NEW, response -> {
            Log.e("Data", response);
            StockInOffPojo pojo = new Gson().fromJson(response, branch_Type);
//            countbranch.setText( pojo.getBranch());
//            countsub_party.setText( pojo.getSubParty());
//            countbrand.setText( pojo.getBrand());
            Log.i("TaG","Url 4 -=-=-=-==" + GET_FILTER_LIST_NEW);
            Log.i("TaG","response4 -=-=-=-= " + response);
            if (pojo.getResponseStatus()) {
                progressBar.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
                filterBranch_sAdap.notifyDataSetChanged();
                filterSubParty_sAdap.notifyDataSetChanged();
                filterBrand_sAdap.notifyDataSetChanged();
                switch (mFilterType) {
                    case BRANCH:
                        List<Branch> branchList = new ArrayList<>(branch_List);
                        List<Branch> size = branchList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterTypeStockInOffice.BRANCH))
                                .collect(Collectors.toList());
                        Log.e("size", size.size() + "");

                        branch_List.clear();
                        if (!filterStack.contains(mFilterType) || stockInOffPojo == null || stockInOffPojo.getBranch() == null || stockInOffPojo.getBranch().isEmpty()) {
                            branch_List.addAll(pojo.getBranch());
                            countbranch.setText("0");
                        } else {
                            countbranch.setText(size.size() + "");
                            stockInOffPojo.getBranch().forEach(branch -> {
                                branch_List.forEach(branch1 -> {
                                    if (branch.getBranchName().equals(branch1.getBranchName())) {
                                        branch.setSelected(branch1.isSelected());
                                    }
                                });
                            });
                            branch_List.addAll(branchList);
                            //  adjustmentTypeList.addAll(ledgerPogo.getAdjustmentType());
                        }
                        filterBranch_sAdap.notifyDataSetChanged();
                        break;
                    case SUB_PARTY:
                        List<SubParty> prevSubPartyList = new ArrayList<>(subParty_List);
                        List<SubParty> size1 = prevSubPartyList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterTypeStockInOffice.SUB_PARTY))
                                .collect(Collectors.toList());
                        Log.e("size", size1.size() + "");

                        subParty_List.clear();
                        if (!filterStack.contains(mFilterType) || stockInOffPojo == null || stockInOffPojo.getSubParty() == null || stockInOffPojo.getSubParty().isEmpty()) {
                            subParty_List.addAll(pojo.getSubParty());
                            countsub_party.setText("0");
                        } else {
                            Log.e("LedgerActivity", "ZHere");
                            countsub_party.setText(size1.size() + "");
                            stockInOffPojo.getSubParty().forEach(subParty -> {
                                prevSubPartyList.forEach(subParty1 -> {
                                    if (subParty.getSubPartyName().equals(subParty1.getSubPartyName())) {
                                        subParty.setSelected(subParty1.isSelected());
                                    }
                                });
                            });
                            subParty_List.addAll(prevSubPartyList);
                            // accountTypeList.addAll(ledgerPogo.getAccountType());
                        }
                        filterSubParty_sAdap.notifyDataSetChanged();
                        break;
                    case BRAND_NAME:
                        List<Brand> prevBrandList = new ArrayList<>(brand_List);
                        Log.e("prevEntryList", new Gson().toJson(prevBrandList));
                        List<Brand> size11 = prevBrandList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterTypeStockInOffice.BRAND_NAME))
                                .collect(Collectors.toList());

                        Log.e("entryTypeListbfrclear", new Gson().toJson(brand_List));
                        brand_List.clear();
                        Log.e("justaftrtclear", new Gson().toJson(brand_List));
                        if (!filterStack.contains(mFilterType) || stockInOffPojo == null || stockInOffPojo.getBrand() == null || stockInOffPojo.getBrand().isEmpty()) {
                            brand_List.addAll(pojo.getBrand());
                            countbrand.setText("0");
                            Log.e("entryTypestaftrclearif", new Gson().toJson(brand_List));
                        } else {
                            countbrand.setText(size11.size() + "");
                            Log.e("ledgerPogo", new Gson().toJson(stockInOffPojo.getBrand()));
                            stockInOffPojo.getBrand().forEach(brand -> {
                                prevBrandList.forEach(brand1 -> {
                                    if (brand.getBrandName().equals(brand1.getBrandName())) {
                                        brand.setSelected(brand1.isSelected());
                                    }
                                });
                            });
                            brand_List.addAll(prevBrandList);
                            // entryTypeList.addAll(ledgerPogo.getEntryType());
                            Log.e("entryTypestaftrclearels", new Gson().toJson(brand_List));
                        }
                        filterBrand_sAdap.notifyDataSetChanged();

                        break;
                    case CLEAR:
                        branch_List.clear();
                        branch_List.addAll(pojo.getBranch());
                        subParty_List.clear();
                        subParty_List.addAll(pojo.getSubParty());
                        brand_List.clear();
                        brand_List.addAll(pojo.getBrand());
                        filterBranch_sAdap.notifyDataSetChanged();
                        filterSubParty_sAdap.notifyDataSetChanged();
                        filterBrand_sAdap.notifyDataSetChanged();

                    case STOCK_IN_OFFICE_REPORT:
                        stockInOffPojo = pojo;
                        break;
                }
            } else {
                progressBar.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                branch_List.clear();
                subParty_List.clear();
                brand_List.clear();
                countbrand.setText("0");
                countbranch.setText("0");
                countsub_party.setText("0");
                filterBranch_sAdap.notifyDataSetChanged();
                filterSubParty_sAdap.notifyDataSetChanged();
                filterBrand_sAdap.notifyDataSetChanged();

            }
            // }
        }, error -> {
            progressBar.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
            branch_List.clear();
            subParty_List.clear();
            brand_List.clear();
            countbrand.setText("0");
            countbranch.setText("0");
            countsub_party.setText("0");
            filterBranch_sAdap.notifyDataSetChanged();
            filterSubParty_sAdap.notifyDataSetChanged();
            filterBrand_sAdap.notifyDataSetChanged();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = new Gson().toJson(request);
                Log.e("str", str);
                Log.e("TaG", "request 4 -=-=-=- " + str );
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
        dialog.setContentView(R.layout.pending_fitter_dailog);
        dialog.setCancelable(false);
        pendingFilter_Date = dialog.findViewById(R.id.pendingFilter_Date);
        pendingFilter_Branch = dialog.findViewById(R.id.pendingFilter_Branch);
        pendingFilter_SubParty = dialog.findViewById(R.id.pendingFilter_SubParty);
        pendingFilter_SuppNikName = dialog.findViewById(R.id.pendingFilter_SuppNikName);
        pending_Recy = dialog.findViewById(R.id.pending_Recy);
        llRange = dialog.findViewById(R.id.ll_price_range);
        pendingFilter_Date.setBackgroundColor(getResources().getColor(R.color.light_pink));
        stockDate = dialog.findViewById(R.id.stockDate);
        stock_ToDate = dialog.findViewById(R.id.stock_ToDate);

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

        if (stock_formDate.equals("null") && stock_toDate.equals("null")) {
            stockDate.setText(SharedPref.read(SharedPref.FY_StartDate, ""));
            stock_ToDate.setText(SharedPref.read(SharedPref.FY_EndDate, ""));
        } else {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy/MM/dd");
            Date newDate = null;
            Date newDate1 = null;
            try {
                newDate = spf.parse(stock_formDate);
                newDate1 = spf.parse(stock_toDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat("dd/MM/yyyy");
            //spf = new SimpleDateFormat("dd/MM/yyyy");
            stock_formDate = spf.format(newDate);
            stock_toDate = spf.format(newDate1);
            stockDate.setText(stock_formDate);
            stock_ToDate.setText(stock_toDate);
        }


        ImageView cancel = dialog.findViewById(R.id.cancle_i);
        TextView clearAll = dialog.findViewById(R.id.clear_all);

        stockDate.setOnClickListener(v -> {
            isFilterShowing = false;
            flag = "from";
            stock_formDate = stockDate.getText().toString();
            stock_toDate = stock_ToDate.getText().toString();
            ;
            String[] items1 = stock_formDate.split("/");
            String[] items2 = stock_toDate.split("/");
            String yy = items1[2];
            String mm = items1[1];
            String dd = items1[0];
            String yy1 = items2[2];
            String mm2 = items2[1];
            String dd3 = items2[0];
            showDate(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), Integer.parseInt(yy1), Integer.parseInt(mm2) - 1, Integer.parseInt(dd3), R.style.NumberPickerStyle);
            getFilters(FilterTypeStockInOffice.CLEAR);
        });
        stock_ToDate.setOnClickListener(v -> {
            isFilterShowing = false;
            flag = "to";
            stock_formDate = stockDate.getText().toString();
            stock_toDate = stock_ToDate.getText().toString();
            ;
            String[] items1 = stock_formDate.split("/");
            String[] items2 = stock_toDate.split("/");
            String yy = items1[2];
            String mm = items1[1];
            String dd = items1[0];
            String yy1 = items2[2];
            String mm2 = items2[1];
            String dd3 = items2[0];
            showDate2(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), Integer.parseInt(yy1), Integer.parseInt(mm2) - 1, Integer.parseInt(dd3), R.style.NumberPickerStyle);
            getFilters(FilterTypeStockInOffice.CLEAR);
        });

        clearAll.setOnClickListener(v -> {
            stockDate.setText(SharedPref.read(SharedPref.FY_StartDate, ""));
            stock_ToDate.setText(SharedPref.read(SharedPref.FY_EndDate, ""));
            countbranch.setText("0");
            countsub_party.setText("0");
            countbrand.setText("0");
            for (int i = 0; i < branchList.size(); i++) {
                branchList.get(i).setSelected(false);
            }
            for (int i = 0; i < subpartyList.size(); i++) {
                subpartyList.get(i).setSelected(false);
            }
            for (int i = 0; i < supplierList.size(); i++) {
                subpartyList.get(i).setSelected(false);
            }

            new Handler().postDelayed(() -> {
                if (isBranchPlace) {
                    branchAdapter = new BranchAdapter(mContext, branchList);
                    pending_Recy.setAdapter(branchAdapter);
                    branchAdapter.notifyDataSetChanged();
                } else if (isSubPartyPlace) {
                    sup_partyAdapter = new Sup_PartyAdapter(mContext, subpartyList);
                    pending_Recy.setAdapter(sup_partyAdapter);
                    sup_partyAdapter.notifyDataSetChanged();
                } else if (isSuppNPlace) {
                    supplierAdapter = new SupplierAdapter(mContext, supplierList);
                    pending_Recy.setAdapter(supplierAdapter);
                    supplierAdapter.notifyDataSetChanged();
                }
            }, 500);
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
                subpartyList.get(i).setSelected(false);
            }

            Count = countbranch.getText().toString();
            Count2 = countsub_party.getText().toString();
            Count3 = countbrand.getText().toString();
            dialog.dismiss();
        });
        apply.setOnClickListener(v -> {
            isFilterShowing = false;
            stock_formDate = stockDate.getText().toString();
            stock_toDate = stock_ToDate.getText().toString();
            Count = countbranch.getText().toString();
            Count2 = countsub_party.getText().toString();
            Count3 = countbrand.getText().toString();

            SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
            Date newDate = null;
            Date newDate1 = null;
            try {
                newDate = spf.parse(stock_formDate);
                newDate1 = spf.parse(stock_toDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat("yyyy/MM/dd");
            stock_formDate = spf.format(newDate);
            stock_toDate = spf.format(newDate1);

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
                } catch (Exception e) {

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


            GetStockInOfficeReport(banch, subparty, supplier, stock_formDate, stock_toDate, dbNAME, true);
            dialog.dismiss();
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
        dialog.show();
    }


    private void BranchDetail(final String keyType) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_DETAIL_LIST,
                response -> {
                    Log.e("Data", response);
                    Log.i("TaG","Url 3 -=-=-=-==" + GET_FILTER_DETAIL_LIST);
                    Log.i("TaG","response3 -=-=-=-= " + response);
                    FilterListPojo pojo = new Gson().fromJson(response, branchType);
                    if (pojo.getResponseStatus()) {
                        branchList.clear();
                        branchList.addAll(pojo.getFilterListResult());
                    } else {
                    }
                }, error -> {
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DATAKEY\":\"" + keyType + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FILTERTYPE\":\"" + "STOCKINOFFICE" + "\"}";
                Log.e("str", str);
                Log.i("TaG", "request3 -=-=-= " + str);
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
                    Log.e("Data", response);
                    Log.i("TaG","Url 2 -=-=-=-==" + GET_FILTER_DETAIL_LIST);
                    Log.i("TaG","response2 -=-=-=-= " + response);
                    com.syber.ssspltd.response.PendingOrdBranchRespo.Sup_PartyRespo.FilterListPojo pojo = new Gson().fromJson(response, subpartyListType);
                    if (pojo.getResponseStatus()) {
                        subpartyList.clear();
                        subpartyList.addAll(pojo.getFilterListResult());
                    } else {
                    }
                }, error -> {

        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DATAKEY\":\"" + keyType + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FILTERTYPE\":\"" + "STOCKINOFFICE" + "\"}";
                Log.e("str", str);
                Log.i("TaG","request 2 -=-=-=-= " + str);
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
                    Log.e("Data", response);
                    Log.i("TaG","Url1--==-=-=--=-" + GET_FILTER_DETAIL_LIST);
                    Log.i("TaG","response1--==-=-=--=-" + response);
                    com.syber.ssspltd.response.PendingOrdBranchRespo.SupplierRespo.FilterListPojo pojo = new Gson().fromJson(response, supList);
                    if (pojo.getResponseStatus()) {
                        supplierList.clear();
                        supplierList.addAll(pojo.getFilterListResult());
                    } else {
                    }
                }, error -> {
        }) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DATAKEY\":\"" + keyType + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FILTERTYPE\":\"" + "STOCKINOFFICE" + "\"}";
                Log.e("str", str);
                Log.i("TaG","Request1======" + str);
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
            stockDate.setText(simpleDateFormat.format(calendar.getTime()));

            // Check if "from" date is after "to" date
            Calendar toDateCalendar = Calendar.getInstance();
            try {
                toDateCalendar.setTime(simpleDateFormat2.parse(stock_ToDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (calendar.after(toDateCalendar)) {
                stock_ToDate.setText(stockDate.getText().toString());
            }
        } else if (flag.equals("to")) {
            stock_ToDate.setText(simpleDateFormat2.format(calendar.getTime()));

            // Check if "to" date is before "from" date
            Calendar fromDateCalendar = Calendar.getInstance();
            try {
                fromDateCalendar.setTime(simpleDateFormat.parse(stockDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (calendar.before(fromDateCalendar)) {
                stockDate.setText(stock_ToDate.getText().toString());
            }
        }

        isFilterShowing = false;
        filterStack.clear();

        countbranch.setText("0");
        countsub_party.setText("0");
        countbrand.setText("0");

        filterChangedStockInOffice(FilterTypeStockInOffice.DATE);
        /*getFilters(FilterTypeStockInOffice.BRANCH);
        getFilters(FilterTypeStockInOffice.SUB_PARTY);
        getFilters(FilterTypeStockInOffice.BRAND_NAME);*/
        getFilters(FilterTypeStockInOffice.STOCK_IN_OFFICE_REPORT);
    }


    @VisibleForTesting
    void showDate(int year1, int monthOfYear1, int dayOfMonth1, int year2, int monthOfYear2, int dayOfMonth2, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(StockInOfficeActivity.this)
                .callback(StockInOfficeActivity.this)
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
                    .context(StockInOfficeActivity.this)
                    .callback(StockInOfficeActivity.this)
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
    public void filterChangedStockInOffice(FilterTypeStockInOffice mFilterType) {

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
                GetStockInOfficeReport(banch, subparty, supplier, stock_formDate, stock_toDate, dbNAME, false);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

}