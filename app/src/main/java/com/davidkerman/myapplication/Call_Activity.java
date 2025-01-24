package com.davidkerman.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.regex.Pattern;

public class Call_Activity extends AppCompatActivity {

    private TextView calltitle;
    private Button dial1;
    private Button    dial2;
    private Button    dial3;
    private Button    dial4;
    private Button    dial5;
    private Button    dial6;
    private Button    dial7;
    private Button    dial8;
    private Button    dial9;
    private Button    dial0;
    private Button    dialast;
    private Button    dialhash;
    private Button    callbutton;

    private ActivityResultLauncher<Intent> phoneCalllauncher;
    private ActivityResultLauncher<String> requestPermissionlauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_call);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setLaunchers();
    }

    private void initializeViews() {
        calltitle    = findViewById(R.id.calltitle);
        dial1        = findViewById(R.id.dial1);
        dial2        = findViewById(R.id.dial2);
        dial3        = findViewById(R.id.dial3);
        dial4        = findViewById(R.id.dial4);
        dial5        = findViewById(R.id.dial5);
        dial6        = findViewById(R.id.dial6);
        dial7        = findViewById(R.id.dial7);
        dial8        = findViewById(R.id.dial8);
        dial9        = findViewById(R.id.dial9);
        dial0        = findViewById(R.id.dial0);
        dialast      = findViewById(R.id.dialast);
        dialhash     = findViewById(R.id.dialhash);
        callbutton   = findViewById(R.id.callbutton);

        setListeners();
    }

    private void setLaunchers() {
        phoneCalllauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {

            }
        });

        requestPermissionlauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                if (o)
                    initializePhonecall();
                else
                    Toast.makeText(Call_Activity.this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializePhonecall() {
        String phone = calltitle.getText().toString().substring(24);

        if (phone != null && Pattern.matches("^\\+?[0-9]{10,15}$", phone)){
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phone));
            phoneCalllauncher.launch(intent);
        }

        else
            Toast.makeText(Call_Activity.this, "Please enter a phone number or check that you have a correct phone number format", Toast.LENGTH_SHORT).show();
    }

    private void setListeners() {
        dial1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calltitle.append(dial1.getText().toString());
            }
        });

        dial2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calltitle.append(dial2.getText().toString());
            }
        });

        dial3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calltitle.append(dial3.getText().toString());
            }
        });

        dial4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calltitle.append(dial4.getText().toString());
            }
        });

        dial5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calltitle.append(dial5.getText().toString());
            }
        });

        dial6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calltitle.append(dial6.getText().toString());
            }
        });

        dial7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calltitle.append(dial7.getText().toString());
            }
        });

        dial8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calltitle.append(dial8.getText().toString());
            }
        });

        dial9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calltitle.append(dial9.getText().toString());
            }
        });

        dial0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calltitle.getText().toString().endsWith("\n"))
                    calltitle.append("+");
                else{
                    calltitle.append(dial0.getText().toString());
                }
            }
        });

        dialast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calltitle.append(dialast.getText().toString());
            }
        });

        dialhash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calltitle.append(dialhash.getText().toString());
            }
        });

        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionCall();
            }
        });
    }

    private void checkPermissionCall() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            initializePhonecall();

        else{
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE)){
                showPermissionRationableDialog();
            }
            else
                requestPermissionlauncher.launch(android.Manifest.permission.CALL_PHONE);
        }
    }

    private void showPermissionRationableDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Permission needed")
                .setMessage("This app needs the PHONE_CALL permission to make phone calls directly. Without this permission we can't initiate phone calls for you.")
                .setPositiveButton("OK", (dialog, which) -> requestPermissionlauncher.launch(android.Manifest.permission.CALL_PHONE))
                .setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(Call_Activity.this, "Permission denied", Toast.LENGTH_SHORT).show())
                .show();
    }
}