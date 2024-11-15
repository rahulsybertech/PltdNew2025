package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_DASHBOARD_DETAILS_PENDING_ORDER;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_DASHBOARD_DETAIL_GRAPH;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_DASHBOARD__DEATILS_BALANCE_INTREST_DISCOUNT;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_DASHBOARD__DEATILS_BALANCE_TILL_DATE;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_DASHBOARD__DEATILS_STOCK_IN_OFFICE;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.anychart.graphics.vector.SolidFill;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.NewDashboadAdap.BalanceTillDate_Adap;
import com.syber.ssspltd.adapter.NewDashboadAdap.InsDis_Adapter;
import com.syber.ssspltd.adapter.NewDashboadAdap.PendingOrder_adap;
import com.syber.ssspltd.adapter.NewDashboadAdap.StockInOffice_adpder;
import com.syber.ssspltd.databinding.ActivityNewDashBoardBinding;
import com.syber.ssspltd.response.GraphData;
import com.syber.ssspltd.response.NewDashboadRespo.BalanceTillDatePojo;
import com.syber.ssspltd.response.NewDashboadRespo.InsDis.InDisPojo;
import com.syber.ssspltd.response.NewDashboadRespo.InsDis.InterestDiscountDetail;
import com.syber.ssspltd.response.NewDashboadRespo.PendingOrder.PendingOrderPojoDash;
import com.syber.ssspltd.response.NewDashboadRespo.PendingOrderDetail;
import com.syber.ssspltd.response.NewDashboadRespo.StockPending.StockInOfficePojo;
import com.syber.ssspltd.response.NewDashboadRespo.StockPending.StockinOfficeDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewDashBoardActivity extends AppCompatActivity {

    ActivityNewDashBoardBinding binding;
    Context mContext = this;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    ArrayList PieEntryLabels;
    BalanceTillDate_Adap balanceTillDate_adap;
    StockInOffice_adpder stockInOffice_adpder;
    PendingOrder_adap pendingOrder_adap;
    InsDis_Adapter insDis_adapter;
    Type listType, listType_stock, listType_pending, listType_intDis;
    List<PendingOrderDetail> balanceTillDate_List;
    List<StockinOfficeDetail> stockInOfficeDetails;
    List<InterestDiscountDetail> interestDiscountDetails;
    List<com.syber.ssspltd.response.NewDashboadRespo.PendingOrder.PendingOrderDetail> pendingOrderDetails;
    boolean setTrue = false;
    boolean isBalanceCalled = false;
    boolean isStockCalled = false;
    boolean isPendingCalled = false;
    boolean isDiscountCalled = false;
    boolean isInterestCalled = false;
    List<GraphData> graphDataList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tool.back2.setText("Payment DashBoard");
        binding.tool.back3.setImageDrawable(ContextCompat.getDrawable(NewDashBoardActivity.this, R.drawable.ic_baseline_keyboard_backspace));
        binding.tool.back3.setOnClickListener(v ->
                onBackPressed()
        );
       // graphDataList.add(new GraphData("Available Limit","50","#2bab1c"));
        graphDataList.add(new GraphData("Balance TIll Date","30","#fff200"));
        graphDataList.add(new GraphData("Pending Amount","20","#80bbfd"));
        binding.includeProgress.progress.setVisibility(View.GONE);

        if (Lazy.haveNetworkConnection(mContext)) {
            GetDashboardDetails();
//            GetStockInOffice();
//            GetPendingOrder();
//            GetBalanceTillDate();
//            GetInterest_Discount("discount");
//            GetInterest_Discount("Interest");
        } else {
            networkConnetion3(mContext);
        }
        listType = new TypeToken<BalanceTillDatePojo>() {
        }.getType();
        listType_stock = new TypeToken<StockInOfficePojo>() {
        }.getType();
        listType_pending = new TypeToken<PendingOrderPojoDash>() {
        }.getType();
        listType_intDis = new TypeToken<InDisPojo>() {
        }.getType();

        balanceTillDate_List = new ArrayList<>();
        stockInOfficeDetails = new ArrayList<>();
        pendingOrderDetails = new ArrayList<>();
        interestDiscountDetails = new ArrayList<>();

        balanceTillDate_adap = new BalanceTillDate_Adap(mContext, balanceTillDate_List);
        binding.balancetillRecy.setAdapter(balanceTillDate_adap);


        stockInOffice_adpder = new StockInOffice_adpder(mContext, stockInOfficeDetails);
        binding.StockRecy.setAdapter(stockInOffice_adpder);

        pendingOrder_adap = new PendingOrder_adap(mContext, pendingOrderDetails);
        binding.pendingRecy.setAdapter(pendingOrder_adap);


        binding.llbalntillImsg.setOnClickListener(view -> {
            if (setTrue == false) {
                // binding.balancetillRecy.forceLayout();
                // binding.scrollView.smoothScrollTo(R.id.balancetillRecy,R.id.balancetillRecy);
                binding.scrollView.post(() -> binding.scrollView.fullScroll(View.FOCUS_DOWN));
                if (!isBalanceCalled) {
                    GetBalanceTillDate();
                }
                binding.balntillImsg.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_up_24));
                binding.balnTill.setVisibility(View.VISIBLE);
                setTrue = true;
            } else if (setTrue == true) {
                binding.balntillImsg.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_down_24));
                binding.balnTill.setVisibility(View.GONE);
                setTrue = false;
            }
        });
        binding.llPendingText.setOnClickListener(view -> {
            if (setTrue == false) {
                binding.pendingVisibility.setVisibility(View.VISIBLE);

                binding.scrollView.post(() -> binding.scrollView.fullScroll(View.FOCUS_DOWN));
                //  Toast.makeText(mContext, isPendingCalled+"", Toast.LENGTH_SHORT).show();
                if (!isPendingCalled) {
                    GetPendingOrder();
                }

                binding.pendingText.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_up_24));

                setTrue = true;
            } else if (setTrue == true) {
                binding.pendingText.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_down_24));
                binding.pendingVisibility.setVisibility(View.GONE);
                setTrue = false;
            }

        });


        binding.llstockText.setOnClickListener(view -> {

            if (setTrue == false) {
                binding.scrollView.post(() -> binding.scrollView.fullScroll(View.FOCUS_DOWN));
                binding.stockText.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_up_24));
                binding.stockVisibility.setVisibility(View.VISIBLE);
                if (!isStockCalled) {
                    GetStockInOffice();
                }

                setTrue = true;
            } else if (setTrue == true) {
                binding.stockText.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_down_24));
                binding.stockVisibility.setVisibility(View.GONE);
                setTrue = false;
            }

        });

        binding.lldisUpDown.setOnClickListener(view -> {

            if (setTrue == false) {
                binding.scrollView.post(() -> binding.scrollView.fullScroll(View.FOCUS_DOWN));
                insDis_adapter = new InsDis_Adapter(mContext, interestDiscountDetails);
                binding.discountRecy.setAdapter(insDis_adapter);
                binding.disUpDown.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_up_24));
                // Toast.makeText(mContext, isDiscountCalled+"", Toast.LENGTH_SHORT).show();
                if (!isDiscountCalled) {
                    GetInterest_Discount("discount");
                }
                binding.disVisibility.setVisibility(View.VISIBLE);
                setTrue = true;
            } else if (setTrue == true) {
                binding.disUpDown.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_down_24));
                binding.disVisibility.setVisibility(View.GONE);
                setTrue = false;
            }

        });
        binding.llintUpDown.setOnClickListener(view -> {

            if (setTrue == false) {
                binding.scrollView.post(() -> binding.scrollView.fullScroll(View.FOCUS_DOWN));
                insDis_adapter = new InsDis_Adapter(mContext, interestDiscountDetails);
                binding.interstRecy.setAdapter(insDis_adapter);
                binding.intUpDown.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_up_24));
                if (!isInterestCalled) {
                    GetInterest_Discount("Interest");
                }
                binding.intVisibility.setVisibility(View.VISIBLE);
                setTrue = true;
            } else if (setTrue == true) {
                binding.intUpDown.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_down_24));
                binding.intVisibility.setVisibility(View.GONE);
                setTrue = false;
            }

        });
    }

    private void GetDashboardDetails() {
        ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Fetching Data ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);//initially progress is 0
        progressBar.setMax(100);//sets the maximum value 100
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();
        // binding.scrollView.setSmoothScrollingEnabled(true);
        String  newUrl = "http://app.ssspltd.com/apipltd/GetDashboardDetail_Graph";
        String oldUrl = "http://app.ssspltd.com/apipltd/GetDashboardDetails";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_DASHBOARD_DETAIL_GRAPH,
                response -> {

                    Log.i("TaG", "URL --" + GET_DASHBOARD_DETAIL_GRAPH);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("DashBoardRespo", response);
                        progressBar.dismiss();
                        if (jsonObject.getBoolean("ResponseStatus") == true) {

                            String stockinOffice = jsonObject.getString("StockinOffice");
                            String pending_order = jsonObject.getString("Pendingorder");
                            String avaialbleLimit = jsonObject.getString("AvaialbleLimit");
                            String balanceTillDate = jsonObject.getString("BalanceTillDate");
                            String Interest = jsonObject.getString("Interest");
                            String discount_d = jsonObject.optString("Discount");
                            // String stockinOffice = "0";
                            //  String pending_order = "0";
//                            String avaialbleLimit = "12154545";
//                            String balanceTillDate = "12124512";
//                            String Interest ="0" ;
//                            String discount_d = "0";
                            String dr_cr = jsonObject.getString("Dr");
//                            boolean isPendingVisible = jsonObject.getBoolean("PendingOrderVisible");
//                            boolean isStockInOfficeVisible = jsonObject.getBoolean("StockInOfficeVisible");
//                            boolean isBalanceTillDateVisible = jsonObject.getBoolean("BalanceTillDateVisible");
//                            boolean isDiscountVisible = jsonObject.getBoolean("DiscountVisible");
//                            boolean isInterestVisible = jsonObject.getBoolean("InterestVisible");
//                            boolean isTotalLimitVisible = jsonObject.getBoolean("TotalLimitVisible");
                            binding.layoutGone.setVisibility(View.VISIBLE);
                            Pie pie = AnyChart.pie();
                            NumberFormat f = NumberFormat.getInstance(); // Gets a NumberFormat with the default locale, you can specify a Locale as first parameter (like Locale.FRENCH)
                            if (Double.parseDouble("54")==0){
                                Log.e("true","true");
                            }else {
                                Log.e("false","false");
                            }
                            double pendingorder = f.parse(pending_order).doubleValue();
                            double stockin_Office = f.parse("0.00").doubleValue();
                            Log.e("stockin_Office",stockin_Office+"");
                            double avaialble_limit = f.parse(avaialbleLimit).doubleValue();
                            double balance_TillDate = f.parse(balanceTillDate).doubleValue();
                            double interest = f.parse(Interest).doubleValue();
                            double discount_graph = f.parse(discount_d).doubleValue();
                            double dr_value = f.parse(dr_cr).doubleValue();
                            //  Log.e("billDate", balance_TillDate + "");
                            //  Log.e("pending", pendingorder + "");


                            List<DataEntry> data = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("graphData");
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject ob = jsonArray.getJSONObject(i);
                                data.add(new ValueDataEntry(ob.getString("title"),Double.parseDouble(ob.getString("value"))));
                                pie.palette().itemAt(i,new SolidFill(ob.getString("color"),1));
                            }
//                            if (isTotalLimitVisible) {
//                                data.add(new ValueDataEntry("Available Limit", avaialble_limit));
//                            }
//                            if (isBalanceTillDateVisible && dr_value > 0) {
//                                data.add(new ValueDataEntry("Balance Till Date", balance_TillDate));
//                            }
//                            if (isPendingVisible) {
//                                data.add(new ValueDataEntry("Pending Order", pendingorder));
//                            }
//                            if (isStockInOfficeVisible) {
//                                data.add(new ValueDataEntry("Stock In Office", stockin_Office));
//                            }
//                            if (isDiscountVisible) {
//                                String beforeReplace_dis = Double.toString(discount_graph);
//                                String afterReplace_discount = beforeReplace_dis.replace("-", "");
//                                data.add(new ValueDataEntry("Discount", Double.parseDouble(afterReplace_discount)));
//                            }
//                            if (isInterestVisible && interest > 0) {
//                                data.add(new ValueDataEntry("Interest", interest));
//                            }
//                            if (stockin_Office <= 0 && pendingorder <= 0 && avaialble_limit <= 0 && balance_TillDate <= 0 && interest <= 0 && discount_graph <= 0) {
//                                binding.dashboardGraph.setVisibility(View.GONE);
//                                binding.whiteBg.setVisibility(View.GONE);
//                            }
//                            if (isTotalLimitVisible){
//                            }
//                            String[] rangeColors = new String[]{"#2bab1c", "#fff200",  "#80bbfd","#3A923E", "#DE79EF"};
//                            String [] rangeOne = new String[]{"#2bab1c","#fff200"};
//                            pie.palette(rangeColors);
//                            //  Log.e("data", new Gson().toJson(data));
//
//                            //   pie.palette().itemAt(0, new SolidFill("#2bab1c", 1));
////                            pie.palette().itemAt(1, new SolidFill("#fff200", 1));
////                            pie.palette().itemAt(3, new SolidFill("#9055F8", 1));
////                            pie.palette().itemAt(4, new SolidFill("#000000", 1));
                            pie.normal();
                            pie.data(data);
//                          pie.title("Fruits imported in 2015 (in kg)");
                            pie.labels().position("outside");
                            pie.legend().title().enabled(false);
                            pie.legend().title()
                                    .text("")
                                    .padding(0d, 0d, 10d, 0d);

                            pie.legend()
                                    .position("bottom")
                                    .itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE)
                                    .align(Align.CENTER);

                            binding.totalLimit.setText(jsonObject.optString("TotalLimit"));
                            binding.avaialbleLimit.setText(jsonObject.optString("AvaialbleLimit"));
                            binding.ablLimitShow.setText("Total Limit : " + jsonObject.optString("TotalLimit"));
                            String cr = jsonObject.optString("Cr");
                            String dr = jsonObject.optString("Dr");
                            if (!jsonObject.optString("BalanceTillDate").equals("0.00")) {
                                if (cr.equals("1")) {
                                    binding.drCrDashboard.setText("(CR)");
                                    binding.drCrDashboard.setTextColor(getResources().getColor(R.color.green));
                                    binding.balTill.setTextColor(getResources().getColor(R.color.green));
                                    binding.balTill.setText("(+) " + jsonObject.optString("BalanceTillDate"));
                                } else if (dr.equals("1")) {
                                    binding.drCrDashboard.setText("(DR)");
                                    binding.drCrDashboard.setTextColor(getResources().getColor(R.color.red));
                                    binding.balTill.setTextColor(getResources().getColor(R.color.red));
                                    binding.balTill.setText("(-) " + jsonObject.optString("BalanceTillDate"));
                                }
                            } else {
                                binding.balntillImsg.setVisibility(View.GONE);
                                binding.drCrDashboard.setText("");
                                binding.balTill.setText(jsonObject.optString("BalanceTillDate"));
                            }
                            if (!jsonObject.optString("StockinOffice").equals("0.00") || jsonObject.optString("StockinOffice").equals("0")) {
                                binding.llStock.setVisibility(View.VISIBLE);
                                binding.stockinOffice.setText("(-) " + jsonObject.optString("StockinOffice"));
                                binding.viewStock.setVisibility(View.VISIBLE);
                            } else {
                                binding.stockText.setVisibility(View.GONE);
                                binding.stockinOffice.setText(jsonObject.optString("StockinOffice"));
                            }
                            if (!jsonObject.optString("Pendingorder").equals("0.00") || jsonObject.optString("Pendingorder").equals("0")) {
                                binding.llPending.setVisibility(View.VISIBLE);
                                binding.pendingorder.setText("(-) " + jsonObject.optString("Pendingorder"));
                            } else {
                                binding.pendingText.setVisibility(View.GONE);
                                binding.pendingorder.setText(jsonObject.optString("Pendingorder"));
                            }
                            if (jsonObject.optString("VVPLimit").equals("0.00") &&
                                    jsonObject.optString("TemporaryLimit").equals("0.00")
                                    && jsonObject.optString("BG").equals("0.00")) {
                                binding.llYourLimt.setVisibility(View.GONE);
                                //  Log.e("VVP", Double.parseDouble(jsonObject.optString("VVPLimit")) + "");
                            } else {
                                if (jsonObject.optString("YourLimit").equals("0.00")) {
                                    binding.llYourLimt.setVisibility(View.GONE);
                                    binding.viewYourLimit.setVisibility(View.GONE);
                                } else if (jsonObject.optString("YourLimit").length() > 8) {
                                    binding.viewYourLimit.setVisibility(View.VISIBLE);
                                    binding.llYourLimt.setVisibility(View.VISIBLE);
                                    binding.yourLimit.setText("(+) " + jsonObject.optString("YourLimit"));
                                    binding.yourLimit.setTextSize(12);
                                } else {
                                    binding.viewYourLimit.setVisibility(View.VISIBLE);
                                    binding.llYourLimt.setVisibility(View.VISIBLE);
                                    binding.yourLimit.setText("(+) " + jsonObject.optString("YourLimit"));
                                }
                            }
                            if (jsonObject.optString("BG").equals("0.00")) {
                                binding.dashBoardBg.setVisibility(View.GONE);
                                binding.bgView.setVisibility(View.GONE);
                            } else {
                                binding.dashBoardBg.setVisibility(View.VISIBLE);
                                binding.bgView.setVisibility(View.VISIBLE);
                                binding.idBg.setText("(+) " + jsonObject.optString("BG"));
                            }
                            if (jsonObject.optString("VVPLimit").equals("0.00")) {
                                binding.llVvpLimit.setVisibility(View.GONE);
                                binding.viewVvp.setVisibility(View.GONE);
                            } else {
                                binding.viewVvp.setVisibility(View.VISIBLE);
                                binding.vvpLimit.setText("(+) " + jsonObject.optString("VVPLimit"));
                                binding.llVvpLimit.setVisibility(View.VISIBLE);

                            }

                            if (jsonObject.optString("TemporaryLimit").equals("0.00")) {
                                binding.llTemLimit.setVisibility(View.GONE);
                                binding.viewTemLimit.setVisibility(View.GONE);
                            } else {
                                binding.tempLimit.setText("(+) " + jsonObject.optString("TemporaryLimit"));
                                binding.viewTemLimit.setVisibility(View.VISIBLE);
//                                binding.llTemLimit.setVisibility(View.VISIBLE);
                            }
                            String int_dis_m = jsonObject.optString("Interest").equalsIgnoreCase("") ? "0" : jsonObject.optString("Interest");
                            String discount_m = jsonObject.optString("Discount").equalsIgnoreCase("") ? "0" : jsonObject.optString("Discount");

                            double int_dis = f.parse(int_dis_m).doubleValue();
                            double discount = f.parse(discount_m).doubleValue();

