package upm.es.proyecto_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class logInScreen extends AppCompatActivity {

    Button logInButton, eraseButton;
    EditText user, password;
    TextView signUpText;
    CheckBox rememberMe;
    int CORRECT = 1, PASS_WRONG = 2, USER_NOT_FOUND = 3;
    LoadLogInDetails load;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        logInButton = findViewById(R.id.logInScreen_btn_signIn);
        signUpText = findViewById(R.id.logInScreen_txt_signUpText);
        eraseButton = findViewById(R.id.logInScreen_btn_eraseData);
        user = findViewById(R.id.logInScreen_txt_username);
        password = findViewById(R.id.logInScreen_txt_password);
        rememberMe = findViewById(R.id.logInScreen_checkBox);
        load = new LoadLogInDetails();




        File internalStorageDir = getFilesDir();
        File database = new File(internalStorageDir, "db.txt");
        try {
            //noinspection ResultOfMethodCallIgnored
            database.createNewFile();
            FileInputStream fis = new FileInputStream(database);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            load = new LoadLogInDetails(br);
            Toast.makeText(logInScreen.this, "Database loaded", Toast.LENGTH_SHORT).show();
        } catch (IOException e){
            Toast.makeText(logInScreen.this, "Error reading database", Toast.LENGTH_SHORT).show();
        }


        File rememberFile = new File(internalStorageDir, "remember.txt");
        try{
            //noinspection ResultOfMethodCallIgnored
            rememberFile.createNewFile();
            FileInputStream fis = new FileInputStream(rememberFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            if (line != null) {
                long time = System.currentTimeMillis();
                String[] parts = line.split(";");
                long prev_time = Long.parseLong(parts[2]);
                long week = 604_800_000_000L;
                if ( time - prev_time < week) {
                    user.setText(parts[0]);
                    password.setText(parts[1]);
                } else {
                    Toast.makeText(logInScreen.this, "Session expired", Toast.LENGTH_SHORT).show();
                    rememberMe.setChecked(false);
                    try (FileOutputStream fos = new FileOutputStream(rememberFile)) {
                        fos.write("".getBytes());
                    }
                }
            }
        }
        catch (IOException e){
            Toast.makeText(logInScreen.this, "Error reading database", Toast.LENGTH_SHORT).show();
        }
        String user_txt = user.getText().toString();

        logInButton.setOnClickListener(view -> {
            if (user.getText().toString().isEmpty()) {
                user.setError("Please enter a username");
            }
            if (password.getText().toString().isEmpty()) {
                password.setError("Please enter a password");
            }
            if (!user.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                int result = load.findUser(user.getText().toString(), password.getText().toString());
                if (result == CORRECT) {
                    if (!user.getText().toString().equals(user_txt) || rememberMe.isChecked()){
                        updateRememberMe(rememberFile, user, password);
                    }
                    String name = "name";
                    try {
                        File userFile = new File(getFilesDir(), user.getText().toString() + ".txt");
                        FileInputStream fis = new FileInputStream(userFile);
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(isr);
                        name = br.readLine();
                    } catch (IOException e){
                        Toast.makeText(logInScreen.this, "Error getting name", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(logInScreen.this, home_screen.class);
                    intent.putExtra("name", name);
                    intent.putExtra("user", user.getText().toString());
                    startActivity(intent);
                } else if (result == USER_NOT_FOUND) {
                    user.setError("User not found");
                    password.setText("");
                } else if (result == PASS_WRONG){
                    password.setError("Wrong password");
                    password.setText("");
                }
            }
        });
        signUpText.setOnClickListener(view -> {
            Intent intent = new Intent(logInScreen.this, signUpScreen.class);
            intent.putExtra("user", user.getText().toString());
            startActivity(intent);
        });
        eraseButton.setOnClickListener(view -> {
            deleteAllFiles(logInScreen.this);
            Toast.makeText(this, "all files erased", Toast.LENGTH_SHORT).show();
            eraseButton.setVisibility(View.GONE);
        });



    }

    public static void deleteAllFiles(Context context){
        File internalStorageDir = context.getFilesDir();
        File[] files = internalStorageDir.listFiles();
        if (files != null){
            for (File file : files){
                file.delete();
            }
        }
    }
    public static void updateRememberMe(File file, EditText user, EditText password){
        try (FileOutputStream fos = new FileOutputStream(file, true)){
            long time = System.currentTimeMillis();
            String line = user.getText().toString() + ";" + password.getText().toString() +
                    ";" + time + "\n";
            fos.write(line.getBytes());
        } catch (IOException e) {
            Log.d("MyClass", "error reading file");
        }
    }
}