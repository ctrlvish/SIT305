package com.example.taskmanagerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<TaskModel> taskList;
    private DatabaseHelper dbHelper;

    private EditText titleInput, descriptionInput, dueDateInput;
    private Button addButton, updateButton, deleteButton;
    private TaskModel selectedTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        //initialise views
        recyclerView = view.findViewById(R.id.recyclerView);
        titleInput = view.findViewById(R.id.titleInput);
        descriptionInput = view.findViewById(R.id.descriptionInput);
        dueDateInput = view.findViewById(R.id.dueDateInput);
        addButton = view.findViewById(R.id.addButton);
        updateButton = view.findViewById(R.id.updateButton);
        deleteButton = view.findViewById(R.id.deleteButton);

        //setup RecyclerView
        taskList = new ArrayList<>();
        adapter = new TaskAdapter(getContext(), taskList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        //initialise database
        dbHelper = new DatabaseHelper(getContext());
        loadTasks();

        //button click listeners
        addButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String description = descriptionInput.getText().toString();
            String dueDate = dueDateInput.getText().toString();

            if (!title.isEmpty() && !dueDate.isEmpty()) {
                if (isValidDateFormat(dueDate)) {
                    if (selectedTask == null) {
                        dbHelper.addTask(title, description, dueDate);
                        clearInputs();
                        loadTasks();
                    } else {
                        // Cancel selection
                        selectedTask = null;
                        clearInputs();
                        updateButton.setEnabled(false);
                        deleteButton.setEnabled(false);
                        addButton.setText("Add");
                    }
                } else {
                    Toast.makeText(getContext(), "Date format should be DD-MM-YYYY", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Title and Due Date are required", Toast.LENGTH_SHORT).show();
            }
        });

        updateButton.setOnClickListener(v -> {
            if (selectedTask != null) {
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                String dueDate = dueDateInput.getText().toString();

                if (!title.isEmpty() && !dueDate.isEmpty()) {
                    if (isValidDateFormat(dueDate)) {
                        dbHelper.updateTask(selectedTask.getId(), title, description, dueDate);
                        clearInputs();
                        selectedTask = null;
                        updateButton.setEnabled(false);
                        deleteButton.setEnabled(false);
                        addButton.setText("Add");
                        loadTasks();
                    } else {
                        Toast.makeText(getContext(), "Date format should be DD-MM-YYYY", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Title and Due Date are required", Toast.LENGTH_SHORT).show();
                }
            }
        });


        deleteButton.setOnClickListener(v -> {
            if (selectedTask != null) {
                dbHelper.deleteTask(selectedTask.getId());
                clearInputs();
                selectedTask = null;
                updateButton.setEnabled(false);
                deleteButton.setEnabled(false);
                addButton.setText("Add");
                loadTasks();
            }
        });

        //initially disable update and delete buttons
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        return view;
    }

    private void loadTasks() {
        taskList = dbHelper.getAllTasks();
        adapter.updateTaskList(taskList);
    }

    private void clearInputs() {
        titleInput.setText("");
        descriptionInput.setText("");
        dueDateInput.setText("");
    }

    public void onTaskClicked(TaskModel task) {
        selectedTask = task;
        titleInput.setText(task.getTitle());
        descriptionInput.setText(task.getDescription());
        dueDateInput.setText(task.getDueDate());
        updateButton.setEnabled(true);
        deleteButton.setEnabled(true);
        addButton.setText("Cancel");
    }

    private boolean isValidDateFormat(String date) {
        return date.matches("\\d{2}-\\d{2}-\\d{4}");
    }
}