//                                String int_dis="10";
//                                String discount="0";
                            //                               String avgDays="456";

                            if (Double.parseDouble(String.valueOf(int_dis)) > 0) {
                                binding.viewInterest.setVisibility(View.VISIBLE);
                                binding.viewDiscount.setVisibility(View.GONE);
                                binding.intRest.setVisibility(View.VISIBLE);
                                binding.disCount.setVisibility(View.GONE);
                                binding.interest.setText("(-)" + jsonObject.optString("Interest"));
                                binding.interest.setTextColor(getResources().getColor(R.color.red));
                                binding.intDrCr.setText("(DR)");
                                binding.intDrCr.setTextColor(getResources().getColor(R.color.red));
                            } else if (Double.parseDouble(String.valueOf(int_dis)) < 0) {
                                binding.viewInterest.setVisibility(View.VISIBLE);
                                binding.viewDiscount.setVisibility(View.GONE);
                                binding.intRest.setVisibility(View.VISIBLE);
                                binding.disCount.setVisibility(View.GONE);
                                String beforeReplace = jsonObject.optString("Interest");
                                String afterReplace = beforeReplace.replace("-", "");
                                binding.interest.setText("(+) " + afterReplace);
                                //  Log.e("afterReplace", afterReplace);
                                binding.interest.setTextColor(getResources().getColor(R.color.green));
                                binding.intDrCr.setText("(CR)");
                                binding.intDrCr.setTextColor(getResources().getColor(R.color.green));
                            } else if (Double.parseDouble(String.valueOf(discount)) > 0) {
                                binding.viewDiscount.setVisibility(View.VISIBLE);
                                binding.disCount.setVisibility(View.VISIBLE);
                                binding.intRest.setVisibility(View.GONE);
                                binding.viewInterest.setVisibility(View.GONE);
                                binding.discount.setText("(+) " + jsonObject.optString("Discount"));
                                binding.discount.setTextColor(getResources().getColor(R.color.green));
                                binding.disDrCr.setText("(CR)");
                                binding.disDrCr.setTextColor(getResources().getColor(R.color.green));

                            } else if (Double.parseDouble(String.valueOf(discount)) < 0) {
                                binding.viewDiscount.setVisibility(View.VISIBLE);
                                binding.disCount.setVisibility(View.VISIBLE);
                                binding.intRest.setVisibility(View.GONE);
                                binding.viewInterest.setVisibility(View.GONE);
                                String beforeReplace = jsonObject.optString("Discount");
                                String afterReplace = beforeReplace.replace("-", "");
                                binding.discount.setText("(-) " + afterReplace);
                                // Log.e("afterReplace", afterReplace);
                                binding.discount.setTextColor(getResources().getColor(R.color.red));
                                binding.disDrCr.setText("(DR)");
                                binding.disDrCr.setTextColor(getResources().getColor(R.color.red));
                            } else if (Double.parseDouble(String.valueOf(int_dis)) <= 0 || Double.parseDouble(String.valueOf(discount)) <= 0) {
                                binding.disCount.setVisibility(View.GONE);
                                binding.intRest.setVisibility(View.GONE);
                                binding.viewInterest.setVisibility(View.GONE);
                                binding.viewDiscount.setVisibility(View.GONE);
                            }
                            if (!jsonObject.optString("AvgDays").equals("0") || jsonObject.optString("AvgDays").equals("0.00")) {
                                binding.avgDays.setText("Avg Days : " + jsonObject.optString("AvgDays"));
                                binding.avgDays.setVisibility(View.VISIBLE);

                            } else {
                                binding.avgDays.setVisibility(View.GONE);

                            }
                            String availLimit = jsonObject.optString("AvaialbleLimit").equalsIgnoreCase("") ? "0" : jsonObject.optString("AvaialbleLimit");
                            double avail_Limit = f.parse(availLimit).doubleValue();
                            if (Double.parseDouble(String.valueOf(avail_Limit)) > 0) {
                                binding.avaialbleLimit.setText(availLimit);
                                binding.avaialbleLimit.setTextColor(getResources().getColor(R.color.green));

                            } else if (Double.parseDouble(String.valueOf(avail_Limit)) < 0) {
                                binding.avaialbleLimit.setText("No Limit");
                                binding.avaialbleLimit.setTextColor(getResources().getColor(R.color.red));

                            } else if (Double.parseDouble(String.valueOf(avail_Limit)) <= 0) {
                                binding.avaialbleLimit.setText("No Limit");
                                binding.avaialbleLimit.setTextColor(getResources().getColor(R.color.red));

                            }
                            binding.dashboardGraph.setChart(pie);

