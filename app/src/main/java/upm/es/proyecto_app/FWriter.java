package upm.es.proyecto_app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FWriter {

    private final String filePath;

    public FWriter(String filePath){
        this.filePath = filePath;
    }
    /*
     * Writes tasks into file, if it doesn't exist, it will be created
     */
    public void writeTasks(Task task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String line = task.getName() + ";" + task.getDescription() + ";" + task.getDate();
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void removeTask(List<Task> tasks, Task taskToRemove) {
        tasks.remove(taskToRemove); // Remove from the list

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) { // Overwrite file
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