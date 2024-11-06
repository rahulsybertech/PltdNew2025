package com.syber.ssspltd.activitys.supplierorderform;

import static com.syber.ssspltd.Constants.ConstantVariable.AUTH_TOKEN;
import static com.syber.ssspltd.Constants.NewErpUrls.ITEM_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.MARKETER_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.NICK_NAME;
import static com.syber.ssspltd.Constants.NewErpUrls.ORDER_NO;
import static com.syber.ssspltd.Constants.NewErpUrls.PCS_TYPE;
import static com.syber.ssspltd.Constants.NewErpUrls.SALE_PARTY;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_ORDER;
import static com.syber.ssspltd.Constants.NewErpUrls.SCHEME_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.STATION_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.SUB_PARTY;
import static com.syber.ssspltd.Constants.NewErpUrls.TRANSPORT;
import static com.syber.ssspltd.Constants.NewErpUrls.TRANSPORT_LIST;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.syber.ssspltd.Interface.OnClick;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.CurrentDateTime;
import com.syber.ssspltd.Utils.MyProgress;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.Util;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.activitys.MainActivity;
import com.syber.ssspltd.adapter.supplierformadapter.ItemAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.MarketerAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.SalePartyAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.SchmeAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.StationAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.StatusAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.SubPartyAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.TransportAdapter;
import com.syber.ssspltd.databinding.ActivitySupplierOrderFormBinding;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupplierOrderFormActivity extends AppCompatActivity implements OnClick, DatePickerDialog.OnDateSetListener {

    ActivitySupplierOrderFormBinding binding;
    static boolean imgFlag;
    static Uri imgUri;
    static Bitmap bitmap;
    String img_string, img_string2, img_string3, img_string4, img_string5;
    private final Context mContext = this;
    String dateFlag = "";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);


    ArrayList<MarketerModel> marketerModelList, marketerData;
    public static ArrayList<MarketerModel> mData = new ArrayList<>();
    MarketerAdapter marketerAdapter;
    MarketerModel marketerModel;

    ArrayList<SalepartyModel> salepartyModelList, saleData;
    public static ArrayList<SalepartyModel> sData = new ArrayList<>();
    SalePartyAdapter salePartyAdapter;
    SalepartyModel salepartyModel;

    SubpartyModel subpartyModel;
    ArrayList<SubpartyModel> subpartyModelList, subdata;
    public static ArrayList<SubpartyModel> sbData = new ArrayList<>();
    SubPartyAdapter subPartyAdapter;

    SchemeModel schemeModel;
    ArrayList<SchemeModel> schemeModelList, schData;
    public static ArrayList<SchemeModel> schemeData = new ArrayList<>();
    SchmeAdapter schmeAdapter;


    TransportModel transportModel;
    ArrayList<TransportModel> transportModelList, tdata;
    public static ArrayList<TransportModel> trData = new ArrayList<>();
    TransportAdapter transportAdapter;

    StationModel stationModel;
    ArrayList<StationModel> stationModelList, sdata;
    public static ArrayList<StationModel> stData = new ArrayList<>();
    StationAdapter stationAdapter;

    ItemModel itemModel;
    ArrayList<ItemModel> itemModelList, idata;
    public static ArrayList<ItemModel> itData = new ArrayList<>();
    ItemAdapter itemAdapter;

    List<String> typeList;
    ArrayAdapter<String> typeAdapter;
    Typeface tfavv;

    String selectedSuperStar = "*";
    String selected2Star = "A";
    String selectedAccountId, selectedSubPartyId;
    private Dialog sDialog;
    RecyclerView recyclerView;
    EditText search;
    TextView titile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupplierOrderFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        salepartyModelList = new ArrayList<>();
        saleData = new ArrayList<>();
        subpartyModelList = new ArrayList<>();
        subdata = new ArrayList<>();
        schemeModelList = new ArrayList<>();
        schData = new ArrayList<>();
        itemModelList = new ArrayList<>();
        idata = new ArrayList<>();
        typeList = new ArrayList<>();
        stationModelList = new ArrayList<>();
        sdata = new ArrayList<>();
        marketerModelList = new ArrayList<>();
        marketerData = new ArrayList<>();
        transportModelList = new ArrayList<>();
        tdata = new ArrayList<>();

        geNickName();
        getMarketer(SharedPref.read(SharedPref.PARTY_CODE, ""));
        getSaleParty(SALE_PARTY);
        getTransport();
