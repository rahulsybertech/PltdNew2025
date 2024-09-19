package com.syber.ssspltd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.response.WhysssRespons.WhySSS_Response;

import java.util.List;

public class WhySSSAdapter extends RecyclerView.Adapter<WhySSSAdapter.MyViewHolder> {

    Context context;
    private List<WhySSS_Response> whySSS_responseList;

    public WhySSSAdapter(Context context, List<WhySSS_Response> whySSS_responseList) {
        this.whySSS_responseList = whySSS_responseList;
        this.context = context;
    }
    @Override
    public WhySSSAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.why_sss_list, parent, false);
        return new WhySSSAdapter.MyViewHolder(itemView);

    }

    @Override
    public int getItemCount() {
        return whySSS_responseList.size();

    }

    @Override
    public void onBindViewHolder(@NonNull final WhySSSAdapter.MyViewHolder holder, final int position) {
        final WhySSS_Response lists;
        lists = whySSS_responseList.get(position);
        holder.whyss_name.setText(lists.getName());
        holder.sn_no.setText(lists.getSN_number());
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView sn_no,whyss_name;


        public MyViewHolder(View view) {
            super(view);
            sn_no = view.findViewById(R.id.sn_no);
            whyss_name = view.findViewById(R.id.whyss_name);

        }
    }
}
