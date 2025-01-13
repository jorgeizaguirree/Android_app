    package upm.es.proyecto_app;

    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import java.io.File;
    import java.util.List;
    import java.util.Random;

    public class home_screen extends AppCompatActivity {

        int quoteCounter;
        List<Quotes> quotes;
        Button url_btn;
        TextView quote, welcome, description;
        ImageView userImageView, settingsImageView;
        View view;

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
            view = findViewById(R.id.homeScreen_view_quoteBackground);

            settingsImageView = findViewById(R.id.homeScreen_image_settings);
            settingsImageView.setOnClickListener(v -> {
                Intent intent = new Intent(home_screen.this, settingsScreen.class);
                intent.putExtra("user", getIntent().getStringExtra("user"));
                intent.putExtra("name", getIntent().getStringExtra("name"));
                intent.putExtra("origin", "home");
                startActivity(intent);
            });

            APIconnection api = new APIconnection(this, quotes, quote, 1);
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
        }
    }