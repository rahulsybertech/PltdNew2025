package com.syber.ssspltd.activitys.supplierorderform;

import static com.syber.ssspltd.Constants.NewErpUrls.GetDispatchTypeList;
import static com.syber.ssspltd.Constants.NewErpUrls.ITEM_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.MARKETER_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.NICK_NAME;
import static com.syber.ssspltd.Constants.NewErpUrls.ORDER_NO;
import static com.syber.ssspltd.Constants.NewErpUrls.PCS_TYPE;
import static com.syber.ssspltd.Constants.NewErpUrls.PartyDetailsByPartyCode;
import static com.syber.ssspltd.Constants.NewErpUrls.SALE_PARTY;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_ORDER;
import static com.syber.ssspltd.Constants.NewErpUrls.SCHEME_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.STATION_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.SUB_PARTY;
import static com.syber.ssspltd.Constants.NewErpUrls.TRANSPORT;
import static com.syber.ssspltd.Constants.NewErpUrls.TRANSPORT_LIST;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.syber.ssspltd.Interface.OnClick;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.CurrentDateTime;
import com.syber.ssspltd.Utils.MyProgress;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.Util;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.activitys.MainActivity;
import com.syber.ssspltd.adapter.DispatchAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.ItemAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.MarketerAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.SalePartyAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.SchmeAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.StationAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.StatusAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.SubPartyAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.TransportAdapter;
import com.syber.ssspltd.databinding.ActivitySupplierOrderFormBinding;
import com.syber.ssspltd.model.partyDetails.TransportResponse;
import com.syber.ssspltd.response.DispatchResponse;
import com.syber.ssspltd.response.ItemModel;
import com.syber.ssspltd.response.MarketerModel;
import com.syber.ssspltd.response.SalepartyModel;
import com.syber.ssspltd.response.SchemeModel;
import com.syber.ssspltd.response.StationModel;
import com.syber.ssspltd.response.SubpartyModel;
import com.syber.ssspltd.response.TransportModel;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
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
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SupplierOrderFormActivity extends AppCompatActivity implements OnClick, DatePickerDialog.OnDateSetListener {
    private static final int REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 201;
    private static final int IMAGE_CAPTURE_CODE = 301;
    public static ArrayList<MarketerModel> mData = new ArrayList<>();
    public static ArrayList<SalepartyModel> sData = new ArrayList<>();
    public static ArrayList<SubpartyModel> sbData = new ArrayList<>();
    public static ArrayList<SchemeModel> schemeData = new ArrayList<>();
    public static ArrayList<TransportModel> trData = new ArrayList<>();
    public static ArrayList<StationModel> stData = new ArrayList<>();
    public static ArrayList<ItemModel> itData = new ArrayList<>();
    static boolean imgFlag;
    //why static
    static Uri imgUri;
    static Bitmap bitmap;
    private final Context mContext = this;
    Uri photoURI;
    ActivitySupplierOrderFormBinding binding;
    String img_string, img_string2, img_string3, img_string4, img_string5;
    String dateFlag = "";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    ArrayList<MarketerModel> marketerModelList, marketerData;
    MarketerAdapter marketerAdapter;
    MarketerModel marketerModel;
    ArrayList<SalepartyModel> salepartyModelList, saleData;
    SalePartyAdapter salePartyAdapter;
    SalepartyModel salepartyModel;
    SubpartyModel subpartyModel;
    ArrayList<SubpartyModel> subpartyModelList, subdata;
    ArrayList<DispatchResponse.DispatchType> dispatchTypeList,filterDispatchList;
    SubPartyAdapter subPartyAdapter;
    DispatchAdapter dispatchAdapter;
    SchemeModel schemeModel;
    ArrayList<SchemeModel> schemeModelList, schData;
    SchmeAdapter schmeAdapter;
    TransportModel transportModel;
    ArrayList<TransportModel> transportModelList, tdata,transportWithMainList,transportWithSubParty;
    TransportAdapter transportAdapter;
    StationModel stationModel;
    ArrayList<StationModel> stationModelList, sdata,stationtWithMainList,stationtWithSubParty;
    StationAdapter stationAdapter;

    ItemModel itemModel;
    ArrayList<ItemModel> itemModelList, idata;
    ItemAdapter itemAdapter;
    List<String> typeList;
    ArrayAdapter<String> typeAdapter;
    Typeface tfavv;
    String selectedSuperStar = "*";
    String selected2Star = "A";
    String selectedAccountId, selectedSubPartyId;
    RecyclerView recyclerView;
    EditText search;
    TextView titile;
    int imageRequestCode = 0;
    int cameraRequestCode = 0;
    private Boolean isPlacedOrderBtnEnabled = true;
    private Dialog sDialog;
    private ActivityResultLauncher<Intent> pickImageLauncher;
    private ActivityResultLauncher<Intent> pickCameraImageLauncher;
    private String subPartyId = "";
    private String traceIdentifier = "";
    private String transportResponseMessage = "";
    private String schemeResponseMessage = "";
    private String dispatchTypeID = "";
    private String tranportID = "";
    private String transportNameMainBrach = "";
    private String bStationNameMainBranch = "";
    private String stationtID = "";


    private TransportResponse responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupplierOrderFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        System.out.println("GETTING_TOKEN " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
        Constants.SettingHeader();
        salepartyModelList = new ArrayList<>();
        saleData = new ArrayList<>();
        subpartyModelList = new ArrayList<>();
        dispatchTypeList = new ArrayList<>();
        filterDispatchList = new ArrayList<>();
        subdata = new ArrayList<>();
        schemeModelList = new ArrayList<>();
        schData = new ArrayList<>();
        itemModelList = new ArrayList<>();
        idata = new ArrayList<>();
        typeList = new ArrayList<>();
        stationModelList = new ArrayList<>();
        stationtWithMainList = new ArrayList<>();
        stationtWithSubParty = new ArrayList<>();

        sdata = new ArrayList<>();
        marketerModelList = new ArrayList<>();
        marketerData = new ArrayList<>();
        transportModelList = new ArrayList<>();
        transportWithMainList = new ArrayList<>();
        transportWithSubParty = new ArrayList<>();

        tdata = new ArrayList<>();

        geNickName();
        getMarketer(SharedPref.read(SharedPref.PARTY_CODE, ""));
        getDispatchTypeList();
//        getSaleParty(SALE_PARTY);
//        getTransport();
//        getTransportDetails(SharedPref.read(SharedPref.PARTY_CODE,""), accountId, "SELF");
//        getScheme(accountId);
        getScheme();
        getPcsType(SharedPref.read(SharedPref.PARTY_CODE, ""), selectedSuperStar);
        //getStation();


        binding.placeholder1.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
        binding.placeholder2.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
        binding.placeholder3.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
        binding.placeholder4.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
        binding.placeholder5.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);

//        dattAhead(CurrentDateTime.getCurrentDateString());
//        binding.date.setText(CurrentDateTime.getCurrentDateStringDDMMYYYY());
//        dattAhead(CurrentDateTime.getCurrentDateStringDDMMYYYY());
//        binding.dateTo.setText(CurrentDateTime.getCurrentDateStringDDMMYYYY());









        handleEditInit();
        initPcsAdapter();
        handleRadioSelect();
        handleClickListner();
        handleDate();


        // Initialize the launcher
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            //getting status code for all images - 1
            System.out.println("GETTING_REQUEST_CODE = " + result.getResultCode() + ", "
                    + "DATA = " + result.getData().getData() + ", " + imageRequestCode);
            // here code is used for only image 1
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                imgUri = result.getData().getData();
                // Use the selected image URI
                System.out.println("MY_NEW_IMAGE_URI " + imgUri);

                if (imageRequestCode == 101) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        System.out.println("MY_YOUNG_BITMAP : " + bitmap);
                        binding.image1.setImageBitmap(bitmap);

                        img_string = getStringImage(bitmap);
                        System.out.println("getting_my_test_image " + img_string);
                        // binding.removeFront.setVisibility(View.VISIBLE);
                        byte[] imageInByte = stream.toByteArray();
                        imgFlag = true;
                        binding.image1.setVisibility(View.VISIBLE);
                        binding.removeImage1.setVisibility(View.VISIBLE);
                        binding.progress1.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("bit", e.toString());
                        imgFlag = false;
                        binding.image1.setVisibility(View.GONE);
                        binding.removeImage1.setVisibility(View.GONE);
                        binding.progress1.setVisibility(View.GONE);
                        binding.placeholder1.setVisibility(View.VISIBLE);
                    }
                } else if (imageRequestCode == 102) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        binding.image2.setImageBitmap(bitmap);
                        img_string2 = getStringImage(bitmap);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        Log.e("img2", lengthbmp + "");
                        Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));
                        //  Log.e("img_string2", img_string2 + "");
                        //Toast.makeText(mContext, "Img2", Toast.LENGTH_SHORT).show();
                        imgFlag = true;
                        binding.image2.setVisibility(View.VISIBLE);
                        binding.removeImage2.setVisibility(View.VISIBLE);
                        binding.placeholder2.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("bit", e.toString());
                        imgFlag = false;
                        binding.image2.setVisibility(View.GONE);
                        binding.removeImage2.setVisibility(View.GONE);
                        binding.progress2.setVisibility(View.GONE);
                        binding.placeholder2.setVisibility(View.VISIBLE);
                    }
                } else if (imageRequestCode == 103) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        binding.image3.setImageBitmap(bitmap);
                        img_string3 = getStringImage(bitmap);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        Log.e("img2", lengthbmp + "");
                        Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));

                        imgFlag = true;
                        binding.image3.setVisibility(View.VISIBLE);
                        binding.removeImage3.setVisibility(View.VISIBLE);
                        binding.progress3.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Log.e("bit", e.toString());
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        imgFlag = false;
                        binding.image3.setVisibility(View.GONE);
                        binding.removeImage3.setVisibility(View.GONE);
                        binding.progress3.setVisibility(View.GONE);
                        binding.placeholder3.setVisibility(View.VISIBLE);
                    }
                } else if (imageRequestCode == 104) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        binding.image4.setImageBitmap(bitmap);
                        img_string4 = getStringImage(bitmap);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        Log.e("img2", lengthbmp + "");
                        Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));
                        imgFlag = true;
                        binding.image4.setVisibility(View.VISIBLE);
                        binding.removeImage4.setVisibility(View.VISIBLE);
                        binding.progress4.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Log.e("bit", e.toString());
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        imgFlag = false;
                        binding.image4.setVisibility(View.GONE);
                        binding.removeImage4.setVisibility(View.GONE);
                        binding.progress4.setVisibility(View.GONE);
                        binding.placeholder4.setVisibility(View.VISIBLE);
                    }
                } else if (imageRequestCode == 105) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        binding.image5.setImageBitmap(bitmap);
                        img_string5 = getStringImage(bitmap);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        Log.e("img2", lengthbmp + "");
                        Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));
                        imgFlag = true;
                        binding.image5.setVisibility(View.VISIBLE);
                        binding.removeImage5.setVisibility(View.VISIBLE);
                        binding.progress5.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("bit", e.toString());
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        imgFlag = false;
                        binding.image5.setVisibility(View.GONE);
                        binding.removeImage5.setVisibility(View.GONE);
                        binding.progress5.setVisibility(View.GONE);
                        binding.placeholder5.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        pickCameraImageLauncher = registerForActivityResult(new ActivityResultContracts.
                StartActivityForResult(), result -> {
            //getting status code for all images - 1
            // URI is correct, 101
            System.out.println("GETTING_REQUEST_CODE_Camera = " + photoURI + ", "
                    + cameraRequestCode);
            // here code is used for only image 1
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                // Use the selected image URI
                System.out.println("MY_NEW_IMAGE_URI " + photoURI);

                if (cameraRequestCode == 101) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        System.out.println("MY_YOUNG_BITMAP : " + bitmap);
                        binding.image1.setImageBitmap(bitmap);

                        img_string = getStringImage(bitmap);
                        System.out.println("getting_my_test_image " + img_string);
                        // binding.removeFront.setVisibility(View.VISIBLE);
                        byte[] imageInByte = stream.toByteArray();
                        imgFlag = true;
                        binding.image1.setVisibility(View.VISIBLE);
                        binding.removeImage1.setVisibility(View.VISIBLE);
                        binding.progress1.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("bit", e.toString());
                        imgFlag = false;
                        binding.image1.setVisibility(View.GONE);
                        binding.removeImage1.setVisibility(View.GONE);
                        binding.progress1.setVisibility(View.GONE);
                        binding.placeholder1.setVisibility(View.VISIBLE);
                    }
                } else if (cameraRequestCode == 102) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        binding.image2.setImageBitmap(bitmap);
                        img_string2 = getStringImage(bitmap);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        Log.e("img2", lengthbmp + "");
                        Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));
                        //  Log.e("img_string2", img_string2 + "");
                        //Toast.makeText(mContext, "Img2", Toast.LENGTH_SHORT).show();
                        imgFlag = true;
                        binding.image2.setVisibility(View.VISIBLE);
                        binding.removeImage2.setVisibility(View.VISIBLE);
                        binding.placeholder2.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("bit", e.toString());
                        imgFlag = false;
                        binding.image2.setVisibility(View.GONE);
                        binding.removeImage2.setVisibility(View.GONE);
                        binding.progress2.setVisibility(View.GONE);
                        binding.placeholder2.setVisibility(View.VISIBLE);
                    }
                } else if (cameraRequestCode == 103) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        binding.image3.setImageBitmap(bitmap);
                        img_string3 = getStringImage(bitmap);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        Log.e("img2", lengthbmp + "");
                        Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));

                        imgFlag = true;
                        binding.image3.setVisibility(View.VISIBLE);
                        binding.removeImage3.setVisibility(View.VISIBLE);
                        binding.progress3.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Log.e("bit", e.toString());
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        imgFlag = false;
                        binding.image3.setVisibility(View.GONE);
                        binding.removeImage3.setVisibility(View.GONE);
                        binding.progress3.setVisibility(View.GONE);
                        binding.placeholder3.setVisibility(View.VISIBLE);
                    }
                } else if (cameraRequestCode == 104) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        binding.image4.setImageBitmap(bitmap);
                        img_string4 = getStringImage(bitmap);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        Log.e("img2", lengthbmp + "");
                        Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));
                        imgFlag = true;
                        binding.image4.setVisibility(View.VISIBLE);
                        binding.removeImage4.setVisibility(View.VISIBLE);
                        binding.progress4.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Log.e("bit", e.toString());
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        imgFlag = false;
                        binding.image4.setVisibility(View.GONE);
                        binding.removeImage4.setVisibility(View.GONE);
                        binding.progress4.setVisibility(View.GONE);
                        binding.placeholder4.setVisibility(View.VISIBLE);
                    }
                } else if (cameraRequestCode == 105) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        binding.image5.setImageBitmap(bitmap);
                        img_string5 = getStringImage(bitmap);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        Log.e("img2", lengthbmp + "");
                        Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));
                        imgFlag = true;
                        binding.image5.setVisibility(View.VISIBLE);
                        binding.removeImage5.setVisibility(View.VISIBLE);
                        binding.progress5.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("bit", e.toString());
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        imgFlag = false;
                        binding.image5.setVisibility(View.GONE);
                        binding.removeImage5.setVisibility(View.GONE);
                        binding.progress5.setVisibility(View.GONE);
                        binding.placeholder5.setVisibility(View.VISIBLE);
                    }
                }
            }
        });



        remark();
    }
    private void remark(){
        // Handle selection
       binding.rgSubparty.setOnCheckedChangeListener((group, checkedId) -> {
           if (checkedId == binding.radioSubparty.getId()) {
               binding.subPartyRemark.getText().clear();
               binding.subPartyRemark.setError(null);
               binding.llRadioRubpartyRemark.setVisibility(View.GONE);
               binding.llSubparty.setVisibility(View.VISIBLE);
               binding.llTransportAndStation.setVisibility(View.VISIBLE);
               binding.subParty.setText("");
               binding.transport.setText("");
               binding.clearTransport.setVisibility(View.GONE);
               binding.clearSubparty.setVisibility(View.GONE);
               binding.clearStation.setVisibility(View.GONE);

               if(transportWithSubParty.isEmpty()){

            //       Toast.makeText(this, "Sub party is empty ", Toast.LENGTH_SHORT).show();


               }else {
                   transportModelList.clear();
                   transportModelList.addAll(transportWithSubParty);
                   transportAdapter = new TransportAdapter(this, transportModelList);
                   recyclerView.setLayoutManager(new LinearLayoutManager(this));
                   recyclerView.setAdapter(transportAdapter);
                   transportAdapter.notifyDataSetChanged();
                   binding.transport.setError(null, null);

                   binding.bStation.setText("");
                   stationModelList.clear();
                   stationModelList.addAll(stationtWithSubParty);
                   stationAdapter = new StationAdapter(this, stationModelList);
                   recyclerView.setLayoutManager(new LinearLayoutManager(this));
                   recyclerView.setAdapter(stationAdapter);
                   stationAdapter.notifyDataSetChanged();
                   binding.bStation.setError(null,null);
               }





           } else {

               binding.subPartyRemark.setError(null);
               binding.llRadioRubpartyRemark.setVisibility(View.VISIBLE);
               binding.llSubparty.setVisibility(View.GONE);
               binding.llTransportAndStation.setVisibility(View.VISIBLE);

               if(!transportWithMainList.isEmpty()){
                   binding.transport.setText("");
                   binding.bStation.setText("");
                   transportModelList.clear();
                   binding.clearTransport.setVisibility(View.GONE);
                   binding.clearSubparty.setVisibility(View.GONE);
                   binding.clearStation.setVisibility(View.GONE);
                   transportModelList.addAll(transportWithMainList);
                   transportAdapter = new TransportAdapter(this, transportModelList);
                   recyclerView.setLayoutManager(new LinearLayoutManager(this));
                   recyclerView.setAdapter(transportAdapter);
                   transportAdapter.notifyDataSetChanged();
                   binding.transport.setError(null, null);


                   stationModelList.clear();
                   stationModelList.addAll(stationtWithMainList);
                   stationAdapter = new StationAdapter(this, stationModelList);
                   recyclerView.setLayoutManager(new LinearLayoutManager(this));
                   recyclerView.setAdapter(stationAdapter);
                   stationAdapter.notifyDataSetChanged();
                   binding.bStation.setError(null, null);
               }


           }
       });
    }

   /* public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }*/

    private void showCustomDialogConfirm() {
        final Dialog  sDialog = new Dialog(mContext);
        sDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sDialog.setCancelable(false);
        sDialog.setContentView(R.layout.dialog_add_to_order);
        sDialog.setCancelable(true);
        TextView text = (TextView) sDialog.findViewById(R.id.dialog_title);
        TextView dialogTitle = sDialog.findViewById(R.id.dialog_title);
        TextView dialogContent = sDialog.findViewById(R.id.dialog_content);
        TextView confirmButton = sDialog.findViewById(R.id.confirm_button);
        RelativeLayout rlButton = sDialog.findViewById(R.id.rlButton);



      /*  dialogTitle.setText("Order Saved Successfully");
        dialogContent.setText("Please check WhatsApp for your PDF");
*/

        rlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
                startActivity(new Intent(mContext, SupplierOrderFormActivity.class));
                finish();

            }
        });
        sDialog.show();


    }
    private void showCustomDialogHold() {
        final Dialog  sDialog = new Dialog(mContext);
        sDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sDialog.setCancelable(false);
        Window window = sDialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        sDialog.setContentView(R.layout.dialog_hold);
        sDialog.setCancelable(true);
        TextView text = (TextView) sDialog.findViewById(R.id.dialog_title);
        TextView dialogTitle = sDialog.findViewById(R.id.dialog_title);
        TextView dialogContent = sDialog.findViewById(R.id.dialog_content);
        dialogContent.setText("Please contact with "+binding.marketer.getText().toString());
        TextView confirmButton = sDialog.findViewById(R.id.confirm_button);
        RelativeLayout rlButton = sDialog.findViewById(R.id.rlButton);

      /*  dialogTitle.setText("Order Saved Successfully");
        dialogContent.setText("Please check WhatsApp for your PDF");
*/

        rlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
                startActivity(new Intent(mContext, SupplierOrderFormActivity.class));
                finish();
            }
        });
        sDialog.show();


    }

    public void handleClear(View view) {
        if (view.getId() == R.id.clear_marketer) {
            binding.marketer.setText("");
            binding.orderNo.setText("");
            binding.clearMarketer.setVisibility(View.GONE);
        } else if (view.getId() == R.id.clear_saleparty) {
            binding.saleParty.setText("");
//            binding.acountName.setText("");
            binding.salePartyMobile.setText("");
            binding.salePartyEmail.setText("");
            binding.subParty.setText("");
            binding.bStation.setText("");
            binding.scheme.setText("");
            binding.transport.setText("");
            subpartyModelList.clear();
            transportModelList.clear();
            stationModelList.clear();
            binding.clearSaleparty.setVisibility(View.GONE);
            binding.clearSubparty.setVisibility(View.GONE);
            binding.clearStation.setVisibility(View.GONE);
            binding.clearScheme.setVisibility(View.GONE);
            binding.clearTransport.setVisibility(View.GONE);

        } else if (view.getId() == R.id.clear_subparty) {
            binding.subParty.setText("");
            binding.bStation.setText("");
            binding.transport.setText("");
            binding.clearSubparty.setVisibility(View.GONE);
            binding.bStation.setText("");
            binding.clearStation.setVisibility(View.GONE);

            binding.clearTransport.setVisibility(View.GONE);
        } else if (view.getId() == R.id.clear_transport) {
            binding.transport.setText("");
            binding.clearTransport.setVisibility(View.GONE);
            binding.clearDispatchType.setVisibility(View.GONE);
            binding.bStation.setText("");
//            binding.scheme.setText("");
            binding.clearStation.setVisibility(View.GONE);

        } else if (view.getId() == R.id.clear_dispatchType) {
            binding.dispatchType.setText("");
            binding.clearDispatchType.setVisibility(View.GONE);

        } else if (view.getId() == R.id.clear_station) {
            binding.bStation.setText("");
//            binding.scheme.setText("");
            binding.clearStation.setVisibility(View.GONE);
//            binding.clearScheme.setVisibility(View.GONE);
        } else if (view.getId() == R.id.clear_scheme) {
            binding.scheme.setText("");
            binding.clearScheme.setVisibility(View.GONE);
        }
    }

    private void BottomSheet(int ReqCode) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.image_picker_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(dialogView);
        ImageView fromCamera = dialogView.findViewById(R.id.from_camera);
        ImageView fromGallery = dialogView.findViewById(R.id.from_gallery);

        fromCamera.setOnClickListener(v -> {
            dialog.cancel();
            if (ReqCode == 101) {
                binding.progress1.setVisibility(View.VISIBLE);
                binding.placeholder1.setVisibility(View.GONE);
            }
            if (ReqCode == 102) {
                binding.progress2.setVisibility(View.VISIBLE);
                binding.placeholder2.setVisibility(View.GONE);
            }
            if (ReqCode == 103) {
                binding.progress3.setVisibility(View.VISIBLE);
                binding.placeholder3.setVisibility(View.GONE);
            }

            if (ReqCode == 104) {
                binding.progress4.setVisibility(View.VISIBLE);
                binding.placeholder4.setVisibility(View.GONE);
            }

            if (ReqCode == 105) {
                binding.progress5.setVisibility(View.VISIBLE);
                binding.placeholder5.setVisibility(View.GONE);
            }
            pickCameraImage(ReqCode);
        });
        fromGallery.setOnClickListener(v -> {

            dialog.cancel();
            if (ReqCode == 101) {
                binding.progress1.setVisibility(View.VISIBLE);
                binding.placeholder1.setVisibility(View.GONE);
            }
            if (ReqCode == 102) {
                binding.progress2.setVisibility(View.VISIBLE);
                binding.placeholder2.setVisibility(View.GONE);
            }
            if (ReqCode == 103) {
                binding.progress3.setVisibility(View.VISIBLE);
                binding.placeholder3.setVisibility(View.GONE);
            }

            if (ReqCode == 104) {
                binding.progress4.setVisibility(View.VISIBLE);
                binding.placeholder4.setVisibility(View.GONE);
            }

            if (ReqCode == 105) {
                binding.progress5.setVisibility(View.VISIBLE);
                binding.placeholder5.setVisibility(View.GONE);
            }

//            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
            pickGalleryImage(ReqCode);
//            }

        });
        dialog.show();
    }

    public void removeImage(View view) {
        int id = view.getId();
        if (id == R.id.removeImage1) {
            img_string = "";
            imgFlag = false;
            binding.image1.setVisibility(View.GONE);
            //  imageView.setImageBitmap(Lazy.StringToBitMap(FormDataPref.read(FormDataPref.IMAG1, "")));
            binding.placeholder1.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
            binding.placeholder1.setVisibility(View.VISIBLE);
            binding.removeImage1.setVisibility(View.GONE);
            binding.progress1.setVisibility(View.GONE);
        } else if (id == R.id.removeImage2) {
            img_string2 = "";
            imgFlag = false;
            binding.image2.setVisibility(View.GONE);
            //  imageView.setImageBitmap(Lazy.StringToBitMap(FormDataPref.read(FormDataPref.IMAG1, "")));
            binding.placeholder2.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
            binding.placeholder2.setVisibility(View.VISIBLE);
            binding.removeImage2.setVisibility(View.GONE);
            binding.progress2.setVisibility(View.GONE);
        } else if (id == R.id.removeImage3) {
            img_string3 = "";
            imgFlag = false;
            binding.image3.setVisibility(View.GONE);
            //  imageView.setImageBitmap(Lazy.StringToBitMap(FormDataPref.read(FormDataPref.IMAG1, "")));
            binding.placeholder3.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
            binding.placeholder3.setVisibility(View.VISIBLE);
            binding.removeImage3.setVisibility(View.GONE);
            binding.progress3.setVisibility(View.GONE);
        } else if (id == R.id.removeImage4) {
            img_string4 = "";
            imgFlag = false;
            binding.image4.setVisibility(View.GONE);
            //  imageView.setImageBitmap(Lazy.StringToBitMap(FormDataPref.read(FormDataPref.IMAG1, "")));
            binding.placeholder4.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
            binding.placeholder4.setVisibility(View.VISIBLE);
            binding.removeImage4.setVisibility(View.GONE);
            binding.progress4.setVisibility(View.GONE);
        } else if (id == R.id.removeImage5) {
            img_string5 = "";
            imgFlag = false;
            binding.image5.setVisibility(View.GONE);
            //  imageView.setImageBitmap(Lazy.StringToBitMap(FormDataPref.read(FormDataPref.IMAG1, "")));
            binding.placeholder5.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
            binding.placeholder5.setVisibility(View.VISIBLE);
            binding.removeImage5.setVisibility(View.GONE);
            binding.progress5.setVisibility(View.GONE);
        }

    }

    public void placeholderClick(View view) {
        int id = view.getId();
        if (id == R.id.placeholder1) {
            BottomSheet(101);
        } else if (id == R.id.placeholder2) {
            BottomSheet(102);
        } else if (id == R.id.placeholder3) {
            BottomSheet(103);
        } else if (id == R.id.placeholder4) {
            BottomSheet(104);
        } else if (id == R.id.placeholder5) {
            BottomSheet(105);
        }
    }

    private void pickCameraImage(int reqCode) {
        System.out.println("my-request-code " + reqCode);
        cameraRequestCode = reqCode;
        checkAndRequestCameraPermission(reqCode);
//        ImagePicker.Companion.with(this).cameraOnly().crop().compress(150).start(reqCode);
    }
    private void pickGalleryImage(int reqCode) {
        System.out.println("my-request-code " + reqCode);
        imageRequestCode = reqCode;
        checkAndRequestPermissions(reqCode);
        // ImagePicker.Companion.with(this).galleryOnly().compress(150).start(reqCode);
    }

