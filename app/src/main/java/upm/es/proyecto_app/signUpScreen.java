package upm.es.proyecto_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

public class signUpScreen extends AppCompatActivity {

    Button createButton;
    EditText name, user, password, repeat;
    TextView logInText;
    int USER_NOT_FOUND = 3;
    LoadLogInDetails load = new LoadLogInDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createButton = findViewById(R.id.signUpScreen_btn_signUp);
        name = findViewById(R.id.signUpScreen_txt_name);
        user = findViewById(R.id.signUpScreen_txt_username);
        password = findViewById(R.id.signUpScreen_txt_password);
        repeat = findViewById(R.id.signUpScreen_txt_confirmPassword);
        logInText = findViewById(R.id.signUpScreen_txt_signInText);

        File internalStorageDir = getFilesDir();
        File myFile = new File(internalStorageDir, "db.txt");
        try  {
            FileInputStream fis = new FileInputStream(myFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            load = new LoadLogInDetails(br);
        } catch (IOException e){
            Toast.makeText(signUpScreen.this, "Error reading database", Toast.LENGTH_SHORT).show();
        }

        createButton.setOnClickListener(view -> {
            if (name.getText().toString().isEmpty()) {
                name.setError("Please enter a name");
            } else if (user.getText().toString().isEmpty()) {
                user.setError("Please enter a username");
            } else if (password.getText().toString().isEmpty()) {
                password.setError("Please enter a password");
            } else if (repeat.getText().toString().isEmpty()) {
                repeat.setError("Please repeat the password");
            } else if (!password.getText().toString().equals(repeat.getText().toString())) {
                repeat.setError("Passwords do not match");
            } else {
                int result = load.findUser(user.getText().toString(), "");
                if (result == USER_NOT_FOUND){
                    File user_file = new File(internalStorageDir, user.getText().toString() + ".txt");
                    try {
                        user_file.createNewFile();
                        try (FileOutputStream fos = new FileOutputStream(user_file)) {
                            fos.write(user.getText().toString().getBytes());
                        }
                    } catch (IOException e) {
                        Toast.makeText(signUpScreen.this, "Error creating user file", Toast.LENGTH_SHORT).show();
                    }
                    try (FileOutputStream fos = new FileOutputStream(myFile, true)){
                        String line = user.getText().toString() + ";" + password.getText().toString().hashCode() + "\n";
                        fos.write(line.getBytes());
                        Toast.makeText(signUpScreen.this, "Account created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(signUpScreen.this, settingsScreen.class);
                        intent.putExtra("name", name.getText().toString());
                        intent.putExtra("user", user.getText().toString());
                        startActivity(intent);
                    } catch (IOException e) {
                        Toast.makeText(signUpScreen.this, "Error writing database", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    user.setError("Username already exists");
                }
            }
        });
        logInText.setOnClickListener(view -> startActivity(new Intent(signUpScreen.this, logInScreen.class)));
    }
}