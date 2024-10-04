package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_COMPLETE_LEDGER_PDF;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_FILTER_LIST_NEW;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_LEDGER_REPORT_WITH_BALANCE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.CurrentDateTime;
import com.syber.ssspltd.Interface.OnCheckChange;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.NewFilterAdapter.LedgerAdapter.AccountTypeAdapter;
import com.syber.ssspltd.NewFilterAdapter.LedgerAdapter.AdjustmentTypeAdapter;
import com.syber.ssspltd.NewFilterAdapter.LedgerAdapter.EntryTypeAdapter;
import com.syber.ssspltd.NewFilterResponse.AccountType;
import com.syber.ssspltd.NewFilterResponse.AdjustmentType;
import com.syber.ssspltd.NewFilterResponse.EntryType;
import com.syber.ssspltd.NewFilterResponse.FilterType;
import com.syber.ssspltd.NewFilterResponse.LedgerFilterRequest;
import com.syber.ssspltd.NewFilterResponse.LedgerPogo;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.LedgerReportAdapter;
import com.syber.ssspltd.databinding.ActivityLedgerBinding;
import com.syber.ssspltd.response.LedgerReportResponse.LedgerReportPojo;
import com.syber.ssspltd.response.LedgerReportResponse.LedgerReportResult;
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

public class LedgerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, FilterCallback, OnCheckChange {

    private ActivityLedgerBinding binding;
    Context mContext = this;
    private LedgerReportAdapter ledgerReportAdapter;
    static List<LedgerReportResult> LedgerReportDetails;
    LinearLayout llRange;
    Type listType;
    LinearLayoutManager linearLayoutManager;
    TextView ledgerDate, ledger_ToDate;
    CheckBox all_entry, clear_entry, unclear_entry, dr_entry, cr_entry;
    String led_formDate = "null", led_toDate = "null", status = "null", tick = "null", dnNAME = SharedPref.read(SharedPref.DB_NAME, "");
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormat2;
    String flag = "", SelectedFdate, SelectedTdate;
    ImageView download_PDF;
    public static String startYear, endYear;
    private boolean isAllEntryCheckrd = false;
    private boolean isClearEntryCheckrd = false;
    private boolean isUnclearEntryCheckrd = false;
    private boolean isDREntryCheckrd = false;
    private boolean isCREntryCheckrd = false;
    String trueFalse = "";
    ProgressBar progressBar;

    LedgerPogo ledgerPogo;
    Stack<FilterType> filterStack;
    List<AdjustmentType> adjustmentTypeList;
    List<AccountType> accountTypeList;
    List<EntryType> entryTypeList;

    AdjustmentTypeAdapter adjustmentTypeAdapter;
    AccountTypeAdapter accountTypeAdapter;
    EntryTypeAdapter entryTypeAdapter;

    boolean isFilterShowing = false;
    String Count = "", Count2 = "", Count3 = "";
    public static TextView count_aduj, count_account, count_entry;
    TextView ledgerFilter_Date, adjustmentFilter, accountFilter, entryFilter, nodata;
    RecyclerView ledger_Recy;
    Type adjustmentType, accountType, entryType;
    String adjustment = "null", account = "null";
    Boolean isDatePressed = false, isAdjustmentPlace = false, isAccountPlace = false, isEntryPlace = false;
    String StartDate_filter, Enddate_filter;
    public static String entry = "", tickforPdf = "";
    TextView textDate;
    String pdfFromDate,pdfToDate;
    Dialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLedgerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.supportChat.supportFab.setOnClickListener(v -> Lazy.openDialog(mContext));
        ImageView backImage = findViewById(R.id.back3);

        filterStack = new Stack<>();
        adjustmentTypeList = new ArrayList<>();
        accountTypeList = new ArrayList<>();
        entryTypeList = new ArrayList<>();

        entryTypeAdapter = new EntryTypeAdapter(mContext, entryTypeList, this, this);
        accountTypeAdapter = new AccountTypeAdapter(mContext, accountTypeList, this);
        adjustmentTypeAdapter = new AdjustmentTypeAdapter(mContext, adjustmentTypeList, this);

        adjustmentType = new TypeToken<LedgerPogo>() {
        }.getType();
        accountType = new TypeToken<LedgerPogo>() {
        }.getType();
        entryType = new TypeToken<LedgerPogo>() {
        }.getType();


        if (Lazy.haveNetworkConnection(mContext)) {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.getStringExtra("ledgerDate") != null || intent.getStringExtra("ledger_ToDate") != null) {
                    GetLedgerReport(intent.getStringExtra("ledgerDate"),
                            intent.getStringExtra("ledger_ToDate"), intent.getStringExtra("entry")
                            , intent.getStringExtra("adjustment"), dnNAME, intent.getStringExtra("account"), true);
                } else {
                    GetLedgerReport(led_formDate, led_toDate, "", adjustment, dnNAME, account, false);
                }
            } else {

            }
        } else {
            networkConnetion3(mContext);
        }


        download_PDF = findViewById(R.id.download_PDF);
        backImage.setImageDrawable(ContextCompat.getDrawable(LedgerActivity.this, R.drawable.ic_baseline_keyboard_backspace));
        backImage.setOnClickListener(v -> onBackPressed());

        ImageView backImage3 = findViewById(R.id.download);
        backImage3.setImageDrawable(ContextCompat.getDrawable(LedgerActivity.this, R.drawable.ic_filter));
        backImage3.setOnClickListener(v -> {
            if (!isFilterShowing) {
                filterDialog2();
            }
        });

        textDate = findViewById(R.id.textDate);
        TextView backImage2 = findViewById(R.id.back2);
        backImage2.setText("LEDGER");

        LedgerReportDetails = new ArrayList<>();

        listType = new TypeToken<LedgerReportPojo>() {
        }.getType();

        ledgerReportAdapter = new LedgerReportAdapter(mContext, LedgerReportDetails);
        binding.LegerReportRecy.setAdapter(ledgerReportAdapter);
        //GetLedgerReport(led_formDate,led_toDate,status,tick,dnNAME);
        download_PDF.setOnClickListener(v ->{
            // ACCORDING TO FILTER
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.getStringExtra("ledgerDate") != null || intent.getStringExtra("ledger_ToDate") != null) {
                    GetCompleteLedgerPDF(intent.getStringExtra("ledgerDate"),
                            intent.getStringExtra("ledger_ToDate"), intent.getStringExtra("entry")
                            , intent.getStringExtra("adjustment"), dnNAME, intent.getStringExtra("account"));
                } else {
                    GetCompleteLedgerPDF(led_formDate, led_toDate, "", adjustment, dnNAME, account);
                }
            } else {

            }

