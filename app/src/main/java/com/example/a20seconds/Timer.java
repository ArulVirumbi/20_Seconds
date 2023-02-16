package com.example.a20seconds;

import android.content.Context;
import android.os.Handler;

public class Timer{
    private Context context;
    boolean timerRun;
    final Handler handler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            Window window = new Window(context);
            window.open();
            handler.postDelayed(this, 55000);  // total delay time (windowON + windowOFF)
        }
    };
   /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
//    public TimerService() {
//        super();
//    }

//    @Override
//    public void onCreate(){
//        super.onCreate();
//
//    }

//    @Override
//    protected void onHandleIntent(Intent startIntent) {
//        // Gets data from the incoming Intent
//        String dataString = startIntent.getDataString();
//        timer = startIntent.getBooleanExtra("timer",false);
//        Toast.makeText(this,"Timer",Toast.LENGTH_SHORT).show();
//    }
}
