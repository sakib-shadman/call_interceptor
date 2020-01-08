package com.example.instorage_caller.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instorage_caller.R;

public class PopUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        showPopUp();
    }

    private void showPopUp(){
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                400,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER;
        WindowManager wm = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.caller_dialog, null);
        Button button = myView.findViewById(R.id.close_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wm.removeView(myView);
            }
        });

      /*  TextView customerName = myView.findViewById(R.id.txtCustomerName);
        TextView customerInfoView = myView.findViewById(R.id.txtCustomerInfo);
        TextView customerActive = myView.findViewById(R.id.txtCustomerActive);
        ImageView imgStatus = myView.findViewById(R.id.imgStatus);
        if(customerInfo != null){
            customerName.setText(customerInfo.getName());
            if(customerInfo.getStatus() != null){
                customerActive.setText(customerInfo.getStatus());
                if(customerInfo.getStatus().equalsIgnoreCase("active")){
                    customerActive.setTextColor(ContextCompat.getColor(context,R.color.color_active));
                    imgStatus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_active));
                } else {
                    customerActive.setTextColor(ContextCompat.getColor(context,R.color.color_inactive));
                    imgStatus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_inactive));
                }
            } else {
                customerActive.setText("Inactive");
                customerActive.setTextColor(ContextCompat.getColor(context,R.color.color_inactive));
                imgStatus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_inactive));
            }

            if(customerInfo.getBooking() != null){
                String info = customerInfo.getBooking().getStorage()+">"+customerInfo.getBooking().getFloorName()+">"+customerInfo.getBooking().getUnitName();
                customerInfoView.setText(info);
            } else {
                customerInfoView.setText("No info available");
            }
        } else {
            customerName.setText("Un-known");
            customerInfoView.setText("No info available");
        }
*/

        // Add layout to window manager
        wm.addView(myView, params);
    }
}