//            NORMAL|| ALL WITHOUT FILTER
        //    GetCompleteLedgerPDF();
        });
    }

    private void GetLedgerReport(String formDate, String toDate, String status, String tick, String db_name, String ledger_type, boolean isisFilterApplied) {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_LEDGER_REPORT_WITH_BALANCE,
                response -> {
                    Log.e("Data", response);
                    LedgerReportPojo pojo = new Gson().fromJson(response, listType);
                    LedgerReportDetails.clear();
                    try {
                    if (pojo.getResponseStatus()) {
                        String open_bla = pojo.getOpeningBal();
                        String clos_bla = pojo.getClosingBal();
                        binding.includeProgress.progress.setVisibility(View.GONE);
                        binding.includeProgress.noData.setVisibility(View.GONE);
                        LedgerReportDetails.addAll(pojo.getLedgerReportResult());
                        binding.currentBalLeg.setText(open_bla);
                        binding.closingBal.setText(clos_bla);
                        ledgerReportAdapter.notifyDataSetChanged();
                        binding.currentBalLeg.setVisibility(View.VISIBLE);
                        //  StartDate_filter = pojo.getStartDate();
                        StartDate_filter = pojo.getmDefaultStartDate();
                        Enddate_filter = pojo.getmDefaultEndDate();
                        // Enddate_filter = pojo.getEnddate();
                        SharedPref.write(SharedPref.END_DATE, pojo.getEnddate());
                        SharedPref.write(SharedPref.START_DATE, pojo.getStartDate());
                        if (!isisFilterApplied) {
                            textDate.setVisibility(View.VISIBLE);
                            textDate.setText(pojo.getmDefaultStartDate() + " To " + pojo.getmDefaultEndDate());
                            pdfFromDate = pojo.getmDefaultStartDate();
                            pdfToDate = pojo.getmDefaultEndDate();
                           // Log.e("pdfFromDate",pdfFromDate);
                        } else {
                            textDate.setVisibility(View.VISIBLE);
                            textDate.setText(formDate + " To " + toDate);
                            pdfFromDate = formDate;
                            pdfToDate = toDate;
                         //   Log.e("formDate",formDate);
                        }

                      //  Log.e("dateFilter", pojo.getStartDate());

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
                        if (!isisFilterApplied) {
                            textDate.setVisibility(View.VISIBLE);
                            textDate.setText(pojo.getmDefaultStartDate() + " To " + pojo.getmDefaultEndDate());
                        } else {
                            textDate.setVisibility(View.VISIBLE);
                            textDate.setText(formDate + " To " + toDate);
                        }
                        if (SharedPref.read(SharedPref.selected_default_yr, "").equals("23-24")) {
                            StartDate_filter = "01/04/2023";
                            Enddate_filter = CurrentDateTime.getCurrentDateDDMMYYY();
                        }
                        else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("22-23")) {
                            StartDate_filter = "01/04/2022";
                            Enddate_filter = "31/03/2023";
                        } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("21-22")) {
                            StartDate_filter = "01/04/2021";
                            Enddate_filter = "31/03/2022";
                        } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("20-21")) {
                            StartDate_filter = "01/04/2020";
                            Enddate_filter = "31/03/2021";
                        } else {
                            StartDate_filter = pojo.getmDefaultStartDate();
                            Enddate_filter = pojo.getmDefaultEndDate();
                        }
                        String open_bla = pojo.getOpeningBal();
                        String clos_bla = pojo.getClosingBal();
                        binding.currentBalLeg.setText(open_bla);
                        binding.closingBal.setText(clos_bla);
                        // Enddate_filter = pojo.getEnddate();
                        SharedPref.write(SharedPref.END_DATE, pojo.getEnddate());
                        SharedPref.write(SharedPref.START_DATE, pojo.getStartDate());

                        binding.includeProgress.progress.setVisibility(View.GONE);
                        binding.includeProgress.noData.setVisibility(View.VISIBLE);
                        binding.currentBalLeg.setVisibility(View.VISIBLE);
                        ledgerReportAdapter.notifyDataSetChanged();
//                        binding.currentBalLeg.setText("0");
                        AlertUtil.responseElse(mContext, "GetLedgerReportWithBalance ", pojo.getResponseMessage() + "");
                    }
                    }catch (JsonIOException e){
                        AlertUtil.responseExecption(mContext, "GetLedgerReportWithBalance ", e.toString());
                    }
                }, error -> {
            AlertUtil.responseError(mContext, "GetLedgerReportWithBalance ", error.toString());
            binding.includeProgress.progress.setVisibility(View.GONE);
            binding.includeProgress.noData.setVisibility(View.VISIBLE);
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN,""));
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FROMDATE\":\"" + formDate + "\",\"TODATE\":\"" + toDate + "\",\"Status\":\"" + status + "\"" +
                        ",\"AVGDATE\":\"" + "null" + "\",\"TICK\":\"" + tick + "\",\"DBNAME\":\"" + db_name + "\",\"LEDGERTYPE\":\"" + ledger_type + "\"}";
                Log.e("str", str);
                return str.getBytes();
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

    public  void GetCompleteLedgerPDF(String formDate, String toDate, String status, String tick, String db_name, String ledger_type) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_COMPLETE_LEDGER_PDF,
                response -> {
                    Log.e("LedgerPdfResponse", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("ResponseStatus")) {
                            String download_pdf = jsonObject.optString("CompletePDF");
                           startActivity(new Intent(this, ViewPDFActivity.class)
                                    .putExtra("pdfUrl",download_pdf));
                        } else {
                            AlertUtil.responseElse(mContext, "GetCompleteLedgerPDF ", jsonObject.optString("ResponseMessage") + "");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AlertUtil.responseExecption(mContext, "GetCompleteLedgerPDF ", e.toString());

                    }
                }, error -> {
            AlertUtil.responseError(mContext, "GetCompleteLedgerPDF ", error.toString());
            // progressBar.cancel();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN,""));
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob = SharedPref.read(SharedPref.USERMOBILE, "");
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FROMDATE\":\"" + formDate + "\",\"TODATE\":\"" + toDate + "\",\"Status\":\"" + status + "\"" +
                        ",\"AVGDATE\":\"" + "null" + "\",\"TICK\":\"" + tick + "\",\"DBNAME\":\"" + db_name + "\",\"LEDGERTYPE\":\"" + ledger_type + "\"}";
                Log.e("LedgerPdfStr", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void setChecked() {
        if (isAllEntryCheckrd) {
            all_entry.setChecked(true);
            clear_entry.setChecked(false);
            unclear_entry.setChecked(false);
            isClearEntryCheckrd = false;
            isUnclearEntryCheckrd = false;
        } else if (isClearEntryCheckrd) {
            all_entry.setChecked(false);
            clear_entry.setChecked(true);
            unclear_entry.setChecked(false);
            isAllEntryCheckrd = false;
            isUnclearEntryCheckrd = false;
        } else if (isUnclearEntryCheckrd) {
            all_entry.setChecked(false);
            clear_entry.setChecked(false);
            unclear_entry.setChecked(true);
            isAllEntryCheckrd = false;
            isClearEntryCheckrd = false;
        }
    }

    private void DR_CR_setChecked() {
      //  Log.e("isDREntryCheckrd", isDREntryCheckrd + "");
       // Log.e("isCREntryCheckrd", isCREntryCheckrd + "");
        if (isDREntryCheckrd) {
            dr_entry.setChecked(true);
            cr_entry.setChecked(false);
            isCREntryCheckrd = false;
        } else if (isCREntryCheckrd) {
            dr_entry.setChecked(false);
            cr_entry.setChecked(true);
            isDREntryCheckrd = false;
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("UseCompatLoadingForDrawables")
    public void filterDialog2() {
        isFilterShowing = true;
        dialog = new Dialog(mContext, R.style.AppTheme);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogThemeUpDown;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.ledger_filter_dailog);
//        dialog.setCancelable(false);

        new Handler().post(() -> {
            filterStack.clear();
            // getFilters(FilterType.CLEAR,true);
            getFilters(FilterType.LEDGER);
        });


        adjustmentFilter = dialog.findViewById(R.id.adjustmentFilter);
        entryFilter = dialog.findViewById(R.id.entryFilter);
        accountFilter = dialog.findViewById(R.id.accountFilter);
        ledgerFilter_Date = dialog.findViewById(R.id.ledgerFilter_Date);
        progressBar = dialog.findViewById(R.id.progress);
        nodata = dialog.findViewById(R.id.no_data);
        ledger_Recy = dialog.findViewById(R.id.ledger_Recy);
        llRange = dialog.findViewById(R.id.ll_price_range);
        ledgerFilter_Date.setBackgroundColor(getResources().getColor(R.color.light_pink));
        ledgerDate = dialog.findViewById(R.id.ledgerDate);
        ledger_ToDate = dialog.findViewById(R.id.ledger_ToDate);

        count_aduj = dialog.findViewById(R.id.count_aduj);
        count_account = dialog.findViewById(R.id.count_account);
        count_entry = dialog.findViewById(R.id.count_entry);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        simpleDateFormat2 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);


        if (led_formDate.equals("null") && led_toDate.equals("null")) {
            ledgerDate.setText(StartDate_filter);
            ledger_ToDate.setText(Enddate_filter);
        } else {
            SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
            Date newDate = null;
            Date newDate1 = null;
            try {
                newDate = spf.parse(led_formDate);
                newDate1 = spf.parse(led_toDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat("dd/MM/yyyy");
            //spf = new SimpleDateFormat("dd/MM/yyyy");
            led_formDate = spf.format(newDate);
            led_toDate = spf.format(newDate1);
            ledgerDate.setText(led_formDate);
            ledger_ToDate.setText(led_toDate);
        }

        ledgerDate.setOnClickListener(v -> {
            // Log.e("selected_default_yr",SharedPref.read(SharedPref.selected_default_yr,"")+"tytrty");
            //   Log.e("cx", CurrentDateTime.getCurrentDateDDMMYYY());
            flag = "from";
            // StartDate_filter = ledgerDate.getText().toString();
            if (SharedPref.read(SharedPref.FY_StartDate, "").equals("")) {
                StartDate_filter = ledgerDate.getText().toString();
            } else {
                StartDate_filter = SharedPref.read(SharedPref.FY_StartDate, "");

            }
            Log.e("startDate", SharedPref.read(SharedPref.FY_StartDate, ""));
            if (!SharedPref.read(SharedPref.selected_default_yr, "").equals("2022-23"))
                Enddate_filter = SharedPref.read(SharedPref.FY_EndDate, "");
            else {
                Enddate_filter = CurrentDateTime.getCurrentDateDDMMYYY();
            }

            // StartDate_filter = ledgerDate.getText().toString();
            //Enddate_filter = ledger_ToDate.getText().toString();
            String[] items1 = StartDate_filter.split("/");
            String[] items2 = Enddate_filter.split("/");
            //  Log.e("date_error",items1[0]);
            // Log.e("date_error2",items2[0]);
            String yy = items1[2];
            String mm = items1[1];
            String dd = items1[0];
            String yy1 = items2[2];
            String mm2 = items2[1];
            String dd3 = items2[0];
            showDate(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), Integer.parseInt(yy1), Integer.parseInt(mm2) - 1, Integer.parseInt(dd3), R.style.NumberPickerStyle);
            filterStack.clear();
            filterChanged(FilterType.DATE);
            getFilters(FilterType.CLEAR);
        });
        ledger_ToDate.setOnClickListener(v -> {
          //  Log.e("dare", SharedPref.read(SharedPref.FY_StartDate, "") + "---" + SharedPref.read(SharedPref.selected_default_yr, ""));
            flag = "to";
            // StartDate_filter = "01/04/2020";

            StartDate_filter = ledgerDate.getText().toString();
            //  StartDate_filter = ledgerDate.getText().toString();
            //  Enddate_filter = ledger_ToDate.getText().toString();
            if (!SharedPref.read(SharedPref.selected_default_yr, "").equals("2022-23"))
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
            filterChanged(FilterType.DATE);
            getFilters(FilterType.CLEAR);
        });

        ImageView cancel = dialog.findViewById(R.id.cancle_i);
        TextView clearAll = dialog.findViewById(R.id.clear_all);
        clearAll.setOnClickListener(v -> {
            isFilterShowing = false;
            filterStack.clear();
            // ledgerDate.setText(SharedPref.read(SharedPref.START_DATE, ""));
            // ledger_ToDate.setText(SharedPref.read(SharedPref.END_DATE, ""));
            count_aduj.setText("0");
            count_account.setText("0");
            count_entry.setText("0");
            Log.e("selected_default_yr",SharedPref.read(SharedPref.selected_default_yr, ""));
            if (SharedPref.read(SharedPref.selected_default_yr, "").equals("23-24")) {
                ledgerDate.setText("01/04/2023");
                ledger_ToDate.setText(CurrentDateTime.getCurrentDateDDMMYYY());
            }
            else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("22-23")) {
                ledgerDate.setText("01/04/2022");
                ledger_ToDate.setText("31/03/2023");
            } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("21-22")) {
                ledgerDate.setText("01/04/2021");
                ledger_ToDate.setText("31/03/2022");
            } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("20-21")) {
                ledgerDate.setText("01/04/2020");
                ledger_ToDate.setText("31/03/2021");
            }else {
                ledgerDate.setText("01/04/2023");
                ledger_ToDate.setText(CurrentDateTime.getCurrentDateDDMMYYY());
            }
            getFilters(FilterType.CLEAR);
        });
        TextView apply = dialog.findViewById(R.id.apply);
        cancel.setOnClickListener(v -> {
            isFilterShowing = false;
            adjustmentTypeList.forEach(item -> item.setSelected(false));
            entryTypeList.forEach(item -> item.setSelected(false));
            accountTypeList.forEach(item -> item.setSelected(false));
            Count = count_aduj.getText().toString();
            Count3 = count_account.getText().toString();
            Count2 = count_entry.getText().toString();
            dialog.dismiss();
        });
        apply.setOnClickListener(v -> {
            isFilterShowing = false;
            //Brand
            StartDate_filter = ledgerDate.getText().toString();
            Enddate_filter = ledger_ToDate.getText().toString();
            Count = count_aduj.getText().toString();
            Count3 = count_account.getText().toString();
            Count2 = count_entry.getText().toString();

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

            List<AdjustmentType> isSelected = adjustmentTypeList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
            String brand_array;
            if (isSelected.size() > 0) {
                StringBuilder sb = new StringBuilder();
                StringBuilder sb1 = new StringBuilder();

                brand_array = new Gson().toJson(isSelected);
                try {
                    JSONArray jsonArray = new JSONArray(brand_array);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objects = jsonArray.getJSONObject(i);
                        String name = objects.getString("AdjustmentName");
                        if (name.equalsIgnoreCase("Unclear")) {
                            sb.append("0");
                            sb1.append("false");
                        } else if (name.equalsIgnoreCase("clear")) {
                            sb.append("1");
                            sb1.append("true");
                        } else if (name.equalsIgnoreCase("All")) {
                            sb.append("null");
                            sb1.append("null");
                        }
                     //   Log.e("adjustment_name", name);
                    }
                    String sbb = sb.toString();
                    String sbb1 = sb1.toString();
                    adjustment = sbb;
                    tickforPdf = sbb1;
                    Log.e("sbb", sbb);
                } catch (Exception ignored) {

                }
            } else {
                adjustment = "null";
                tickforPdf = "null";
            }
            List<AccountType> isSelected1 = accountTypeList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
            String accountArray;

            if (isSelected1.size() > 0) {
                accountArray = new Gson().toJson(isSelected1);
                try {
                    StringBuilder sb = new StringBuilder();
                    JSONArray jsonArray = new JSONArray(accountArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objects = jsonArray.getJSONObject(i);
                        String name = objects.getString("AccountTypeName");
                        sb.append(name).append(",");
                       // Log.e("account_name", name);
                    }
                    String sbb = sb.toString();
                    account = sbb;
                  //  Log.e("account_name", sbb);
                } catch (Exception e) {
                }
            } else {
                account = "null";
            }
            List<EntryType> isSelected2 = entryTypeList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
            String entryType_array;
            if (isSelected2.size() > 0) {
                entryType_array = new Gson().toJson(isSelected2);
                try {
                    StringBuilder sb = new StringBuilder();
                    JSONArray jsonArray = new JSONArray(entryType_array);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objects = jsonArray.getJSONObject(i);
                        String name = objects.getString("EntryTypeName");
                        sb.append(name);
                      //  Log.e("entry_name", name);
                    }
                    String sbb = sb.toString();
                    entry = sbb;

                  //  Log.e("supplier_list", sbb);
                } catch (Exception e) {

                }
            } else {
                entry = "null";
            }
         //   Log.e("led_formDate", ledgerDate.getText().toString() + "/" + ledger_ToDate.getText().toString());
            //  GetLedgerReport(ledgerDate.getText().toString(), ledger_ToDate.getText().toString(), entry,adjustment, dnNAME, account);
            SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date d1 = sdformat.parse(ledgerDate.getText().toString());
                Date d2 = sdformat.parse(ledger_ToDate.getText().toString());
                if (d1.compareTo(d2) < 0 || d1.compareTo(d2) == 0) {
                    startActivity(new Intent(mContext, LedgerActivity.class)
                            .putExtra("ledgerDate", ledgerDate.getText().toString())
                            .putExtra("ledger_ToDate", ledger_ToDate.getText().toString())
                            .putExtra("entry", entry)
                            .putExtra("account", account)
                            .putExtra("tickforPdf", tickforPdf)
                            .putExtra("adjustment", adjustment));
                    EntryTypeAdapter.lastSelectedPosition=-1;
                    finish();
                    dialog.dismiss();
                } else {
                    Toast.makeText(mContext, "From Date छोटी होनी चाहिए To Date से", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        ledgerDate.setBackground(getResources().getDrawable(R.drawable.selected_button));
        ledgerDate.setTextColor(getResources().getColor(R.color.white));
        ledger_ToDate.setBackground(getResources().getDrawable(R.drawable.selected_button));
        ledger_ToDate.setTextColor(getResources().getColor(R.color.white));

        isDatePressed = true;
        ledgerFilter_Date.setBackground(getResources().getDrawable(R.drawable.selected_button));
        ledgerFilter_Date.setTextColor(getResources().getColor(R.color.white));
        nodata.setVisibility(View.GONE);
        ledgerFilter_Date.setOnClickListener(v -> {
            nodata.setVisibility(View.GONE);
            isDatePressed = true;
            ledgerFilter_Date.setBackground(getResources().getDrawable(R.drawable.selected_button));
            ledger_ToDate.setTextColor(getResources().getColor(R.color.white));
            if (isAdjustmentPlace || isAccountPlace || isEntryPlace) {
                llRange.setVisibility(View.VISIBLE);
                ledger_Recy.setVisibility(View.GONE);
                count_aduj.setTextColor(getResources().getColor(R.color.black));
                count_account.setTextColor(getResources().getColor(R.color.black));
                count_entry.setTextColor(getResources().getColor(R.color.black));
                adjustmentFilter.setBackground(getResources().getDrawable(R.drawable.text_bg));
                adjustmentFilter.setTextColor(getResources().getColor(R.color.black));
                accountFilter.setBackground(getResources().getDrawable(R.drawable.text_bg));
                accountFilter.setTextColor(getResources().getColor(R.color.black));
                entryFilter.setBackground(getResources().getDrawable(R.drawable.text_bg));
                entryFilter.setTextColor(getResources().getColor(R.color.black));
                isAdjustmentPlace = false;
                isAccountPlace = false;
                isEntryPlace = false;
            }
        });

        adjustmentFilter.setOnClickListener(v -> {
            isAdjustmentPlace = true;
            adjustmentFilter.setBackground(getResources().getDrawable(R.drawable.selected_button));
            adjustmentFilter.setTextColor(getResources().getColor(R.color.white));
//            keyTypeList = "BRANCH";
//            Log.e("filter", keyTypeList);
//             if (isDatePressed || isAccountPlace || isEntryPlace) {
            llRange.setVisibility(View.GONE);
            ledger_Recy.setVisibility(View.VISIBLE);
            count_entry.setTextColor(getResources().getColor(R.color.black));
            count_account.setTextColor(getResources().getColor(R.color.black));
            count_aduj.setTextColor(getResources().getColor(R.color.white));
            ledgerFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
            ledgerFilter_Date.setTextColor(getResources().getColor(R.color.black));
            accountFilter.setBackground(getResources().getDrawable(R.drawable.text_bg));
            accountFilter.setTextColor(getResources().getColor(R.color.black));
            entryFilter.setBackground(getResources().getDrawable(R.drawable.text_bg));
            entryFilter.setTextColor(getResources().getColor(R.color.black));
            ledger_Recy.setAdapter(adjustmentTypeAdapter);
            isDatePressed = false;
            isAccountPlace = false;
            isEntryPlace = false;
            // }
            getFilters(FilterType.ADJUSTMENT);
        });
        entryFilter.setOnClickListener(v -> {
            isEntryPlace = true;
            entryFilter.setBackground(getResources().getDrawable(R.drawable.selected_button));
            entryFilter.setTextColor(getResources().getColor(R.color.white));
//            keyTypeList = "SUBPARTY";
//            Log.e("filter", keyTypeList);
//             if (isDatePressed || isAdjustmentPlace || isAccountPlace) {
            ledger_Recy.setVisibility(View.VISIBLE);
            llRange.setVisibility(View.GONE);
            count_entry.setTextColor(getResources().getColor(R.color.white));
            count_aduj.setTextColor(getResources().getColor(R.color.black));
            count_account.setTextColor(getResources().getColor(R.color.black));
            ledgerFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
            ledgerFilter_Date.setTextColor(getResources().getColor(R.color.black));
            adjustmentFilter.setBackground(getResources().getDrawable(R.drawable.text_bg));
            adjustmentFilter.setTextColor(getResources().getColor(R.color.black));
            accountFilter.setBackground(getResources().getDrawable(R.drawable.text_bg));
            accountFilter.setTextColor(getResources().getColor(R.color.black));
            ledger_Recy.setAdapter(entryTypeAdapter);
            isDatePressed = false;
            isAdjustmentPlace = false;
            isAccountPlace = false;
            // }
            getFilters(FilterType.ENTRY);
        });
        accountFilter.setOnClickListener(v -> {
            isAccountPlace = true;
            accountFilter.setBackground(getResources().getDrawable(R.drawable.selected_button));
            accountFilter.setTextColor(getResources().getColor(R.color.white));
            // if (isDatePressed || isAdjustmentPlace || isEntryPlace) {
            ledger_Recy.setVisibility(View.VISIBLE);
            llRange.setVisibility(View.GONE);
            count_aduj.setTextColor(getResources().getColor(R.color.black));
            count_account.setTextColor(getResources().getColor(R.color.white));
            count_entry.setTextColor(getResources().getColor(R.color.black));
            ledgerFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
            ledgerFilter_Date.setTextColor(getResources().getColor(R.color.black));
            adjustmentFilter.setBackground(getResources().getDrawable(R.drawable.text_bg));
            adjustmentFilter.setTextColor(getResources().getColor(R.color.black));
            entryFilter.setBackground(getResources().getDrawable(R.drawable.text_bg));
            entryFilter.setTextColor(getResources().getColor(R.color.black));
            ledger_Recy.setAdapter(accountTypeAdapter);
            isDatePressed = false;
            isAdjustmentPlace = false;
            isEntryPlace = false;
            // }
            getFilters(FilterType.ACCOUNT);
        });

        dialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                isFilterShowing = false;
                dialog.dismiss();
            }
            return true;
        });
        //   getSIze(getIntent().getStringExtra("d_code"),false);
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getFilters(FilterType mFilterType) {
        progressBar.setVisibility(View.VISIBLE);
        LedgerFilterRequest request;
        request = new LedgerFilterRequest(
                ledgerDate.getText().toString(), ledger_ToDate.getText().toString(), "LEDGERREPORT",
                SharedPref.read(SharedPref.PARTY_CODE, ""),
                accountTypeList.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterType.ACCOUNT))
                        .collect(Collectors.toList()),
                entryTypeList.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterType.ENTRY))
                        .collect(Collectors.toList()),
                adjustmentTypeList.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterType.ADJUSTMENT))
                        .collect(Collectors.toList()),
                SharedPref.read(SharedPref.DB_NAME, "")
        );

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_LIST_NEW, response -> {
           // Log.e("Data", response);
            LedgerPogo pojo = new Gson().fromJson(response, adjustmentType);
            count_aduj.setText(pojo.getmAdjustmentTypeCount());
            count_entry.setText(pojo.getmEntryTypeCount());
            count_account.setText(pojo.getmAccountTypeCount());
            Log.e("mFilterType",mFilterType+"");
            Log.e("filterStack",filterStack+"");
            if (pojo.getResponseStatus()) {
                progressBar.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
                switch (mFilterType) {
                    case ADJUSTMENT:
                        List<AdjustmentType> prevAdjustmentList = new ArrayList<>(adjustmentTypeList);
                        List<AdjustmentType> size = prevAdjustmentList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterType.ADJUSTMENT))
                                .collect(Collectors.toList());
                    //    Log.e("size", size.size() + "");

                        adjustmentTypeList.clear();
                        if (!filterStack.contains(mFilterType) || ledgerPogo == null || ledgerPogo.getAdjustmentType() == null || ledgerPogo.getAdjustmentType().isEmpty()) {
                            adjustmentTypeList.addAll(pojo.getAdjustmentType());
                            count_aduj.setText("0");
                        } else {
                            count_aduj.setText(size.size() + "");
                            ledgerPogo.getAdjustmentType().forEach(adjustment -> {
                                prevAdjustmentList.forEach(adjustment1 -> {
                                    if (adjustment.getAdjustmentName().equals(adjustment1.getAdjustmentName())) {
                                        adjustment.setSelected(adjustment1.isSelected());
                                    }
                                });
                            });
                            adjustmentTypeList.addAll(prevAdjustmentList);
                            //  adjustmentTypeList.addAll(ledgerPogo.getAdjustmentType());
                        }
                        adjustmentTypeAdapter.notifyDataSetChanged();

                        break;
                    case ACCOUNT:
                        List<AccountType> prevAccountList = new ArrayList<>(accountTypeList);
                        List<AccountType> size1 = prevAccountList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterType.ACCOUNT))
                                .collect(Collectors.toList());
                        //Log.e("size", size1.size() + "");

                        accountTypeList.clear();
                        if (!filterStack.contains(mFilterType) || ledgerPogo == null || ledgerPogo.getAccountType() == null || ledgerPogo.getAccountType().isEmpty()) {
                            accountTypeList.addAll(pojo.getAccountType());
                          //  Log.e("if","if");
                            count_account.setText(size1.size() + "");
                        } else {
                          //  Log.e("else","else");
                            count_account.setText(size1.size() + "");
                            Log.e("getAccountType", new Gson().toJson( ledgerPogo.getAccountType()));
                            Log.e("prevAccountList", new Gson().toJson(prevAccountList));
                            ledgerPogo.getAccountType().forEach(account -> {
                                prevAccountList.forEach(account1 -> {
                                    if (account.getAccountTypeName().equals(account1.getAccountTypeName())) {
                                        account.setSelected(account1.isSelected());
                                    }
                                });
                            });
                            accountTypeList.addAll(prevAccountList);
                            // accountTypeList.addAll(ledgerPogo.getAccountType());
                        }
                        accountTypeAdapter.notifyDataSetChanged();
                        break;
                    case ENTRY:
                        List<EntryType> prevEntryList = new ArrayList<>(entryTypeList);
                      //  Log.e("prevEntryList", new Gson().toJson(prevEntryList));
                        List<EntryType> size11 = prevEntryList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterType.ENTRY))
                                .collect(Collectors.toList());
                        entryTypeList.clear();
                        if (!filterStack.contains(mFilterType) || ledgerPogo == null || ledgerPogo.getEntryType() == null || ledgerPogo.getEntryType().isEmpty()) {
                            entryTypeList.addAll(pojo.getEntryType());
                            onCheckChangeReferesh();
                            Log.e("pojo",new Gson().toJson(pojo.getEntryType()));
                          //  count_entry.setText( "0");
                        //    Toast.makeText(mContext, "if", Toast.LENGTH_SHORT).show();
                        } else {
                          //  Toast.makeText(mContext, "else", Toast.LENGTH_SHORT).show();
                            //count_entry.setText( size11.size()+"");
                            ledgerPogo.getEntryType().forEach(entry -> {
                                prevEntryList.forEach(entry1 -> {
                                    if (entry.getEntryTypeName().equals(entry1.getEntryTypeName())) {
                                        entry.setSelected(entry1.isSelected());
                                    }
                                });
                            });
                            entryTypeList.addAll(prevEntryList);
                            // entryTypeList.addAll(ledgerPogo.getEntryType());
                         //   Log.e("entryTypestaftrclearels", new Gson().toJson(entryTypeList));
                        }
                        entryTypeAdapter.notifyDataSetChanged();
                        break;
                    case CLEAR:
                        adjustmentTypeList.clear();
                        adjustmentTypeList.addAll(pojo.getAdjustmentType());
                        accountTypeList.clear();
                        accountTypeList.addAll(pojo.getAccountType());
                        entryTypeList.clear();
                        entryTypeList.addAll(pojo.getEntryType());
                        EntryTypeAdapter.lastSelectedPosition=-1;
                      //  Log.e("entryTypeList",new Gson().toJson(pojo.getEntryType()));
                        adjustmentTypeAdapter.notifyDataSetChanged();
                        entryTypeAdapter.notifyDataSetChanged();
                        accountTypeAdapter.notifyDataSetChanged();

                    case LEDGER:
                        ledgerPogo = pojo;
                        break;
                }
            } else {
                progressBar.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                adjustmentTypeList.clear();
                accountTypeList.clear();
                entryTypeList.clear();
                adjustmentTypeAdapter.notifyDataSetChanged();
                entryTypeAdapter.notifyDataSetChanged();
                accountTypeAdapter.notifyDataSetChanged();

            }
            // }
        }, error -> {
            progressBar.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
            adjustmentTypeList.clear();
            accountTypeList.clear();
            entryTypeList.clear();
            adjustmentTypeAdapter.notifyDataSetChanged();
            entryTypeAdapter.notifyDataSetChanged();
            accountTypeAdapter.notifyDataSetChanged();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = new Gson().toJson(request);
              //  Log.e("str", str);
                return str.getBytes();
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
            ledgerDate.setText(simpleDateFormat.format(calendar.getTime()));
            if (simpleDateFormat.format(calendar.getTime()).equals(CurrentDateTime.getCurrentDateDDMMYYY())) {
                ledger_ToDate.setText(ledgerDate.getText().toString());
            }
        } else if (flag.equals("to")) {
            ledger_ToDate.setText(simpleDateFormat2.format(calendar.getTime()));
        }
    }
    @VisibleForTesting
    void showDate(int year1, int monthOfYear1, int dayOfMonth1, int year2, int monthOfYear2, int dayOfMonth2, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(LedgerActivity.this)
                .callback(LedgerActivity.this)
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
                    .context(LedgerActivity.this)
                    .callback(LedgerActivity.this)
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
    public void filterChanged(FilterType mFilterType) {
        if (filterStack.contains(mFilterType)) {
            while (filterStack.pop() != mFilterType) {

            }
        }
        filterStack.push(mFilterType);
      //  Log.e("Seq", filterStack.toString());
    }


    public void networkConnetion3(Context mContext) {

        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.network_connetion_dailog, null);
        ImageView cross = dialogView.findViewById(R.id.cross);
        TextView try_button = dialogView.findViewById(R.id.try_button);
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.RoundedDialog);

        builder.setView(dialogView);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        cross.setOnClickListener(v -> alertDialog.dismiss());
        try_button.setOnClickListener(view -> {
            GetLedgerReport(led_formDate, led_toDate, "", adjustment, dnNAME, account, false);
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCheckChangeReferesh() {
        EntryTypeAdapter.lastSelectedPosition = -1;
        entryTypeAdapter.notifyDataSetChanged();
        count_entry.setText("0");
       //  Log.e("check", "call");
    }
}

//    public void filterDialog() {
//        isFilterShowing = true;
//        final Dialog dialog = new Dialog(mContext, R.style.AppTheme);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogThemeUpDown;
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.ledger_dailog);
//        llRange = dialog.findViewById(R.id.ll_price_range);
//        ledgerDate = dialog.findViewById(R.id.ledgerDate);
//        ledger_ToDate = dialog.findViId(R.id.clear_entry);
//        unclear_entry=dialog.findViewByewById(R.id.ledger_ToDate);
//////        all_entry=dialog.findViewById(R.id.all_entry);
//////        clear_entry=dialog.findViewById(R.id.unclear_enter);
//        dr_entry=dialog.findViewById(R.id.dr_entry);
//
//        cr_entry=dialog.findViewById(R.id.cr_entry);
////        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
////        simpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
//        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
//        simpleDateFormat2 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
//        if (tick.equals("null"))
//        {
//            all_entry.setChecked(true);
//            clear_entry.setChecked(false);
//            unclear_entry.setChecked(false);
//        }
//        else if (tick.equals("1"))
//        {
//            all_entry.setChecked(false);
//            clear_entry.setChecked(true);
//            unclear_entry.setChecked(false);
//        }
//        else  if (tick.equals("0"))
//        {
//            all_entry.setChecked(false);
//            clear_entry.setChecked(false);
//            unclear_entry.setChecked(true);
//        }
//        else
//        {
//            all_entry.setChecked(true);
//            clear_entry.setChecked(false);
//            unclear_entry.setChecked(false);
//
//        }
//        if (status.equals("null"))
//        {
//            dr_entry.setChecked(false);
//            cr_entry.setChecked(false);
//
//        }
//        else if (status.equals("Credit"))
//        {
//            dr_entry.setChecked(false);
//            cr_entry.setChecked(true);
//        }
//        else if (status.equals("Debit"))
//        {
//            dr_entry.setChecked(true);
//            cr_entry.setChecked(false);
//        }
////        ledgerDate.setText(led_formDate);
////        ledger_ToDate.setText(led_toDate);
//
//
//        ImageView cancel = dialog.findViewById(R.id.cancle_i);
//        TextView clearAll = dialog.findViewById(R.id.clear_all);
////        ledgerDate.setText("01/04/2021");
////        ledger_ToDate.setText("31/03/2022");
//
//
//
//        if (led_formDate.equals("null")&& led_toDate.equals("null"))
//        {
//            ledgerDate.setText(SharedPref.read(SharedPref.FY_StartDate,""));
//            ledger_ToDate.setText(SharedPref.read(SharedPref.FY_EndDate,""));
//        }
//        else {
//            SimpleDateFormat spf = new SimpleDateFormat("yyyy/MM/dd");
//            Date newDate = null;
//            Date newDate1 = null;
//            try {
//                newDate = spf.parse(led_formDate);
//                newDate1 = spf.parse(led_toDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            spf = new SimpleDateFormat("dd/MM/yyyy");
//            //spf = new SimpleDateFormat("dd/MM/yyyy");
//            led_formDate = spf.format(newDate);
//            led_toDate = spf.format(newDate1);
//            ledgerDate.setText(led_formDate);
//            ledger_ToDate.setText(led_toDate);
//        }
////        ledgerDate.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                flag = "from";
////                led_formDate=ledgerDate.getText().toString();
////                led_toDate=ledger_ToDate.getText().toString();
////                String[] items1 = led_formDate.split("/");
////                String[] items2 = led_toDate.split("/");
////                String yy=items1[2];
////                String mm=items1[1];
////                String dd=items1[0];
////                String yy1=items2[2];
////                String mm2=items2[1];
////                String dd3=items2[0];
////                showDate(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd),Integer.parseInt(yy1),Integer.parseInt(mm2) - 1, Integer.parseInt(dd3), R.style.NumberPickerStyle);
////
////            }
////        });
////
////        ledger_ToDate.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                flag = "to";
////                led_formDate=ledgerDate.getText().toString();
////                led_toDate=ledger_ToDate.getText().toString();
////                String[] items1 = led_formDate.split("/");
////                String[] items2 = led_toDate.split("/");
////                String yy=items1[2];
////                String mm=items1[1];
////                String dd=items1[0];
////                String yy1=items2[2];
////                String mm2=items2[1];
////                String dd3=items2[0];
////                showDate2(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd),Integer.parseInt(yy1),Integer.parseInt(mm2) - 1, Integer.parseInt(dd3), R.style.NumberPickerStyle);
////            }
////        });
//        clearAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isFilterShowing = false;
//                led_formDate ="";
//                led_toDate ="";
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        ledgerDate.setText("01/04/2021");
//                        ledger_ToDate.setText("31/03/2022");
//                        if (isAllEntryCheckrd|| isClearEntryCheckrd||isUnclearEntryCheckrd||isDREntryCheckrd|| isCREntryCheckrd)
//                        {
//                            tick="null";
//                            Log.e("tick",tick);
//                            status="null";n
//                            Log.e("status",status);
//                            all_entry.setChecked(true);
//                            cr_entry.setChecked(false);
//                            unclear_entry.setChecked(false);
//                            dr_entry.setChecked(false);
//                            dr_entry.setChecked(false);
//                        }
//                        else {
//                            tick="null";
//                            Log.e("tick",tick);
//                            status="null";
//                            Log.e("status",status);
//                            all_entry.setChecked(true);
//                            cr_entry.setChecked(false);
//                            unclear_entry.setChecked(false);
//                            dr_entry.setChecked(false);
//                            dr_entry.setChecked(false);
//                        }
//                    }
//                },3000);
//            }
//        });
//        TextView apply = dialog.findViewById(R.id.apply);
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isFilterShowing = false;
//
//                if (trueFalse.equals(""))
//                {
//                    ledgerDate.setText("01/04/2021");
//                    ledger_ToDate.setText("31/03/2022");
//                }
//                else if (trueFalse.equals("true"))
//                {
//                    led_formDate = SelectedFdate;
//                    led_toDate = SelectedTdate;
//                    SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
//                    Date newDate = null;
//                    Date newDate1 = null;
//
//                    try {
//                        newDate = spf.parse(led_formDate);
//                        newDate1 = spf.parse(led_toDate);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    spf = new SimpleDateFormat("yyyy/MM/dd");
//                    led_formDate = spf.format(newDate);
//                    led_toDate = spf.format(newDate1);
//                }
//                dialog.dismiss();
//            }
//        });
//
//        apply.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View v) {
//                isFilterShowing = false;
//                //Brand
//                led_formDate = ledgerDate.getText().toString();
//                led_toDate = ledger_ToDate.getText().toString();
//                SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
//                Date newDate = null;
//                Date newDate1 = null;
//                try {
//                    newDate = spf.parse(led_formDate);
//                    newDate1 = spf.parse(led_toDate);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                spf = new SimpleDateFormat("yyyy/MM/dd");
//                led_formDate = spf.format(newDate);
//                led_toDate = spf.format(newDate1);
//
////
//                if (all_entry.isChecked())
//                {
//                    tick="null";
//                    Log.e("check",tick);
//                }
//                if (clear_entry.isChecked())
//                {
//                    tick="1";
//                    Log.e("check",tick);
//                }
//                if (unclear_entry.isChecked())
//                {
//                    tick="0";
//                    Log.e("check",tick);
//                }
//                if (dr_entry.isChecked())
//                {
//                    status="Debit";
//                    Log.e("check status",status);
//                }
//                if (cr_entry.isChecked())
//                {
//                    status="Credit";
//                    Log.e("check status",status);
//                }
//                GetLedgerReport(led_formDate,led_toDate,status,tick,dnNAME,account);
//
//                dialog.dismiss();
//            }
//        });
//
//        all_entry.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener()
//        {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked)
//                {
//                    //Toast.makeText(MainActivity.this, "mcheckboxale", Toast.LENGTH_SHORT).show();
//                    //  tick="null";
//                    Log.e("tick",tick);
//                    clear_entry.setChecked(false);
//                    isClearEntryCheckrd = false;
//                    unclear_entry.setChecked(false);
//                    isUnclearEntryCheckrd = false;
//                    all_entry.setChecked(true);
//                    isAllEntryCheckrd = true;// disable
//                    // disable
//
//                } else {
//                    //   tick="null";
//                    Log.e("tick",tick);
//                    isAllEntryCheckrd = false;
//                    all_entry.setChecked(false);
//                    isClearEntryCheckrd = false;
//                    clear_entry.setChecked(false);
//                    isUnclearEntryCheckrd = false;// disable
//                    unclear_entry.setChecked(false);
//                }
//            }
//        });
//
//        clear_entry.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener()
//        {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked) {
//                    //Toast.makeText(MainActivity.this, "male", Toast.LENGTH_SHORT).show();
//                    isAllEntryCheckrd = false;
//                    all_entry.setChecked(false);
//                    isUnclearEntryCheckrd = false;
//                    unclear_entry.setChecked(false);
//                    // tick="1";
//                    Log.e("tick",tick);
//                    clear_entry.setChecked(true);
//                    isClearEntryCheckrd = true;// disable checkbox
//                }else {
//                    //  tick = "1";
//                    Log.e("tick", tick);
//                    isAllEntryCheckrd = false;
//                    all_entry.setChecked(false);
//                    isClearEntryCheckrd = false;
//                    clear_entry.setChecked(false);
//                    isUnclearEntryCheckrd = false;// disable checkbox
//                    unclear_entry.setChecked(false);
//                }
//
//            }
//        });
//
//        unclear_entry.setOnCheckedChangeListener ((compoundButton, isChecked) -> {
//            if (isChecked) {
//                //  tick="0";
//                Log.e("tick",tick);
//                isAllEntryCheckrd = false;
//                all_entry.setChecked(false);
//                isClearEntryCheckrd = false;
//                clear_entry.setChecked(false);// disable checkbox
//                unclear_entry.setChecked(true);
//                isUnclearEntryCheckrd = true;
//            }else
//            {
//                //   tick="0";
//                Log.e("tick",tick);
//                isAllEntryCheckrd = false;
//                isClearEntryCheckrd = false;
//                isUnclearEntryCheckrd = false;
//                unclear_entry.setChecked(false);
//                all_entry.setChecked(false); // disable checkbox
//                clear_entry.setChecked(false);
//            }
//        });
//
//        dr_entry.setOnCheckedChangeListener ((compoundButton, isChecked) -> {
//            if (isChecked) {
////                    if (isFilterApplied == false) {
//                Log.e("isChecked", isChecked + "");
//                Log.e("isCREntryCheckrd11", isCREntryCheckrd + "");
//                cr_entry.setChecked(false);
//                isCREntryCheckrd = false;
//                dr_entry.setChecked(true);
//                isDREntryCheckrd = true;
//                // status = "Debit";
//                Log.e("isDREntryCheckrd11", isCREntryCheckrd + "");
////                    }else
////                        {}
//            }else {
//                // status="null";
//                Log.e("isChecked1",isChecked+"");
//                cr_entry.setChecked(false);
//                isCREntryCheckrd = false;
//                dr_entry.setChecked(false);
//                isDREntryCheckrd = false;
//
//                Log.e("isDREntryCheckrd2",isCREntryCheckrd+"");
//
//            }
//        });
//        cr_entry.setOnCheckedChangeListener ((compoundButton, isChecked) -> {
//            if (isChecked) {
//                Log.e("isChecked",isChecked+"");
//                Log.e("isCREntryCheckrd11",isCREntryCheckrd+"");
//                dr_entry.setChecked(false);
//                isDREntryCheckrd = false;
//                cr_entry.setChecked(true);
//                isCREntryCheckrd = true;
//
//                //Toast.makeText(MainActivity.this, "male", Toast.LENGTH_SHORT).show();
//                //status="Credit";
//                Log.e("isCREntryCheckrd11",isCREntryCheckrd+"");
//
//            }else {
//                //status="null";
//                Log.e("isChecked1",isChecked+"");
//                dr_entry.setChecked(false);
//                isDREntryCheckrd = false;
//                cr_entry.setChecked(false);
//                isCREntryCheckrd = false;
//                Log.e("isCREntryCheckrd2",isCREntryCheckrd+"");
//                //  dr_entry.setChecked(false);
//                // isDREntryCheckrd = false;
//            }
//        });
//  //   getSIze(getIntent().getStringExtra("d_code"),false);
//        dialog.show();
//    }