//    private File createImageFile() throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        System.out.println("STORAGE_LOCATION - " + storageDir);
//        return File.createTempFile(imageFileName, ".jpg", storageDir);
//    }

    //changes done by abhinavv to updating the storage permission and customised the code by creating
    //image URI



    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        long lengthbmp = imageBytes.length;
        Log.e("sss", lengthbmp + "");
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private void pickImageFromCamera() {

        System.out.println("reached 2.1");
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            try {
                File photoFile = createImageFile();
                photoURI = FileProvider.getUriForFile(this, "com.syber.ssspltd.fileprovider", photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                pickCameraImageLauncher.launch(cameraIntent);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error creating image file", Toast.LENGTH_SHORT).show();
            }
        }
        System.out.println("STORAGE_LOCATION 2 " + photoURI);


    }

    private void checkAndRequestCameraPermission(int reqCode) {
        System.out.println("reached 1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // For API 33+ (Android 13+)
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_MEDIA_IMAGES,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        CAMERA_REQUEST_CODE);
            } else {
                System.out.println("reached 2.0");
                // Permissions already granted
                pickImageFromCamera();
            }
        } else {
            System.out.println("reached 3");
            // For API 32 and below
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        CAMERA_REQUEST_CODE);
            } else {
                System.out.println("reached 4");
                // Permissions already granted
                ImagePicker.Companion.with(this).cameraOnly().crop().compress(150).start(reqCode);
            }
        }
    }

    private void checkAndRequestPermissions(int reqCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // For API level 33+ (Android 13 and above)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        REQUEST_CODE
                );
            } else {
                pickImageFromGallery();
            }
        } else {
            // For API level below 33
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE
                );
            } else {
                ImagePicker.Companion.with(this).galleryOnly().compress(150).start(reqCode);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                checkAndRequestPermissions(imageRequestCode);
                System.out.println("permission_accepted : " + requestCode);
            } else {
                // Permission denied
                System.out.println("permission_denied");
            }
        } else if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted
