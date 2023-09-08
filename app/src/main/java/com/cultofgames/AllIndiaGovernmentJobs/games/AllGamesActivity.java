package com.cultofgames.AllIndiaGovernmentJobs.games;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;


import com.cultofgames.AllIndiaGovernmentJobs.R;
import com.cultofgames.AllIndiaGovernmentJobs.databinding.ActivityAllGamesBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class AllGamesActivity extends AppCompatActivity {

    private ArrayList<GameItems> allGamesList;
    private ActivityAllGamesBinding binding;
    private GameAdapter adapter;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllGamesBinding.inflate(getLayoutInflater());
        getWindow().setFlags(1024, 1024);
        setContentView(binding.getRoot());

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewA);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        allGamesList = GamesList.allGames();
binding.btnBackGames.setOnClickListener(view -> {
    finish();
});

        adapter = new GameAdapter(this, allGamesList);
        binding.allGamesRecycler.setAdapter(adapter);
        binding.allGamesRecycler.setLayoutManager(new GridLayoutManager(this,4));


    }
}