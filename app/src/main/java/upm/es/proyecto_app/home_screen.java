package upm.es.proyecto_app;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileReader;
import java.util.List;
import java.util.Random;

public class home_screen extends AppCompatActivity {
    private List<Task> task_list;

    int quoteCounter;
    List<Quotes> quotes;
    Button url_btn;
    TextView quote, welcome, description;
    ImageView userImageView;
    Uri imageUri;
    ActivityResultLauncher<Intent> galleryLauncher;
    ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Random random = new Random();
        quoteCounter = random.nextInt(10);
        url_btn = findViewById(R.id.homeScreen_btn_createQuote);
        quote = findViewById(R.id.homeScreen_txt_quote);
        description = findViewById(R.id.homeScreen_txt_welcomeDescription);
        welcome = findViewById(R.id.homeScreen_txt_username);
        userImageView = findViewById(R.id.homeScreen_image_defaultUserIcon);

        String welcome_text = "Hello, " + getIntent().getStringExtra("user") + "!";
        welcome.setText(welcome_text);

        APIconnection api = new APIconnection(this, quotes, quote);
        api.start();
        quote.setText("Loading...");


        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            userImageView.setImageURI(selectedImageUri);
                        }
                    } else {
                        Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        if (imageUri != null) {
                            userImageView.setImageURI(imageUri);
                        }
                    } else {
                        Toast.makeText(this, "No image captured", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Load tasks from file
        Reader fileReader = new Reader("tasks.txt"); // Replace with your actual file path
        task_list = fileReader.readTasks();

        // Set up ListView
        ListView listView = findViewById(R.id.task_list);
        TaskAdapter adapter = new TaskAdapter(this, task_list); // Create an adapter
        listView.setAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new com.google.ar.imp.view.View.OnClickListener() {
            @Override
            public void onClick(com.google.ar.imp.view.View v) {
                showCreateTaskDialog();
            }
        });
    }
    private void showCreateTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.create_task_dialog, null);
        builder.setView(dialogView);

        final EditText nameEditText = dialogView.findViewById(R.id.edit_task_name);
        final EditText descriptionEditText = dialogView.findViewById(R.id.edit_task_description);
        final EditText dateEditText = dialogView.findViewById(R.id.edit_task_date);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String date = dateEditText.getText().toString();

                Task newTask = new Task(name, description, date);
                task_list.add(newTask); // Add to the list

                FWriter fileWriter = new FWriter("tasks.txt"); // Replace with your actual file path
                fileWriter.writeTasks(newTask); // Write to file

                adapter.notifyDataSetChanged(); // Update ListView
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}