package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_KYC_INFO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.databinding.ActivityApplyForKycactivityBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ApplyForKYCActivity extends AppCompatActivity {

    private ActivityApplyForKycactivityBinding binding;

    public static final int RequestPermissionCode = 7;
    private static final String TAG = ApplyForKYCActivity.class.getSimpleName();
    Context mContext= this;

    private String frontImgString = "",backtImgString ="",panImgString="",passporttImgString="";
    static boolean imgFlag;
    static Uri imgUri;
    static Bitmap bitmap;
    Intent Intent;
    String uploadImg_click="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityApplyForKycactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.supportChat.supportFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lazy.openDialog(mContext);
            }
        });

//        if(CheckingPermissionIsEnabledOrNot())
//        {
//            Toast.makeText(ApplyForKYCActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
//        }
//
//        else {
//            RequestMultiplePermission();
//        }

        imgFlag = false;
        binding.textCompleteKyc.setTextColor(Color.RED);
        binding.toolbar.setTitle("Apply For KYC");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.cameraAdharFront.setOnClickListener(view -> {
            pickCameraImage(100);

        });
        binding.cameraAdharBack.setOnClickListener(view -> {
            pickCameraImage(101);

        });
        binding.cameraPancard.setOnClickListener(view -> {
            pickCameraImage(102);

        });
        binding.cameraPasport.setOnClickListener(view -> {
            pickCameraImage(103);

        });

        binding.galleryAdharFront.setOnClickListener(view -> {
            pickGalleryImage(100);

        });
        binding.galleryAdharBack.setOnClickListener(view -> {
            pickGalleryImage(101);

        });
        binding.galleryPancard.setOnClickListener(view -> {
            pickGalleryImage(102);

        });
        binding.galleryPasport.setOnClickListener(view -> {
            pickGalleryImage(103);

        });

        binding.kycSubmit.setOnClickListener(view -> {
            if (validateImages()) {
                ApplyForKYC(panImgString, frontImgString, backtImgString, passporttImgString);
            }

        });

        binding.removeFront.setOnClickListener(view -> {
            frontImgString = "";
            binding.imageAdharFront.setImageDrawable(ContextCompat.getDrawable(ApplyForKYCActivity.this, R.color.light_gray));
            binding.removeFront.setVisibility(View.GONE);
        });
        binding.removeBack.setOnClickListener(view -> {
            backtImgString="";
            binding.imageAdharBack.setImageDrawable(ContextCompat.getDrawable(ApplyForKYCActivity.this, R.color.light_gray));
            binding.removeBack.setVisibility(View.GONE);
        });

        binding.removePan.setOnClickListener(view -> {
            panImgString="";
            binding.imagePancard.setImageDrawable(ContextCompat.getDrawable(ApplyForKYCActivity.this, R.color.light_gray));
            binding.removePan.setVisibility(View.GONE);
        });
        binding.removePasportImg.setOnClickListener(view -> {
            passporttImgString="";
            binding.imagePasportImg.setImageDrawable(ContextCompat.getDrawable(ApplyForKYCActivity.this, R.color.light_gray));
            binding.removePasportImg.setVisibility(View.GONE);
        });
