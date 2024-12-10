package upm.es.proyecto_app;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class home_screen extends AppCompatActivity {

    public BufferedReader get_url(String url_string){
        try {
            URL url = new URL(url_string);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            Toast.makeText(home_screen.this, conn.getResponseCode() + "", Toast.LENGTH_SHORT).show();
            Toast.makeText(home_screen.this, conn.getResponseMessage(), Toast.LENGTH_SHORT).show();
            InputStream is = conn.getInputStream();
            return new BufferedReader(new InputStreamReader(is));
        }
        catch (Exception e) {
            Toast.makeText(home_screen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

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




        TextView text = findViewById(R.id.homeScreen_txt_welcome);
        String welcome = "Welcome " + getIntent().getStringExtra("user");
        text.setText(welcome);

    }
}