package upm.es.proyecto_app;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class changePasswordScreen extends AppCompatActivity {

    EditText password, confirm;
    Button change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_screen);
        password = findViewById(R.id.changePasswordScreen_editTextTextPassword_newPassword);
        confirm = findViewById(R.id.changePasswordScreen_editTextTextPassword_confirmPassword);
        change = findViewById(R.id.changePasswordScreen_button_updateProfile);

        String user = getIntent().getStringExtra("user");



        }



}