//        getTransportDetails(SharedPref.read(SharedPref.PARTY_CODE,""), accountId, "SELF");
//        getScheme(accountId);
        getPcsType(SharedPref.read(SharedPref.PARTY_CODE, ""), selectedSuperStar);
        getStation();


        binding.placeholder1.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
        binding.placeholder2.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
        binding.placeholder3.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
        binding.placeholder4.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);
        binding.placeholder5.setImageResource(R.drawable.ic_baseline_add_a_photo_blue);

//        dattAhead(CurrentDateTime.getCurrentDateString());
        binding.date.setText(CurrentDateTime.getCurrentDateStringDDMMYYYY());
        dattAhead(CurrentDateTime.getCurrentDateStringDDMMYYYY());
//        binding.dateTo.setText(CurrentDateTime.getCurrentDateStringDDMMYYYY());

        handleEditInit();
        initPcsAdapter();
        handleRadioSelect();
        handleClickListner();
        handleDate();


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
            binding.subParty.setText("");
            binding.bStation.setText("");
            binding.scheme.setText("");
            binding.transport.setText("");
            binding.clearSaleparty.setVisibility(View.GONE);
            binding.clearSubparty.setVisibility(View.GONE);
            binding.clearStation.setVisibility(View.GONE);
            binding.clearScheme.setVisibility(View.GONE);
            binding.clearTransport.setVisibility(View.GONE);

        } else if (view.getId() == R.id.clear_subparty) {
            binding.subParty.setText("");
//            binding.salePartyMobile.setText("");
//            binding.bStation.setText("");
//            binding.scheme.setText("");
//            binding.transport.setText("");
            binding.clearSubparty.setVisibility(View.GONE);
//            binding.clearStation.setVisibility(View.GONE);
//            binding.clearScheme.setVisibility(View.GONE);
//            binding.clearTransport.setVisibility(View.GONE);
        } else if (view.getId() == R.id.clear_transport) {
            binding.transport.setText("");
            binding.clearTransport.setVisibility(View.GONE);

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

            pickGalleryImage(ReqCode);
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
        ImagePicker.Companion.with(this).cameraOnly().crop().compress(150).start(reqCode);
    }

    private void pickGalleryImage(int reqCode) {
        ImagePicker.Companion.with(this).galleryOnly().compress(150).start(reqCode);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  checkPermissionOnActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                imgUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    binding.image1.setImageBitmap(bitmap);
                    img_string = getStringImage(bitmap);
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

        if (saleData.size() > 0) {
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
            getMarketer(SharedPref.read(SharedPref.PARTY_CODE,""));
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
        getMarketer(SharedPref.read(SharedPref.PARTY_CODE,""));
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

        int maxHeight   = 600;
        int itemHeight  = 100;
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
                    if (subpartyModelList.get(p).getName().toLowerCase().contains(charSequence.toString().toLowerCase())
                    || subpartyModelList.get(p).getAccountCode().toLowerCase().contains(charSequence.toString().toLowerCase())) {
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
        getSubParty(selectedAccountId);
        sDialog.show();

    }

    private void transportDialog(final String title) {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
        search = sDialog.findViewById(R.id.search);
        if (transportModelList.size() > 0) {
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
        }
        sDialog.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
            }
        });
        if (tdata.size() > 0) {
            filterTransport(trData);

        } else {
            getTransport();
        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tdata.clear();
                for (int p = 0; p < transportModelList.size(); p++) {
                    if (transportModelList.get(p).gettName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
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

        getTransport();

        sDialog.show();

    }

    private void schmeDialog(final String title) {
        sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.search_dialog);
        sDialog.setCancelable(true);
        titile = sDialog.findViewById(R.id.title);
        titile.setText(title);
        recyclerView = sDialog.findViewById(R.id.dist_recycler);
        search = sDialog.findViewById(R.id.search);
//        if (schemeModelList.size() > 0) {
            sDialog.findViewById(R.id.my_progress).setVisibility(View.GONE);
      //  }
        sDialog.findViewById(R.id.cancle).setOnClickListener(v -> sDialog.dismiss());
        if (schData.size() > 0) {
            filterScheme(schemeData);
        } else {
            getScheme();
        }
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
        schmeAdapter = new SchmeAdapter(this, schemeModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(schmeAdapter);
        getScheme();
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
        sDialog.findViewById(R.id.cancle).setOnClickListener(v -> sDialog.dismiss());
        if (sdata.size() > 0) {
            filterStation(stData);
        } else {
            getStation();
        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sdata.clear();
                for (int p = 0; p < stationModelList.size(); p++) {
                    if (stationModelList.get(p).getsName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
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
        getStation();
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
            Log.i("TaG", "Response " + NICK_NAME  +"---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
//                JSONObject js = jsonObject.getJSONObject("data");
                if (jsonObject.getString("ResponseStatus").equals("true")) {
                    binding.nickName.setText(jsonObject.getString("Nickname"));
                    if (jsonObject.getBoolean("AllowedAllType")) {
                        binding.stra3.setEnabled(true);
                        binding.stra2.setEnabled(true);
                        binding.redioStarLl.setVisibility(View.VISIBLE);
                    }else {
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
            AlertUtil.responseError(mContext, "Nick Name", error.toString());
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"SupplierAccountID\":\"" + SharedPref.read(SharedPref.PARTY_CODE,"") + "\"}";
                Log.i("TaG", "Request " + NICK_NAME  +"---> " + str);
                return str.getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, AUTH_TOKEN));
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

            Log.i("TaG", "Response " + ORDER_NO  +"---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
//                JSONObject js = jsonObject.getJSONObject("data");
                if (jsonObject.getString("ResponseStatus").equals("true")) {
                    binding.orderNo.setText(jsonObject.getString("OrderNo"));
                    binding.orderNo.setError(null, null);
                } else {
                    AlertUtil.responseElse(mContext, "MaxOrderNoByMarketer ", "api is getting false status. Please try after sometime ");

                }
            } catch (Exception e) {
                AlertUtil.responseExecption(mContext, "MaxOrderNoByMarketer ", e.toString());
            }
        }, error -> {
            AlertUtil.responseError(mContext, "MaxOrderNoByMarketer ", error.toString());
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"MarketerName\":\"" + marketerName + "\"}";

                Log.i("TaG", "Request " + ORDER_NO  +"---> " + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, AUTH_TOKEN));
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
            Log.i("TaG", "Response " + MARKETER_LIST  +"---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray2 = jsonObject.getJSONArray("Marketerlist");
                marketerModelList.clear();
                for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONObject ob2 = jsonArray2.getJSONObject(i);
                    String marketerName = ob2.optString("MarketerName");
                    String mCode = ob2.optString("MCode");
                    marketerModel = new MarketerModel(marketerName, mCode);
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

                Log.i("TaG", "Request " +  MARKETER_LIST + "---> " + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, AUTH_TOKEN));
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
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, AUTH_TOKEN));
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
            Log.i("TaG", "Response " + TRANSPORT  +"---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("ResponseStatus").equals("true")) {
                    binding.transport.setText(jsonObject.getString("TransportName"));
                    binding.bStation.setText(jsonObject.getString("Station"));
                    binding.salePartyMobile.setText(jsonObject.getString("MobileNo"));
                    binding.salePartyEmail.setText(jsonObject.getString("EmailID"));
                    handleEditInit();
                    binding.saleParty.setError(null, null);
                    binding.subParty.setError(null, null);
                    binding.transport.setError(null, null);
                    binding.bStation.setError(null, null);
                    binding.scheme.setError(null, null);
//                    binding.subParty.setText("SELF");
                } else {
                    AlertUtil.responseElse(mContext, "TransportStationbyAccountID ", "api is getting false status. Please try after sometime ");

                }
            } catch (Exception e) {
                AlertUtil.responseExecption(mContext, "TransportStationbyAccountID ", e.toString());
            }
        }, error -> {
            AlertUtil.responseError(mContext, "TransportStationbyAccountID ", error.toString());
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("AccountID", accountId);
                    jsonBody.put("SupplierAccountID", supplierAccountId);
                    jsonBody.put("SubPartyID", subpartyId);
                    Log.i("TaG", "Request " + TRANSPORT  +"---> " + jsonBody);
                    return jsonBody.toString().getBytes("utf-8");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, AUTH_TOKEN));
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

            Log.i("TaG", "Response " + SUB_PARTY  +"---> " + response);

            try {
                subpartyModelList.clear();
                JSONObject jsonObject = new JSONObject(response);
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
                Log.e("Exce", e.toString());
            }
        }, error -> {
            Log.e("Volly ", error.getMessage() + "");
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"AccountID\":\"" + accountId + "\"}";
                Log.i("TaG", "Request " + SUB_PARTY  +"---> " + str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, AUTH_TOKEN));
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
            Log.i("TaG", "Response " + TRANSPORT_LIST  +"---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
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
        })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {

                JSONObject jsonBody = new JSONObject();
                try {

                    jsonBody.put("SupplierAccountID", SharedPref.read(SharedPref.PARTY_CODE, ""));

                    Log.i("TaG", "Request " + TRANSPORT_LIST  +"---> " + jsonBody);
                    return jsonBody.toString().getBytes("utf-8");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, AUTH_TOKEN));
                Log.e("str", "transport header =-=-=" + headers + "\n" );
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
            Log.i("TaG", "Response " + STATION_LIST  +"---> " + response);
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
        })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonBody = new JSONObject();
                try {

                    jsonBody.put("SupplierAccountID", SharedPref.read(SharedPref.PARTY_CODE, ""));

                    Log.i("TaG", "Request " + STATION_LIST  +"---> " + jsonBody);
                    return jsonBody.toString().getBytes("utf-8");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, AUTH_TOKEN));
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
            Log.i("TaG", "Response " + SCHEME_LIST  +"---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("SchemeName");
                Log.e("jsonObject", new Gson().toJson(jsonObject));
                schemeModelList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    String s_name = ob.optString("Scheme");
                    schemeModel = new SchemeModel(s_name);
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
                String str = "{\"SupplierAccountID\":\"" + SharedPref.read(SharedPref.PARTY_CODE,"") + "\"}";
                Log.i("TaG", "Request " + SCHEME_LIST  +"---> " + str);
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

    private void getItem() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ITEM_LIST, response -> {
            Log.i("TaG", "Response " + ITEM_LIST  +"---> " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("ItemName");
                itemModelList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    String it = ob.optString("ItemName");
                    itemModel = new ItemModel(it);
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

                    Log.i("TaG", "Request " + ITEM_LIST  +"---> " + jsonBody);
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
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
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
            Log.i("TaG", "Response " + PCS_TYPE  +"---> " + response);
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
                Log.i("TaG", "Request " + PCS_TYPE  +"---> " + str);
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
        binding.saleParty.setText(salepartyModel.getAccountId()+" "+n);
        binding.subParty.setText("SELF");
        binding.clearSaleparty.setVisibility(View.VISIBLE);
        binding.clearSubparty.setVisibility(View.VISIBLE);
        binding.clearScheme.setVisibility(View.VISIBLE);
        binding.clearTransport.setVisibility(View.VISIBLE);
        binding.clearStation.setVisibility(View.VISIBLE);
        selectedAccountId = salepartyModel.getAccountId();
        getSubParty(salepartyModel.getAccountId());
        getScheme();
        getTransportDetails(SharedPref.read(SharedPref.PARTY_CODE, ""), salepartyModel.getAccountId(), "SELF");
        binding.saleParty.setError(null, null);
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

    public void setStatus(String status) {
        sDialog.dismiss();
        binding.tvStatus.setText(status);
    }

    @Override
    public void setSubParty(SubpartyModel subpartyModel) {
        sDialog.dismiss();
        String n = subpartyModel.getName();
        if (subpartyModel.getAccountCode().equalsIgnoreCase("self")){
            binding.subParty.setText("SELF");
        }else {
            binding.subParty.setText(subpartyModel.getAccountCode() + " " + n);
        }
        binding.clearSubparty.setVisibility(View.VISIBLE);
        selectedSubPartyId = subpartyModel.getAccountCode();
        binding.subParty.setError(null, null);
        // clearStation.setVisibility(View.VISIBLE);
//        getSubPartyData1(transportstationmarka, saleParty.getText().toString(), n);
    }

    @Override
    public void setTransport(TransportModel transportModel) {
        sDialog.dismiss();
        String n = transportModel.gettName();
        binding.transport.setText(n);
        binding.clearTransport.setVisibility(View.VISIBLE);
        binding.transport.setError(null, null);
    }

    @Override
    public void setStation(StationModel stationModel) {
        sDialog.dismiss();
        String n = stationModel.getsName();
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
        new SpinnerDatePickerDialogBuilder().context(this).
                callback(SupplierOrderFormActivity.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .minDate(year, monthOfYear, dayOfMonth)
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
            if (validate())
                SendData();
        });
        binding.image1.setOnClickListener(v -> BottomSheet(101));
        binding.image2.setOnClickListener(v -> BottomSheet(102));
        binding.image3.setOnClickListener(v -> BottomSheet(103));
        binding.image4.setOnClickListener(v -> BottomSheet(104));
        binding.image5.setOnClickListener(v -> BottomSheet(105));

        binding.textAddImage.setOnClickListener(v -> {
            binding.textAddImage.setBackgroundColor(getResources().getColor(R.color.green));
            binding.llImg.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(() -> binding.scroll.fullScroll(View.FOCUS_DOWN), 50);
        });
        binding.marketer.setOnClickListener(v -> {
            searchMarketer("Select Marketer");
        });
        binding.saleParty.setOnClickListener(v -> {
            searchDialog("Select Sale Party");
        });
        binding.subParty.setOnClickListener(v -> {
            if (!binding.saleParty.getText().toString().isEmpty()) {
                subPartyDialog("Select Sub Party");
            } else {
                Toast.makeText(mContext, "Select Sale Party First", Toast.LENGTH_SHORT).show();
            }
        });
        binding.transport.setOnClickListener(v -> {
            transportDialog("Select Transport");
        });
        binding.bStation.setOnClickListener(v -> {
            stationDialog("Select Station");
        });
        binding.llRow.item.setOnClickListener(v -> itemDialog("Select Item"));
        binding.scheme.setOnClickListener(v -> schmeDialog("Select Scheme"));


        ArrayList<String> statusOptions    = new ArrayList<>(Arrays.asList("PENDING", "HOLD"));
        binding.tvStatus.setText("PENDING");
        binding.tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusListDialog("Select Status", statusOptions);
            }
        });

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
            StringTokenizer tokens = new StringTokenizer(ddd, "/");
            String dd = tokens.nextToken();// this will contain "Fruit"
            String mm = tokens.nextToken();
            String yy = tokens.nextToken();
            showDate(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd) + 3, R.style.NumberPickerStyle);
        });
        binding.dateTo.setOnClickListener(view -> {
            dateFlag = "to";
            String ddd = binding.date.getText().toString();
            StringTokenizer tokens = new StringTokenizer(ddd, "/");
            String dd = tokens.nextToken();// this will contain "Fruit"
            String mm = tokens.nextToken();
            String yy = tokens.nextToken();
            showDate(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd), R.style.NumberPickerStyle);
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
            binding.scroll.smoothScrollTo(binding.marketer.getScrollX(),binding.marketer.getScrollY());
            binding.marketer.setError("Can't be empty");
//            Toast.makeText(mContext, "2", Toast.LENGTH_SHORT).show();
            temp = false;

        }
        else if (binding.marketer.getText().toString().isEmpty()) {
            binding.marketer.setError("Can't be empty");
//            Toast.makeText(mContext, "3", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.marketer.getScrollX(),binding.marketer.getScrollY());
            temp = false;

        }
       else if (binding.saleParty.getText().toString().isEmpty()) {
            binding.saleParty.setError("Can't be empty");
//            Toast.makeText(mContext, "4", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.saleParty.getScrollX(),binding.saleParty.getScrollY());
            temp = false;
        }
        else  if (binding.subParty.getText().toString().isEmpty()) {
            binding.subParty.setError("Can't be empty");
//            Toast.makeText(mContext, "5", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.subParty.getScrollX(),binding.subParty.getScrollY());
            temp = false;
        }
        else if (binding.transport.getText().toString().isEmpty()) {
            binding.transport.setError("Can,t be empty");
//            Toast.makeText(mContext, "6", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.transport.getScrollX(),binding.transport.getScrollY());
            temp = false;
        }
        else  if (binding.bStation.getText().toString().isEmpty()) {
            binding.bStation.setError("Can't be empty");
//            Toast.makeText(mContext, "7", Toast.LENGTH_SHORT).show();
            binding.scroll.smoothScrollTo(binding.bStation.getScrollX(),binding.bStation.getScrollY());
            temp = false;
        }
