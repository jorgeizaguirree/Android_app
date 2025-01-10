package upm.es.proyecto_app;

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
                String[] values = line.split(";");
                if (values.length >= 3) { // Check if there are enough values for a Task
                    String name = values[0];
                    String description = values[1];
                    String date = values[2];
                    Task task = new Task(name, description, date);
                    tasks.add(task);
                } else {
                    // Handle lines with insufficient data, e.g., log a warning
                    System.err.println("Skipping line with insufficient data: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            // Handle the exception appropriately
        }

        return tasks;
    }
}
