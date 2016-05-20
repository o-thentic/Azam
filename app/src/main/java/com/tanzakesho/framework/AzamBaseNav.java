package com.tanzakesho.framework;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tanzakesho.azam.Home;
import com.tanzakesho.azam.Packages;
import com.tanzakesho.azam.R;


public class AzamBaseNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int mContentViewId;
    int mToolBarId;
    int mTitleTextId;
    int mDrawerId;
    TextView toolbarTitle;
    String mTypeFace = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        initVariables(mContentViewId, mToolBarId, mTitleTextId, mDrawerId, mTypeFace);

        setContentView(mContentViewId);

        Toolbar toolbar = (Toolbar) findViewById(mToolBarId);
        setSupportActionBar(toolbar);

        toolbarTitle = (TextView) findViewById(mTitleTextId);

        if(!mTypeFace.equals("")){
            Typeface typeface = FontCache.getTypeface(mTypeFace, this);

            toolbarTitle.setTypeface(typeface);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(mDrawerId);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(mDrawerId);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            startActivity(new Intent(this, Home.class));

        } else if (id == R.id.nav_packages) {
            startActivity(new Intent(this, Packages.class));

        } else if (id == R.id.nav_wallet) {

        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_feedback) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(mDrawerId);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initVariables(int mContentViewId, int mToolBarId, int mTitleTextId, int mDrawerId, String mTypeFace){

        this.mContentViewId = mContentViewId;
        this.mToolBarId = mToolBarId;
        this.mTitleTextId = mTitleTextId;
        this.mDrawerId = mDrawerId;
        this.mTypeFace = mTypeFace;
    }

    public void setContentTitle(String title){
        toolbarTitle.setText(title);
    }
}
