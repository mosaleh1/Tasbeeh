package com.runcode.tasbee7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    NavController mNavController;
    private static final String TAG = "MainActivity";
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //todo(2) setup the toolbar
        mDrawerLayout = findViewById(R.id.main_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.main_activity_toolbar);
        //mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        //todo(1) init navController
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        mNavController = navHostFragment.getNavController();
        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.tasbeehFragment);
        topLevelDestinations.add(R.id.quranFragment);
        AppBarConfiguration abc = new AppBarConfiguration.Builder(topLevelDestinations)
                .setOpenableLayout(mDrawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this,
                mNavController,
                abc);
        //NavigationUI.setupWithNavController(mToolbar, mNavController, abc);
        //NavigationUI.setupActionBarWithNavController(this, mNavController, mDrawerLayout);
        mNavigationView.setNavigationItemSelectedListener(this);
        //handleNavigationDrawerChanges();


        MobileAds.initialize(this, initializationStatus -> {
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-1035381369536866/8798881298",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                       // mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                    }
                });
        if (mRewardedAd != null) {
            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d(TAG, "Ad was shown.");
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when ad fails to show.
                    Log.d(TAG, "Ad failed to show.");
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.
                    Log.d(TAG, "Ad was dismissed.");
                    //mRewardedAd = null;
                }
            });
        }
    }

    private void handleNavigationDrawerChanges() {
        mNavigationView.setCheckedItem(R.id.tasbeehFragment);
        mNavController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.tasbeehFragment) {
                mNavigationView.setCheckedItem(R.id.tasbeehFragment);
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else if (destination.getId() == R.id.quranFragment) {
                mNavigationView.setCheckedItem(R.id.quranFragment);
                mToolbar.setBackgroundColor(getResources().getColor(R.color.customColor));
            } else if (destination.getId() == R.id.azkarFragment) {
                mNavigationView.setCheckedItem(R.id.azkarFragment);
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavController, mDrawerLayout) || super.onSupportNavigateUp();
    }

    private RewardedAd mRewardedAd;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.action_support) {
            showRewardAds();
        }
        return NavigationUI.onNavDestinationSelected(
                item, mNavController) || super.onOptionsItemSelected(item);
    }

    private void showRewardAds() {
        if (mRewardedAd != null) {
            Activity activityContext = MainActivity.this;
            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d(TAG, "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                }
            });
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mToolbar.setTitle("");
        mNavigationView.setCheckedItem(R.id.tasbeehFragment);
    }
}