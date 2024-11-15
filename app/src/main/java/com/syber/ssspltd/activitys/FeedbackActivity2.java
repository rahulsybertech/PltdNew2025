package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.ADD_FEEDBACK;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity2 extends AppCompatActivity {
    Context mContext = this;
    ImageView star_1,star_2,star_3,star_4,star_5;
    TextView feedback,feedbackSumbit;
    TextView setText_change;
    Boolean isOnePressed = false, isSecondPlace = false, isThirdPlace = false, isforthPlace = false,isfivePlace = false;
    String noOfStar ="";
    FloatingActionButton supportChat;
    public static String networkCount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback2);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Feedback");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        star_1=findViewById(R.id.star_one);
        star_2=findViewById(R.id.star_two);
        star_3=findViewById(R.id.star_three);
        star_4=findViewById(R.id.star_four);
        star_5=findViewById(R.id.star_five);
        supportChat=findViewById(R.id.support_fab);

        supportChat.setOnClickListener((View.OnClickListener) v ->
                Lazy.openDialog(mContext));
        feedback=findViewById(R.id.feedback_f);
        setText_change=findViewById(R.id.feedback_text);
        feedbackSumbit=findViewById(R.id.feedbackSumbit);


        feedbackSumbit.setOnClickListener(v -> {
            if (noOfStar.equals("1")||noOfStar.equals("2")||noOfStar.equals("3")||noOfStar.equals("4")||noOfStar.equals("5"))
            {
                AddFeedback();
            }
            else
            {
                    Toast.makeText(mContext, "Select Any one", Toast.LENGTH_LONG).show();
            }

        });
        star_1.setOnClickListener(v -> {
            isOnePressed = true;
            noOfStar ="1";
            star_1.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_two));
            feedback.setText("VERY BAD");
            setText_change.setHint("Tell us more.....");
            if (isSecondPlace || isThirdPlace ||isforthPlace || isfivePlace) {
                star_2.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_one));
                star_3.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_one));
                star_4.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_one));
                star_5.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_one));

                isSecondPlace = false;
                isThirdPlace = false;
                isforthPlace = false;
                isfivePlace=false;
            }
        });
        star_2.setOnClickListener(v -> {
            isSecondPlace = true;
            noOfStar ="2";
            feedback.setText("BAD");
            setText_change.setHint("Tell us more.....");
            star_1.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_two));
            star_2.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_two));
            if (isOnePressed||isThirdPlace ||isforthPlace || isfivePlace) {
                star_1.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_two));
                star_3.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_one));
                star_4.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_one));
                star_5.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_one));

                isOnePressed=true;
                isThirdPlace = false;
                isforthPlace = false;
                isfivePlace=false;
            }
        });
        star_3.setOnClickListener(v -> {
            isThirdPlace = true;
            noOfStar ="3";
            feedback.setText("AVERAGE");
            setText_change.setHint("Tell us more suggestions.....");
            star_1.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_four));
            star_2.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_four));
            star_3.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_four));
            if (isOnePressed||isSecondPlace ||isforthPlace || isfivePlace) {
                star_1.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_four));
                star_2.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_four));
                star_4.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_one));
                star_5.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_one));

                isOnePressed=false;
                isSecondPlace = false;
                isforthPlace = false;
                isfivePlace=false;
            }

        });
        star_4.setOnClickListener(v -> {
            isforthPlace = true;
            noOfStar ="4";
            feedback.setText("GOOD");
            setText_change.setHint("Tell us What you liked.....");
            star_1.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_four));
            star_2.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_four));
            star_3.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_four));
            star_4.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_four));

            if (isOnePressed||isSecondPlace || isThirdPlace ||isfivePlace) {
                star_1.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_four));
                star_2.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_four));
                star_3.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_four));
                star_5.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_one));

                isOnePressed=false;
                isSecondPlace = false;
                isThirdPlace = false;
                isfivePlace=false;
            }
        });
        star_5.setOnClickListener(v -> {
            isfivePlace=true;
            noOfStar ="5";
            feedback.setText("LOVE IT!");
            setText_change.setHint("Tell us What you liked.....");
            star_1.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_three));
            star_2.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_three));
            star_3.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_three));
            star_4.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_three));
            star_5.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_three));

            if (isOnePressed||isSecondPlace || isThirdPlace ||isforthPlace ) {
                star_1.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_three));
                star_2.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_three));
                star_3.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_three));
                star_4.setImageDrawable(ContextCompat.getDrawable(FeedbackActivity2.this, R.drawable.ic_star_three));

                isOnePressed=false;
                isSecondPlace = false;
                isThirdPlace = false;
                isforthPlace = false;
            }

        });
        if (Lazy.haveNetworkConnection(mContext))
        {

        }else {
            networkConnetion3(mContext);
        }

    }

    boolean AddFeedback() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_FEEDBACK,
                response -> {//"http://app.ssspltd.com/apipltd/AddFeedback" old url
                    Log.e("Data", ADD_FEEDBACK + " ====== " +  response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("ResponseStatus") == true) {
                            Toast.makeText(mContext, "Thanks for the feedback..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(mContext, MainActivity.class));
                            finish();

                        } else {
                            AlertUtil.responseElse(mContext, "AddFeedback ", jsonObject.optString("ResponseMessage") + "");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertUtil.responseExecption(mContext, "AddFeedback ", e.toString());
                    }
                }, error ->
            AlertUtil.responseError(mContext, "AddFeedback ", error.toString()))
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob2 = SharedPref.read(SharedPref.USERMOBILE, "");
                String str = "{\"MOBILENO\":\"" + mob2 + "\",\"ACCOUNTID\":\"" + SharedPref.read(SharedPref.PARTY_CODE,"") + "\",\"STAR\":\"" + noOfStar + "\",\"REMARKS\":\"" + setText_change.getText().toString() + "\",\"Device\":\"" + "Android" +"\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME,"") + "\"}";
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                80000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void  networkConnetion3(Context mContext) {
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
        cross.setOnClickListener(v -> alertDialog.dismiss());
        try_button.setOnClickListener(view -> {
            if (Lazy.haveNetworkConnection(mContext))
            {
                alertDialog.dismiss();
            }else {
                networkConnetion3(mContext);
            }

        });
        alertDialog.show();
    }
}