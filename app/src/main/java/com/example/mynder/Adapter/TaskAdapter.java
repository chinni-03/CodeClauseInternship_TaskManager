package com.example.mynder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynder.MainActivity;
import com.example.mynder.Model.TaskModel;
import com.example.mynder.R;
import com.example.mynder.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    Context context;
    private List<TaskModel> taskList;

    public TaskAdapter(Context context, List<TaskModel> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        TaskModel task = taskList.get(position);
        holder.taskName.setText(task.getTask());

        ImageView deleteTask= holder.itemView.findViewById(R.id.deleteTask);

//        if (task.isChecked()) {
//            holder.taskName.setPaintFlags(holder.taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        } else {
//            holder.taskName.setPaintFlags(holder.taskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Task.class);
                intent.putExtra("TASK_POSITION", holder.getAdapterPosition());
                context.startActivity(intent);
            }
        });

        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                ((MainActivity) context).deleteTask(adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        public ViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.addedTask);
        }
    }
    public void setTaskList(List<TaskModel> taskList) {
        this.taskList = taskList;
    }

}
