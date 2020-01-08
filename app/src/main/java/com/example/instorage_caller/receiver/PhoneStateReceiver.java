package com.example.instorage_caller.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.instorage_caller.R;
import com.example.instorage_caller.activity.PopUpActivity;
import com.example.instorage_caller.roomdb.AppDatabase;
import com.example.instorage_caller.roomdb.CustomerInfo;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.WINDOW_SERVICE;

public class PhoneStateReceiver extends BroadcastReceiver {

    private CustomerInfo customerInfo;
    private boolean isAlreadyShown = false;
    String phoneNumber = "";
    private AppDatabase appDatabase;
    private Context mContext;
    private boolean isAlreadyFetched = false;
    WindowManager wm;
    View myView;
    private int count = 0;
    @Override
    public void onReceive(Context context, Intent intent) {

        try {

            appDatabase = AppDatabase.getDatabase(context);
            mContext = context;
            count = count+1;
            System.out.println("Count------>"+count);

            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            System.out.println("Receiver start");
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){

                Toast.makeText(context,"Ringing State Number is -",Toast.LENGTH_SHORT).show();


                TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

                telephony.listen(new PhoneStateListener(){
                    @Override
                    public void onCallStateChanged(int state, String incomingNumber) {
                        super.onCallStateChanged(state, incomingNumber);
                        //System.out.println("incomingNumber : "+incomingNumber);
                        phoneNumber = incomingNumber;
                        if(!phoneNumber.isEmpty() && !isAlreadyFetched){
                            new getCustomer().execute();

                            /*Intent i = new Intent(context,PopUpActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);*/
                        }
                        System.out.println("incomingNumber inside listener: "+phoneNumber);
                    }

                }, PhoneStateListener.LISTEN_CALL_STATE);
                System.out.println("incomingNumber outside listener: "+phoneNumber);
                /*if(!isAlreadyShown){
                    isAlreadyShown = true;
                    showPopUp(context);
                }*/




            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
                isAlreadyShown = false;
                Toast.makeText(context,"Received State",Toast.LENGTH_SHORT).show();
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                isAlreadyShown = false;
                Toast.makeText(context,"Idle State",Toast.LENGTH_SHORT).show();
                System.out.println("idle state");
                removePopUp(wm,myView);
            }
           // Toast.makeText(context," Receiver start ", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /*private void showPopUp(Context context){

        WindowManager wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);

        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.format = PixelFormat.TRANSLUCENT;

        params.gravity = Gravity.TOP;

        LinearLayout ly = new LinearLayout(context);
        ly.setBackgroundColor(Color.RED);
        ly.setOrientation(LinearLayout.VERTICAL);

        wm.addView(ly, params);
    }*/

    private void showPopUp(Context context, CustomerInfo customerInfo){

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
        wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        myView = inflater.inflate(R.layout.caller_dialog, null);
        Button button = myView.findViewById(R.id.close_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wm.removeView(myView);
            }
        });

        TextView customerName = myView.findViewById(R.id.txtCustomerName);
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


        // Add layout to window manager
        wm.addView(myView, params);
    }

    private void removePopUp(WindowManager wm,View view){

        if(wm != null && view != null){
            wm.removeView(view);
            Log.i("Remove view","View removed");
        }

    }


    private class getCustomer extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {


            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {

            try {
                showPopUp(mContext,customerInfo);
                super.onPostExecute(result);
            } catch (Exception ex) {

            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                customerInfo = appDatabase.customerDao().getCustomerByPhoneNo(phoneNumber);
                isAlreadyFetched = true;
                return null;
            } catch (Exception ex) {
                return null;
            }
        }

    }
}
