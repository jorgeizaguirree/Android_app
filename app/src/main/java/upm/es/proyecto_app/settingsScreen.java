package upm.es.proyecto_app;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class settingsScreen extends AppCompatActivity {

    ImageView userImageView;
    Uri imageUri;
    ActivityResultLauncher<Intent> galleryLauncher;
    ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        userImageView = findViewById(R.id.profileScreen_image_defaultUserIcon);
        ImageView backIcon = findViewById(R.id.profileScreen_backIcon);

        backIcon.setOnClickListener(v -> finish());

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
                    if (result.getResultCode() == RESULT_OK) {
                        if (imageUri != null) {
                            userImageView.setImageURI(imageUri);
                        }
                    } else {
                        Toast.makeText(this, "No image captured", Toast.LENGTH_SHORT).show();
                    }
                }
        );

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