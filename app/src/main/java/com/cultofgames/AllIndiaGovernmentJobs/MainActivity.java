package com.cultofgames.AllIndiaGovernmentJobs;



import static com.cultofgames.AllIndiaGovernmentJobs.SarkariAdapter.SliderAdapter.openCustomTab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cultofgames.AllIndiaGovernmentJobs.SarkariAdapter.SliderAdapter;
import com.cultofgames.AllIndiaGovernmentJobs.SarkariModal.Slider;
import com.cultofgames.AllIndiaGovernmentJobs.games.GamesActivity;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.firebase.crashlytics.BuildConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    LinearLayout findgovjob, currentaffairs, dailynews, examresult, category, profession, qualification, location,playGame,playQuiz,jobGpt,portfolioBuilder,globalNews;
    ImageView notification,share,bannerImage;
    SliderView sliderView;
    DatabaseReference databaseReference,imageRef;
    ArrayList<Slider> list;
    SliderAdapter adapter;
    String imageBanner,imageLink;

    private NativeAd mobNativeView;

    private void NativeBinding(NativeAd nativeAd, NativeAdView adView) {
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
    }

    public void NativeShow(final FrameLayout frameLayout) {
        AdLoader.Builder builder = new AdLoader.Builder(getApplication(), getString(R.string.Admob_Native));

        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {

                boolean isDestroyed = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    isDestroyed = isDestroyed();
                }
                if (isDestroyed || isFinishing() || isChangingConfigurations()) {
                    nativeAd.destroy();
                    return;
                }
                if (MainActivity.this.mobNativeView != null) {
                    MainActivity.this.mobNativeView.destroy();
                }
                MainActivity.this.mobNativeView = nativeAd;
                NativeAdView adView = (NativeAdView) getLayoutInflater().inflate(R.layout.mobnative, null);
                NativeBinding(nativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }
        });
        VideoOptions videoOptions = new VideoOptions.Builder().build();
        com.google.android.gms.ads.nativead.NativeAdOptions adOptions = new com.google.android.gms.ads.nativead.NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {


            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());


    }

    public void NativeLoad() {
        NativeShow((FrameLayout) findViewById(R.id.mobadslayout));
    }

    InterstitialAd mMobInterstitialAds;

    public void InterstitialLoad() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("1ADAD30F02CD84CDE72190C2ABE5EB5E")).build();
        MobileAds.setRequestConfiguration(configuration);
        InterstitialAd.load(getApplicationContext(), getString(R.string.Admob_Interstitial), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                MainActivity.this.mMobInterstitialAds = interstitialAd;
                interstitialAd.setFullScreenContentCallback(
                        new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {

                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {

                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                            }
                        });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
            }
        });

    }

    private void ShowFunUAds() {
        if (this.mMobInterstitialAds != null) {
            this.mMobInterstitialAds.show(MainActivity.this);
        }
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_main);

        InterstitialLoad();
        NativeLoad();

        sliderView = findViewById(R.id.imageSliderMain);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        imageRef = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();
        findgovjob = findViewById(R.id.findgovjob);
        bannerImage=findViewById(R.id.bannerImage);
        currentaffairs = findViewById(R.id.currentaffairs);
        dailynews = findViewById(R.id.dailynews);
        examresult = findViewById(R.id.examresult);
        category = findViewById(R.id.category);
        profession = findViewById(R.id.profession);
        share=findViewById(R.id.btnShare);
        qualification = findViewById(R.id.qualification);
        location = findViewById(R.id.location);
        notification = findViewById(R.id.notification);
        playGame=findViewById(R.id.playGame);
        playQuiz=findViewById(R.id.playQuiz);
        jobGpt=findViewById(R.id.jobGpt);
        portfolioBuilder=findViewById(R.id.portfolioBuilder);
        globalNews=findViewById(R.id.globalNews);


        globalNews.setOnClickListener(view -> {
            CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

            // below line is setting toolbar color
            // for our custom chrome tab.
            customIntent.setToolbarColor(ContextCompat.getColor(this, R.color.purple_200));

            // we are calling below method after
            // setting our toolbar color.
            openCustomTab(MainActivity.this, customIntent.build(), Uri.parse("https://7165.read.newszop.com/"));
        });

        findgovjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AllGovjob.class));
                ShowFunUAds();
            }
        });

        currentaffairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Currentaffairs.class));
                ShowFunUAds();
            }
        });
        share.setOnClickListener(view -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        });
        jobGpt.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this,WebActivity.class);
            i.putExtra("url","https://jobgpt.jobtanks.com/");
            startActivity(i);
        });

        portfolioBuilder.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this,WebActivity.class);
            i.putExtra("url","https://rethrow.jobtanks.com/");
            startActivity(i);
        });
        playQuiz.setOnClickListener(view -> {

            // Toast.makeText(context, ""+slider.getImageUrl(), Toast.LENGTH_SHORT).show();
            CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

            // below line is setting toolbar color
            // for our custom chrome tab.
            customIntent.setToolbarColor(ContextCompat.getColor(this, R.color.purple_200));

            // we are calling below method after
            // setting our toolbar color.
            openCustomTab(MainActivity.this, customIntent.build(), Uri.parse("https://735.game.qureka.com"));
//    Intent i = new Intent(MainActivity.this,WebActivity.class);
//    i.putExtra("url","http://735.game.qureka.com/");
//    startActivity(i);
        });
        playGame.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, GamesActivity.class);
            //i.putExtra("url","https://4515.play.quizzop.com/");
            startActivity(i);
        });
        dailynews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Dailynews.class));
                ShowFunUAds();
            }
        });

        examresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Examresults.class));
                ShowFunUAds();
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Category.class).putExtra("title", "Category"));
                ShowFunUAds();
            }
        });

        profession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Category.class).putExtra("title", "Profession"));
                ShowFunUAds();
            }
        });

        qualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Category.class).putExtra("title", "Qualification"));
                ShowFunUAds();
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Category.class).putExtra("title", "Location"));
                ShowFunUAds();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Notification.class));
                ShowFunUAds();
            }
        });

        bannerImage.setOnClickListener(view -> {
            CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

            // below line is setting toolbar color
            // for our custom chrome tab.
            customIntent.setToolbarColor(ContextCompat.getColor(this, R.color.purple_200));

            // we are calling below method after
            // setting our toolbar color.
            openCustomTab(MainActivity.this, customIntent.build(), Uri.parse(imageLink));

        });
        fetchBanners();
        fetchImage();
    }

    private void fetchImage() {
        imageRef.child("Image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    imageBanner=  snapshot.child("image").getValue(String.class);
                    imageLink=snapshot.child("link").getValue(String.class);
                    Glide.with(MainActivity.this).load(imageBanner).into(bannerImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String imageUrl = snapshot1.child("imageUrl").getValue(String.class);
                        String imageLink = snapshot1.child("imageLink").getValue(String.class);
                        String pushId = snapshot1.child("pushId").getValue(String.class);

//                        Toast.makeText(StartScreen.this, ""+data, Toast.LENGTH_SHORT).show();
                        list.add(new Slider(imageLink, imageUrl, pushId));
                    }
                    adapter = new SliderAdapter(list, MainActivity.this);
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