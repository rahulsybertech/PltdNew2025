package com.syber.ssspltd.fragment;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_BANNER_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_SECURITY_CHECK_REPORT;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.enums.IndicatorAnimationType;
import com.jama.carouselview.enums.OffsetType;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.activitys.LoginPage;
import com.syber.ssspltd.adapter.DashBoardAdapter;
import com.syber.ssspltd.adapter.SliderAdapter;
import com.syber.ssspltd.helper.MovableFloatingActionButton;
import com.syber.ssspltd.response.BannerResponse.Banner.BannerList;
import com.syber.ssspltd.response.BannerResponse.Banner.BannerPojo;
import com.syber.ssspltd.response.DeasbordListType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class HomeFragment extends Fragment {

    public static List<BannerList> bannerList;
    public static LinearLayout securityCheck_vi;
    public static boolean supplierOrderStatus = false;
    public static String getUserStatus, taxType;
    static TextView pen_bel, current_bel, textPending;
    public int[] images = {R.drawable.button_one,
            R.drawable.button_two, R.drawable.button_three, R.drawable.button_four, R.drawable.button_five};
    SliderView sliderView;
    CarouselView carouselView;
    TextView pending_order, courier_report, ledger, sale_report, stock_in_office, sale_service, dash_board, Dr_Note, create_note, cr_noteSuppl, dr_NoteCust;
    Type listType, bannerType;
    String[] list1_name = {"Ledger", "Debit Note", "Credit Note To Supplier", "Sale Service", "Brands", "Customer Review", "Pending Order", "Honhar Khiladi", "Add Order","StayBooking"};
    String[] list1_onclickId = {"1", "2", "3", "4", "13", "16", "23", "25", "22","26"};
    Integer list1_Img[] = {R.drawable.button_two, R.drawable.button_nine, R.drawable.button_eleven, R.drawable.button_eight, R.drawable.button_six, R.drawable.button_twelve, R.drawable.button_two, R.drawable.button_thirteen, R.drawable.button_four,R.drawable.button_four};

    String[] list2_name = {"DashBoard", "Ledger", "Sale Report", "Stock in office", "Pending Order", "Courier Report", "Debit Note To Customer", "Credit Note", "Sale Service", "Why SSS", "Brands"
            , "Customer Review", "Club Features", "Honhar Khiladi","StayBooking"};
    String[] list2_onclickId = {"5", "1", "6", "7", "8", "9", "10", "11", "4", "12", "13", "16", "24", "25","26"};
    Integer list2_Img[] = {R.drawable.button_co, R.drawable.button_two, R.drawable.button_eight, R.drawable.button_three
            , R.drawable.button_four, R.drawable.button_six, R.drawable.button_twelve, R.drawable.button_ten,
            R.drawable.button_grey, R.drawable.button_four, R.drawable.button_six, R.drawable.button_twelve, R.drawable.button_nine, R.drawable.button_thirteen,R.drawable.button_thirteen};
    String[] list_newuser = {"Why SSS", "Branches", "Brands", "Offers", "Customer Review", "Gallery", "Bank Details", "Feedback", "Apply KYC", "DashBoard", "Ledger", "Sale Report", "Stock in office", "Pending Order", "Courier Report", "Debit Note To Customer", "Credit Note", "Sale Service"};
    String[] newuser_onclickId = {"12", "14", "13", "21", "16", "15", "18", "19", "17", "20", "5", "1", "6", "7", "8", "9", "10", "11", "4"};
    Integer newuser_Img[] = {R.drawable.button_co, R.drawable.button_two, R.drawable.button_eight, R.drawable.button_three, R.drawable.button_co, R.drawable.button_two, R.drawable.button_eight, R.drawable.button_three
            , R.drawable.button_four, R.drawable.button_six, R.drawable.button_twelve, R.drawable.button_ten, R.drawable.button_four, R.drawable.button_six, R.drawable.button_twelve, R.drawable.button_ten,
            R.drawable.button_grey, R.drawable.button_three};

    String[] list3_name = {"Why SSS", "Ledger", "Brands", "Customer Review"};
    String[] list3_onclickId = {"12", "1", "13", "16"};
    Integer list3_Img[] = {R.drawable.button_two, R.drawable.button_four, R.drawable.button_six, R.drawable.button_twelve};
    String[] list4_name = {"Kavita"};
    String[] list4_onclickId = {"1"};
    Integer list4_Img[] = {R.drawable.button_one};
    List<DeasbordListType> deasbordListTypeList;

    DeasbordListType deasbordListType;
    DashBoardAdapter dashBoardAdapter;
    RecyclerView recyclerView;
    String uploadImg_click = "";
    MovableFloatingActionButton support_fab;
    LinearLayout current_belTab;
    private SliderAdapter sliderAdapterExample;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        pending_order = view.findViewById(R.id.pending_order);
        courier_report = view.findViewById(R.id.courier_report);
        sale_report = view.findViewById(R.id.sale_report);
        stock_in_office = view.findViewById(R.id.stock_in_office);
        sale_service = view.findViewById(R.id.sale_service);
        dash_board = view.findViewById(R.id.dash_board);
        Dr_Note = view.findViewById(R.id.Dr_Note);
        dr_NoteCust = view.findViewById(R.id.dr_NoteCust);
        cr_noteSuppl = view.findViewById(R.id.cr_noteSuppl);
        create_note = view.findViewById(R.id.create_note);
        carouselView = view.findViewById(R.id.carouselView);
        textPending = view.findViewById(R.id.textPending);
        current_belTab = view.findViewById(R.id.current_belTab);

        securityCheck_vi = view.findViewById(R.id.securityCheck_vi);
        current_bel = view.findViewById(R.id.current_bel);
        pen_bel = view.findViewById(R.id.pen_bel);
        support_fab = view.findViewById(R.id.support_fab);

        deasbordListTypeList = new ArrayList<>();
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.e("login_page", SharedPref.read(SharedPref.USERMOBILE, ""));
        Log.e("DASHBOARD_TYPE1", SharedPref.read(SharedPref.DASHBOARD_TYPE, ""));
        if (SharedPref.read(SharedPref.DASHBOARD_TYPE, "").equals("Supplier")) {
            Log.e("DASHBOARD_TYPE", SharedPref.read(SharedPref.DASHBOARD_TYPE, ""));
            for (int i = 0; i < list1_name.length; i++) {
                Log.e("list1_name", list1_name[i]);
                deasbordListType = new DeasbordListType(list1_onclickId[i], list1_name[i], list1_Img[i]);
                deasbordListTypeList.add(deasbordListType);
            }
            dashBoardAdapter = new DashBoardAdapter(getContext(), deasbordListTypeList, false);
            recyclerView.setAdapter(dashBoardAdapter);
            securityCheck_vi.setVisibility(View.GONE);
        }
        if (SharedPref.read(SharedPref.DASHBOARD_TYPE, "").equals("Customer")) {
            Log.e("DASHBOARD_TYPE", SharedPref.read(SharedPref.DASHBOARD_TYPE, ""));
            for (int i = 0; i < list2_name.length; i++) {
                deasbordListType = new DeasbordListType(list2_onclickId[i], list2_name[i], list2_Img[i]);
                deasbordListTypeList.add(deasbordListType);
            }

            dashBoardAdapter = new DashBoardAdapter(getContext(), deasbordListTypeList, false);
            recyclerView.setAdapter(dashBoardAdapter);
            securityCheck_vi.setVisibility(View.VISIBLE);

        }
        if (SharedPref.read(SharedPref.DASHBOARD_TYPE, "").equals("Other")) {
            Log.e("DASHBOARD_TYPE", SharedPref.read(SharedPref.DASHBOARD_TYPE, ""));
            for (int i = 0; i < list3_name.length; i++) {
                deasbordListType = new DeasbordListType(list3_onclickId[i], list3_name[i], list3_Img[i]);
                deasbordListTypeList.add(deasbordListType);
            }
            securityCheck_vi.setVisibility(View.GONE);
            dashBoardAdapter = new DashBoardAdapter(getContext(), deasbordListTypeList, false);
            recyclerView.setAdapter(dashBoardAdapter);
        }
        if (SharedPref.read(SharedPref.DASHBOARD_TYPE, "").equals("New User")) {
            Log.e("DASHBOARD_TYPE", SharedPref.read(SharedPref.DASHBOARD_TYPE, ""));
            for (int i = 0; i < list_newuser.length; i++) {
                deasbordListType = new DeasbordListType(newuser_onclickId[i], list_newuser[i], newuser_Img[i]);
                deasbordListTypeList.add(deasbordListType);
            }
            securityCheck_vi.setVisibility(View.GONE);
            textPending.setVisibility(View.GONE);
            pen_bel.setVisibility(View.GONE);
            current_belTab.setVisibility(View.GONE);
            dashBoardAdapter = new DashBoardAdapter(getContext(), deasbordListTypeList, true);
            recyclerView.setAdapter(dashBoardAdapter);
        }
        if (SharedPref.read(SharedPref.DASHBOARD_TYPE, "").equals("")) {
            Log.e("DASHBOARD_TYPE", SharedPref.read(SharedPref.DASHBOARD_TYPE, ""));
            for (int i = 0; i < list4_name.length; i++) {
                deasbordListType = new DeasbordListType(list4_onclickId[i], list4_name[i], list4_Img[i]);
                deasbordListTypeList.add(deasbordListType);
            }
            dashBoardAdapter = new DashBoardAdapter(getContext(), deasbordListTypeList, false);
            recyclerView.setAdapter(dashBoardAdapter);
            securityCheck_vi.setVisibility(View.GONE);
        }
        bannerType = new TypeToken<com.syber.ssspltd.response.BannerResponse.Banner.BannerPojo>() {
        }.getType();
        String blank_partyCode = SharedPref.read(SharedPref.PARTY_CODE, "");
        if (blank_partyCode.equals("new")) {
            SharedPref.write(SharedPref.PARTY_CODE, "");
            getBanner();
        } else if (blank_partyCode.equals("")) {
            getBanner();
        } else if (blank_partyCode.equals(SharedPref.read(SharedPref.PARTY_CODE, ""))) {
            getBanner();
        }
        if (Lazy.haveNetworkConnection(getContext())) {
            GetSecurityCheckReport(getContext());
        } else {
            //abhinav_poor_connection
            networkConnetion3(getContext());
        }
        //Lazy.loaderDialog(getContext());
        return view;
    }

    private void getBanner() {
//        final ProgressDialog progressBar = new ProgressDialog(getContext());
//        progressBar.setTitle(" Loading ");
//        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_BANNER_LIST,
                response -> {
                    Log.e("Data", response);
                    try {
                        BannerPojo bannerPojo = new Gson().fromJson(response, bannerType);
                        if (bannerPojo.getResponseStatus()) {
                            bannerList = new ArrayList<>();
                            bannerList.addAll(bannerPojo.getBannerList());
                            carouselView.setSize(bannerList.size());
                            Log.e("size", bannerList.size() + "");
                            carouselView.setResource(R.layout.image_view_page);
                            carouselView.setAutoPlay(true);
                            carouselView.setIndicatorAnimationType(IndicatorAnimationType.THIN_WORM);
                            carouselView.setCarouselOffset(OffsetType.CENTER);
                            carouselView.setCarouselViewListener((view, position) -> {
                                // Example here is setting up a full image carousel
                                BannerList img = bannerList.get(position);
                                Log.e("size", img + "");
                                ImageView imageView = view.findViewById(R.id.iamge_list);
                                imageView.setOnClickListener(v -> {
                                    // startActivity(new Intent(getContext(), BranchWithLogoActivity.class));
                                });
                                Picasso.with(getContext())
                                        .load(img.getLinkPath())
                                        .into(imageView);
                            });
                            // After you finish setting up, show the CarouselView
                            carouselView.show();

                        } else {
                            //Toast.makeText(getContext(), jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error ->
        {
            //Lazy.networkConnetion(getContext());
            //  ProgressDialog.show(getContext(), "Loading", "Poor Network Connection");
            //  progressBar.cancel();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob = SharedPref.read(SharedPref.USERMOBILE, "");
                // String mob=mobile_no_otp.getText().toString();
                String str = "{\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\"}";
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
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


    public void GetSecurityCheckReport(Context context) {
        // Toast.makeText(context, "goodtogo", Toast.LENGTH_SHORT).show();
        final ProgressDialog progressBar = new ProgressDialog(getContext());
//        progressBar.setTitle("Data Fetching ...");
        progressBar.setMessage("Please wait...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_SECURITY_CHECK_REPORT,
                response -> {
                    progressBar.cancel();
                    System.out.println();
                    Log.e("SecurityCheckRespo", response);
                    Log.i("TaG", "resp----->" + GET_SECURITY_CHECK_REPORT + " " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("ResponseStatus")) {
                            if (SharedPref.read(SharedPref.DASHBOARD_TYPE, "").equals("Customer") && !jsonObject.getBoolean("BlackListReportStatus")) {
                                deasbordListTypeList.clear();
                                for (int i = 0; i < (list2_name.length) - 1; i++) {
                                    deasbordListType = new DeasbordListType(list2_onclickId[i], list2_name[i], list2_Img[i]);
                                    deasbordListTypeList.add(deasbordListType);
                                }
                                dashBoardAdapter = new DashBoardAdapter(getContext(), deasbordListTypeList, false);
                                recyclerView.setAdapter(dashBoardAdapter);
                            } else if (SharedPref.read(SharedPref.DASHBOARD_TYPE, "").equals("Supplier") && !jsonObject.getBoolean("SupplierOrderStatus") && !jsonObject.getBoolean("BlackListReportStatus")) {
                                deasbordListTypeList.clear();
                                for (int i = 0; i < (list1_name.length) - 2; i++) {
                                    deasbordListType = new DeasbordListType(list1_onclickId[i], list1_name[i], list1_Img[i]);
                                    deasbordListTypeList.add(deasbordListType);
                                }
                                dashBoardAdapter.notifyDataSetChanged();
                                securityCheck_vi.setVisibility(View.GONE);
                            } else if (SharedPref.read(SharedPref.DASHBOARD_TYPE, "").equals("Supplier") && !jsonObject.getBoolean("SupplierOrderStatus") && jsonObject.getBoolean("BlackListReportStatus")) {
                                deasbordListTypeList.clear();
                                for (int i = 0; i < (list1_name.length) - 1; i++) {
                                    deasbordListType = new DeasbordListType(list1_onclickId[i], list1_name[i], list1_Img[i]);
                                    deasbordListTypeList.add(deasbordListType);
                                }
                                dashBoardAdapter.notifyDataSetChanged();
                                securityCheck_vi.setVisibility(View.GONE);
                            }

                            taxType = jsonObject.getString("TaxType");
                            Log.e("taxType", taxType);
                            JSONArray securityCheck = jsonObject.getJSONArray("SecurityCheckReportResult");
                            Log.e("BankListData", securityCheck + "");
                            if (jsonObject.getBoolean("StatusLock")) {
                                lockStatusDialog(jsonObject.getString("LockMsg"));
                            }
                            for (int i = 0; i < securityCheck.length(); i++) {
                                JSONObject ob = securityCheck.getJSONObject(i);
                                String Count = ob.optString("Count");
                                String CurrentBalance = ob.optString("CurrentBalance");
                                if (CurrentBalance != null) {
                                    current_bel.setText(CurrentBalance.equals("") ? "0" : CurrentBalance);
                                } else {
                                    current_bel.setText("0");
                                }

                                SharedPref.write(SharedPref.Current_Bal, CurrentBalance);
                                Log.e("name", Count);
                                pen_bel.setText(Count);
                                securityCheck_vi.setVisibility(View.GONE);
                                if (SharedPref.read(SharedPref.DASHBOARD_TYPE, "").equals("Customer")) {
                                    if (Count.equals("0")) {
                                        pen_bel.setText("3");
                                        securityCheck_vi.setVisibility(View.VISIBLE);
                                    } else if (Count.equals("1")) {
                                        pen_bel.setText("2");
                                        securityCheck_vi.setVisibility(View.VISIBLE);
                                    } else if (Count.equals("2")) {
                                        pen_bel.setText("1");
                                        securityCheck_vi.setVisibility(View.VISIBLE);
                                    } else if (Count.equals("3")) {
                                        pen_bel.setText("0");
                                        securityCheck_vi.setVisibility(View.VISIBLE);
                                    } else if (Count.equals("3")) {
                                        securityCheck_vi.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    securityCheck_vi.setVisibility(View.GONE);

                                }
                            }

                            if (taxType.equals("1")) {
                                Lazy.TextTypeDialog(context, taxType);
                            } else if (taxType.equals("0")) {

                            }
                            //   chooseCatagriesAdp.notifyDataSetChanged();
                        } else {
                            //logout
                            current_bel.setText("0");
                                String s = SharedPref.read(SharedPref.clubType,"");
                                SharedPref.clear();
                                SharedPref.write(SharedPref.clubType,s);
                                Log.i("TaG","after logout -=-=-=-=> " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
                                Intent logout = new Intent(context, LoginPage.class);
                                logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(logout);
                                ((Activity) context).finish();
                            // Toast.makeText(context, jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        current_bel.setText("0");
                        e.printStackTrace();
                    }
                }, error -> {
            current_bel.setText("0");
            progressBar.cancel();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob = SharedPref.read(SharedPref.USERMOBILE, "");
                // String mob=mobile_no_otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\"}";
                Log.e("straff", str);
                Log.i("TaG", "req----->" + GET_SECURITY_CHECK_REPORT + " " + str);
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
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void networkConnetion3(Context mContext) {
//abhinav_poor_connection
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
            getBanner();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

    public boolean lockStatusDialog(String statusLockMsg) {
        final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.lock_status_dialog, null);
        ImageView cross = dialogView.findViewById(R.id.cross);
        TextView ok = dialogView.findViewById(R.id.ok);
        TextView lockMsg = dialogView.findViewById(R.id.status_msg);
        lockMsg.setText(statusLockMsg);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.BottomSheetDialogTheme2);
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext(), R.style.RoundedDialog);

        builder.setView(dialogView);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        cross.setOnClickListener(v -> alertDialog.dismiss());
        ok.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();
        return true;
    }
}