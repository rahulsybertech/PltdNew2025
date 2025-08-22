package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_USER_LIST;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.saleFilterAdapter.SuppAdminAdapter.ChooseCatagriesAdp;
import com.syber.ssspltd.adapter.saleFilterAdapter.SuppAdminAdapter.CustomerDialogAdapter;
import com.syber.ssspltd.adapter.saleFilterAdapter.SuppAdminAdapter.EmployeeDialogAdapte;
import com.syber.ssspltd.response.ChooseCatagriesRespo.CustomerListResult;
import com.syber.ssspltd.response.ChooseCatagriesRespo.EmployeeListResult;
import com.syber.ssspltd.response.ChooseCatagriesRespo.SupplierListResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseCategries extends AppCompatActivity {

    public static TextView suplierClick, customClick, employeeClick;
    public static ArrayAdapter<String> type_Adapter1, type_Adapter2, type_Adapter3;
    public static ArrayList<String> type_List1 = new ArrayList<>();
    public static ArrayList<String> type_List2 = new ArrayList<>();
    public static ArrayList<String> type_List3 = new ArrayList<>();
    public static String cust_num, part_co;
    public static ArrayList<SupplierListResult> salepartyModelList, saleData;
    public static ArrayList<SupplierListResult> sData = new ArrayList<>();
    public static ArrayList<CustomerListResult> CustomerList, CustomerData;
    public static ArrayList<CustomerListResult> CData = new ArrayList<>();
    public static ArrayList<EmployeeListResult> EmployeeList, EmployeeData;
    public static ArrayList<EmployeeListResult> EData = new ArrayList<>();
    public static String partyCode;
    public static String mobNo;
    TextView nextPage;
    String str;
    Context mContext = this;
    RelativeLayout supVisility, cusVisility, empVisility;
    String supplier;
    ChooseCatagriesAdp chooseCatagriesAdp;
    CustomerDialogAdapter customerDialogAdapter;
    EmployeeDialogAdapte employeeDialogAdapte;
    ProgressBar progressBar;
    EditText search;
    TextView titile;
    RecyclerView recyclerView;
    SupplierListResult supplierListResult;
    String suppl;
    RadioButton suppler, customer, emplooye;
    String selectedName = "";
    private Dialog sDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_categries);

        nextPage = findViewById(R.id.nextPage);
        suplierClick = findViewById(R.id.suplierClick);

        customClick = findViewById(R.id.customClick);
        employeeClick = findViewById(R.id.employeeClick);

        supVisility = findViewById(R.id.supVisility);
        cusVisility = findViewById(R.id.cusVisility);
        empVisility = findViewById(R.id.empVisility);
        RadioButton radiosupplier = findViewById(R.id.supplier);
        RadioButton radiocustomer = findViewById(R.id.custom);
        RadioButton radioemployee = findViewById(R.id.employee);