//        if (uploadImg_click.equals("click_upload"))
//        {
//            if (!frontImgString.equals("")){
//                binding.imageAdharFront.setOnClickListener(view ->
//                {
//                    Intent intent = new Intent(mContext, ImageGalleryActivity.class);
//                    intent.putExtra("img", frontImgString);
//                    startActivity(intent);
//                });
//
//
//            }
//            else if (!backtImgString.equals("")){
//                binding.imageAdharBack.setOnClickListener(view ->
//                {
//                    Intent intent = new Intent(mContext, ImageGalleryActivity.class);
//                    intent.putExtra("img", backtImgString);
//                    startActivity(intent);
//                });
//
//
//            }
//            else if (!panImgString.equals("")){
//                binding.imagePancard.setOnClickListener(view ->
//                {
//                    Intent intent = new Intent(mContext, ImageGalleryActivity.class);
//                    intent.putExtra("img", panImgString);
//                    startActivity(intent);
//                });
//
//
//            }
//            if (!passporttImgString.equals("")){
//                binding.imagePasportImg.setOnClickListener(view ->
//                {
//                    Intent intent = new Intent(mContext, ImageGalleryActivity.class);
//                    intent.putExtra("img", passporttImgString);
//                    startActivity(intent);
//                });
//
//
//            }
//        }

        GetKYCDetails();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                imgUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    binding.imageAdharFront.setImageBitmap(bitmap);
                    frontImgString = getStringImage(bitmap);
                    binding.removeFront.setVisibility(View.VISIBLE);
                    byte[] imageInByte = stream.toByteArray();
                    long lengthbmp =imageInByte.length;
                    imgFlag = true;

                } catch (Exception e) {
                    Log.e("bit", e.toString());
                    imgFlag = false;
                }

            }  else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                imgUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    binding.imageAdharBack.setImageBitmap(bitmap);
                    backtImgString = getStringImage(bitmap);
                    binding.removeBack.setVisibility(View.VISIBLE);
                    byte[] imageInByte = stream.toByteArray();
                    long lengthbmp =imageInByte.length;
                    imgFlag = true;

                } catch (Exception e) {
                    Log.e("bit", e.toString());
                    imgFlag = false;
                }

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 102) {
            if (resultCode == RESULT_OK) {
                imgUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                    binding.imagePancard.setImageBitmap(bitmap);
                    panImgString = getStringImage(bitmap);
                    binding.removePan.setVisibility(View.VISIBLE);
                    byte[] imageInByte = stream.toByteArray();
                    long lengthbmp =imageInByte.length;
                    Log.e("img3", lengthbmp + "");
                    imgFlag = true;

                } catch (Exception e) {
                    Log.e("bit", e.toString());
                    imgFlag = false;
                }

            }  else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 103) {
            if (resultCode == RESULT_OK) {
                imgUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                    binding.imagePasportImg.setImageBitmap(bitmap);
                    passporttImgString = getStringImage(bitmap);
                    binding.removePasportImg.setVisibility(View.VISIBLE);
                    imgFlag = true;

                } catch (Exception e) {
                    Log.e("bit", e.toString());
                    imgFlag = false;
                }

            }  else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void ApplyForKYC(String panCart,String front , String back,String passportSize) {
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);

    }
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        long lengthbmp =imageBytes.length;
        Log.e("Img1", lengthbmp + "");
        Log.e("kb3", String.format("Size : %s", getReadableFileSize(lengthbmp)));
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    boolean validateImages(){
        boolean temp = true;
        if (frontImgString.isEmpty()){
            Toast.makeText(mContext, "Front img Selected", Toast.LENGTH_SHORT).show();
            temp = false;
        }else if (backtImgString.isEmpty()){
            Toast.makeText(mContext, "Back img Selected", Toast.LENGTH_SHORT).show();
            temp = false;
        }else if (panImgString.isEmpty()){
            Toast.makeText(mContext, "PanCard img Selected", Toast.LENGTH_SHORT).show();
            temp = false;
        }
        else if (passporttImgString.isEmpty()){
            Toast.makeText(mContext, "PassportSize img Selected", Toast.LENGTH_SHORT).show();
            temp = false;
        }
        return  temp;
    }

    private void pickCameraImage(int reqCode) {
        ImagePicker.Companion.with(this)
                .cameraOnly()
                .crop()
                .compress(150)
                .start(reqCode);
    }
    private void pickGalleryImage(int reqCode) {
        ImagePicker.Companion.with(this)
                .galleryOnly()
                .compress(150)
                .start(reqCode);
    }

    private void GetKYCDetails() {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor( Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Fetching KYC details\nPlease wait...");
        pDialog.setCancelable(false);
        pDialog.show(); //"http://app.ssspltd.com/apipltd/GetKYCinfo" old url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_KYC_INFO,
                response -> {
            Log.i("TaG",GET_KYC_INFO + "======" + response);
            pDialog.cancel();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("ResponseStatus") && jsonObject.getString("KYCStatus").equals("APPROVAL PENDING")) {
                            JSONObject ob = jsonObject.getJSONObject("UserKYCList");
                            Log.e("ob",ob.toString());
                                binding.removeFront.setVisibility(View.GONE);
                                binding.removeBack.setVisibility(View.GONE);
                                binding.removePan.setVisibility(View.GONE);
                                binding.removePasportImg.setVisibility(View.GONE);
                                binding.cameraAdharFront.setVisibility(View.GONE);
                                binding.cameraAdharBack.setVisibility(View.GONE);
                                binding.cameraPancard.setVisibility(View.GONE);
                                binding.cameraPasport.setVisibility(View.GONE);
                                binding.galleryAdharFront.setVisibility(View.GONE);
                                binding.galleryAdharBack.setVisibility(View.GONE);
                                binding.galleryPancard.setVisibility(View.GONE);
                                binding.galleryPasport.setVisibility(View.GONE);
                                binding.kycSubmit.setVisibility(View.GONE);
                                binding.textCompleteKyc.setText("KYC "+ob.optString("KYCStatus"));
                                uploadImg_click="click_upload";
                                Picasso.with(mContext)
                                        .load(ob.optString("Aadhar_ImagePath"))
                                        .into(binding.imageAdharFront);
                                Picasso.with(mContext)
                                        .load(ob.optString("Aadhar_ImagePathBack"))
                                        .into(binding.imageAdharBack);
                                Picasso.with(mContext)
                                        .load(ob.optString("PAN_ImagePath"))
                                        .into(binding.imagePancard);
                                Picasso.with(mContext)
                                        .load(ob.optString("ProfileImage_Path"))
                                        .into(binding.imagePasportImg);

                        } else {
                            binding.textCompleteKyc.setText("Complete KYC Request");
                           // binding.textCompleteKyc.setTextColor(Color.RED);
                          //  Toast.makeText(mContext, jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            pDialog.cancel();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                // String mob = enter_mobile_number.getText().toString();
                String str = "{\"MOBILENO\":\"" + SharedPref.read(SharedPref.USERMOBILE,"") + "\"}";
                //key - new
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

        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }


    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


//    private void RequestMultiplePermission() {
//
//        // Creating String Array with Permissions.
//        ActivityCompat.requestPermissions(ApplyForKYCActivity.this, new String[]
//                {
//                        CAMERA,
//                        RECORD_AUDIO,
//                }, RequestPermissionCode);
//
//    }
//
//    // Calling override method.
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//
//            case RequestPermissionCode:
//
//                if (grantResults.length > 0) {
//
//                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean RecordAudioPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//
//                    if (CameraPermission && RecordAudioPermission) {
//
//                        Toast.makeText(ApplyForKYCActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(ApplyForKYCActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
//
//                    }
//                }
//
//                break;
//        }
//    }
//
//    public boolean CheckingPermissionIsEnabledOrNot() {
//
//        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
//        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
//
//        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
//                SecondPermissionResult == PackageManager.PERMISSION_GRANTED;

    }
