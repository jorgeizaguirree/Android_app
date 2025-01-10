package upm.es.proyecto_app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FWriter {

    private final String filePath;

    public FWriter(String filePath) {
        this.filePath = filePath;
    }

    public void writeTasks(List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                String line = task.getName() + ";" + task.getDescription() + ";" + task.getDate();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}