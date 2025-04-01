package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GetGuestMasterListByCustomerId;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_ORDER;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_UPDATEBOOKING;
import static com.syber.ssspltd.Constants.NewErpUrls.STATION_LIST;
import static com.syber.ssspltd.Constants.NewErpUrls.SaveUpdateGuestMasterDetails;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

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
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.MyConstant;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.SnackbarUtils;
import com.syber.ssspltd.Utils.Util;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.GuestListBookingAdapter;
import com.syber.ssspltd.databinding.ActivityAddGuestInBookingBinding;
import com.syber.ssspltd.model.booking.branchlist.GuestMasterDetail;
import com.syber.ssspltd.model.booking.branchlist.GuestMasterResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddGuestInBookingActivity extends AppCompatActivity {
    private ActivityAddGuestInBookingBinding binding;
    private ActivityResultLauncher<Intent> pickCameraImageLauncher;
    private ActivityResultLauncher<Intent> pickImageLauncher;
    Uri photoURI;
    static Uri imgUri;
    static Bitmap bitmap;
    ArrayList<GuestMasterDetail> guestList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddGuestInBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        initUi();
    }

    private void initUi() {
        guestList = new ArrayList<>();
        String account_id = getIntent().getStringExtra(MyConstant.ACCOUNT_ID);
        binding.image1.setOnClickListener(v -> BottomSheet(101));
        binding.save.setOnClickListener(v -> sendData());
        binding.backBookingList.setOnClickListener(v -> finish());
        binding.tvManageGuest.setOnClickListener(v ->
                clickManageGuest()

        );
        getGuestList(account_id);

    }
    private void clickManageGuest(){
        if(guestList.size()>0){
            startActivity(  new Intent(this,GuestListBookingActivity.class)
                    .putExtra(MyConstant.ACCOUNT_ID,getIntent().getStringExtra(MyConstant.ACCOUNT_ID))
            );
        }else {
            SnackbarUtils.showSuccessSnackbar(findViewById(android.R.id.content), "No data found.");
        //    Toast.makeText(this, "No data found." + "", Toast.LENGTH_LONG).show();
        }

    }
    private void getGuestList(String account_id) {
        String getGuestMasterListByCustomerId="";
        getGuestMasterListByCustomerId = GetGuestMasterListByCustomerId+ "?accountId=" + account_id+ "&partyCode=" + SharedPref.read(SharedPref.PARTY_CODE, "");
  //      getGuestMasterListByCustomerId = GetGuestMasterListByCustomerId+ "?partyCode=" + SharedPref.read(SharedPref.PARTY_CODE, "");
        String finalGetGuestMasterListByCustomerId = getGuestMasterListByCustomerId;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getGuestMasterListByCustomerId, response -> {
            Log.i("TaG", "Response " + finalGetGuestMasterListByCustomerId + "---> " + response);
            try {
                guestList.clear();
                Gson gson = new Gson();
                GuestMasterResponse bookingResponse = gson.fromJson(response, GuestMasterResponse.class);
                //   JSONObject jsonObject = new JSONObject(response);
                guestList.addAll(bookingResponse.getGuestMasterDetailList());


            } catch (Exception e) {
                Log.e("Exce", e.toString());
            }

        }, error -> {
            Toast.makeText(this, error.getMessage() + "", Toast.LENGTH_LONG).show();
            Log.e("Volly ", error.getMessage() + "");
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonBody = new JSONObject();
                try {

//                    jsonBody.put("SupplierAccountID", SharedPref.read(SharedPref.PARTY_CODE, ""));
                    //     jsonBody.put("SupplierAccountID", selectedAccountId);

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
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    boolean isEditMode;
    private void sendData() {


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SaveUpdateGuestMasterDetails, response -> {
            Util.getInstance().logLargeString("TaG", "Response " + SaveUpdateGuestMasterDetails + "---> " + response);
            Log.i("TaG", "Response " + SaveUpdateGuestMasterDetails  +"---> " + response);

            try {
             /*   {"ResponseCode":200,"ResponseStatus":true,"ResponseMessage":"Data Saved Successfully!!","BookingTime":0}*/
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("ResponseCode") == 200) {
                    progressDialog.dismiss();
                    finish();
                    Toast.makeText(this, jsonObject.getString("ResponseMessage") + "", Toast.LENGTH_SHORT).show();
                } else if (jsonObject.getInt("ResponseCode") == 204) {
//                    myProgress.dismiss();
                    Toast.makeText(this, jsonObject.getString("ResponseMessage") + "", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    AlertUtil.responseElse(this, "", jsonObject.getString("ResponseMessage"));
                } else {

//                    myProgress.dismiss();
                    progressDialog.dismiss();
                    new AlertDialog.Builder(this).setMessage(jsonObject.getString("ResponseMessage") + "")
                            .setNegativeButton("Cancel", (dialog, which) -> {

                                dialog.cancel();
                            })
                            .create().show();
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

            // isPlacedOrderBtnEnabled = true;
            new AlertDialog.Builder(this)
                    .setMessage("Try again.. Something went wrong")
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            // Dismiss the progress dialog and retry sending data
                            progressDialog.dismiss();
                            sendData();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Dismiss the progress dialog and perform cancel action
                            progressDialog.dismiss();
                            dialog.dismiss();  // Close the dialog

                           /* binding.save.setEnabled(false);
                            binding.save.setBackgroundColor(Color.parseColor("#2bab1c"));
                            binding.tvSave.setText("Save");*/
                        }
                    })
                    .create()
                    .show();

        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                String jsonString = "";
                try {
                    JSONObject jsonObject = new JSONObject();

              /*      {
                        "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                            "companyID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                            "accountID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                            "customerName": "string",
                            "guestName": "string",
                            "frontDocPath": "string",
                            "backDocPath": "string",
                            "date": "2025-03-28T11:16:37.749Z",
                            "updatedDate": "2025-03-28T11:16:37.749Z",
                            "activeStatus": true,
                            "deletedStatus": true,
                            "partyCode": "string",
                            "nameCount": 0
                    }*/

                   /* if (isEditMode) {
                        *//*jsonObject.put("id",bookingData.getId() );*//*

                        String userType = getIntent().getStringExtra(MyConstant.USERTYPE);
                        if(userType.equals("Other")){
                            jsonObject.put("accountID", "");


                        }else {
                            jsonObject.put("id", JSONObject.NULL);
                            jsonObject.put("accountID", "a3b605ba-f20a-4dd5-9544-784ad0243a1f");

                        }
                    }
                    else {
                        jsonObject.put("id", JSONObject.NULL);
                        String userType = getIntent().getStringExtra(MyConstant.USERTYPE);
                        if(userType.equals("Other")){
                            jsonObject.put("accountID", "accountNameId");

                        }else {
                            jsonObject.put("accountID", JSONObject.NULL);

                        }
                    }*/





                    jsonObject.put("id", JSONObject.NULL);
                    if(getIntent().getStringExtra(MyConstant.ACCOUNT_ID).isEmpty()){
                        jsonObject.put("accountID", JSONObject.NULL);
                    }else {
                        jsonObject.put("accountID", getIntent().getStringExtra(MyConstant.ACCOUNT_ID));
                    }

                    jsonObject.put("partyCode", SharedPref.read(SharedPref.PARTY_CODE, ""));
                    jsonObject.put("guestName", binding.guestName.getText().toString());
                    jsonObject.put("frontDocPath", img_string);
                    jsonObject.put("backDocPath", img_string2);
                    jsonObject.put("activeStatus", true);
                    jsonString = jsonObject.toString();
                    System.out.println(jsonString);




                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("TaG", "Request " + SAVE_ORDER + "---> " + jsonString);
                Util.getInstance().logLargeString("TaG", "Request " + SAVE_ORDER + "---> " + jsonString);

                return jsonString.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                Log.i("TaG", "Token " +Constants.SettingHeader());
                return headers;
            }


            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        RetryPolicy retryPolicy = new DefaultRetryPolicy(800000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
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


//            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
            pickGalleryImage(ReqCode);
//            }

        });
        dialog.show();
    }
    String img_string, img_string2, img_string3, img_string4, img_string5;
    String dateFlag = "";
    static boolean imgFlag;
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
    int imageRequestCode = 0;
    int cameraRequestCode = 0;
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
    private static final int REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 201;
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
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }
    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
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
            }

        }
    }

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
}
