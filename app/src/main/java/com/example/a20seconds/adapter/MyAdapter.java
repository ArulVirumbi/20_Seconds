package com.example.a20seconds.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a20seconds.R;
import com.example.a20seconds.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Task> taskList;
    public SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy", Locale.US);
    public SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-M-yyyy", Locale.US);
    Date date = null;
    String outputDateString = null;

    public MyAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_name, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskTitle.setText(task.getTaskTitle());
        try {
            date = inputDateFormat.parse(task.getDate());
            assert date != null;
            outputDateString = dateFormat.format(date);

            String[] items1 = outputDateString.split(" ");
            String day = items1[0];
            String dd = items1[1];
            String month = items1[2];

            holder.taskDate.setText(dd+" "+month);
            holder.taskDay.setText(day);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

        @Override
        public int getItemCount() {
            return taskList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView taskTitle;
            TextView taskDate;
            TextView taskDay;
//            TextView taskMonth;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                taskTitle = itemView.findViewById(R.id.taskTitle);
                taskDate = itemView.findViewById(R.id.taskDate);
                taskDay = itemView.findViewById(R.id.taskDay);
//                taskMonth = itemView.findViewById(R.id.taskMonth);

            }
        }
}