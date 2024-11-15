package com.syber.ssspltd.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.activitys.MainActivity;
import com.syber.ssspltd.Interface.TopicClickListener;
import com.syber.ssspltd.response.FinanacialYearListRespon.FYearListResult;

import java.util.List;

public class FYearAdapter extends RecyclerView.Adapter<FYearAdapter.MyViewHolder> {

   public static String startYear,endYear;
    private Context mContext;
    private List<FYearListResult> fYearListDetails;
    private final TopicClickListener topicClickListener;
    private String db_name;
    int lastSelectedPosition= Integer.parseInt(SharedPref.read(SharedPref.default_db,"-1"));

    public FYearAdapter(Context mContext, List<FYearListResult> detailList, TopicClickListener topicClickListener) {
        this.mContext = mContext;
        this.fYearListDetails = detailList;
        this.topicClickListener = topicClickListener;
    }

    public FYearAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fyear_recy, parent, false);
        return new FYearAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FYearAdapter.MyViewHolder holder, final int position) { 

        final FYearListResult datum = fYearListDetails.get(position);
        holder.fyear_check.setText(datum.getFYEAR());
       if(lastSelectedPosition == -1) {
           String defaultFY = fYearListDetails.get(position).getmDEFAULTDB();
           holder.fyear_check.setChecked(defaultFY.equals(datum.getFYEAR()));
       } else {
           holder.fyear_check.setChecked(lastSelectedPosition == position);
       }
       /*if (SharedPref.read(SharedPref.selected_default_yr,"").equals("")) {
           MainActivity.def_db = position + "";
           MainActivity.set_year = fYearListDetails.get(position).getFYEAR();
           MainActivity.db_name = fYearListDetails.get(position).getDBNAME();
           MainActivity.selectedYr = fYearListDetails.get(position).getFYEAR();
           Log.e("year", fYearListDetails.get(position).getFYEAR());
           MainActivity.fy_StartDate = fYearListDetails.get(position).getmFY_StartDate();
           MainActivity.fy_EndDate = fYearListDetails.get(position).getmFY_EndDate();
       }
//        M ainActivity.fy_StartDate=fYearListDetails.get(position).getmFY_StartDate();
        MainActivity.fy_EndDate=fYearListDetails.get(position).getmFY_EndDate();
       if (datum.getmDEFAULTDB().equals("True")){
           holder.fyear_check.setChecked(true);
       }*/


    }


    @Override
    public int getItemCount() {
        return fYearListDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox fyear_check;


        public MyViewHolder(View itemView) {
            super(itemView);

            fyear_check = itemView.findViewById(R.id.fyear_check);
            fyear_check.setOnClickListener(v -> {
              //  if ()
//                    if (fyear_check.isChecked()){
//
//                    }
               lastSelectedPosition = getAdapterPosition();

                //MainActivity.pos = getAdapterPosition() + "";
               // SharedPref.write(SharedPref.DB_NAME, fYearListDetails.get(getAdapterPosition()).getDBNAME());
              //  MainActivity.DB_NAME =  fYearListDetails.get(getAdapterPosition()).getDBNAME();
              //  SharedPref.write(SharedPref.SET_YEAR, fYearListDetails.get(getAdapterPosition()).getFYEAR());
              //  SharedPref.write(SharedPref.selected_default_yr,  fYearListDetails.get(getAdapterPosition()).getFYEAR());
              //  SharedPref.write(SharedPref.FY_StartDate, fYearListDetails.get(getAdapterPosition()).getmFY_StartDate());
              //  SharedPref.write(SharedPref.FY_EndDate, fYearListDetails.get(getAdapterPosition()).getmFY_EndDate());
//                SharedPref.write(SharedPref.DB_NAME, fYearListDetails.get(getAdapterPosition()).getDBNAME());
//                SharedPref.write(SharedPref.SET_YEAR, fYearListDetails.get(getAdapterPosition()).getFYEAR());
//                SharedPref.write(SharedPref.selected_default_yr,  fYearListDetails.get(getAdapterPosition()).getFYEAR());
//                SharedPref.write(SharedPref.FY_StartDate, fYearListDetails.get(getAdapterPosition()).getmFY_StartDate());
//                SharedPref.write(SharedPref.FY_EndDate, fYearListDetails.get(getAdapterPosition()).getmFY_EndDate());

                MainActivity.def_db =  getAdapterPosition()+"";
                MainActivity.set_year =  fYearListDetails.get(getAdapterPosition()).getFYEAR();
                MainActivity.db_name = fYearListDetails.get(getAdapterPosition()).getDBNAME();
                MainActivity.selectedYr = fYearListDetails.get(getAdapterPosition()).getFYEAR();
                Log.e("getDBNAME",fYearListDetails.get(getAdapterPosition()).getDBNAME());
                MainActivity.fy_StartDate=fYearListDetails.get(getAdapterPosition()).getmFY_StartDate();
                MainActivity.fy_EndDate=fYearListDetails.get(getAdapterPosition()).getmFY_EndDate();
//                String FY_StartDate=fYearListDetails.get(getAdapterPosition()).getmFY_StartDate();
//                String FY_EndDate=fYearListDetails.get(getAdapterPosition()).getmFY_EndDate();
//                SharedPref.write(SharedPref.FY_StartDate,FY_StartDate);
//                SharedPref.write(SharedPref.FY_EndDate,FY_EndDate);

//                    String yy = fYearListDetails.get(getAdapterPosition()).getFYEAR();
//                    String[] split = yy.split("-");
//                    String firstSubString = split[0];
//                    String secondSubString = split[1];
//                    endYear = secondSubString;
//                    int yy_yy = Integer.parseInt(secondSubString) - 1;
//                    startYear = yy_yy+"";
//                    Log.e("startYear :::: endYear",startYear+"::::"+endYear);
                notifyDataSetChanged();
            });
        }
    }
}
