package com.example.tp2partie3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textViewBattery;
    TextView textViewReceiver;
    MyBroadcastBatteryLow myBroadcastBatteryLow = new MyBroadcastBatteryLow();
    MyBroadcastCallReceiver myBroadcastCallReceiver = new MyBroadcastCallReceiver();
    IntentFilter filter = new IntentFilter();


    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button send = findViewById(R.id.sendeventbtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("FAKE_EVENT_INFO");
                sendBroadcast(intent);
            }
        });

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        }

        filter.addAction(Intent.ACTION_BATTERY_LOW);

        textViewBattery = findViewById(R.id.viewBattery);

        textViewReceiver = findViewById(R.id.viewReceiver);



    }

    public class MyBroadcastBatteryLow extends MyReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            textViewBattery.setText("Evenement Batterie faible reçu");
        }
    }

    private class MyBroadcastCallReceiver extends MyReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBroadcastBatteryLow, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadcastBatteryLow);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[]
                                                   grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
        }
    }

}