//                checkAndRequestCameraPermission(cameraRequestCode);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    pickImageFromCamera();
                } else {
                    ImagePicker.Companion.with(this).cameraOnly().crop().compress(150).start(cameraRequestCode);
                }
                System.out.println("camera_permission_accepted : " + requestCode);
            } else {
                // Permissions denied
                System.out.println("camera_permission_denied");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  checkPermissionOnActivityResult(requestCode, resultCode, data);
        //198368459, -1, 0, 101
        System.out.println("SSS_REQUEST_CODE " + requestCode + " " + resultCode + " "
                + imageRequestCode + ", " + cameraRequestCode);

        if (data != null) {
            if (requestCode == 101) {
                //result ok = -1
                System.out.println("my-result-code " + resultCode + " " + RESULT_OK);
                if (resultCode == RESULT_OK) {
                    imgUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        binding.image1.setImageBitmap(bitmap);
                        img_string = getStringImage(bitmap);
                        System.out.println("");
                        // binding.removeFront.setVisibility(View.VISIBLE);
                        byte[] imageInByte = stream.toByteArray();
                        imgFlag = true;
                        binding.image1.setVisibility(View.VISIBLE);
                        binding.removeImage1.setVisibility(View.VISIBLE);
                        binding.progress1.setVisibility(View.GONE);

                    } catch (Exception e) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("bit", e.toString());
                        imgFlag = false;
                        binding.image1.setVisibility(View.GONE);
                        binding.removeImage1.setVisibility(View.GONE);
                        binding.progress1.setVisibility(View.GONE);
                        binding.placeholder1.setVisibility(View.VISIBLE);
                    }
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    binding.image1.setVisibility(View.GONE);
                    binding.removeImage1.setVisibility(View.GONE);
                    binding.progress1.setVisibility(View.GONE);
                    binding.placeholder1.setVisibility(View.VISIBLE);
                    Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
                } else {
                    binding.image1.setVisibility(View.GONE);
                    binding.removeImage1.setVisibility(View.GONE);
                    binding.progress1.setVisibility(View.GONE);
                    binding.placeholder1.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
            if (requestCode == 102) {
                if (resultCode == RESULT_OK) {
                    imgUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        binding.image2.setImageBitmap(bitmap);
                        img_string2 = getStringImage(bitmap);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        Log.e("img2", lengthbmp + "");
                        Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));
                        //  Log.e("img_string2", img_string2 + "");
                        //Toast.makeText(mContext, "Img2", Toast.LENGTH_SHORT).show();
                        imgFlag = true;
                        binding.image2.setVisibility(View.VISIBLE);
                        binding.removeImage2.setVisibility(View.VISIBLE);
                        binding.placeholder2.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("bit", e.toString());
                        imgFlag = false;
                        binding.image2.setVisibility(View.GONE);
                        binding.removeImage2.setVisibility(View.GONE);
                        binding.progress2.setVisibility(View.GONE);
                        binding.placeholder2.setVisibility(View.VISIBLE);
                    }

                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
                    binding.image2.setVisibility(View.GONE);
                    binding.removeImage2.setVisibility(View.GONE);
                    binding.progress2.setVisibility(View.GONE);
                    binding.placeholder2.setVisibility(View.VISIBLE);
                } else {
                    binding.image2.setVisibility(View.GONE);
                    binding.removeImage2.setVisibility(View.GONE);
                    binding.progress2.setVisibility(View.GONE);
                    binding.placeholder2.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
            if (requestCode == 103) {
                if (resultCode == RESULT_OK) {
                    imgUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        binding.image3.setImageBitmap(bitmap);
                        img_string3 = getStringImage(bitmap);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        Log.e("img2", lengthbmp + "");
                        Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));

                        imgFlag = true;
                        binding.image3.setVisibility(View.VISIBLE);
                        binding.removeImage3.setVisibility(View.VISIBLE);
                        binding.progress3.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Log.e("bit", e.toString());
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        imgFlag = false;
                        binding.image3.setVisibility(View.GONE);
                        binding.removeImage3.setVisibility(View.GONE);
                        binding.progress3.setVisibility(View.GONE);
                        binding.placeholder3.setVisibility(View.VISIBLE);
                    }

                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    imgFlag = false;
                    binding.image3.setVisibility(View.GONE);
                    binding.removeImage3.setVisibility(View.GONE);
                    binding.progress3.setVisibility(View.GONE);
                    binding.placeholder3.setVisibility(View.VISIBLE);
                    Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
                } else {
                    imgFlag = false;
                    binding.image3.setVisibility(View.GONE);
                    binding.removeImage3.setVisibility(View.GONE);
                    binding.progress3.setVisibility(View.GONE);
                    binding.placeholder3.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
            if (requestCode == 104) {
                if (resultCode == RESULT_OK) {
                    imgUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        binding.image4.setImageBitmap(bitmap);
                        img_string4 = getStringImage(bitmap);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        Log.e("img2", lengthbmp + "");
                        Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));
                        imgFlag = true;
                        binding.image4.setVisibility(View.VISIBLE);
                        binding.removeImage4.setVisibility(View.VISIBLE);
                        binding.progress4.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Log.e("bit", e.toString());
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        imgFlag = false;
                        binding.image4.setVisibility(View.GONE);
                        binding.removeImage4.setVisibility(View.GONE);
                        binding.progress4.setVisibility(View.GONE);
                        binding.placeholder4.setVisibility(View.VISIBLE);
                    }

                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    imgFlag = false;
                    binding.image4.setVisibility(View.GONE);
                    binding.removeImage4.setVisibility(View.GONE);
                    binding.progress4.setVisibility(View.GONE);
                    binding.placeholder4.setVisibility(View.VISIBLE);
                    Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
                } else {
                    imgFlag = false;
                    binding.image4.setVisibility(View.GONE);
                    binding.removeImage4.setVisibility(View.GONE);
                    binding.progress4.setVisibility(View.GONE);
                    binding.placeholder4.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
            if (requestCode == 105) {
                if (resultCode == RESULT_OK) {
                    imgUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        binding.image5.setImageBitmap(bitmap);
                        img_string5 = getStringImage(bitmap);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        Log.e("img2", lengthbmp + "");
                        Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));
                        imgFlag = true;
                        binding.image5.setVisibility(View.VISIBLE);
                        binding.removeImage5.setVisibility(View.VISIBLE);
                        binding.progress5.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("bit", e.toString());
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        imgFlag = false;
                        binding.image5.setVisibility(View.GONE);
                        binding.removeImage5.setVisibility(View.GONE);
                        binding.progress5.setVisibility(View.GONE);
                        binding.placeholder5.setVisibility(View.VISIBLE);
                    }

                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    imgFlag = false;
                    binding.image5.setVisibility(View.GONE);
                    binding.removeImage5.setVisibility(View.GONE);
                    binding.progress5.setVisibility(View.GONE);
                    binding.placeholder5.setVisibility(View.VISIBLE);
                    Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
                } else {
                    imgFlag = false;
                    binding.image5.setVisibility(View.GONE);
                    binding.removeImage5.setVisibility(View.GONE);
                    binding.progress5.setVisibility(View.GONE);
                    binding.placeholder5.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (cameraRequestCode == 101) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    System.out.println("MY_YOUNG_BITMAP : " + bitmap);
                    binding.image1.setImageBitmap(bitmap);

                    img_string = getStringImage(bitmap);
                    System.out.println("getting_my_test_image " + img_string);
                    // binding.removeFront.setVisibility(View.VISIBLE);
                    byte[] imageInByte = stream.toByteArray();
                    imgFlag = true;
                    binding.image1.setVisibility(View.VISIBLE);
                    binding.removeImage1.setVisibility(View.VISIBLE);
                    binding.progress1.setVisibility(View.GONE);
                } catch (Exception e) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("bit", e.toString());
                    imgFlag = false;
                    binding.image1.setVisibility(View.GONE);
                    binding.removeImage1.setVisibility(View.GONE);
                    binding.progress1.setVisibility(View.GONE);
                    binding.placeholder1.setVisibility(View.VISIBLE);
                }
            } else if (cameraRequestCode == 102) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    binding.image2.setImageBitmap(bitmap);
                    img_string2 = getStringImage(bitmap);
                    byte[] imageInByte = stream.toByteArray();
                    long lengthbmp = imageInByte.length;
                    Log.e("img2", lengthbmp + "");
                    Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));
                    //  Log.e("img_string2", img_string2 + "");
                    //Toast.makeText(mContext, "Img2", Toast.LENGTH_SHORT).show();
                    imgFlag = true;
                    binding.image2.setVisibility(View.VISIBLE);
                    binding.removeImage2.setVisibility(View.VISIBLE);
                    binding.placeholder2.setVisibility(View.GONE);
                } catch (Exception e) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("bit", e.toString());
                    imgFlag = false;
                    binding.image2.setVisibility(View.GONE);
                    binding.removeImage2.setVisibility(View.GONE);
                    binding.progress2.setVisibility(View.GONE);
                    binding.placeholder2.setVisibility(View.VISIBLE);
                }
            } else if (cameraRequestCode == 103) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    binding.image3.setImageBitmap(bitmap);
                    img_string3 = getStringImage(bitmap);
                    byte[] imageInByte = stream.toByteArray();
                    long lengthbmp = imageInByte.length;
                    Log.e("img2", lengthbmp + "");
                    Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));

                    imgFlag = true;
                    binding.image3.setVisibility(View.VISIBLE);
                    binding.removeImage3.setVisibility(View.VISIBLE);
                    binding.progress3.setVisibility(View.GONE);
                } catch (Exception e) {
                    Log.e("bit", e.toString());
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    imgFlag = false;
                    binding.image3.setVisibility(View.GONE);
                    binding.removeImage3.setVisibility(View.GONE);
                    binding.progress3.setVisibility(View.GONE);
                    binding.placeholder3.setVisibility(View.VISIBLE);
                }
            } else if (cameraRequestCode == 104) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    binding.image4.setImageBitmap(bitmap);
                    img_string4 = getStringImage(bitmap);
                    byte[] imageInByte = stream.toByteArray();
                    long lengthbmp = imageInByte.length;
                    Log.e("img2", lengthbmp + "");
                    Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));
                    imgFlag = true;
                    binding.image4.setVisibility(View.VISIBLE);
                    binding.removeImage4.setVisibility(View.VISIBLE);
                    binding.progress4.setVisibility(View.GONE);
                } catch (Exception e) {
                    Log.e("bit", e.toString());
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    imgFlag = false;
                    binding.image4.setVisibility(View.GONE);
                    binding.removeImage4.setVisibility(View.GONE);
                    binding.progress4.setVisibility(View.GONE);
                    binding.placeholder4.setVisibility(View.VISIBLE);
                }
            } else if (cameraRequestCode == 105) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    binding.image5.setImageBitmap(bitmap);
                    img_string5 = getStringImage(bitmap);
                    byte[] imageInByte = stream.toByteArray();
                    long lengthbmp = imageInByte.length;
                    Log.e("img2", lengthbmp + "");
                    Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));
                    imgFlag = true;
                    binding.image5.setVisibility(View.VISIBLE);
                    binding.removeImage5.setVisibility(View.VISIBLE);
                    binding.progress5.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("bit", e.toString());
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    imgFlag = false;
                    binding.image5.setVisibility(View.GONE);
                    binding.removeImage5.setVisibility(View.GONE);
                    binding.progress5.setVisibility(View.GONE);
                    binding.placeholder5.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void searchDialog(final String title) {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
        search = sDialog.findViewById(R.id.search);
        if (salepartyModelList.size() > 0) {
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        }
        sDialog.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
            }
        });

        if (!saleData.isEmpty()) {

            filterBc(sData);
        } else {

            getSaleParty(SALE_PARTY);
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
                            || salepartyModelList.get(p).getAccountId().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        saleData.add(salepartyModelList.get(p));
                    }
                }
                filterBc(saleData);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        salePartyAdapter = new SalePartyAdapter(this, salepartyModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(salePartyAdapter);
        getSaleParty(SALE_PARTY);
        sDialog.show();

    }

    private void searchMarketer(final String title) {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
        search = sDialog.findViewById(R.id.search);
        if (marketerModelList.size() > 0) {
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        }
        sDialog.findViewById(R.id.cancle).setOnClickListener(v -> sDialog.dismiss());
        if (marketerData.size() > 0) {
            filterMarketer(mData);
        } else {

            getMarketer(SharedPref.read(SharedPref.PARTY_CODE, ""));
        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                marketerData.clear();
                for (int p = 0; p < marketerModelList.size(); p++) {
                    if (marketerModelList.get(p).getMarketerName().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || marketerModelList.get(p).getmCode().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        marketerData.add(marketerModelList.get(p));
                    }
                }
                filterMarketer(marketerData);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        marketerAdapter = new MarketerAdapter(this, marketerModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(marketerAdapter);
        getMarketer(SharedPref.read(SharedPref.PARTY_CODE, ""));
        sDialog.show();

    }

    private void statusListDialog(final String title, final ArrayList<String> statusList) {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.search_dialog);

        sDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        sDialog.setCancelable(true);
        TextView titile = sDialog.findViewById(R.id.title);
        titile.setText(title);
        RecyclerView recyclerView = sDialog.findViewById(R.id.dist_recycler);
        EditText search = sDialog.findViewById(R.id.search);
        search.setVisibility(View.GONE);

        if (!statusList.isEmpty()) {
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        }

        sDialog.findViewById(R.id.cancle).setOnClickListener(v -> sDialog.dismiss());

        StatusAdapter statusAdapter = new StatusAdapter(this, statusList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(statusAdapter);

        int maxHeight = 600;
        int itemHeight = 100;
        int totalHeight = Math.min(statusList.size() * itemHeight, maxHeight);

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = totalHeight;
        recyclerView.setLayoutParams(params);

        sDialog.show();
    }

    private void subPartyDialog(final String title) {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
        search = sDialog.findViewById(R.id.search);
        if (subpartyModelList.size() > 0) {
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        }
        sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        sDialog.findViewById(R.id.cancle).setOnClickListener(v -> sDialog.dismiss());
        if (subdata.size() > 0) {
            filterSubParty(sbData);
        }

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                subdata.clear();
                for (int p = 0; p < subpartyModelList.size(); p++) {
                    if (subpartyModelList.get(p).getSubPartyName().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || subpartyModelList.get(p).getSubPartyId().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        subdata.add(subpartyModelList.get(p));
                    }
                }
                filterSubParty(subdata);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        subPartyAdapter = new SubPartyAdapter(this, subpartyModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(subPartyAdapter);

        System.out.println("SUB_PARTY 1 " + selectedAccountId);
      //  getSubParty(selectedAccountId);
        sDialog.show();

    }

    private void transportDialog(final String title) {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        TextView transportNoData = sDialog.findViewById(R.id.transportNoData);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
        search = sDialog.findViewById(R.id.search);
        System.out.println("GET_TRANSPORT_LIST " + transportModelList);
        if (!transportModelList.isEmpty()) {
            transportNoData.setVisibility(View.VISIBLE);
            transportNoData.setVisibility(View.GONE);
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        } else {

            transportNoData.setText(transportResponseMessage);
            transportNoData.setVisibility(View.VISIBLE);
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        }
        sDialog.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
            }
        });
        if (!tdata.isEmpty()) {
            filterTransport(trData);

        }
//        else {
//            getTransport();
//        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tdata.clear();
                for (int p = 0; p < transportModelList.size(); p++) {
                    if (transportModelList.get(p).getTransportName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        tdata.add(transportModelList.get(p));
                    }
                }
                filterTransport(tdata);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        transportAdapter = new TransportAdapter(this, transportModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(transportAdapter);

//        getTransport();

        sDialog.show();

    }

    private void schmeDialog(final String title) {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        TextView transportNoData = sDialog.findViewById(R.id.transportNoData);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
        schmeAdapter = new SchmeAdapter(this, schemeModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(schmeAdapter);

        System.out.println("GET_RESPONSE_MESSAGE 1 " + schemeResponseMessage);
        search = sDialog.findViewById(R.id.search);
        if (!schemeModelList.isEmpty()) {
            transportNoData.setVisibility(View.GONE);
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        } else {
            System.out.println("GET_RESPONSE_MESSAGE 2 " + schemeResponseMessage);
            transportNoData.setText(schemeResponseMessage);
            transportNoData.setVisibility(View.VISIBLE);
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        }
        sDialog.findViewById(R.id.cancle).setOnClickListener(v -> sDialog.dismiss());
        if (!schData.isEmpty()) {
            filterScheme(schemeData);
        }
//        else {
//            getScheme();
//        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                schData.clear();
                for (int p = 0; p < schemeModelList.size(); p++) {
                    if (schemeModelList.get(p).getScheme().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        schData.add(schemeModelList.get(p));
                    }
                }
                filterScheme(schData);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        sDialog.show();
    }

    private void dispatchTypeListDialog(final String title) {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        TextView transportNoData = sDialog.findViewById(R.id.transportNoData);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);

        dispatchAdapter = new DispatchAdapter(this, dispatchTypeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dispatchAdapter);
        dispatchAdapter.notifyDataSetChanged();


   //     System.out.println("GET_RESPONSE_MESSAGE 1 " + schemeResponseMessage);
        search = sDialog.findViewById(R.id.search);
        if (!dispatchTypeList.isEmpty()) {
            transportNoData.setVisibility(View.GONE);
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        } else {
          //  System.out.println("GET_RESPONSE_MESSAGE 2 " + schemeResponseMessage);
            transportNoData.setText(schemeResponseMessage);
            transportNoData.setVisibility(View.VISIBLE);
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        }
        sDialog.findViewById(R.id.cancle).setOnClickListener(v -> sDialog.dismiss());



        if (!filterDispatchList.isEmpty()) {
            filterDispatch(dispatchTypeList);
        }
//        else {
//            getScheme();
//        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterDispatchList.clear();
                for (int p = 0; p < dispatchTypeList.size(); p++) {
                    if (dispatchTypeList.get(p).getValue().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filterDispatchList.add(dispatchTypeList.get(p));
                    }
                }
                filterDispatch(filterDispatchList);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        sDialog.show();
    }

    private void itemDialog(final String title) {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
        search = sDialog.findViewById(R.id.search);
        if (itemModelList.size() > 0) {
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        }
        sDialog.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
            }
        });
        if (idata.size() > 0) {
            filterItem(itData);
        } else {
            getItem();
        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                idata.clear();
                for (int p = 0; p < itemModelList.size(); p++) {
                    if (itemModelList.get(p).getItemName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        idata.add(itemModelList.get(p));
                    }
                }
                filterItem(idata);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        itemAdapter = new ItemAdapter(this, itemModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
        getItem();
        sDialog.show();
    }

    private void stationDialog(final String title) {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
        search = sDialog.findViewById(R.id.search);
        if (stationModelList.size() > 0) {
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        }
        sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);

        sDialog.findViewById(R.id.cancle).setOnClickListener(v -> sDialog.dismiss());
        if (sdata.size() > 0) {
            filterStation(stData);
        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sdata.clear();
                for (int p = 0; p < stationModelList.size(); p++) {
                    if (stationModelList.get(p).getStationName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        sdata.add(stationModelList.get(p));
                    }
                }
                filterStation(sdata);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        stationAdapter = new StationAdapter(this, stationModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(stationAdapter);
        if (selectedAccountId != null && !selectedAccountId.isEmpty())
         //  getStation();
        sDialog.show();
    }

    private void filterMarketer(ArrayList<MarketerModel> bc) {
        marketerAdapter = new MarketerAdapter(this, bc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(marketerAdapter);

    }

    void filterBc(ArrayList<SalepartyModel> bc) {
        salePartyAdapter = new SalePartyAdapter(this, bc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(salePartyAdapter);
    }

    void filterSubParty(ArrayList<SubpartyModel> bc) {
        subPartyAdapter = new SubPartyAdapter(this, bc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(subPartyAdapter);
    }

    void filterScheme(ArrayList<SchemeModel> bc) {
        // MyPref.storePrefs(context).setTotalMechanics(bc.size() + "");
        schmeAdapter = new SchmeAdapter(this, bc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(schmeAdapter);
    }
    void filterDispatch(ArrayList<DispatchResponse.DispatchType> bc) {
        // MyPref.storePrefs(context).setTotalMechanics(bc.size() + "");
        dispatchAdapter = new DispatchAdapter(this, bc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dispatchAdapter);
    }

    void filterTransport(ArrayList<TransportModel> bc) {
        // MyPref.storePrefs(context).setTotalMechanics(bc.size() + "");
        transportAdapter = new TransportAdapter(this, bc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(transportAdapter);
    }

    void filterItem(ArrayList<ItemModel> bc) {
        // MyPref.storePrefs(context).setTotalMechanics(bc.size() + "");
        itemAdapter = new ItemAdapter(this, bc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
    }

    void filterStation(ArrayList<StationModel> bc) {
        stationAdapter = new StationAdapter(this, bc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(stationAdapter);
    }

    private void geNickName() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, NICK_NAME, response -> {
            Log.i("TaG", "Response " + NICK_NAME + "---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
//                JSONObject js = jsonObject.getJSONObject("data");
                if (jsonObject.getString("ResponseStatus").equals("true")) {
                    binding.nickName.setText(jsonObject.getString("Nickname"));
                    if (jsonObject.getBoolean("AllowedAllType")) {
                        binding.stra3.setEnabled(true);
                        binding.stra2.setEnabled(true);
                        binding.redioStarLl.setVisibility(View.VISIBLE);
                    } else {
                        binding.stra3.setEnabled(false);
                        binding.stra2.setEnabled(false);
                        binding.redioStarLl.setVisibility(View.GONE);
                    }
                } else {
                    AlertUtil.responseElse(mContext, "Nick Name ", jsonObject.getString("ResponseMessage"));

                }
            } catch (Exception e) {
                AlertUtil.responseExecption(mContext, "Nick Name", e.toString());
            }
        }, error -> {
            try {
                Constants.convertByteToString(mContext, "Nick Name ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"SupplierAccountID\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\"}";
                Log.i("TaG", "Request " + NICK_NAME + "---> " + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        stringRequest.setShouldCache(true);
        stringRequest.shouldCache();
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void getPartyDetailsByPartyCode(String partyCode ) {
        String supplierCode = SharedPref.read(SharedPref.PARTY_CODE, "");
     String   urlWithPartyCode = PartyDetailsByPartyCode+ "?partyCode=" + partyCode+ "&supplierCode=" + supplierCode;
   //  String   urlWithPartyCode = PartyDetailsByPartyCode+ "?partyCode=" + partyCode;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlWithPartyCode, response -> {
            Log.i("TaG", "Response " + urlWithPartyCode + "---> " + response);
            try {

                JSONObject jsonObject = new JSONObject(response);
                Gson gson = new Gson();
                 responseData = gson.fromJson(response, TransportResponse.class);


                String mobile = responseData.getMobileNo();
                if (mobile == null || mobile.isEmpty()) {
                    binding.salePartyMobile.setText("**********");
                } else {
                    binding.salePartyMobile.setText("*".repeat(mobile.length()));
                }

                // Handle Email ID
                String email = responseData.getEmailId();
                if (email == null || email.isEmpty()) {
                    binding.salePartyEmail.setText("**********");
                } else {
                    binding.salePartyEmail.setText("*".repeat(email.length()));
                }
                try {


                   /* if (responseData.getSubPartyList().get(p).getTransportList() != null && !responseData.getSubPartyList().get(p).getTransportList().isEmpty()) {

                    }*/

                    if (responseData.getSubPartyList()!= null && !responseData.getSubPartyList().isEmpty()) {
                        subPartyAdapter = new SubPartyAdapter(this, subpartyModelList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        recyclerView.setAdapter(subPartyAdapter);

                        //SubParty
                        subpartyModelList.clear();
                        subpartyModelList.addAll(responseData.getSubPartyList());
                        subPartyAdapter.notifyDataSetChanged();
                  //      binding.subParty.setText(responseData.getSubPartyList().get(0).getSubPartyName());
                        if (responseData.getSubPartyList().get(0).getSubPartyName().equalsIgnoreCase("self")) {
                            binding.subParty.setText("SELF");
                            subPartyId = "SELF";
                        } else {
                            binding.subParty.setText(responseData.getSubPartyList().get(0).getSubPartyName());
                            subPartyId = responseData.getSubPartyList().get(0).getAccountCode();
                        }


                        if (responseData.getSubPartyList().get(0).getTransportList() != null && !responseData.getSubPartyList().get(0).getTransportList().isEmpty()) {

                            transportNameMainBrach=     responseData.getSubPartyList().get(0).getTransportList().get(0).getTransportName();
                            tranportID=responseData.getSubPartyList().get(0).getTransportList().get(0).getTransportId();
                            binding.transport.setText(responseData.getSubPartyList().get(0).getTransportList().get(0).getTransportName());
                            transportAdapter = new TransportAdapter(this, transportModelList);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
                            recyclerView.setAdapter(transportAdapter);
                            transportModelList.clear();
                            transportModelList.addAll(responseData.getSubPartyList().get(0).getTransportList());
                            transportWithMainList.clear();
                            transportWithMainList.addAll(responseData.getSubPartyList().get(0).getTransportList());
                            transportAdapter.notifyDataSetChanged();
                            binding.transport.setError(null, null);


                            if (responseData.getSubPartyList().get(0).getTransportList().get(0).getStationList() != null && !responseData.getSubPartyList().get(0).getTransportList().get(0).getStationList().isEmpty()) {
                                stationAdapter = new StationAdapter(this, stationModelList);
                                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                                recyclerView.setAdapter(stationAdapter);
                                bStationNameMainBranch=responseData.getSubPartyList().get(0).getTransportList().get(0).getStationList().get(0).getStationName();
                                binding.bStation.setText(responseData.getSubPartyList().get(0).getTransportList().get(0).getStationList().get(0).getStationName());
                                stationModelList.clear();
                                stationModelList.addAll(responseData.getSubPartyList().get(0).getTransportList().get(0).getStationList());
                                stationtWithMainList.clear();
                                stationtWithMainList.addAll(responseData.getSubPartyList().get(0).getTransportList().get(0).getStationList());
                                stationAdapter.notifyDataSetChanged();
                                binding.bStation.setError(null, null);
                            }else {
                                stationModelList.clear();
                            }

                        }else {

                            transportModelList.clear();
                            stationModelList.clear();
                        }


                    }else {
                        subpartyModelList.clear();
                        transportModelList.clear();
                        stationModelList.clear();
                    }

                  //  binding.salePartyMobile.setText(jsonObject.getString("MobileNo"));
                    // Original text
                    String originalText = jsonObject.optString("MobileNo");

// Replace it with *'s (you can replace it with as many * as the length of the original text)
                 //   binding.salePartyMobile.setText("*".repeat(originalText.length()));
                  //  binding.salePartyEmail.setText(jsonObject.getString("EmailID"));
                  //  String originalText1 = jsonObject.optString("EmailID");
                 //   binding.salePartyEmail.setText("*".repeat(originalText1.length()));
                /*    handleEditInit();
                    binding.saleParty.setError(null, null);
                    binding.subParty.setError(null, null);
                    binding.transport.setError(null, null);
                    binding.bStation.setError(null, null);
                    binding.scheme.setError(null, null);
*/
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Exce", e.toString());
                }

//                JSONObject js = jsonObject.getJSONObject("data");
                if (jsonObject.getString("ResponseStatus").equals("true")) {

                } else {
                    AlertUtil.responseElse(mContext,  urlWithPartyCode, jsonObject.getString("ResponseMessage"));

                }
            } catch (Exception e) {
                AlertUtil.responseExecption(mContext, urlWithPartyCode, e.toString());
            }
        }, error -> {
            try {
                Constants.convertByteToString(mContext, urlWithPartyCode, error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }) {
         /*   @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"SupplierAccountID\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\"}";
                Log.i("TaG", "Request " + NICK_NAME + "---> " + str);
                return str.getBytes();
            }*/

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        stringRequest.setShouldCache(true);
        stringRequest.shouldCache();
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void getDispatchTypeList( ) {
        String   urlWithPartyCode = GetDispatchTypeList;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlWithPartyCode, response -> {
            Log.i("TaG", "Response " + urlWithPartyCode + "---> " + response);
            try {

                JSONObject jsonObject = new JSONObject(response);
                Gson gson = new Gson();
             DispatchResponse   responseData = gson.fromJson(response, DispatchResponse.class);

                try {


                    if (responseData.getDispatchTypeList()!= null && !responseData.getDispatchTypeList().isEmpty()) {
                        dispatchTypeList.clear();
                        dispatchTypeList.addAll(responseData.getDispatchTypeList());
                     //   binding.dispatchType.setText(responseData.getDispatchTypeList().get(0).getValue());
                      //  dispatchTypeID=responseData.getDispatchTypeList().get(0).getId();
                    }else {
                        dispatchTypeList.clear();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Exce", e.toString());
                }

//                JSONObject js = jsonObject.getJSONObject("data");
                if (jsonObject.getString("ResponseStatus").equals("true")) {

                } else {
                    AlertUtil.responseElse(mContext,  urlWithPartyCode, jsonObject.getString("ResponseMessage"));

                }
            } catch (Exception e) {
                AlertUtil.responseExecption(mContext, urlWithPartyCode, e.toString());
            }
        }, error -> {
            try {
                Constants.convertByteToString(mContext, urlWithPartyCode, error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }) {
         /*   @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"SupplierAccountID\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\"}";
                Log.i("TaG", "Request " + NICK_NAME + "---> " + str);
                return str.getBytes();
            }*/

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        stringRequest.setShouldCache(true);
        stringRequest.shouldCache();
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void getOrderCodeSr(final String marketerName) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ORDER_NO, response -> {

            Log.i("TaG", "Response " + ORDER_NO + "---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
//                JSONObject js = jsonObject.getJSONObject("data");
                if (jsonObject.getString("ResponseStatus").equals("true")) {
                    binding.orderNo.setText(jsonObject.getString("OrderNo"));
                    traceIdentifier =jsonObject.optString("TraceIdentifier");
                    binding.orderNo.setError(null, null);
                } else {
                    AlertUtil.responseElse(mContext, "MaxOrderNoByMarketer ", "api is getting false status. Please try after sometime ");

                }
            } catch (Exception e) {
                AlertUtil.responseExecption(mContext, "MaxOrderNoByMarketer ", e.toString());
            }
        }, error -> {
            try {
                Constants.convertByteToString(mContext, "MaxOrderNoByMarketer ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"MarketerName\":\"" + marketerName + "\"}";

                Log.i("TaG", "Request " + ORDER_NO + "---> " + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        stringRequest.setShouldCache(true);
        stringRequest.shouldCache();
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void getMarketer(final String SupplierAccountID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MARKETER_LIST, response -> {
            Log.i("TaG", "Response " + MARKETER_LIST + "---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray2 = jsonObject.getJSONArray("Marketerlist");
                marketerModelList.clear();

                for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONObject ob2 = jsonArray2.getJSONObject(i);
                    String marketerName = ob2.optString("MarketerName");
                    String mCode = ob2.optString("MCode");
                    marketerModel = new MarketerModel(marketerName, mCode,"1");
                    marketerModelList.add(marketerModel);
                }


                marketerAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Exce", e.toString());
            }

        }, error -> {
            Toast.makeText(mContext, error.getMessage() + "", Toast.LENGTH_LONG).show();
            Log.e("Volly ", error.getMessage() + "");

        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"SupplierAccountID\":\"" + SupplierAccountID + "\"}";

                Log.i("TaG", "Request " + MARKETER_LIST + "---> " + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

    }

    private void getSaleParty(final String url) {
        final MyProgress myProgress = new MyProgress(this);
        myProgress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            Util.getInstance().logLargeString("TaG", "Response " + url + " -=-=-=>" + response);
            myProgress.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("salesPartyNames");
                salepartyModelList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    String name = ob.optString("NickName");
                    String accountId = ob.optString("AccountCode");
                    salepartyModel = new SalepartyModel(name, false, "", accountId);
                    salepartyModelList.add(salepartyModel);

                }
             //   getStation();
                salePartyAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Exce", e.toString());
            }
        }, error -> {
            myProgress.dismiss();
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
//                String str = "{\"MOBILENO\":\"" + SharedPref.read(SharedPref.USERMOBILE, "") + "\",\"Filter\":\"" + "selected" + "\"}";
                String str = "{}";
                Log.i("TaG", "Request " + url + " -=-=-=>" + str);

                return str.getBytes();

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };
        stringRequest.setShouldCache(true);
        stringRequest.shouldCache();
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

    }

    private void getTransportDetails(final String supplierAccountId, final String accountId, final String subpartyId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TRANSPORT, response -> {
            Log.i("TaG", "Response " + TRANSPORT + "---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("ResponseStatus").equals("true")) {
                /*    binding.transport.setText(jsonObject.getString("TransportName"));
                    binding.bStation.setText(jsonObject.getString("Station"));*/
                    binding.salePartyMobile.setText(jsonObject.getString("MobileNo"));
                    // Original text
                    String originalText = jsonObject.optString("MobileNo");

// Replace it with *'s (you can replace it with as many * as the length of the original text)
                    binding.salePartyMobile.setText("*".repeat(originalText.length()));
                    binding.salePartyEmail.setText(jsonObject.getString("EmailID"));
                    String originalText1 = jsonObject.optString("EmailID");
                    binding.salePartyEmail.setText("*".repeat(originalText1.length()));
                    handleEditInit();
                    binding.saleParty.setError(null, null);
                    binding.subParty.setError(null, null);
                  /*  binding.transport.setError(null, null);
                    binding.bStation.setError(null, null);*/
                    binding.scheme.setError(null, null);
//                    binding.subParty.setText("SELF");
                } else {
                    AlertUtil.responseElse(mContext, "TransportStationbyAccountID ", "api is getting false status. Please try after sometime ");

                }
            } catch (Exception e) {
                AlertUtil.responseExecption(mContext, "TransportStationbyAccountID ", e.toString());
            }
        }, error -> {
            try {
                Constants.convertByteToString(mContext, "TransportStationbyAccountID ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("AccountID", accountId);
                    jsonBody.put("SupplierAccountID", supplierAccountId);
                    jsonBody.put("SubPartyID", subpartyId);
                    Log.i("TaG", "Request " + TRANSPORT + "---> " + jsonBody);
                    return jsonBody.toString().getBytes("utf-8");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }



        };
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

    }

    private void getSubParty(final String accountId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SUB_PARTY, response -> {

            Log.i("TaG", "Response " + SUB_PARTY + "---> " + response);

            try {
                subpartyModelList.clear();
                JSONObject jsonObject = new JSONObject(response);
                System.out.println("GETTING_SALES" + response);
                JSONArray jsonArray = jsonObject.getJSONArray("subPartyNames");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    String name = ob.optString("Name");
                    String nick_name = ob.optString("AccountCode");
                    binding.subParty.setText("SELF");
                    subpartyModel = new SubpartyModel(name, nick_name);
                    subpartyModelList.add(subpartyModel);
                }
                subPartyAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exce", e.toString());
            }
        }, error -> {
            error.getMessage();
            System.out.println("Volly_SUB_PARTY " + error.getMessage());
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"AccountID\":\"" + accountId + "\"}";
                Log.i("TaG", "Request_SUB_PARTY " + SUB_PARTY + "---> " + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void getTransport() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TRANSPORT_LIST, response -> {
            Log.i("TaG", "Response " + TRANSPORT_LIST + "---> " + response);


            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("ResponseCode") == 201) {
                    System.out.println("GETTING_RESPONSE " + jsonObject.getString("ResponseMessage"));
                    transportResponseMessage = jsonObject.getString("ResponseMessage");
                    return;
                }
                JSONArray jsonArray = jsonObject.getJSONArray("transportNames");
                transportModelList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob2 = jsonArray.getJSONObject(i);
                    String t_name = ob2.optString("TransportName");
                    transportModel = new TransportModel(t_name);
                    transportModelList.add(transportModel);
                    // Log.e("TransportList",ob.getString("TransportName"));
                }

                transportAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Exce", e.toString());
            }

        }, error -> {
            Toast.makeText(mContext, error.getMessage() + "", Toast.LENGTH_LONG).show();
            Log.e("Volly ", error.getMessage() + "");
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                JSONObject jsonBody = new JSONObject();
                try {

                    jsonBody.put("SupplierAccountID", SharedPref.read(SharedPref.PARTY_CODE, ""));
                    //sale party
                    jsonBody.put("AccountID", selectedAccountId);
                    //sub party name i.e self
                    jsonBody.put("SubPartyID", subPartyId);

                    Log.i("TaG", "Request " + TRANSPORT_LIST + "---> " + jsonBody);
                    return jsonBody.toString().getBytes("utf-8");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                Log.e("str", "transport header =-=-=" + headers + "\n");
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

    }

    private void getStation() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STATION_LIST, response -> {
            Log.i("TaG", "Response " + STATION_LIST + "---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray2 = jsonObject.getJSONArray("stationName");
                stationModelList.clear();
                for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONObject ob2 = jsonArray2.getJSONObject(i);
                    String s_name = ob2.optString("StationName");
                    //Log.e("s_name", s_name);
                    stationModel = new StationModel(s_name);
                    stationModelList.add(stationModel);
                }
                stationAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Exce", e.toString());
            }

        }, error -> {
            Toast.makeText(mContext, error.getMessage() + "", Toast.LENGTH_LONG).show();
            Log.e("Volly ", error.getMessage() + "");
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonBody = new JSONObject();
                try {

//                    jsonBody.put("SupplierAccountID", SharedPref.read(SharedPref.PARTY_CODE, ""));
                    jsonBody.put("SupplierAccountID", selectedAccountId);

                    Log.i("TaG", "Request " + STATION_LIST + "---> " + jsonBody);
                    return jsonBody.toString().getBytes("utf-8");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

    }


    private void getScheme() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SCHEME_LIST, response -> {
            Log.i("TaG", "Response " + SCHEME_LIST + "---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("ResponseCode") == 201) {
                    schemeResponseMessage = jsonObject.getString("ResponseMessage");
                    System.out.println("GET_RESPONSE_MESSAGE 0 " + schemeResponseMessage);
                    return;
                }
                JSONArray jsonArray = jsonObject.getJSONArray("SchemeName");
                Log.e("jsonObject", new Gson().toJson(jsonObject));
                schemeModelList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    String s_name = ob.optString("Scheme");
                    String id = ob.optString("ID");
                    schemeModel = new SchemeModel(s_name,id);
                    schemeModelList.add(schemeModel);
                }
                schmeAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Exce", e.toString());
            }
        }, error -> {
            Toast.makeText(mContext, "Poor Network Connection", Toast.LENGTH_LONG).show();
            Log.e("Volly ", error.getMessage() + "");
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"SupplierAccountID\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\"}";
                Log.i("TaG", "Request " + SCHEME_LIST + "---> " + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void getItem() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ITEM_LIST, response -> {
            Log.i("TaG", "Response " + ITEM_LIST + "---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("ItemName");
                itemModelList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    String it = ob.optString("ItemName");
                    itemModel = new ItemModel(it,"1");
                    itemModelList.add(itemModel);
                }
                itemAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Exception", e.toString());
            }
        }, error -> {
            Toast.makeText(mContext, "Poor Network Connection", Toast.LENGTH_LONG).show();
            Log.e("Volley Error", error.getMessage());
        }) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonBody = new JSONObject();

                    jsonBody.put("SupplierAccountID", SharedPref.read(SharedPref.PARTY_CODE, "")); // Use the exact value from Postman

                    Log.i("TaG", "Request " + ITEM_LIST + "---> " + jsonBody);
                    return jsonBody.toString().getBytes("utf-8");
                } catch (Exception e) {
                    throw new RuntimeException("Body creation error: " + e.toString());
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("accept", "*/*");
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", Constants.SettingHeader());
                Log.e("Headers", "Authorization Header = " + headers);
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        stringRequest.setShouldCache(true);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void getPcsType(String SupplierAccountID, final String ORDERTYPE) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PCS_TYPE, response -> {
            Log.i("TaG", "Response " + PCS_TYPE + "---> " + response);
            try {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("PcsType");
                typeList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    String tp = ob.optString("PcsType");
                    typeList.add(tp);
                }
                typeAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                Log.e("Exce", e.toString());
            }

        }, error -> {
            Toast.makeText(mContext, "Poor Network Connection", Toast.LENGTH_LONG).show();
            Log.e("Volly ", error.getMessage() + "");
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                String str = "{\"SupplierAccountID\":\"" + SupplierAccountID + "\"" +
                        ",\"ORDERTYPE\":\"" + ORDERTYPE + "\"}";
                Log.i("TaG", "Request " + PCS_TYPE + "---> " + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        stringRequest.setShouldCache(true);
        stringRequest.shouldCache();
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

    }

    @Override
    public void setSaleParty(SalepartyModel salepartyModel) {
        sDialog.dismiss();
        String n = salepartyModel.getName();
        binding.saleParty.setText(salepartyModel.getAccountId() + " " + n);
     //   binding.subParty.setText("SELF");

        binding.subParty.setText("");
        binding.bStation.setText("");
        binding.scheme.setText("");
        binding.transport.setText("");
        tranportID="";
        subPartyId="";
        stationtID="";

        binding.clearSaleparty.setVisibility(View.VISIBLE);
        binding.clearSubparty.setVisibility(View.VISIBLE);
        binding.clearScheme.setVisibility(View.VISIBLE);
        binding.clearTransport.setVisibility(View.VISIBLE);
        binding.clearStation.setVisibility(View.VISIBLE);
        selectedAccountId = salepartyModel.getAccountId();
        System.out.println("SUB_PARTY " + salepartyModel.getAccountId());

        getPartyDetailsByPartyCode(salepartyModel.getAccountId());
        getTransportDetails(SharedPref.read(SharedPref.PARTY_CODE, ""), salepartyModel.getAccountId(), "SELF");
    //    getSubParty(salepartyModel.getAccountId());

        binding.saleParty.setError(null, null);
//        getScheme();

    //    getTransport();
        // getPcsType(pcstype,orderCode.getText().toString(),selectedSuperStar,saleParty.getText().toString());
        //  getSubPartyData(transportstationmarka, n, "SELF");



    }

    @Override
    public void setMarketer(MarketerModel marketerModel) {
        sDialog.dismiss();
        String n = marketerModel.getMarketerName();
        binding.marketer.setText(n);
        getOrderCodeSr(marketerModel.getMarketerName());
        binding.clearMarketer.setVisibility(View.VISIBLE);
        binding.marketer.setError(null, null);
    }

    @Override
    public void setSubParty(SubpartyModel subpartyModel) {

    }

    public void setStatus(String status) {
        sDialog.dismiss();
        binding.tvStatus.setText(status);
    }

    @Override
    public void setSubParty(SubpartyModel subpartyModel1, int p) {
        sDialog.dismiss();
           String n = subpartyModel1.getSubPartyName();

        if (subpartyModel1.getSubPartyName().equalsIgnoreCase("self")) {
            binding.subParty.setText("SELF");
            subPartyId = "SELF";
        } else {
            binding.subParty.setText(subpartyModel1.getSubPartyName());
            subPartyId = subpartyModel1.getAccountCode();
        }

        if (subpartyModel1.getTransportList() != null && !subpartyModel1.getTransportList().isEmpty()) {


            if (transportAdapter != null) {
                transportWithSubParty.clear();
                transportModelList.addAll(subpartyModel1.getTransportList());
                transportModelList.clear();
                transportModelList.addAll(subpartyModel1.getTransportList());
                transportAdapter.notifyDataSetChanged();
            } else {
                transportAdapter = new TransportAdapter(this, subpartyModel1.getTransportList() );
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(transportAdapter);
                transportWithSubParty.clear();
                transportModelList.addAll(subpartyModel1.getTransportList());
           /*     transportModelList.addAll(subpartyModel1.getTransportList());
                transportModelList.addAll(responseData.getSubPartyList().get(0).getTransportList());*/
                transportWithMainList.clear();
                transportWithMainList.addAll(subpartyModel1.getTransportList());
                Log.e("AdapterError", "TransportAdapter is null");
            }


            String tranport = transportModelList.get(0).getTransportName();
            binding.transport.setText(tranport);
            tranportID=transportModelList.get(0).getTransportId();
            binding.clearTransport.setVisibility(View.VISIBLE);
            binding.transport.setError(null, null);

            stationtWithSubParty.clear();
            stationtWithSubParty.addAll(subpartyModel1.getTransportList().get(0).getStationList());
            stationModelList.clear();
            stationModelList.addAll(subpartyModel1.getTransportList().get(0).getStationList());
            if (transportAdapter != null) {
                transportAdapter.notifyDataSetChanged();
            } else {
                Log.e("AdapterError", "TransportAdapter is null");
            }

            String station = subpartyModel1.getTransportList().get(0).getStationList().get(0).getStationName();
            binding.bStation.setText(station);
            stationtID=subpartyModel1.getTransportList().get(0).getStationList().get(0).getStationId();
            binding.clearStation.setVisibility(View.VISIBLE);
            binding.bStation.requestFocus();
            binding.bStation.setError(null, null);

        } else {
            transportModelList.clear();
            stationModelList.clear();
            binding.transport.setText("");
            tranportID="";

            binding.bStation.setText("");
            Log.d("CheckTransportList", "TransportList is either NULL or EMPTY");
        }
        binding.clearSubparty.setVisibility(View.VISIBLE);
        selectedSubPartyId = responseData.getSubPartyList().get(p).getSubPartyId();
        binding.subParty.setError(null, null);

     //   getTransport();
        // clearStation.setVisibility(View.VISIBLE);
//        getSubPartyData1(transportstationmarka, saleParty.getText().toString(), n);
    }

    @Override
    public void setDispatchType(SubpartyModel subpartyModel1, int p) {

    }

    @Override
    public void setDispatchType(DispatchResponse.DispatchType dispatchType, int p) {
        sDialog.dismiss();
        binding.clearDispatchType.setVisibility(View.VISIBLE);
        binding.dispatchType.setText(dispatchType.getValue());
        dispatchTypeID=dispatchType.getId();
    }

    @Override
    public void setTransport(TransportModel transportModel) {
        sDialog.dismiss();
        String n = transportModel.getTransportName();
        binding.transport.setText(n);
        binding.clearTransport.setVisibility(View.VISIBLE);
        binding.transport.setError(null, null);

        stationModelList.clear();
        stationModelList.addAll(transportModel.getStationList());
        transportAdapter.notifyDataSetChanged();
        String station = transportModel.getStationList().get(0).getStationName();
        binding.bStation.setText(station);
        binding.clearStation.setVisibility(View.VISIBLE);
        binding.bStation.requestFocus();
        binding.bStation.setError(null, null);


    }

    @Override
    public void setStation(StationModel stationModel) {
        sDialog.dismiss();
        String n = stationModel.getStationName();
        binding.clearStation.setVisibility(View.VISIBLE);
        binding.bStation.setText(n);
        binding.clearStation.setVisibility(View.VISIBLE);
        binding.bStation.requestFocus();
        binding.bStation.setError(null, null);
    }

    @Override
    public void setItemName(ItemModel itemModel) {
        sDialog.dismiss();
        String n = itemModel.getItemName();
        binding.llRow.item.setText(n);
        binding.llRow.item.setError(null, null);
    }

    @Override
    public void setScheme(SchemeModel schemeModel) {
        sDialog.dismiss();
        String n = schemeModel.getScheme();
        binding.scheme.setText(n);
        binding.clearScheme.setVisibility(View.VISIBLE);
        binding.scheme.setError(null, null);
    }


    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        System.out.println("DATE&TIME:: " + year + ", " + monthOfYear + ", " + dayOfMonth);
        new SpinnerDatePickerDialogBuilder().context(this).
                callback(SupplierOrderFormActivity.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .minDate(year, monthOfYear, dayOfMonth)
                .maxDate(year, monthOfYear + 3, dayOfMonth)
                .build().show();

    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        if (dateFlag.equals("from")) {
            binding.date.setText(simpleDateFormat.format(calendar.getTime()));
            dattAhead(simpleDateFormat.format(calendar.getTime()));
        } else if (dateFlag.equals("to")) {
            // dattAhead();
            binding.dateTo.setText(simpleDateFormat.format(calendar.getTime()));
        }

        if (!binding.date.getText().toString().isEmpty() && !binding.dateTo.getText().toString().isEmpty()) {
            binding.date.setError(null);
            binding.dateTo.setError(null);
        }

    }

    public String dattAhead(String date) {
        String dateBefore = date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(dateBefore));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DAY_OF_MONTH, 3);
        String dateAfter = sdf.format(cal.getTime());
        binding.dateTo.setText(dateAfter);
        return dateAfter;
    }

    private void initPcsAdapter() {
        typeAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, typeList) {
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                tfavv = Typeface.DEFAULT;
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(tfavv);
                //  v.setTextColor(Color.RED);
                v.setTextSize(12);
                return v;
            }

            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(tfavv);
                v.setTextSize(15);
                return v;
            }
        };
        binding.llRow.type.setAdapter(typeAdapter);
    }

    private void handleRadioSelect() {
        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (binding.stra1.isChecked()) {
                selectedSuperStar = "*";
                binding.ll2star.setVisibility(View.GONE);
                getPcsType("AH6682", "*");

            } else if (binding.stra2.isChecked()) {
                selectedSuperStar = "**";
                binding.ll2star.setVisibility(View.VISIBLE);
                getPcsType("AH6682", "**");
            } else if (binding.stra3.isChecked()) {
                selectedSuperStar = "***";
                binding.ll2star.setVisibility(View.GONE);
                getPcsType("AH6682", "***");
            }
        });


        binding.twoStarReadioOption.setOnCheckedChangeListener((group, checkedId) -> {
            if (binding.radioA.isChecked()) {
                selected2Star = "A";
            } else if (binding.radioB.isChecked()) {
                selected2Star = "B";
            } else if (binding.radioC.isChecked()) {
                selected2Star = "C";
            } else if (binding.radioD.isChecked()) {
                selected2Star = "D";
            }
        });
    }

    private void handleClickListner() {
        binding.placeOrder.setOnClickListener(v -> {

            if (validate() && isPlacedOrderBtnEnabled) {
                isPlacedOrderBtnEnabled = false;
                binding.placeOrder.setEnabled(false);
                binding.placeOrder.setBackgroundColor(Color.parseColor("#808080"));
                binding.placeOrder.setText("Please Wait...");

                SendData();
            }

        });
        binding.image1.setOnClickListener(v -> BottomSheet(101));
        binding.image2.setOnClickListener(v -> BottomSheet(102));
        binding.image3.setOnClickListener(v -> BottomSheet(103));
        binding.image4.setOnClickListener(v -> BottomSheet(104));
        binding.image5.setOnClickListener(v -> BottomSheet(105));

        binding.textAddImage.setOnClickListener(v -> {
//            checkAndRequestPermissions();
            binding.textAddImage.setBackgroundColor(getResources().getColor(R.color.green));
            binding.llImg.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(() -> binding.scroll.fullScroll(View.FOCUS_DOWN), 50);
        });
        binding.marketer.setOnClickListener(v -> {
            searchMarketer("Select Marketer");
        });
        binding.saleParty.setOnClickListener(v -> {
            binding.radioSubparty.setChecked(true);
            searchDialog("Select Sale Party");
        });
        binding.subParty.setOnClickListener(v -> {
            if (!binding.saleParty.getText().toString().isEmpty()) {
                if(subpartyModelList.size()>0){
                    subPartyDialog("Select Sub Party");
                }else {
                    Toast.makeText(mContext, "Subparty list is empty!", Toast.LENGTH_SHORT).show();

                }


            } else {
                Toast.makeText(mContext, "Select Sale Party First", Toast.LENGTH_SHORT).show();
            }
        });
        binding.transport.setOnClickListener(v -> {

            if(binding.radioSubpartyRemark.isChecked()){
                if(transportModelList.isEmpty()){
                    Toast.makeText(mContext, "Transport List is empty", Toast.LENGTH_SHORT).show();
                }else {
                    transportDialog("Select Transport");
                }
            }else {
                if (binding.subParty.getText().length() > 0) {
                    if(transportModelList.isEmpty()){
                        Toast.makeText(mContext, "Transport List is empty", Toast.LENGTH_SHORT).show();
                    }else {
                        transportDialog("Select Transport");
                    }

                } else {
                    Toast.makeText(mContext, "Select Subparty First", Toast.LENGTH_SHORT).show();
                }
            }

        });
        binding.bStation.setOnClickListener(v -> {

            if (binding.transport.getText().length() > 0) {
                stationDialog("Select Station");
            } else {
                Toast.makeText(mContext, "Select Transport First", Toast.LENGTH_SHORT).show();
            }

        });
        binding.llRow.item.setOnClickListener(v -> itemDialog("Select Item"));
        binding.scheme.setOnClickListener(v -> schmeDialog("Select Scheme"));
        binding.dispatchType.setOnClickListener(v -> dispatchTypeListDialog("Select Dispatch Type"));


       // ArrayList<String> statusOptions = new ArrayList<>(Arrays.asList("PENDING", "HOLD"));
        binding.tvStatus.setText("PENDING");
  /*      binding.tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusListDialog("Select Status", statusOptions);
            }
        });*/

    }

    private void handleEditInit() {
        if (!binding.marketer.getText().toString().isEmpty()) {
            binding.clearMarketer.setVisibility(View.VISIBLE);
        } else {
            binding.clearMarketer.setVisibility(View.GONE);
        }
        if (!binding.saleParty.getText().toString().isEmpty()) {
            binding.clearSaleparty.setVisibility(View.VISIBLE);
        } else {
            binding.clearSaleparty.setVisibility(View.GONE);
        }
        if (!binding.subParty.getText().toString().isEmpty()) {
            binding.clearSubparty.setVisibility(View.VISIBLE);
        } else {
            binding.clearSubparty.setVisibility(View.GONE);
        }
        if (!binding.subPartyRemark.getText().toString().isEmpty()) {
            binding.clearSubPartyRemark.setVisibility(View.VISIBLE);
        } else {
            binding.clearSubPartyRemark.setVisibility(View.GONE);
        }
        if (!binding.transport.getText().toString().isEmpty()) {
            binding.clearTransport.setVisibility(View.VISIBLE);
        } else {
            binding.clearTransport.setVisibility(View.GONE);
        }
        if (!binding.bStation.getText().toString().isEmpty()) {
            binding.clearStation.setVisibility(View.VISIBLE);
        } else {
            binding.clearStation.setVisibility(View.GONE);
        }
        if (!binding.scheme.getText().toString().isEmpty()) {
            binding.clearScheme.setVisibility(View.VISIBLE);
        } else {
            binding.clearScheme.setVisibility(View.GONE);
        }
    }

    private void handleDate() {
        binding.setDate.setOnClickListener(view -> {
            dateFlag = "from";
            String ddd = CurrentDateTime.getCurrentDateStringDDMMYYYY();
            StringTokenizer tokens = new StringTokenizer(ddd, "/");
            String dd = tokens.nextToken();// this will contain "Fruit"
            String mm = tokens.nextToken();
            String yy = tokens.nextToken();
            showDate(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), R.style.NumberPickerStyle);

        });
        binding.date.setOnClickListener(view -> {
            dateFlag = "from";
            String ddd = CurrentDateTime.getCurrentDateStringDDMMYYYY();
            StringTokenizer tokens = new StringTokenizer(ddd, "/");
            String dd = tokens.nextToken();// this will contain "Fruit"
            String mm = tokens.nextToken();
            String yy = tokens.nextToken();
            showDate(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), R.style.NumberPickerStyle);

        });
        binding.setDateTo.setOnClickListener(view -> {
            dateFlag = "to";
            String ddd = binding.date.getText().toString();
            try {
                int day, month, year;

                if (ddd.isEmpty()) {
                    Calendar calendar = Calendar.getInstance();
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    month = calendar.get(Calendar.MONTH);
                    year = calendar.get(Calendar.YEAR);
                } else {
                    StringTokenizer tokens = new StringTokenizer(ddd, "/");
                    day = Integer.parseInt(tokens.nextToken());
                    month = Integer.parseInt(tokens.nextToken());
                    year = Integer.parseInt(tokens.nextToken());
                }

                showDate(year, month - 1, day + 3, R.style.NumberPickerStyle);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        binding.dateTo.setOnClickListener(view -> {
            dateFlag = "to";
            String ddd = binding.date.getText().toString();

            try {
                int day, month, year;

                if (ddd.isEmpty()) {
                    Calendar calendar = Calendar.getInstance();
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    month = calendar.get(Calendar.MONTH);
                    year = calendar.get(Calendar.YEAR);
                } else {
                    StringTokenizer tokens = new StringTokenizer(ddd, "/");
                    day = Integer.parseInt(tokens.nextToken());
                    month = Integer.parseInt(tokens.nextToken());
                    year = Integer.parseInt(tokens.nextToken());
                }
//abhinavDate

                showDate(year, month - 1, day + 3, R.style.NumberPickerStyle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private boolean validate() {
        String regex = "[^a-zA-Z0-9]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(binding.llRow.amount.getText().toString());
        boolean temp = true;
        if (binding.nickName.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Nick Name Can't be empty", Toast.LENGTH_SHORT).show();
//            Toast.makeText(mContext, "1", Toast.LENGTH_SHORT).show();
            temp = false;
        }
        else if (binding.orderNo.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Order Code Can't be empty. Please select any marketer", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.marketer.getScrollX(), binding.marketer.getScrollY());
            binding.marketer.setError("Can't be empty");
//            Toast.makeText(mContext, "2", Toast.LENGTH_SHORT).show();
            temp = false;

        }
        else if (binding.marketer.getText().toString().isEmpty()) {
            binding.marketer.setError("Can't be empty");
//            Toast.makeText(mContext, "3", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.marketer.getScrollX(), binding.marketer.getScrollY());
            temp = false;

        } else if (binding.saleParty.getText().toString().isEmpty()) {
            binding.saleParty.setError("Can't be empty");
//            Toast.makeText(mContext, "4", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.saleParty.getScrollX(), binding.saleParty.getScrollY());
            temp = false;
        }
        else  if(binding.radioSubparty.isChecked() && binding.radioSubparty.getText().toString().isEmpty()){
            binding.subParty.setError("Can't be empty");
//            Toast.makeText(mContext, "5", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.subParty.getScrollX(), binding.subParty.getScrollY());
            temp = false;

        }
        else if (binding.radioSubpartyRemark.isChecked() && binding.subPartyRemark.getText().toString().isEmpty()){
            binding.subPartyRemark.setError("Can't be empty");
//            Toast.makeText(mContext, "5", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.subPartyRemark.getScrollX(), binding.subPartyRemark.getScrollY());
            temp = false;
        }



       /* else if (binding.subParty.getText().toString().isEmpty()) {
            binding.subParty.setError("Can't be empty");
//            Toast.makeText(mContext, "5", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.subParty.getScrollX(), binding.subParty.getScrollY());
            temp = false;
        }*/ else if (binding.transport.getText().toString().isEmpty()) {
            binding.transport.setError("Can,t be empty");
//            Toast.makeText(mContext, "6", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.transport.getScrollX(), binding.transport.getScrollY());
            temp = false;
        } else if (binding.bStation.getText().toString().isEmpty()) {
            binding.bStation.setError("Can't be empty");
//            Toast.makeText(mContext, "7", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.bStation.getScrollX(), binding.bStation.getScrollY());
            temp = false;
        } else if (binding.dispatchType.getText().toString().isEmpty()) {
            binding.dispatchType.setError("Can't be empty");
//            Toast.makeText(mContext, "7", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.dispatchType.getScrollX(), binding.dispatchType.getScrollY());
            temp = false;
        }
//
//        if (binding.scheme.getText().toString().isEmpty()) {
//            binding.scheme.setError("Can,t be empty");
//            temp = false;
//        }

        else if (binding.llRow.item.getText().toString().isEmpty()) {
            binding.llRow.item.setError("Select Item");
            binding.llRow.qty.requestFocus();
            temp = false;
//            binding.scroll.smoothScrollTo(binding.llRow.item.getScrollX(),binding.llRow.item.getScrollY());
        } else if (binding.llRow.qty.getText().toString().isEmpty() || (Double.parseDouble(binding.llRow.qty.getText().toString()) <= 0)) {
            binding.llRow.qty.setError("Can't be empty");
            binding.llRow.qty.requestFocus();
//            Toast.makeText(mContext, "9", Toast.LENGTH_SHORT).show();
            temp = false;
//            binding.scroll.smoothScrollTo(binding.llRow.qty.getScrollX(),binding.llRow.qty.getScrollY());
        } else if (binding.llRow.amount.getText().toString().isEmpty() || binding.llRow.amount.getText().toString().charAt(0) == '.' || (Double.parseDouble(binding.llRow.amount.getText().toString()) <= 0)) {
            binding.llRow.amount.setError("Can't be empty");
            binding.llRow.qty.requestFocus();
            temp = false;
//          binding.scroll.smoothScrollTo(binding.llRow.amount.getScrollX(),binding.llRow.amount.getScrollY());
        } else if (binding.date.getText().toString().isEmpty()) {
            binding.date.setError("Can't be empty");
            temp = false;
//          binding.scroll.smoothScrollTo(binding.llRow.amount.getScrollX(),binding.llRow.amount.getScrollY());
        } else if (binding.dateTo.getText().toString().isEmpty()) {
            binding.dateTo.setError("Can't be empty");
            temp = false;
//          binding.scroll.smoothScrollTo(binding.llRow.amount.getScrollX(),binding.llRow.amount.getScrollY());
        }
        return temp;
    }

    private void SendData() {
        //test code for disable all views
//        for(int i = 0; i < binding.llLl.getChildCount(); i++){
//            View v = binding.llLl.getChildAt(i);
//            v.setEnabled(false);
//        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

//        myProgress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SAVE_ORDER, response -> {
            Util.getInstance().logLargeString("TaG", "Response " + SAVE_ORDER + "---> " + response);
//            Log.i("TaG", "Response " + SAVE_ORDER  +"---> " + response);

            isPlacedOrderBtnEnabled = true;
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("ResponseCode") == 200) {

                    progressDialog.dismiss();
                    if("Order added as Pending successfully".equals(jsonObject.optString("ResponseMessage"))){
                        showCustomDialogConfirm();

                    }else {
                        showCustomDialogHold();
                     //   showCustomDialogConfirm();
                      //  showCustomDialogHold();

                /*        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                //.setCustomImage(R.drawable.error)
                                .setConfirmButtonTextColor(ContextCompat.getColor(this, R.color.white))
                                .setConfirmButtonBackgroundColor(ContextCompat.getColor(this, R.color.success_text))
                                *//*    .setTitleText("successTitle" + this.getString(R.string.happy_emoji))*//*
                                .setTitleText("Order saved as Successfully")
                                .setContentText( "Please check Whatsapp for pdf")
                                .setConfirmText("CONFRIM")
                                .setConfirmClickListener(sweetAlertDialog -> {
                                    //  myProgress.dismiss();
                                    sweetAlertDialog.dismissWithAnimation();
                                    startActivity(new Intent(mContext, SupplierOrderFormActivity.class));
                                    finish();
                                })
                                .show();*/
                    }



                    Toast.makeText(mContext, jsonObject.getString("ResponseMessage") + "", Toast.LENGTH_SHORT).show();
                } else if (jsonObject.getInt("ResponseCode") == 204) {
//                    myProgress.dismiss();
                    progressDialog.dismiss();
                    AlertUtil.responseElse(mContext, "", jsonObject.getString("ResponseMessage"));
                } else {
//                    myProgress.dismiss();
                    progressDialog.dismiss();
                    new AlertDialog.Builder(mContext).setMessage(jsonObject.getString("ResponseMessage") + "").setPositiveButton("Retry", (arg0, arg1) -> SendData()).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).create().show();
                }
            } catch (JSONException e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        }, error -> {
//            myProgress.dismiss();
            progressDialog.dismiss();
            NetworkResponse response = error.networkResponse;
            if (error instanceof ServerError && response != null) {
                try {
                    String res = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                    // Now you can use any deserializer to make sense of data
                    JSONObject obj = new JSONObject(res);
                    System.out.println("GETTING_ERROR_IN_ORDER " + obj);
                } catch (UnsupportedEncodingException e1) {
                    // Couldn't properly decode data to string
                    e1.printStackTrace();
                } catch (JSONException e2) {
                    // returned data is not JSONObject?
                    e2.printStackTrace();
                }
            }

            isPlacedOrderBtnEnabled = true;
            new AlertDialog.Builder(mContext).setMessage("Try again.. Somthing went wrong").setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
//                    myProgress.dismiss();
                    progressDialog.dismiss();
                    SendData();
                }
            }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).create().show();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String img = img_string != null ? img_string : "";
                String img2 = img_string2 != null ? img_string2 : "";
                String img3 = img_string3 != null ? img_string3 : "";
                String img4 = img_string4 != null ? img_string4 : "";
                String img5 = img_string5 != null ? img_string5 : "";
//                String img = img_string != null ? Base64.encodeToString(img_string.getBytes(), Base64.NO_WRAP) : "";
//                String img2 = img_string2 != null ? Base64.encodeToString(img_string2.getBytes(), Base64.NO_WRAP) : "";
//                String img3 = img_string3 != null ? Base64.encodeToString(img_string3.getBytes(), Base64.NO_WRAP) : "";
//                String img4 = img_string4 != null ? Base64.encodeToString(img_string4.getBytes(), Base64.NO_WRAP) : "";
//                String img5 = img_string5 != null ? Base64.encodeToString(img_string5.getBytes(), Base64.NO_WRAP) : "";

                String SubPartyID = selectedSubPartyId == null ? binding.subParty.getText().toString() : selectedSubPartyId;
                String jsonString = "";
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("AccountID", selectedAccountId);
                    jsonObject.put("SupplierAccountID", SharedPref.read(SharedPref.PARTY_CODE, ""));

                    if(binding.radioSubparty.isChecked()){
                        jsonObject.put("SubPartyID", subPartyId);
                        jsonObject.put("Transport", binding.transport.getText().toString());
                        jsonObject.put("BStation", binding.bStation.getText().toString());


                    }else {
                        jsonObject.put("SubPartyID", null);
                        jsonObject.put("Transport", transportNameMainBrach);
                        jsonObject.put("BStation", bStationNameMainBranch);
                    }
                 //   jsonObject.put("SubPartyID", subPartyId);
                    jsonObject.put("SubPartyasRemark", binding.subPartyRemark.getText().toString().trim());
                    jsonObject.put("dispatchTypeID", dispatchTypeID);
                    jsonObject.put("Marketer", binding.marketer.getText().toString());
                    jsonObject.put("OrderRatio", selected2Star);
                    jsonObject.put("Lattitude", null);  // null can be directly passed
                    jsonObject.put("Longitude", null);
             //       jsonObject.put("Transport", binding.transport.getText().toString());


                    jsonObject.put("TraceIdentifier", traceIdentifier);

                    jsonObject.put("SupplierNickName", binding.nickName.getText().toString());
                    jsonObject.put("SchemeName", binding.scheme.getText().toString());
                    jsonObject.put("Remark", binding.noRemark.getText().toString());
                    jsonObject.put("DeliveryDate", binding.date.getText().toString());
                    jsonObject.put("DeliveryDateTo", binding.dateTo.getText().toString());
                    jsonObject.put("OrderType", selectedSuperStar);
                    jsonObject.put("PcsType", binding.llRow.type.getSelectedItem().toString());
                    jsonObject.put("ItemName", binding.llRow.item.getText().toString());
                    jsonObject.put("Qty", binding.llRow.qty.getText().toString());
                    jsonObject.put("Amount", binding.llRow.amount.getText().toString());
                    jsonObject.put("OrderStatus", "PENDING");
                    jsonObject.put("Image1", img);
                    jsonObject.put("Image2", img2);
                    jsonObject.put("Image3", img3);
                    jsonObject.put("Image4", img4);
                    jsonObject.put("Image5", img5);

                    jsonString = jsonObject.toString();
                    System.out.println(jsonString);

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                String str = "{\"AccountID\":\"" + selectedAccountId + "\"" +
//                        ",\"SupplierAccountID\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\"" +
//                        ",\"SubPartyID\":\"" + SubPartyID + "\"" +
//                        ",\"Marketer\":\"" + binding.marketer.getText().toString() + "\"" +
//                        ",\"OrderRatio\":\"" + selected2Star + "\"" +
//                        ",\"Lattitude\":\"" + null + "\"" +
//                        ",\"Longitude\":\"" + null + "\"" +
//                        ",\"Transport\":\"" + binding.transport.getText().toString() + "\"" +
//                        ",\"BStation\":\"" + binding.bStation.getText().toString() + "\"" +
//                        ",\"SupplierNickName\":\"" + binding.nickName.getText().toString() + "\"" +
//                        ",\"SchemeName\":\"" + binding.scheme.getText().toString() + "\"" +
//                        ",\"Remark\":\"" + binding.noRemark.getText().toString() + "\"" +
//                        ",\"DeliveryDate\":\"" + binding.date.getText().toString() + "\"" +
//                        ",\"DeliveryDateTo\":\"" + binding.dateTo.getText().toString() + "\"" +
//                        ",\"OrderType\":\"" + selectedSuperStar + "\"" +
//                        ",\"PcsType\":\"" + binding.llRow.type.getSelectedItem().toString() + "\"" + "" +
//                        ",\"ItemName\":\"" + binding.llRow.item.getText().toString() + "\"" +
//                        ",\"Qty\":\"" + binding.llRow.qty.getText().toString() + "\"" +
//                        ",\"Amount\":\"" + binding.llRow.amount.getText().toString() + "\"" +
//                        ",\"OrderStatus\":\"" + binding.tvStatus.getText() + "\"" +
//                        ",\"Image1\":\"" + img + "\"" +
//                        ",\"Image2\":\"" + img2 + "\"" +
//                        ",\"Image3\":\"" + img3 + "\"" +
//                        ",\"Image4\":\"" + img4 + "\"" +
//                        ",\"Image5\":\"" + img5 + "\"" + "}";
                Log.i("TaG", "Request " + SAVE_ORDER + "---> " + jsonString);
                Util.getInstance().logLargeString("TaG", "Request " + SAVE_ORDER + "---> " + jsonString);

               return jsonString.getBytes();
            //    return "jsonString.getBytes()";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }


            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        RetryPolicy retryPolicy = new DefaultRetryPolicy(800000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (binding.marketer.getText().toString().isEmpty() || binding.saleParty.getText().toString().isEmpty())
                    finish();
                else
                    onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (binding.marketer.getText().toString().isEmpty() || binding.saleParty.getText().toString().isEmpty()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            if (binding.scroll.getScrollX() == 0 && binding.scroll.getScrollY() == 0) {
                new AlertDialog.Builder(mContext)
                        .setMessage("Do you want to cancel")
                        .setPositiveButton("Yes", (arg0, arg1) -> {
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.cancel()).create().show();
            } else {
                binding.scroll.smoothScrollTo(0, 0);
            }
        }
    }
}