//                            binding.includeProgress.progress.setVisibility(View.GONE);

                        } else {
                            binding.includeProgress.progress.setVisibility(View.GONE);
                            binding.includeProgress.noData.setVisibility(View.VISIBLE);
                            binding.layoutGone.setVisibility(View.GONE);
                            Toast.makeText(mContext, jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | ParseException e) {
                        Toast.makeText(mContext, "Server Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }, error -> {
            //GetDashboardDetails();
            binding.includeProgress.progress.setVisibility(View.GONE);
            progressBar.dismiss();
            networkConnetion3(mContext);
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"ACCOUNTID\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\"}";
                Log.e("str", str);
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

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
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        cross.setOnClickListener(v -> alertDialog.dismiss());
        try_button.setOnClickListener(view -> {
            GetDashboardDetails();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }


    private void GetBalanceTillDate() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_DASHBOARD__DEATILS_BALANCE_TILL_DATE,
                response -> {
                    //  Log.e("Data", response);
                    Log.i("TaG",GET_DASHBOARD__DEATILS_BALANCE_TILL_DATE + "=========" + response);
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show()
                    BalanceTillDatePojo pojo = new Gson().fromJson(response, listType);
                    try {
                        if (pojo.getResponseStatus()) {
                            isBalanceCalled = true;
                            balanceTillDate_List.clear();
                            balanceTillDate_List.addAll(pojo.getPendingOrderDetails());
                            balanceTillDate_adap.notifyDataSetChanged();
                            binding.balntillImsg.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_down_24));

                        } else {
                            binding.balntillImsg.setVisibility(View.GONE);
                            binding.llbalntillImsg.setOnClickListener(null);
                            binding.balntillImsg.setImageDrawable(ContextCompat.getDrawable(mContext, R.color.white));
                            //Toast.makeText(mContext, pojo.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(mContext, "Server Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error ->
                        networkConnetion3(mContext)) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                /*{
                    "PartyID": "TET697" // partyCode
                }*/

                String str = "{\"MOBILENO\":\"" + mob3 +"\",\"PartyID\":\"" +  SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"ACCOUNTID\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\"}";
                Log.e("str", str);
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }


    private void GetStockInOffice() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_DASHBOARD__DEATILS_STOCK_IN_OFFICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                          Log.e("DataStock", response);
                        binding.includeProgress.progress.setVisibility(View.GONE);
                        //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show()
                        StockInOfficePojo pojo = new Gson().fromJson(response, listType_stock);
                        try {
                            if (pojo.getResponseStatus()) {
                                stockInOfficeDetails.clear();
                                stockInOfficeDetails.addAll(pojo.getStockinOfficeDetail());
                                stockInOffice_adpder.notifyDataSetChanged();
                            } else {
                                binding.llstockText.setOnClickListener(null);
                                binding.stockText.setImageDrawable(ContextCompat.getDrawable(mContext, R.color.white));
                                binding.stockVisibility.setVisibility(View.GONE);
                                //Toast.makeText(mContext, pojo.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(mContext, "Server Error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                },
                error ->
                        networkConnetion3(mContext)) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"ACCOUNTID\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\"}";
                //  Log.e("str", str);
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void GetPendingOrder() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_DASHBOARD_DETAILS_PENDING_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   Log.e("Data", response);
                        Log.i("TaG",GET_DASHBOARD_DETAILS_PENDING_ORDER + "=========" + response);
                        binding.includeProgress.progress.setVisibility(View.GONE);
                        //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show()
                        PendingOrderPojoDash pojo = new Gson().fromJson(response, listType_pending);
                        try {
                            if (pojo.getResponseStatus()) {
                                isPendingCalled = true;
                                // binding.pendingText.setVisibility(View.VISIBLE);
                                pendingOrderDetails.clear();
                                pendingOrderDetails.addAll(pojo.getPendingOrderDetails());
                                pendingOrder_adap.notifyDataSetChanged();
                            } else {
                                binding.llPendingText.setOnClickListener(null);
                                binding.pendingText.setImageDrawable(ContextCompat.getDrawable(mContext, R.color.white));
                                binding.pendingVisibility.setVisibility(View.GONE);
                                // Toast.makeText(mContext, pojo.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(mContext, "Server Error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                },
                error ->
                        networkConnetion3(mContext)) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"ACCOUNTID\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\"}";
                // Log.e("str", str);
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

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void GetInterest_Discount(String datakey) {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_DASHBOARD__DEATILS_BALANCE_INTREST_DISCOUNT,
                response -> {
                     Log.e("DataDis", response);
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show()
                    InDisPojo pojo = new Gson().fromJson(response, listType_intDis);
                    try {
                        if (pojo.getResponseStatus()) {
                            // Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                            if (datakey.equals("discount")) {
                                isDiscountCalled = true;
                            } else {
                                isInterestCalled = true;
                            }
                            binding.intUpDown.setVisibility(View.VISIBLE);
                            binding.disUpDown.setVisibility(View.VISIBLE);
                            interestDiscountDetails.clear();
                            interestDiscountDetails.addAll(pojo.getInterestDiscountDetails());

                        } else {
                            binding.llintUpDown.setOnClickListener(null);
                            binding.lldisUpDown.setOnClickListener(null);
                            binding.intUpDown.setVisibility(View.GONE);
                            binding.intVisibility.setVisibility(View.GONE);
                            binding.disVisibility.setVisibility(View.GONE);
                            binding.disUpDown.setVisibility(View.GONE);
                            // Toast.makeText(mContext, pojo.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                        insDis_adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e("IntExec", e.toString());
                        Toast.makeText(mContext, "Server Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("IntExec", error.toString());
                    networkConnetion3(mContext);
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"ACCOUNTID\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\",\"DATAKEY\":\"" + datakey + "\"}";
                Log.e("Disstr", str);
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }


}