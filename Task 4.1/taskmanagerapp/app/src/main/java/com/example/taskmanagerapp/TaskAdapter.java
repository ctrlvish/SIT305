package com.example.taskmanagerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{
    private Context context;
    private List<TaskModel> taskList;
    private TaskFragment fragment;

    public TaskAdapter(Context context, List<TaskModel> taskList, TaskFragment fragment) {
        this.context = context;
        this.taskList = taskList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_layout, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel task = taskList.get(position);
        holder.titleTextView.setText(task.getTitle());
        holder.dueDateTextView.setText(task.getDueDate());

        holder.itemView.setOnClickListener(v -> {
            //launch the detail activity
            Intent intent = new Intent(context, TaskDetailActivity.class);
            intent.putExtra("TASK_TITLE", task.getTitle());
            intent.putExtra("TASK_DESCRIPTION", task.getDescription());
            intent.putExtra("TASK_DUE_DATE", task.getDueDate());
            context.startActivity(intent);
        });

        //long press still for editing
        holder.itemView.setOnLongClickListener(v -> {
            fragment.onTaskClicked(task);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void updateTaskList(List<TaskModel> newList) {
        taskList = newList;
        notifyDataSetChanged();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, dueDateTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
        }
    }
}
