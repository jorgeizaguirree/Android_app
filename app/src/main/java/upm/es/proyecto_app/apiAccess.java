package upm.es.proyecto_app;

import android.net.Uri;
import android.os.Bundle;

import android.content.Intent;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class apiAccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_api_access);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(view -> register());
    }

    public void register() {
        String clientId = "TU_CLIENT_ID";
        String redirectUri = "https://tuaplicacion.com/callback";
        String scope = "data:read_write";
        String state = "algún_valor_unico";

        String link = "https://app.todoist.com/auth/signup" +
                "?client_id=" + Uri.encode(clientId) +
                "&scope=" + Uri.encode(scope) +
                "&redirect_uri=" + Uri.encode(redirectUri) +
                "&state=" + Uri.encode(state);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

}