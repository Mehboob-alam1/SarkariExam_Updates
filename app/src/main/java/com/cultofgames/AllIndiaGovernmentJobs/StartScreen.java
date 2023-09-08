package com.cultofgames.AllIndiaGovernmentJobs;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cultofgames.AllIndiaGovernmentJobs.SarkariAdapter.SliderAdapter;
import com.cultofgames.AllIndiaGovernmentJobs.SarkariModal.Slider;

import com.google.android.gms.ads.nativead.NativeAd;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.cultofgames.AllIndiaGovernmentJobs.SarkariHelper.PreferClass;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;


public class StartScreen extends AppCompatActivity{
    boolean doubleBackToExitPressedOnce = false;
    RelativeLayout sharewithfriend;
    CardView nextpage;
    TextView privacy;
    SliderView sliderView;
     DatabaseReference databaseReference;
     ArrayList<Slider> list;
    SliderAdapter adapter;


    private NativeAd mobNativeView;
//    private void NativeBinding(NativeAd nativeAd, NativeAdView adView) {
//        MediaView mediaView = adView.findViewById(R.id.ad_media);
//        adView.setMediaView(mediaView);
//        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
//        adView.setBodyView(adView.findViewById(R.id.ad_body));
//        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
//        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
//        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
//        if (nativeAd.getBody() == null) {
//            adView.getBodyView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getBodyView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
//        }
//        if (nativeAd.getCallToAction() == null) {
//            adView.getCallToActionView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getCallToActionView().setVisibility(View.VISIBLE);
//            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
//        }
//        if (nativeAd.getIcon() == null) {
//            adView.getIconView().setVisibility(View.GONE);
//        } else {
//            ((ImageView) adView.getIconView()).setImageDrawable(
//                    nativeAd.getIcon().getDrawable());
//            adView.getIconView().setVisibility(View.VISIBLE);
//        }
//        adView.setNativeAd(nativeAd);
//    }
//    public void NativeShow(final FrameLayout frameLayout) {
//        AdLoader.Builder builder = new AdLoader.Builder(getApplication(), getString(R.string.Admob_Native));
//
//        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
//            @Override
//            public void onNativeAdLoaded(NativeAd nativeAd) {
//
//                boolean isDestroyed = false;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                    isDestroyed = isDestroyed();
//                }
//                if (isDestroyed || isFinishing() || isChangingConfigurations()) {
//                    nativeAd.destroy();
//                    return;
//                }
//                if (StartScreen.this.mobNativeView != null) {
//                    StartScreen.this.mobNativeView.destroy();
//                }
//                StartScreen.this.mobNativeView = nativeAd;
//                NativeAdView adView = (NativeAdView) getLayoutInflater().inflate(R.layout.mobnative, null);
//                NativeBinding(nativeAd, adView);
//                frameLayout.removeAllViews();
//                frameLayout.addView(adView);
//            }
//        });
//        VideoOptions videoOptions = new VideoOptions.Builder().build();
//        com.google.android.gms.ads.nativead.NativeAdOptions adOptions = new com.google.android.gms.ads.nativead.NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
//        builder.withNativeAdOptions(adOptions);
//        AdLoader adLoader = builder.withAdListener(new AdListener() {
//            @Override
//            public void onAdFailedToLoad(LoadAdError loadAdError) {
//
//
//            }
//        }).build();
//        adLoader.loadAd(new AdRequest.Builder().build());
//
//
//    }
//    public void NativeLoad() {
//        NativeShow((FrameLayout) findViewById(R.id.mobadslayout));
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_start_screen);
//        NativeLoad();


        sliderView = findViewById(R.id.imageSlider);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                PreferClass.setString(getApplicationContext(), PreferClass.TOKEN, "providetokenhere");
                return;
            }
            String token = task.getResult();

            Log.e(":::token", "---" + token);
            PreferClass.setString(getApplicationContext(), PreferClass.TOKEN, token);
        });

        nextpage = findViewById(R.id.nextpage);

        privacy = findViewById(R.id.privacy);

        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartScreen.this, MainActivity.class));
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.privacy))));

            }
        });


        fetchBanners();
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "double tap to exit!", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);


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

    private void fetchBanners() {

        databaseReference.child("Banners").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                  String imageUrl=      snapshot1.child("imageUrl").getValue(String.class);
                  String imageLink=      snapshot1.child("imageLink").getValue(String.class);
                  String pushId=      snapshot1.child("pushId").getValue(String.class);

//                        Toast.makeText(StartScreen.this, ""+data, Toast.LENGTH_SHORT).show();
                        list.add(new Slider(imageLink,imageUrl,pushId));
                    }
                    adapter=new SliderAdapter(list,StartScreen.this);
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