//        suppler=findViewById(R.id.suppler);
//        customer=findViewById(R.id.customer);
//        emplooye=findViewById(R.id.emplooye);
//        supplier
        SharedPref.init(mContext);


        suplierClick.setOnClickListener(v -> {
            SharedPref.write(SharedPref.DASHBOARD_TYPE, "Supplier");
            searchDialog("Supplier");
        });
        customClick.setOnClickListener(v -> {
            SharedPref.write(SharedPref.DASHBOARD_TYPE, "Customer");
            CustomerDialog("Customer");
        });
        employeeClick.setOnClickListener(v -> {
            SharedPref.write(SharedPref.DASHBOARD_TYPE, "Other");
            employeeDialog("Others");
        });


        if (SharedPref.read(SharedPref.typeNumber, "").equals("4")) {
            radioemployee.setVisibility(View.GONE);
        } else {
            radioemployee.setVisibility(View.VISIBLE);
        }


        salepartyModelList = new ArrayList<>();
        saleData = new ArrayList<>();

        CustomerList = new ArrayList<>();
        CustomerData = new ArrayList<>();

        EmployeeList = new ArrayList<>();
        EmployeeData = new ArrayList<>();
        // suplierClick.setAdapter(type_Adapter);

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPref.write(SharedPref.PARTY_CODE,partyCode);
//                SharedPref.write(SharedPref.USERMOBILE,mobNo);

                if (radiosupplier.isChecked() && (!suplierClick.getText().toString().isEmpty()) || radiocustomer.isChecked() && (!customClick.getText().toString().isEmpty()) || radioemployee.isChecked() && (!employeeClick.getText().toString().isEmpty())) {
                    startActivity(new Intent(ChooseCategries.this, MainActivity.class)
                            .putExtra("", supplier));
                    SharedPref.write(SharedPref.TYPE, "Admin");
                    SharedPref.write(SharedPref.BACK_BUTTON, "5");
                    SharedPref.write(SharedPref.IS_ANY_CHOOSEN, "true");

                    finish();
                } else {
                    Toast.makeText(mContext, "Select Any one", Toast.LENGTH_LONG).show();
                }
            }
        });
//        if (Lazy.haveNetworkConnection(mContext)){
        // GetUsersTypeList();
