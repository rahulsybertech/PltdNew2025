package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_USER_TYPE_LIST;
import static com.syber.ssspltd.Utils.SharedPref.ACCCESS_TOKEN;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.UsersTypeListAdapter;
import com.syber.ssspltd.response.UsersTypeResponse.UsersTypeListResult;
import com.syber.ssspltd.response.UsersTypeResponse.UsersTypePoojo;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class registered_msg extends AppCompatActivity {
    public static Integer pos = -1;
    public static String typePos = "", praty_code = "";
    public static String postionSelected = "";
    static List<UsersTypeListResult> UsersTyperDetails;
    TextView msg_ok;
    RecyclerView typeRecyclerview;
    Context mContext = this;
    UsersTypeListAdapter usersTypeListAdapter;
    Type listType;
    LinearLayoutManager linearLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_msg);
        msg_ok = findViewById(R.id.msg_ok);
        SharedPref.init(mContext);
//        Bundle bundle = new Bundle();
//        bundle.putInt("position", SharedPref.read(SharedPref.DB,pos));
        UsersTyperDetails = new ArrayList<>();

        listType = new TypeToken<UsersTypePoojo>() {
        }.getType();
        Log.i("TaG", "listType Initialized<><><>><>> " + listType.getTypeName() + " == " + listType);
        msg_ok.setOnClickListener(v -> {
            SharedPref.write(SharedPref.DB, pos.toString());
            SharedPref.write(SharedPref.LIST_TYPE, typePos);
            SharedPref.write(SharedPref.PARTY_CODE, praty_code);
            Log.e("typePos", typePos + "pos ::: " + pos + "pcode ::: " + praty_code);
            //  Toast.makeText(mContext, typePos+"m", Toast.LENGTH_SHORT).show();
            //    List<UsersTypeListResult> isSelected=UsersTyperDetails.stream().filter(p->p.isSelected()).collect(Collectors.toList());
            if (SharedPref.read(SharedPref.LIST_TYPE, "").equals("5")) {
                SharedPref.write(SharedPref.BACK_BUTTON, "5");
                startActivity(new Intent(registered_msg.this, ChooseCategries.class));
                finish();
                SharedPref.write(SharedPref.IS_SUPPER_SELECTED, "true");
                Log.e("pos", typePos);
            } else if (SharedPref.read(SharedPref.LIST_TYPE, "").equals("2")) {
                startActivity(new Intent(registered_msg.this, MainActivity.class));
                SharedPref.read(SharedPref.PARTY_CODE, praty_code);
                SharedPref.write(SharedPref.DASHBOARD_TYPE, "Supplier");
//                        SharedPref.write(SharedPref.TYPE,"Admin");
                Log.e("pos", typePos);
                
                Log.e("praty_code", praty_code);
                SharedPref.write(SharedPref.IS_SUPPER_SELECTED, "false");
            } else if (SharedPref.read(SharedPref.LIST_TYPE, "").equals("1")) {
                startActivity(new Intent(registered_msg.this, MainActivity.class));
                SharedPref.read(SharedPref.PARTY_CODE, praty_code);
                SharedPref.write(SharedPref.DASHBOARD_TYPE, "Customer");
//                           SharedPref.write(SharedPref.TYPE,"Admin");
                SharedPref.write(SharedPref.IS_SUPPER_SELECTED, "false");
                Log.e("pos", typePos);
                Log.e("praty_code", praty_code);
            } else if (SharedPref.read(SharedPref.LIST_TYPE, "").equals("3")) {
                startActivity(new Intent(registered_msg.this, MainActivity.class));
                SharedPref.read(SharedPref.PARTY_CODE, praty_code);
                SharedPref.write(SharedPref.DASHBOARD_TYPE, "Other");
//                           SharedPref.write(SharedPref.TYPE,"Admin");
                SharedPref.write(SharedPref.IS_SUPPER_SELECTED, "false");
                Log.e("pos", typePos);
                Log.e("praty_code", praty_code);
            } else if (SharedPref.read(SharedPref.LIST_TYPE, "").equals("")) {
                Toast.makeText(mContext, "Nothing Selected", Toast.LENGTH_SHORT).show();
            }


//                    }else
//                        {
//                            Log.e("pos","selected postion");
//                        }
//               else {
//                    startActivity(new Intent(registered_msg.this, MainActivity.class));
//                    SharedPref.read(SharedPref.PARTY_CODE,praty_code);
//                    //SharedPref.write(SharedPref.DASHBOARD_TYPE,"Other");
//                    SharedPref.write(SharedPref.TYPE,"Admin");
//                    Log.e("pos",typePos);
//                    Log.e("praty_code",praty_code);
//                }
        });


        typeRecyclerview = findViewById(R.id.typeRecyclerview);
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        typeRecyclerview.setLayoutManager(linearLayoutManager);
        usersTypeListAdapter = new UsersTypeListAdapter(mContext, UsersTyperDetails);
        typeRecyclerview.setAdapter(usersTypeListAdapter);
        if (Lazy.haveNetworkConnection(mContext)) {
            GetUsersTypeList();
        } else {
            networkConnetion3(mContext);
        }


    }

    private void GetUsersTypeList() {
        final ProgressDialog progressBar = new ProgressDialog(mContext);
        progressBar.setTitle("Fetching Data");
        // progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_USER_TYPE_LIST, response -> {
            Log.i("TaG", "URl --> " + GET_USER_TYPE_LIST);
            Log.e("Data", response);

            progressBar.dismiss();
            UsersTypePoojo pojo = new Gson().fromJson(response, listType);
            Log.i("TaG", "pojo --->" + pojo.getResponseStatus());
            try {
                if (pojo.getResponseStatus()) {
                    UsersTyperDetails.clear();
                    Log.e("ListSize", pojo.getUsersTypeListResult().size() + "");
                    UsersTyperDetails.addAll(pojo.getUsersTypeListResult());
                    usersTypeListAdapter.notifyDataSetChanged();
                } else {
                    Log.i("TaG", "pojo else");
                    AlertUtil.responseElse(mContext, "GetUsersTypeList ", pojo.getResponseMessage() + "");
                }
            } catch (Exception e) {
                Log.i("TaG", "get exception -==-=-=-=" + e);
                AlertUtil.responseExecption(mContext, "GetUsersTypeList ", e.toString());
            }

        }, error ->
        {
            try {
                Constants.convertByteToString(mContext, "GetUsersTypeList ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                String str = "{\"mobileno\":\"" + mob3 + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;

            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
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
            GetUsersTypeList();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }
}