package com.syber.ssspltd.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.activitys.registered_msg;
import com.syber.ssspltd.response.UsersTypeResponse.UsersTypeListResult;

import java.util.List;

public class UsersTypeListAdapter extends RecyclerView.Adapter<UsersTypeListAdapter.MyViewHolder>  {

    private Context mContext;
    private List<UsersTypeListResult> TypeListDetails;

    int lastSelectedPosition= -1;


    public UsersTypeListAdapter(Context mContext, List<UsersTypeListResult> detailList) {
        this.mContext = mContext;
        this.TypeListDetails = detailList;
    }

    @Override
    public UsersTypeListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_list_recylerview, parent, false);
        return new UsersTypeListAdapter.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final UsersTypeListAdapter.MyViewHolder holder, final int position) {

        final UsersTypeListResult datum = TypeListDetails.get(position);
        holder.UserTypeList.setText(datum.getName());
        holder.UserTypeList.setChecked(lastSelectedPosition == position);


        datum.setSelected(true);
        SharedPref.init(mContext);

    }


    @Override
    public int getItemCount()
    {
        return TypeListDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox UserTypeList;
        LinearLayout ll_sup,ll_sale;
        ImageView call;
        TextView dr_amt,bal_name;

        public MyViewHolder(View itemView) {
            super(itemView);
//            registered_msg.typePos = "";
            UserTypeList = itemView.findViewById(R.id.UserTypeList);
            UserTypeList.setOnClickListener(v -> {
                lastSelectedPosition = getAbsoluteAdapterPosition();
                Bundle bundle = new Bundle();
                bundle.putInt("position", lastSelectedPosition);
                registered_msg.pos=getAbsoluteAdapterPosition();
                SharedPref.write(SharedPref.SELECTED,TypeListDetails.get(getAbsoluteAdapterPosition()).getName());
                registered_msg.typePos = TypeListDetails.get(getAbsoluteAdapterPosition()).getUserType();
               // Toast.makeText(mContext, registered_msg.typePos+"op", Toast.LENGTH_SHORT).show();
                registered_msg.praty_code = TypeListDetails.get(getAbsoluteAdapterPosition()).getPartyCode();
                notifyDataSetChanged();

            });
        }
    }
}
