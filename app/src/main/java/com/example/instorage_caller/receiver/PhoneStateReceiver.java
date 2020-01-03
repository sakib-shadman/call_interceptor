package com.example.instorage_caller.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PhoneStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        try {

            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            System.out.println("Receiver start");
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                String incomingNumber = "";
                incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                /*TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                telephony.listen(new PhoneStateListener(){
                    @Override
                    public void onCallStateChanged(int state, String incomingNumber) {
                        super.onCallStateChanged(state, incomingNumber);
                        System.out.println("incomingNumber : "+incomingNumber);
                        incomingNumber = incomingNumber + incomingNumber;
                        Toast.makeText(context,"Ringing State Number is -"+incomingNumber,Toast.LENGTH_SHORT).show();
                    }
                }, PhoneStateListener.LISTEN_CALL_STATE);*/

                showPopUp(context);
                Toast.makeText(context,"Ringing State Number is -"+incomingNumber,Toast.LENGTH_SHORT).show();

            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
                Toast.makeText(context,"Received State",Toast.LENGTH_SHORT).show();
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                Toast.makeText(context,"Idle State",Toast.LENGTH_SHORT).show();
            }
           // Toast.makeText(context," Receiver start ", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void showPopUp(Context context){

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

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
    }
}
