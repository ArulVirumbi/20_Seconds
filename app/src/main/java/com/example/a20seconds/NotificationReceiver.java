package com.example.a20seconds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    private Context context;
//    Boolean timer;
    static Boolean timerStatus=false;
    final Handler handler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if(timerStatus){
                Window window = new Window(context);
                window.open();
                handler.postDelayed(this, 55000);
            } // total delay time (windowON + windowOFF)
        }
    };


    @Override
    public void onReceive(Context context, Intent intent){
        this.context = context;
        if(!timerStatus) {
            timerStatus=true;
            Toast.makeText(context, "Timer Started", Toast.LENGTH_SHORT).show();
            startTimer(context,true);
        }else{
            timerStatus=false;
            Toast.makeText(context, "Timer Stopped", Toast.LENGTH_SHORT).show();
            stopTimer();
        }

    }

    protected void startTimer(Context context, boolean check){
//        while (check){
//            new CountDownTimer(2000, 1000) {
//
//                @Override
//                public void onTick(long millisUntilFinished) {
//
//                }
//
//                @Override
//                public void onFinish() {
//                    Window window = new Window(context);
//                    window.open();
//                }
//            }.start();
//        }

        handler.postDelayed(timerRunnable, 2000);   // Starting time

    }

    protected void stopTimer(){
        handler.removeCallbacks(timerRunnable);
    }
}
