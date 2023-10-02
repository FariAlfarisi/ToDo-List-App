package com.pemrogramanbergerak.to_dolistapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pemrogramanbergerak.to_dolistapp.Model.TaskModel;
import com.pemrogramanbergerak.to_dolistapp.Utils.DataBaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{

    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
    private FloatingActionButton fab;

    private List<TaskModel> taskList;
    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        db = new DataBaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        taskRecyclerView = findViewById(R.id.rVTasksList);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(this);
        taskRecyclerView.setAdapter(taskAdapter);

        fab = findViewById(R.id.addNewTaskButton);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(taskRecyclerView);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTask(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialogInterface) {
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTask(taskList);
        taskAdapter.notifyDataSetChanged();
    }

}