//
//        if (binding.scheme.getText().toString().isEmpty()) {
//            binding.scheme.setError("Can,t be empty");
//            temp = false;
//        }

        else  if (binding.llRow.item.getText().toString().isEmpty()) {
            binding.llRow.item.setError("Select Item");
            binding.llRow.qty.requestFocus();
            temp = false;
//            binding.scroll.smoothScrollTo(binding.llRow.item.getScrollX(),binding.llRow.item.getScrollY());
        }
        else if (binding.llRow.qty.getText().toString().isEmpty() || (Double.parseDouble(binding.llRow.qty.getText().toString()) <=0)) {
            binding.llRow.qty.setError("Can't be empty");
            binding.llRow.qty.requestFocus();
//            Toast.makeText(mContext, "9", Toast.LENGTH_SHORT).show();
            temp = false;
//            binding.scroll.smoothScrollTo(binding.llRow.qty.getScrollX(),binding.llRow.qty.getScrollY());
        }
        else  if (binding.llRow.amount.getText().toString().isEmpty() || binding.llRow.amount.getText().toString().charAt(0)=='.' || (Double.parseDouble(binding.llRow.amount.getText().toString()) <=0)) {
            binding.llRow.amount.setError("Can't be empty");
            binding.llRow.qty.requestFocus();
            temp=false;
//          binding.scroll.smoothScrollTo(binding.llRow.amount.getScrollX(),binding.llRow.amount.getScrollY());
        }
        return temp;
    }

    private void SendData() {
        final MyProgress progress = new MyProgress(mContext);
        progress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SAVE_ORDER,
                response -> {
            Log.i("TaG", "Response " + SAVE_ORDER  +"---> " + response);
            progress.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("ResponseCode")==200) {
//                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
//
//                            //.setCustomImage(R.drawable.error)
//                            .setConfirmButtonTextColor(ContextCompat.getColor(this, R.color.success_text))
//                            .setConfirmButtonBackgroundColor(ContextCompat.getColor(this, R.color.success_bg))
//                            .setTitleText("successTitle" + this.getString(R.string.happy_emoji))
//                            .setContentText( "succesMsg")
//                            .setConfirmText("confirmText")
                           // .setConfirmClickListener(sweetAlertDialog -> {
                                startActivity(new Intent(mContext, MainActivity.class));
                                finish();
//                            })
//                            .show();

                    Toast.makeText(mContext, jsonObject.getString("ResponseMessage")+"", Toast.LENGTH_SHORT).show();
                }else if(jsonObject.getInt("ResponseCode")==204){
                    AlertUtil.responseElse(mContext,"",jsonObject.getString("ResponseMessage"));
                }else {
                    new AlertDialog.Builder(mContext).setMessage(jsonObject.getString("ResponseMessage") + "").setPositiveButton("Retry", (arg0, arg1) -> SendData()).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).create().show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> new AlertDialog.Builder(mContext).setMessage("Try again.. Somthing went wrong").setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                progress.dismiss();
                SendData();
            }
        }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).create().show()) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                progress.dismiss();
                String img  = img_string != null ? Base64.encodeToString(img_string.getBytes(), Base64.NO_WRAP) : "";
                String img2 = img_string2 != null ? Base64.encodeToString(img_string2.getBytes(), Base64.NO_WRAP) : "";
                String img3 = img_string3 != null ? Base64.encodeToString(img_string3.getBytes(), Base64.NO_WRAP) : "";
                String img4 = img_string4 != null ? Base64.encodeToString(img_string4.getBytes(), Base64.NO_WRAP) : "";
                String img5 = img_string5 != null ? Base64.encodeToString(img_string5.getBytes(), Base64.NO_WRAP) : "";

                String SubPartyID = selectedSubPartyId==null?binding.subParty.getText().toString():selectedSubPartyId;
                String str = "{\"AccountID\":\"" + selectedAccountId + "\"" +
                        ",\"SupplierAccountID\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\"" +
                        ",\"SubPartyID\":\"" + SubPartyID + "\"" +
                        ",\"Marketer\":\"" + binding.marketer.getText().toString() + "\"" +
                        ",\"OrderRatio\":\"" + selected2Star + "\"" +
                        ",\"Lattitude\":\"" + null + "\"" +
                        ",\"Longitude\":\"" + null + "\"" +
                        ",\"Transport\":\"" + binding.transport.getText().toString() + "\"" +
                        ",\"BStation\":\"" + binding.bStation.getText().toString() + "\"" +
                        ",\"SupplierNickName\":\"" + binding.nickName.getText().toString() + "\"" +
                        ",\"SchemeName\":\"" + binding.scheme.getText().toString() + "\"" +
                        ",\"Remark\":\"" + binding.noRemark.getText().toString() + "\"" +
                        ",\"DeliveryDate\":\"" + binding.date.getText().toString() + "\"" +
                        ",\"DeliveryDateTo\":\"" + binding.dateTo.getText().toString() + "\"" +
                        ",\"OrderType\":\"" + selectedSuperStar + "\"" +
                        ",\"PcsType\":\"" + binding.llRow.type.getSelectedItem().toString() + "\"" + "" +
                        ",\"ItemName\":\"" + binding.llRow.item.getText().toString() + "\"" +
                        ",\"Qty\":\"" + binding.llRow.qty.getText().toString() + "\"" +
                        ",\"Amount\":\"" + binding.llRow.amount.getText().toString() + "\"" +
                        ",\"OrderStatus\":\"" + binding.tvStatus.getText() + "\"" +
                        ",\"Image1\":\"" + img + "\"" +
                        ",\"Image2\":\"" + img2 + "\"" +
                        ",\"Image3\":\"" + img3 + "\"" +
                        ",\"Image4\":\"" + img4 + "\"" +
                        ",\"Image5\":\"" + img5 + "\"" + "}";

                Log.i("TaG", "Request " + SAVE_ORDER  +"---> " + str);

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
        RetryPolicy retryPolicy = new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
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
        }
        else {
            if (binding.scroll.getScrollX()==0 && binding.scroll.getScrollY()==0 ){
                            new AlertDialog.Builder(mContext)
                    .setMessage("Do you want to cancel")
                    .setPositiveButton("Yes", (arg0, arg1) -> {
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.cancel()).create().show();
            }else {
                binding.scroll.smoothScrollTo(0, 0);
            }
        }
    }
}