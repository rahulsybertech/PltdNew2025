package com.syber.ssspltd.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.syber.ssspltd.R;

public class AppropriateTypeActivity extends AppCompatActivity{
    RelativeLayout rlLogin;
    String showImg="";
    ImageView show_img;
    private String user_type = null;
    TextView login;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appropriate_type);
        rlLogin = findViewById(R.id.rl_login);
        login = findViewById(R.id.login);
        login.setOnClickListener(v -> {
            if(user_type != null){
                startActivity(new Intent(context,LoginPage.class)
                .putExtra("user_type",user_type));
            }else {
                Toast.makeText(context, "Please Select User Type", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.new_user:
                if (checked)
                    user_type ="new";
                    break;
            case R.id.existing_customer:
                if (checked)
                    user_type = "";
                    break;
        }
    }
}