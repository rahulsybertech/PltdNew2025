package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_COURIER_REPORT;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_FILTER_DETAIL_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_FILTER_LIST_NEW;

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
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.Interface.FilterChangedCourier;
import com.syber.ssspltd.Interface.OnCheckChangesCourier;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterCourierReport.Courier;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterCourierReport.CourierNo;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterCourierReport.CourierReportFilterRequest;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterCourierReport.CourierReportPojo;
import com.syber.ssspltd.NewFilter.PendingOrder.FilterCourierReport.Salebill;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.CurrentDateTime;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.CourierAdapter;
import com.syber.ssspltd.adapter.CourierReportAdap.CourierBillNoAdp;
import com.syber.ssspltd.adapter.CourierReportAdap.CourierNameAdp;
import com.syber.ssspltd.adapter.CourierReportAdap.CourierNoAdp;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.N_CourierReport.FilterCourierNoAdap;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.N_CourierReport.FilterCourierReportAdap;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.N_CourierReport.FilterSalebillAdap;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.N_CourierReport.FilterTypeCourierReport;
import com.syber.ssspltd.databinding.ActivityCourierReportBinding;
import com.syber.ssspltd.response.BranchListResponse.FilterListResult;
import com.syber.ssspltd.response.CourierNameRespons.FilterListPojo;
import com.syber.ssspltd.response.CourierReport.CourierReportPoojo;
import com.syber.ssspltd.response.CourierReport.CourierReportResult;
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

public class CourierReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, FilterChangedCourier, OnCheckChangesCourier {
    public static TextView count_Cname, count_CNo, count_CBill_No;
    static List<CourierReportResult> courierDetails;
    Context mContext = this;
    CourierAdapter courierAdapter;
    Type listType;
    LinearLayoutManager linearLayoutManager;
    TextView courierFilter_Date, courierFilter_Name, courierFilter_No, courierFilter_BillNo, nodata;
    RecyclerView recycler_courier;
    LinearLayout llRange;
    TextView formDate, todate;
    List<FilterListResult> FilterListDetails;
    String courierName = "null", courierNo = "null ", courierBill_no = "null", form_date = "null", to_Date = "null", dbNAME = SharedPref.read(SharedPref.DB_NAME, "");
    String keyTypeList = "";
    CourierNameAdp courierNameAdp;
    CourierBillNoAdp courierBillNoAdp;
    CourierNoAdp courierNoAdp;
    List<com.syber.ssspltd.response.CourierNameRespons.CourierNoRespo.FilterListResult> courierNoList;
    List<com.syber.ssspltd.response.CourierNameRespons.CourierBillNoRespo.FilterListResult> courierBillNoList;
    List<com.syber.ssspltd.response.CourierNameRespons.FilterListResult> courierNameList;
    Type courierNameType, courierNoType, courierBillNoType;
    Type courierNameType1, courierNoType1, courierBillNoType1;
    String flag = "";
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormat2;
    String Count = "", Count2 = "", Count3 = "";
    boolean isDatePressed = false, isNamePlace = false, isNoPlace = false, isbillNoPlace = false;
    boolean isFilterShowing = false;
    FilterCourierReportAdap filterCourierReportAdap;
    FilterCourierNoAdap filterCourierNoAdap;
    FilterSalebillAdap filterSalebillAdap;
    List<Courier> courier_List;
    List<CourierNo> courierNo_List;
    List<Salebill> salebill_List;
    Stack<FilterTypeCourierReport> filterStack;
    CourierReportPojo courierReportPojo;
    ProgressBar progressBar;
    String StartDate_filter, Enddate_filter;
    Dialog dialog;
    ImageView backImage3;
    private ActivityCourierReportBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourierReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Handle system bars (status + nav bar) insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.supportChat.supportFab.setOnClickListener((View.OnClickListener) v ->
                Lazy.openDialog(mContext));

        //new Filter
        filterStack = new Stack<>();
        courier_List = new ArrayList<>();
        courierNo_List = new ArrayList<>();
        salebill_List = new ArrayList<>();

        filterCourierReportAdap = new FilterCourierReportAdap(mContext, courier_List, this);
        filterCourierNoAdap = new FilterCourierNoAdap(mContext, courierNo_List, this);
        filterSalebillAdap = new FilterSalebillAdap(mContext, salebill_List, this);

        courierNameType1 = new TypeToken<CourierReportPojo>() {
        }.getType();
        courierNoType1 = new TypeToken<CourierReportPojo>() {
        }.getType();
        courierBillNoType1 = new TypeToken<CourierReportPojo>() {
        }.getType();
        //new Filter

        courierDetails = new ArrayList<>();
        courierNameList = new ArrayList<>();
        courierNoList = new ArrayList<>();
        courierBillNoList = new ArrayList<>();

        listType = new TypeToken<CourierReportPoojo>() {
        }.getType();
        courierNameType = new TypeToken<FilterListPojo>() {
        }.getType();
        courierNoType = new TypeToken<com.syber.ssspltd.response.CourierNameRespons.CourierNoRespo.FilterListPojo>() {
        }.getType();
        courierBillNoType = new TypeToken<com.syber.ssspltd.response.CourierNameRespons.CourierBillNoRespo.FilterListPojo>() {
        }.getType();


        courierAdapter = new CourierAdapter(mContext, courierDetails);
        binding.courierRecyclerview.setAdapter(courierAdapter);


        ImageView backImage = findViewById(R.id.back3);
        backImage.setImageDrawable(ContextCompat.getDrawable(CourierReportActivity.this, R.drawable.ic_baseline_keyboard_backspace));
        backImage.setOnClickListener(v -> onBackPressed());
        courierNameDetail("COURIERNAME");
        CourierNoDetail("COURIERNO");
        CourierBillNoDetail("SALEBILLNO");
        backImage3 = findViewById(R.id.download);
        backImage3.setImageDrawable(ContextCompat.getDrawable(CourierReportActivity.this, R.drawable.ic_filter));

        backImage3.setOnClickListener(v -> {
            if (!isFilterShowing) {
                filterDialog2();
                // filterDialog();
            } else {

            }
        });
        TextView backImage2 = findViewById(R.id.back2);
        backImage2.setText("COURIER REPORT ");

