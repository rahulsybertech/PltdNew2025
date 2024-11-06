package com.syber.ssspltd.NewFilterAdapter.LedgerAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.syber.ssspltd.Interface.OnCheckChange;
import com.syber.ssspltd.NewFilterResponse.EntryType;
import com.syber.ssspltd.NewFilterResponse.FilterType;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.FilterCallback;
import com.syber.ssspltd.activitys.LedgerActivity;

import java.util.List;
import java.util.stream.Collectors;

public class EntryTypeAdapter extends RecyclerView.Adapter<EntryTypeAdapter.MyViewHolder> {
    private Context mContext;
    private List<EntryType> filterListDetails;
    private FilterCallback filterCallback;
    public static int lastSelectedPosition= -1;
     private OnCheckChange onCheckChange;


    public EntryTypeAdapter(Context mContext, List<EntryType> detailList, FilterCallback filterCallback,OnCheckChange onCheckChange) {
        this.mContext = mContext;
        this.filterListDetails = detailList;
        this.filterCallback = filterCallback;
        this.onCheckChange=onCheckChange;
    }

    @Override
    public EntryTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_recyler, parent, false);
        return new EntryTypeAdapter.MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(EntryTypeAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final EntryType datum = filterListDetails.get(position);
        holder.filter.setText(datum.getEntryTypeName().equals("") ? "Without Branch" : datum.getEntryTypeName());
//        holder.filter.setChecked(lastSelectedPosition == position);
        Log.e("lastSelectedPosition",lastSelectedPosition+"");
        /*if (lastSelectedPosition== position ){
            datum.setSelected(true);
        }else {
            datum.setSelected(false);
        }*/
        if (datum.isSelected()) {
            holder.filter.setChecked(true);
        } else {
            holder.filter.setChecked(false);
        }
        holder.filter.setTag(position);

//
        holder.filter.setOnClickListener(v -> {
          Integer pos = (Integer) holder.filter.getTag();
            EntryType productDetails1 = filterListDetails.get(pos);
            if (productDetails1.isSelected()) {
                //lastSelectedPosition = -1;
                productDetails1.setSelected(false);
                holder.filter.setChecked(false);
                holder.filter.setTag(position);
                try{
                    List<EntryType> sis = filterListDetails.stream().filter(EntryType :: isSelected).collect(Collectors.toList());
                    Log.i("TaG","1111========>" + sis);
                    LedgerActivity.count_entry.setText(sis.size() + "");
                   /* if(sis.isEmpty()) {
                        LedgerActivity.count_entry.setVisibility(View.GONE);
                    } else {
                        LedgerActivity.count_entry.setVisibility(View.VISIBLE);
                    }*/
                } catch(Exception e) {
                    e.printStackTrace();
                }

//                Log.e("ss",new Gson().toJson(sis));
             //   onCheckChange.onCheckChangeReferesh();
            } else {
//                lastSelectedPosition = position;
                productDetails1.setSelected(true);
                holder.filter.setChecked(true);
                holder.filter.setTag(position);
                try{
                    List<EntryType> sis = filterListDetails.stream().filter(EntryType :: isSelected).collect(Collectors.toList());
                    Log.i("TaG","22222========>" + sis);
                    LedgerActivity.count_entry.setText(sis.size() + "");
                    /*if(sis.isEmpty()) {
                        LedgerActivity.count_entry.setVisibility(View.GONE);
                    } else {
                        LedgerActivity.count_entry.setVisibility(View.VISIBLE);
                    }*/
                }catch (Exception e) {
                    e.printStackTrace();
                }
//                Log.e("sis",new Gson().toJson(sis));
             //   onCheckChange.onCheckChangeReferesh();
               // LedgerActivity.tick_filter = "1";
            }
            filterCallback.filterChanged(FilterType.ENTRY);
            notifyDataSetChanged();

        });


    }


    @Override
    public int getItemCount() {
        return filterListDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox filter;

        public MyViewHolder(View itemView) {
            super(itemView);

            filter = itemView.findViewById(R.id.filter);
//            filter.setOnClickListener(v->{
//                lastSelectedPosition = getAbsoluteAdapterPosition();
//                notifyDataSetChanged();
//            });

        }
    }

}
