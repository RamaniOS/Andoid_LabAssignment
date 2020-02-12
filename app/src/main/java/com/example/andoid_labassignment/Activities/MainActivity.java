package com.example.andoid_labassignment.Activities;

import android.os.Bundle;

import com.example.andoid_labassignment.R;
import com.example.andoid_labassignment.ui.menu.DashboardFragment;

import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.andoid_labassignment.ui.menu.DistanceFragment;
import com.example.andoid_labassignment.ui.menu.MapFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

public class MainActivity extends AppCompatActivity {


    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // Added first fragment as Home
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction mFragTrans = manager.beginTransaction();
        mFragTrans.add(R.id.container, new DashboardFragment());
        getSupportActionBar().setTitle("Dashboard");
        mFragTrans.commit();
        setUpNavigationDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawers();
            } else {
                finish();
            }
        }
    }

    public void setUpNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction mFragTrans = manager.beginTransaction();
                getSupportActionBar().setTitle(menuItem.getTitle().toString());
                switch (menuItem.getItemId()) {
                    case R.id.nav_dashboard:
                        mFragTrans.replace(R.id.container, new DashboardFragment());
                        break;
                    case R.id.nav_search:
                        mFragTrans.replace(R.id.container, new MapFragment());
                        break;
                    case R.id.nav_distance:
                        mFragTrans.replace(R.id.container, new DistanceFragment());
                        break;
                }
                mFragTrans.commit();
                drawer.closeDrawers();
                return true;
            }
        });
    }
}