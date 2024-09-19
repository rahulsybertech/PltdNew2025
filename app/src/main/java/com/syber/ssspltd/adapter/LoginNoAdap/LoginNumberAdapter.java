package com.syber.ssspltd.adapter.LoginNoAdap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.LoginPage;
import com.syber.ssspltd.response.LoginNoResponse.AccountDetail;

import java.util.List;

public class LoginNumberAdapter extends RecyclerView.Adapter<LoginNumberAdapter.MyViewHolder> {
    private Context mContext;
    private List<AccountDetail> accountDetails;
    public static String mobile_number;
    public LoginNumberAdapter(Context mContext,List<AccountDetail> accountDetails)
    {
        this.mContext=mContext;
        this.accountDetails=accountDetails;

    }
    @NonNull
    @Override
    public LoginNumberAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.login_recyclerview,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LoginNumberAdapter.MyViewHolder holder, int position) {
        final AccountDetail datum=accountDetails.get(position);
        holder.login_name.setText(datum.getAccountName());
        holder.login_num.setText(datum.getMobileNo());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginPage)mContext).setLoginNo(accountDetails.get(position));
//                                SharedPref.write(SharedPref.USERMOBILE,datum.getMobileNo().toString());
//                                Log.e("login_sharedPref", datum.getMobileNo().toString());
//                                Toast.makeText(mContext, "OTP sent on mobile number", Toast.LENGTH_SHORT).show();
//                                mContext.startActivity(new Intent(mContext, OTPActivity.class)
//                                        .putExtra("reg_status", reg_status));
//                ((Activity)mContext).finish();
            }
        });


    }

    @Override
    public int getItemCount() {
        return accountDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView login_name,login_num;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            login_name=itemView.findViewById(R.id.login_name);
            login_num=itemView.findViewById(R.id.login_num);
        }

    }
}
