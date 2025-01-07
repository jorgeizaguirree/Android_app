package upm.es.proyecto_app;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
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
    }
}