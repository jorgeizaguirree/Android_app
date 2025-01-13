package upm.es.proyecto_app;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class settingsScreen extends AppCompatActivity {

    ImageView userImageView;
    Uri imageUri;
    ActivityResultLauncher<Intent> galleryLauncher;
    ActivityResultLauncher<Intent> cameraLauncher;
    EditText name;
    Button update, logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        userImageView = findViewById(R.id.profileScreen_image_defaultUserIcon);
        ImageView backIcon = findViewById(R.id.profileScreen_backIcon);
        name = findViewById(R.id.profileScreen_editTextText_changeName);
        update = findViewById(R.id.profileScreen_button_updateProfile);
        logOut = findViewById(R.id.profileScreen_button_logOut);

        String origin = getIntent().getStringExtra("origin");
        if (origin != null && origin.equals("singUp")) {
            update.setText("Set Profile");
        }

        name.setHint(getIntent().getStringExtra("name"));
        File internalStorageDir = getFilesDir();
        File imageFile = new File(internalStorageDir, getIntent().getStringExtra("user") + ".jpg");
        File userFile = new File(internalStorageDir, getIntent().getStringExtra("user") + ".txt");
        if (imageFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            userImageView.setImageBitmap(bitmap);
        } else {
            userImageView.setImageResource(R.drawable.ic_default_boy);
        }

        backIcon.setOnClickListener(v -> finish());

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        if (imageUri != null) {
                            userImageView.setImageURI(imageUri);
                        } else {
                            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        if (imageUri != null) {
                            userImageView.setImageURI(imageUri);
                        }
                    } else {
                        Toast.makeText(this, "No image captured", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        update.setOnClickListener(v -> {
            if (imageUri != null) {
                try {
                    imageFile.createNewFile();
                    try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                        InputStream is = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            String name_txt = getIntent().getStringExtra("name");
            if (!name.getText().toString().isEmpty()){
                try {
                    userFile.createNewFile();
                    try (FileOutputStream fos = new FileOutputStream(userFile)) {
                        fos.write(name.getText().toString().getBytes());
                        name_txt = name.getText().toString();
                    }
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(settingsScreen.this, home_screen.class);
            intent.putExtra("name", name_txt);
            intent.putExtra("user", getIntent().getStringExtra("user"));
            startActivity(intent);
        });
        logOut.setOnClickListener(v -> startActivity(new Intent(settingsScreen.this, welcomeScreen.class)));
        findViewById(R.id.profileScreen_image_changeIcon).setOnClickListener(v -> showIconPickerDialog());
    }

    private void showIconPickerDialog() {
        String[] options = {"Default Icon", "Gallery", "Camera"};
        new AlertDialog.Builder(this)
                .setTitle("Choose Icon")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        showDefaultIconPicker();
                    } else if (which == 1) {
                        openGallery();
                    } else {
                        openCamera();
                    }
                })
                .show();
    }

    private void showDefaultIconPicker() {
        String[] defaultIcons = {"Girl", "Boy"};
        new AlertDialog.Builder(this)
                .setTitle("Choose Default Icon")
                .setItems(defaultIcons, (dialog, which) -> {
                    if (which == 0) {
                        userImageView.setImageResource(R.drawable.ic_default_girl);
                    } else {
                        userImageView.setImageResource(R.drawable.ic_default_boy);
                    }
                })
                .show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraLauncher.launch(intent);
        }
    }
}