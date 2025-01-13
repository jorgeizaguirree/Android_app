    package upm.es.proyecto_app;

    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.ListView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import java.io.File;
    import java.util.ArrayList;
    import java.util.List;

    public class home_screen extends AppCompatActivity {
        List <Task> taskList;
        List<Quotes> quotes;
        TextView quote, welcome, description, noTask;
        ImageView userImageView, settingsImageView;
        View view;

        @SuppressLint("SetTextI18n")
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


            quote = findViewById(R.id.homeScreen_txt_quote);
            description = findViewById(R.id.homeScreen_txt_welcomeDescription);
            welcome = findViewById(R.id.homeScreen_txt_username);
            userImageView = findViewById(R.id.homeScreen_image_defaultUserIcon);
            view = findViewById(R.id.homeScreen_view_quoteBackground);
            noTask = findViewById(R.id.homeScreen_txt_noQuote);


            settingsImageView = findViewById(R.id.homeScreen_image_settings);
            settingsImageView.setOnClickListener(v -> {
                Intent intent = new Intent(home_screen.this, settingsScreen.class);
                intent.putExtra("user", getIntent().getStringExtra("user"));
                intent.putExtra("name", getIntent().getStringExtra("name"));
                intent.putExtra("origin", "home");
                startActivity(intent);
            });

            APIconnection api = new APIconnection(this, quotes, quote);
            api.start();
            quote.setText("Loading...");

            String welcome_text = "Hello, " + getIntent().getStringExtra("name") + "!";
            welcome.setText(welcome_text);
            File internalStorageDir = getFilesDir();
            File imageFile = new File(internalStorageDir, getIntent().getStringExtra("user") + ".jpg");
            if (imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                userImageView.setImageBitmap(bitmap);
            } else {
                userImageView.setImageResource(R.drawable.ic_default_boy);
            }

            view.setOnClickListener(v -> {
                Intent intent = new Intent(home_screen.this, quotes_activity.class);
                intent.putExtra("user", getIntent().getStringExtra("user"));
                startActivity(intent);
            });

            File taskFile = new File(getFilesDir(), getIntent().getStringExtra("user")+ "_tasks.txt");
            Reader reader= new Reader(taskFile);
            taskList =  new ArrayList<>();
            reader.readTasks(taskList, taskFile);
            if(taskList.isEmpty()) Toast.makeText(this, "Lista Vacia",Toast.LENGTH_SHORT).show();
            TaskAdapter adapter = new TaskAdapter(this, taskList);

            if(taskList.isEmpty()) noTask.setVisibility(View.VISIBLE);
            else noTask.setVisibility(View.GONE);


            ListView listView = findViewById(R.id.task_list);
            listView.setAdapter(adapter);

            ImageView addTaskButton = findViewById(R.id.homeScreen_btn_addTask);
            addTaskButton.setOnClickListener(v -> showAddTaskDialog());

        }

        private void showAddTaskDialog() {
            // Construir el cuadro de diálogo
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add New Task");

            // Inflar un diseño para el cuadro de diálogo
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
            builder.setView(dialogView);

            // Referenciar los campos de entrada en el diseño
            EditText taskNameInput = dialogView.findViewById(R.id.dialog_task_name);
            EditText taskDescriptionInput = dialogView.findViewById(R.id.dialog_task_description);
            EditText taskDateInput = dialogView.findViewById(R.id.dialog_task_date);


            builder.setPositiveButton("Add", (dialog, which) -> {
                String name = taskNameInput.getText().toString().trim();
                String description = taskDescriptionInput.getText().toString().trim();
                String dateString = taskDateInput.getText().toString().trim();
                Toast.makeText(this, getFilesDir().toString(), Toast.LENGTH_SHORT).show();
                // Validar entrada
                if (name.isEmpty() || dateString.isEmpty()) {
                    Toast.makeText(this, "Name and Date are required", Toast.LENGTH_SHORT).show();
                    return;
                }
                    // Crear nueva tarea y añadirla a la lista
                    Task newTask = new Task(name, description, dateString);
                    taskList.add(newTask);

                    // Escribir la nueva tarea al archivo
                    FWriter writer = new FWriter(new File(getFilesDir(), getIntent().getStringExtra("user") + "_tasks.txt"));
                    writer.writeTasks(newTask);

                    // Notificar al adaptador
                    ListView listView = findViewById(R.id.task_list);
                    ((TaskAdapter) listView.getAdapter()).notifyDataSetChanged();


            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            // Mostrar el cuadro de diálogo
            builder.create().show();
        }
    }