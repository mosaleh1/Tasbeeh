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

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.Objects;


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
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        //todo(1) init navController
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        mNavController = navHostFragment.getNavController();
        AppBarConfiguration apc = new AppBarConfiguration.Builder(mNavController.getGraph())
                .setOpenableLayout(mDrawerLayout)
                .build();
        NavigationUI.setupWithNavController(mToolbar, mNavController, apc);
        NavigationUI.setupActionBarWithNavController(this, mNavController, mDrawerLayout);
        mNavigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return NavigationUI.onNavDestinationSelected(
                item, mNavController) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mToolbar.setTitle("");
    }
}