package com.syber.ssspltd.fragment;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_IMAGE_LIST_DETAILS_APP;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.GalleryAdapter;
import com.syber.ssspltd.response.GalleryResponse.GalleryPojo;
import com.syber.ssspltd.response.GalleryResponse.ImageListAppResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GrelleyFragment extends Fragment {
    RecyclerView imagelistRecy;
    GalleryAdapter galleryAdapter;
    public static List<ImageListAppResult> imageList;
    Type listType;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_grelley, container, false);
        imagelistRecy=view.findViewById(R.id.imagelistRecy);
        imageList=new ArrayList<>();
        listType=new TypeToken<GalleryPojo>(){}.getType();

        galleryAdapter = new GalleryAdapter(getContext(),imageList);
        imagelistRecy.setAdapter(galleryAdapter);
        GetImageList();
        return  view;
    }

    private void GetImageList() {
//        final ProgressDialog progressBar = new ProgressDialog(getContext());
//        progressBar.setTitle("Fetching Data");
//        progressBar.show(); // "http://app.ssspltd.com/apipltd/GetImageListDetailsApp" old url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_IMAGE_LIST_DETAILS_APP,
                new Response.Listener<String>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(String response) {
                        Log.e("Data", GET_IMAGE_LIST_DETAILS_APP + " --> " + response);
                       // progressBar.dismiss();
                        //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show()
                        GalleryPojo pojo = new Gson().fromJson(response,listType);
                        if (pojo.getResponseStatus()){
                            imageList.clear();
                            imageList.addAll(pojo.getImageListAppResult());
                            galleryAdapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(getContext(), pojo.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, error -> {
                  //  progressBar.cancel();
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE,"");
                // String otpp = otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME,"") + "\"}";
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
}