//            GetUsersTypeList1();
//            GetUsersTypeList2();
//        }else {
//            networkConnetion3(mContext);
//        }

    }


    private void GetUsersTypeList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_USER_LIST, response -> {
            Log.e("Data", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("ResponseStatus")) {
                    JSONArray BankListData = jsonObject.getJSONArray("SupplierListResult");
                    salepartyModelList.clear();
                    for (int i = 0; i < BankListData.length(); i++) {
                        JSONObject ob = BankListData.getJSONObject(i);
                        String name = ob.optString("Name");
                        String psrty = ob.optString("PartyCode");
                        String mobNo = ob.optString("UserMobileNo");
                        Log.e("name", mobNo);
                        salepartyModelList.add(new SupplierListResult("", name, psrty, "", mobNo, "",""));
                    }
                    chooseCatagriesAdp.notifyDataSetChanged();
                } else {
                    AlertUtil.responseElse(mContext, "GetUserList ", jsonObject.optString("ResponseMessage") + "");
                }
            } catch (JSONException e) {
                AlertUtil.responseExecption(mContext, "GetUserList ", e.toString());
            }
        }, error -> {
            try {
                Constants.convertByteToString(mContext, "GetUserList ", error);
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
                String mob = SharedPref.read(SharedPref.USERMOBILE, "");
                String str = "{\"MOBILENO\":\"" + mob + "\",\"USERTYPE\":\"" + "5" + "\"}";
                Log.e("abcstr", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void GetUsersTypeList1() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_USER_LIST, response -> {
            Log.e("Data", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("ResponseStatus") == true) {

                    JSONArray CustomerListData = jsonObject.getJSONArray("CustomerListResult");
                    Log.e("CustomerListResult", CustomerListData + "");

                    CustomerList.clear();
                    for (int i = 0; i < CustomerListData.length(); i++) {
                        JSONObject ob = CustomerListData.getJSONObject(i);
                        String name = ob.optString("Name");
                        Log.e("c+name", name);
                        String psrty = ob.optString("PartyCode");
                        String mobNo = ob.optString("UserMobileNo");
                        // supplierListResult = new SupplierListResult(name);
                        CustomerList.add(new CustomerListResult("", name, psrty, "", mobNo, ""));
                    }
                    customerDialogAdapter.notifyDataSetChanged();
                } else {
                    AlertUtil.responseElse(mContext, "GetUserList ", jsonObject.optString("ResponseMessage") + "");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                AlertUtil.responseExecption(mContext, "GetUserList ", e.toString());
            }
        }, error ->
        {
            try {
                Constants.convertByteToString(mContext, "GetUserList ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob = SharedPref.read(SharedPref.USERMOBILE, "");
                String str = "{\"MOBILENO\":\"" + mob + "\",\"USERTYPE\":\"" + "5" + "\"}";
                Log.e("abcstr", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void GetUsersTypeList2() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_USER_LIST, response -> {
            Log.e("Data", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("ResponseStatus") == true) {

                    JSONArray EmployeeListData = jsonObject.getJSONArray("EmployeeListResult");
                    Log.e("EmployeeListData", EmployeeListData + "");

                    EmployeeList.clear();

                    for (int i = 0; i < EmployeeListData.length(); i++) {
                        JSONObject ob = EmployeeListData.getJSONObject(i);
                        String name = ob.optString("Name");
                        Log.e("e_name", name);
                        String psrty = ob.optString("PartyCode");
                        String mobNo = ob.optString("UserMobileNo");
                        // supplierListResult = new SupplierListResult(name);
                        EmployeeList.add(new EmployeeListResult("", name, psrty, "", mobNo, ""));
                    }

                    employeeDialogAdapte.notifyDataSetChanged();

                } else {
                    AlertUtil.responseElse(mContext, "GetUserList ", jsonObject.optString("ResponseMessage") + "");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                AlertUtil.responseExecption(mContext, "GetUserList ", e.toString());
            }
        }, error ->
        {
            try {
                Constants.convertByteToString(mContext, "GetUserList ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob = SharedPref.read(SharedPref.USERMOBILE, "");
                String str = "{\"MOBILENO\":\"" + mob + "\",\"USERTYPE\":\"" + "5" + "\"}";
                Log.e("abcstr", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.supplier:
                if (checked)
                    selectedName = suplierClick.getText().toString();
                supVisility.setVisibility(View.VISIBLE);
//                supplier=suplierClick.getSelectedItem().toString();
                cusVisility.setVisibility(View.GONE);
                empVisility.setVisibility(View.GONE);
                break;
            case R.id.custom:
                if (checked)
                    selectedName = customClick.getText().toString();
                //   searchDialog("");
                cusVisility.setVisibility(View.VISIBLE);
                supVisility.setVisibility(View.GONE);
                empVisility.setVisibility(View.GONE);
                break;
            case R.id.employee:
                if (checked)
                    selectedName = employeeClick.getText().toString();
                //suppGetList=employeeClick.getText().toString();
                empVisility.setVisibility(View.VISIBLE);
                cusVisility.setVisibility(View.GONE);
                supVisility.setVisibility(View.GONE);
                break;
        }
        //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    private void searchDialog(final String title) {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
        search = sDialog.findViewById(R.id.search);
        sDialog.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
            }
        });
        GetUsersTypeList();
        if (saleData.size() > 0) {
            filterBc(sData);
        } else {
            GetUsersTypeList();
        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saleData.clear();
                for (int p = 0; p < salepartyModelList.size(); p++) {
                    if (salepartyModelList.get(p).getName().toLowerCase().contains(charSequence.toString().toLowerCase())
                    ) {
                        saleData.add(salepartyModelList.get(p));
                    }
                }
                filterBc(saleData);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        chooseCatagriesAdp = new ChooseCatagriesAdp(this, salepartyModelList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chooseCatagriesAdp);

        sDialog.show();
    }


    private void CustomerDialog(final String title) {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
        progressBar = sDialog.findViewById(R.id.my_progress);
        search = sDialog.findViewById(R.id.search);
        sDialog.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
            }
        });
        if (CustomerData.size() > 0) {
            filtercustomer(CData);
        } else {
            GetUsersTypeList1();
        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CustomerData.clear();
                for (int p = 0; p < CustomerList.size(); p++) {
                    if (CustomerList.get(p).getName().toLowerCase().contains(charSequence.toString().toLowerCase())
                    ) {
                        CustomerData.add(CustomerList.get(p));
                    }
                }
                filtercustomer(CustomerData);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        customerDialogAdapter = new CustomerDialogAdapter(this, CustomerList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customerDialogAdapter);
        GetUsersTypeList1();

        sDialog.show();
    }


    private void employeeDialog(final String title) {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
        search = sDialog.findViewById(R.id.search);
        // GetUsersTypeList();
        sDialog.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
            }
        });
        if (EmployeeData.size() > 0) {
            filterEmployee(EData);
            Log.e("if", "if");
        } else {
            GetUsersTypeList2();
            Log.e("else", "else");
        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                EmployeeData.clear();
                for (int p = 0; p < EmployeeList.size(); p++) {
                    if (EmployeeList.get(p).getName().toLowerCase().contains(charSequence.toString().toLowerCase())
                    ) {
                        EmployeeData.add(EmployeeList.get(p));
                    }
                }
                filterEmployee(EmployeeData);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        employeeDialogAdapte = new EmployeeDialogAdapte(this, EmployeeList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(employeeDialogAdapte);
        GetUsersTypeList2();

        sDialog.show();
    }

    void filterBc(ArrayList<SupplierListResult> bc) {
        // MyPref.storePrefs(context).setTotalMechanics(bc.size() + "");
        chooseCatagriesAdp = new ChooseCatagriesAdp(ChooseCategries.this, bc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chooseCatagriesAdp);

    }


    void filtercustomer(ArrayList<CustomerListResult> bc) {
        // MyPref.storePrefs(context).setTotalMechanics(bc.size() + "");
        customerDialogAdapter = new CustomerDialogAdapter(ChooseCategries.this, bc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customerDialogAdapter);

    }

    void filterEmployee(ArrayList<EmployeeListResult> bc) {
        // MyPref.storePrefs(context).setTotalMechanics(bc.size() + "");
        employeeDialogAdapte = new EmployeeDialogAdapte(ChooseCategries.this, bc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(employeeDialogAdapte);

    }


    public void setsupplierList(SupplierListResult supplierListResult) {

        sDialog.dismiss();
        String n = supplierListResult.getName();
        SharedPref.write(SharedPref.SELECTED, n);
        SharedPref.write(SharedPref.PARTY_CODE, supplierListResult.getPartyCode());
        SharedPref.write(SharedPref.
                PURCHASE_PARTY_ID, supplierListResult.getID());
        SharedPref.write(SharedPref.
                PERMISSION_TYPE, supplierListResult.getPermissionType());
        //SharedPref.write(SharedPref.USERMOBILE, supplierListResult.getUserMobileNo());
        Log.e("SharedPref.USERMOBILE", supplierListResult.getUserMobileNo());
        Log.e("party_code", supplierListResult.getPartyCode());
        Log.e("mobNo", supplierListResult.getUserMobileNo());
        suplierClick.setText(n);
    }

    public void setCustomerList(CustomerListResult customerList) {

        sDialog.dismiss();
        String n = customerList.getName();
        SharedPref.write(SharedPref.SELECTED, n);
        SharedPref.write(SharedPref.PARTY_CODE, customerList.getPartyCode());
        // SharedPref.write(SharedPref.USERMOBILE, customerList.getUserMobileNo());
        Log.e("party_code", customerList.getPartyCode());
        Log.e("mobNo", customerList.getUserMobileNo());
        customClick.setText(n);

    }

    public void setEmployeeList(EmployeeListResult employeeListResult) {
        sDialog.dismiss();
        String n = employeeListResult.getName();
//         partyCode = employeeListResult.getPartyCode();
//         mobNo = employeeListResult.getUserMobileNo();

        SharedPref.write(SharedPref.SELECTED, n);
        SharedPref.write(SharedPref.PARTY_CODE, employeeListResult.getPartyCode());
        //SharedPref.write(SharedPref.USERMOBILE, employeeListResult.getUserMobileNo());

        Log.e("party_code", employeeListResult.getPartyCode());
        Log.e("mobNo", employeeListResult.getUserMobileNo());

        employeeClick.setText(n);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
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
                GetUsersTypeList();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}


