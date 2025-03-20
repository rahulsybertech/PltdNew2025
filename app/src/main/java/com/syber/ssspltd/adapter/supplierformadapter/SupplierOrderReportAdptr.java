package com.syber.ssspltd.adapter.supplierformadapter;

import static com.syber.ssspltd.Constants.NewErpUrls.ORDER_BOOK_GENERATE_PDF;
import static com.syber.ssspltd.Constants.NewErpUrls.SAVE_ORDER;
import static com.syber.ssspltd.Constants.NewErpUrls.UPDATE_ORDER_STATUS;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.syber.ssspltd.Interface.RefreshOrderReport;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.MyProgress;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.StringUtils;
import com.syber.ssspltd.Utils.Util;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.activitys.ViewPDFActivity;
import com.syber.ssspltd.activitys.supplierorderform.OrderImageActivity;
import com.syber.ssspltd.response.SupplierOrderReport.OrderDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SupplierOrderReportAdptr extends RecyclerView.Adapter<SupplierOrderReportAdptr.SupplierViewHolder> {
    private Context mContext;
    private List<OrderDetail> detailList;
    private RefreshOrderReport refreshOrderReport;

    public SupplierOrderReportAdptr(Context mContext, List<OrderDetail> detailList, RefreshOrderReport refreshOrderReport) {
        this.mContext = mContext;
        this.detailList = detailList;
        this.refreshOrderReport = refreshOrderReport;
    }

    @Override
    public SupplierViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplier_order_report_list, parent, false);
        return new SupplierViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SupplierViewHolder holder, final int position) {

        final OrderDetail datum = detailList.get(position);
        holder.orderNo.setText(datum.getOrderNo());
        holder.suplier.setText(datum.getSupplierName());
        holder.qty.setText(datum.getQty() + "");
        holder.oDate.setText(datum.getOrderDate());
        holder.saleParty.setText(datum.getSaleParty());
        holder.item.setText(datum.getItemName());
        holder.pcsType.setText(datum.getPcsType());
        holder.amt.setText(Lazy.amountFormat(datum.getAmount() + ""));
        holder.subParty_penOrder.setText(datum.getSubParty());
        if (SharedPref.read(SharedPref.DASHBOARD_TYPE, "").equalsIgnoreCase("Supplier")) {
            holder.ll_sup.setVisibility(View.GONE);
            holder.ll_sale.setVisibility(View.VISIBLE);
        } else if (SharedPref.read(SharedPref.DASHBOARD_TYPE, "").equalsIgnoreCase("Customer")) {
            holder.ll_sup.setVisibility(View.VISIBLE);
            holder.ll_sale.setVisibility(View.GONE);
        }
        if (SharedPref.read(SharedPref.DASHBOARD_TYPE, "").equalsIgnoreCase("Supplier")) {
            if (datum.getOrderStatus().equalsIgnoreCase("Confirm")) {
                holder.cancel.setVisibility(View.INVISIBLE);
                holder.confirm.setVisibility(View.INVISIBLE);
            } else if (datum.getOrderStatus().equalsIgnoreCase("Approval Pending")) {
                holder.cancel.setVisibility(View.VISIBLE);
                holder.confirm.setVisibility(View.INVISIBLE);
            } else if (datum.getOrderStatus().equalsIgnoreCase("Hold")) {
                holder.cancel.setVisibility(View.INVISIBLE);
                holder.confirm.setVisibility(View.INVISIBLE);
            }
        } else if (SharedPref.read(SharedPref.DASHBOARD_TYPE, "").equalsIgnoreCase("Customer"))
            if (datum.getOrderStatus().equalsIgnoreCase("Confirm")) {
                holder.cancel.setVisibility(View.INVISIBLE);
                holder.confirm.setVisibility(View.INVISIBLE);
            } else if (datum.getOrderStatus().equalsIgnoreCase("Approval Pending")) {
                holder.cancel.setVisibility(View.VISIBLE);
                holder.confirm.setVisibility(View.VISIBLE);
            } else if (datum.getOrderStatus().equalsIgnoreCase("Hold")) {
                holder.cancel.setVisibility(View.INVISIBLE);
                holder.confirm.setVisibility(View.INVISIBLE);
            }


        if (datum.getImageList() != null && datum.getImageList().isEmpty()) {
            holder.viewImage.setVisibility(View.INVISIBLE);
        } else {
            holder.viewImage.setVisibility(View.VISIBLE);
        }
        holder.cancel.setOnClickListener(v -> {
            checkDialog("Cancel", "Do you want to cancel this order?", "CANCEL", datum.getSaleParty(), datum.getOrderNo());
        });
        holder.confirm.setOnClickListener(v -> {
            checkDialog("Confirm", "Do you want to confirm this order?", "PENDING", datum.getSaleParty(), datum.getOrderNo());
//            SendData(datum.getSaleParty(), datum.getOrderNo(), "PENDING");
        });


        holder.orderNo.setOnClickListener(v -> {

            if (!datum.getPdfPath().isEmpty()) {
                boolean isPdf = StringUtils.containsPdf(datum.getPdfPath());
                if(isPdf){
                    mContext.startActivity(new Intent(mContext, ViewPDFActivity.class)
                            .putExtra("pdfUrl", datum.getPdfPath()));
                }else {
                    Toast.makeText(mContext, "PDF File Not Available", Toast.LENGTH_SHORT).show();
                }

            } else {
                generatePdf( datum.getRecordId());
            //    Toast.makeText(mContext, "PDF File Not Available", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return detailList.size();
    }

    private void checkDialog(String titleText, String discText, String orderStatus, String acountId, String orderNo) {
        Dialog sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.confirmation1_dialog);
        sDialog.setCancelable(false);
        Window window = sDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        sDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        sDialog.setCancelable(true);
        TextView title, disc, no, yes;
        ImageView cancel;
        title = sDialog.findViewById(R.id.title);
        disc = sDialog.findViewById(R.id.disc);
        no = sDialog.findViewById(R.id.no);
        yes = sDialog.findViewById(R.id.yes);
        cancel = sDialog.findViewById(R.id.cancel);
        title.setBackgroundResource(R.color.warning_text);
        title.setText(titleText);
        disc.setText(discText);
        cancel.setOnClickListener(v -> {
            sDialog.dismiss();
        });
        no.setOnClickListener(v -> {
            sDialog.dismiss();
        });
        yes.setOnClickListener(v -> {
            SendData(acountId, orderNo, orderStatus);
            sDialog.dismiss();
        });
        sDialog.show();
    }

    private void SendData(final String acountId, final String orderno, final String orderStatus) {
        final MyProgress progress = new MyProgress(mContext);
        progress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_ORDER_STATUS, response -> {
            Log.e("Data", response);
            progress.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("ResponseCode") == 200) {
                    AlertUtil.responseSuccess(mContext, jsonObject.getString("ResponseMessage") + "");
                    refreshOrderReport.onOrderRefresh();
                } else if (jsonObject.getInt("ResponseCode") == 204) {
                    AlertUtil.responseElse(mContext, "", jsonObject.getString("ResponseMessage") + "");
                } else if (jsonObject.getInt("ResponseCode") == 400) {
                    checkDialog("Hold!", jsonObject.getString("ResponseMessage") + "", "HOLD", acountId, orderno);
                    refreshOrderReport.onOrderRefresh();
                } else {
                    AlertUtil.responseElse(mContext, "", jsonObject.getString("ResponseMessage") + "");
                }

            } catch (JSONException e) {
                AlertUtil.responseError(mContext, "ChangeOrderStatus ", e + "");
                e.printStackTrace();
            }
        }, error -> {
            try {
                Constants.convertByteToString(mContext, "ChangeOrderStatus", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            progress.cancel();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"AccountID\":\"" + acountId + "\"" +
                        ",\"OrderNo\":\"" + orderno + "\"" +
                        ",\"OrderStatus\":\"" + orderStatus + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        RetryPolicy retryPolicy = new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void generatePdf(final String orderID) {
        final MyProgress progress = new MyProgress(mContext);
        progress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ORDER_BOOK_GENERATE_PDF, response -> {
            Log.e("Data", response);
            progress.dismiss();
         /*   {
                "data": "https://images.ssspltd.com/SyberERP/IMAGES/43029624-ea4a-434c-9a14-d7da24840bad/OrderPdf_162620701.pdf",
                    "message": "Order Book Pdf Generated Successfully",
                    "success": true,
                    "error": false,
                    "responsecode": "200"
            }*/
            try {
                JSONObject jsonObject = new JSONObject(response);

                // Ensure the key matches the actual JSON structure (use "responsecode" instead of "ResponseCode")
                if (jsonObject.optInt("responsecode") == 200) {

                    if (jsonObject.optBoolean("success")) {
                        // Check if the "data" contains a PDF URL
                        boolean isPdf = jsonObject.optString("data").toLowerCase().endsWith(".pdf");

                        if (isPdf) {
                            // Open PDF in ViewPDFActivity
                            mContext.startActivity(new Intent(mContext, ViewPDFActivity.class)
                                    .putExtra("pdfUrl", jsonObject.optString("data")));
                        } else {
                            Toast.makeText(mContext, "PDF File Not Available", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Failed to Generate PDF", Toast.LENGTH_SHORT).show();
                    }

                } else if (jsonObject.optInt("responsecode") == 204) {
                    Toast.makeText(mContext, "No Content Available", Toast.LENGTH_SHORT).show();

                } else if (jsonObject.optInt("responsecode") == 400) {
                    refreshOrderReport.onOrderRefresh();

                } else {
                    Toast.makeText(mContext, "Unknown Error: " + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                AlertUtil.responseError(mContext, "ChangeOrderStatus", e.toString());
                e.printStackTrace();
            }

        }, error -> {
            try {
                Constants.convertByteToString(mContext, "ChangeOrderStatus", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            progress.cancel();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                String jsonString = "";
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("recordId",orderID );
                    jsonString = jsonObject.toString();

                }catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("TaG", "Request " + ORDER_BOOK_GENERATE_PDF + "---> " + jsonString);
                Util.getInstance().logLargeString("TaG", "Request " + ORDER_BOOK_GENERATE_PDF + "---> " + jsonString);

                return jsonString.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }
        };
        RetryPolicy retryPolicy = new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    class SupplierViewHolder extends RecyclerView.ViewHolder {

        TextView call, call_, orderNo, suplier, oDate, item, qty, saleParty, amt, cancel, email, confirm, pcsType, viewImage, subParty_penOrder;
        LinearLayout ll_sale, ll_sup;

        public SupplierViewHolder(View itemView) {
            super(itemView);

            orderNo = itemView.findViewById(R.id.order_no);
//          call = itemView.findViewById(R.id.call);
            call_ = itemView.findViewById(R.id.call_);
            viewImage = itemView.findViewById(R.id.view_img);
            confirm = itemView.findViewById(R.id.confirm);
            oDate = itemView.findViewById(R.id.order_date);
            suplier = itemView.findViewById(R.id.suplier);
            saleParty = itemView.findViewById(R.id.sale_party);
            item = itemView.findViewById(R.id.item_no);
            qty = itemView.findViewById(R.id.qty);
            amt = itemView.findViewById(R.id.amt_no);
            ll_sup = itemView.findViewById(R.id.ll_sup);
            ll_sale = itemView.findViewById(R.id.ll_sale);
//            email = itemView.findViewById(R.id.email_status);
            cancel = itemView.findViewById(R.id.cancel);
            pcsType = itemView.findViewById(R.id.pcs_type);
            subParty_penOrder = itemView.findViewById(R.id.subParty_penOrder);
            viewImage.setOnClickListener(v -> {
                OrderDetail datum = detailList.get(getAbsoluteAdapterPosition());
                Intent i = new Intent(mContext, OrderImageActivity.class);
                i.putExtra("img", datum);
                Log.e("imgList", new Gson().toJson(datum));
                mContext.startActivity(i);
            });
        }

    }


}
