package com.pemrogramanbergerak.to_dolistapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.pemrogramanbergerak.to_dolistapp.Model.TaskModel;
import com.pemrogramanbergerak.to_dolistapp.Utils.DataBaseHandler;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<TaskModel> taskList;
    private MainActivity mainActivity;
    private DataBaseHandler db;

    public TaskAdapter(MainActivity activity) {
        this.db = db;
        this.mainActivity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        db = new DataBaseHandler(getContext());
        db.openDatabase();
        TaskModel item = taskList.get(position);
        viewHolder.task.setText(item.getTask());
        viewHolder.task.setChecked(toBoolean(item.getStatus()));
        viewHolder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    db.updateStatus(item.getId(),1);
                } else {
                    db.updateStatus(item.getId(),0);
                }
            }
        });
    }

    public int getItemCount() {
        return taskList.size();
    }

    private boolean toBoolean(int n) {
        return n!=0;
    }

    public void setTask(List<TaskModel> toDoList) {
        this.taskList = toDoList;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return mainActivity;
    }

    public void deleteItem(int position) {
        TaskModel item = taskList.get(position);
        db.deleteTask(item.getId());
        taskList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem (int position) {
        TaskModel item = taskList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(mainActivity.getSupportFragmentManager(), AddNewTask.TAG);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.taskCheckbox);
        }
    }
}
