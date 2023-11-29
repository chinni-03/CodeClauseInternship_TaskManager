package com.example.mynder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mynder.Adapter.TaskAdapter;
import com.example.mynder.Model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TaskAdapter taskAdapter;
    RecyclerView recyclerView;
    ImageView add;
    EditText text;
    List<TaskModel> taskModelList;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.addBtn);

        sharedPreferences = getSharedPreferences("My Tasks", Context.MODE_PRIVATE);

        taskModelList = loadTasks();
        setTaskRecycler(taskModelList);

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                text = findViewById(R.id.enterTask);
                taskModelList.add(new TaskModel(text.getText().toString()));
                setTaskRecycler(taskModelList);
                text.getText().clear();
                saveTasks(taskModelList);

                taskAdapter.setTaskList(taskModelList);
                taskAdapter.notifyDataSetChanged();
            }
        });

    }

    private List<TaskModel> loadTasks () {
        List<TaskModel> taskModelList = new ArrayList<>();
        String savedTasks = sharedPreferences.getString("TASKS", "");
        if(!savedTasks.isEmpty()) {
            String[] tasks = savedTasks.split(",");
            for(String task: tasks) {
                taskModelList.add(new TaskModel(task));
            }
        }
        return taskModelList;
    }

    private void saveTasks(List<TaskModel>  taskList) {
        StringBuilder taskString = new StringBuilder();
        for(TaskModel task: taskList) {
            taskString.append(task.getTask()).append(",");
        }
        sharedPreferences.edit().putString("TASKS", taskString.toString()).apply();
    }

    private void setTaskRecycler(List<TaskModel> taskList) {
        recyclerView = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        taskAdapter = new TaskAdapter(this, taskList);
        recyclerView.setAdapter(taskAdapter);
    }

//    private void addTask(String taskText) {
//        List<TaskModel> taskModelList = loadTasks();
//        taskModelList.add(new TaskModel(taskText));
//        saveTasks(taskModelList);
//        setTaskRecycler(taskModelList);
//    }

    public void deleteTask(int position) {
        taskModelList.remove(position);
        saveTasks(taskModelList);
        taskAdapter.setTaskList(taskModelList);
        taskAdapter.notifyDataSetChanged();
    }
}