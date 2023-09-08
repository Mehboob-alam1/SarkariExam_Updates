package com.cultofgames.AllIndiaGovernmentJobs.games;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.cultofgames.AllIndiaGovernmentJobs.MainActivity;
import com.cultofgames.AllIndiaGovernmentJobs.R;
import com.cultofgames.AllIndiaGovernmentJobs.SarkariAdapter.SliderAdapter;
import com.cultofgames.AllIndiaGovernmentJobs.SarkariModal.Slider;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class GamesActivity extends AppCompatActivity {
    private ArrayList<Slider> list;
    private ArrayList<GameItems> gameItemsArrayList;
    private SliderView sliderView;
    private SliderAdapter adapter;
    private GameAdapter gameAdapter;
    private RecyclerView gameRecyclerView;
    private AdView mAdView;
    private TextView txtMore;
      private ImageView btnBack;
private DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_games);
        sliderView = findViewById(R.id.imageSliderGame);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        btnBack=findViewById(R.id.btnBack_Games);
databaseReference= FirebaseDatabase.getInstance().getReference();
        initViews();
        txtMore.setOnClickListener(view -> {
            startActivity(new Intent(GamesActivity.this, AllGamesActivity.class));
        });
        fetchBanners();
        initializeGames();

        btnBack.setOnClickListener(view -> {
            finish();
        });


    }

    private void initializeGames() {

        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/B1fSpMkP51m/thumb.png", "https://www.gamezop.com/g/B1fSpMkP51m?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/BkdJhTX50B/thumb.png", "https://www.gamezop.com/g/BkdJhTX50B?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/BJ9bvzIKdJl/thumb.png", "https://www.gamezop.com/g/BJ9bvzIKdJl?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/H1AN6fkwqJ7/thumb.png", "https://www.gamezop.com/g/H1AN6fkwqJ7?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/HJP4afkvqJQ/thumb.png", "https://www.gamezop.com/g/HJP4afkvqJQ?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/B1JBaM1D9y7/thumb.png", "https://www.gamezop.com/g/B1JBaM1D9y7?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/BkzmafyPqJm/thumb.png", "https://www.gamezop.com/g/BkzmafyPqJm?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/SkJf58Ouf0/thumb.png", "https://www.gamezop.com/g/SkJf58Ouf0?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/Skz4pzkDqyX/thumb.png", "https://www.gamezop.com/g/Skz4pzkDqyX?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/SkQwnwnbK/thumb.png", "https://www.gamezop.com/g/SkQwnwnbK?id=4805"));

        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/SykGDfUKOkg/thumb.png", "https://www.gamezop.com/g/UYiznUAya?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/HJT46GkPcy7/thumb.png", "https://www.gamezop.com/g/QPcVkaHi1?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/B1MXhUFQke/thumb.png", "https://www.gamezop.com/g/r1xZyhTQ50r?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/rkb--Io78Ux/thumb.png", "https://www.gamezop.com/g/r1u7J3TmqCS?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/BkEv3wn-t/thumb.png", "https://www.gamezop.com/g/S1Ne12TQqCH?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/H1WmafkP9JQ/thumb.png", "https://www.gamezop.com/g/H1CWk36mq0S?id=4805"));



        gameAdapter = new GameAdapter(this, gameItemsArrayList);

        gameRecyclerView.setAdapter(gameAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        gameRecyclerView.setLayoutManager(layoutManager);


    }


    private void setSlider() {

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

    }

    private void initViews() {

        gameRecyclerView = findViewById(R.id.games_RecyclerView);
        txtMore = findViewById(R.id.txtMore);
        list = new ArrayList<>();
        gameItemsArrayList = new ArrayList<>();
    }

    private void fetchBanners() {

        databaseReference.child("Banners").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String imageUrl = snapshot1.child("imageUrl").getValue(String.class);
                        String imageLink = snapshot1.child("imageLink").getValue(String.class);
                        String pushId = snapshot1.child("pushId").getValue(String.class);

//                        Toast.makeText(StartScreen.this, ""+data, Toast.LENGTH_SHORT).show();
                        list.add(new Slider(imageLink, imageUrl, pushId));
                    }
                    adapter = new SliderAdapter(list,GamesActivity.this);
                    sliderView.setSliderAdapter(adapter);
                 setSlider();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}