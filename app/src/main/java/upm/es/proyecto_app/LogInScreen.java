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


public class LogInScreen extends AppCompatActivity {

    Button logInButton, eraseButton;
    EditText user, password;
    TextView signUpText;
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
        logInButton = findViewById(R.id.logInScreen_btn_logIN);
        signUpText = findViewById(R.id.logInScreen_txt_signUpText);
        eraseButton = findViewById(R.id.logInScreen_btn_eraseData);
        user = findViewById(R.id.logInScreen_txt_user);
        password = findViewById(R.id.logInScreen_txt_password);


        load = new LoadLogInDetails();

        File internalStorageDir = getFilesDir();
        File myFile = new File(internalStorageDir, "db.txt");
        try {
            //noinspection ResultOfMethodCallIgnored
            myFile.createNewFile();
            FileInputStream fis = new FileInputStream(myFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            load = new LoadLogInDetails(br);
            Toast.makeText(LogInScreen.this, "Database loaded", Toast.LENGTH_SHORT).show();
        } catch (IOException e){
            Toast.makeText(LogInScreen.this, "Error reading database", Toast.LENGTH_SHORT).show();
        }

        logInButton.setOnClickListener(view -> {

            if (user.getText().toString().isEmpty()) {
                user.setError("Please enter a username");
            }
            if (password.getText().toString().isEmpty()) {
                password.setError("Please enter a password");
            }
            if (!user.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                Toast.makeText(LogInScreen.this, "Loading...", Toast.LENGTH_SHORT).show();
                int result = load.findUser(user.getText().toString(), password.getText().toString());
                if (result == CORRECT) {
                    Intent intent = new Intent(LogInScreen.this, home_screen.class);
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
            Intent intent = new Intent(LogInScreen.this, sign_up_screen.class);
            intent.putExtra("user", user.getText().toString());
            startActivity(intent);
        });
        eraseButton.setOnClickListener(view -> {
            try{
                FileOutputStream fos = new FileOutputStream(myFile);
                fos.write("".getBytes());
                Toast.makeText(LogInScreen.this, "Database erased", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(LogInScreen.this, "Error deleting database", Toast.LENGTH_SHORT).show();
            }
        });


    }
}