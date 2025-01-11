package upm.es.proyecto_app;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    private String filePath;

    public Reader(String filePath) {
        this.filePath = filePath;
    }

    public List<Task> readTasks() {
        List<Task> tasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = getTask(line);
                tasks.add(task);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return tasks;
    }

    @NonNull
    private static Task getTask(String line) {
        String[] values = line.split(";");
        String name = "";
        String description = "";
        String date = "";

        // Assign values based on the number of fields in the line
        if (values.length >= 1) {
            name = values[0];
        }
        if (values.length >= 2) {
            description = values[1];
        }
        if (values.length >= 3) {
            date = values[2];
        }

        Task task = new Task(name, description, date);
        return task;
    }
}