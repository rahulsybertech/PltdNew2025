package com.syber.ssspltd.adapter.brandCategroy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.response.brand.BrandCategoryA;

import java.util.List;

public class BrandCategroyAdapter extends RecyclerView.Adapter<BrandCategroyAdapter.MyViewHolder>{

    private Context mContext;
    private List<BrandCategoryA> brand_CategoryADetails;
    public BrandCategroyAdapter(Context mContext, List<BrandCategoryA> detailList) {
        this.mContext = mContext;
        this.brand_CategoryADetails = detailList;
    }


    public BrandCategroyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.categroy_list, parent, false);
        return new BrandCategroyAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BrandCategroyAdapter.MyViewHolder holder, final int position) {

        final BrandCategoryA datum = brand_CategoryADetails.get(position);
        holder.nameCategroy.setText(datum.getBrand_Category());

    }


    @Override
    public int getItemCount() {
        return brand_CategoryADetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameCategroy;


        public MyViewHolder(View itemView) {
            super(itemView);

            nameCategroy = itemView.findViewById(R.id.bandCategoryName);

        }

    }

}
