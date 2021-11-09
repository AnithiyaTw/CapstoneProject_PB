package com.example.capstoneproject_pb;


import static android.provider.AlarmClock.EXTRA_MESSAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private String mMessage;
    public static final String EXTRA_MESSAGE = "com.example.CapstoneProject_PB.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        startActivity(new Intent(MainActivity.this, DaftarWisata.class));
    }
}

