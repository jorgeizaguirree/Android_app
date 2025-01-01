package upm.es.proyecto_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Random;


public class home_screen extends AppCompatActivity {

    int quoteCounter;
    List<Quotes> quotes;
    Button url_btn;
    TextView  quote, welcome, description;


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
        url_btn = findViewById(R.id.home_btn_createQuote);
        quote = findViewById(R.id.homeScreen_txt_quote);
        description = findViewById(R.id.homeScreen_txt_welcomeDescription);
        welcome = findViewById(R.id.homeScreen_txt_username);


        String welcome_text = "Hello, " + getIntent().getStringExtra("user") + "!";
        welcome.setText(welcome_text);
        description.setText("Welcome to the app!");

        url_btn.setOnClickListener(view -> {
            APIconnection api = new APIconnection(this, quotes, quote);
            api.start();
            quote.setText("Loading...");
        });
    }
}