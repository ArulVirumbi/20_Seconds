package com.example.a20seconds;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import static android.content.Context.WINDOW_SERVICE;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a20seconds.adapter.MyAdapter;
//import com.example.a20seconds.adapter.PopupAdapter;
import com.example.a20seconds.adapter.TaskAdapter;
import com.example.a20seconds.bottomSheetFragment.CreateTaskBottomSheetFragment;
import com.example.a20seconds.database.DatabaseClient;
import com.example.a20seconds.model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Window {

    // declaring required variables
//    @BindView(R.id.taskPopupRecycler)
    RecyclerView taskRecycler;
//    PopupAdapter PopupAdapter;
    MyAdapter taskAdapter;
    List<Task> tasks = new ArrayList<>();
    private Context context;
    private View mView;
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;
    private LayoutInflater layoutInflater;
    private int i=20;
    TextView countdown;
    ProgressBar progressBar;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.popup_window);
//    }

    public Window(Context context){
        this.context=context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // set the layout parameters of the window
            mParams = new WindowManager.LayoutParams(
                    // Shrink the window to wrap the content rather
                    // than filling the screen
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
                    // Display it on top of other application windows
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    // Don't let it grab the input focus
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, // change to flag_not_focusable to make background clickable
                    // Make the underlying application window visible
                    // through any transparent parts
                    PixelFormat.TRANSLUCENT);

        }

//        binding = FullscreenWindowBinding.inflate(getLayoutInflater());
        // getting a LayoutInflater
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflating the view with the custom layout we created
        mView = layoutInflater.inflate(R.layout.popup_window, null);
        taskRecycler = mView.findViewById(R.id.taskPopupRecycler);
//        taskAdapter = new MyAdapter(Arrays.asList("Hello","World","Application"));
        getSavedTasks();
        taskAdapter = new MyAdapter(tasks);
        taskRecycler.setAdapter(taskAdapter);
        taskRecycler.setLayoutManager(new LinearLayoutManager(context));
        mView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        mView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        // set onClickListener on the remove button, which removes
        // the view from the window
//        mView.findViewById(R.id.window_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                close();
//            }
//        });
        countdown = mView.findViewById(R.id.countdown);
        progressBar = mView.findViewById(R.id.progress_bar);
//        ButterKnife.bind(this);
//        setUpAdapter();
//        getSavedTasks();
        // Define  position of the
        // window within the screen
//        mParams.gravity = Gravity.CENTER;
        mWindowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);

    }

    public void open() {

        try {
            // check if the view is already
            // inflated or present in the window
            if(mView.getWindowToken()==null) {
                if(mView.getParent()==null) {
                    mWindowManager.addView(mView, mParams);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(i>=0) {
                                progressBar.setProgress(i*5);
                                countdown.setText(Integer.toString(i));
                                i--;
                                handler.postDelayed(this, 1000);
                            }else{
                                close();
                            }
                        }
                    }, 2000);
                }
            }
        } catch (Exception e) {
            Log.d("Error1",e.toString());
        }

    }

    public void close() {

        try {
            // remove the view from the window
            ((WindowManager)context.getSystemService(WINDOW_SERVICE)).removeView(mView);
            // invalidate the view
            mView.invalidate();
            // remove all views
            ((ViewGroup)mView.getParent()).removeAllViews();

            // the above steps are necessary when you are adding and removing
            // the view simultaneously, it might give some exceptions
        } catch (Exception e) {
            Log.d("Error2",e.toString());
        }
    }

    public void setUpAdapter() {
        taskAdapter = new MyAdapter(tasks);
        taskRecycler.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
        taskRecycler.setAdapter(taskAdapter);
    }

    private void getSavedTasks() {

        class GetSavedTasks extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                tasks = DatabaseClient
                        .getInstance(context.getApplicationContext())
                        .getAppDatabase()
                        .dataBaseAction()
                        .getAllTasksList();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                setUpAdapter();
            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();
    }

//    @Override
//    public void refresh() {
//        getSavedTasks();
//    }
}
