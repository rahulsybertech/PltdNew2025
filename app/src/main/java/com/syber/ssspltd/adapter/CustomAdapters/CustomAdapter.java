package com.syber.ssspltd.adapter.CustomAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.activitys.ChooseCategries;
import com.syber.ssspltd.activitys.MainActivity;
import com.syber.ssspltd.response.ModelClass.RowItem;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context mContext;
    private List<RowItem> rowItem;

    public CustomAdapter(Context mContext, List<RowItem> detailList) {
        this.mContext = mContext;
        this.rowItem = detailList;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitems_layout, parent, false);
        return new CustomAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        final RowItem datum = rowItem.get(position);
        holder.textViewName.setText(datum.getmName());
        holder.itemView.setOnClickListener(v -> {
            ((MainActivity) mContext).setListRecyler(rowItem.get(position));
            ChooseCategries.partyCode = rowItem.get(position).getmPartyCode();

//by rahul
            SharedPref.write(SharedPref.PARTY_CODE,datum.getmPartyCode());
            SharedPref.write(SharedPref.ADMIN_ID, datum.getID());
        });

        if (datum.getmUserType().equals("5")) {
            holder.textViewName.setVisibility(View.GONE);
        } else {
            holder.textViewName.setVisibility(View.VISIBLE);
        }

//        if (rowItem != null) {
//            holder.textViewName.setText(datum.getmName());
//        }


    }


    @Override
    public int getItemCount() {
        return rowItem.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.listName);
        }
    }

}
