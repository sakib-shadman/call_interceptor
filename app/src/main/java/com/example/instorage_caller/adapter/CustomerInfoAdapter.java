package com.example.instorage_caller.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instorage_caller.R;
import com.example.instorage_caller.roomdb.CustomerInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerInfoAdapter extends RecyclerView.Adapter<CustomerInfoAdapter.CustomerInfoAdapterViewHolder> {

    private final Context context;
    private List<CustomerInfo> mDataSet;

    public CustomerInfoAdapter(List<CustomerInfo> mDataSet, Context context) {
        this.mDataSet = mDataSet;
        this.context = context;
    }


    @Override
    public CustomerInfoAdapterViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer_info, parent, false);
        return new CustomerInfoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerInfoAdapterViewHolder holder, int position) {
        CustomerInfo item = mDataSet.get(position);
        holder.bindData(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDataSet == null) {
            return 0;
        }
        return mDataSet.size();
    }

    public void updateDataSet(List<CustomerInfo> newDataSet) {
        try {
            mDataSet = newDataSet;
            notifyDataSetChanged();
        } catch (Exception ex) {

        }
    }



    public class CustomerInfoAdapterViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txtCustomerInfo)
        TextView txtCustomerInfo;
        @BindView(R.id.txtCustomerAddress)
        TextView txtCustomerAddress;
        @BindView(R.id.txtCustomerEmail)
        TextView txtCustomerEmail;
        @BindView(R.id.txtCustomerPhone)
        TextView txtCustomerPhone;
        @BindView(R.id.txtCustomerName)
        TextView txtCustomerName;
        @BindView(R.id.txtCustomerActive)
        TextView txtCustomerActive;
        @BindView(R.id.imgStatus)
        ImageView imgStatus;

        public CustomerInfoAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CustomerInfo item) {

            try{
                if(item != null){
                    if(item.getName() != null){
                        txtCustomerName.setText(item.getName());
                    }

                    if(item.getPhone() != null){
                        txtCustomerPhone.setText(item.getPhone());
                    }

                    if(item.getEmail() !=null){
                        txtCustomerEmail.setText(item.getEmail());
                    }

                    if(item.getAddress() != null){
                        txtCustomerAddress.setText(item.getAddress());
                    }

                    if(item.getStatus() != null){
                        txtCustomerActive.setText(item.getStatus());
                        if(item.getStatus().equalsIgnoreCase("active")){
                            txtCustomerActive.setTextColor(ContextCompat.getColor(context,R.color.color_active));
                            imgStatus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_active));
                        } else {
                            txtCustomerActive.setTextColor(ContextCompat.getColor(context,R.color.color_inactive));
                            imgStatus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_inactive));
                        }
                    } else {
                        txtCustomerActive.setText("No Info");
                        txtCustomerActive.setTextColor(ContextCompat.getColor(context,R.color.color_inactive));
                        imgStatus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_inactive));
                    }

                    if(item.getBooking() != null){
                        String info = item.getBooking().getStorage()+">"+item.getBooking().getFloorName()+">"+item.getBooking().getUnitName();
                        txtCustomerInfo.setText(info);
                    } else {
                        txtCustomerInfo.setText("No info available");
                    }
                }
            } catch (Exception ex){
                Log.i("Exception---->",ex.getMessage());
            }

        }


    }

}