        if (Lazy.haveNetworkConnection(mContext)) {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.getStringExtra("formDate") != null || intent.getStringExtra("todate") != null) {
                    //  GetLedgerReport(ledgerDate.getText().toString(), ledger_ToDate.getText().toString(), entry,adjustment, dnNAME, account);
                    GetCourierReport(intent.getStringExtra("courierName"),
                            intent.getStringExtra("courierNo"), intent.getStringExtra("courierBill_no")
                            , intent.getStringExtra("formDate"), intent.getStringExtra("todate"), dbNAME, true);
                } else {

                    if (isSetFYDate()) {
                        GetCourierReport(courierName, courierNo, courierBill_no, StartDate_filter, Enddate_filter, dbNAME, false);
                    } else {
                        GetCourierReport(courierName, courierNo, courierBill_no, form_date, to_Date, dbNAME, false);
                    }
                }
            } else {

            }
        } else {
            networkConnetion3(mContext);
        }
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
        } else if (SharedPref.read(SharedPref.selected_default_yr, "").equals("2020-2021")) {
            StartDate_filter = "01/04/2020";
            Enddate_filter = "31/03/2021";
        } else {
            return false;

        }
        return true;
    }

    private void GetCourierReport(String courierName, String courierNo, String courierBill_no, String form_Date, String to_date, String db_name, boolean isisFilterApplied) {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_COURIER_REPORT, response -> {
            Log.i("TaG", "GetCourierReport resp : URl " + GET_COURIER_REPORT + " " + response);
            CourierReportPoojo pojo = new Gson().fromJson(response, listType);
            courierDetails.clear();
            System.out.println("GET_COURIER_RESPONSE_CODE " + pojo.getResponseStatus());
            try {
                if (pojo.getResponseStatus()) {
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    binding.includeProgress.noData.setVisibility(View.GONE);
                    courierDetails.addAll(pojo.getCourierReportResult());
                    courierAdapter.notifyDataSetChanged();

                    if (isSetFYDate() == false) {
                        StartDate_filter = pojo.getmDefaultStartDate();
                        Enddate_filter = pojo.getmDefaultEndDate();
                    }

                    // Enddate_filter = pojo.getEnddate();
                    SharedPref.write(SharedPref.END_DATE, pojo.getmEnddate());
                    SharedPref.write(SharedPref.START_DATE, pojo.getmStartDate());

                    Log.e("dateFilter", pojo.getmStartDate());
                    if (!isisFilterApplied) {
                        binding.tool.textDate.setVisibility(View.VISIBLE);
                        if (pojo.getmDefaultStartDate() != null && pojo.getmDefaultEndDate() != null && !pojo.getmDefaultStartDate().isEmpty() && !pojo.getmDefaultEndDate().isEmpty()) {
                            binding.tool.textDate.setText(pojo.getmDefaultStartDate() + " To " + pojo.getmDefaultEndDate());
                        } else {
                            binding.tool.textDate.setText("");
                        }
                    } else {
                        binding.tool.textDate.setVisibility(View.VISIBLE);
                        if (form_Date != null && to_date != null && !form_Date.isEmpty() && !to_date.isEmpty()) {
                            binding.tool.textDate.setText(form_Date + " To " + to_date);
                        } else {
                            binding.tool.textDate.setText("");
                        }
                    }
//                            binding.tool.textDate.setVisibility(View.VISIBLE);
//                            binding.tool.textDate.setText(pojo.getmDefaultStartDate()+" To "+pojo.getmDefaultEndDate());


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
                        binding.tool.textDate.setVisibility(View.VISIBLE);
                        if (pojo.getmDefaultStartDate() != null && pojo.getmDefaultEndDate() != null && !pojo.getmDefaultStartDate().isEmpty() && !pojo.getmDefaultEndDate().isEmpty()) {
                            binding.tool.textDate.setText(pojo.getmDefaultStartDate() + " To " + pojo.getmDefaultEndDate());
                        } else {
                            binding.tool.textDate.setText("");
                        }
                    } else {
                        binding.tool.textDate.setVisibility(View.VISIBLE);
                        if (form_Date != null && to_date != null && !form_Date.isEmpty() && !to_date.isEmpty()) {
                            binding.tool.textDate.setText(form_Date + " To " + to_date);
                        } else {
                            binding.tool.textDate.setText("");
                        }
                    }
                    StartDate_filter = pojo.getmDefaultStartDate();
                    Enddate_filter = pojo.getmDefaultEndDate();
                    // Enddate_filter = pojo.getEnddate();
                    SharedPref.write(SharedPref.END_DATE, pojo.getmEnddate());
                    SharedPref.write(SharedPref.START_DATE, pojo.getmStartDate());
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    binding.includeProgress.noData.setVisibility(View.VISIBLE);
                    courierAdapter.notifyDataSetChanged();
                }
            } catch (JsonIOException e) {
                e.printStackTrace();
                AlertUtil.responseExecption(mContext, "GetCourierReport ", e.toString());
            }

        }, error -> {
            try {
                Constants.convertByteToString(mContext, "GetCourierReport ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            binding.includeProgress.progress.setVisibility(View.GONE);
            binding.includeProgress.noData.setVisibility(View.VISIBLE);
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                Log.i("TaG", "GetCourierReport header : URl " + GET_COURIER_REPORT + " " + "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"PartyCode\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FromDate\":\"" + form_Date + "\",\"ToDate\":\"" + to_date + "\",\"CourierName\":\"" + courierName + "\"" +
                        ",\"CourierNumber\":\"" + courierNo + "\",\"SaleBillNumber\":\"" + courierBill_no + "\",\"DBNAME\":\"" + db_name + "\"}";
                Log.i("TaG", "GetCourierReport req : URl " + GET_COURIER_REPORT + " " + str);

                return str.getBytes();
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
        dialog.setContentView(R.layout.courier_fitter_dailog);

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
        dialog.setCancelable(false);
        courierFilter_Date = dialog.findViewById(R.id.courierFilter_Date);
        courierFilter_Name = dialog.findViewById(R.id.courierFilter_Name);
        courierFilter_No = dialog.findViewById(R.id.courierFilter_No);
        courierFilter_BillNo = dialog.findViewById(R.id.courierFilter_BillNo);
        recycler_courier = dialog.findViewById(R.id.recycler_courier);
        llRange = dialog.findViewById(R.id.ll_price_range);
        courierFilter_Date.setBackgroundColor(getResources().getColor(R.color.light_pink));
        formDate = dialog.findViewById(R.id.courier_formDate);
        todate = dialog.findViewById(R.id.courier_toDate);
        progressBar = dialog.findViewById(R.id.progress);
        nodata = dialog.findViewById(R.id.no_data);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        simpleDateFormat2 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
//
//        formDate.setText(form_date);
//        todate.setText(to_Date);

        ImageView cancel = dialog.findViewById(R.id.cancle_i);
        TextView clearAll = dialog.findViewById(R.id.clear_all);
//        formDate.setText("01/04/2021");
//        todate.setText("31/03/2022");
        count_Cname = dialog.findViewById(R.id.count_Cname);
        count_CNo = dialog.findViewById(R.id.count_CNo);
        count_CBill_No = dialog.findViewById(R.id.count_CBill_No);


        new Handler().post(() -> {
            filterStack.clear();
            // getFilters(FilterType.CLEAR,true);
            getFilters(FilterTypeCourierReport.COURIER_REPORT);
        });

//
//        if (Count.equals("") || Count2.equals("") || Count3.equals("")) {
//            count_Cname.setText("0");
//            count_CNo.setText("0");
//            count_CBill_No.setText("0");
//        } else {
//            count_Cname.setText(Count);
//            count_CNo.setText(Count2);
//            count_CBill_No.setText(Count3);
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
        formDate.setOnClickListener(v -> {
            flag = "from";
            // StartDate_filter = ledgerDate.getText().toString();
            if (SharedPref.read(SharedPref.FY_StartDate, "").equals("")) {
                StartDate_filter = formDate.getText().toString();
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
            String yy = items1[2];
            String mm = items1[1];
            String dd = items1[0];
            String yy1 = items2[2];
            String mm2 = items2[1];
            String dd3 = items2[0];
            showDate(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), Integer.parseInt(yy1), Integer.parseInt(mm2) - 1, Integer.parseInt(dd3), R.style.NumberPickerStyle);
            filterStack.clear();
            filterChangedCourier(FilterTypeCourierReport.DATE);
            getFilters(FilterTypeCourierReport.CLEAR);
        });
        todate.setOnClickListener(v -> {
            Log.e("date", SharedPref.read(SharedPref.FY_StartDate, "") + "---" + SharedPref.read(SharedPref.selected_default_yr, ""));
            flag = "to";
            // StartDate_filter = "01/04/2020";
            StartDate_filter = formDate.getText().toString();
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
            filterChangedCourier(FilterTypeCourierReport.DATE);
            getFilters(FilterTypeCourierReport.CLEAR);
        });
        clearAll.setOnClickListener(v -> {
            isFilterShowing = false;
            filterStack.clear();
            // ledgerDate.setText(SharedPref.read(SharedPref.START_DATE, ""));
            // ledger_ToDate.setText(SharedPref.read(SharedPref.END_DATE, ""));
            count_Cname.setText("0");
            count_CNo.setText("0");
            count_CBill_No.setText("0");
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
            getFilters(FilterTypeCourierReport.CLEAR);
        });
        TextView apply = dialog.findViewById(R.id.apply_button);
        cancel.setOnClickListener(v -> {
            isFilterShowing = false;
            courier_List.forEach(item -> item.setSelected(false));
            courierNo_List.forEach(item -> item.setSelected(false));
            salebill_List.forEach(item -> item.setSelected(false));
            Count = count_Cname.getText().toString();
            Count2 = count_CNo.getText().toString();
            Count3 = count_CBill_No.getText().toString();
            dialog.dismiss();
        });
        apply.setOnClickListener(v -> {
            isFilterShowing = false;
            form_date = formDate.getText().toString();
            to_Date = todate.getText().toString();
            Count = count_Cname.getText().toString();
            Count2 = count_CNo.getText().toString();
            Count3 = count_CBill_No.getText().toString();
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
            List<Courier> isSelected1 = courier_List.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
            String courierNameArray;

            if (isSelected1.size() > 0) {
                courierNameArray = new Gson().toJson(isSelected1);
                try {
                    StringBuilder sb = new StringBuilder();
                    JSONArray jsonArray = new JSONArray(courierNameArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objects = jsonArray.getJSONObject(i);
                        String name = objects.getString("CourierName");
                        if (name.isEmpty()) {
                            sb.append(name).append("without courier name");
                        } else {
                            sb.append(name).append(",");
                        }
                        System.out.println("My_Courier_Name - " + name);
                        Log.e("courierName", name);
                    }

                    String sbb = sb.toString();
                    courierName = sbb;
                    Log.e("courier_name", sbb);
                    System.out.println("My_Courier_Name 2 - " + sbb);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                courierName = "null";
            }
            List<CourierNo> isSelected2 = courierNo_List.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
            String courierNoarray;
            if (isSelected2.size() > 0) {
                courierNoarray = new Gson().toJson(isSelected2);
                try {
                    StringBuilder sb = new StringBuilder();
                    JSONArray jsonArray = new JSONArray(courierNoarray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objects = jsonArray.getJSONObject(i);
                        String name = objects.getString("CourierNumber");
                        sb.append(name + ",");
                        Log.e("courierNo_name", name);
                        System.out.println("My_Courier_Name 3 - " + name);
                    }
                    String sbb = sb.toString();
                    courierNo = sbb;
                    Log.e("courierNo_list", sbb);
                    System.out.println("My_Courier_Name 4 - " + sbb);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                courierNo = "null";
            }

            List<Salebill> isSelected3 = salebill_List.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
            String courierBillNo_array;
            if (isSelected3.size() > 0) {
                courierBillNo_array = new Gson().toJson(isSelected3);
                try {
                    StringBuilder sb = new StringBuilder();
                    JSONArray jsonArray = new JSONArray(courierBillNo_array);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objects = jsonArray.getJSONObject(i);
                        String name = objects.getString("SalebillNo");
                        sb.append(name + ",");
                        Log.e("courierBNo_name", name);
                        System.out.println("My_Courier_Name 5 - " + name);
                    }
                    String sbb = sb.toString();
                    courierBill_no = sbb;
                    Log.e("courierBNo_list", sbb);
                    System.out.println("My_Courier_Name 6 - " + sbb);
                } catch (Exception e) {

                }


            } else {
                courierBill_no = "null";
            }
            Log.e("formDate", formDate.getText().toString() + "/" + todate.getText().toString());
            //  GetLedgerReport(ledgerDate.getText().toString(), ledger_ToDate.getText().toString(), entry,adjustment, dnNAME, account);
            SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date d1 = sdformat.parse(formDate.getText().toString());
                Date d2 = sdformat.parse(todate.getText().toString());
                if (d1.compareTo(d2) < 0 || d1.compareTo(d2) == 0) {
                    System.out.println("My_Courier_Name 7 - " + courierName);
                    startActivity(new Intent(mContext, CourierReportActivity.class)
                            .putExtra("formDate", formDate.getText().toString())
                            .putExtra("todate", todate.getText().toString())
                            .putExtra("courierName", courierName)
                            .putExtra("courierNo", courierNo)
                            .putExtra("courierBill_no", courierBill_no));
                    finish();
                    dialog.dismiss();
                } else {
                    Toast.makeText(mContext, "From Date छोटी होनी चाहिए To Date से", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            GetCourierReport(courierName, courierNo, courierBill_no, form_date, to_Date, dbNAME);
//            dialog.dismiss();
        });


        isDatePressed = true;
        courierFilter_Date.setBackground(getResources().getDrawable(R.drawable.selected_button));
        courierFilter_Date.setTextColor(getResources().getColor(R.color.white));


        courierFilter_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDatePressed = true;
                courierFilter_Date.setBackground(getResources().getDrawable(R.drawable.selected_button));
                courierFilter_Date.setTextColor(getResources().getColor(R.color.white));
                if (isNamePlace || isNoPlace || isbillNoPlace) {
                    llRange.setVisibility(View.VISIBLE);
                    recycler_courier.setVisibility(View.GONE);
                    count_Cname.setTextColor(getResources().getColor(R.color.black));
                    count_CNo.setTextColor(getResources().getColor(R.color.black));
                    count_CBill_No.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_Name.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_Name.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_No.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_No.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_BillNo.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_BillNo.setTextColor(getResources().getColor(R.color.black));
                    isNamePlace = false;
                    isNoPlace = false;
                    isbillNoPlace = false;
                }
            }
        });

        courierFilter_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNamePlace = true;
                courierFilter_Name.setBackground(getResources().getDrawable(R.drawable.selected_button));
                courierFilter_Name.setTextColor(getResources().getColor(R.color.white));
                keyTypeList = "COURIERNAME";
                Log.e("filter", keyTypeList);
                if (isDatePressed || isNoPlace || isbillNoPlace) {
                    llRange.setVisibility(View.GONE);
                    recycler_courier.setVisibility(View.VISIBLE);
                    count_Cname.setTextColor(getResources().getColor(R.color.white));
                    count_CNo.setTextColor(getResources().getColor(R.color.black));
                    count_CBill_No.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_Date.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_No.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_No.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_BillNo.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_BillNo.setTextColor(getResources().getColor(R.color.black));
                    recycler_courier.setAdapter(filterCourierReportAdap);
                    filterCourierReportAdap.notifyDataSetChanged();
                    isDatePressed = false;
                    isNoPlace = false;
                    isbillNoPlace = false;
                    //getFilters(FilterTypeCourierReport.COURIER_NAME);
                }
            }
        });
        courierFilter_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNoPlace = true;
                courierFilter_No.setBackground(getResources().getDrawable(R.drawable.selected_button));
                courierFilter_No.setTextColor(getResources().getColor(R.color.white));
                keyTypeList = "COURIERNO";
                Log.e("filter", keyTypeList);
                if (isDatePressed || isNamePlace || isbillNoPlace) {
                    recycler_courier.setVisibility(View.VISIBLE);
                    llRange.setVisibility(View.GONE);
                    count_Cname.setTextColor(getResources().getColor(R.color.black));
                    count_CNo.setTextColor(getResources().getColor(R.color.white));
                    count_CBill_No.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_Date.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_Name.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_Name.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_BillNo.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_BillNo.setTextColor(getResources().getColor(R.color.black));
                    recycler_courier.setAdapter(filterCourierNoAdap);
                    filterCourierNoAdap.notifyDataSetChanged();
                    isDatePressed = false;
                    isNamePlace = false;
                    isbillNoPlace = false;
                    //getFilters(FilterTypeCourierReport.COURIER_NO);
                }
            }
        });
        courierFilter_BillNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isbillNoPlace = true;
                courierFilter_BillNo.setBackground(getResources().getDrawable(R.drawable.selected_button));
                courierFilter_BillNo.setTextColor(getResources().getColor(R.color.white));
                keyTypeList = "SALEBILLNO";
                Log.e("filter", keyTypeList);
                if (isDatePressed || isNamePlace || isNoPlace) {
                    recycler_courier.setVisibility(View.VISIBLE);
                    llRange.setVisibility(View.GONE);
                    count_Cname.setTextColor(getResources().getColor(R.color.black));
                    count_CNo.setTextColor(getResources().getColor(R.color.black));
                    count_CBill_No.setTextColor(getResources().getColor(R.color.white));
                    courierFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_Date.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_Name.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_Name.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_No.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_No.setTextColor(getResources().getColor(R.color.black));
                    recycler_courier.setAdapter(filterSalebillAdap);
                    filterSalebillAdap.notifyDataSetChanged();
                    isDatePressed = false;
                    isNamePlace = false;
                    isNoPlace = false;
                    //getFilters(FilterTypeCourierReport.SALE_BILL_NO);
                }
            }
        });

        dialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                isFilterShowing = false;
                dialog.dismiss();
            }
            return true;
        });
        //   getSIze(getIntent().getStringExtra("d_code"),false);
        Window window = dialog.getWindow();
        if (window != null) {
            WindowCompat.setDecorFitsSystemWindows(window, false);
        }
        dialog.show();

        getFilters(FilterTypeCourierReport.COURIER_NAME);
        getFilters(FilterTypeCourierReport.COURIER_NO);
        getFilters(FilterTypeCourierReport.SALE_BILL_NO);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getFilters(FilterTypeCourierReport mFilterType) {
        progressBar.setVisibility(View.VISIBLE);
        CourierReportFilterRequest request;
        request = new CourierReportFilterRequest(
                formDate.getText().toString(), todate.getText().toString(), "COURIERREPORT",
                SharedPref.read(SharedPref.PARTY_CODE, ""),
                courier_List.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterTypeCourierReport.COURIER_NAME))
                        .collect(Collectors.toList()),
                courierNo_List.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterTypeCourierReport.COURIER_NO))
                        .collect(Collectors.toList()),
                salebill_List.stream()
                        .filter(e -> e.isSelected() && filterStack.contains(FilterTypeCourierReport.SALE_BILL_NO))
                        .collect(Collectors.toList()),
                SharedPref.read(SharedPref.DB_NAME, "")
        );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_LIST_NEW, response -> {
            Log.i("TaG", "GetCourierReport res : URl1 " + GET_FILTER_LIST_NEW + " " + response);
            CourierReportPojo pojo = new Gson().fromJson(response, courierNameType1);

            if (pojo.getResponseStatus()) {
                progressBar.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
                switch (mFilterType) {
                    case COURIER_NAME:
                        List<Courier> prevCourierList = new ArrayList<>(courier_List);
                        List<Courier> size = prevCourierList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterTypeCourierReport.COURIER_NAME))
                                .collect(Collectors.toList());
                        Log.e("size", size.size() + "");
                        //  count_Cname.setText(size.size() + "");
                        courier_List.clear();
                        if (!filterStack.contains(mFilterType) || courierReportPojo == null || courierReportPojo.getCourier() == null || courierReportPojo.getCourier().isEmpty()) {
                            courier_List.addAll(pojo.getCourier());
                            count_Cname.setText("0");
                            //   Toast.makeText(mContext, "if", Toast.LENGTH_SHORT).show();
                        } else {
                            //  Toast.makeText(mContext, "else", Toast.LENGTH_SHORT).show();
                            count_Cname.setText(size.size() + "");
                            courierReportPojo.getCourier().forEach(courier -> {
                                prevCourierList.forEach(courier2 -> {
                                    if (courier.getCourierName().equals(courier2.getCourierName())) {
                                        courier.setSelected(courier2.isSelected());
                                    }
                                });
                            });
                            courier_List.addAll(prevCourierList);
                            //  adjustmentTypeList.addAll(ledgerPogo.getAdjustmentType());
                        }
                        filterCourierReportAdap.notifyDataSetChanged();
                        break;
                    case COURIER_NO:
                        List<CourierNo> prevCourierNoList = new ArrayList<>(courierNo_List);
                        List<CourierNo> size1 = prevCourierNoList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterTypeCourierReport.COURIER_NO))
                                .collect(Collectors.toList());
                        Log.e("size", size1.size() + "");

                        courierNo_List.clear();
                        if (!filterStack.contains(mFilterType) || courierReportPojo == null || courierReportPojo.getCourierNo() == null || courierReportPojo.getCourierNo().isEmpty()) {
                            courierNo_List.addAll(pojo.getCourierNo());
                            count_CNo.setText("0");
                        } else {
                            Log.e("LedgerActivity", "ZHere");
                            count_CNo.setText(size1.size() + "");
                            courierReportPojo.getCourierNo().forEach(courierNo -> {
                                prevCourierNoList.forEach(courierNo1 -> {
                                    if (courierNo.getCourierNumber().equals(courierNo1.getCourierNumber())) {
                                        courierNo.setSelected(courierNo1.isSelected());
                                    }
                                });
                            });
                            courierNo_List.addAll(prevCourierNoList);
                            // accountTypeList.addAll(ledgerPogo.getAccountType());
                        }
                        filterCourierNoAdap.notifyDataSetChanged();
                        break;
                    case SALE_BILL_NO:
                        List<Salebill> prevSalebillList = new ArrayList<>(salebill_List);
                        Log.e("prevEntryList", new Gson().toJson(prevSalebillList));
                        List<Salebill> size11 = prevSalebillList.stream()
                                .filter(e -> e.isSelected() && filterStack.contains(FilterTypeCourierReport.SALE_BILL_NO))
                                .collect(Collectors.toList());

                        Log.e("entryTypeListbfrclear", new Gson().toJson(salebill_List));
                        salebill_List.clear();
                        Log.e("justaftrtclear", new Gson().toJson(salebill_List));
                        if (!filterStack.contains(mFilterType) || courierReportPojo == null || courierReportPojo.getSalebill() == null || courierReportPojo.getSalebill().isEmpty()) {
                            salebill_List.addAll(pojo.getSalebill());
                            count_CBill_No.setText("0");
                            Log.e("entryTypestaftrclearif", new Gson().toJson(salebill_List));
                        } else {
                            count_CBill_No.setText(size11.size() + "");
                            Log.e("ledgerPogo", new Gson().toJson(courierReportPojo.getSalebill()));
                            courierReportPojo.getSalebill().forEach(Salebill -> {
                                prevSalebillList.forEach(Salebill1 ->
                                {
                                    if (Salebill.getSalebillNo().equals(Salebill1.getSalebillNo())) {
                                        Salebill.setSelected(Salebill1.isSelected());
                                    }
                                });
                            });
                            salebill_List.addAll(prevSalebillList);
                            // entryTypeList.addAll(ledgerPogo.getEntryType());
                            Log.e("entryTypestaftrclearels", new Gson().toJson(salebill_List));
                        }
                        filterSalebillAdap.notifyDataSetChanged();

                        break;
                    case CLEAR:
                        courier_List.clear();
                        courier_List.addAll(pojo.getCourier());
                        courierNo_List.clear();
                        courierNo_List.addAll(pojo.getCourierNo());
                        salebill_List.clear();
                        salebill_List.addAll(pojo.getSalebill());
                        filterCourierReportAdap.notifyDataSetChanged();
                        filterCourierNoAdap.notifyDataSetChanged();
                        filterSalebillAdap.notifyDataSetChanged();

                    case COURIER_REPORT:
                        courierReportPojo = pojo;
                        break;
                }
            } else {
                progressBar.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                courier_List.clear();
                courierNo_List.clear();
                salebill_List.clear();
                filterCourierReportAdap.notifyDataSetChanged();
                filterCourierNoAdap.notifyDataSetChanged();
                filterSalebillAdap.notifyDataSetChanged();

            }
            // }
        }, error -> {
            progressBar.setVisibility(View.GONE);
            // nodata.setVisibility(View.VISIBLE);
            courier_List.clear();
            courierNo_List.clear();
            salebill_List.clear();
            filterCourierReportAdap.notifyDataSetChanged();
            filterCourierNoAdap.notifyDataSetChanged();
            filterSalebillAdap.notifyDataSetChanged();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = new Gson().toJson(request);
                Log.e("str", str);
                Log.i("TaG", "GetCourierReport req : URl1 " + GET_FILTER_LIST_NEW + " " + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
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
        dialog.setContentView(R.layout.courier_fitter_dailog);
        dialog.setCancelable(false);
        courierFilter_Date = dialog.findViewById(R.id.courierFilter_Date);
        courierFilter_Name = dialog.findViewById(R.id.courierFilter_Name);
        courierFilter_No = dialog.findViewById(R.id.courierFilter_No);
        courierFilter_BillNo = dialog.findViewById(R.id.courierFilter_BillNo);
        recycler_courier = dialog.findViewById(R.id.recycler_courier);
        llRange = dialog.findViewById(R.id.ll_price_range);
        courierFilter_Date.setBackgroundColor(getResources().getColor(R.color.light_pink));
        formDate = dialog.findViewById(R.id.courier_formDate);
        todate = dialog.findViewById(R.id.courier_toDate);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        simpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        formDate.setText(form_date);
        todate.setText(to_Date);

        ImageView cancel = dialog.findViewById(R.id.cancle_i);
        TextView clearAll = dialog.findViewById(R.id.clear_all);
        formDate.setText("01/04/2021");
        todate.setText("31/03/2022");

        count_Cname = dialog.findViewById(R.id.count_Cname);
        count_CNo = dialog.findViewById(R.id.count_CNo);
        count_CBill_No = dialog.findViewById(R.id.count_CBill_No);


        if (Count.equals("") || Count2.equals("") || Count3.equals("")) {
            count_Cname.setText("0");
            count_CNo.setText("0");
            count_CBill_No.setText("0");
        } else {
            count_Cname.setText(Count);
            count_CNo.setText(Count2);
            count_CBill_No.setText(Count3);
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
        formDate.setOnClickListener(v -> {
            flag = "from";
            form_date = formDate.getText().toString();
            to_Date = todate.getText().toString();
            ;
            String[] items1 = form_date.split("/");
            String[] items2 = to_Date.split("/");
            String yy = items1[2];
            String mm = items1[1];
            String dd = items1[0];
            String yy1 = items2[2];
            String mm2 = items2[1];
            String dd3 = items2[0];
            showDate(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), Integer.parseInt(yy1), Integer.parseInt(mm2) - 1, Integer.parseInt(dd3), R.style.NumberPickerStyle);
        });
        todate.setOnClickListener(v -> {
            flag = "to";
            form_date = formDate.getText().toString();
            to_Date = todate.getText().toString();
            ;
            String[] items1 = form_date.split("/");
            String[] items2 = to_Date.split("/");
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
            formDate.setText(SharedPref.read(SharedPref.FY_StartDate, ""));
            todate.setText(SharedPref.read(SharedPref.FY_EndDate, ""));
            count_Cname.setText("0");
            count_CNo.setText("0");
            count_CBill_No.setText("0");
            for (int i = 0; i < courierNameList.size(); i++) {
                courierNameList.get(i).setSelected(false);
            }
            for (int i = 0; i < courierNoList.size(); i++) {
                courierNoList.get(i).setSelected(false);
            }
            for (int i = 0; i < courierBillNoList.size(); i++) {
                courierBillNoList.get(i).setSelected(false);
            }
            new Handler().postDelayed(() -> {
                if (isNamePlace) {
                    courierNameAdp = new CourierNameAdp(mContext, courierNameList);
                    recycler_courier.setAdapter(courierNameAdp);
                    courierNameAdp.notifyDataSetChanged();
                } else if (isNoPlace) {
                    courierNoAdp = new CourierNoAdp(mContext, courierNoList);
                    recycler_courier.setAdapter(courierNoAdp);
                    courierNoAdp.notifyDataSetChanged();
                } else if (isbillNoPlace) {
                    courierBillNoAdp = new CourierBillNoAdp(mContext, courierBillNoList);
                    recycler_courier.setAdapter(courierBillNoAdp);
                    courierBillNoAdp.notifyDataSetChanged();
                }
            }, 500);
        });
        TextView apply = dialog.findViewById(R.id.apply_button);
        cancel.setOnClickListener(v -> {
            isFilterShowing = false;
            for (int i = 0; i < courierNameList.size(); i++) {
                courierNameList.get(i).setSelected(false);
            }
            for (int i = 0; i < courierNoList.size(); i++) {
                courierNoList.get(i).setSelected(false);
            }
            for (int i = 0; i < courierBillNoList.size(); i++) {
                courierBillNoList.get(i).setSelected(false);
            }
            dialog.dismiss();
        });
        apply.setOnClickListener(v -> {
            isFilterShowing = false;
            form_date = formDate.getText().toString();
            to_Date = todate.getText().toString();
            Count = count_Cname.getText().toString();
            Count2 = count_CNo.getText().toString();
            Count3 = count_CBill_No.getText().toString();
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
            List<com.syber.ssspltd.response.CourierNameRespons.FilterListResult> isSelected1 = courierNameList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
            String courierNameArray;

            if (isSelected1.size() > 0) {
                courierNameArray = new Gson().toJson(isSelected1);
                try {
                    StringBuilder sb = new StringBuilder();
                    JSONArray jsonArray = new JSONArray(courierNameArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objects = jsonArray.getJSONObject(i);
                        String name = objects.getString("FilterName");
                        sb.append(name + ",");
                        Log.e("courierName", name);
                    }
                    String sbb = sb.toString();
                    courierName = sbb;
                    Log.e("courier_name", sbb);
                } catch (Exception e) {

                }
            } else {
                courierName = "null";
            }
            List<com.syber.ssspltd.response.CourierNameRespons.CourierNoRespo.FilterListResult> isSelected2 = courierNoList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
            String courierNoarray;
            if (isSelected2.size() > 0) {
                courierNoarray = new Gson().toJson(isSelected2);
                try {
                    StringBuilder sb = new StringBuilder();
                    JSONArray jsonArray = new JSONArray(courierNoarray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objects = jsonArray.getJSONObject(i);
                        String name = objects.getString("FilterName");
                        sb.append(name + ",");
                        Log.e("courierNo_name", name);
                    }
                    String sbb = sb.toString();
                    courierNo = sbb;
                    Log.e("courierNo_list", sbb);
                } catch (Exception e) {

                }
            } else {
                courierNo = "null";
            }

            List<com.syber.ssspltd.response.CourierNameRespons.CourierBillNoRespo.FilterListResult> isSelected3 = courierBillNoList.stream().filter(p -> p.isSelected()).collect(Collectors.toList());
            String courierBillNo_array;
            if (isSelected3.size() > 0) {
                courierBillNo_array = new Gson().toJson(isSelected3);
                try {
                    StringBuilder sb = new StringBuilder();
                    JSONArray jsonArray = new JSONArray(courierBillNo_array);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objects = jsonArray.getJSONObject(i);
                        String name = objects.getString("FilterName");
                        sb.append(name + ",");
                        Log.e("courierBNo_name", name);
                    }
                    String sbb = sb.toString();
                    courierBill_no = sbb;
                    Log.e("courierBNo_list", sbb);
                } catch (Exception e) {

                }


            } else {
                courierBill_no = "null";
            }
            GetCourierReport(courierName, courierNo, courierBill_no, form_date, to_Date, dbNAME, true);
            dialog.dismiss();
        });


        isDatePressed = true;
        courierFilter_Date.setBackground(getResources().getDrawable(R.drawable.selected_button));
        courierFilter_Date.setTextColor(getResources().getColor(R.color.white));


        courierFilter_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDatePressed = true;
                courierFilter_Date.setBackground(getResources().getDrawable(R.drawable.selected_button));
                courierFilter_Date.setTextColor(getResources().getColor(R.color.white));
                if (isNamePlace || isNoPlace || isbillNoPlace) {
                    llRange.setVisibility(View.VISIBLE);
                    recycler_courier.setVisibility(View.GONE);
                    count_Cname.setTextColor(getResources().getColor(R.color.black));
                    count_CNo.setTextColor(getResources().getColor(R.color.black));
                    count_CBill_No.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_Name.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_Name.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_No.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_No.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_BillNo.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_BillNo.setTextColor(getResources().getColor(R.color.black));
                    isNamePlace = false;
                    isNoPlace = false;
                    isbillNoPlace = false;
                }
            }
        });

        courierFilter_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNamePlace = true;
                courierFilter_Name.setBackground(getResources().getDrawable(R.drawable.selected_button));
                courierFilter_Name.setTextColor(getResources().getColor(R.color.white));
                keyTypeList = "COURIERNAME";
                Log.e("filter", keyTypeList);
                if (isDatePressed || isNoPlace || isbillNoPlace) {
                    llRange.setVisibility(View.GONE);
                    recycler_courier.setVisibility(View.VISIBLE);
                    count_Cname.setTextColor(getResources().getColor(R.color.white));
                    count_CNo.setTextColor(getResources().getColor(R.color.black));
                    count_CBill_No.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_Date.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_No.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_No.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_BillNo.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_BillNo.setTextColor(getResources().getColor(R.color.black));
                    linearLayoutManager = new LinearLayoutManager(mContext);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recycler_courier.setLayoutManager(linearLayoutManager);
                    courierNameAdp = new CourierNameAdp(mContext, courierNameList);
                    recycler_courier.setAdapter(courierNameAdp);
                    courierNameAdp.notifyDataSetChanged();
                    isDatePressed = false;
                    isNoPlace = false;
                    isbillNoPlace = false;
                }
            }
        });
        courierFilter_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNoPlace = true;
                courierFilter_No.setBackground(getResources().getDrawable(R.drawable.selected_button));
                courierFilter_No.setTextColor(getResources().getColor(R.color.white));
                keyTypeList = "COURIERNO";
                Log.e("filter", keyTypeList);
                if (isDatePressed || isNamePlace || isbillNoPlace) {
                    recycler_courier.setVisibility(View.VISIBLE);
                    llRange.setVisibility(View.GONE);
                    count_Cname.setTextColor(getResources().getColor(R.color.black));
                    count_CNo.setTextColor(getResources().getColor(R.color.white));
                    count_CBill_No.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_Date.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_Name.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_Name.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_BillNo.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_BillNo.setTextColor(getResources().getColor(R.color.black));
                    linearLayoutManager = new LinearLayoutManager(mContext);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recycler_courier.setLayoutManager(linearLayoutManager);
                    courierNoAdp = new CourierNoAdp(mContext, courierNoList);
                    recycler_courier.setAdapter(courierNoAdp);
                    courierNoAdp.notifyDataSetChanged();
                    isDatePressed = false;
                    isNamePlace = false;
                    isbillNoPlace = false;
                }
            }
        });
        courierFilter_BillNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isbillNoPlace = true;
                courierFilter_BillNo.setBackground(getResources().getDrawable(R.drawable.selected_button));
                courierFilter_BillNo.setTextColor(getResources().getColor(R.color.white));
                keyTypeList = "SALEBILLNO";
                Log.e("filter", keyTypeList);
                if (isDatePressed || isNamePlace || isNoPlace) {
                    recycler_courier.setVisibility(View.VISIBLE);
                    llRange.setVisibility(View.GONE);
                    count_Cname.setTextColor(getResources().getColor(R.color.black));
                    count_CNo.setTextColor(getResources().getColor(R.color.black));
                    count_CBill_No.setTextColor(getResources().getColor(R.color.white));
                    courierFilter_Date.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_Date.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_Name.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_Name.setTextColor(getResources().getColor(R.color.black));
                    courierFilter_No.setBackground(getResources().getDrawable(R.drawable.text_bg));
                    courierFilter_No.setTextColor(getResources().getColor(R.color.black));
                    linearLayoutManager = new LinearLayoutManager(mContext);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recycler_courier.setLayoutManager(linearLayoutManager);
                    courierBillNoAdp = new CourierBillNoAdp(mContext, courierBillNoList);
                    recycler_courier.setAdapter(courierBillNoAdp);
                    courierBillNoAdp.notifyDataSetChanged();
                    isDatePressed = false;
                    isNamePlace = false;
                    isNoPlace = false;
                }
            }
        });


        //   getSIze(getIntent().getStringExtra("d_code"),false);
        dialog.show();
    }


    private void courierNameDetail(final String keyType) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_DETAIL_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("TaG", "GetCourierReport rsp : URl2 " + GET_FILTER_DETAIL_LIST + " " + response);
                        FilterListPojo pojo = new Gson().fromJson(response, courierNameType);
                        if (pojo.getResponseStatus()) {
                            courierNameList.clear();
                            courierNameList.addAll(pojo.getFilterListResult());
                            //filterAdapter.notifyDataSetChanged();
                        } else {
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
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DATAKEY\":\"" + keyType + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FILTERTYPE\":\"" + "COURIERREPORT" + "\"}";
                Log.i("TaG", "GetCourierReport req : URl2 " + GET_FILTER_DETAIL_LIST + " " + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void CourierNoDetail(final String keyType) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_DETAIL_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TaG", "GetCourierReport rsp : URl3 " + GET_FILTER_DETAIL_LIST + " " + response);
                        com.syber.ssspltd.response.CourierNameRespons.CourierNoRespo.FilterListPojo pojo = new Gson().fromJson(response, courierNoType);
                        if (pojo.getResponseStatus()) {
                            courierNoList.clear();
                            courierNoList.addAll(pojo.getFilterListResult());
                            ///  subPartyListAdapter.notifyDataSetChanged();
                        } else {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DATAKEY\":\"" + keyType + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FILTERTYPE\":\"" + "COURIERREPORT" + "\"}";
                Log.i("TaG", "GetCourierReport req : URl3 " + GET_FILTER_DETAIL_LIST + " " + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void CourierBillNoDetail(final String keyType) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_FILTER_DETAIL_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TaG", "GetCourierReport res : URl4 " + GET_FILTER_DETAIL_LIST + " " + response);
                        com.syber.ssspltd.response.CourierNameRespons.CourierBillNoRespo.FilterListPojo pojo = new Gson().fromJson(response, courierBillNoType);
                        if (pojo.getResponseStatus()) {
                            courierBillNoList.clear();
                            courierBillNoList.addAll(pojo.getFilterListResult());

                        } else {
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
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DATAKEY\":\"" + keyType + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"FILTERTYPE\":\"" + "COURIERREPORT" + "\"}";

                Log.i("TaG", "GetCourierReport req : URl4 " + GET_FILTER_DETAIL_LIST + " " + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
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
            formDate.setText(simpleDateFormat.format(calendar.getTime()));

            // If the selected "from" date is today, set the "to" date as the same
            if (simpleDateFormat.format(calendar.getTime()).equals(CurrentDateTime.getCurrentDateDDMMYYY())) {
                todate.setText(formDate.getText().toString());
            }

            // Check if "from" date is after "to" date
            Calendar toDateCalendar = Calendar.getInstance();
            try {
                toDateCalendar.setTime(simpleDateFormat2.parse(todate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (calendar.after(toDateCalendar)) {
                todate.setText(formDate.getText().toString()); // Set "to" date same as "from" date
            }

        } else if (flag.equals("to")) {
            // Set the "to" date
            todate.setText(simpleDateFormat2.format(calendar.getTime()));

            // Check if "to" date is before "from" date
            Calendar fromDateCalendar = Calendar.getInstance();
            try {
                fromDateCalendar.setTime(simpleDateFormat.parse(formDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (calendar.before(fromDateCalendar)) {
                formDate.setText(todate.getText().toString()); // Set "from" date same as "to" date
            }
        }

        isFilterShowing = false;
        filterStack.clear();

        count_Cname.setText("0");
        count_CNo.setText("0");
        count_CBill_No.setText("0");

        /*getFilters(FilterTypeCourierReport.COURIER_NAME);
        getFilters(FilterTypeCourierReport.COURIER_NO);
        getFilters(FilterTypeCourierReport.SALE_BILL_NO);*/
        getFilters(FilterTypeCourierReport.COURIER_REPORT);
    }


    @VisibleForTesting
    void showDate(int year1, int monthOfYear1, int dayOfMonth1, int year2, int monthOfYear2, int dayOfMonth2, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(CourierReportActivity.this)
                .callback(CourierReportActivity.this)
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
                    .context(CourierReportActivity.this)
                    .callback(CourierReportActivity.this)
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
    public void filterChangedCourier(FilterTypeCourierReport mFilterType) {
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
                GetCourierReport(courierName, courierNo, courierBill_no, form_date, to_Date, dbNAME, true);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCheckChangeReferesh(FilterTypeCourierReport mFilterType) {

        switch (mFilterType) {
            case COURIER_NAME:
                salebill_List.forEach(e -> e.setSelected(false));
                courierNo_List.forEach(e -> e.setSelected(false));
                filterSalebillAdap.notifyDataSetChanged();
                filterCourierNoAdap.notifyDataSetChanged();
                break;

            case COURIER_NO:
                courier_List.forEach(e -> e.setSelected(false));
                salebill_List.forEach(e -> e.setSelected(false));
                filterCourierReportAdap.notifyDataSetChanged();
                filterSalebillAdap.notifyDataSetChanged();
                break;

            case SALE_BILL_NO:
                courier_List.forEach(e -> e.setSelected(false));
                courierNo_List.forEach(e -> e.setSelected(false));
                filterCourierReportAdap.notifyDataSetChanged();
                filterCourierNoAdap.notifyDataSetChanged();
                break;

        }
    }
}