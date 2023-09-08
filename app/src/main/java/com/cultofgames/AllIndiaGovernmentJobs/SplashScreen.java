package com.cultofgames.AllIndiaGovernmentJobs;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.cultofgames.AllIndiaGovernmentJobs.AppInit.Constants;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

import java.util.Arrays;
import java.util.TimeZone;

public class SplashScreen extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 321;
    InterstitialAd mMobInterstitialAds;
    private AppUpdateManager maAppUpdateManager;

    public void InterstitialLoad() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("1ADAD30F02CD84CDE72190C2ABE5EB5E")).build();
        MobileAds.setRequestConfiguration(configuration);
        maAppUpdateManager = AppUpdateManagerFactory.create(this);
        maAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                try {
                    maAppUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, SplashScreen.this, MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        maAppUpdateManager.registerListener(installStateUpdatedListener);
        InterstitialAd.load(getApplicationContext(), getString(R.string.Admob_Interstitial), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                SplashScreen.this.mMobInterstitialAds = interstitialAd;
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
            this.mMobInterstitialAds.show(SplashScreen.this);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(1024, 1024);

        InterstitialLoad();

        if (isNetworkAvailable()){
            if (vpnActive(this)){
                startActivity(new Intent(SplashScreen.this, UsingVpn.class));
                finish();
            }else {
                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        startActivity(new Intent(SplashScreen.this, StartScreen.class));
//                ShowFunUAds();
                        finish();
                    }
                }, 6000);
            }
        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static boolean vpnActive(Context context) {

        // This method doesn't work below API 21
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return false;

        boolean vpnInUse = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(activeNetwork);

            return caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
        }

        Network[] networks = connectivityManager.getAllNetworks();

        for (int i = 0; i < networks.length; i++) {
            NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(networks[i]);
            if (caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                vpnInUse = true;
                break;
            }
        }

        return vpnInUse;
    }


    private void checkCountry() {

        TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (telMgr == null)
            return;

        int simState = telMgr.getSimState();

        switch (simState) {

            // If a SIM card is not available then the
            // country is found using the timezone ID
            case TelephonyManager.SIM_STATE_ABSENT:
                TimeZone tz = TimeZone.getDefault();
                String timeZoneId = tz.getID();
                if (timeZoneId.equalsIgnoreCase(Constants.INDIA_TIME_ZONE_ID)) {
                    // Do something
                } else {
                    // Do something
                }
                break;

            // If a SIM card is available then the telephony
            // manager network country information is used
            case TelephonyManager.SIM_STATE_READY:

                if (telMgr != null) {
                    String countryCodeValue = telMgr.getNetworkCountryIso();

                    // Check if the network country code is "in"
                    if (countryCodeValue.equalsIgnoreCase(Constants.NETWORK_INDIA_CODE)) {
                        // Do something
                    } else {
                        // Do something
                    }
                }
                break;
        }
    }

    private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(@NonNull InstallState installState) {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                showUpdateCompleted();
            }
        }
    };

    private void showUpdateCompleted() {

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"New app is ready",Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install",view -> {
            maAppUpdateManager.completeUpdate();
        });
        snackbar.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == MY_REQUEST_CODE && resultCode != RESULT_OK) {
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    protected void onStop() {

        if (maAppUpdateManager !=null) maAppUpdateManager.unregisterListener(installStateUpdatedListener);
        super.onStop();


    }
}