package com.syber.ssspltd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.response.UsersTypeResponse.UsersTypeListResult;

import java.util.List;

public class HomeNameAdapter extends RecyclerView.Adapter<HomeNameAdapter.MyViewHolder> {


    private Context mContext;
    private List<UsersTypeListResult> TypeListDetails;
//    int lastSelectedPosition= Integer.parseInt(SharedPref.read(SharedPref.DB,"-1"));


    public HomeNameAdapter(Context mContext, List<UsersTypeListResult> detailList) {
        this.mContext = mContext;
        this.TypeListDetails = detailList;
    }

    @Override
    public HomeNameAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_type_recyl, parent, false);
        return new HomeNameAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final HomeNameAdapter.MyViewHolder holder, final int position) {

        final UsersTypeListResult datum = TypeListDetails.get(position);
        holder.listName.setText(datum.getName());

        holder.listName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lastSelectedPosition = getAdapterPosition();


            }
        });
       // holder.listName.setChecked(lastSelectedPosition == position);

//          holder.UserTypeList.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                  if (SharedPref.read(SharedPref.DB_NAME,"").equals("5")){
//                      //mContext.startActivity(new Intent(mContext, ChooseCategries.class));
//                      lastSelectedPosition = holder.getAdapterPosition();
//                      registered_msg.pos = holder.getAdapterPosition()+"";
//                      Log.e("pos",holder.getAdapterPosition()+"");
//                    registered_msg.typePos = TypeListDetails.get(holder.getAdapterPosition()).getUserType();
////                      SharedPref.write(SharedPref.PARTY_CODE,TypeListDetails.get(holder.getAdapterPosition()).getPartyCode());
////                      SharedPref.write(SharedPref.CUM_NUM,TypeListDetails.get(holder.getAdapterPosition()).getSRNO());
//                      notifyDataSetChanged();
//                  }
//                  else {
//                     // mContext.startActivity(new Intent(mContext, MainActivity.class));
//                      lastSelectedPosition = holder.getAdapterPosition();
//                      registered_msg.pos = holder.getAdapterPosition()+"";
//                      Log.e("pos",holder.getAdapterPosition()+"");
//                      registered_msg.typePos = TypeListDetails.get(holder.getAdapterPosition()).getUserType();
//                      // reg.pos=holder.getAdapterPosition()+"";
//                      //.db_name = TypeListDetails.get( holder.getAdapterPosition()).getDATABASENAMES();
//                      notifyDataSetChanged();
//                  }
//              }
//          });
        //  SharedPref.init(mContext);

        if (datum.getUserType().equals("5")){
            holder.listName.setVisibility(View.GONE);
        }else {
            holder.listName.setVisibility(View.VISIBLE);
        }
//
//        holder.bal_name.setText(tt);

    }


    @Override
    public int getItemCount() {
        return TypeListDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        LinearLayout ll_sup,ll_sale;
        ImageView call;
        TextView listName;

        public MyViewHolder(View itemView) {
            super(itemView);

            listName = itemView.findViewById(R.id.listName);

        }
    }
}
