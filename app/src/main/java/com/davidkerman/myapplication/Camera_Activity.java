package com.davidkerman.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;

public class Camera_Activity extends AppCompatActivity {

    private ImageView photo;
    private Button    cambutton;
    private Bitmap    picture;
    private Button    tomenu;

    private ActivityResultLauncher<Void>   cameralauncher;
    private ActivityResultLauncher<Intent> gallerylauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeVies();
        setLaunchers();
    }

    private void initializeVies() {
        photo     = findViewById(R.id.photo);
        cambutton = findViewById(R.id.cambutton);
        tomenu    = findViewById(R.id.tomenubutton_cam);

        setListeners();
    }

    private void setListeners() {
        cambutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(Camera_Activity.this)
                        .setTitle("Picture")
                        .setMessage("Take a picture")
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cameralauncher.launch(null);
                            }
                        })
                        .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent gallintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                gallerylauncher.launch(gallintent);
                            }
                        })
                        .setNeutralButton("Cancel", null)
                        .setCancelable(true)
                        .show();
            }
        });

        tomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Camera_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setLaunchers() {
        cameralauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap o) {
                picture = o;
                photo.setImageBitmap(picture);
            }
        });

        gallerylauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode() == RESULT_OK && o.getData() != null){
                    Uri imageUri = o.getData().getData();
                    try{
                        picture = uriToBitmap(imageUri);
                        photo.setImageBitmap(picture);
                    }
                    catch (Exception ex) {}
                }
            }
        });
    }

    private Bitmap uriToBitmap(Uri uri) throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            return ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), uri));
        }
        else{
            return MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        }
    }
}