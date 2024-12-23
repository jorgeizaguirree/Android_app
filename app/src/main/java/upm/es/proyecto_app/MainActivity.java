package upm.es.proyecto_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Procesar la URL de redirección cuando la app recibe el intent
        handleRedirect(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Asegurarse de que procesamos la URL de redirección si la actividad se reinicia
        setIntent(intent);
        handleRedirect(intent);
    }

    private void handleRedirect(Intent intent) {
        // Verificar si el intent contiene la URI esperada
        Uri uri = intent.getData();
        if (uri != null && uri.getScheme().equals("myapp") && uri.getHost().equals("callback")) {
            // Aquí obtenemos el parámetro 'code' de la URL de Todoist
            String authorizationCode = uri.getQueryParameter("code");
            String state = uri.getQueryParameter("state");

            if (authorizationCode != null) {
                // Aquí procesas el código de autorización recibido
                Log.d(TAG, "Authorization code: " + authorizationCode);
                // Puedes usar este código para obtener el token de acceso
                Toast.makeText(this, "Authorization code received: " + authorizationCode, Toast.LENGTH_SHORT).show();

                // Aquí puedes realizar una solicitud para obtener el token de acceso desde el servidor de Todoist
                // Este es un ejemplo de cómo hacer el intercambio del código por un token de acceso.
                // Omitido para simplificación.

            } else {
                Log.e(TAG, "Authorization failed, code is null.");
            }
        } else {
            Log.e(TAG, "Invalid redirect URL.");
        }
    }
}
