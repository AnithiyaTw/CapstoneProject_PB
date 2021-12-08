package com.example.capstoneproject_pb;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private String mMessage;
    public static final String EXTRA_MESSAGE = "com.example.CapstoneProject_PB.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DarkMode
        if (DarkMode.getInstance().isNightModeEnabled()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(R.layout.activity_main);

        //SwitchDarkmode
        Switch enableDark = findViewById(R.id.switchDark);
        enableDark.setChecked(false);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            enableDark.setChecked(true);

        enableDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DarkMode.getInstance().setIsNightModeEnabled(true);
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                } else {
                    DarkMode.getInstance().setIsNightModeEnabled(false);
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                }
            }
        });

    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
            }


    public void AplikasiBy(View view) {
        mMessage = getString(R.string.by);
        displayToast(mMessage);
    }

    public void DaftarWisata(View view) {
        Intent intent = new Intent(MainActivity.this, DaftarWisata.class);
        startActivity(intent);
    }
    public void TentangKota(View view) {
      Intent intent = new Intent(MainActivity.this, DetailTentangKota.class);
      startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        //set pesan yang akan ditampilkan
        builder.setMessage("Anda Yakin Ingin Keluar ?");
        //set "ya"
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        //set "tidak"
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

