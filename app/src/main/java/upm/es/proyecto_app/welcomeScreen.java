package upm.es.proyecto_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class welcomeScreen extends AppCompatActivity {
    Button LogInBtn, SignUPBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LogInBtn = findViewById(R.id.welcomeScreen_btn_login);
        SignUPBtn = findViewById(R.id.welcomeScreen_btn_createAccount);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        LogInBtn.setOnClickListener(view -> startActivity(new Intent(welcomeScreen.this, logInScreen.class)));
        SignUPBtn.setOnClickListener(view -> startActivity(new Intent(welcomeScreen.this, signUpScreen.class)));
    }
}