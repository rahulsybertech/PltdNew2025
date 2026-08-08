package com.syber.ssspltd.activitys;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.syber.ssspltd.R;
import static com.syber.ssspltd.Constants.NewErpUrls.GetDispatchTypeList;
import static com.syber.ssspltd.Constants.NewErpUrls.GetFairCustomerList;
import static com.syber.ssspltd.Constants.NewErpUrls.GetFairPartyDetailsByAccountId;
import static com.syber.ssspltd.Constants.NewErpUrls.GetFairSchemeDetail;
import static com.syber.ssspltd.Constants.NewErpUrls.GetMarketerNameByCustomerId;
import static com.syber.ssspltd.Constants.NewErpUrls.GetMarketerNameBySupplierId;
import static com.syber.ssspltd.Constants.NewErpUrls.GetNewTraceIdentifierId;
import static com.syber.ssspltd.Constants.NewErpUrls.ITEM_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.ITEM_LIST_FAIR;
import static com.syber.ssspltd.Constants.NewErpUrls.MARKETER_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.ORDER_NO;
import static com.syber.ssspltd.Constants.NewErpUrls.PCS_TYPE;
import static com.syber.ssspltd.Constants.NewErpUrls.PartyDetailsByPartyCode;
import static com.syber.ssspltd.Constants.NewErpUrls.SALE_PARTY;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_ORDER;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_ORDER_FAIR;
import static com.syber.ssspltd.Constants.NewErpUrls.SCHEME_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.STATION_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.SUB_PARTY;
import static com.syber.ssspltd.Constants.NewErpUrls.StayBookingDataList;
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
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.CurrentDateTime;
import com.syber.ssspltd.Utils.MyProgress;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.Util;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.DispatchAdapter;
import com.syber.ssspltd.adapter.ItemAdapterNew;
import com.syber.ssspltd.adapter.SalePartyAdapterFair;
import com.syber.ssspltd.adapter.fairOrder.PackTypeAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.ItemAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.MarketerAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.SalePartyAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.SchmeAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.StationAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.StatusAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.SubPartyAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.TransportAdapter;
import com.syber.ssspltd.databinding.ActivityFairOrderBinding;
import com.syber.ssspltd.model.fairOrder.ItemDetail;
import com.syber.ssspltd.model.fairOrder.OrderBookSecondary;
import com.syber.ssspltd.model.fairOrder.OrderRequest;
import com.syber.ssspltd.model.fairOrder.PackDataInputAdapter;
import com.syber.ssspltd.model.fairOrder.model.ItemDetailNew;
import com.syber.ssspltd.model.fairOrder.model.ItemsData;
import com.syber.ssspltd.model.fairOrder.model.OrderRequestNew;
import com.syber.ssspltd.model.fairOrder.model.PackTypeItem;
import com.syber.ssspltd.model.fairOrder.model.SalepartyModelFair;
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
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FairOrderActivity extends AppCompatActivity implements OnClick, DatePickerDialog.OnDateSetListener {
    private static final int REQUEST_CODE = 100;
    private int editingPosition = -1; // -1 means not in edit mode
    private static final int CAMERA_REQUEST_CODE = 201;
    public static ArrayList<MarketerModel> mData = new ArrayList<>();
    public static ArrayList<SalepartyModelFair> sData = new ArrayList<>();
    public static ArrayList<SubpartyModel> sbData = new ArrayList<>();
    public static ArrayList<SchemeModel> schemeData = new ArrayList<>();
    public static ArrayList<TransportModel> trData = new ArrayList<>();
    public static ArrayList<StationModel> stData = new ArrayList<>();
    public static ArrayList<ItemModel> itData = new ArrayList<>();
    public static ArrayList<PackTypeItem> selectItemList = new ArrayList<>();
    private final List<PackTypeItem> itemList = new ArrayList<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    static boolean imgFlag;
    //why static
    static Uri imgUri;
    static Bitmap bitmap;
    private final Context mContext = this;
    Uri photoURI;
    ActivityFairOrderBinding binding;
    String img_string, img_string2, img_string3, img_string4, img_string5;
    String dateFlag = "";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    ArrayList<MarketerModel> marketerModelList, marketerData;
    MarketerAdapter marketerAdapter;
    MarketerModel marketerModel;
    ArrayList<SalepartyModelFair> salepartyModelList, saleData;
    SalePartyAdapterFair salePartyAdapter;
    SalepartyModel salepartyModel;
    SalepartyModelFair salepartyModelFair;
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
    ItemAdapterNew itemAdapternew;
    List<String> typeList;
    List<String> typeListID;
    Map<String, String> pcsTypeMap = new HashMap<>();
    ArrayAdapter<String> typeAdapter;
    Typeface tfavv;
    String selectedSuperStar = "*";
    String selected2Star = "A";
    String pcsId = "";
    String selectedAccountId, selectedSubPartyId;
    RecyclerView recyclerView;
    EditText search;
    TextView titile;
    TextView transportNoData;
    int imageRequestCode = 0;
    int cameraRequestCode = 0;
    private Boolean isPlacedOrderBtnEnabled = true;
    private Dialog sDialog;
    private ActivityResultLauncher<Intent> pickImageLauncher;
    private ActivityResultLauncher<Intent> pickCameraImageLauncher;
    private String subPartyId = "";
    private String seletedItemID = "";
    private String traceIdentifier = "";
    private String transportResponseMessage = "";
    private String schemeResponseMessage = "";
    private String dispatchTypeID = "";
    private String tranportID = "";
    private String salePartyIdFair = "";
    private String subPartyIdFair = "";
    private String transportIdFair = "";
    private String markertarIdFair = "";
    private String stationIdFair = "";
    private String  totalQuantity= "1";
    private String schemeIdFair = "";
    private String transportNameMainBrach = "";
    private String bStationNameMainBranch = "";
    private String stationtID = "";
    private RecyclerView recyclerItem;
    private List<ItemsData> itemSuggestions;
    private PackDataInputAdapter adapter;
    private PackTypeAdapter packTypeAdapter;



    private TransportResponse responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFairOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Handle system bars (status + nav bar) insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
        typeListID = new ArrayList<>();
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

        getDispatchTypeList();
        getOrderCodeSr();
        getScheme();
        getPcsType(SharedPref.read(SharedPref.PARTY_CODE, ""), selectedSuperStar);
        binding.placeholder1.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
        binding.placeholder2.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
        binding.placeholder3.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
        binding.placeholder4.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
        binding.placeholder5.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
        handleEditInit();
        initPcsAdapter();
        handleClickListner();
        handleDate();
        getItem();
        binding.llRow.llItemQuantity.setVisibility(View.VISIBLE);
        binding.llRow.item.setVisibility(View.GONE);
     /*   binding.marketer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/



        binding.noRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.noRemark.setFocusable(true);
                binding.noRemark.setFocusableInTouchMode(true);
                binding.noRemark.requestFocus();

                // ⬇️ Explicitly show the keyboard
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(binding.noRemark, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });

        binding.llRow.qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.llRow.qty.setFocusable(true);
                binding.llRow.qty.setFocusableInTouchMode(true);
                binding.llRow.qty.requestFocus();

                // ⬇️ Explicitly show the keyboard
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(binding.llRow.qty, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
        binding.llRow.amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.llRow.amount.setFocusable(true);
                binding.llRow.amount.setFocusableInTouchMode(true);
                binding.llRow.amount.requestFocus();

                // ⬇️ Explicitly show the keyboard
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(binding.llRow.amount, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
      //  getOrderCodeSr("");

        itemSuggestions = Arrays.asList();
      //  setTotalQuantity(totalQuantity);

       /* btnSubmit.setOnClickListener(v -> {
            List<PackTypeItem> list = adapter.getList();
            for (PackTypeItem item : list) {
                Log.i("Submit", "ID: " + item.itemID + ", Name: " + item.itemName + ", Qty: " + item.itemQuantity);
            }
        });*/


        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            System.out.println("GETTING_REQUEST_CODE = " + result.getResultCode() + ", "
                    + "DATA = " + result.getData().getData() + ", " + imageRequestCode);
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
            System.out.println("GETTING_REQUEST_CODE_Camera = " + photoURI + ", "
                    + cameraRequestCode);
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


      binding.llRow.qty.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().matches("\\d+")) {
                    binding.llRow.qty.setError("Only numbers allowed");

                }else {
                    setTotalQuantity(s.toString());
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.llRow.amount.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                try {
                    float val = Float.parseFloat(s.toString());
                    if (val <= 0) {
                        binding.llRow.amount.setError("Amount must be > 0");
                    }
                } catch (NumberFormatException e) {
                    binding.llRow.amount.setError("Invalid amount");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        ArrayList<OrderRequestNew> orderList = new ArrayList<>();


        // Initialize form adapter here
        packTypeAdapter = new PackTypeAdapter(FairOrderActivity.this, orderList, new PackTypeAdapter.OnActionClickListener() {
            @Override
            public void onEditClicked(int position) {
                // Handle edit
            }

            @Override
            public void onDeleteClicked(int position) {
                orderList.remove(position);
                packTypeAdapter.notifyItemRemoved(position);
            }
        });
        binding.recyclerPackItem.setLayoutManager(new LinearLayoutManager(FairOrderActivity.this));
        binding.recyclerPackItem.setAdapter(packTypeAdapter);



        // Save button logic
        binding.llRow.btnSave.setOnClickListener(v -> {
            binding.llRow.llSummary.setVisibility(View.GONE);
            // Clear and repopulate selectItemList
            selectItemList.clear();
            //NEW CODE FOR SINGLE ITEM

            itemList.add(0, new PackTypeItem(seletedItemID, binding.llRow.etItem.getText().toString(), binding.llRow.etQuantity.getText().toString()));

            for (PackTypeItem item : itemList) {
                if (!item.itemName.trim().isEmpty() && !item.itemQuantity.trim().isEmpty()) {
                    selectItemList.add(item);
                }
            }

            // Create OrderRequestNew
            OrderRequestNew orderRequest = new OrderRequestNew();
            orderRequest.setTotalQty(Integer.parseInt(binding.llRow.qty.getText().toString()));
            orderRequest.setTotalAmount(Integer.parseInt(binding.llRow.amount.getText().toString()));
            orderRequest.setPcsId(binding.llRow.type.getSelectedItem().toString());

            // Add item details
            ArrayList<ItemDetailNew> itemDetails = new ArrayList<>();
            for (PackTypeItem packItem : selectItemList) {
                ItemDetailNew item = new ItemDetailNew();
                item.setItemName(packItem.itemName);
                item.setItemQty(packItem.itemQuantity);
                item.setAmount("10");
                item.setItemId(packItem.itemID);
                itemDetails.add(item);
            }
            orderRequest.setItemDetail(itemDetails);

            // Check for add vs edit
            if (editingPosition != -1) {
                orderList.set(editingPosition, orderRequest);
                packTypeAdapter.notifyItemChanged(editingPosition);
                editingPosition = -1; // reset
            } else {
                orderList.add(orderRequest);
                packTypeAdapter.notifyItemInserted(orderList.size() - 1);
            }


        });

        binding.llAddImage.setOnClickListener(v -> {
            if (binding.llImg.getVisibility() == View.VISIBLE) {
                // Hide the layout
        /*        binding.llImg.setVisibility(View.GONE);
                binding.textAddImage.setBackgroundColor(getResources().getColor(R.color.gray)); // optional: revert color
       */     } else {
                // Show the layout
                binding.llImg.setVisibility(View.VISIBLE);
                binding.textAddImage.setBackgroundColor(getResources().getColor(R.color.green)); // highlight
                new Handler().postDelayed(() -> binding.scroll.fullScroll(View.FOCUS_DOWN), 50);
                binding.llAddImage.setVisibility(View.GONE);
            }
        });


        binding.llRow.llSummary.setVisibility(View.VISIBLE);
        binding.llAddItem.setOnClickListener(v -> {
            if (binding.llRow.llSummary.getVisibility() == View.VISIBLE) {
                // Hide the layout
                binding.llRow.llSummary.setVisibility(View.GONE);
            } else {
                // Show the layout and scroll down
                binding.llRow.llSummary.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> binding.scroll.fullScroll(View.FOCUS_DOWN), 50);
            }
        });
    }




    private void showItemBottomSheetDialog(String selectedSuperStar) {
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.summery, null);
        dialog.setContentView(view);

        // Initialize views from layout
        EditText qty = view.findViewById(R.id.qty);
        EditText amount = view.findViewById(R.id.amount);
        TextView item = view.findViewById(R.id.item);
        Button btnSave = view.findViewById(R.id.btnSave);
        RecyclerView recyclerItem = view.findViewById(R.id.recyclerItem);


        // Step 1: Call your method
        getPcsType(SharedPref.read(SharedPref.PARTY_CODE, ""), selectedSuperStar);

        // Step 2: qty TextWatcher
        qty.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().matches("\\d+")) {
                    qty.setError("Only numbers allowed");
                } else {
                    setTotalQuantity(s.toString()); // your function
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // Step 3: amount TextWatcher
        amount.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                try {
                    float val = Float.parseFloat(s.toString());
                    if (val <= 0) {
                        amount.setError("Amount must be > 0");
                    }
                } catch (NumberFormatException e) {
                    amount.setError("Invalid amount");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // Step 4: Save Button click
        btnSave.setOnClickListener(v -> {
            selectItemList.clear(); // Clear if needed

            for (PackTypeItem itemObj : adapter.getList()) {
                if (itemObj.itemName.trim().isEmpty() || itemObj.itemQuantity.trim().isEmpty()) {
                    // Ignore incomplete
                } else {
                    selectItemList.add(itemObj);
                }
            }

            Log.i("selectItemList", selectItemList.toString());
            dialog.dismiss();
        });

        // Step 5: RecyclerView init
        initPcsAdapter();
        recyclerItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerItem.setAdapter(adapter); // make sure 'adapter' is already initialized

        // Step 6: item click
        item.setOnClickListener(v -> itemDialog("Select Item"));


        setTotalQuantity(totalQuantity);
        dialog.show();
    }



    private void setTotalQuantity(String totalQuantity){
        adapter = new PackDataInputAdapter(this, "100", new PackDataInputAdapter.ItemClickCallback() {
            @Override
            public void onItemClick(String itemName, String selectedItemName,String itemID, int adapterPosition) {
                adapter.setItemNameAtPosition(adapterPosition, selectedItemName,itemID);
                Toast.makeText(FairOrderActivity.this, "Selected: " + itemName, Toast.LENGTH_SHORT).show();
                showitemDialog("Select Item",adapterPosition);
            }


        });
        binding.llRow.recyclerItem.setLayoutManager(new LinearLayoutManager(this));
        binding.llRow.recyclerItem.setAdapter(adapter);
        adapter.setSuggestions(itemSuggestions);
    }


    private void showCustomDialogConfirm() {
        final Dialog  sDialog = new Dialog(mContext);
        sDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sDialog.setCanceledOnTouchOutside(false);
        sDialog.setCancelable(false);
        sDialog.setContentView(R.layout.dialog_add_to_order);
        sDialog.setCancelable(true);
        TextView text = (TextView) sDialog.findViewById(R.id.dialog_title);
        TextView dialogTitle = sDialog.findViewById(R.id.dialog_title);
        TextView dialogContent = sDialog.findViewById(R.id.dialog_content);
        TextView confirmButton = sDialog.findViewById(R.id.confirm_button);
        RelativeLayout rlButton = sDialog.findViewById(R.id.rlButton);
        TextView confirm_button = sDialog.findViewById(R.id.confirm_button);

        confirm_button.setText("Ok");


      /*  dialogTitle.setText("Order Saved Successfully");
        dialogContent.setText("Please check WhatsApp for your PDF");
*/

        rlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
                startActivity(new Intent(mContext, FairOrderActivity.class));
                finish();

            }
        });
        sDialog.show();


    }
    private void showCustomDialogHold() {
        final Dialog  sDialog = new Dialog(mContext);
        sDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sDialog.setCanceledOnTouchOutside(false);
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
        TextView confirm_button = sDialog.findViewById(R.id.confirm_button);
        confirm_button.setText("Ok");
        String marketerName = binding.marketer.getText().toString().trim();

        if (marketerName.isEmpty()) {
            dialogContent.setText("Please contact with SSS team");
        } else {
            dialogContent.setText("Please contact with SSS team");
        }
        TextView confirmButton = sDialog.findViewById(R.id.confirm_button);
        RelativeLayout rlButton = sDialog.findViewById(R.id.rlButton);

      /*  dialogTitle.setText("Order Saved Successfully");
        dialogContent.setText("Please check WhatsApp for your PDF");
*/

        rlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
                startActivity(new Intent(mContext, FairOrderActivity.class));
                finish();
            }
        });
        sDialog.show();


    }
    private void showCustomDialogAlreadyExists() {
        final Dialog  sDialog = new Dialog(mContext);
        sDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sDialog.setCanceledOnTouchOutside(false);
        sDialog.setCancelable(false);
        Window window = sDialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        sDialog.setContentView(R.layout.dialog_hold);
        sDialog.setCancelable(true);
        TextView text = (TextView) sDialog.findViewById(R.id.dialog_title);
        text.setVisibility(View.GONE);
        TextView dialogTitle = sDialog.findViewById(R.id.dialog_title);
        TextView dialogContent = sDialog.findViewById(R.id.dialog_content);
        TextView confirm_button = sDialog.findViewById(R.id.confirm_button);
        confirm_button.setText("Ok");
        String marketerName = binding.marketer.getText().toString().trim();

        dialogContent.setText("Record already exists");
        TextView confirmButton = sDialog.findViewById(R.id.confirm_button);
        RelativeLayout rlButton = sDialog.findViewById(R.id.rlButton);

      /*  dialogTitle.setText("Order Saved Successfully");
        dialogContent.setText("Please check WhatsApp for your PDF");
*/

        rlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
                startActivity(new Intent(mContext, FairOrderActivity.class));
                finish();
            }
        });
        sDialog.show();


    }

    public void handleClear(View view) {
        if (view.getId() == R.id.clear_marketer) {
            binding.marketer.setText("");
            binding.clearMarketer.setVisibility(View.GONE);
        } else if (view.getId() == R.id.clear_saleparty) {
            binding.saleParty.setText("");
            binding.subParty.setText("");
            binding.transport.setText("");
            binding.bStation.setText("");
            binding.marketer.setText("");
            markertarIdFair="";
            transportIdFair="";
            subPartyIdFair="";
            stationIdFair="";
            markertarIdFair="";
       //     binding.scheme.setText("");
            binding.transport.setText("");
            subpartyModelList.clear();
            transportModelList.clear();
            stationModelList.clear();
            binding.clearSaleparty.setVisibility(View.GONE);
            binding.clearMarketer.setVisibility(View.GONE);
            binding.clearSubparty.setVisibility(View.GONE);
            binding.clearStation.setVisibility(View.GONE);
            binding.clearTransport.setVisibility(View.GONE);

        } else if (view.getId() == R.id.clear_subparty) {
            binding.subParty.setText("");
            binding.bStation.setText("");
            binding.transport.setText("");
            subPartyIdFair="";
            transportIdFair="";
            stationIdFair="";
            binding.clearSubparty.setVisibility(View.GONE);
            binding.clearStation.setVisibility(View.GONE);
            binding.bStation.setText("");
            binding.clearTransport.setVisibility(View.GONE);
        } else if (view.getId() == R.id.clear_transport) {
            binding.transport.setText("");
            binding.clearTransport.setVisibility(View.GONE);
            binding.bStation.setText("");
//            binding.scheme.setText("");
            binding.clearStation.setVisibility(View.GONE);
            transportIdFair="";
            stationIdFair="";


        } else if (view.getId() == R.id.clear_dispatchType) {


        }
        /*else if (view.getId() == R.id.clearITem) {
            binding.llRow.etItem.setText("");
            binding.llRow.clearITem.setVisibility(View.GONE);
            seletedItemID="";

        } */else if (view.getId() == R.id.clear_station) {
            binding.bStation.setText("");
//            binding.scheme.setText("");
            binding.clearStation.setVisibility(View.GONE);
            stationIdFair="";
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
            BottomSheetNew("1");
         //   BottomSheet(101);
        } else if (id == R.id.placeholder2) {

            BottomSheetNew("2");
        } else if (id == R.id.placeholder3) {
            BottomSheetNew("3");
        } else if (id == R.id.placeholder4) {
         //   BottomSheet(104);
        } else if (id == R.id.placeholder5) {
        //    BottomSheet(105);
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




/*    @Override
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
    }*/

 /*   @Override
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
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  checkPermissionOnActivityResult(requestCode, resultCode, data);
        //198368459, -1, 0, 101
        System.out.println("SSS_REQUEST_CODE " + requestCode + " " + resultCode + " "
                + imageRequestCode + ", " + cameraRequestCode);

        if (data != null) {
           /* if (requestCode == 101) {
                //result ok = -1
                System.out.println("my-result-code " + resultCode + " " + RESULT_OK);
                if (resultCode == RESULT_OK) {
                    imgUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        if (isPlaceHolderSelect.equals("1")){
                            binding.image1.setImageBitmap(bitmap);
                            img_string = getStringImage(bitmap);
                          //  binding.etFront.setError("",null);
                            System.out.println("");
                            // binding.removeFront.setVisibility(View.VISIBLE);
                            byte[] imageInByte = stream.toByteArray();
                            imgFlag = true;
                            binding.image1.setVisibility(View.VISIBLE);
                            binding.removeImage1.setVisibility(View.VISIBLE);
                            binding.progress1.setVisibility(View.GONE);
                        }else if(isPlaceHolderSelect.equals("2")){
                            binding.image2.setImageBitmap(bitmap);
                            img_string2 = getStringImage(bitmap);
                          //  binding.etBack.setError("",null);
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
                        }
                        else {
                            binding.image3.setImageBitmap(bitmap);
                            img_string3 = getStringImage(bitmap);
                            //  binding.etBack.setError("",null);
                            byte[] imageInByte = stream.toByteArray();
                            long lengthbmp = imageInByte.length;
                            Log.e("img2", lengthbmp + "");
                            Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));
                            //  Log.e("img_string2", img_string2 + "");
                            //Toast.makeText(mContext, "Img2", Toast.LENGTH_SHORT).show();
                            imgFlag = true;
                            binding.image3.setVisibility(View.VISIBLE);
                            binding.removeImage3.setVisibility(View.VISIBLE);
                            binding.placeholder3.setVisibility(View.GONE);
                        }
                   *//*     binding.image1.setImageBitmap(bitmap);
                        img_string = getStringImage(bitmap);
                        binding.etFront.setError("",null);
                        System.out.println("");
                        // binding.removeFront.setVisibility(View.VISIBLE);
                        byte[] imageInByte = stream.toByteArray();
                        imgFlag = true;
                        binding.image1.setVisibility(View.VISIBLE);
                        binding.removeImage1.setVisibility(View.VISIBLE);
                        binding.progress1.setVisibility(View.GONE);*//*

                    } catch (Exception e) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("bit", e.toString());

                        imgFlag = false;
                        binding.image1.setVisibility(View.GONE);
                        binding.removeImage1.setVisibility(View.GONE);
                        binding.progress1.setVisibility(View.GONE);
                        binding.placeholder1.setVisibility(View.VISIBLE);
                    }
                }
                else if (resultCode == ImagePicker.RESULT_ERROR) {

                    if (isPlaceHolderSelect.equals("1")){
                        binding.image1.setVisibility(View.GONE);
                        binding.removeImage1.setVisibility(View.GONE);
                        binding.progress1.setVisibility(View.GONE);
                        binding.placeholder1.setVisibility(View.VISIBLE);
                        Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
                    }
                    else if(isPlaceHolderSelect.equals("2")) {
                        Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
                        binding.image2.setVisibility(View.GONE);
                        binding.removeImage2.setVisibility(View.GONE);
                        binding.progress2.setVisibility(View.GONE);
                        binding.placeholder2.setVisibility(View.VISIBLE);
                    }
                    else  {
                        Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
                        binding.image3.setVisibility(View.GONE);
                        binding.removeImage3.setVisibility(View.GONE);
                        binding.progress3.setVisibility(View.GONE);
                        binding.placeholder3.setVisibility(View.VISIBLE);
                    }

                }
                else {
                    if (isPlaceHolderSelect.equals("1")){
                        binding.image1.setVisibility(View.GONE);
                        binding.removeImage1.setVisibility(View.GONE);
                        binding.progress1.setVisibility(View.GONE);
                        binding.placeholder1.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                    }
                    else if(isPlaceHolderSelect.equals("2")) {
                        binding.image2.setVisibility(View.GONE);
                        binding.removeImage2.setVisibility(View.GONE);
                        binding.progress2.setVisibility(View.GONE);
                        binding.placeholder2.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        binding.image3.setVisibility(View.GONE);
                        binding.removeImage3.setVisibility(View.GONE);
                        binding.progress3.setVisibility(View.GONE);
                        binding.placeholder3.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            if (requestCode == 102) {
                if (resultCode == RESULT_OK) {
                    imgUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        if (isPlaceHolderSelect.equals("2")){
                            binding.image2.setImageBitmap(bitmap);
                            img_string2 = getStringImage(bitmap);
                        //    binding.etBack.setError("",null);
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
                        }else if(isPlaceHolderSelect.equals("1")) {
                            binding.image1.setImageBitmap(bitmap);
                            img_string = getStringImage(bitmap);
                         //   binding.etFront.setError("",null);
                            System.out.println("");
                            // binding.removeFront.setVisibility(View.VISIBLE);
                            byte[] imageInByte = stream.toByteArray();
                            imgFlag = true;
                            binding.image1.setVisibility(View.VISIBLE);
                            binding.removeImage1.setVisibility(View.VISIBLE);
                            binding.progress1.setVisibility(View.GONE);
                        }
                        else {
                            binding.image3.setImageBitmap(bitmap);
                            img_string3 = getStringImage(bitmap);
                            //   binding.etFront.setError("",null);
                            System.out.println("");
                            // binding.removeFront.setVisibility(View.VISIBLE);
                            byte[] imageInByte = stream.toByteArray();
                            imgFlag = true;
                            binding.image3.setVisibility(View.VISIBLE);
                            binding.removeImage3.setVisibility(View.VISIBLE);
                            binding.progress3.setVisibility(View.GONE);
                        }

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

                    if (isPlaceHolderSelect.equals("2")){
                        Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
                        binding.image2.setVisibility(View.GONE);
                        binding.removeImage2.setVisibility(View.GONE);
                        binding.progress2.setVisibility(View.GONE);
                        binding.placeholder2.setVisibility(View.VISIBLE);
                    }else if(isPlaceHolderSelect.equals("1")) {
                        binding.image1.setVisibility(View.GONE);
                        binding.removeImage1.setVisibility(View.GONE);
                        binding.progress1.setVisibility(View.GONE);
                        binding.placeholder1.setVisibility(View.VISIBLE);
                        Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        binding.image3.setVisibility(View.GONE);
                        binding.removeImage3.setVisibility(View.GONE);
                        binding.progress3.setVisibility(View.GONE);
                        binding.placeholder3.setVisibility(View.VISIBLE);
                        Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();

                    }

                } else {
                    if (isPlaceHolderSelect.equals("2")){
                        binding.image2.setVisibility(View.GONE);
                        binding.removeImage2.setVisibility(View.GONE);
                        binding.progress2.setVisibility(View.GONE);
                        binding.placeholder2.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                    }else if(isPlaceHolderSelect.equals("1")) {
                        binding.image1.setVisibility(View.GONE);
                        binding.removeImage1.setVisibility(View.GONE);
                        binding.progress1.setVisibility(View.GONE);
                        binding.placeholder1.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                    }
                    else  {
                        binding.image3.setVisibility(View.GONE);
                        binding.removeImage3.setVisibility(View.GONE);
                        binding.progress3.setVisibility(View.GONE);
                        binding.placeholder3.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                    }


                }
            }*/

            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                imgUri = data.getData();

                // Show progress (optional)
                if (isPlaceHolderSelect.equals("1")) {
                    binding.progress1.setVisibility(View.VISIBLE);
                } else if (isPlaceHolderSelect.equals("2")) {
                    binding.progress2.setVisibility(View.VISIBLE);
                } else {
                    binding.progress3.setVisibility(View.VISIBLE);
                }

                executor.execute(() -> {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);

                        // Resize to avoid large memory usage
                        bitmap = resizeBitmap(bitmap, 1024);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream); // Use 80 to reduce size
                        byte[] imageInBytes = stream.toByteArray();
                        String imageString =Base64.encodeToString(imageInBytes, Base64.NO_WRAP);;

                        Bitmap finalBitmap = bitmap;
                        mainHandler.post(() -> {
                            imgFlag = true;

                            if (isPlaceHolderSelect.equals("1")) {
                                binding.image1.setImageBitmap(finalBitmap);
                                img_string = imageString;
                                binding.image1.setVisibility(View.VISIBLE);
                                binding.removeImage1.setVisibility(View.VISIBLE);
                                binding.progress1.setVisibility(View.GONE);
                                binding.placeholder1.setVisibility(View.GONE);
                            } else if (isPlaceHolderSelect.equals("2")) {
                                binding.image2.setImageBitmap(finalBitmap);
                                img_string2 = imageString;
                                binding.image2.setVisibility(View.VISIBLE);
                                binding.removeImage2.setVisibility(View.VISIBLE);
                                binding.progress2.setVisibility(View.GONE);
                                binding.placeholder2.setVisibility(View.GONE);
                            } else {
                                binding.image3.setImageBitmap(finalBitmap);
                                img_string3 = imageString;
                                binding.image3.setVisibility(View.VISIBLE);
                                binding.removeImage3.setVisibility(View.VISIBLE);
                                binding.progress3.setVisibility(View.GONE);
                                binding.placeholder3.setVisibility(View.GONE);
                            }
                        });

                    } catch (Exception e) {
                        mainHandler.post(() -> {
                            Toast.makeText(this, "Image Load Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            imgFlag = false;

                            if (isPlaceHolderSelect.equals("1")) {
                                binding.image1.setVisibility(View.GONE);
                                binding.removeImage1.setVisibility(View.GONE);
                                binding.progress1.setVisibility(View.GONE);
                                binding.placeholder1.setVisibility(View.VISIBLE);
                            } else if (isPlaceHolderSelect.equals("2")) {
                                binding.image2.setVisibility(View.GONE);
                                binding.removeImage2.setVisibility(View.GONE);
                                binding.progress2.setVisibility(View.GONE);
                                binding.placeholder2.setVisibility(View.VISIBLE);
                            } else {
                                binding.image3.setVisibility(View.GONE);
                                binding.removeImage3.setVisibility(View.GONE);
                                binding.progress3.setVisibility(View.GONE);
                                binding.placeholder3.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }
            else {
                if (isPlaceHolderSelect.equals("1")) {
                    binding.image1.setVisibility(View.GONE);
                    binding.removeImage1.setVisibility(View.GONE);
                    binding.progress1.setVisibility(View.GONE);
                    binding.placeholder1.setVisibility(View.VISIBLE);
                } else if (isPlaceHolderSelect.equals("2")) {
                    binding.image2.setVisibility(View.GONE);
                    binding.removeImage2.setVisibility(View.GONE);
                    binding.progress2.setVisibility(View.GONE);
                    binding.placeholder2.setVisibility(View.VISIBLE);
                } else {
                    binding.image3.setVisibility(View.GONE);
                    binding.removeImage3.setVisibility(View.GONE);
                    binding.progress3.setVisibility(View.GONE);
                    binding.placeholder3.setVisibility(View.VISIBLE);
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
            }
            else if (cameraRequestCode == 102) {
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
            }

        }
    }

    private Bitmap
    resizeBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float ratio = (float) width / height;

        if (ratio > 1) {
            width = maxSize;
            height = (int) (width / ratio);
        } else {
            height = maxSize;
            width = (int) (height * ratio);
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
    private static final int MIN_SEARCH_LENGTH = 1;
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
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
            getSaleParty(GetFairCustomerList);
        }

        search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        search.setInputType(InputType.TYPE_CLASS_TEXT);
        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                String searchData = search.getText().toString().trim();
                if (!searchData.isEmpty() && searchData.length() >= MIN_SEARCH_LENGTH) {
                    String finalUrl = GetFairCustomerList + "?searchData=" + searchData;

                    // Perform search
                    getSaleParty(finalUrl);

                    // Hide the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                    }
                } else {
                    Toast.makeText(FairOrderActivity.this, "Please enter at least " + MIN_SEARCH_LENGTH + " characters.", Toast.LENGTH_SHORT).show();
                }
                return true; // consume the event
            }
            return false;
        });

        salePartyAdapter = new SalePartyAdapterFair(this, salepartyModelList, salepartyModel -> {
            sDialog.dismiss();
            salePartyIdFair=salepartyModel.getID();
            subPartyIdFair="";
            stationIdFair="";

            String n = salepartyModel.getName();
            binding.saleParty.setText(salepartyModel.getAccountId() + " " + n);
            //   binding.subParty.setText("SELF");
            binding.subParty.setText("");
            binding.bStation.setText("");
          //  binding.scheme.setText("");
            binding.transport.setText("");
            tranportID="";

            transportIdFair="";
            subPartyId="";
            subPartyIdFair="";
            markertarIdFair="";
            stationtID="";
            stationIdFair="";

            binding.clearSaleparty.setVisibility(View.VISIBLE);
            binding.clearSubparty.setVisibility(View.VISIBLE);
            binding.clearScheme.setVisibility(View.VISIBLE);
            binding.clearTransport.setVisibility(View.VISIBLE);
            binding.clearStation.setVisibility(View.VISIBLE);
            selectedAccountId = salepartyModel.getAccountId();
            System.out.println("SUB_PARTY " + salepartyModel.getAccountId());

            marketerModelList.clear();
            getMarketer(salePartyIdFair);
            getPartyDetailsByPartyCode(salePartyIdFair);

          //  getTransportDetails(SharedPref.read(SharedPref.PARTY_CODE, ""), salepartyModel.getAccountId(), "SELF");
            //    getSubParty(salepartyModel.getAccountId());

            binding.saleParty.setError(null, null);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(salePartyAdapter);
     //   getSaleParty(GetFairCustomerList);
        search.requestFocus();
        search.postDelayed(() -> {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 100);

        sDialog.show();

    }

    private void searchMarketer(final String title) {
        focasableClearDis();
        sDialog = new Dialog(mContext);

        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
         transportNoData = sDialog.findViewById(R.id.transportNoData);


        search = sDialog.findViewById(R.id.search);

        sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        sDialog.findViewById(R.id.cancle).setOnClickListener(v -> sDialog.dismiss());
        if (marketerModelList.size() > 0) {
          filterMarketer(marketerModelList);
        } else {
            getMarketerNew(salePartyIdFair);
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
   //     getMarketer(salePartyIdFair);
        sDialog.show();

    }



    private void subPartyDialog(final String title) {
        focasableClearDis();
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
        focasableClearDis();
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
        focasableClearDis();
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



    private void itemDialog(final String title) {
        focasableClearDis();

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
        if (itemModelList.size() > 0) {
            filterItemNew(itemModelList);
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
                filterItemNew(idata);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        ItemAdapterNew itemAdapter = new ItemAdapterNew(this, itemModelList, (selectedItemName, itemID) -> {
            binding.llRow.etItem.setText("");
            binding.llRow.etQuantity.setError(null);
            String n = selectedItemName;
            seletedItemID=itemID;
            //  binding.llRow.item.setText(n);
            binding.llRow.etItem.setText(n);
            binding.llRow.etItem.setError(null, null);
         //   binding.llRow.clearITem.setVisibility(View.VISIBLE);
         //   adapter.updateItemNameAt(position, selectedItemName,itemID); // or itemID if needed
            sDialog.dismiss();
        });
    /*    itemAdapter = new ItemAdapter(this, itemModelList);*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
        getItem();
        sDialog.show();
    }
    private void showitemDialog(final String title,Integer position) {
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

        ItemAdapterNew itemAdapter = new ItemAdapterNew(this, itemModelList, (selectedItemName, itemID) -> {
            adapter.updateItemNameAt(position, selectedItemName,itemID); // or itemID if needed
            sDialog.dismiss();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
        getItem();
        sDialog.show();
    }



    private void stationDialog(final String title) {
        focasableClearDis();
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

    void filterBc(ArrayList<SalepartyModelFair> bc) {
        salePartyAdapter = new SalePartyAdapterFair(this, bc,new SalePartyAdapterFair.ItemClickCall() {
            @Override
            public void onItemClick(SalepartyModelFair salepartyModel) {
                sDialog.dismiss();
                salePartyIdFair=salepartyModel.getID();
                subPartyIdFair="";
                transportIdFair="";
                stationIdFair="";

                String n = salepartyModel.getName();
                binding.saleParty.setText(salepartyModel.getAccountId() + " " + n);
                //   binding.subParty.setText("SELF");
                binding.subParty.setText("");
                binding.bStation.setText("");
           //     binding.scheme.setText("");
                binding.transport.setText("");
                tranportID="";
                transportIdFair="";
                markertarIdFair="";
                subPartyId="";
                subPartyIdFair="";
                stationtID="";
                stationIdFair="";

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

            }
        });
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
      /*  itemAdapter = new ItemAdapter(this, bc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);*/

        ItemAdapterNew itemAdapter = new ItemAdapterNew(this, itemModelList, (selectedItemName, itemID) -> {
            binding.llRow.etItem.setText("");
            String n = itemModel.getItemName();
            seletedItemID=itemModel.getItemID();
            //  binding.llRow.item.setText(n);
            binding.llRow.etItem.setText(n);
            binding.llRow.etItem.setError( null);
            //   adapter.updateItemNameAt(position, selectedItemName,itemID); // or itemID if needed
            sDialog.dismiss();
        });
    }
    void filterItemNew(ArrayList<ItemModel> bc) {
        // MyPref.storePrefs(context).setTotalMechanics(bc.size() + "");
      /*  itemAdapter = new ItemAdapter(this, bc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);*/

        ItemAdapterNew itemAdapter = new ItemAdapterNew(this, bc, (selectedItemName, itemID) -> {
            binding.llRow.etQuantity.setError(null);
            binding.llRow.etItem.setText("");
          //  String n = itemModel.getItemName();
            seletedItemID=itemID;
            //  binding.llRow.item.setText(n);
            binding.llRow.etItem.setText(selectedItemName);

            binding.llRow.etItem.setError( null);

            //   adapter.updateItemNameAt(position, selectedItemName,itemID); // or itemID if needed
            sDialog.dismiss();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
    }

    void filterStation(ArrayList<StationModel> bc) {
        stationAdapter = new StationAdapter(this, bc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(stationAdapter);
    }

    private void getPartyDetailsByPartyCode(String partyCode ) {
        String supplierCode = SharedPref.read(SharedPref.PARTY_CODE, "");
        String   urlWithPartyCode = GetFairPartyDetailsByAccountId+ "?accountId=" + partyCode+ "&supplierId=" + SharedPref.read(SharedPref.PURCHASE_PARTY_ID, "");
        //  String   urlWithPartyCode = PartyDetailsByPartyCode+ "?partyCode=" + partyCode;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlWithPartyCode, response -> {
            Log.i("TaG", "Response " + urlWithPartyCode + "---> " + response);
            try {

                JSONObject jsonObject = new JSONObject(response);
                Gson gson = new Gson();
                responseData = gson.fromJson(response, TransportResponse.class);

                try {
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
                            subPartyIdFair=responseData.getSubPartyList().get(0).getSubPartyId();
                        } else {
                            binding.subParty.setText(responseData.getSubPartyList().get(0).getSubPartyName());
                            subPartyId = responseData.getSubPartyList().get(0).getAccountCode();
                            subPartyIdFair=responseData.getSubPartyList().get(0).getSubPartyId();
                        }


                        if (responseData.getSubPartyList().get(0).getTransportList() != null && !responseData.getSubPartyList().get(0).getTransportList().isEmpty()) {

                            transportNameMainBrach=     responseData.getSubPartyList().get(0).getTransportList().get(0).getTransportName();
                            tranportID=responseData.getSubPartyList().get(0).getTransportList().get(0).getTransportId();
                            transportIdFair=responseData.getSubPartyList().get(0).getTransportList().get(0).getTransportId();
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
                                stationIdFair =responseData.getSubPartyList().get(0).getTransportList().get(0).getStationList().get(0).getStationId();
                                stationModelList.clear();
                                stationModelList.addAll(responseData.getSubPartyList().get(0).getTransportList().get(0).getStationList());
                                stationtWithMainList.clear();
                                stationtWithMainList.addAll(responseData.getSubPartyList().get(0).getTransportList().get(0).getStationList());
                                stationAdapter.notifyDataSetChanged();
                                binding.bStation.setError(null, null);
                            }else {
                                binding.clearStation.setVisibility(View.GONE);
                                stationModelList.clear();
                            }

                        }else {
                            binding.clearTransport.setVisibility(View.GONE);
                            binding.clearStation.setVisibility(View.GONE);
                            transportModelList.clear();
                            stationModelList.clear();
                        }


                    }else {
                        subpartyModelList.clear();
                        transportModelList.clear();

                        stationModelList.clear();
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

    private void getOrderCodeSr() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetNewTraceIdentifierId, response -> {

            Log.i("TaG", "Response " + GetNewTraceIdentifierId + "---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("ResponseStatus").equals("true")) {
                    traceIdentifier =jsonObject.optString("TraceIdentifier");

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
           /* @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"MarketerName\":\"" + marketerName + "\"}";
                Log.i("TaG", "Request " + GetNewTraceIdentifierId + "---> " + str);
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

    private void getMarketerNew(final String SupplierAccountID) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetMarketerNameBySupplierId, response -> {
            Log.i("TaG", "Response " + GetMarketerNameBySupplierId + "---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray2 = jsonObject.getJSONArray("Marketerlist");
                marketerModelList.clear();
           /*     if(jsonArray2.length()<1){
                    transportNoData.setVisibility(View.VISIBLE);
                    transportNoData.setText(jsonObject.optString("ResponseMessage"));
                    recyclerView.setVisibility(View.INVISIBLE);
                  //  Toast.makeText(this, jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                }else {
                    transportNoData.setText("");
                    transportNoData.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
*/
                for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONObject ob2 = jsonArray2.getJSONObject(i);
                    String marketerName = ob2.optString("MarketerName");
                    String mCode = ob2.optString("MCode");
                    String ID = ob2.optString("ID");
                    marketerModel = new MarketerModel(marketerName, mCode,ID);
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
                String str = "{\"SupplierId\":\"" + SharedPref.read(SharedPref.PURCHASE_PARTY_ID, "") + "\"}";

                Log.i("TaG", "Request " + GetMarketerNameByCustomerId + "---> " + str);
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
    private void getMarketer(final String SupplierAccountID) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetMarketerNameByCustomerId, response -> {
            Log.i("TaG", "Response " + GetMarketerNameByCustomerId + "---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray marketerArray = jsonObject.getJSONArray("Marketerlist");
                marketerModelList.clear();
                /*if(jsonArray2.length()<1){
                    transportNoData.setVisibility(View.VISIBLE);
                    transportNoData.setText(jsonObject.optString("ResponseMessage"));
                    recyclerView.setVisibility(View.INVISIBLE);
                    //  Toast.makeText(this, jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                }else {
                    transportNoData.setText("");
                    transportNoData.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }*/

             /*   for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONObject ob2 = jsonArray2.getJSONObject(i);
                    String marketerName = ob2.optString("MarketerName");
                    String mCode = ob2.optString("MCode");
                    String ID = ob2.optString("ID");
                    marketerModel = new MarketerModel(marketerName, mCode,ID);
                    marketerModelList.add(marketerModel);
                }*/

                if (marketerArray.length() > 0) {
                    JSONObject firstMarketer = marketerArray.getJSONObject(0);
                    String marketerName = firstMarketer.getString("MarketerName");
                    markertarIdFair = firstMarketer.getString("ID");
                    // Set to your view
                    binding.marketer.setText(marketerName);
                    binding.clearMarketer.setVisibility(View.VISIBLE);
                    binding.marketer.setError(null, null);
                }

             //   marketerAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Exce", e.toString());
            }

        }, error -> {
            Toast.makeText(mContext, error.getMessage() + "", Toast.LENGTH_LONG).show();
            Log.e("Volly ", error.getMessage() + "");

        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"SalePartyId\":\"" + SupplierAccountID + "\"}";

                Log.i("TaG", "Request " + GetMarketerNameByCustomerId + "---> " + str);
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
        focasableClearDis();
        final MyProgress myProgress = new MyProgress(this);
        myProgress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            Util.getInstance().logLargeString("TaG", "Response " + url + " -=-=-=>" + response);
            Log.i("TaG", "Response " + url + "---> " + response);
            myProgress.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("salesPartyNames");
                if(jsonArray.length()<1){
                    Toast.makeText(this, jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                }
                salepartyModelList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    String name = ob.optString("Name");
                    String accountId = ob.optString("AccountCode");
                    String ID = ob.optString("ID");
                    salepartyModelFair = new SalepartyModelFair(name, false, "", accountId,ID);
                    salepartyModelList.add(salepartyModelFair);

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

    private void getSalePartyOld(final String url) {
        final MyProgress myProgress = new MyProgress(this);
        myProgress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SALE_PARTY, response -> {
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
                    String ID = ob.optString("ID");
                    salepartyModelFair = new SalepartyModelFair(name, false, "", accountId,ID);
                    salepartyModelList.add(salepartyModelFair);

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
                    handleEditInit();
                    binding.saleParty.setError(null, null);
                    binding.subParty.setError(null, null);
                    binding.scheme.setError(null, null);
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


    private void getScheme() {

      String  getFairSchemeDetail = GetFairSchemeDetail + "?supplierId=" + SharedPref.read(SharedPref.PURCHASE_PARTY_ID, "");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getFairSchemeDetail, response -> {
            Log.i("TaG", "Response " + getFairSchemeDetail + "---> " + response);
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
                    Boolean DefaultStatus = ob.optBoolean("DefaultStatus");
                    String id = ob.optString("ID");
                    schemeModel = new SchemeModel(s_name,id);
                    schemeModelList.add(schemeModel);
                    // If DefaultStatus is true, set value to TextView
                    if (DefaultStatus != null && DefaultStatus) {
                        binding.scheme.setText(s_name);
                        schemeIdFair=schemeModel.getID();
                    }
                }
                schmeAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Exce", e.toString());
            }
        }, error -> {
         //   Toast.makeText(mContext, "Poor Network Connection", Toast.LENGTH_LONG).show();
            Log.e("Volly ", error.getMessage() + "");
        }) {
        /*    @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"SupplierAccountID\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\"}";
                Log.i("TaG", "Request " + SCHEME_LIST + "---> " + str);
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
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void getItem() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ITEM_LIST_FAIR, response -> {
            Log.i("TaG", "Response " + ITEM_LIST_FAIR + "---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                itemModelList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    String it = ob.optString("itemName");
                    String id = ob.optString("itemID");
                    itemModel = new ItemModel(it,id);
                    itemModelList.add(itemModel);
                }
                Log.e("list",itemModelList.toString());
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
                typeListID.clear();
                pcsTypeMap.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    String tp = ob.optString("PcsType");
                    String ID = ob.optString("ID");
                    typeList.add(tp);
                    typeListID.add(ID);
                    pcsTypeMap.put(tp, ID); // key: type, value: ID
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
        salePartyIdFair=salepartyModel.getAccountId();
        subPartyIdFair="";

        stationIdFair="";

        String n = salepartyModel.getName();

        binding.saleParty.setText(salepartyModel.getAccountId() + " " + n);
        //   binding.subParty.setText("SELF");
        binding.subParty.setText("");
        binding.bStation.setText("");
        binding.scheme.setText("");
        binding.transport.setText("");
        tranportID="";
        transportIdFair="";
        markertarIdFair="";
        subPartyId="";
        subPartyIdFair="";
        stationtID="";
        stationIdFair="";

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
        markertarIdFair=marketerModel.getID();
        binding.marketer.setText(n);
        binding.clearMarketer.setVisibility(View.VISIBLE);
        binding.marketer.setError(null, null);
    }

    @Override
    public void setSubParty(SubpartyModel subpartyModel) {

    }

    public void setStatus(String status) {
        sDialog.dismiss();

    }

    @Override
    public void setSubParty(SubpartyModel subpartyModel1, int p) {
        sDialog.dismiss();
        String n = subpartyModel1.getSubPartyName();

        if (subpartyModel1.getSubPartyName().equalsIgnoreCase("self")) {
            binding.subParty.setText("SELF");
            subPartyId = "SELF";
            subPartyIdFair = subpartyModel1.getSubPartyId();
        } else {
            binding.subParty.setText(subpartyModel1.getSubPartyName());
            subPartyId = subpartyModel1.getAccountCode();
            subPartyIdFair = subpartyModel1.getSubPartyId();

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
            transportIdFair=transportModelList.get(0).getTransportId();
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
            stationIdFair=subpartyModel1.getTransportList().get(0).getStationList().get(0).getStationId();
            binding.clearStation.setVisibility(View.VISIBLE);
            binding.bStation.requestFocus();
            binding.bStation.setError(null, null);

        } else {
            transportModelList.clear();
            stationModelList.clear();
            binding.transport.setText("");
            tranportID="";
            transportIdFair="";

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
        dispatchTypeID=dispatchType.getId();
    }

    @Override
    public void setTransport(TransportModel transportModel) {
        sDialog.dismiss();
        String n = transportModel.getTransportName();
        transportIdFair= transportModel.getTransportId();
        stationIdFair= "";
        transportIdFair= transportModel.getTransportId();
        binding.transport.setText(n);
        binding.clearTransport.setVisibility(View.VISIBLE);
        binding.transport.setError(null, null);
        stationModelList.clear();
        stationModelList.addAll(transportModel.getStationList());
        transportAdapter.notifyDataSetChanged();
        String station = transportModel.getStationList().get(0).getStationName();
        stationIdFair=transportModel.getStationList().get(0).getStationId();
        binding.bStation.setText(station);
        binding.clearStation.setVisibility(View.VISIBLE);
        binding.bStation.requestFocus();
        binding.bStation.setError(null, null);


    }

    @Override
    public void setStation(StationModel stationModel) {
        sDialog.dismiss();
        String n = stationModel.getStationName();
        stationIdFair = stationModel.getStationId();
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
        seletedItemID=itemModel.getItemID();

      //  binding.llRow.item.setText(n);
        binding.llRow.etItem.setText(n);
        binding.llRow.etItem.setError(null, null);


    }

    @Override
    public void setScheme(SchemeModel schemeModel) {
        sDialog.dismiss();
        String n = schemeModel.getScheme();
        schemeIdFair=schemeModel.getID();
        binding.scheme.setText(n);
        binding.clearScheme.setVisibility(View.VISIBLE);
        binding.scheme.setError(null, null);
    }


    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        System.out.println("DATE&TIME:: " + year + ", " + monthOfYear + ", " + dayOfMonth);
        new SpinnerDatePickerDialogBuilder().context(this).
                callback(this)
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
                String pcsType = typeList.get(position);             // PcsType (display name)
               //  pcsId = pcsTypeMap.get(pcsType);
                 pcsId = typeListID.get(position);

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


    private void handleClickListner() {
        binding.ll4.setOnClickListener(v -> {

            if (validate() && isPlacedOrderBtnEnabled) {
            /*    isPlacedOrderBtnEnabled = false;
                binding.placeOrder.setEnabled(false);
                binding.placeOrder.setBackgroundColor(Color.parseColor("#808080"));
                binding.placeOrder.setText("Please Wait...");*/

                SendData();
            }

        });
        binding.image1.setOnClickListener(v -> BottomSheetNew("1"));
        binding.image2.setOnClickListener(v -> BottomSheetNew("2"));
        binding.image3.setOnClickListener(v -> BottomSheetNew("3"));
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
           // Toast.makeText(this,"ssssssss",Toast.LENGTH_SHORT).show();
            if(binding.saleParty.getText().toString().isEmpty()){
                Toast.makeText(this,"Sale party is not empty!",Toast.LENGTH_SHORT).show();
            }else {
                searchMarketer("Select Marketer");
            }

        });

        binding.saleParty.setOnClickListener(v -> {

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
            if(transportModelList.isEmpty()){
                Toast.makeText(mContext, "Transport List is empty", Toast.LENGTH_SHORT).show();
            }else {
                transportDialog("Select Transport");
            }

        });
        binding.bStation.setOnClickListener(v -> {

            if (binding.transport.getText().length() > 0) {
                stationDialog("Select Station");
            } else {
                Toast.makeText(mContext, "Select Transport First", Toast.LENGTH_SHORT).show();
            }

        });
      //  binding.llRow.item.setOnClickListener(v -> itemDialog("Select Item"));
        binding.llRow.etItem.setFocusable(false);        // Prevent keyboard
        binding.llRow.etItem.setClickable(true);         // Still allow click
        binding.llRow.etItem.setCursorVisible(false);    // Hide blinking cursor
        binding.llRow.etItem.setLongClickable(false);
        binding.llRow.etItem.setOnClickListener(v ->
                {itemDialog("Select Item");
                 /*   binding.llRow.etItem.setFocusable(false);        // Prevent keyboard
                    binding.llRow.etItem.setClickable(true);         // Still allow click
                    binding.llRow.etItem.setCursorVisible(false);    // Hide blinking cursor
                    binding.llRow.etItem.setLongClickable(false);*/
                }
              );
        binding.scheme.setOnClickListener(v -> schmeDialog("Select Scheme"));


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
      /*  if (!binding.llRow.etItem.getText().toString().isEmpty()) {
            binding.llRow.clearITem.setVisibility(View.VISIBLE);
        } else {
            binding.llRow.clearITem.setVisibility(View.GONE);
        }*/
    }

    private void handleDate() {
        binding.setDate.setOnClickListener(view -> {
            focasableClearDis();
            dateFlag = "from";
            String ddd = CurrentDateTime.getCurrentDateStringDDMMYYYY();
            StringTokenizer tokens = new StringTokenizer(ddd, "/");
            String dd = tokens.nextToken();// this will contain "Fruit"
            String mm = tokens.nextToken();
            String yy = tokens.nextToken();
            showDate(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), R.style.NumberPickerStyle);

        });
        binding.date.setOnClickListener(view -> {
            focasableClearDis();
            dateFlag = "from";
            String ddd = CurrentDateTime.getCurrentDateStringDDMMYYYY();
            StringTokenizer tokens = new StringTokenizer(ddd, "/");
            String dd = tokens.nextToken();// this will contain "Fruit"
            String mm = tokens.nextToken();
            String yy = tokens.nextToken();
            showDate(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), R.style.NumberPickerStyle);

        });
        binding.setDateTo.setOnClickListener(view -> {
            focasableClearDis();
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
            focasableClearDis();
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

    /*    if (binding.marketer.getText().toString().isEmpty()) {
            binding.marketer.setError("Can't be empty");
//            Toast.makeText(mContext, "3", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.marketer.getScrollX(), binding.marketer.getScrollY());
            temp = false;

        }*/
         if (binding.saleParty.getText().toString().isEmpty()) {
             binding.subParty.setError(null, null);
            binding.saleParty.setError("Can't be empty");
            binding.scroll.smoothScrollTo(binding.saleParty.getScrollX(), binding.saleParty.getScrollY());
            temp = false;
        } else if (binding.subParty.getText().toString().isEmpty()) {
             binding.subParty.setError("Can,t be empty");
             binding.transport.setError(null, null);
             binding.scroll.smoothScrollTo(binding.subParty.getScrollX(), binding.subParty.getScrollY());
             temp = false;
         }
      /*  else if (binding.transport.getText().toString().isEmpty()) {
             binding.bStation.setError(null, null);
            binding.transport.setError("Can,t be empty");
            binding.scroll.smoothScrollTo(binding.transport.getScrollX(), binding.transport.getScrollY());
            temp = false;
        } else if (binding.bStation.getText().toString().isEmpty()) {
            binding.bStation.setError("Can't be empty");
            binding.scroll.smoothScrollTo(binding.bStation.getScrollX(), binding.bStation.getScrollY());
            temp = false;
        }*/
        else if (binding.date.getText().toString().isEmpty()) {
            binding.date.setError("Can't be empty");
             binding.date.requestFocus();
       //      binding.scroll.smoothScrollTo(binding.llRow.qty.getScrollX(),binding.llRow.qty.getScrollY());
            temp = false;
        } else if (binding.dateTo.getText().toString().isEmpty()) {
            binding.dateTo.setError("Can't be empty");
            temp = false;}
else if (binding.llRow.qty.getText().toString().isEmpty() || (Double.parseDouble(binding.llRow.qty.getText().toString()) <= 0)) {
            binding.llRow.qty.setError("Can't be empty");
            binding.llRow.qty.requestFocus();
            temp = false;
        //    binding.scroll.smoothScrollTo(binding.llRow.qty.getScrollX(),binding.llRow.qty.getScrollY());
        }
         else if (binding.llRow.amount.getText().toString().trim().isEmpty() || binding.llRow.amount.getText().toString().trim().charAt(0) == '.' || (Double.parseDouble(binding.llRow.amount.getText().toString().trim()) <= 0)) {
             binding.llRow.amount.setError("Can't be empty");
             binding.llRow.amount.requestFocus();
             temp = false;
         }
        else if (binding.llRow.etItem.getText().toString().isEmpty()) {
             binding.llRow.etItem.setError("Can't be empty");
             binding.llRow.etItem.requestFocus();
             new Handler().postDelayed(() -> binding.scroll.fullScroll(View.FOCUS_DOWN), 50);
             binding.llRow.qty.clearFocus();

             // Disable further focusing
             binding.llRow.qty.setFocusable(false);
             binding.llRow.qty.setFocusableInTouchMode(false);

             binding.llRow.amount.clearFocus();

             // Disable further focusing
             binding.llRow.amount.setFocusable(false);
             binding.llRow.amount.setFocusableInTouchMode(false);
            temp = false;
        }else if (binding.llRow.etQuantity.getText().toString().isEmpty() || (Double.parseDouble(binding.llRow.etQuantity.getText().toString()) <= 0)) {
            binding.llRow.etQuantity.setError("Can't be empty");
            binding.llRow.etQuantity.requestFocus();
            temp = false;
           /* new Handler().postDelayed(() ->
                    binding.scroll.fullScroll(View.FOCUS_DOWN), 50);*/
        }
            return temp;
    }

    private void SendData() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

//        myProgress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SAVE_ORDER_FAIR, response -> {
            Util.getInstance().logLargeString("TaG", "Response " + SAVE_ORDER_FAIR + "---> " + response);
          Log.i("TaG", "Response " + SAVE_ORDER_FAIR  +"---> " + response);

            isPlacedOrderBtnEnabled = true;
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("ResponseCode") == 200) {

                    progressDialog.dismiss();
                    if("PENDING".equals(jsonObject.optString("OrderStatus"))){
                        showCustomDialogConfirm();

                    }else {
                        if (jsonObject.getString("ResponseMessage").contains("Record already exists")) {
                            showCustomDialogAlreadyExists();
                        } else {
                            showCustomDialogHold();
                        }
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
                OrderRequest orderRequest = new OrderRequest();

                // Top-level order fields
                orderRequest.salePartyId = salePartyIdFair;
                orderRequest.purchasePartyId = SharedPref.read(SharedPref.PURCHASE_PARTY_ID, "");
                orderRequest.subPartyID = subPartyIdFair;
                orderRequest.orderTypeId = null;
                if(binding.marketer.getText().toString().isEmpty()){
                    orderRequest.marketerId = null;
                }else {
                    orderRequest.marketerId = markertarIdFair.isEmpty() ? null : markertarIdFair;
                }

                orderRequest.bStationId = stationIdFair.isEmpty() ? null : stationIdFair;
                orderRequest.totalQty = 1;
                orderRequest.totalAmount = 10;
                orderRequest.transportId = transportIdFair.isEmpty() ? null : transportIdFair;
                orderRequest.schemeId = schemeIdFair.isEmpty() ? null : schemeIdFair;
                orderRequest.deliveryDateFrom = binding.date.getText().toString();
                orderRequest.deliveryDateTo = binding.dateTo.getText().toString();
                orderRequest.orderStatus = "PENDING";
                orderRequest.traceIdentifier = traceIdentifier.isEmpty() ? null : traceIdentifier;
                orderRequest.remark = binding.noRemark.getText().toString();
                orderRequest.pvtMarka = "Test Pltd Comp.";

                // Nested OrderBookSecondary
                OrderBookSecondary secondary = new OrderBookSecondary();
                secondary.pcsId = pcsId;
            //    secondary.pcsId = binding.llRow.type.getSelectedItem().toString();
                String input = binding.llRow.qty.getText().toString().trim();
                int itemValue = 0;

                if (!input.isEmpty()) {
                    try {
                        itemValue = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        e.printStackTrace(); // or handle invalid number input
                    }
                }
                secondary.qty = itemValue; // Replace with Integer.parseInt(binding.llRow.qty.getText().toString()) if needed


                String amount = binding.llRow.amount.getText().toString().trim();
                if (!amount.isEmpty()) {
                    try {
                        itemValue = Integer.parseInt(amount);
                    } catch (NumberFormatException e) {
                        e.printStackTrace(); // or handle invalid number input
                    }
                }
                secondary.amount = itemValue; // Replace with Integer.parseInt(binding.llRow.amount.getText().toString()) if needed

                // Add item detail
                secondary.itemDetail = new ArrayList<>();
                itemList.add(0, new PackTypeItem(seletedItemID, binding.llRow.etItem.getText().toString(), binding.llRow.etQuantity.getText().toString()));
                for (PackTypeItem packItem : itemList) {
                    ItemDetail item = new ItemDetail();
                    item.itemId = packItem.itemID;
                    item.itemName = packItem.itemName;
                    item.itemQty = String.valueOf(Integer.parseInt(packItem.itemQuantity)); // make sure quantity is numeric
                    item.amount = String.valueOf(itemValue); // optional
                    item.sizeName = "";
                    item.colorName = "";
                    secondary.itemDetail.add(item);
                }

                // Attach OrderBookSecondary list
                orderRequest.orderBookSecondaries = new ArrayList<>();
                orderRequest.orderBookSecondaries.add(secondary);

                // Conditionally add non-empty images
                List<String> images = new ArrayList<>();
                if (img_string != null && !img_string.isEmpty()) images.add(img_string);
                if (img_string2 != null && !img_string2.isEmpty()) images.add(img_string2);
                if (img_string3 != null && !img_string3.isEmpty()) images.add(img_string3);
                if (img_string4 != null && !img_string4.isEmpty()) images.add(img_string4);
                if (img_string5 != null && !img_string5.isEmpty()) images.add(img_string5);
                if (!images.isEmpty()) {
                    orderRequest.images = images;
                }

                // Convert to JSON
                Gson gson = new Gson();
                String jsonBody = gson.toJson(orderRequest);
                Log.i("TAG", "Req " + SAVE_ORDER_FAIR + " ---> " + jsonBody);
                return jsonBody.getBytes(StandardCharsets.UTF_8);
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                Log.i("TaG", "Token " + "Token"  +"---> " + Constants.SettingHeader());
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


    public void focasableClearDis(){
        // Remove focus from noRemark EditText
        binding.noRemark.clearFocus();

        // Disable further focusing
        binding.noRemark.setFocusable(false);
        binding.noRemark.setFocusableInTouchMode(false);




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


    private void launchGallery(int requestCode) {
        ImagePicker.Companion.with(this)
                .galleryOnly()
                /*.crop()*/
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start(requestCode);
    }

    private void launchCamera(int requestCode) {
        ImagePicker.Companion.with(this)
                .cameraOnly()
                /*.crop()*/
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start(requestCode);
    }
    private  String isPlaceHolderSelect="1";

    private void BottomSheetNew(String value) {
        isPlaceHolderSelect=value;
        View dialogView = LayoutInflater.from(this).inflate(R.layout.image_picker_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(dialogView);
        ImageView fromCamera = dialogView.findViewById(R.id.from_camera);
        ImageView fromGallery = dialogView.findViewById(R.id.from_gallery);

        fromCamera.setOnClickListener(v -> {
            dialog.cancel();
            if (isPlaceHolderSelect.equals("1")) {
                binding.progress1.setVisibility(View.VISIBLE);
                binding.placeholder1.setVisibility(View.GONE);
            }else if(isPlaceHolderSelect.equals("2")){
                binding.progress2.setVisibility(View.VISIBLE);
                binding.placeholder2.setVisibility(View.GONE);
            }else {
                binding.progress3.setVisibility(View.VISIBLE);
                binding.placeholder3.setVisibility(View.GONE);

            }
            launchCamera(101);
            //   pickImageFromCamera(101);
            // pickCameraImage(ReqCode);
        });
        fromGallery.setOnClickListener(v -> {

            dialog.cancel();
            if (isPlaceHolderSelect.equals("2")) {
                binding.progress2.setVisibility(View.VISIBLE);
                binding.placeholder2.setVisibility(View.GONE);

            }
            else if(isPlaceHolderSelect.equals("1"))  {
                binding.progress1.setVisibility(View.VISIBLE);
                binding.placeholder1.setVisibility(View.GONE);
            }else {
                binding.progress3.setVisibility(View.VISIBLE);
                binding.placeholder3.setVisibility(View.GONE);
            }


//            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
            launchGallery(102);
            //      pickImageFromGallery(102);
            //    launchGallery(ReqCode);
            //  pickGalleryImage(ReqCode);
//            }

        });
        dialog.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // Check which request it was
            if (requestCode == 101) {
                // This was for Gallery
                launchCamera(101);
                //      launchGallery(101);

            } else if (requestCode == 102) {
                // This was for Camera
                //      launchGallery(102);
            }

        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

}