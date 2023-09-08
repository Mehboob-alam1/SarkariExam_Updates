package com.cultofgames.AllIndiaGovernmentJobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;

public class UsingVpn extends AppCompatActivity {
private AppCompatButton btnGoBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_vpn);


        btnGoBack=findViewById(R.id.btnGOBack);
        btnGoBack.setOnClickListener( view -> {
            finish();
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}