package upm.es.proyecto_app;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;

public class Reader extends AppCompatActivity {

    private File file;

    public Reader(File file) {
        this.file = file;
    }

    public void readTasks(List<Task> tasks, File internalStorageDir) {
        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                Task task = getTask(line);
                tasks.add(task);
            }

        } catch (IOException e){
            Toast.makeText(this, "Error reading database", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @NonNull
    private static Task getTask(String line) throws ParseException {
        String[] values = line.split(";");
        String name = "";
        String description = "";
        String date = null;

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