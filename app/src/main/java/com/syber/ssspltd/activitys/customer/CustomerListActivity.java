package com.syber.ssspltd.activitys.customer;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_BLACK_LIST_NAME;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.Responses.customer.BlackListedName;
import com.syber.ssspltd.Responses.customer.CustomerListPojo;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.CustomerAdptr;
import com.syber.ssspltd.databinding.ActivityCustomerListBinding;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerListActivity extends AppCompatActivity {

    public static ArrayList<BlackListedName> blackListedNameList, data;
    public static ArrayList<BlackListedName> mData = new ArrayList<>();
    CustomerAdptr customerAdptr;
    private ActivityCustomerListBinding binding;
    private Type listType;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerListBinding.inflate(getLayoutInflater(), null, false);
        setContentView(binding.getRoot());

        listType = new TypeToken<CustomerListPojo>() {
        }.getType();
        blackListedNameList = new ArrayList<>();
        data = new ArrayList<>();


        binding.toolbar.setTitle("Customer");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        customerAdptr = new CustomerAdptr(this, blackListedNameList);
        binding.recyclerview.setAdapter(customerAdptr);


        if (data.size() > 0) {
            filterBc(mData);

        } else {
            getCustomerList();
        }

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                data.clear();
                for (int p = 0; p < blackListedNameList.size(); p++) {
                    if (blackListedNameList.get(p).getName().toLowerCase().contains(arg0.toString().toLowerCase())
                            || blackListedNameList.get(p).getGSTNo().toLowerCase().contains(arg0.toString().toLowerCase())
                            || blackListedNameList.get(p).getOwnerName().toLowerCase().contains(arg0.toString().toLowerCase())
                            || blackListedNameList.get(p).getMobileNo().toLowerCase().contains(arg0.toString().toLowerCase())
                            || blackListedNameList.get(p).getAddress().toLowerCase().contains(arg0.toString().toLowerCase())) {
                        data.add(blackListedNameList.get(p));
                    }
                }
                filterBc(data);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }
        });


    }

    void filterBc(ArrayList<BlackListedName> bc) {
        // MyPref.storePrefs(context).setTotalMechanics(bc.size() + "");
        Log.e("bc", bc.size() + "");
        binding.noOfRecord.setText("(" + bc.size() + " records )");
        customerAdptr = new CustomerAdptr(mContext, bc);
        binding.recyclerview.setAdapter(customerAdptr);
    }

    private void getCustomerList() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_BLACK_LIST_NAME, response -> {
//                    Log.e("Data", response);
            binding.includeProgress.progress.setVisibility(View.GONE);
            Log.i("TaG", "url ---" + GET_BLACK_LIST_NAME);
            Log.i("TaG", "response ---> " + response);
            CustomerListPojo pojo = new Gson().fromJson(response, listType);
            try {
                if (pojo.getResponseStatus()) {
                    blackListedNameList.clear();
                    blackListedNameList.addAll(pojo.getBlackListedName());
                    binding.noOfRecord.setText("(" + pojo.getBlackListedName().size() + " records)");
                    customerAdptr.notifyDataSetChanged();
                } else {
                    AlertUtil.responseElse(mContext, "", pojo.getResponseMessage() + "");
                }
            } catch (JsonIOException e) {
                AlertUtil.responseExecption(mContext, "BlackListedName ", e.toString());
            }

        }, error ->
        {
            try {
                Constants.convertByteToString(mContext, "BlackListedName ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }
        };
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
}