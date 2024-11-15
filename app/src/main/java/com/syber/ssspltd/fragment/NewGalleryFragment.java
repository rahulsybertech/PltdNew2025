package com.syber.ssspltd.fragment;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_ALL_EVENT_IMAGE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.NewGalleryAdap.NewGalleryAdapter;
import com.syber.ssspltd.response.NewGalleryResponse.Event;
import com.syber.ssspltd.response.NewGalleryResponse.NewGalleryPojo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NewGalleryFragment extends Fragment {
    RecyclerView imagelistRecy;
    NewGalleryAdapter galleryAdapter;
    public static ArrayList<Event> imageList;
    Type listType;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_gallery, container, false);
        progressBar=view.findViewById(R.id.progress);
        imagelistRecy=view.findViewById(R.id.imagelistRecy);
        imageList=new ArrayList<>();
        listType=new TypeToken<NewGalleryPojo>(){}.getType();

        galleryAdapter = new NewGalleryAdapter(getContext(),imageList);
        imagelistRecy.setAdapter(galleryAdapter);


        if (Lazy.haveNetworkConnection(getContext())){
            GetImageList();
        }else {
            networkConnetion3(getContext());
        }
        return view;
    }


    private void GetImageList() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_ALL_EVENT_IMAGE,
                new Response.Listener<String>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(String response) {
                        Log.e("Data",GET_ALL_EVENT_IMAGE + " ===== " + response);
                        progressBar.setVisibility(View.GONE);
                        //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show()
                        NewGalleryPojo pojo = new Gson().fromJson(response,listType);
                        if (pojo.getResponseStatus()){
                            imageList.clear();
                            imageList.addAll(pojo.getEvents());
                            galleryAdapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(getContext(), pojo.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, error -> {
            networkConnetion3(getContext());

        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE,"");
                // String otpp = otp.getText().toString();
                String str = "{}";
                Log.e("str", str);
                return str.getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
                return headers;
            }
            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
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
                GetImageList();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}